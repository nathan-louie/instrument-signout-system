package tune.log.table;

import tune.log.classes.Teacher;

public class TeacherEntry extends UserEntry
{
	private String username;

	/**
	 * Constructor for converting a Teacher object to teacher entry.
	 * 
	 * @param teacher Teacher object to convert
	 */
	public TeacherEntry(Teacher teacher)
	{
		super(teacher.getFirstName(), teacher.getLastName());
		this.username = teacher.getUsername();
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}
}
