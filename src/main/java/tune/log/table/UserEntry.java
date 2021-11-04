package tune.log.table;

public abstract class UserEntry
{
	private String firstName;
	private String lastName;

	/**
	 * Constructor for a User Table Entry.
	 * 
	 * @param firstName first name of user
	 * @param lastName  last name of user
	 */
	public UserEntry(String firstName, String lastName)
	{
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
}
