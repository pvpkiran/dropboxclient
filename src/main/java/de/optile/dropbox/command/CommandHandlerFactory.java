package de.optile.dropbox.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by panikiran on 03.05.17.
 */
@Component
public class CommandHandlerFactory {

  @Autowired
  private Map<String, CommandHandler> commandHandlerMap;

  public CommandHandler   getCommandHandler(final String commandHandlerName){
    return commandHandlerMap.get(commandHandlerName);
  }
}
