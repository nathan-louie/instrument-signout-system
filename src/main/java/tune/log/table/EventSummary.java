package tune.log.table;

public class EventSummary
{
	private String instrumentName;
	private String studentName;
	private String eventType;

	/**
	 * Constructor for converting an Event object to event entry.
	 * 
	 * @param event    Event object to parse
	 * @param database Database object to use to get data
	 */
	public EventSummary(EventEntry event)
	{
		this.instrumentName = event.getInstrumentName();
		this.studentName = event.getStudentName();
		this.eventType = event.getEventType();
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
