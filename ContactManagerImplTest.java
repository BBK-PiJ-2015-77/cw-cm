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
		addTestContacts();
		int aFM = cm.addFutureMeeting(contacts, futureDate);
		assertTrue(aFM>0);
		//some test that shows an int is returned
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testsfutureMeetingDateAFM() {
		//IllegalArgumentException if date is in past
		cm.addFutureMeeting(contacts, pastDate);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testsfutureMeetingDateBadContacts() {
		//IllegalArgumentException if contact is unknown of doesn't exist
		Set<Contact> badContacts = new HashSet<Contact>();
		Contact con1 = new ContactImpl(1, "Jane", "notes1");
		Contact con2 = new ContactImpl(2, "June", "notes2");
		badContacts.add(con1);
		badContacts.add(con2);
		cm.addFutureMeeting(badContacts, futureDate);
	}
	
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
	
	@Test
	public void testsgetPastMeeting() {
		addTestContacts();
		cm.addNewPastMeeting(contacts, pastDate, text);
		PastMeeting pastMeeting = cm.getPastMeeting(1);
		assertEquals(text, pastMeeting.getNotes());
		assertEquals(pastDate, pastMeeting.getDate());
		
		Set<Contact> pastMeetingContacts = pastMeeting.getContacts();
		
		assertTrue(equalsContactList(pastMeetingContacts,contacts));
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void testsgetPastMeetingFutureID() {
		addTestContacts();
		cm.addFutureMeeting(contacts, futureDate);
		cm.getPastMeeting(1);
	}
	
	@Test
	public void testsgetPastMeetingNull() {
		addTestContacts();
		cm.addNewPastMeeting(contacts, pastDate, text);
		PastMeeting pastMeeting = cm.getPastMeeting(100);
		assertEquals(pastMeeting,null);
	}
	
	
	/////////getFutureMeeting////////////
	
	@Test
	public void testsgetFutureMeeting() {
		addTestContacts();
		cm.addNewPastMeeting(contacts, futureDate, text);
		FutureMeeting fm = cm.getFutureMeeting(1);
		assertEquals(pastDate, fm.getDate());
		
		Set<Contact> futureMeetingContacts = fm.getContacts();
		
		assertTrue(equalsContactList(futureMeetingContacts,contacts));
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void testsgetFutureMeetingPastID() {
		addTestContacts();
		cm.addNewPastMeeting(contacts, pastDate, text);
		cm.getFutureMeeting(1);
	}
	
	@Test
	public void testsgetFutureMeetingNull() {
		addTestContacts();
		cm.addNewPastMeeting(contacts, pastDate, text);
		PastMeeting pastMeeting = cm.getPastMeeting(100);
		assertEquals(pastMeeting,null);
	}
	
	/////////getMeeting////////////
	
	@Test
	public void testsgetMeeting() {
		addTestContacts();
		cm.addNewPastMeeting(contacts, pastDate, text);
		Meeting meeting = cm.getMeeting(1);
		assertEquals(pastDate, meeting.getDate());
		
		Set<Contact> meetingContacts = meeting.getContacts();
		
		assertTrue(equalsContactList(meetingContacts,contacts));
	}
	
	@Test
	public void testsgetMeetingNull() {
		addTestContacts();
		cm.addNewPastMeeting(contacts, pastDate, text);
		Meeting meeting = cm.getMeeting(100);
		assertEquals(meeting,null);
	}
	
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
		//NullPointerException if contacts is null
		contacts = null;
		cm.addNewPastMeeting(contacts, pastDate, text);
	}
	
	@Test(expected=NullPointerException.class)
	public void testsaddPastMeetingDateNullpastDate() {
		//NullPointerException if date is null
		addTestContacts();
		pastDate = null;
		cm.addNewPastMeeting(contacts, pastDate, text);
	}
	
	@Test(expected=NullPointerException.class)
	public void testsaddPastMeetingDateNulltext() {
		//NullPointerException if text is null
		addTestContacts();
		text = null;
		cm.addNewPastMeeting(contacts, pastDate, text);
	}
	
	
	/////////addMeetingNotes////////////
	/**
	@Test
	public void testsaddMeetingNotes() {
		addTestContacts();
		text = "";
		cm.addNewPastMeeting(contacts, pastDate, text);
		String testNotes = "Testing addMeetingNotes";
		cm.addMeetingNotes(1, testNotes);
		Meeting pastMeeting = cm.getPastMeeting(1);
		assertEquals(testNotes, pastMeeting.getNotes());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testsaddMeetingNotesNoMeeting() {
		//IllegalArgumentException if meeting doesn't exist
		addTestContacts();
		cm.addMeetingNotes(1, "Testing addMeetingNotes");
	}
	
	*/
	
	
	
	//IllegalStateException if the meeting date is set for the future
	
	//NullPointerException if notes is null
	
	
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
			if (containsContact(contacts, testContact)) {}
			else {
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
			if (containsContact(badContacts, testContact)) {}
			else {
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
			if (containsContact(testList, testContact)) {}
			else {
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
			if (containsContact(testList, testContact)) {}
			else {
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
			if (containsContact(testList, testContact)) {}
			else {
				result = false;
			}
		}
		assertTrue(result);
	}
	
	@Test
	public void testsgetContactsID3() {
		Set<Contact> testList = new HashSet<Contact>();
		Contact tom = new ContactImpl(1, "Tom", "Good");
		testList.add(tom);
		Contact tim = new ContactImpl(2, "Tim", "Bad");
		testList.add(tim);
		
		addTestContacts();
		Set<Contact> cmContactList = cm.getContacts(1, 2);
		
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
	
	@Test(expected=IllegalArgumentException.class)
	public void testsgetContactsNoID() {
		addTestContacts();
		int[] ident = new int[0];
		Set<Contact> cmContactList = cm.getContacts(ident);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testsgetContactsContactNonexistent() {
		addTestContacts();
		Set<Contact> cmContactList = cm.getContacts(5032);
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
	
	private boolean equalsContactList(Set<Contact> list1, Set<Contact> list2) {
		boolean result = true;
		if (list1.isEmpty() || list2.isEmpty()) {
			result = false;
			return result;
		} else {
			//check all contacts in list1 are in list2
			for ( Contact con : list1) {
				if (!containsContact(list2, con)) {
					result = false;
				}
			}
			//and check all contacts in list2 are in list1
			for ( Contact con : list2) {
				if (!containsContact(list1, con)) {
					result = false;
				}
			}
			
		}
		return result;
	}
	
	
}