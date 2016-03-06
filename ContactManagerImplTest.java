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
		addTestContacts();
		Set<Contact> cmContactList = cm.getContacts("");
		boolean result = true;
		for ( Contact testContact : cmContactList) {
			if (containsContact(contacts, testContact)) {
				//do nothing
			} else {
				result = false;
			}
		}
		assertTrue(result);		
	}
	
	@Test
	public void testsgetContactsFalse() {
		Set<Contact> badContacts = addBadTestContacts();
		addTestContacts();
		Set<Contact> cmContactList = cm.getContacts("");
		boolean result = true;
		for ( Contact testContact : cmContactList) {
			if (containsContact(badContacts, testContact)) {
				//do nothing
			} else {
				result = false;
			}
		}
		assertFalse(result);		
	}
	
	@Test
	public void testsgetContactsName() {
		Set<Contact> testList = new HashSet<Contact>();
		Contact tim = new ContactImpl(2, "Tim", "Bad");
		testList.add(tim);
		
		addTestContacts();
		Set<Contact> cmContactList = cm.getContacts("Tim");
		
		boolean result = true;
		for ( Contact testContact : cmContactList) {
			if (containsContact(testList, testContact)) {
				//do nothing
			} else {
				result = false;
			}
		}
		assertTrue(result);
	}
	
	@Test(expected=NullPointerException.class)
	public void testsgetContactsNull() {
		//NullPointerException if String name is null
		String nullName = null;
		cm.getContacts(nullName);
	}
	
    /////////getContacts////////////
    
    @Test
	public void testsgetContactsID() {
		Set<Contact> testList = new HashSet<Contact>();
		Contact tim = new ContactImpl(2, "Tim", "Bad");
		testList.add(tim);
		
		addTestContacts();
		Set<Contact> cmContactList = cm.getContacts(2);
		
		boolean result = true;
		for ( Contact testContact : cmContactList) {
			if (containsContact(testList, testContact)) {
				//do nothing
			} else {
				result = false;
			}
		}
		assertTrue(result);
	}
	
	@Test
	public void testsgetContactsID2() {
		Set<Contact> testList = new HashSet<Contact>();
		Contact tom = new ContactImpl(1, "Tom", "Good");
		testList.add(tom);
		
		addTestContacts();
		Set<Contact> cmContactList = cm.getContacts(1);
		
		boolean result = true;
		for ( Contact testContact : cmContactList) {
			if (containsContact(testList, testContact)) {
				//do nothing
			} else {
				result = false;
			}
		}
		assertTrue(result);
	}
    
    /////////flush////////////
	
	//private methods here
	
	private void addTestContacts() {
		cm.addNewContact("Tom", "Good");
		cm.addNewContact("Tim", "Bad");
	}
	
	private Set<Contact> addBadTestContacts() {
		Set<Contact> badContacts = new HashSet<Contact>();
		Contact con1 = new ContactImpl(1, "Jane", "notes1");
		Contact con2 = new ContactImpl(2, "June", "notes2");
		badContacts.add(con1);
		badContacts.add(con2);
		return badContacts;
	}
	
	private boolean equalsContact(Contact con1, Contact con2) {
		boolean result = false;
		if (con1.getId() == con2.getId() &&
			con1.getName().equals(con2.getName()) && 
			con1.getNotes().equals(con2.getNotes())) {
				result = true;
				return result;
		} else {
			return result;
		}
	}
	
	private boolean containsContact(Set<Contact> list, Contact con) {
		boolean result = false;
		if (list.isEmpty()) {
			return result;
		} else {
			for ( Contact check : list) {
				if (equalsContact(check, con)) {
					result = true;
					return result;
				} 
			}
		}
		return result;
	}
	
	/////////check checkContacts/////
	
}