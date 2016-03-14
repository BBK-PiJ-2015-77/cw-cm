import org.junit.Before;
import org.junit.Test;
import java.util.Calendar;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class ContactManagerImplTest {
	
	Calendar pastDate;
	Calendar pastDate2;
	Calendar currentDate;
	Calendar futureDate;
	Calendar futureDate2;
	Set<Contact> contacts;
	ContactManagerImpl cm;
	String text;
	Contact con1;
	Contact con2;
	
	@Before
	public void setUp() {
		pastDate = Calendar.getInstance();
		pastDate.set(2015,12,3);
		pastDate2 = Calendar.getInstance();
		pastDate2.set(2014,12,3);
		
		currentDate = Calendar.getInstance();
		
		futureDate = Calendar.getInstance();
		futureDate.add(Calendar.DAY_OF_YEAR, +1);
		futureDate2 = Calendar.getInstance();
		futureDate2.set(2046,12,3);
		
		contacts = new HashSet<Contact>();
		con1 = new ContactImpl(1, "Tom", "Good");
		con2 = new ContactImpl(2, "Tim", "Bad");
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
	
	
	@Test(expected=IllegalStateException.class)
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
		cm.addFutureMeeting(contacts, futureDate);
		FutureMeeting fm = cm.getFutureMeeting(1);
		assertEquals(futureDate, fm.getDate());
		
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
	
	@Test
	public void testsgetFutureMeetingListChronological() {
		addTestContacts();
		addTestMeetings();
		List<Meeting> fml = cm.getFutureMeetingList(con1);
		assertTrue(fml.get(0).getDate().before(fml.get(1).getDate()));
	}
	
	@Test
	public void testsgetFutureMeetingList() {
		addTestContacts();
		addTestMeetings();
		List<Meeting> fml = cm.getFutureMeetingList(con1);
		assertEquals(fml.get(0).getDate(),futureDate);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testsgetFutureMeetingListBadContact() {
		addTestContacts();
		addTestMeetings();
		Contact con3 = new ContactImpl(1, "Jane", "notes1");
		cm.getFutureMeetingList(con3);
	}
	
	@Test(expected=NullPointerException.class)
	public void testsgetFutureMeetingListNullContact() {
		addTestContacts();
		addTestMeetings();
		Contact con3 = null;
		cm.getFutureMeetingList(con3);
	}
	
	@Test
	public void testsgetFutureMeetingListNoDuplicates() {
		addTestContacts();
		for (int i = 0; i<100; i++) {
			cm.addFutureMeeting(contacts, futureDate);
		}
		List<Meeting> fml = cm.getFutureMeetingList(con1);
		assertTrue(uniqueIds(fml));
	}
	
	@Test
	public void testsgetFutureMeetingListNoMeeting() {
		addTestContacts();
		Contact con3 = new ContactImpl(3, "Jane", "fml no meeting");
		cm.addNewContact("Jane", "fml no meeting");
		List<Meeting> fml = cm.getFutureMeetingList(con3);
		assertTrue(fml.isEmpty());
	}
	
	
	/////////getMeetingListOn////////////
	
	@Test
	public void testsgetMeetingListOn() {
		addTestContacts();
		addTestMeetings();
		List<Meeting> mlo = cm.getMeetingListOn(futureDate);
		assertTrue(mlo.get(0).getId() == 1);
	}
	
	@Test
	public void testsgetMeetingListOn2() {
		addTestContacts();
		addTestMeetings();
		List<Meeting> mlo = cm.getMeetingListOn(futureDate);
		assertTrue(equalsContactList(mlo.get(0).getContacts(),contacts));
	}
	
	@Test
	public void testsgetMeetingList2Meetings1Date() {
		addTestContacts();
		addTestMeetings();
		Set<Contact> contacts2 = new HashSet<Contact>();
		contacts2.add(con2);
		cm.addFutureMeeting(contacts2, futureDate2);
		List<Meeting> mlo = cm.getMeetingListOn(futureDate2);
		assertTrue(mlo.size() == 2);
	}
	
	@Test
	public void testsgetMeetingListOnNoMeetings() {
		addTestContacts();
		addTestMeetings();
		Calendar futureDate3 = Calendar.getInstance();
		futureDate3.set(2020,12,3);
		List<Meeting> mlo = cm.getMeetingListOn(futureDate3);
		assertTrue(mlo.isEmpty());
	}
	
	@Test(expected=NullPointerException.class)
	public void testsgetMeetingListOnNullDate() {
		addTestContacts();
		addTestMeetings();
		Calendar nullDate = null;
		cm.getMeetingListOn(nullDate);
	}
	
	@Test
	public void testsgetMeetingListOnChronological() {
		addTestContacts();
		Calendar date1 = Calendar.getInstance();
		Calendar date2 = Calendar.getInstance();
		date1.set(2020,12,3,8,40);
		date2.set(2020,12,3,10,40);
		cm.addFutureMeeting(contacts, date2);
		cm.addFutureMeeting(contacts, date1);
		Calendar checkDate = Calendar.getInstance();
		checkDate.set(2020,12,3);
		List<Meeting> mlo = cm.getMeetingListOn(checkDate);
		assertTrue(mlo.get(0).getDate().before(mlo.get(1).getDate()));
	}
	
	//need to re-do so that remove duplicates isn't necessary, only that id's are unique
	@Test
	public void testsgetMeetingListOnNoDuplicates() {
		addTestContacts();
		for (int i = 0; i<100; i++) {
			cm.addFutureMeeting(contacts, futureDate);
		}
		List<Meeting> mlo = cm.getMeetingListOn(futureDate);
		assertTrue(uniqueIds(mlo));
	}
	
	/////////getPastMeetingListFor////////////
	
	@Test
	public void testsgetPastMeetingFor() {
		addTestContacts();
		addTestMeetings();
		List<PastMeeting> pml = cm.getPastMeetingListFor(con1);
		assertTrue(pml.get(0).getId() == 4);
	}

	
	@Test(expected=IllegalArgumentException.class)
	public void testsgetPastMeetingListForBadContact() {
		addTestContacts();
		addTestMeetings();
		Contact con3 = new ContactImpl(1, "Jane", "notes1");
		cm.getPastMeetingListFor(con3);
	}
	
	
	@Test(expected=NullPointerException.class)
	public void testsgetPastMeetingListForNullContact() {
		addTestContacts();
		addTestMeetings();
		Contact con3 = null;
		cm.getFutureMeetingList(con3);
	}
	
	@Test
	public void testsgetPastMeetingListForNoDuplicates() {
		addTestContacts();
		for (int i = 0; i<100; i++) {
			cm.addNewPastMeeting(contacts, futureDate, text);
		}
		List<PastMeeting> pml = cm.getPastMeetingListFor(con1);
		assertTrue(uniqueIdsPastMeeting(pml));
	}
	
	@Test
	public void testsgetPastMeetingListForNoMeeting() {
		addTestContacts();
		Contact con3 = new ContactImpl(3, "Jane", "fml no meeting");
		cm.addNewContact("Jane", "fml no meeting");
		List<PastMeeting> pml = cm.getPastMeetingListFor(con3);
		assertTrue(pml.isEmpty());
	}
	
	@Test
	public void testsgetPastMeetingListForChronological() {
		addTestContacts();
		addTestMeetings();
		List<PastMeeting> pml = cm.getPastMeetingListFor(con1);
		assertTrue(pml.get(0).getDate().before(pml.get(1).getDate()));
	}
	
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
	
	@Test
	public void testsaddMeetingNotesFutureMeeting() {
		addTestContacts();
		addTestMeetings();
		//Meeting 1 & 3 are future meetings
		//need to change the date of the FutureMeeting so that it is now in the past
		futureDate2.set(2015,12,25);
		text = "Testing addMeetingNotes()";
		PastMeeting pm = cm.addMeetingNotes(3,text);
		assertEquals(text, pm.getNotes());
	}
	
	@Test
	public void testsaddMeetingNotesPastMeeting() {
		addTestContacts();
		addTestMeetings();
		//Meeting 2 & 4 are future meetings
		text = "Testing addMeetingNotes()";
		String expectedText = "Meeting notesTesting addMeetingNotes()";
		PastMeeting pm = cm.addMeetingNotes(2,text);
		assertEquals(expectedText, pm.getNotes());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testsaddMeetingNotesNoMeeting() {
		//IllegalArgumentException if meeting doesn't exist
		addTestContacts();
		cm.addMeetingNotes(1,text);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testsaddMeetingNotesNoMeeting2() {
		//IllegalArgumentException if meeting doesn't exist
		addTestContacts();
		addTestMeetings();
		cm.addMeetingNotes(100,text);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testsaddMeetingNotesFutureMeeting2() {
		//IllegalStateException if meeting is in future
		addTestContacts();
		addTestMeetings();
		cm.addMeetingNotes(3,text);
	}
	
	@Test(expected=NullPointerException.class)
	public void testsaddMeetingNotesNullNotes() {
		//IllegalStateException if meeting is in future
		addTestContacts();
		addTestMeetings();
		text = null;
		cm.addMeetingNotes(2,text);
	}
	
	
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
	
	private void addTestMeetings() {
		//adds 2 future meetings and 2 past meetings
		cm.addFutureMeeting(contacts, futureDate);
		cm.addNewPastMeeting(contacts, pastDate, text);
		cm.addFutureMeeting(contacts, futureDate2);
		cm.addNewPastMeeting(contacts, pastDate2, text);
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
	
	private boolean uniqueIds(List<Meeting> list) {
		boolean result = true;
		for (int i = 0; i<list.size(); i++) {
			for (int j = (i+1); j<list.size(); j++) {
				if (list.get(i).getId() == list.get(j).getId()) {
					result = false;
				}
			}
		}
		return result;
	}
	
	private boolean uniqueIdsPastMeeting(List<PastMeeting> list) {
		boolean result = true;
		for (int i = 0; i<list.size(); i++) {
			for (int j = (i+1); j<list.size(); j++) {
				if (list.get(i).getId() == list.get(j).getId()) {
					result = false;
				}
			}
		}
		return result;
	}
	
	
}