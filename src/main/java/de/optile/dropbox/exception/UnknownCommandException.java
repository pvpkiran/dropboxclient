package de.optile.dropbox.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by panikiran on 03.05.17.
 */
public class UnknownCommandException extends Exception {
  private static final Logger LOG = LoggerFactory.getLogger(UnknownCommandException.class);

  public UnknownCommandException(String message) {
    LOG.info("---------------------------------------------------------------------------------");
    LOG.error(message);
    LOG.info("---------------------------------------------------------------------------------");
  }
}
