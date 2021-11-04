package tune.log.table;

import java.time.format.DateTimeFormatter;

import tune.log.classes.Event;
import tune.log.database.Database;

public class EventEntry
{
	private String timestamp;
	private String instrumentName;
	private String studentName;
	private String eventType;

	/**
	 * Constructor for converting an Event object to event entry.
	 * 
	 * @param event    Event object to parse
	 * @param database Database object to use to get data
	 */
	public EventEntry(Event event, Database database)
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a 'on' yyyy/MM/dd");
		this.timestamp = event.getTimestamp().format(formatter);
		if (event.getInstrumentId() == -1) {
			this.instrumentName = "---";
		} else {
			this.instrumentName = database.getInstrument(event.getInstrumentId()).getName();
		}
		if (event.getStudentId() == -1) {
			this.studentName = "---";
		} else {
			this.studentName = database.getStudent(event.getStudentId()).getFirstName() + " "
					+ database.getStudent(event.getStudentId()).getLastName();
		}
		if (event.getType() == 2) {
			this.eventType = "Classroom Close";
		} else if (event.getType() == 3) {
			this.eventType = "Classroom Open";
		} else {
			this.eventType = (event.getType() == 0) ? "In" : "Out";
		}
	}

	public String getTimestamp()
	{
		return timestamp;
	}

	public void setTimestamp(String timestamp)
	{
		this.timestamp = timestamp;
	}

	public String getInstrumentName()
	{
		return instrumentName;
	}

	public void setInstrumentName(String instrumentName)
	{
		this.instrumentName = instrumentName;
	}

	public String getStudentName()
	{
		return studentName;
	}

	public void setStudentName(String studentName)
	{
		this.studentName = studentName;
	}

	public String getEventType()
	{
		return eventType;
	}

	public void setEventType(String eventType)
	{
		this.eventType = eventType;
	}
}
