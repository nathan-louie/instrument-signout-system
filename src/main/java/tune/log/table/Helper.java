package tune.log.table;

import java.util.ArrayList;

import tune.log.classes.Event;
import tune.log.classes.Instrument;
import tune.log.classes.Student;
import tune.log.classes.Teacher;
import tune.log.database.Database;

public class Helper
{
	/**
	 * Convert a list of Students to a list of StudentEntry.
	 * 
	 * @param students list of Students to convert
	 * @return the list of student table entries
	 */
	public static ArrayList<StudentEntry> getStudentTable(ArrayList<Student> students)
	{
		ArrayList<StudentEntry> studentTable = new ArrayList<>();
		for (Student student : students) {
			studentTable.add(new StudentEntry(student));
		}
		return studentTable;
	}

	/**
	 * Convert a list of Teachers to a list of TeacherEntry.
	 * 
	 * @param teachers list of Teachers to convert
	 * @return the list of teacher table entries
	 */
	public static ArrayList<TeacherEntry> getTeacherTable(ArrayList<Teacher> teachers)
	{
		ArrayList<TeacherEntry> teacherTable = new ArrayList<>();
		for (Teacher teacher : teachers) {
			teacherTable.add(new TeacherEntry(teacher));
		}
		return teacherTable;
	}

	/**
	 * Convert a list of Instruments to a list of InstrumentEntry.
	 * 
	 * @param instruments list of instruments to convert
	 * @param database    database to use to access data
	 * @return the list of instrument table entries
	 */
	public static ArrayList<InstrumentEntry> getInstrumentTable(ArrayList<Instrument> instruments, Database database)
	{
		ArrayList<InstrumentEntry> instrumentTable = new ArrayList<>();
		for (Instrument instrument : instruments) {
			instrumentTable.add(new InstrumentEntry(instrument, database));
		}
		return instrumentTable;
	}

	/**
	 * Convert a list of Events to a list of EventEntry.
	 * 
	 * @param events   list of events to convert
	 * @param database database to use to access data
	 * @return the list of event table entries
	 */
	public static ArrayList<EventEntry> getEventsTable(ArrayList<Event> events, Database database)
	{
		ArrayList<EventEntry> eventTable = new ArrayList<>();
		for (Event event : events) {
			eventTable.add(new EventEntry(event, database));
		}
		return eventTable;
	}

	/**
	 * Convert a list of EventEntry to a list of EventSummary.
	 * 
	 * @param events list of evententry to convert
	 * @return the list of event summary entries
	 */
	public static ArrayList<EventSummary> getEventSummaryTable(ArrayList<EventEntry> events)
	{
		ArrayList<EventSummary> eventTable = new ArrayList<>();
		for (EventEntry event : events) {
			eventTable.add(new EventSummary(event));
		}
		return eventTable;
	}
}
