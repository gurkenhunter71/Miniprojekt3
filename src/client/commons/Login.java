package client.commons;

import server.User;
import server.Client;

public class Login extends Message{

	private String username;
	private String password;

	public Login(String[] data) {
		super(data);
		this.username = data[1];
		this.password = data[2];
	}


	@Override
	public void process(Client client) {
		Message reply;
		// Find existing login matching the username
		User User = User.exists(username);
		if (User != null && User.checkPassword(password)) { //Password mit Hash funktioniert nicht 
			String token = User.getToken();
			client.setToken(token);
			reply = new Result(true, token);
		} else {
			reply = new Result(false);
		}
		client.send(reply);
		
	}
}
