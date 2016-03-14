import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;

public class Methods {
	
	public static boolean equalsContact(Contact con1, Contact con2) {
		boolean result = false;
		if (con1.getId() == con2.getId() &&
			con1.getName().equals(con2.getName()) && 
			con1.getNotes().equals(con2.getNotes())) {
				result = true;
				return result;
		} else {
			return result;
		}
	}
	
	public static boolean containsContact(Set<Contact> list, Contact con) {
		boolean result = false;
		if (list.isEmpty()) {
			return result;
		} else {
			for ( Contact check : list) {
				if (equalsContact(check, con)) {
					result = true;
					return result;
				} 
			}
		}
		return result;
	}
	
	public static boolean equalsContactList(Set<Contact> list1, Set<Contact> list2) {
		boolean result = true;
		if (list1.isEmpty() || list2.isEmpty()) {
			result = false;
			return result;
		} else {
			//check all contacts in list1 are in list2
			for ( Contact con : list1) {
				if (!containsContact(list2, con)) {
					result = false;
				}
			}
			//and check all contacts in list2 are in list1
			for ( Contact con : list2) {
				if (!containsContact(list1, con)) {
					result = false;
				}
			}
			
		}
		return result;
	}
	
	public static boolean checkDuplicateMeetings(List<Meeting> meetings) {
		boolean result = false;
		for (int i = 0; i<meetings.size(); i++) {
			for (int j = (i+1); j<meetings.size(); j++) {
				if (equalsMeeting(meetings.get(i),meetings.get(j))) {
					result = true;
				}
			}
		}
		return result;
	}
	
	
	public static boolean equalsMeeting(Meeting m1, Meeting m2) {
		boolean result = false;
		if (m1.getId() == m2.getId() &&
			m1.getDate().equals(m2.getDate()) && 
			equalsContactList(m1.getContacts(), m2.getContacts())) {
				result = true;
				return result;
		} else if  (m1.getDate().equals(m2.getDate()) && 
					equalsContactList(m1.getContacts(), m2.getContacts())) {
						result = true;
						return result;
		} else {
			return result;
		}
	}
	
	public static List<Meeting> removeDuplicateMeetings(List<Meeting> meetings) {
		List<Meeting> uniqueList = meetings;
		
		boolean result = false;
		for (int i = 0; i<meetings.size(); i++) {
			for (int j = (i+1); j<meetings.size(); j++) {
				if (equalsMeeting(meetings.get(i),meetings.get(j))) {
					uniqueList.remove(meetings.get(j));
				}
			}
		}
		return uniqueList;
	}
	
}