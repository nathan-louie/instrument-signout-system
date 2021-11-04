package tune.log.table;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import tune.log.database.Database;

public class HelperTest
{
	private Database d = new Database();

	@Test
	@Order(1)
	void testGetStudentTable()
	{
		assertTrue(Helper.getStudentTable(d.getAllStudents()) != null);
	}

	@Test
	@Order(2)
	void testGetTeacherTable()
	{
		assertTrue(Helper.getTeacherTable(d.getAllTeachers()) != null);
	}

	@Test
	@Order(3)
	void testGetInstrumentTable()
	{
		assertTrue(Helper.getInstrumentTable(d.getAllInstruments(), d) != null);
	}

	@Test
	@Order(4)
	void testGetEventsTable()
	{
		assertTrue(Helper.getEventsTable(d.getAllEvents(), d) != null);
	}

	@Test
	@Order(5)
	void testGetEventSummaryTable()
	{
		assertTrue(Helper.getEventSummaryTable(Helper.getEventsTable(d.getAllEvents(), d)) != null);
	}
}
