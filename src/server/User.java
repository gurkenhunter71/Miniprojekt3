package server;

import java.io.Serializable;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class User implements Serializable  {
	
	
	private static final long serialVersionUID = 1;
	private static Logger logger = Logger.getLogger("");
	private String password;
	private ArrayList<Integer>  toDoList;
	private static final SecureRandom rand = new SecureRandom();
	private final String username;
	private static final int iterations = 127;
	
	private static final ArrayList<User> Users = new ArrayList<User>();
	
	private final byte[] salt = new byte[64];
	private String hashedPassword;
	
	public User(String username, String password) {
		this.username = username;
		
		
		this.hashedPassword = hash(password);
		this.toDoList = new ArrayList<Integer>();
		
	}
	
	//return existing user
	public static User exists(String username) {
		synchronized (Users) {
			for (User User : Users) {
				if (User.username.equals(username)) return User;
			}
		}
		return null;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ArrayList<Integer> getToDoList() {
		return toDoList;
	}

	public void setToDoList(ArrayList<Integer> toDoList) {
		this.toDoList = toDoList;
	}
	
	@Override
	public String toString() {
		return "User [username=" + username +"]";
	}

	public static void add(User newUser) {
		synchronized (Users) {
			Users.add(newUser);
		}
	}

		//hashed pw
	public boolean checkPassword(String password) {
		String newHash = hash(password);
		boolean success = hashedPassword.equals(newHash);
		return success;
	
	}
	
	public void changePassword(String newPassword) {
		rand.nextBytes(salt); // Change the salt with the password!
		this.hashedPassword = hash(newPassword);
	}

	public static String getToken() {
		byte[] token = new byte[16];
		rand.nextBytes(token);
		return bytesToHex(token);
	
	}
	
	//hashing
	private String hash(String password) {
		try {
			char[] chars = password.toCharArray();
			PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			byte[] hash = skf.generateSecret(spec).getEncoded();
			return bytesToHex(hash);
		} catch (Exception e) {
			logger.severe("Secure password hashing not possible - stopping server");
			System.exit(0);
			return null; // Will never execute, but keeps Java happy
		}
	}
	
	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

	public static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	
	}
}
