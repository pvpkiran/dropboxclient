package de.optile.dropbox.command;

import de.optile.dropbox.exception.InvalidCommandFormatException;
import de.optile.dropbox.util.Utilities;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by panikiran on 03.05.17.
 */

@Component
public class AuthCommandHandler implements CommandHandler {

  private static final Logger LOG = LoggerFactory.getLogger(AuthCommandHandler.class);
  private final DbxWebAuth.Request webAuthRequest;
  private final InputStream inputStream;

  @Autowired
  AuthCommandHandler(DbxWebAuth.Request webAuthRequest, InputStream inputStream) {
    this.webAuthRequest = webAuthRequest;
    this.inputStream = inputStream;
  }

  @Override
  public void handleCommand(final String[] args) throws Exception{

    if(args.length < 3 ) {
      throw new InvalidCommandFormatException("auth command should have two arguments. Format is <auth appKey appSecret>");
    }

    final DbxWebAuth webAuth = getDbxWebAuth(args);
    String authorizeUrl = webAuth.authorize(webAuthRequest);

    LOG.info("Use the below link to authorize the app to access your client information from DropBox. You might have to login first.");
    LOG.info(authorizeUrl);
    LOG.info("Paste the authorization code here which you got and press Enter.");

    try {
      String code = new BufferedReader(new InputStreamReader(inputStream)).readLine().trim();

      final DbxAuthFinish authFinish =  webAuth.finishFromCode(code);
      String accessToken = authFinish.getAccessToken();
      LOG.info("Use this access token for future requests: {}", accessToken);
    } catch (DbxException e) {
      LOG.info("Invalid client key and or client secret.");
    } catch (IOException e) {
      LOG.info("Error reading authorization code");
    }
  }

   DbxWebAuth getDbxWebAuth(String[] args) {
    final DbxRequestConfig config = Utilities.getDbxRequestConfigWithLocale();

    final DbxAppInfo dbxAppInfo = new DbxAppInfo(args[1], args[2]);
    return new DbxWebAuth(config, dbxAppInfo);
  }
}
