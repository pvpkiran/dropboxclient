package de.optile.dropbox.command;

import de.optile.dropbox.exception.InvalidCommandFormatException;

import com.dropbox.core.v1.DbxClientV1;
import com.dropbox.core.v1.DbxEntry;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Date;

import static org.mockito.Mockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.whenNew;

/**
 * Created by panikiran on 03.05.17.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({DbxClientV1.class, ListCommandHandler.class})
public class ListCommandHandlerTest {

  private ListCommandHandler listCommandHandler;
  private DbxClientV1 dbxClientV1;

  @Rule
  public ExpectedException thrown= ExpectedException.none();

  @Before
  public void setUp() {
    dbxClientV1 = PowerMockito.mock(DbxClientV1.class);
    listCommandHandler = spy(new ListCommandHandler());
  }

  @Test
  public void testExceptionIsThrownForIncorrectCommand() throws Exception {
    thrown.expect(InvalidCommandFormatException.class);
    listCommandHandler.handleCommand(new String[]{"list"});
  }

  @Test
  public void testHandleCommand() throws Exception {
    whenNew(DbxClientV1.class).withAnyArguments().thenReturn(dbxClientV1);

    DbxEntry dbxEntryRootFolder = new DbxEntry.Folder("/docs", null, true);
    DbxEntry dbxEntryFile = new DbxEntry.File("/docs/fileName.pdf", null, true, 12345, "12 KB", new Date(123456789), new Date(), null);
    DbxEntry dbxEntrySubFolder = new DbxEntry.Folder("/docs/subFolder", null, true);

    DbxEntry.WithChildren withChildren = new DbxEntry.WithChildren(dbxEntryRootFolder, "12324432432432", Lists.newArrayList(dbxEntryFile, dbxEntrySubFolder));

    doReturn(withChildren).when(dbxClientV1).getMetadataWithChildren("/docs", true);
    listCommandHandler.handleCommand(new String[]{"list", "authcode", "/docs"});
    verifyPrivate(listCommandHandler).invoke("printFileInfo", withChildren);
    verifyPrivate(listCommandHandler).invoke("handleDirOutput",
        "{}                       : dir, modified at: \"{}\"",
        "/docs");

    verifyPrivate(listCommandHandler).invoke("handleFileOutput", "- /{}                      :  file, {}, {}, modified at: \"{}\"", "fileName.pdf", "12 KB", "application/pdf", new Date(123456789));
 }
}
