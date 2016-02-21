import java.util.Calendar;
import java.util.Set;

public class MeetingImpl implements Meeting {
	
	private int id;
	private Calendar date;
	private Set<Contact> contacts;
	
	public MeetingImpl(int id, Calendar date, Set<Contact> contacts) {
		//constructor
	}
	
	@Override
	public int getId() {
		return 0;
	}
	
	@Override
	public Calendar getDate() {
		return null;
	}
	
	@Override
	public Set<Contact> getContacts() {
		return null;
	}
	
}