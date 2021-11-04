package tune.log.classes;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption
{
	private String password;

	/**
	 * Constructor for the encryption of a password.
	 * 
	 * @param password password to encrypt
	 */
	public Encryption(String password)
	{
		this.password = password;
	}

	/**
	 * Encrypt the password using the SHA-256 encryption standard.
	 * 
	 * @return the String encrypted password
	 */
	public String encrypt()
	{
		MessageDigest md = null;
		String s = "";
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes());
			byte byteData[] = md.digest();

			for (int i = 0; i < byteData.length; ++i) {
				s += (Integer.toHexString((byteData[i] & 0xFF) | 0x100).substring(1, 3));
			}
		} catch (NoSuchAlgorithmException e) {
		}
		return s;
	}

}
