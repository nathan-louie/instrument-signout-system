package tune.log.table;

import tune.log.classes.Instrument;
import tune.log.classes.Student;
import tune.log.database.Database;

public class InstrumentEntry
{
	private int instrumentId;
	private String instrumentName;
	private String status;
	private String currentUser;

	/**
	 * Constructor for converting Instrument object to instrument table entry.
	 * 
	 * @param instrument Instrument object to convert
	 * @param database   Database object to use to access data
	 */
	public InstrumentEntry(Instrument instrument, Database database)
	{
		this.instrumentId = instrument.getInstrumentId();
		this.instrumentName = instrument.getName();
		this.status = instrument.getStatus() == 0 ? "In" : "Out";
		if (instrument.getCurrentUser() != -1) {
			Student student = database.getStudent(instrument.getCurrentUser());
			this.currentUser = student.getFirstName() + " " + student.getLastName();
		} else {
			this.currentUser = "";
		}
	}

	public int getInstrumentId()
	{
		return instrumentId;
	}

	public void setInstrumentId(int instrumentId)
	{
		this.instrumentId = instrumentId;
	}

	public String getInstrumentName()
	{
		return instrumentName;
	}

	public void setInstrumentName(String instrumentName)
	{
		this.instrumentName = instrumentName;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getCurrentUser()
	{
		return currentUser;
	}

	public void setCurrentUser(String currentUser)
	{
		this.currentUser = currentUser;
	}
}
