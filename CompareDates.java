import java.util.Calendar;
import java.util.Comparator;

public class CompareDates implements Comparator<Meeting> {
	
	@Override
	public int compare(Meeting m1, Meeting m2) {
		Calendar date1 = m1.getDate();
		Calendar date2 = m2.getDate();
		
		return date1.compareTo(date2);
	}
	
}