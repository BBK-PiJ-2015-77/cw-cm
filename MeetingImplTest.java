import org.junit.Before;
import org.junit.Test;
import java.util.Calendar;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class MeetingImplTest {
	int id;
	Calendar date;
	Set<Contact> contacts;
	MeetingImpl mi;
	
	@Before
	public void setUp() {
		id = 11;
		date = Calendar.getInstance();
		date.set(2016,12,3);
		Contact con1 = new ContactImpl(1, "Tom", "Good");
		Contact con2 = new ContactImpl(2, "Tim", "Bad");
		contacts.add(con1);
		contacts.add(con2);
		
		mi = new MeetingImpl(id, date, contacts);
	}
	
	@Test
	public void testsgetId() {
		assertEquals(id, mi.getId());
	}
	
	@Test
	public void testsgetDate() {
		assertEquals(date, mi.getDate());
	}
	
	@Test
	public void testsgetContacts() {
		assertEquals(contacts, mi.getContacts());
	}
	
}