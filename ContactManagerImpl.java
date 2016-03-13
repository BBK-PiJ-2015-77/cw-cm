import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

public class ContactManagerImpl implements ContactManager {
	
	//private int meetingCount = 1;
	private Set<Contact> contactIdList;
	private Set <Meeting> meetingIdList;
	//pastMeetingIdList?
	//futureMeetingIdList?
	//private Calendar currentDate = Calendar.getInstance();
	FutureMeetingImpl fm;
	PastMeetingImpl pm;
	
	public ContactManagerImpl() {
		contactIdList = new HashSet<Contact>();
		meetingIdList = new HashSet<Meeting>();
	}
	
	public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
		CMExceptions.checkIfDateInPast(date);
		
		for (Contact con : contacts) {
			if (!containsContact(contactIdList,con)) {
				throw new IllegalArgumentException("An invalid contact has been entered");
			}
		}
		
		int meetingID = getMeetingID();
		fm = new FutureMeetingImpl(meetingID, date, contacts);
		meetingIdList.add(fm);
		return (meetingID);
	}
	
	public PastMeeting getPastMeeting(int id) {
		//Have to explicitly return null, otherwise, when casting
		//the Meeting m as a PastMeeting, it will throw a NullPointerException
		if (id > (meetingIdList.size() + 1) || id <= 0) {
			return null;
		}
		Meeting m = getMeeting(id);
		Calendar currentDate = Calendar.getInstance();
		CMExceptions.checkIfDateInFuture(m.getDate());
		return (PastMeeting) m;
	}
	
	public FutureMeeting getFutureMeeting(int id) {
		Meeting m = getMeeting(id);
		Calendar currentDate = Calendar.getInstance();
		CMExceptions.checkIfDateInPast(m.getDate());
		return (FutureMeeting) m;
	}
	
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
	
	public List<Meeting> getFutureMeetingList(Contact contact) {
		CMExceptions.setNonNullObject(contact);
		if (!containsContact(contactIdList, contact)) {
			throw new IllegalArgumentException("An invalid contact has been entered");
		}
		
		List<Meeting> fml = new ArrayList<Meeting>();
		
		//get an unsorted list of future meetings
		for (Meeting m : meetingIdList) {
			if (m instanceof FutureMeeting) {
				fml.add(m);
			}
		}
		
		//remove duplicates
		if (checkDuplicateMeetings(fml)) {
			fml = removeDuplicateMeetings(fml);
		}
		
		//order chronologically
		Collections.sort(fml, new CompareDates());
		
		//check if the contact is contained in any of the meetings,
		for (Meeting m : fml) {
			if (!containsContact(m.getContacts(),contact)) {
				fml.remove(m);
			}
		}
		
		return fml;
	}
	
	public List<Meeting> getFutureMeetingList(Calendar date) {
		return null;
	}
	
	public List<PastMeeting> getPastMeetingList(Contact contact) {
		return null;
	}
	
	public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {
		//Already throws IllegalArgumentException if contacts is empty
		//using PastMeetingImpl
		
		//Throw IllegalArgumentException if any contacts don't exist
		for (Contact con : contacts) {
			if (!containsContact(contactIdList,con)) {
				throw new IllegalArgumentException("An invalid contact has been entered");
			}
		}

		int meetingID = getMeetingID();
		pm = new PastMeetingImpl(meetingID, date, contacts, text);
		meetingIdList.add(pm);
	}

    public PastMeeting addMeetingNotes(int id, String text) {
    	return null;
    }

    public int addNewContact(String name, String notes) {
    	int id = getContactID();
    	Contact newContact = new ContactImpl(id, name, notes);
    	contactIdList.add(newContact);
    	return id;
    	//create unique id
    	//create a new contact
    	//add it to list
    	//return id
    }

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
    	//check if that each element exists in the current contact list
    	//if it is, add it to a new list
    	//return the list
    	
    	
    }
    
    public void flush() {
    	//do nothing
    }
    
    
    
    private int getMeetingID() {
    	int meetingCount = meetingIdList.size();
    	return (meetingCount + 1);
    }
    
    private int getContactID() {
    	int id = contactIdList.size();
    	return (id + 1);
    }
    
    private boolean checkContact(int i) {
    	boolean result = false;
    	for ( Contact con : contactIdList) {
    		if(i == con.getId()) {
    			result = true;
    		}
    	}
    	return result;
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
	
	private List<Meeting> removeDuplicateMeetings(List<Meeting> meetings) {
		List<Meeting> uniqueList = meetings;
		
		boolean result = false;
		for (int i = 0; i<meetings.size(); i++) {
			for (int j = (i+1); j<meetings.size(); j++) {
				if (equalsMeeting(meetings.get(i),meetings.get(j))) {
					uniqueList.remove(meetings.get(j));
				}
			}
		}
		return uniqueList;
	}
	
	
	private boolean equalsMeeting(Meeting m1, Meeting m2) {
		boolean result = false;
		if (m1.getId() == m2.getId() &&
			m1.getDate().equals(m2.getDate()) && 
			equalContactList(m1.getContacts(), m2.getContacts())) {
				result = true;
				return result;
		} else if  (m1.getDate().equals(m2.getDate()) && 
					equalContactList(m1.getContacts(), m2.getContacts())) {
						result = true;
						return result;
		} else {
			return result;
		}
	}
	
}