/**
 * Implementation of the Contact interface.
 * <p/>
 * This class does not ensure that ID's are unique, and therefore has a dependency on another class to
 * ensure that IDs provided to this are unique.
 * <p/>
 * ContactManagerImpl ensures IDs provided to this are unique.
 * <p/>
 * Several assumptions have been made in the creation of this class:
 * <ul>
 * <li>Adding notes when notes already exist will, if the current notes and new string is nonempty, append these
 * with a '++' in the middle with a space either side. Adding empty spaces will be appended in the same way. This has no impact for use
 * within ContactManager as there is no way of altering notes once a Contact is created.</li>
 * <li>Nulls are allowed to be entered for name and notes. It has not been specified to throw any exceptions. The
 * class ContactManager deals with nulls and empty strings.</li>
 * <li>Empty names and notes can be stored.</li>
 * </ul>
 */

public class ContactImpl implements Contact {
	
	private int ID;
	private String name;
	private String notes;
	
	 /**
     * Creates a Contact. Name and Notes can be null. ID should be ensured that it is unique before it is passed into this method.
     *
     * @param id    the ID for the contact
     * @param name  the name of the contact
     * @param notes the notes about the contact
     */
	public ContactImpl(int ID, String name, String notes) {
		setAge(ID);
		this.ID = ID;
		this.name = name;
		this.notes = notes;
		//throw exception - if ID isn't unique. Don't think this
		//can be done here, do it implementation of CM
	}
	
	/**
     * Creates a Contact. Name and Notes can be null. ID should be ensured that it is unique before it is passed into this method.
     *
     * @param id    the ID for the contact
     * @param name  the name of the contact
     */
	public ContactImpl(int ID, String name) {
		setAge(ID);
		this.ID = ID;
		this.name = name;
		this.notes = "";
	}
	
	
	/**
	 * @see Contact
	 */
	@Override
	public int getId() {
		return ID;
	}
	
	
	/**
	 * @see Contact
	 */
	@Override
	public String getName() {
		return name;
	}
	
	
	/**
	 * @see Contact
	 */
	@Override
	public String getNotes() {
		return notes;
	}
	
	
	/**
	 * @see Contact
	 */
	@Override
	public void addNotes(String note) {
		notes = notes + " ++ " + note;
	}
	
	/**
     * Throws an exception for the ID entered at the constructor if the ID isn't a non-zero positive integer
     *
     * @param id    the ID for the contact
     *
     * @throws IllegalArgumentException if the ID isn't a non-zero positive integer
     */
	private void setAge(int ID) {
		if (ID <= 0) {
			throw new IllegalArgumentException("The ID must be a non-zero positive integer");
		}
	}
}