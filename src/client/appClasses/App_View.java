package client.appClasses;

import java.time.LocalDate;
import java.util.Locale;
import server.Prio;
import client.ServiceLocator;
import client.abstractClasses.View;
import client.commons.Translator;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 * 
 * @author Brad Richards
 */
public class App_View extends View<App_Model> {
	Menu menuFile;
	Menu menuFileLanguage;
	Menu menuHelp;
	
	protected MenuBar menuBar;


	
	protected Scene sceneBase, sceneRoot, sceneLogin, sceneRegistration, sceneMainView, sceneChangePW,
		sceneCreateToDo;
	
	protected BorderPane root, registerBP, loginBP, ToDoBP, pwchangeBP, todoBP;
		
	protected GridPane serverEnterGP, registerGPinner, loginGPinner, todoGPinner, pwchangeGPinner;
	
	protected ListView todoList;
	
    protected ComboBox<String> comboGroup;
	
	protected ToolBar rootTBbot, mainViewTB, pwchangeTBtop, mainViewTBbot, pwchangeTBbot, todoTBtop, todoTBbot;
	
	protected Label registerLabel, usernameLabel, pwLabel, loginLabel, usernameLabelLogin, pwLabelLogin, ipLbl, portLabel,
		portLabelMyView, portLabelNrMyView, portLabelOurView, portLabelNrOurView, lbServerMyView, lbServerIPMyView,
			lbServerOurView, lbServerIPOurView, titleLabel, describeLabel, dateLabel, lbShare, lbCreator, 
			lbCreateDate, prioLabel, registerQLabel, pwchangeLabel, newPWLabel;
	

	
	protected Button backRootButton, connectButton, btLogin, registerButton, createUserButton, loginButton, logoutButton, changePWButton, pwchangeSaveButton, backButton,
		btMyToDo, backButtonToDo, backBtn2, saveBtn, deleteBtn, newToDoButton;
	
	protected TextField usernameTFTODO, usernameTF, usernameTFLogin, ipAddressTF, portTF, txtTitle, txtCreator, idTF, newPWTF;
	
	protected TextArea txtaDescription;
	
	protected DatePicker dueDP, dpCreateDate;
	
	protected ChoiceBox <Prio> prioCB;
	
	protected PasswordField pwTF, pwTFLogin;
/*
	private enum Prio { // TODO make external
		LOW, MEDIUM, HIGH
	};*/
	private Label idLabel;

	public App_View(Stage stage, App_Model model) {
		super(stage, model);
		ServiceLocator.getServiceLocator().getLogger().info("Application view initialized");
	}
	

