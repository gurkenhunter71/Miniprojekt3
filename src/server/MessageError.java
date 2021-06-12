package server;

import client.commons.Message;

public class MessageError extends Message {
	
	public MessageError() {
		super(new String[] {"MessageError", "Invalid command"});		
	}

	@Override
	public void process(Client client) {
		// Auto-generated method stub
		
	}
	
	

}
