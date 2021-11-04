package tune.log.classes;

import org.bson.codecs.pojo.annotations.BsonProperty;

import com.opencsv.bean.CsvBindByName;

public class Instrument
{
	@BsonProperty(value = "_id")
	@CsvBindByName(column = "instrumentId")
	private int instrumentId;

	@CsvBindByName(column = "name")
	private String name;

	@CsvBindByName(column = "status")
	private int status;

	@BsonProperty(value = "current_user")
	@CsvBindByName(column = "currentUser")
	private int currentUser;

	/**
	 * Empty constructor for BSON decoding.
	 */
	public Instrument()
	{
	}

	/**
	 * Constructor for an Instrument.
	 * 
	 * @param instrumentId Id of the instrument
	 * @param name         name of the instrument (in the format
	 *                     "instrument-number")
	 * @param status       status of the instrument (0 or 1 if signed-in/out)
	 * @param currentUser  Id of the user that currently has it (-1 if none)
	 */
	public Instrument(int instrumentId, String name, int status, int currentUser)
	{
		this.instrumentId = instrumentId;
		this.name = name;
		this.status = status;
		this.currentUser = currentUser;
	}

	public int getInstrumentId()
	{
		return instrumentId;
	}

	public void setInstrumentId(int id)
	{
		this.instrumentId = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public int getCurrentUser()
	{
		return currentUser;
	}

	public void setCurrentUser(int currentUser)
	{
		this.currentUser = currentUser;
	}
}
