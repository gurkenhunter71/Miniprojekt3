package client.appClasses;

import java.io.IOException;
import java.time.LocalDate;

import server.Prio;
import server.ToDo;
import client.JavaFX_App_Template;
import client.ServiceLocator;
import client.abstractClasses.Controller;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.WindowEvent;

/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 * 
 * @author Brad Richards
 */

public class App_Controller extends Controller<App_Model, App_View> {
	ServiceLocator serviceLocator;

	
	public App_Controller(final JavaFX_App_Template main, App_Model model, App_View view) {
		super(model, view);

		serviceLocator = ServiceLocator.getServiceLocator();
		serviceLocator.getLogger().info("Application controller initialized");
		this.view = view;
		this.model = model;
		
		view.connectButton.setOnAction(event -> {
			String ipAddress = view.ipAddressTF.getText();
			int port = Integer.parseInt(view.portTF.getText());
			model.connect(ipAddress, port);
			view.backToLogin();
			try {
				model.connectionControl();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		
		//Validator
		
        view.ipAddressTF.textProperty().addListener(
				// Parameters of any PropertyChangeListener
				(observable, oldValue, newValue) -> validateipAdress(newValue));
	
        view.portTF.textProperty().addListener((observable, oldValue, newValue) -> validatePort(newValue));
        view.usernameTFLogin.textProperty().addListener((observable, oldValue, newValue) -> validateUserName(newValue));
        // register ourselves to handle window-closing event
        view.getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.exit();
            }
        });
		
		
		view.registerButton.setOnAction(this::changeViewRegistration);
		
		view.createUserButton.setOnAction(arg0 -> {
			try {
				createUser(arg0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		
		view.changePWButton.setOnAction(this::pwView);
		
		view.newToDoButton.setOnAction(this::changeViewCreateToDOs);
		
		view.logoutButton.setOnAction(this::backToLogin);
		
		view.backButton.setOnAction(this::backToLogin);
		
		view.backBtn2.setOnAction(this::changetodoBp);

		view.saveBtn.setOnAction(arg0 -> {
			try {
				saveNewToDo(arg0);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		view.todoList.setOnMouseClicked(this::showToDo);
		
		
		view.deleteBtn.setOnAction(this::deleteToDo);
		
		view.loginButton.setOnAction(arg0 -> {
			try {
				loginClient(arg0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		view.logoutButton.setOnAction(arg0 -> {
			try {
				logOut(arg0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		
	


		
	}
	
	private void logOut(Event e) throws IOException {
		model.logOut();
		view.backToLogin();
	}
	private void loginClient(Event e) throws IOException {
		String userName = view.usernameTFLogin.getText();
		String password = view.pwTFLogin.getText();
		Boolean passwordCheck = model.login(userName, password);
		if (passwordCheck == true) {
		view.changetodoBp();
		} else {
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setHeaderText("Wrong password");
			errorAlert.setContentText("Please fill in the correct password");
			errorAlert.showAndWait();
		}
		
	}
	
	
	private void showToDo(MouseEvent mouseevent1) {
		view.changeViewCreateToDOs();
		// disable
		ToDo toDo = (ToDo) view.todoList.getSelectionModel().getSelectedItem();
		this.updateView(toDo);
		
	}
	

	
	private void deleteToDo(Event e) {
		int id = Integer.parseInt(view.idTF.getText());
		
		server.ToDo toDo = model.getSelectedToDo(id);
		model.myTreeToDoList.remove(toDo);
		this.updateView(null);
		this.updateAllLists();
		
	}
	
	private void updateAllLists() {
		view.todoList.getItems().clear();
		
		
		
		for(server.ToDo t : model.myTreeToDoList) {
			view.todoList.getItems().add(t);
		}
		
		
	}

	private void updateView(ToDo toDo) {
		if (toDo != null) {
			view.txtTitle.setText(toDo.getTitle());
			view.txtaDescription.setText(toDo.getDescription());
			view.prioCB.getSelectionModel().select(toDo.getPrio());
			view.dueDP.setValue(toDo.getDueDate());
			//int i = ToDo.getIDNr();
			//view.idTF.setText(String.valueOf(i));
			view.idTF.setText(String.valueOf(toDo.getID()));
			
			
			
		} else {
			view.txtTitle.setText("");
			view.txtaDescription.setText("");
			view.prioCB.getSelectionModel().select(null);
			view.dueDP.setValue(null);
			
			
		}
		
	}
	
	

	private void saveNewToDo(Event e) throws IOException {
		String titel = view.txtTitle.getText();
		Prio Prio = view.prioCB.getSelectionModel().getSelectedItem();
		String description = view.txtaDescription.getText();
		LocalDate dueDate = view.dueDP.getValue();
		
		
		
		model.myTreeToDoList.add(new ToDo(titel, Prio, description, dueDate));
		System.out.println(dueDate);
		
	
	
		view.todoList.getItems().clear();
		for(ToDo t : model.myTreeToDoList) {
			view.todoList.getItems().add(t);
		}
		
		
	
		view.changeMainView();
		
	}
	
	//Methoden für Eingabeprüfung
	
	private void validateipAdress(String newValue) {
		boolean valid = false;
		
		view.ipAddressTF.getStyleClass().remove("ipAddressNotOk");
		view.ipAddressTF.getStyleClass().remove("ipAddressOk");
		
		if (isAportNr(newValue)) {
			valid = true;
		} else {
			valid = false;
		}
		
		if (valid) {
			view.ipAddressTF.getStyleClass().add("ipAddressOk");
		} else {
			view.ipAddressTF.getStyleClass().add("ipAddressNotOk");
		}
	}
	
	private boolean isAportNr(String s) {
		
		String regex = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
	            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
	            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
	            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$"; 
		return s.matches(regex); //returns true if input and regex matches otherwise false
	}
	
	private void validatePort(String NewValue) {
		
		boolean valid = false;
		
		view.portTF.getStyleClass().remove("PortOk");
		view.portTF.getStyleClass().remove("PortNotOk");
		
		if (isValidPort(NewValue)) {
			valid = true;
		} else {
			valid = false;
		}
		
		if (valid) {
			view.portTF.getStyleClass().add("PortOk");
		} else {
			view.portTF.getStyleClass().add("PortNotOk");
		}
	}
	
	private boolean isValidPort(String i) {
		int x = Integer.parseInt(i);
		
		if (x >= 1 && x <= 100000) {
			return true;
		}else {
			return false;
		}
	}
	
	private void validateUserName(String newValue) {
		boolean valid = false;
		
		view.usernameTFLogin.getStyleClass().remove("UserNameNotOk");
		view.usernameTFLogin.getStyleClass().remove("UserNameOk");
		
		if (isValidName(newValue)) {
			valid = true;
		} else {
			valid = false;
		}
		
		if (valid) {
			view.usernameTFLogin.getStyleClass().add("UserNameOk");
		} else {
			view.usernameTFLogin.getStyleClass().add("UserNameNotOk");
		}
	}
	    
	 public boolean isValidName(String s){      
	     String regex="\"[a-zA-Z0-9\\\\._\\\\-]{3,}\"";      
	      return s.matches(regex);//returns true if input and regex matches otherwise false;
	 }
	
	private void changeViewRegistration (Event e) {
		view.changeViewRegistration();
	}
	
	private void pwView(Event e) {
		view.pwView();
	}
	
	private void changeViewCreateToDOs(Event e) {
		view.changeViewCreateToDOs();
	}
	
	private void changetodoBp(Event e) {
		view.changetodoBp();
	}
	
	private void backToLogin(Event e) {
		view.backToLogin();
	}
	private void createUser (Event e) throws IOException {
		String name = view.usernameTF.getText();
		String password = view.pwTF.getText();
		if(password != null && password.length()>= 3) {
			if (name != null && name.length() >= 3) {
			Boolean login = model.createUser(name, password);
			view.chageViewLogin();
			}
		} else {
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setHeaderText("invalid Password or Username");
			errorAlert.setContentText("length>=3 of username and password ");
			errorAlert.showAndWait();
		}
		
		; 
	}
	
	
	
	
}

