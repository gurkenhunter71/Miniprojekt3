package client.commons;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import server.Client;
import server.Prio;
import server.ToDo;

public class CreateToDo extends Message {
	private String token;
	private String title;
	private String Prio;
	private String description;
	private String dueDate;
	private LocalDate today = LocalDate.now();
	

	public CreateToDo(String[] data) {
		super(data);
		this.token = data[1];
		this.title = data[2];
		this.Prio = data[3];
		this.description = data[4];
		this.dueDate = data[5];
	}

	@Override
	public void process(Client client) {
		boolean result = false; 
		Prio p = Prio.valueOf(Prio);
		LocalDate localDate = LocalDate.parse(dueDate);
		if (client.getToken().equals(token)) {
			if(title.length()>= 3) {
				if(localDate.compareTo(today) >= 0) {
					ToDo toDo = new ToDo(this.title, p, this.description,localDate);
					
				}
				
		}
		}
		
	}

}