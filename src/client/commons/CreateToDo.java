package client.commons;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import message.Result;
import server.Client;
import server.Prio;
import server.ToDo;


public class CreateToDo extends Message {
	private String token;
	private String title;
	private String priority;
	private String description;
	private String username;
	private String dueDate;
	private LocalDate today = LocalDate.now();
	

	public CreateToDo(String[] data) {
		super(data);
		this.token = data[1];
		this.title = data[2];
		this.priority = data[3];
		this.description = data[4];
		this.dueDate = data[5];
	}

	@Override
	public void process(Client client) {
		boolean result = false; 
		ToDo toDo = null;
		Message reply = null;
		Prio p = Prio.valueOf(priority);
		LocalDate localDate = LocalDate.parse(dueDate);
		//if (client.getToken().equals(token)) {
			if(title.length()>= 1) {
				if(localDate.compareTo(today) >= 0) {
					//String username = client.getName();
					String username = this.token;
					toDo = new ToDo(this.title, p, this.description,localDate, username);
					//add it to the list
					//ServerModel.getToDoList().add(toDo);
					
				}} reply = new Result(true);
				client.send(reply);
		
	

}
}
