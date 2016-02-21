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