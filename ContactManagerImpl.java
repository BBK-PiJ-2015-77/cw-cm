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
    	return null;
    }

    public Set<Contact> getContacts(String name) {
    	return null;
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
	
}