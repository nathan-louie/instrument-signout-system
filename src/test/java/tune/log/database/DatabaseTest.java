package tune.log.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import tune.log.classes.Event;
import tune.log.classes.Instrument;
import tune.log.classes.Status;
import tune.log.classes.Student;
import tune.log.classes.Teacher;

public class DatabaseTest
{
	private Database d = new Database();

	@Test
	@Order(1)
	void testGetInstrument()
	{
		assertNull(d.getInstrument(-1));
	}

	@Test
	@Order(2)
	void testGetAllInstrument()
	{
		ArrayList<Instrument> a = d.getAllInstruments();
		boolean result = a.equals(null);
		boolean expected = false;
		assertEquals(expected, result);
	}

	@Test
	@Order(3)
	void testAddInstrument()
	{
		Instrument i = new Instrument(1, "testAdd", 0, -1);
		d.addInstrument(i);
		int result = d.getInstrument(1).getInstrumentId();
		int expected = 1;
		assertEquals(expected, result);
	}

	@Test
	@Order(4)
	void testUpdateInstrument()
	{
		Instrument i = new Instrument(1, "testAdd", 0, -1);
		d.addInstrument(i);
		i.setName("testUpdate");
		d.updateInstrument(i);
		String result = d.getInstrument(1).getName();
		String expected = "testUpdate";
		assertEquals(expected, result);
	}

	@Test
	@Order(5)
	void testDeleteInstrument()
	{
		Instrument i = new Instrument(1, "testAdd", 0, -1);
		d.addInstrument(i);
		d.deleteInstrument(i);
		d.deleteInstrument(i);
		d.deleteInstrument(i);
		assertNull(d.getInstrument(1));
	}

	@Test
	@Order(6)
	void testGetStatus()
	{
		assertTrue(d.getStatus() != null);
	}

	@Test
	@Order(7)
	void testUpdateStatus()
	{
		Status s = d.getStatus();
		d.updateStatus(s);
		assertEquals(s.getStatus(), d.getStatus().getStatus());
	}

	@Test
	@Order(8)
	void testGetAllEvent()
	{
		ArrayList<Event> a = d.getAllEvents();
		boolean result = a.equals(null);
		boolean expected = false;
		assertEquals(expected, result);
	}

	@Test
	@Order(9)
	void testGetStudent()
	{
		assertNull(d.getStudent(-1));
	}

	@Test
	@Order(10)
	void testGetAllStudent()
	{
		ArrayList<Student> a = d.getAllStudents();
		boolean result = a.equals(null);
		boolean expected = false;
		assertEquals(expected, result);
	}

	@Test
	@Order(11)
	void testAddStudent()
	{
		Student s = new Student(-1, "testAdd", "testAdd", "testAdd");
		d.addStudent(s);
		int result = d.getStudent(-1).getStudentId();
		int expected = -1;
		assertEquals(expected, result);
	}

	@Test
	@Order(12)
	void testUpdateStudent()
	{
		Student s = new Student(-1, "testAdd", "testAdd", "testAdd");
		d.addStudent(s);
		s.setFirstName("testUpdate");
		d.updateStudent(s);
		String result = d.getStudent(-1).getFirstName();
		String expected = "testUpdate";
		assertEquals(expected, result);
	}

	@Test
	@Order(13)
	void testDeleteStudent()
	{
		Student s = new Student(-1, "testAdd", "testAdd", "testAdd");
		d.addStudent(s);
		d.deleteStudent(s);
		assertNull(d.getStudent(-1));
	}

	@Test
	@Order(14)
	void testGetTeacher()
	{
		assertNull(d.getTeacher(""));
	}

	@Test
	@Order(15)
	void testGetAllTeacher()
	{
		ArrayList<Teacher> a = d.getAllTeachers();
		boolean result = a.equals(null);
		boolean expected = false;
		assertEquals(expected, result);
	}

	@Test
	@Order(16)
	void testAddTeacher()
	{
		Teacher t = new Teacher("testAdd", "testAdd", "testAdd", "testAdd");
		d.addTeacher(t);
		String result = d.getTeacher("testAdd").getUsername();
		String expected = "testAdd";
		assertEquals(expected, result);
	}

	@Test
	@Order(17)
	void testUpdateTeacher()
	{
		Teacher t = new Teacher("testAdd", "testAdd", "testAdd", "testAdd");
		d.addTeacher(t);
		t.setFirstName("testUpdate");
		d.updateTeacher(t);
		String result = d.getTeacher("testAdd").getFirstName();
		String expected = "testUpdate";
		assertEquals(expected, result);
	}

	@Test
	@Order(18)
	void testDeleteTeacher()
	{
		Teacher t = new Teacher("testAdd", "testAdd", "testAdd", "testAdd");
		d.addTeacher(t);
		d.deleteTeacher(t);
		d.deleteTeacher(t);
		d.deleteTeacher(t);
		assertNull(d.getTeacher("testAdd"));
	}
}
