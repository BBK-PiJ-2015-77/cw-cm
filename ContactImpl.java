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
     *
     * @throws...
     */
	public ContactImpl(int ID, String name, String notes) {
		setAge(ID);
		this.ID = ID;
		this.name = name;
		this.notes = notes;
		//throw exception - if ID isn't unique. Don't think this
		//can be done here, do it implementation of CM
	}
	
	public ContactImpl(int ID, String name) {
		setAge(ID);
		this.ID = ID;
		this.name = name;
		this.notes = "";
	}
	
	@Override
	public int getId() {
		return ID;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public String getNotes() {
		return notes;
	}
	
	@Override
	public void addNotes(String note) {
		notes = notes + " ++ " + note;
	}
	//need to create a list of ID's, to be able to check if they are unique
	
	private void setAge(int ID) {
		if (ID <= 0) {
			throw new IllegalArgumentException("The ID must be a non-zero positive integer");
		}
	}
}