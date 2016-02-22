import org.junit.Before;
import org.junit.Test;
import java.util.Calendar;
import java.util.Set;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class PastMeetingImplTest {
	int id;
	Calendar date;
	Set<Contact> contacts;
	String notes;
	PastMeetingImpl pm;
	
	@Before
	public void setUp() {
		id = 11;
		date = Calendar.getInstance();
		date.set(2016,12,3);
		contacts = new HashSet<Contact>();
		Contact con1 = new ContactImpl(1, "Tom", "Good");
		Contact con2 = new ContactImpl(2, "Tim", "Bad");
		contacts.add(con1);
		contacts.add(con2);
		notes = "Meeting notes.";
		
		pm = new PastMeetingImpl(id, date, contacts, notes);
	}
	
	@Test
	public void testsgetNotes() {
		assertEquals(notes, pm.getNotes());
	}
	
	@Test
	public void testsdoesntGetNotes() {
		assertNotEquals("Random", pm.getNotes());
	}
	
	@Test
	public void testsgetId() {
		assertEquals(id, pm.getId());
	}
	
	@Test
	public void testsdoesntgetId() {
		assertNotEquals((id-1), pm.getId());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testsbadId() {
		pm = new PastMeetingImpl(-3, date, contacts, notes);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testszeroId() {
		pm = new PastMeetingImpl(0, date, contacts, notes);
	}
	
	@Test(expected=NullPointerException.class)
	public void testsnullDate() {
		date = null;
		pm = new PastMeetingImpl(id, date, contacts, notes);
	}
	
	@Test(expected=NullPointerException.class)
	public void testsnullContacts() {
		contacts = null;
		pm = new PastMeetingImpl(id, date, contacts, notes);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testsemptyContacts() {
		contacts.clear();
		pm = new PastMeetingImpl(id, date, contacts, notes);
	}
	
	@Test
	public void testsgetDate() {
		assertEquals(date, pm.getDate());
	}
	
	@Test
	public void testsgetContacts() {
		assertEquals(contacts, pm.getContacts());
	}

}