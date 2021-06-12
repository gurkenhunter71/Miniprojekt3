package server;

import java.time.LocalDate;

import server.Prio;

public class ToDo implements Comparable <ToDo> {
	
	private static int IDNr = 0;
	private final int ID;
	private String title;
	private Prio Prio;
	private String description;
	private LocalDate dueDate; 

	

	private static int raiseID() {
		return IDNr++;
	}
	
	@Override
	public String toString() {
		return title + " Deadline: " + dueDate;
	}

	public ToDo(String title, Prio Prio, String description, LocalDate dueDate) {
		this.ID = raiseID();
		this.title = title;
		this.Prio = Prio;
		this.description = description;
		this.dueDate = dueDate;
	}
	
	public ToDo(String title, Prio Prio, String description) {
		this.ID = raiseID();
		this.title = title;
		this.Prio = Prio;
		this.description = description;
		this.dueDate = null;
		
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

}
