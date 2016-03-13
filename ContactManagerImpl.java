import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

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
		//meetingCount++;
		return (meetingID);
	}
	
	public PastMeeting getPastMeeting(int id) {
		Calendar currentDate = Calendar.getInstance();
		Meeting m = null;
		
		if (id > (meetingIdList.size() + 1) || id <= 0) {
			return null;
		}
		
		for (Meeting meet : meetingIdList) {
			if (meet.getId() == id) {
				m = meet;
			}
		}

		CMExceptions.checkIfDateInFuture(m.getDate());
		return (PastMeeting) m;
	}
	
	public FutureMeeting getFutureMeeting(int id) {
		return null;
	}
	
	public Meeting getMeeting(int id) {
		return null;
	}
	
	public List<Meeting> getFutureMeetingList(Contact contact) {
		return null;
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
	
}