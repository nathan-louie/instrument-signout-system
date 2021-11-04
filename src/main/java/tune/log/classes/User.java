package tune.log.classes;

import org.bson.codecs.pojo.annotations.BsonProperty;

import com.opencsv.bean.CsvBindByName;

public abstract class User
{
	@CsvBindByName(column = "password")
	private String password;

	@BsonProperty(value = "first_name")
	@CsvBindByName(column = "firstName")
	private String firstName;

	@BsonProperty(value = "last_name")
	@CsvBindByName(column = "lastName")
	private String lastName;

	/**
	 * Empty constructor for BSON decoding.
	 */
	public User()
	{
	}

	/**
	 * Constructor for a User.
	 * 
	 * @param password  hashed password
	 * @param firstName first name of the student
	 * @param lastName  last name of the student
	 */
	public User(String password, String firstName, String lastName)
	{
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
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

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		User other = (User) obj;
		if (password == null) {
			if (other.password != null) {
				return false;
			}
		} else if (!password.equals(other.password)) {
			return false;
		}
		return true;
	}
}
