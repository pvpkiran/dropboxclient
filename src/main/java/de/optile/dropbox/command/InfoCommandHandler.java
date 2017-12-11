package de.optile.dropbox.command;

import de.optile.dropbox.util.Utilities;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.InvalidAccessTokenException;
import com.dropbox.core.v1.DbxAccountInfo;
import com.dropbox.core.v1.DbxClientV1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Created by panikiran on 03.05.17.
 */
@Component
public class InfoCommandHandler implements CommandHandler{

  private static final Logger LOG = LoggerFactory.getLogger(InfoCommandHandler.class);

  @Override
  public void handleCommand(final String[] args) throws Exception {
    String locale = getLocale(args);
    final DbxRequestConfig config = Utilities.getDbxRequestConfigWithLocale(locale);
    final DbxClientV1 client = new DbxClientV1(config, args[1]);

    try {
      printClientInfo(client.getAccountInfo());
    }catch(InvalidAccessTokenException e){
      LOG.error("Invalid Access Token. Recheck your accessToken or try regenerating using 'auth' command.");
    }
  }

  private static String method(String s){
    return s;
  }

  private void printClientInfo(final DbxAccountInfo accountInfo) {
    LOG.info("-----------------------------------------------------------------------");
    LOG.info("User ID :             {}", accountInfo.userId);
    LOG.info("Display Name :        {}", accountInfo.displayName);
    LOG.info("Name :        {} {} ({})", accountInfo.nameDetails.givenName, accountInfo.nameDetails.surname, accountInfo.nameDetails.familiarName);
    LOG.info("E-mail :              {}", accountInfo.email);
    LOG.info("Country :             {}", accountInfo.country);
    LOG.info("Referral link         {}", accountInfo.referralLink);
    LOG.info("-----------------------------------------------------------------------");
  }

  private String getLocale(final String[] args) {
    Locale locale = Locale.getDefault();

    if(args.length == 3)
      locale = new Locale(args[2]);

    return locale.toString();
  }
}
