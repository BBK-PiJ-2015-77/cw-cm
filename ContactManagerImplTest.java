import org.junit.Before;
import org.junit.Test;
import java.util.Calendar;
import java.util.Set;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class ContactManagerImplTest {
	
	Calendar pastDate;
	Calendar currentDate;
	Calendar futureDate;
	Set<Contact> contacts;
	ContactManagerImpl cm;
	String text;
	
	@Before
	public void setUp() {
		pastDate = Calendar.getInstance();
		pastDate.set(2015,12,3);
		
		currentDate = Calendar.getInstance();
		
		futureDate = Calendar.getInstance();
		futureDate.add(Calendar.DAY_OF_YEAR, +1);
		
		contacts = new HashSet<Contact>();
		Contact con1 = new ContactImpl(1, "Tom", "Good");
		Contact con2 = new ContactImpl(2, "Tim", "Bad");
		contacts.add(con1);
		contacts.add(con2);
		text = "Meeting notes";
		
		cm = new ContactManagerImpl();
	}
	
	/////////addFutureMeeting////////////
	
	@Test
	public void testsaddFutureMeeting() {
		int aFM = cm.addFutureMeeting(contacts, futureDate);
		assertTrue(aFM>0);
		//some test that shows an int is returned
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testsfutureMeetingDateAFM() {
		//IllegalArgumentException if date is in past
		cm.addFutureMeeting(contacts, pastDate);
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
      	futureDate = null;
		cm.addFutureMeeting(contacts, futureDate);
	}
	
	@Test(expected=NullPointerException.class)
	public void testsnullContactsAFM() {
      	contacts = null;
		cm.addFutureMeeting(contacts, futureDate);
	}
	
	/////////getPastMeeting////////////
	
	/////////getFutureMeeting////////////
	
	/////////getMeeting////////////
	
	/////////getFutureMeetingList////////////
	
	/////////getFutureMeetingList////////////
	
	/////////getPastMeetingList////////////
	
	/////////addNewPastMeeting////////////
	
	@Test(expected=IllegalArgumentException.class)
	public void testsaddPastMeetingDateEmptyContacts() {
		//IllegalArgumentException if list of contacts is empty
		contacts.clear();
		cm.addNewPastMeeting(contacts, pastDate, text);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testsaddPastMeetingDateNonContact() {
		//IllegalArgumentException if any of the contacts don't exist
		Set<Contact> badContacts = new HashSet<Contact>();
		Contact con1 = new ContactImpl(1, "Jane", "notes1");
		Contact con2 = new ContactImpl(2, "June", "notes2");
		badContacts.add(con1);
		badContacts.add(con2);
		cm.addNewPastMeeting(badContacts, pastDate, text);
	}
	
	@Test(expected=NullPointerException.class)
	public void testsaddPastMeetingDateNullcontacts() {
		//NullPointerException if date is null
		contacts = null;
		cm.addNewPastMeeting(contacts, pastDate, text);
	}
	/**
	//doesnt work - illegalArgumentException - the contacts need to be added first
	@Test(expected=NullPointerException.class)
	public void testsaddPastMeetingDateNullpastDate() {
		//NullPointerException if date is null
		cm.addNewContact("Tom", "Good");
		cm.addNewContact("Tim", "Bad");
		pastDate = null;
		cm.addNewPastMeeting(contacts, pastDate, text);
	}
	
	//doesnt work - illegalArgumentException  - the contacts need to be added first, if contacts empty, or if zero or negative id
	@Test(expected=NullPointerException.class)
	public void testsaddPastMeetingDateNulltext() {
		//NullPointerException if text is null
		cm.addNewContact("Tom", "Good");
		cm.addNewContact("Tim", "Bad");
		text = null;
		cm.addNewPastMeeting(contacts, pastDate, text);
	}
	*/
	
	/////////addMeetingNotes////////////

    /////////addNewContact////////////
	
	@Test
	public void testsaddNewContacts1() {
		int result = cm.addNewContact("Tom", "Good");
		assertEquals(1, result);
	}
	
	@Test
	public void testsaddNewContacts2() {
		cm.addNewContact("Tom", "Good");
		int result = cm.addNewContact("Tim", "Bad");
		assertEquals(2, result);
	}

    /////////getContacts////////////
	
	@Test
	public void testsgetContacts() {
		cm.addNewContact("Tom", "Good");
		//cm.addNewContact("Tim", "Bad");
		Set<Contact> result = cm.getContacts("");
		for ( Contact testContact : result) {
			assertTrue(contacts.contains(testContact));
		}
		//this doesnt work because they aren't pointing to the same object
		//need to check that the information contained in each 'Contact'
		//is present in each list
		
	}
	//the hashset doesnt add things in order, so the two sets may well be different
	
    /////////getContacts////////////
    
    /////////flush////////////
	
	//private methods here
	
	/////////check checkContacts/////
	
}