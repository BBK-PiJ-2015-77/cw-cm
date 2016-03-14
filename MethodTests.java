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

public class MethodTests {
	
	Set<Contact> contacts1;
	Set<Contact> contacts2;
	Contact con1;
	Contact con2;
	Contact con3;
	Calendar pastDate;
	Calendar currentDate;
	Calendar futureDate;
	
	@Before
	public void setUp() {
		contacts1 = new HashSet<Contact>();
		contacts2 = new HashSet<Contact>();
		con1 = new ContactImpl(1, "Tom", "Good");
		con2 = new ContactImpl(2, "Tim", "Bad");
		con3 = new ContactImpl(3, "Sarah", "Notes3");
		
		pastDate = Calendar.getInstance();
		pastDate.set(2015,12,3);
		currentDate = Calendar.getInstance();
		futureDate = Calendar.getInstance();
		futureDate.add(Calendar.DAY_OF_YEAR, +1);
	}
	
	/////////equalsContactList////////////
	
	@Test
	public void testsequalsContactList() {
		contacts1.add(con1);
		contacts1.add(con2);
		contacts1.add(con3);
		
		contacts2.add(con1);
		contacts2.add(con2);
		contacts2.add(con3);
		
		assertTrue(Methods.equalsContactList(contacts1,contacts2));
	}
	
	@Test
	public void testsequalsEmptyContactList() {
		assertFalse(Methods.equalsContactList(contacts1,contacts2));
	}
	
	@Test
	public void testsequalsNonEqualContactList() {
		contacts1.add(con1);
		contacts1.add(con2);
		contacts1.add(con3);
		
		Contact conA = new ContactImpl(1, "Mary", "Good");
		Contact conB = new ContactImpl(2, "Jane", "Bad");
		Contact conC = new ContactImpl(3, "Steve", "Notes3");
		contacts2.add(conA);
		contacts2.add(conB);
		contacts2.add(conC);
		
		assertFalse(Methods.equalsContactList(contacts1,contacts2));
	}
	
	@Test
	public void testsequalsContactListWrongNotes() {
		contacts1.add(con1);
		contacts1.add(con2);
		contacts1.add(con3);
		
		Contact conA = new ContactImpl(1, "Tom", "Wrong notes");
		contacts2.add(conA);
		contacts2.add(con2);
		contacts2.add(con3);
		
		assertFalse(Methods.equalsContactList(contacts1,contacts2));
	}
	
	@Test
	public void testsequalsContactListWrongName() {
		contacts1.add(con1);
		contacts1.add(con2);
		contacts1.add(con3);
		
		Contact conA = new ContactImpl(1, "Tim", "Good");
		contacts2.add(conA);
		contacts2.add(con2);
		contacts2.add(con3);
		
		assertFalse(Methods.equalsContactList(contacts1,contacts2));
	}
	
	@Test
	public void testsequalsContactListWrongID() {
		contacts1.add(con1);
		contacts1.add(con2);
		contacts1.add(con3);
		
		Contact conA = new ContactImpl(2, "Tom", "Good");
		contacts2.add(conA);
		contacts2.add(con2);
		contacts2.add(con3);
		
		assertFalse(Methods.equalsContactList(contacts1,contacts2));
	}
	
	@Test
	public void testscontainsContact() {
		contacts1.add(con1);
		contacts1.add(con2);
		contacts1.add(con3);
		
		contacts2.add(con1);
		contacts2.add(con2);
		contacts2.add(con3);
		
		assertTrue(Methods.containsContact(contacts1, con1));
		assertTrue(Methods.containsContact(contacts1, con2));
		assertTrue(Methods.containsContact(contacts1, con3));
		assertTrue(Methods.containsContact(contacts2, con1));
		assertTrue(Methods.containsContact(contacts2, con2));
		assertTrue(Methods.containsContact(contacts2, con3));
	}
	
