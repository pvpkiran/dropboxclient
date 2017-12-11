package de.optile.dropbox.runner;

import de.optile.dropbox.command.CommandHandlerFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Created by panikiran on 03.05.17.
 */
@RunWith(SpringRunner.class)
public class CommandLineExecutorTest {

  private CommandLineExecutor commandLineExecutor;

  @MockBean
  private CommandHandlerFactory commandHandlerFactory;

  @Before
  public void setUp(){
    MockitoAnnotations.initMocks(this);
    commandLineExecutor = new CommandLineExecutor(commandHandlerFactory);
  }

  @Test
  public void incorrectArgumentsTest() throws Exception {
    commandLineExecutor.run("auth");
    verify(commandHandlerFactory, never()).getCommandHandler(anyString());
  }
}
