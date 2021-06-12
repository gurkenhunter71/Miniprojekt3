package client.commons;

import server.User;
import server.Client;


public class ChangePassword extends Message{
	
	private String token;
	private String password;
	
	public ChangePassword(String[] data) {
		super(data);
		this.token = data[1];
		this.password = data[2];
		
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void process(Client client) {
		boolean result = false;
		if (client.getToken().equals(token)) {
			User User = client.getUser();
			User.changePassword(password);
			result = true;
		}
		client.send(new Result(result));
	}
		
	

}
