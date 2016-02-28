import java.util.Set;

/**
 * A class to contain all possible exceptions that will be thrown
 * as part of the Contract Manager project
 */
public class CMExceptions {
	
	/**
     * Throws an exception for the object entered at the constructor if the object is null
     * Is static so that it can be used easily in other classes.
     *
     * @param obj    the object being checked for NullPointerException
     *
     * @throws NullPointerException if the ID isn't a non-zero positive integer
     */
	public static void setNonNullObject(Object obj) {
		if (obj == null) {
			throw new NullPointerException("A null string can not be entered as an argument");
		}
	}
	
	/**
     * Throws an exception for the ID entered at the constructor if the ID isn't a non-zero positive integer
     * Is static so that it can be used easily in other classes.
     *
     * @param id    the ID for the contact or any other purpose
     *
     * @throws IllegalArgumentException if the ID isn't a non-zero positive integer
     */
	public static void setId(int ID) {
		if (ID <= 0) {
			throw new IllegalArgumentException("The ID must be a non-zero positive integer");
		}
	}
	
	/**
     * Throws an exception if a set of Contacts is empty
     * Is static so that it can be used easily in other classes.
     *
     * @param contacts    A Set<Contact> 
     *
     * @throws IllegalArgumentException if the set is empty
     */
	public static void setNonEmptySet(Set<Contact> contacts) {
		if (contacts.isEmpty()) {
			throw new IllegalArgumentException("An empty list can not be entered as an argument");
		}
	}
	
}