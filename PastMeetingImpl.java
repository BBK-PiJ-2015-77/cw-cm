import java.util.Calendar;
import java.util.Set;

public class PastMeetingImpl extends MeetingImpl implements PastMeeting {
	
	private String notes;
	
	public PastMeetingImpl(int id, Calendar date, Set<Contact> contacts, String notes) {
		super(id, date, contacts);
		this.notes = notes;
	}
	
	public String getNotes() {
		return notes;
	}
	
	
}