package test.java.dbTests;

import java.io.File;

import net.viperfish.journal.archieveDB.ArArchiveEntryDatabase;
import net.viperfish.journal.archieveDB.ArchiveEntryDatabase;

public class ArArchiveDatabaseTest extends ArchiveDatabaseTest {

	@Override
	protected ArchiveEntryDatabase getADB(File archiveFile) {
		return new ArArchiveEntryDatabase(archiveFile);
	}

}