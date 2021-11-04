package tune.log.database;

import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.ArrayList;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import tune.log.classes.Event;
import tune.log.classes.Instrument;
import tune.log.classes.Status;
import tune.log.classes.Student;
import tune.log.classes.Teacher;

public class Database
{
	private final MongoCollection<Instrument> instruments;
	private final MongoCollection<Event> history;
	private final MongoCollection<Teacher> teachers;
	private final MongoCollection<Status> classroom;
	private final MongoCollection<Student> students;

	public Database()
	{
		CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
		CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
		MongoClientSettings clientSettings = MongoClientSettings.builder()
				.applyConnectionString(
						new ConnectionString("mongodb+srv://TuneLogAdmin:%23rL5!3tG8MAphed9@main.ov5jn.mongodb.net"))
				.codecRegistry(codecRegistry).build();
		MongoClient client = MongoClients.create(clientSettings);
		MongoDatabase database = client.getDatabase("main");
		this.students = database.getCollection("students", Student.class);
		this.instruments = database.getCollection("instruments", Instrument.class);
		this.classroom = database.getCollection("classroomStatus", Status.class);
		this.history = database.getCollection("history", Event.class);
		this.teachers = database.getCollection("teachers", Teacher.class);
	}

	/**
	 * Get an instrument from the database. Return null if none found.
	 * 
	 * @param instrument_id Id of the instrument to get
	 * @return the Instrument object
	 */
	public Instrument getInstrument(int instrument_id)
	{
		return instruments.find(eq("_id", instrument_id)).first();
	}

	/**
	 * Get all instruments from the database.
	 * 
	 * @return the List of all instruments
	 */
	public ArrayList<Instrument> getAllInstruments()
	{
		ArrayList<Instrument> allInstruments = new ArrayList<Instrument>();
		for (Instrument instrument : instruments.find()) {
			allInstruments.add(instrument);
		}
		return allInstruments;
	}

	/**
	 * Add an instrument to the database.
	 * 
	 * @param instrument Instrument object to add
	 */
	public void addInstrument(Instrument instrument)
	{
		if (getInstrument(instrument.getInstrumentId()) == null) {
			// Add the instrument to the collection.
			instruments.insertOne(instrument);
		} else {
			// Update the instrument if already found.
			updateInstrument(instrument);
		}
	}

	/**
	 * Update an instrument in the database.
	 * 
	 * @param instrument Updated Instrument object
	 */
	public void updateInstrument(Instrument instrument)
	{
		instruments.findOneAndReplace(eq("_id", instrument.getInstrumentId()), instrument);
	}

	/**
	 * Remove the specified instrument from the database.
	 * 
	 * @param instrument Instrument object to remove
	 */
	public void deleteInstrument(Instrument instrument)
	{
		instruments.deleteOne(eq("_id", instrument.getInstrumentId()));
	}

	/**
	 * Get the classroom status from the database.
	 * 
	 * @return Status object
	 */
	public Status getStatus()
	{
		return classroom.find(eq("_id", 0)).first();
	}

	/**
	 * Update the classroom status.
	 * 
	 * @param status Updated status object
	 */
	public void updateStatus(Status status)
	{
		classroom.findOneAndReplace(eq("_id", 0), status);
	}

	/**
	 * Get the nth recent event from the database. Return null if none found.
	 * 
	 * @param n nth recent event to get (0 is the most recent event)
	 * @return the nth recent Event object
	 */
	public Event getEvent(int n)
	{
		return history.find().sort(new Document("timestamp", -1)).limit(n).skip(n).first();
	}

	/**
	 * Get all events from the database.
	 * 
	 * @return the list of all events
	 */
	public ArrayList<Event> getAllEvents()
	{
		ArrayList<Event> events = new ArrayList<Event>();
		for (Event event : history.find()) {
			events.add(event);
		}
		return events;
	}

	/**
	 * Add an event to the database.
	 * 
	 * @param record Event object to add
	 */
	public void addEvent(Event record)
	{
		history.insertOne(record);
	}

	/**
	 * Get a student from the database. Return null if none found.
	 * 
	 * @param student_id Student Id of the student to get
	 * @return the Student object
	 */
	public Student getStudent(int student_id)
	{
		return students.find(eq("_id", student_id)).first();
	}

	/**
	 * Get all students from the database.
	 * 
	 * @return the list of all students
	 */
	public ArrayList<Student> getAllStudents()
	{
		ArrayList<Student> allStudents = new ArrayList<Student>();
		for (Student student : students.find()) {
			allStudents.add(student);
		}
		return allStudents;
	}

	/**
	 * Add a student to the database.
	 * 
	 * @param student Student object to add
	 */
	public void addStudent(Student student)
	{
		if (getStudent(student.getStudentId()) == null) {
			// Add the student to the collection.
			students.insertOne(student);
		} else {
			// Update the student if already found.
			updateStudent(student);
		}
	}

	/**
	 * Update a student in the database.
	 * 
	 * @param student Updated Student object
	 */
	public void updateStudent(Student student)
	{
		students.findOneAndReplace(eq("_id", student.getStudentId()), student);
	}

	/**
	 * Remove a student from the database.
	 * 
	 * @param student Student object to remove
	 */
	public void deleteStudent(Student student)
	{
		students.deleteOne(eq("_id", student.getStudentId()));
	}

	/**
	 * Get a teacher from the database. Return null if none found.
	 * 
	 * @param username username of the teacher to get
	 * @return the Teacher object
	 */
	public Teacher getTeacher(String username)
	{
		return teachers.find(eq("username", username)).first();
	}

	/**
	 * Get all teachers from the database.
	 * 
	 * @return the list of all teachers
	 */
	public ArrayList<Teacher> getAllTeachers()
	{
		ArrayList<Teacher> allTeachers = new ArrayList<Teacher>();
		for (Teacher teacher : teachers.find()) {
			allTeachers.add(teacher);
		}
		return allTeachers;
	}

	/**
	 * Add a teacher to the database.
	 * 
	 * @param teacher Teacher object to add
	 */
	public void addTeacher(Teacher teacher)
	{
		teachers.insertOne(teacher);
	}

	/**
	 * Update a teacher in the database.
	 * 
	 * @param teacher Updated Teacher object
	 */
	public void updateTeacher(Teacher teacher)
	{
		teachers.findOneAndReplace(eq("username", teacher.getUsername()), teacher);
	}

	/**
	 * Delete a teacher from the database.
	 * 
	 * @param teacher Teacher object to delete
	 */
	public void deleteTeacher(Teacher teacher)
	{
		teachers.deleteOne(eq("username", teacher.getUsername()));
	}
}
