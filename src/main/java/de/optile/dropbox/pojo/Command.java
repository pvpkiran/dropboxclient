package de.optile.dropbox.pojo;

import de.optile.dropbox.exception.UnknownCommandException;
import lombok.Getter;

/**
 * Created by panikiran on 03.05.17.
 */
@Getter
public enum Command {

  AUTH("auth", "authCommandHandler"),
  INFO("info", "infoCommandHandler"),
  List("list", "listCommandHandler")
  ;

  private final String command;
  private final String commandHandlerBeanName;

  Command(final String com, final String commandHandlerBeanName) {
    this.command = com;
    this.commandHandlerBeanName = commandHandlerBeanName;
  }

  public static String getCommandHandlerForCommand(final String command) throws UnknownCommandException {
    for(Command commandHandler :values()){
      if(commandHandler.getCommand().equals(command))
        return commandHandler.getCommandHandlerBeanName();
    }
    throw new UnknownCommandException(String.format("Command %s is unrecognized. Valid Commands are ['auth', 'info', 'list']", command));
  }
}
