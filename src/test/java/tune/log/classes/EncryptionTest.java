package tune.log.classes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class EncryptionTest
{
	@Test
	void testEncryptOne()
	{
		Encryption e = new Encryption("aA");
		String result = e.encrypt();
		String expected = "af1c08098cf119bb4981729721714a4b9948dbcb6b5fff21cb0f45f06ad1f7ea";
		assertEquals(expected, result);
	}

	@Test
	void testEncryptTwo()
	{
		Encryption e = new Encryption(
				" !\\\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[]^_`abcdefghijklmnopqrstuvwxyz{|}~");
		String result = e.encrypt();
		String expected = "82213ab46fb6bee4367c599610cd7225e5a5be0cd9a6dda8ab951cc07ce90d48";
		assertEquals(expected, result);
	}

}
