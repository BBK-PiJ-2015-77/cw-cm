import org.junit.Before;
import org.junit.Test;
import java.util.Calendar;
import java.util.Set;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class ContactManagerImplTest {
	
	Calendar date;
	Calendar currentDate;
	Set<Contact> contacts;
	ContactManagerImpl cm;
	
	@Before
	public void setUp() {
		date = Calendar.getInstance();
		date.set(2015,12,3);
		currentDate = Calendar.getInstance();
		contacts = new HashSet<Contact>();
		Contact con1 = new ContactImpl(1, "Tom", "Good");
		Contact con2 = new ContactImpl(2, "Tim", "Bad");
		contacts.add(con1);
		contacts.add(con2);
		
		cm = new ContactManagerImpl();
	}
	
	@Test
	public void testsaddFutureMeeting() {
		int aFM = cm.addFutureMeeting(contacts, date);
		assertTrue(aFM>0);
		//some test that shows an int is returned
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testsfutureMeetingDateAFM() {
		//IllegalArgumentException if date is in future
      	currentDate.add(Calendar.DAY_OF_YEAR, +1);
		cm.addFutureMeeting(contacts, currentDate);
	}
	
	/**
	//test if the contact exists(check id?)
	@Test(expected=IllegalArgumentException.class)
	public void testsfutureMeetingDateAFM() {
		//IllegalArgumentException if contact is unknown
	}
	*/
	
	@Test(expected=NullPointerException.class)
	public void testsnullDateAFM() {
		//IllegalArgumentException if date is in future
      	date = null;
		cm.addFutureMeeting(contacts, date);
	}
	
	
}