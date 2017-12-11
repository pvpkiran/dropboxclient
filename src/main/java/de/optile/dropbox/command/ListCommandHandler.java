package de.optile.dropbox.command;

import de.optile.dropbox.exception.InvalidCommandFormatException;
import de.optile.dropbox.pojo.MimeType;
import de.optile.dropbox.util.Utilities;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.InvalidAccessTokenException;
import com.dropbox.core.v1.DbxClientV1;
import com.dropbox.core.v1.DbxEntry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by panikiran on 03.05.17.
 */
@Component
public class ListCommandHandler implements CommandHandler{

  private static final Logger LOG = LoggerFactory.getLogger(ListCommandHandler.class);

  @Override
  public void handleCommand(final String[] args) throws Exception {

    if(args.length < 3 ) {
      throw new InvalidCommandFormatException("list command should have two arguments. Format is <list authcode filepath/dirpath>");
    }

    final DbxRequestConfig config = Utilities.getDbxRequestConfigWithLocale();
    final DbxClientV1 client = new DbxClientV1(config, args[1]);

    try {
      final DbxEntry.WithChildren metadataWithChildren = client.getMetadataWithChildren(args[2], true);
      printFileInfo(metadataWithChildren);
    }catch(InvalidAccessTokenException e){
      LOG.error("Invalid Access Token. Recheck your accessToken or try regenerating using 'auth' command.");
    }
  }

  private void printFileInfo(DbxEntry.WithChildren metadataWithChildren) {
    LOG.info("-----------------------------------------------------------------------");
    final List<DbxEntry> children = metadataWithChildren.children;

    if(children == null) {
      handleFileOutput("{}                      :  file, {}, {}, modified at: \"{}\"", metadataWithChildren.entry.path, metadataWithChildren.entry.asFile().humanSize, getMime(metadataWithChildren.entry.asFile()), metadataWithChildren.entry.asFile().lastModified);
    } else {
      handleDirOutput("{}                       : dir, modified at: \"{}\"", metadataWithChildren.entry.path);
      children.forEach(child -> {
        if (child.isFolder()) {
          handleDirOutput("- /{}                       : dir, modified at: \"{}\"", child.name);
        } else if (child.isFile()) {
          handleFileOutput("- /{}                      :  file, {}, {}, modified at: \"{}\"", child.name, child.asFile().humanSize, getMime(child.asFile()), child.asFile().lastModified);
        }
      });
    }
    LOG.info("-----------------------------------------------------------------------");
  }

  private void handleDirOutput(String template, String name) { //Dropbox API doesn't provide modified information for directories. Hence using current Date.
    LOG.info(template, name, new Date());
  }

  private void handleFileOutput(String template, String path, String humanSize, String mime, Date lastModified) {
    LOG.info(template, path,
        humanSize,
        mime,
        lastModified);
  }

  //DropBox API doesn't provide mime information. Hence implementing a small subset of mime types here.
  private String getMime(DbxEntry.File file) {
    String fileName = file.name;
    String extension = fileName.substring(fileName.lastIndexOf(".") +1);
    return MimeType.getMimeType(extension);
  }
}
