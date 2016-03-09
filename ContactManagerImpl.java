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
		int meetingID = getMeetingID();
		fm = new FutureMeetingImpl(meetingID, date, contacts);
		meetingIdList.add(fm);
		//meetingCount++;
		return (meetingID);
	}
	
	public PastMeeting getPastMeeting(int id) {
		return null;
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
		//IllegalArgumentException if contacts empty
		//NullPointerException if any arguments are null
		CMExceptions.checkContacts(contacts, contactIdList);
		int meetingID = getMeetingID();
		pm = new PastMeetingImpl(meetingID, date, contacts, text);
		meetingIdList.add(pm);
		//meetingCount++;
		//needs to throw an exception if a contact isn't in the contact list
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
	
}