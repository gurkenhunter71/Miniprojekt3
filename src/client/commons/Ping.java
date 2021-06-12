package client.commons;

import server.Client;


public class Ping extends Message {
	private String token;
	
	public Ping(String[] data) {
		super(data);
		token = null;
		if (data.length > 1) token = data[1];
	}

	@Override
	public void process(Client client) {
		boolean result = (token == null || token.equals(client.getToken()));
		client.send(new Result(result));
	}
	

}
