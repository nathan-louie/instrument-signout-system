package tune.log.classes;

import org.bson.codecs.pojo.annotations.BsonProperty;

import com.opencsv.bean.CsvBindByName;

public class Student extends User
{
	@BsonProperty(value = "_id")
	@CsvBindByName(column = "studentId")
	private int studentId;

	/**
	 * Empty constructor for BSON decoding.
	 */
	public Student()
	{
	}

	/**
	 * Constructor for a Student with no first name or last name.
	 * 
	 * @param id       Id of the student
	 * @param password hashed password
	 */
	public Student(int id, String password)
	{
		super(password, "", "");
		this.studentId = id;
	}

	/**
	 * Constructor for a Student.
	 * 
	 * @param studentId Id of the student
	 * @param password  hashed password
	 * @param firstName first name of the student
	 * @param lastName  last name of the student
	 */
	public Student(int studentId, String password, String firstName, String lastName)
	{
		super(password, firstName, lastName);
		this.studentId = studentId;
	}

	public int getStudentId()
	{
		return studentId;
	}

	public void setStudentId(int studentId)
	{
		this.studentId = studentId;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + studentId;
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
		Student other = (Student) obj;
		if (studentId != other.studentId) {
			return false;
		}
		return true;
	}
}
