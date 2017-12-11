package de.optile.dropbox.command;

import de.optile.dropbox.config.BeansConfig;
import de.optile.dropbox.exception.InvalidCommandFormatException;

import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxWebAuth;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;

/**
 * Created by panikiran on 03.05.17.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({DbxAuthFinish.class, AuthCommandHandler.class})
@ContextConfiguration(classes = BeansConfig.class)
public class AuthCommandHandlerTest {

  private AuthCommandHandler authCommandHandler;

  @Autowired
  private DbxWebAuth.Request webAuthRequest;

  @Rule
  public ExpectedException thrown= ExpectedException.none();

  @Mock
  private DbxWebAuth dbxWebAuth;

  @Before
  public void setUp() throws DbxException {
    MockitoAnnotations.initMocks(this);

    String expected = "testinput";
    ByteArrayInputStream fakeInputStream = new ByteArrayInputStream(expected.getBytes());

    authCommandHandler = spy(new AuthCommandHandler(webAuthRequest, fakeInputStream));
  }

  @Test
  public void testExceptionIsThrownForIncorrectCommand() throws Exception {
    thrown.expect(InvalidCommandFormatException.class);
    authCommandHandler.handleCommand(new String[]{"auth", "somekey"});
  }

  @Test
  public void testHandleCommand() throws Exception {
    final ArgumentCaptor<String[]> argumentCaptor = ArgumentCaptor.forClass(String[].class);

    doAnswer(invocation-> {
      String[] args = argumentCaptor.getValue();
      assertEquals(3, args.length);
      return dbxWebAuth;
    }).when(authCommandHandler).getDbxWebAuth(argumentCaptor.capture());

    DbxAuthFinish dbxAuthFinish = PowerMockito.mock(DbxAuthFinish.class);
    doReturn(dbxAuthFinish).when(dbxWebAuth).finishFromCode("testinput");

    authCommandHandler.handleCommand(new String[]{"auth", "somekey", "somesecret"});
    verifyPrivate(dbxAuthFinish).invoke("getAccessToken");
  }
}
