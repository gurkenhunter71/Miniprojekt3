package server;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;


import server.Prio;

public class ToDo implements Comparable <ToDo>, Serializable, Sendable {
	
	protected static ArrayList<ToDo> toDolistServer = new ArrayList<ToDo>();

	private static int IDNr = 0;
	private int ID;
	private String title;
	private Prio Prio;
	private String description;
	private LocalDate dueDate; 
	private String user;
	private static final long serialVersionUID = 0;
	

	private static int raiseID() {
		return IDNr++;
	}
	
	@Override
	public String toString() {
		return ID + "  :  " + title + "  :  " + dueDate+ "  :  " + user ;
	}

	public ToDo(String title, Prio Prio, String description, LocalDate dueDate, String user) {
		this.ID = raiseID();
		this.title = title;
		this.Prio = Prio;
		this.description = description;
		this.dueDate = dueDate;
		this.user = user;
	}
	
	public ToDo(String title, Prio Prio, String description, String user) {
		this.ID = raiseID();
		this.title = title;
		this.Prio = Prio;
		this.description = description;
		this.dueDate = null;
		this.user = user;
		
	}
	
	public ToDo(String title, Prio Prio, String description) {
		this.ID = raiseID();
		this.title = title;
		this.Prio = Prio;
		this.description = description;
		this.dueDate = null;
		
		
	}
	public ToDo(String title, Prio Prio, String description, LocalDate dueDate) {
		this.ID = raiseID();
		this.title = title;
		this.Prio = Prio;
		this.description = description;
		this.dueDate = dueDate;
		
		
		
	}

	public static int getIDNr() {
		return IDNr;
	}

	public static void setIDNr(int iDNr) {
		IDNr = iDNr;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Prio getPrio() {
		return Prio;
	}

	public void setPrio(Prio Prio) {
		this.Prio = Prio;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public int getID() {
		return ID;
	}
	public String getUser() {
		return this.user;
	}
	public void setUser(String user) {
		this.user=user;
	}

	public void setID(int toDoID) {
		this.ID = toDoID;
		// Correct highest ID if IDs are restored from files (may have gaps after
		// deleting entries)
		if (this.ID > highestID)
			highestID = toDoID + 1;
	}
	private static int getNextID() {
		return highestID++;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void send(Message msg) {
		// TODO Auto-generated method stub
		
	}
	public static ArrayList<ToDo> getTodolistserver() {
		return toDolistServer;
	}
	@Override
	public int compareTo(ToDo o) {
		int compValue = this.getTitle().compareTo(o.getTitle());
		if(compValue  == 0)
			return 0;
			else
				if (compValue < 0)
					return -1;
				else
					return 1;
	}
	
	
	public boolean equals(ToDo o) {
		if(this.ID == o.getID()) 
			return true;
		else 
			return false;
	}

	@Override
	public void send(client.commons.Message msg) {
		// TODO Auto-generated method stub
		
	}

}

