package server;

import client.commons.Message;

public interface Sendable {	
		public abstract String getName(); // name of msg receiver
		public abstract void send(Message msg); // send msg
	}


