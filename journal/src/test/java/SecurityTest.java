package test.java;

import net.viperfish.journal.Configuration;
import net.viperfish.journal.framework.Journal;
import net.viperfish.journal.persistent.EntryDatabase;
import net.viperfish.journal.secure.SecureEntryDatabaseWrapper;

import org.junit.Assert;
import org.junit.Test;

public class SecurityTest {
	private EntryDatabase db;
	private SecureEntryDatabaseWrapper wrapper;

	public SecurityTest() {
		Configuration.defaultConfig();
		Configuration.getProperty().setProperty("user.password", "password");
		db = new DatabaseStub();
		wrapper = new SecureEntryDatabaseWrapper(db,
				Configuration.getProperty());
		wrapper.setPassword("password");

	}

	@Test
	public void testGet() {
		wrapper.clear();
		Journal j = new Journal();
		j.setSubject("test get");
		j.setContent("test get content");
		Journal result = wrapper.addEntry(j);
		result = wrapper.getEntry(result.getId());
		String plainContent = result.getContent();
		Assert.assertEquals("test get content", plainContent);
		Assert.assertEquals("test get", result.getSubject());
		wrapper.clear();
	}

	@Test
	public void testEdit() {
		Journal j = new Journal();
		j.setSubject("unedited subject");
		j.setContent("unedited content");
		Long id = wrapper.addEntry(j).getId();
		Journal edit = new Journal();
		edit.setSubject("edited");
		edit.setContent("edited content");
		wrapper.updateEntry(id, edit);
		Journal result = wrapper.getEntry(id);
		Assert.assertEquals("edited", result.getSubject());
		Assert.assertEquals("edited content", result.getContent());
	}
}
