package client.appClasses;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.logging.Logger;

import server.Prio;
import server.ToDo;
import server.User;
import client.ServiceLocator;
import client.abstractClasses.Model;
import javafx.beans.property.SimpleStringProperty;

/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 * 
 * @author Brad Richards
 */
public class App_Model extends Model {
	ServiceLocator serviceLocator;
	
	private static String SEPARATOR = "|";
	protected TreeSet<ToDo> myTreeToDoList = new TreeSet<ToDo>();
	

protected SimpleStringProperty newestMessage = new SimpleStringProperty();
protected DateTimeFormatter LocalFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	
	private Logger logger = Logger.getLogger("");
	private Socket socket;
	private String token;
	private OutputStreamWriter socketOut;
	private BufferedReader socketIn;
	
	private ArrayList<User> userList = new ArrayList<>();
	private ArrayList<ToDo> toDoList = new ArrayList<>();

	private static String USERS = "Users.txt";
	private static String TODO = "TODO.txt";
	

	
	public void connect(String ipAddress, int port) {
		logger.info("Connect");
		try {
			socket = new Socket(ipAddress, port);
			socketOut = new OutputStreamWriter(socket.getOutputStream());
			socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
			
		} catch (Exception e) {
			logger.warning(e.toString());
		}
	}
	
	
	public void disconnect() {
		logger.info("Disconnect");
		if (socket != null)
			try {
				socket.close();
			} catch (IOException e) {
				// Uninteresting
			}
	}
	
	
	
	
	
	
	

