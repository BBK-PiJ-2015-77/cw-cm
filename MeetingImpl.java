import java.util.Calendar;
import java.util.Set;

public class MeetingImpl implements Meeting {
	
	private int id;
	private Calendar date;
	private Set<Contact> contacts;
	
	
	//ID needs to be positive non zero
	public MeetingImpl(int id, Calendar date, Set<Contact> contacts) {
		ContactImpl.setId(id);
		this.id = id;
		this.date = date;
		this.contacts = contacts;
	}
	
	@Override
	public int getId() {
		return id;
	}
	
	@Override
	public Calendar getDate() {
		return date;
	}
	
	@Override
	public Set<Contact> getContacts() {
		return contacts;
	}
	
}