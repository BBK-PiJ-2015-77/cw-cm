import java.util.Calendar;
import java.util.Set;

/**
 * An implementation of FutureMeeting by extending MeetingImpl.
 * 
 * Holds and returns the id, date and list of contacts of the meeting.
 * 
 * This class does not ensure that ID's are unique, and therefore has a dependency on
 * another class to ensure that IDs provided to this are unique.
 * 
 * ContactManagerImpl ensures IDs provided to this are unique.
 */
 
public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting {
	
	public FutureMeetingImpl(int id, Calendar date, Set<Contact> contacts) {
		super(id, date, contacts);
	}
	
}