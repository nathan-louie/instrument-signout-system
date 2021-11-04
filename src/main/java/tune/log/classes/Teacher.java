package tune.log.classes;

import com.opencsv.bean.CsvBindByName;

public class Teacher extends User
{
	@CsvBindByName(column = "username")
	private String username;

	/**
	 * Empty constructor for BSON decoding.
	 */
	public Teacher()
	{
	}

	/**
	 * Constructor for a Teacher with no first name or last name.
	 * 
	 * @param username username of the teacher
	 * @param password hashed password
	 */
	public Teacher(String username, String password)
	{
		super(password, "", "");
		this.username = username;
	}

	/**
	 * Constructor for a Teacher.
	 * 
	 * @param username  username of the teacher
	 * @param password  hashed password
	 * @param firstName first name of the teacher
	 * @param lastName  last name of the teacher
	 */
	public Teacher(String username, String password, String firstName, String lastName)
	{
		super(password, firstName, lastName);
		this.username = username;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Teacher other = (Teacher) obj;
		if (username == null) {
			if (other.username != null) {
				return false;
			}
		} else if (!username.equals(other.username)) {
			return false;
		}
		return true;
	}
}
