import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class ContactManagerImpl implements ContactManager {
	
	Set<Contact> contactIdList;
	Set <Meeting> meetingIdList;
	//pastMeetingIdList?
	//futureMeetingIdList?
	
	public ContactManagerImpl() {
		contactIdList = new HashSet<Contact>();
		meetingIdList = new HashSet<Meeting>();
	}
	
	public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
		return 0;
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
		//do nothing
	}

    public void addMeetingNotes(int id, String text) {
    	//do nothing
    }

    public void addNewContact(String name, String notes) {
    	//do nothing
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
	
}