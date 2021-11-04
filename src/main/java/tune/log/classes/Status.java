package tune.log.classes;

import org.bson.codecs.pojo.annotations.BsonProperty;

public class Status
{
	@BsonProperty(value = "_id")
	private final int classroomId = 0;

	private int status;

	/**
	 * Empty constructor for BSON decoding.
	 */
	public Status()
	{
	}

	/**
	 * Constructor for the Status of a classroom.
	 * 
	 * @param status status of the classroom (0 or 1 if closed or open)
	 */
	public Status(int status)
	{
		this.status = status;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public int getClassroomId()
	{
		return classroomId;
	}
}
