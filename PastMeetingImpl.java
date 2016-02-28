import java.util.Calendar;
import java.util.Set;

/**
 * An implementation of PastMeeting by extending MeetingImpl.
 * 
 * Holds and returns the id, date, list of attendees and notes of the meeting.
 * 
 * This class does not ensure that ID's are unique, and therefore has a dependency on
 * another class to ensure that IDs provided to this are unique.
 * 
 * ContactManagerImpl ensures IDs provided to this are unique.
 */

public class PastMeetingImpl extends MeetingImpl implements PastMeeting {
	
	StringBuilder sb = new StringBuilder();
	
	/**
     * A constructor to produce a new PastMeeting. ID not guaranteed to be unique.
     *
     * @param id        the id of the meeting
     * @param date      the date the meeting took place
     * @param contacts  the attendees of the meeting
     * @param notes     the notes of the meeting
     */
	public PastMeetingImpl(int id, Calendar date, Set<Contact> contacts, String notes) {
		super(id, date, contacts);
		ContactImpl.setNonNullObject(notes);
		sb.append(notes);
	}
	
	
	/**
	 * @see Meeting
	 */
	@Override
	public String getNotes() {
		String gNotes = sb.toString();
		return gNotes;
	}
	
	
}