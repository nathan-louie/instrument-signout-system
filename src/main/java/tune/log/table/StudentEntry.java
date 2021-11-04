package tune.log.table;

import tune.log.classes.Student;

public class StudentEntry extends UserEntry
{
	private int studentId;

	/**
	 * Constructor for converting a Student object to student entry.
	 * 
	 * @param student student object to convert
	 */
	public StudentEntry(Student student)
	{
		super(student.getFirstName(), student.getLastName());
		this.studentId = student.getStudentId();
	}

	public int getStudentId()
	{
		return studentId;
	}

	public void setStudentId(int studentId)
	{
		this.studentId = studentId;
	}
}
