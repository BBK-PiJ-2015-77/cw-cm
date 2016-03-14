import java.util.Calendar;
import java.util.Set;
import java.io.Serializable;

public abstract class MeetingImpl implements Meeting, Serializable {
	
	private int id;
	private Calendar date;
	private Set<Contact> contacts;
	
	/**
     * Creates a new MeetingImpl. It should be ensured that ID passed into this is unique elsewhere as this class does not ensure uniqueness.
     *
     * @param id        the ID of the meeting
     * @param date      the date of the meeting
     * @param contacts  the contacts attending the meeting
     * @throws IllegalArgumentException if ID isn't a non-zero positive integer
     * @throws NullPointerException if date is null
     * @throws NullPointerException if contacts is null
     * @throws IllegalArgumentException if contacts is empty
     */
	public MeetingImpl(int id, Calendar date, Set<Contact> contacts) {
		CMExceptions.setId(id);
		CMExceptions.setNonNullObject(date);
		CMExceptions.setNonNullObject(contacts);
		CMExceptions.setNonEmptySet(contacts);
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