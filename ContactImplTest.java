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
	
	@Test(expected=IllegalArgumentException.class)
	public void testsbadId() {
		con1 = new ContactImpl(-3, name);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testszerobadId() {
		con1 = new ContactImpl(0, name);
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
	public void testsgetEmptyNotes() {
		con1 = new ContactImpl(id, name);
		String expected = "";
		assertEquals(expected, con1.getNotes());
	}
	
	@Test
	public void testsaddNotes() {
		con1.addNotes("String");
		assertNotEquals(notes, con1.getNotes());
	}
	
	@Test
	public void testsaddNotes2() {
		con1.addNotes(notes);
		String expected = "Notes... ++ Notes...";
		assertEquals(expected, con1.getNotes());
	}
	
}