	public String createToDo(String titel, Prio prio, String description, LocalDate dueDate) throws IOException {
		// function toDo = new ToDo(titel, Prio, description, dueDate, user);
		int ID = -1;
		boolean status = false;
		String line = "Create|" + this.token + SEPARATOR + titel + SEPARATOR + prio + SEPARATOR + description + SEPARATOR + dueDate;
		socketOut.write(line + "\n");
		socketOut.flush();
		System.out.println("Sent: " + line);
		String msg = null;
		try {
		msg = socketIn.readLine();
		System.out.println("Received: " + msg);
		String[] parts = msg.split("\\|");
		if(parts[1].equalsIgnoreCase("true")) {
			status = true;
		}
		//ID = Integer.valueOf(parts[2]);
		return this.token;
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		return this.token;
		
	}

	public ToDo getSelectedToDo(int id) {
		ToDo toDo = null;
		for(ToDo t : myTreeToDoList) {
			if(t.getID() == id) {
				toDo = t;
			}
		}
		return toDo;
	}

	public boolean createUser(String name, String password) throws IOException {
	boolean status = false; 
	String line = "CreateLogin|" + name + SEPARATOR + password;
	socketOut.write(line + "\n");
	socketOut.flush();
	System.out.println("Sent: " + line);
	String msg = null;
	
	try {
	msg = socketIn.readLine();
	System.out.println("Received: " + msg);
	String[] parts = msg.split("\\|");
	if(parts[1].equalsIgnoreCase("true")) {
		status = true;
	}
	} catch (IOException e) {
		e.printStackTrace();
	}
	 return status;
	
	}


	public boolean login(String userName, String password) throws IOException {
		boolean status = false; 
		String line = "Login|" + userName + SEPARATOR + password;
		socketOut.write(line + "\n");
		socketOut.flush();
		System.out.println("Sent: " + line);
		String msg = null;
		try {
		msg = socketIn.readLine();
		System.out.println("Received: " + msg);
		String[] parts = msg.split("\\|");
		
		if(parts[1].equalsIgnoreCase("true")) {
			this.token = parts[2];
			
			status = true;
		} 
		
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Hat nicht funktioniert");
		
		} 
		return status;


	}	
	
	public boolean logOut() throws IOException {
		boolean status = false; 
		String line = "Logout";
		socketOut.write(line + "\n");
		socketOut.flush();
		System.out.println("Sent: " + line);
		String msg = null;
		
		try {
		msg = socketIn.readLine();
		System.out.println("Received: " + msg);
		String[] parts = msg.split("\\|");
		if(parts[1].equalsIgnoreCase("true")) {
			status = true;
		}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		 return status;
		
		}


	public void connectionControl() throws IOException {
		boolean status = false;
		String line = "Ping";
		socketOut.write(line + "\n");
		socketOut.flush();
		System.out.println("Sent: " + line);
		String msg = null;
		try {
		msg = socketIn.readLine();
		System.out.println("Received: " + msg);
		String[] parts = msg.split("\\|");
		if(parts[1].equalsIgnoreCase("true")) {
			status = true;
		}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public static Boolean checkEmail(String email) {
		boolean valid = false;

		// Split on '@': must give us two not-empty parts
		String[] addressParts = email.split("@");
		if (addressParts.length == 2 && !addressParts[0].isEmpty() && !addressParts[1].isEmpty()) {
			// We want to split the domain on '.', but split does not give us an empty
			// string, if the split-character is the last character in the string. So we
			// first ensure that the string does not end with '.'
			if (addressParts[1].charAt(addressParts[1].length() - 1) != '.') {
				// Split domain on '.': must give us at least two parts.
				// Each part must be at least two characters long
				String[] domainParts = addressParts[1].split("\\.");
				if (domainParts.length >= 2) {
					valid = true;
					for (String s : domainParts) {
						if (s.length() < 2) valid = false;
					}
				}
			}
		}
		return valid;

	}

	
	

	

	public ArrayList<User> getUserList() {
		return userList;
	}

	public ArrayList<ToDo> getToDoList() {
		return toDoList;
	}

	/**
	  Save and restore server data
	  ------------------------------------------------------------------------------------------------------------------
	 */
	

	public void writeSaveFileUsers() {
		File file = new File(USERS);
		try (FileWriter fileOut = new FileWriter(file)) {
			for (User user : userList) {
				String line = writeUser(user);
				fileOut.write(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Token is not saved - every user is logged out if the server crashes
	public String writeUser(User user) {
		String line =  user.getUsername() + SEPARATOR + user.getPassword() + "\n";
		return line;
	}

	public void writeSaveFileToDo() {
		File file = new File(TODO);
		try (FileWriter fileOut = new FileWriter(file)) {
			for (ToDo todo : toDoList) {
				String line = writeToDo(todo);
				fileOut.write(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// System.out.println("saved");
	}

	public String writeToDo(ToDo todo) {
		String line = todo.getID() + SEPARATOR + todo.getTitle() + SEPARATOR + todo.getPrio() + SEPARATOR
				+ todo.getDescription() + SEPARATOR + todo.getUser() + "\n";
		return line;
	}

	public void readSaveFileUser() {
		File file = new File(USERS);
		String data = "";
		try (BufferedReader fileIn = new BufferedReader(new FileReader(file))) {
			String line = fileIn.readLine();
			while (line != null) {
				User user = readUser(line);
				userList.add(user);
				line = fileIn.readLine();
			}
		} catch (FileNotFoundException e) {
			data = "Save file does not exist";
		} catch (IOException e) {
			data = e.getClass().toString();
		}
	}

	public User readUser(String line) {
		String[] attributes = line.split(SEPARATOR);
		int userID = -999;
		String userName = "-";
		String userPassword = "-";
		try {
			userID = Integer.valueOf(attributes[0]);
			userName = attributes[1];
			userPassword = attributes[2];
		} catch (Exception e) {
			userName = "-Error in Line-";
		}
		User user = new User(userName, userPassword, null);
		user.setID(userID); // restore ID
		return user;
	}

	public void readSaveFileToDo() {
		File file = new File(TODO);
		String data = "";
		try (BufferedReader fileIn = new BufferedReader(new FileReader(file))) {
			String line = fileIn.readLine();
			while (line != null) {
				ToDo todo = readToDo(line);
				toDoList.add(todo);
				line = fileIn.readLine();
			}
		} catch (FileNotFoundException e) {
			data = "Save file does not exist";
		} catch (IOException e) {
			data = e.getClass().toString();
		}
	}

	public ToDo readToDo(String line) {
		String[] attributes = line.split(SEPARATOR);
		int todoID = -999;
		String todoTitle = "-";
		String todoPriority = "-";
		String todoDescription = "-";
		String todoUser = "-";
		try {
			todoID = Integer.valueOf(attributes[0]);
			todoTitle = attributes[1];
			todoPriority = attributes[2];
			todoDescription = attributes[3];
			todoUser = attributes[4];
		} catch (Exception e) {
			todoTitle = "-Error in Line-";
		}
		ToDo todo = new ToDo(todoTitle, Prio.valueOf(todoPriority), todoDescription, todoUser);
		todo.setID(todoID);
		myTreeToDoList.add(todo);
		this.toDoList.add(todo);
		return todo;
	}
	
	



}