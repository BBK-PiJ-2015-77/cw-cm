import java.util.Calendar;
import java.util.Set;

public abstract class MeetingImpl implements Meeting {
	
	private int id;
	private Calendar date;
	private Set<Contact> contacts;
	
	/**
     * Creates a new MeetingImpl. It should be ensured that ID passed into this is unique elsewhere as this class does not ensure uniqueness.
     *
     * @param id        the ID of the meeting
     * @param date      the date of the meeting
     * @param contacts  the contacts attending the meeting
     */
	public MeetingImpl(int id, Calendar date, Set<Contact> contacts) {
		ContactImpl.setId(id);
		this.id = id;
		this.date = date;
		this.contacts = contacts;
	}
	
	
	/**
	 * @see Meeting
	 */
	@Override
	public int getId() {
		return id;
	}
	
	
	/**
	 * @see Meeting
	 */
	@Override
	public Calendar getDate() {
		return date;
	}
	
	
	/**
	 * @see Meeting
	 */
	@Override
	public Set<Contact> getContacts() {
		return contacts;
	}
	
}