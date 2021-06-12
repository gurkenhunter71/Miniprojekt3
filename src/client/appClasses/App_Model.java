package client.appClasses;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.TreeSet;
import java.util.logging.Logger;

import server.Prio;
import server.ToDo;
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
	protected TreeSet<ToDo> myTreeToDoList = new TreeSet<ToDo>();
	protected TreeSet<ToDo> ourToDoList = new TreeSet<ToDo>();
	private static String SEPARATOR = "|";
	

protected SimpleStringProperty newestMessage = new SimpleStringProperty();
protected DateTimeFormatter LocalFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	
	private Logger logger = Logger.getLogger("");
	private Socket socket;
	private String token;
	private OutputStreamWriter socketOut;
	private BufferedReader socketIn;

	
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
	
	
	
	
	
	
	

	public ToDo createToDo(String titel, Prio Prio, String description, LocalDate dueDate) throws IOException {
		// ToDo toDo = new ToDo(titel, Prio, description, dueDate);
	//	return toDo;
		boolean status = false;
		String line = "Login|" + this.token + SEPARATOR + titel + SEPARATOR + Prio + SEPARATOR + description + SEPARATOR + dueDate;
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
			return null;
		}
		
		
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


	public Object getUser() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	



}