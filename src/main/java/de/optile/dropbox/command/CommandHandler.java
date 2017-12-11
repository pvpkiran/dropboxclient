package de.optile.dropbox.command;

/**
 * Created by panikiran on 03.05.17.
 */
public interface CommandHandler {
  public void handleCommand(String[] args) throws Exception;
}
