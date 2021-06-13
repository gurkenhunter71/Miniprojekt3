package client.commons;

import server.User;
import server.Client;
import server.ServerModel;
import client.appClasses.*;

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
		User user = User.exists(username);
		if (user != null && user.checkPassword(password)) { //Password mit Hash funktioniert nicht 
			if(user.getToken() == null) {
				user.setUserToken();}
			
			
			String token = user.getToken();
			client.setUser(user);
			client.setToken(token);
			
			reply = new Result(true, token);
		} else {
			reply = new Result(false);
		}
		client.send(reply);
		
	
	}}
	
