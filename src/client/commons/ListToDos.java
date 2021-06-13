package client.commons;

import java.util.ArrayList;

import server.Client;
import server.Prio;
import server.ToDo;
import server.ServerModel;

public class ListToDos extends Message {
	
	private String token;
	private ArrayList<String> ids = new ArrayList<String>();
	private ArrayList<String> toDos = new ArrayList<String>();

	public ListToDos(String[] data) {
		super(data);
		this.token = data[1];
	}

	@Override
	public void process(Client client) {
		boolean result = false;
		if (client.getToken().equals(token)) {
			for(ToDo t : ServerModel.getToDoList()) {
				if(t.getUser().equals(client.getUser().getUsername())) {		
					this.ids.add(Integer.toString(t.getID()));
					this.toDos.add(t.toString());

					
					
				}
			}
			result = true;
		}
		
		client.send(new Result(result, ids ));
	}
}