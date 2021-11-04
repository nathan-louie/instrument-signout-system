package tune.log.classes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StudentTest
{
	@Test
	void testEqualsOne()
	{
		Student s1 = new Student(101, "abc123");
		Student s2 = new Student(101, "abc123");
		boolean result = s1.equals(s2);
		boolean expected = true;
		assertEquals(expected, result);
	}

	@Test
	void testEqualsTwo()
	{
		Student s1 = new Student(1012, "abc123");
		Student s2 = new Student(101, "abc123");
		boolean result = s1.equals(s2);
		boolean expected = false;
		assertEquals(expected, result);
	}

	@Test
	void testEqualsThree()
	{
		Student s1 = new Student(101, "abc123");
		Student s2 = new Student(101, "abc1232");
		boolean result = s1.equals(s2);
		boolean expected = false;
		assertEquals(expected, result);
	}

	@Test
	void testEqualsFour()
	{
		Student s1 = new Student(101, "abc123", "aba", "bab");
		Student s2 = new Student(101, "abc123", "aba", "bab");
		boolean result = s1.equals(s2);
		boolean expected = true;
		assertEquals(expected, result);
	}

	@Test
	void testEqualsFive()
	{
		Student s1 = new Student(101, "abc123", "aba", "bab");
		Student s2 = new Student(101, "abc1232", "aba", "bab");
		boolean result = s1.equals(s2);
		boolean expected = false;
		assertEquals(expected, result);
	}

	@Test
	void testEqualsSix()
	{
		Student s1 = new Student(101, "abc123", "aba", "bab");
		Student s2 = new Student(1018, "abc123", "aba", "bab");
		boolean result = s1.equals(s2);
		boolean expected = false;
		assertEquals(expected, result);
	}
}