	@Override //template method -- add everything in the GUI
	protected Scene create_GUI() {
		 //template menu and logger
		ServiceLocator sl = ServiceLocator.getServiceLocator();  
	    //Logger logger = sl.getLogger();
	  
	    menuBar = new MenuBar();
	    menuFile = new Menu();
	    menuFileLanguage = new Menu();
	    menuFile.getItems().add(menuFileLanguage);
	    
	    this.menuBar.getMenus().addAll(menuFile);
		
		
	    this.root = new BorderPane();
		
		//TEMPLATE language options
	       for (Locale locale : sl.getLocales()) {
	           MenuItem language = new MenuItem(locale.getLanguage());
	           menuFileLanguage.getItems().add(language);
	           language.setOnAction( event -> {
					sl.getConfiguration().setLocalOption("Language", locale.getLanguage());
	                sl.setTranslator(new Translator(locale.getLanguage()));
	                //this.comboGroup.getItems().removeAll(this.comboGroup.getItems());
	                updateTexts();
	            });
	       }
	       
		// Connection view
		
		
		
		this.root = new BorderPane();
		this.root.setTop(this.menuBar); //set for every scene		
		
		this.serverEnterGP = new GridPane();
		
		
		this.ipLbl = new Label ("label.ipLbl");
		
		this.portLabel = new Label ("label.portLabel");
		
		
		this.connectButton = new Button("button.connectButton");
		
		this.ipAddressTF = new TextField("127.0.0.1");
		this.portTF = new TextField("50002");
		
		this.serverEnterGP.add(this.ipLbl, 0, 0);
		this.serverEnterGP.add(this.ipAddressTF, 1, 0);
		this.serverEnterGP.add(this.portLabel, 0, 1);
		this.serverEnterGP.add(this.portTF, 1, 1);
		this.serverEnterGP.add(this.connectButton, 0, 2, 2, 1);
		
		this.root.setCenter(this.serverEnterGP);
		
		//View für Registrierung
		
		this.registerBP = new BorderPane();
		//this.registerBP.setTop(this.menuBar);
		
		this.registerGPinner = new GridPane();
		
		this.registerLabel = new Label("label.registerLabel");
		
		this.usernameLabel = new Label("label.usernameLabel");
		
		this.pwLabel = new Label("label.pwLabel");
		
		
		this.usernameTF = new TextField();
		this.pwTF = new PasswordField();
		
		this.createUserButton = new Button("button.createUserButton");
		
		this.backButton = new Button("button.backButton");
		
		this.registerGPinner.add(this.registerLabel, 0, 0);
		this.registerGPinner.add(this.usernameLabel, 0, 1);
		this.registerGPinner.add(this.usernameTF, 1, 1);
		this.registerGPinner.add(this.pwLabel, 0, 2);
		this.registerGPinner.add(this.pwTF, 1, 2);
		this.registerGPinner.add(this.createUserButton, 0, 3, 2, 1);
		
		this.registerBP.setBottom(this.backButton);
		this.registerBP.setCenter(this.registerGPinner);
		
		
		//View für Anmeldung
		
		this.loginBP = new BorderPane();
		//this.loginBP.setTop(this.menuBar);
		
		this.loginGPinner = new GridPane();
		
		
		this.loginLabel = new Label("label.loginLabel");
		
		this.usernameLabelLogin = new Label("label.usernameLabelLogin");
		
		this.pwLabelLogin = new Label("label.pwLabelLogin");
		
		
		
		this.registerQLabel = new Label("");
		
		
		this.usernameTFLogin = new TextField();
		this.pwTFLogin = new PasswordField();

		
		this.loginButton = new Button("button.loginButton");
	
		this.registerButton = new Button("button.registerButton");

		this.rootTBbot = new ToolBar();
		this.backRootButton = new Button("button.backRootButton");
		this.rootTBbot.getItems().add(backRootButton);
		
		
		this.loginGPinner.add(this.loginLabel, 0, 0);
		this.loginGPinner.add(this.usernameLabelLogin, 0, 1);
		this.loginGPinner.add(this.usernameTFLogin, 1, 1);
		this.loginGPinner.add(this.pwLabelLogin, 0, 2);
		this.loginGPinner.add(this.pwTFLogin, 1, 2);
		this.loginGPinner.add(this.loginButton, 0, 3, 2, 1);
		this.loginGPinner.add(this.registerQLabel, 0, 4);
		this.loginGPinner.add(this.registerButton, 0, 5, 2, 1);
		
		this.loginBP.setCenter(this.loginGPinner);
		this.loginBP.setBottom(this.rootTBbot);
		
		
		//View Meine ToDo's
		this.ToDoBP = new BorderPane();
		//this.mytodoBp.setTop(this.menuBar);
		
		this.todoList = new ListView<>();
		
		this.mainViewTB = new ToolBar();
		this.mainViewTBbot = new ToolBar();
		
		this.logoutButton = new Button("button.logoutButton");
		this.changePWButton = new Button("button.changePWButton");
		this.newToDoButton = new Button("button.newToDoButton");
		
		
		HBox spacer = new HBox();
		HBox.setHgrow(spacer, Priority.ALWAYS);
		
		this.mainViewTB.getItems().addAll(spacer, this.newToDoButton);
	
		HBox spacer2 = new HBox();
		HBox.setHgrow(spacer2, Priority.ALWAYS);
		this.mainViewTBbot.getItems().addAll(this.logoutButton, spacer2, this.changePWButton );
		
		this.ToDoBP.setTop(this.mainViewTB);
		this.ToDoBP.setCenter(this.todoList);
		this.ToDoBP.setBottom(this.mainViewTBbot);
		
		
		//View Change PW
		this.pwchangeBP = new BorderPane();
		//this.pwchangeBP.setTop(this.menuBar);
		
		this.pwchangeGPinner = new GridPane();
		
		this.pwchangeTBtop = new ToolBar();
		this.pwchangeTBbot = new ToolBar();
		
		this.backButtonToDo = new Button("button.backButtonToDo");
		this.pwchangeSaveButton = new Button("button.pwchangeSaveButton");
		

		this.pwchangeLabel = new Label("label.pwchangeLabel");
		this.newPWLabel = new Label("label.newPWLabel");
		
		this.newPWTF = new TextField();
		
		this.pwchangeGPinner.add(this.pwchangeLabel, 0, 0);
		this.pwchangeGPinner.add(this.newPWLabel, 0, 1);
		this.pwchangeGPinner.add(this.newPWTF, 1, 1);
		this.pwchangeGPinner.add(this.pwchangeSaveButton, 0, 2, 2, 1);
		
		
		this.pwchangeTBtop.getItems().addAll(this.backButtonToDo);
		
		this.pwchangeBP.setTop(this.pwchangeTBtop);
		this.pwchangeBP.setCenter(this.pwchangeGPinner);
		this.pwchangeBP.setBottom(this.pwchangeTBbot);
		
		
		//View für ToDo's zu erstellen und Detailansicht
		
		this.todoBP = new BorderPane();
		//this.todoBP.setTop(this.menuBar);
		
		this.todoGPinner = new GridPane();
		
		this.todoTBtop = new ToolBar();
		this.todoTBbot = new ToolBar();
		
		this.backBtn2 = new Button("button.backBtn2");
		this.saveBtn = new Button("button.saveBtn");
		this.deleteBtn = new Button("button.deleteBtn");
		
		this.titleLabel = new Label("label.titleLabel");
		this.describeLabel = new Label("label.describeLabel");
		
		this.dateLabel = new Label("label.dateLabel");
		
		
		
		
		
		this.prioLabel = new Label("label.prioLabel");
		
		this.txtTitle = new TextField();
		this.txtaDescription = new TextArea();
		
		// date selection
		this.dueDP = new DatePicker();
		dueDP.setDayCellFactory(picker -> new DateCell() {
		        public void updateItem(LocalDate date, boolean empty) {
		            super.updateItem(date, empty);
		            LocalDate today = LocalDate.now();

		            setDisable(empty || date.compareTo(today) < 0 );
		        }
		        
		    });
		
		dueDP.setEditable(false);
		
		this.idTF = new TextField();
		this.idTF.setEditable(false);
		this.idLabel = new Label("label.idLabel");
		/*this.usernameTFTODO = new TextField();
		this.usernameTFTODO.setEditable(false);
		*/
		
		this.prioCB = new ChoiceBox <Prio>();
		this.prioCB.getItems().addAll(Prio.values());
		
		this.todoGPinner.add(this.titleLabel, 0, 0);
		this.todoGPinner.add(this.txtTitle, 1, 0);
		this.todoGPinner.add(this.describeLabel, 0, 1);
		this.todoGPinner.add(this.txtaDescription, 1, 1);
		this.todoGPinner.add(this.prioLabel, 0, 2);
		this.todoGPinner.add(this.prioCB, 1, 2);
		this.todoGPinner.add(this.dateLabel, 0, 3);
		this.todoGPinner.add(this.dueDP, 1, 3);
		this.todoGPinner.add(this.idTF, 5, 9);
		this.todoGPinner.add(this.idLabel, 5, 8);
		//this.todoGPinner.add(this.usernameTFTODO, 5, 10);
		
		
		
		this.todoTBtop.getItems().addAll(this.backBtn2, this.saveBtn);
		this.todoTBbot.getItems().addAll(this.deleteBtn);
		
		this.todoBP.setTop(this.todoTBtop);
		this.todoBP.setCenter(this.todoGPinner);
		this.todoBP.setBottom(this.todoTBbot);
		
		
		
		
		 updateTexts();
		 
		//Set Scene
		sceneRoot = new Scene(root, 700, 550);		
		sceneLogin = new Scene(loginBP, 700, 550);		
		sceneRegistration = new Scene(registerBP, 700, 550);	
		sceneMainView = new Scene(ToDoBP, 700, 550);	
		sceneChangePW = new Scene(pwchangeBP, 700, 550);		
		sceneCreateToDo = new Scene(todoBP, 700, 550);		
		
		sceneRoot.getStylesheets().add(getClass().getResource("app.css").toExternalForm());
		sceneLogin.getStylesheets().add(getClass().getResource("app.css").toExternalForm());
		sceneRegistration.getStylesheets().add(getClass().getResource("app.css").toExternalForm());
		sceneMainView.getStylesheets().add(getClass().getResource("app.css").toExternalForm());
		sceneChangePW.getStylesheets().add(getClass().getResource("app.css").toExternalForm());
		sceneCreateToDo.getStylesheets().add(getClass().getResource("app.css").toExternalForm());
	     
		
		return sceneRoot;
		
	}
	 protected void updateTexts() {
		 Translator t = ServiceLocator.getServiceLocator().getTranslator();

			// The menu entries
		 menuFile.setText(t.getString("program.menu.file"));
	       menuFileLanguage.setText(t.getString("program.menu.file.language"));
         //menuHelp.setText(t.getString("program.menu.help"));

			// Labels

			
			
			ipLbl.setText(t.getString("label.ipLbl"));
			pwLabel.setText(t.getString("label.pwLabel"));
			portLabel.setText(t.getString("label.portLabel"));
			registerLabel.setText(t.getString("label.registerLabel"));
			usernameLabel.setText(t.getString("label.usernameLabel"));
			titleLabel.setText(t.getString("label.titleLabel"));
			loginLabel.setText(t.getString("label.loginLabel"));
			describeLabel.setText(t.getString("label.describeLabel"));
			registerQLabel.setText(t.getString("label.registerQLabel"));
			dateLabel.setText(t.getString("label.dateLabel"));
			usernameLabelLogin.setText(t.getString("label.usernameLabelLogin"));
			pwLabelLogin.setText(t.getString("label.pwLabelLogin"));
			prioLabel.setText(t.getString("label.prioLabel"));
			idLabel.setText(t.getString("label.idLabel"));
			
			pwchangeLabel.setText(t.getString("label.pwchangeLabel"));
			newPWLabel.setText(t.getString("label.newPWLabel"));
			
			
			
			// Text Fields
			//  .setPromptText(t.getString("textField."));
			

			// Text Area
			//.setPromptText(t.getString("textArea."));

			// Buttons
			createUserButton.setText(t.getString("button.createUserButton"));
			pwchangeSaveButton.setText(t.getString("button.pwchangeSaveButton"));
			//pingButton.setText(t.getString("button.pingButton"));
			
			newToDoButton.setText(t.getString("button.newToDoButton"));
			changePWButton.setText(t.getString("button.changePWButton"));
			deleteBtn.setText(t.getString("button.deleteBtn"));
			connectButton.setText(t.getString("button.connectButton"));
			backButton.setText(t.getString("button.backButton"));
			
			
			backButtonToDo.setText(t.getString("button.backButton"));
			
			logoutButton.setText(t.getString("button.logoutButton"));
			loginButton.setText(t.getString("button.loginButton"));
			registerButton.setText(t.getString("button.registerButton"));
			backBtn2.setText(t.getString("button.backBtn2"));
			saveBtn.setText(t.getString("button.saveBtn"));
			backRootButton.setText(t.getString("button.backRootButton"));
			

			//stage.setTitle(t.getString("program.name"));
			}
	   
	
	


	public void changeViewRegistration() {
		stage.setScene(sceneRegistration);
		stage.show();
		
	}


	public void changeViewLogin() {
		stage.setScene(sceneLogin);
		stage.show();
		
	}


	public void changeMainView() {
		stage.setScene(sceneMainView);
		stage.show();
		
	}

	public void backToRoot() {
		stage.setScene(sceneRoot);
		stage.show();
		
	}

	public void pwView() {
		stage.setScene(sceneChangePW);
		stage.show();
	}


	public void changeViewCreateToDOs() {
		stage.setScene(sceneCreateToDo);
		stage.show();
		
	}


	public void backToLogin() {
		stage.setScene(sceneLogin);
		stage.show();
		
	}


	public void changetodoBp() {
		stage.setScene(sceneMainView);
		stage.show();
		
	}
	


	

}