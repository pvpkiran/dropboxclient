package de.optile.dropbox.command;

import com.dropbox.core.v1.DbxAccountInfo;
import com.dropbox.core.v1.DbxClientV1;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.whenNew;

/**
 * Created by panikiran on 03.05.17.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({DbxClientV1.class, InfoCommandHandler.class})
public class InfoCommandHandlerTest {

  private InfoCommandHandler infoCommandHandler;
  private DbxClientV1 dbxClientV1;

  @Before
  public void setUp() {
    dbxClientV1 = PowerMockito.mock(DbxClientV1.class);
    infoCommandHandler = spy(new InfoCommandHandler());
  }

  @Test
  public void testHandleCommand() throws Exception {
    whenNew(DbxClientV1.class).withAnyArguments().thenReturn(dbxClientV1);

    DbxAccountInfo.NameDetails nameDetails = new DbxAccountInfo.NameDetails("familiarName", "givenName", "surname");
    final DbxAccountInfo dbxAccountInfo =  new DbxAccountInfo(12334L, "displayName", "DE", "https://referral/link", null, "abc@def.com", nameDetails, true);

    doAnswer(invocationOnMock -> dbxAccountInfo).when(dbxClientV1).getAccountInfo();

    infoCommandHandler.handleCommand(new String[]{"info", "accessCode"});
    PowerMockito.verifyNew(DbxClientV1.class);
    verifyPrivate(infoCommandHandler).invoke("printClientInfo", dbxAccountInfo);
  }

  @Test
  public void test() throws Exception {
    String s = Whitebox.invokeMethod(infoCommandHandler, "method", "sumne");
    assertEquals(s, "sumne");
  }
}
