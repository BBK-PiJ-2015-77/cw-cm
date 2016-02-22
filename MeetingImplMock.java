import java.util.Calendar;
import java.util.Set;

public class MeetingImplMock extends MeetingImpl {
	
	public MeetingImplMock(int id, Calendar date, Set<Contact> contacts) {
		super(id, date, contacts);
	}
	
}