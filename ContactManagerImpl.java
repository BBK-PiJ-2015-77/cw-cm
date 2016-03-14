import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

public class ContactManagerImpl implements ContactManager {
	
	private Set<Contact> contactIdList;
	private Set <Meeting> meetingIdList;
	
	public ContactManagerImpl() {
		contactIdList = new HashSet<Contact>();
		meetingIdList = new HashSet<Meeting>();
	}
	
	/**
	 * @see ContactManager
	 */
	@Override
	public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
		CMExceptions.checkIfDateInPast(date);
		
		for (Contact con : contacts) {
			if (!containsContact(contactIdList,con)) {
				throw new IllegalArgumentException("An invalid contact has been entered");
			}
		}
		
		int meetingID = getMeetingID();
		FutureMeeting fm = new FutureMeetingImpl(meetingID, date, contacts);
		meetingIdList.add(fm);
		return (meetingID);
	}
	
	/**
	 * @see ContactManager
	 */
	@Override
	public PastMeeting getPastMeeting(int id) {
		if (id > (meetingIdList.size() + 1) || id <= 0) {
			return null;
		}
		Meeting m = getMeeting(id);
		Calendar currentDate = Calendar.getInstance();
		if (m.getDate().after(currentDate)) {
			throw new IllegalStateException("The date can not be set in the future");
		}
		return (PastMeeting) m;
	}
	
	/**
	 * @see ContactManager
	 */
	@Override
	public FutureMeeting getFutureMeeting(int id) {
		Meeting m = getMeeting(id);
		Calendar currentDate = Calendar.getInstance();
		CMExceptions.checkIfDateInPast(m.getDate());
		return (FutureMeeting) m;
	}
	
	/**
	 * @see ContactManager
	 */
	@Override
	public Meeting getMeeting(int id) {
		Meeting m = null;
		if (id > (meetingIdList.size() + 1) || id <= 0) {
			return m;
		}
		
		for (Meeting meet : meetingIdList) {
			if (meet.getId() == id) {
				m = meet;
			}
		}
		return m;
	}
	
	/**
	 * @see ContactManager
	 */
	@Override
	public List<Meeting> getFutureMeetingList(Contact contact) {
		CMExceptions.setNonNullObject(contact);
		if (!containsContact(contactIdList, contact)) {
			throw new IllegalArgumentException("An invalid contact has been entered");
		}
		List<Meeting> fml = new ArrayList<Meeting>();
		for (Meeting m : meetingIdList) {
			if (m instanceof FutureMeeting) {
				fml.add(m);
			}
		}
		if (checkDuplicateMeetings(fml)) {
			fml = removeDuplicateMeetings(fml);
		}
		Collections.sort(fml, new CompareDates());
		for (Meeting m : fml) {
			if (!containsContact(m.getContacts(),contact)) {
				fml.remove(m);
			}
		}
		return fml;
	}
	
	/**
	 * @see ContactManager
	 */
	@Override
	public List<Meeting> getMeetingListOn(Calendar date) {
		CMExceptions.setNonNullObject(date);
		List<Meeting> mlo = new ArrayList<Meeting>();
		for (Meeting m : meetingIdList) {
			if (m.getDate().get(Calendar.YEAR) == date.get(Calendar.YEAR) &&
				m.getDate().get(Calendar.MONTH) == date.get(Calendar.MONTH) &&
				m.getDate().get(Calendar.DATE) == date.get(Calendar.DATE)) {
					mlo.add(m);
			}
		}
		if (mlo.isEmpty()) {
			return mlo;
		}
		mlo = removeDuplicateMeetings(mlo);
		Collections.sort(mlo, new CompareDates());
		return mlo;
	}
	
	/**
	 * @see ContactManager
	 */
	@Override
	public List<PastMeeting> getPastMeetingListFor(Contact contact) {
		CMExceptions.setNonNullObject(contact);
		if (!containsContact(contactIdList, contact)) {
			throw new IllegalArgumentException("An invalid contact has been entered");
		}
		
		List<PastMeeting> pml = new ArrayList<PastMeeting>();
		for (Meeting m : meetingIdList) {
			if (m instanceof PastMeeting) {
				pml.add((PastMeeting) m); 
			}
		}
		
		/**
		//remove duplicates
		if (checkDuplicateMeetings(pml)) {
			pml = removeDuplicateMeetings(pml);
		}
		*/
		
		Collections.sort(pml, new CompareDates());
		for (PastMeeting m : pml) {
			if (!containsContact(m.getContacts(),contact)) {
				pml.remove(m);
			}
		}
		return pml;
	}
	
	/**
	 * @see ContactManager
	 */
	@Override
	public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {
		for (Contact con : contacts) {
			if (!containsContact(contactIdList,con)) {
				throw new IllegalArgumentException("An invalid contact has been entered");
			}
		}
		int meetingID = getMeetingID();
		PastMeeting pm = new PastMeetingImpl(meetingID, date, contacts, text);
		meetingIdList.add(pm);
	}
	
	/**
	 * @see ContactManager
	 */
    @Override
    public PastMeeting addMeetingNotes(int id, String text) {
    	if (!checkMeeting(id)) {
    		throw new IllegalArgumentException("An invalid Meeting ID has been entered");
    	}
    	if (text.equals(null)) {
    		throw new NullPointerException("Null meeting notes have been entered");
    	}
    	Calendar currentDate = Calendar.getInstance();
    	int pmID = 0;
    	Calendar pmDate = null;
    	Set <Contact> pmContacts = null;
    	String pmNotes = null;
    	StringBuilder sb = new StringBuilder();
    	PastMeeting newPastmeeting = null;
    	Meeting meetingToRemove = null;
    	for (Meeting m : meetingIdList) {
    		if(m.getId() == id) {
    			pmID = m.getId();
    			pmDate = m.getDate();
    			pmContacts = m.getContacts();
    			meetingToRemove = m;
    		}
    	}
    	if (meetingToRemove instanceof PastMeeting) {
    		PastMeeting mtr = (PastMeeting) meetingToRemove;
    		sb.append(mtr.getNotes());
    	}
    	sb.append(text);
    	pmNotes = sb.toString();
    	meetingIdList.remove(meetingToRemove);
    	
    	if(pmDate.after(currentDate)) {
    		throw new IllegalStateException("The meeting date must be in the past");
    	} else {
    		newPastmeeting = new PastMeetingImpl(pmID,pmDate,pmContacts,pmNotes);
    	}
    	
    	return newPastmeeting;
    }
	
	/**
	 * @see ContactManager
	 */
    @Override
    public int addNewContact(String name, String notes) {
    	int id = getContactID();
    	Contact newContact = new ContactImpl(id, name, notes);
    	contactIdList.add(newContact);
    	return id;
    }
	
	/**
	 * @see ContactManager
	 */
    @Override
    public Set<Contact> getContacts(int... ids) {
    	if (ids.length == 0) {
    		throw new IllegalArgumentException("An ID must be entered as an argument");
    	} else {
    		Set<Contact> newContactList = new HashSet<Contact>();
    		for (int i : ids) {
    			if (!checkContact(i)) {
    				throw new IllegalArgumentException("A valid ID must be entered as an argument");
    			} else {
    				for ( Contact con : contactIdList) {
    					if (con.getId() == i) {
    						newContactList.add(con);
    					}
    				}
    			}
    		}
    		return newContactList;
    	}
    }
	
	/**
	 * @see ContactManager
	 */
    @Override
    public Set<Contact> getContacts(String name) {
    	Set<Contact> newContactList = new HashSet<Contact>();
    	CMExceptions.setNonNullObject(name);
    	if (name == "") {
    		return contactIdList;
    	} else {
    		for ( Contact con : contactIdList) {
    			if (con.getName().equals(name)) {
    				newContactList.add(con);
    			}
    		}
    		return newContactList;
    	}
    }
    
    /**
	 * @see ContactManager
	 */
    @Override
    public void flush() {
    	//do nothing
    }
    
    /**
	 * Creates a unique Meeting ID
	 *
	 * @return a unique int value
	 */
    private int getMeetingID() {
    	int meetingCount = meetingIdList.size();
    	return (meetingCount + 1);
    }
    
    /**
	 * Creates a unique Contact ID
	 *
	 * @return a unique int value
	 */
    private int getContactID() {
    	int id = contactIdList.size();
    	return (id + 1);
    }
    
    /**
	 * Checks whether a Contact exists in the contactIdList
	 *
	 * @param i the Contact ID to check
	 * @return whether the Contact exists (true) or not (false)
	 */
    private boolean checkContact(int i) {
    	boolean result = false;
    	for ( Contact con : contactIdList) {
    		if(i == con.getId()) {
    			result = true;
    		}
    	}
    	return result;
    }
    
    /**
	 * Checks whether a Meeting exists in the meetingIdList
	 *
	 * @param i the Meeting ID to check
	 * @return whether the Meeting exists (true) or not (false)
	 */
    private boolean checkMeeting(int i) {
    	boolean result = false;
    	for ( Meeting m : meetingIdList) {
    		if(i == m.getId()) {
    			result = true;
    		}
    	}
    	return result;
    }
    
    /**
	 * Compares whether two Contact objects are the same
	 *
	 * @param con1 the first Contact to compare
	 * @param con2 the second Contact to compare
	 * @return whether these two are the same (true) or not (false)
	 */
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
	
	/**
	 * Checks a Set<Contact> to see if it contains a specified Contact
	 *
	 * @param list the Set<Contact> to check
	 * @param con the Contact to check for
	 * @return whether con is in the list (true) or not (false)
	 */
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
	
	/**
	 * Compares whether two Set<Contact> are the same
	 *
	 * @param list1 the first Set<Contact> to compare
	 * @param list2 the second Set<Contact> to compare
	 * @return whether these two are the same (true) or not (false)
	 */
	private boolean equalContactList(Set<Contact> list1, Set<Contact> list2) {
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
	
	/**
	 * Checks whether a List<Meeting> contains duplicate Meetings
	 *
	 * @param meetings the list of Meetings to compare
	 * @return if the list contains a duplicate (true) or not (false)
	 */
	private boolean checkDuplicateMeetings(List<Meeting> meetings) {
		boolean result = false;
		for (int i = 0; i<meetings.size(); i++) {
			for (int j = (i+1); j<meetings.size(); j++) {
				if (equalsMeeting(meetings.get(i),meetings.get(j))) {
					result = true;
				}
			}
		}
		return result;
	}
	
	/**
	 * Removes any duplicate Meetings from a List<Meeting> and returns
	 * a List<Meeting> of unique Meetings
	 *
	 * @param meetings the list of Meetings to check
	 * @return a List<Meeting> of unique Meetings, having had any duplicates removed
	 */
	private List<Meeting> removeDuplicateMeetings(List<Meeting> meetings) {
		List<Meeting> uniqueList = meetings;
		
		boolean result = false;
		for (int i = 0; i<meetings.size(); i++) {
			for (int j = (i+1); j<meetings.size(); j++) {
				if (equalsMeeting(meetings.get(i),meetings.get(j))) {
					uniqueList.remove(meetings.get(j));
					j--;
				}
			}
		}
		return uniqueList;
	}
	
	/**
	 * Compares whether two Meeting objects are the same
	 *
	 * @param m1 the first Meeting to compare
	 * @param m2 the second Meeting to compare
	 * @return whether these two are the same (true) or not (false)
	 */
	private boolean equalsMeeting(Meeting m1, Meeting m2) {
		boolean result = false;
		if (m1.getId() == m2.getId() &&
			equalsDate(m1.getDate(),m2.getDate()) && 
			equalContactList(m1.getContacts(), m2.getContacts())) {
				result = true;
				return result;
		} else if  (equalsDate(m1.getDate(),m2.getDate()) && 
					equalContactList(m1.getContacts(), m2.getContacts())) {
						result = true;
						return result;
		} else {
			return result;
		}
	}
	
	/**
	 * Compares whether two Calendar objects are the same date
	 *
	 * @param date1 the first date to compare
	 * @param date2 the second date to compare
	 * @return whether these two are the same date (true) or not (false)
	 */
	private boolean equalsDate(Calendar date1, Calendar date2) {
		boolean result = false;
		if (date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR) &&
			date1.get(Calendar.MONTH) == date2.get(Calendar.MONTH) &&
			date1.get(Calendar.DATE) == date2.get(Calendar.DATE) &&
			date1.get(Calendar.HOUR) == date2.get(Calendar.HOUR) &&
			date1.get(Calendar.MINUTE) == date2.get(Calendar.MINUTE)) {
				result = true;
		}
		return result;
	}
	
}