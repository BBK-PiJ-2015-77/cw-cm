import org.junit.Before;
import org.junit.Test;
import java.util.Calendar;
import java.util.Set;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class FutureMeetingImplTest {
	int id;
	Calendar date;
	Set<Contact> contacts;
	FutureMeetingImpl fm;
	
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
		
		fm = new FutureMeetingImpl(id, date, contacts);
	}
	
	@Test
	public void testsgetId() {
		assertEquals(id, fm.getId());
	}
	
	@Test
	public void testsdoesntgetId() {
		assertNotEquals((id-1), fm.getId());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testsbadId() {
		fm = new FutureMeetingImpl(-3, date, contacts);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testszeroId() {
		fm = new FutureMeetingImpl(0, date, contacts);
	}
	
	@Test(expected=NullPointerException.class)
	public void testsnullDate() {
		date = null;
		fm = new FutureMeetingImpl(id, date, contacts);
	}
	
	@Test(expected=NullPointerException.class)
	public void testsnullContacts() {
		contacts = null;
		fm = new FutureMeetingImpl(id, date, contacts);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testsemptyContacts() {
		contacts.clear();
		fm = new FutureMeetingImpl(id, date, contacts);
	}
	
	@Test
	public void testsgetDate() {
		assertEquals(date, fm.getDate());
	}
	
	@Test
	public void testsgetContacts() {
		assertEquals(contacts, fm.getContacts());
	}
	
}