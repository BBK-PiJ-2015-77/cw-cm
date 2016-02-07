public class ContactImpl implements Contact {
	
	private int ID;
	private String name;
	private String notes;
	
	public  ContactImpl(int ID, String name, String notes) {
		this.ID = ID;
		this.name = name;
		this.notes = notes;
	}
	
	@Override
	public int getId() {
		return ID;
		//needs to be unique
		//needs to be a non-zero positive integer
		//use exception handling if an inappropriate iD is entered?
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
		notes = notes + "++ " + note;
	}
	
}