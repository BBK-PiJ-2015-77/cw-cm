import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ContactImplTest {
	int id;
	String name;
	String notes;
	Contact con1;
	
	@Before
	public void setUp() {
		id = 11;
		name = "Tom";
		notes = "Notes...";
		
		con1 = new ContactImpl(id, name, notes);
	}
	
	@Test
	public void testsgetId() {
		assertEquals(id, con1.getId());
	}
	
	@Test
	public void testsgetName() {
		assertEquals(name, con1.getName());
	}
	
	@Test
	public void testsgetNotes() {
		assertEquals(notes, con1.getNotes());
	}
	
	@Test
	public void testsaddNotes() {
		con1.addNotes("String");
		assertNotEquals(notes, con1.getNotes());
	}
	
}