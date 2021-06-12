package client.commons;

import server.Client;

public class Logout extends Message{

	public Logout(String[] data) {
		super(data);
	}

	@Override
	public void process(Client client) {
		client.setToken(null); // Destroy authentication token
		client.setUser(null); // Destroy User information
		client.send(new Result(true));
	}
		

}
