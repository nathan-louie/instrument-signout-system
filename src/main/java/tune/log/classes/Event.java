package tune.log.classes;

import java.time.LocalDateTime;

import org.bson.codecs.pojo.annotations.BsonProperty;

public class Event
{
	private LocalDateTime timestamp;
	@BsonProperty(value = "instrument_id")
	private int instrumentId;
	@BsonProperty(value = "student_id")
	private int studentId;
	@BsonProperty(value = "event_type")
	private int type;

	/**
	 * Empty constructor for BSON decoding.
	 */
	public Event()
	{
	}

	/**
	 * Constructor for an Event with a set timestamp.
	 * 
	 * @param timestamp    timestamp of the event
	 * @param instrumentId Id of the instrument
	 * @param studentId    Id of the student
	 * @param type         type of action (0 or 1 if sign-in/out, and 2 or 3 if
	 *                     classroom close/open)
	 */
	public Event(LocalDateTime timestamp, int instrumentId, int studentId, int type)
	{
		this.timestamp = timestamp;
		this.instrumentId = instrumentId;
		this.studentId = studentId;
		this.type = type;
	}

	/**
	 * Constructor for an Event with an automatically set timestamp.
	 * 
	 * @param instrumentId Id of the instrument
	 * @param studentId    Id of the student
	 * @param type         type of action (0 or 1 if sign-in/out)
	 */
	public Event(int instrumentId, int studentId, int type)
	{
		this(LocalDateTime.now(), instrumentId, studentId, type);
	}

	public LocalDateTime getTimestamp()
	{
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp)
	{
		this.timestamp = timestamp;
	}

	public int getInstrumentId()
	{
		return instrumentId;
	}

	public void setInstrumentId(int instrumentId)
	{
		this.instrumentId = instrumentId;
	}

	public int getStudentId()
	{
		return studentId;
	}

	public void setStudentId(int studentId)
	{
		this.studentId = studentId;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}
}