	@Test
	public void testsequalsMeeting() {
		contacts1.add(con1);
		contacts1.add(con2);
		contacts1.add(con3);
		FutureMeeting fm1 = new FutureMeetingImpl(1, futureDate, contacts1);
		FutureMeeting fm2 = new FutureMeetingImpl(2, futureDate, contacts1);
		assertTrue(Methods.equalsMeeting(fm1,fm2));
	}
	
	@Test
	public void testsequalsMeeting2() {
		contacts1.add(con1);
		contacts1.add(con2);
		contacts1.add(con3);
		FutureMeeting fm1 = new FutureMeetingImpl(1, futureDate, contacts1);
		FutureMeeting fm2 = new FutureMeetingImpl(1, futureDate, contacts1);
		assertTrue(Methods.equalsMeeting(fm1,fm2));
	}
	
	@Test
	public void testsequalsMeeting3() {
		contacts1.add(con1);
		contacts1.add(con2);
		contacts1.add(con3);
		FutureMeeting fm1 = new FutureMeetingImpl(1, futureDate, contacts1);
		Calendar futureDate2 = Calendar.getInstance();
		futureDate2.set(2046,12,3);
		FutureMeeting fm2 = new FutureMeetingImpl(1, futureDate2, contacts1);
		assertEquals(false,Methods.equalsMeeting(fm1,fm2));
	}
	
	@Test
	public void testsequalsMeeting4() {
		contacts1.add(con1);
		contacts1.add(con2);
		contacts1.add(con3);
		contacts2.add(con1);
		FutureMeeting fm1 = new FutureMeetingImpl(1, futureDate, contacts1);
		FutureMeeting fm2 = new FutureMeetingImpl(1, futureDate, contacts2);
		assertFalse(Methods.equalsMeeting(fm1,fm2));
	}
	
	@Test
	public void testscheckDuplicateMeetings() {
		List<Meeting> meetingList = new ArrayList<Meeting>();
		contacts1.add(con1);
		contacts1.add(con2);
		contacts1.add(con3);
		contacts2.add(con1);
		FutureMeeting fm1 = new FutureMeetingImpl(1, futureDate, contacts1);
		meetingList.add(fm1);
		Calendar futureDate2 = Calendar.getInstance();
		futureDate2.set(2046,12,3);
		FutureMeeting fm2 = new FutureMeetingImpl(1, futureDate2, contacts1);
		meetingList.add(fm2);
		assertFalse(Methods.checkDuplicateMeetings(meetingList));
	}
	
	@Test
	public void testscheckDuplicateMeetings2() {
		List<Meeting> meetingList = new ArrayList<Meeting>();
		contacts1.add(con1);
		contacts1.add(con2);
		contacts1.add(con3);
		contacts2.add(con1);
		FutureMeeting fm1 = new FutureMeetingImpl(1, futureDate, contacts1);
		meetingList.add(fm1);
		Calendar futureDate2 = Calendar.getInstance();
		futureDate2.set(2046,12,3);
		FutureMeeting fm2 = new FutureMeetingImpl(1, futureDate2, contacts1);
		meetingList.add(fm2);
		FutureMeeting fm3 = new FutureMeetingImpl(1, futureDate, contacts1);
		meetingList.add(fm3);
		assertTrue(Methods.checkDuplicateMeetings(meetingList));
	}
	
	@Test
	public void testscheckRemoveDuplicate() {
		List<Meeting> meetingList = new ArrayList<Meeting>();
		contacts1.add(con1);
		contacts1.add(con2);
		contacts1.add(con3);
		contacts2.add(con1);
		FutureMeeting fm1 = new FutureMeetingImpl(1, futureDate, contacts1);
		meetingList.add(fm1);
		Calendar futureDate2 = Calendar.getInstance();
		futureDate2.set(2046,12,3);
		FutureMeeting fm2 = new FutureMeetingImpl(1, futureDate2, contacts1);
		meetingList.add(fm2);
		FutureMeeting fm3 = new FutureMeetingImpl(1, futureDate, contacts1);
		meetingList.add(fm3);
		List<Meeting> meetingList2 = Methods.removeDuplicateMeetings(meetingList);
		assertFalse(Methods.checkDuplicateMeetings(meetingList2));
	}

}