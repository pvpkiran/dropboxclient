package de.optile.dropbox.runner;

import de.optile.dropbox.command.CommandHandler;
import de.optile.dropbox.command.CommandHandlerFactory;
import de.optile.dropbox.pojo.Command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by panikiran on 03.05.17.
 */
@Component
public class CommandLineExecutor implements CommandLineRunner {

  private static final Logger LOG = LoggerFactory.getLogger(CommandLineExecutor.class);

  private final CommandHandlerFactory commandHandlerFactory;

  @Autowired
  CommandLineExecutor(CommandHandlerFactory commandHandlerFactory) {
    this.commandHandlerFactory = commandHandlerFactory;
  }

  @Override
  public void run(String... args) throws Exception {

    if(args.length < 2){
      LOG.info("Command parameters incorrect. Please check README document");
      return;
    }

    String commandHandlerBeanName = Command.getCommandHandlerForCommand(args[0]);
    final CommandHandler commandHandler = commandHandlerFactory.getCommandHandler(commandHandlerBeanName);
    commandHandler.handleCommand(args);
  }

}
