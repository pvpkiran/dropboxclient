package de.optile.dropbox.util;

import com.dropbox.core.DbxRequestConfig;

import java.util.Locale;

/**
 * Created by panikiran on 03.05.17.
 */
public class Utilities {

  public static DbxRequestConfig getDbxRequestConfigWithLocale(final String... args) {
    if(args.length == 0)
      return DbxRequestConfig.newBuilder("DropBoxClient")
          .withUserLocale(Locale.getDefault().toString())
          .build();
    else
      return DbxRequestConfig.newBuilder("DropBoxClient")
          .withUserLocale(args[0])
          .build();
  }
}
