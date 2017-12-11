package de.optile.dropbox.command;

import de.optile.dropbox.config.BeansConfig;
import de.optile.dropbox.exception.UnknownCommandException;
import de.optile.dropbox.pojo.Command;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

/**
 * Created by panikiran on 03.05.17.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {BeansConfig.class, ListCommandHandler.class, AuthCommandHandler.class, InfoCommandHandler.class, CommandHandlerFactory.class})
public class CommandHandlerFactoryTest {

  @Rule
  public ExpectedException thrown= ExpectedException.none();

  @Autowired
  private CommandHandlerFactory commandHandlerFactory;

  @Test
  public void testIfCorrectHandlerIsRetrieved() throws UnknownCommandException {
    final CommandHandler commandHandler = commandHandlerFactory.getCommandHandler(Command.getCommandHandlerForCommand("auth"));
    assertTrue(commandHandler instanceof AuthCommandHandler);
  }

  @Test
  public void testIfExceptionIsThrownForInvalidCommand() throws UnknownCommandException {
    thrown.expect(UnknownCommandException.class);
    commandHandlerFactory.getCommandHandler(Command.getCommandHandlerForCommand("junk"));
  }

}
