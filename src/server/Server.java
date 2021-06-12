package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

import client.commons.Message;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Server {
	
	private static final Logger logger = Logger.getLogger("");
	private volatile static boolean stop = false;
	private static ServerSocket server;
	private static int port = 50002;
	
	
	
	
	public static void startServer(int Port) {
		logger.info("Start server");
		try {
			server = new ServerSocket(port, 10, null);
			Runnable r = new Runnable() {
				@Override
				public void run() {
					while (!stop) {
						try {
							Socket socket = server.accept();
							Client client = new Client(socket);
							System.out.println("Verbindung zum Server hergestellt");
							Client.add(client);
						} catch (Exception e) {
							logger.info(e.toString());
						}
					}
				}
			};
			Thread t = new Thread(r, "ServerSocket");
			t.start();
		} catch (IOException e) {
			logger.info(e.toString());
		}
	}
	
	


	public static void main(String[] args) {
		startServer(port);
		
		
	}
	


}
