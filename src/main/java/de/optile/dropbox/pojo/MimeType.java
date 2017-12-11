package de.optile.dropbox.pojo;

import lombok.Getter;

/**
 * Created by panikiran on 03.05.17.
 */
@Getter
public enum MimeType {

  PDF("pdf", "application/pdf"),
  GZ("gz", "application/x-gzip"),
  RAR("rar", "application/rar"),
  JAR("jar", "application/java-archive"),
  XML("xml", "application/xml"),
  PNG("png", "image/png"),
  JPG("jpg", "image/jpeg"),
  MPEG(".mpeg", "video/mpeg"),
  DEFAULT("NotMatched", "text/x-")
  ;

  private final String extension;
  private final String mimeType;

  MimeType(final String extension, final String mimeType) {
    this.extension = extension;
    this.mimeType = mimeType;
  }

  public static String getMimeType(String extension){
    for(MimeType mimeType : values()){
      if(mimeType.extension.equals(extension))
        return mimeType.getMimeType();
    }
    return DEFAULT.getMimeType().concat(extension);
  }
}
