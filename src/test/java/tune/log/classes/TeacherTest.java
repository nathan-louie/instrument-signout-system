package tune.log.classes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TeacherTest
{
	@Test
	void testEqualsOne()
	{
		Teacher t1 = new Teacher("aba", "abc123");
		Teacher t2 = new Teacher("aba", "abc123");
		boolean result = t1.equals(t2);
		boolean expected = true;
		assertEquals(expected, result);
	}

	@Test
	void testEqualsTwo()
	{
		Teacher t1 = new Teacher("aba", "abc123");
		Teacher t2 = new Teacher("aban", "abc123");
		boolean result = t1.equals(t2);
		boolean expected = false;
		assertEquals(expected, result);
	}

	@Test
	void testEqualsThree()
	{
		Teacher t1 = new Teacher("aba", "abc123");
		Teacher t2 = new Teacher("aba", "abc123a");
		boolean result = t1.equals(t2);
		boolean expected = false;
		assertEquals(expected, result);
	}

	@Test
	void testEqualsFour()
	{
		Teacher t1 = new Teacher("aba", "abc123", "bob", "obo");
		Teacher t2 = new Teacher("aba", "abc123", "bob", "obo");
		boolean result = t1.equals(t2);
		boolean expected = true;
		assertEquals(expected, result);
	}

	@Test
	void testEqualsFive()
	{
		Teacher t1 = new Teacher("abaa", "abc123", "bob", "obo");
		Teacher t2 = new Teacher("aba", "abc123", "bob", "obo");
		boolean result = t1.equals(t2);
		boolean expected = false;
		assertEquals(expected, result);
	}

	@Test
	void testEqualsSix()
	{
		Teacher t1 = new Teacher("aba", "abc123c", "bob", "obo");
		Teacher t2 = new Teacher("aba", "abc123", "bob", "obo");
		boolean result = t1.equals(t2);
		boolean expected = false;
		assertEquals(expected, result);
	}
}
