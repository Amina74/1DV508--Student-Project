package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Optional;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import userHandling.User;
import userHandling.UserHandler;
import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * 
 * @author Melker Fält
 * @date 2020-04-27
 */

public class ManagementMenuDialog 
{
	private static int fieldWidth = 200; //The primary length used for fields.
	private static int fieldHeight = 25; //The primary height used for fields.
	private static int currentPage = 1;  //The current page (Admin view only).
	private static String cssStyle = "-fx-border-color: #000000; -fx-border-width: 0.5px; -fx-background-color: #FFFFFF;"
			+ "-fx-text-inner-color: #808080;";
	private static Alert alert;
	
	/**
	 * Displays the user promotion dialog window.
	 * @return The user promotion dialog window.
	 * @throws FileNotFoundException
	 */
	public static Scene promoteUserDialog() throws FileNotFoundException, SQLException {
		ObservableList<String> users = FXCollections.observableArrayList(UserHandler.viewUsersList()/* Implement user table importing here*/);

		// Labels and TextFields \\
		Label selectUserLabel = new Label("Select a user");
		selectUserLabel.setFont(Font.font("Open Sans", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 22));

		/* List of available users */
		final ComboBox userList = new ComboBox(users);
		userList.setPrefSize(fieldWidth, fieldHeight);
		userList.setStyle(cssStyle);
		userList.setPromptText("Users");
		
		/* A users ID field */
		TextField userId = new TextField();
		userId.setFont(Font.font("Open Sans", FontWeight.EXTRA_BOLD,12));
		userId.setPrefSize(fieldWidth, fieldHeight);
		userId.setStyle(cssStyle);
		userId.setPromptText("User ID");
		
		/* User name input field */
		TextField userNameInput = new TextField();
		userNameInput.setFont(Font.font("Open Sans", FontWeight.EXTRA_BOLD,12));
		userNameInput.setPrefSize(fieldWidth, fieldHeight);
		userNameInput.setStyle(cssStyle);
		/* Sets the stored name to that of the currently logged in user and disables editing */
		userNameInput.setText(UserMain.getCurrentUserName()); 
		userNameInput.setEditable(false);
		
		/* Confirmation Button */
		Button confirmationBtn = new Button("Confirm");
		confirmationBtn.setFont(Font.font("Open Sans", FontWeight.NORMAL,12));
		confirmationBtn.setPrefSize(100, fieldHeight);
		confirmationBtn.setStyle(cssStyle);
		
		confirmationBtn.setOnAction(actionEvent -> {
			
			/* Checks if a user has been selected from the ComboBox */
			if(!userList.getSelectionModel().isEmpty()) {
				alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirmation Dialog");
				alert.setHeaderText(null);
				alert.setContentText("Are you sure that you want to promote the user " + "\"" + 
									 userList.getSelectionModel().getSelectedItem().toString() + "\"?");
				
				Optional<ButtonType> result = alert.showAndWait();
				if(result.get() == ButtonType.OK) {
					/* PROMOTING FUNCTIONALITY HERE */
					
					if(UserHandler.checkAdmin(userList.getSelectionModel().getSelectedItem().toString()) == 0) {
						UserHandler.setUserTypeToAdmin(userList.getSelectionModel().getSelectedItem().toString());
					}
					/* Closes the dialog window */
	                try {
	                	ManagementMenu.closeWindow();
	                } catch (FileNotFoundException e) {
	                    e.printStackTrace();
	                }
				}
			}
			
			else {
				alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Dialog");
				alert.setHeaderText(null);
				alert.setContentText("You need to select a user to promote!");
				alert.showAndWait();
			}
			
			/**
			 * Check the users privilege so that they can only change
			 * their own name and no one else's.
			 */
		});
		
		GridPane textFields = new GridPane();
		textFields.setAlignment(Pos.BASELINE_CENTER);
        textFields.setPadding(new Insets(0));
        textFields.setHgap(20);
        textFields.setVgap(20);
		
		
		/* Changes the layout depending on the user type */
		if(UserMain.getIsAdmin() == 1) {
			textFields.setPadding(new Insets(-45));
			textFields.add(selectUserLabel, 0, 0);
	        GridPane.setHalignment(selectUserLabel, HPos.CENTER);
			textFields.add(userList, 0, 1);
			textFields.add(confirmationBtn, 0, 2);
	        GridPane.setHalignment(confirmationBtn, HPos.RIGHT);
		}
		
		else {
			textFields.setPadding(new Insets(-15));
			textFields.add(userNameInput, 0, 1);
			textFields.add(confirmationBtn, 0, 2);
	        GridPane.setHalignment(confirmationBtn, HPos.RIGHT);
		}
        
		VBox emptySpace = new VBox();
		emptySpace.setPadding(new Insets(10));
        
		VBox root = new VBox(textFields, emptySpace);
        root.setStyle("-fx-background-color: #587dd1");
        root.setAlignment(Pos.BASELINE_CENTER);

		Scene outputScene = new Scene(root);
		return outputScene;
	}
	
	/**
	 * Displays the user deletion dialog window.
	 * @return The user deletion dialog window.
	 * @throws FileNotFoundException
	 */
	public static Scene deleteUserDialog() throws FileNotFoundException, SQLException {
		ObservableList<String> users = FXCollections.observableArrayList(UserHandler.viewUsersList());

		// Labels and TextFields \\
		Label selectUserLabel = new Label("Select a user");
		selectUserLabel.setFont(Font.font("Open Sans", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 22));

		/* List of available users */
		final ComboBox userList = new ComboBox(users);
		userList.setPrefSize(fieldWidth, fieldHeight);
		userList.setStyle(cssStyle);
		userList.setPromptText("Users");
		
		/* A users ID field */
		TextField userId = new TextField();
		userId.setFont(Font.font("Open Sans", FontWeight.EXTRA_BOLD,12));
		userId.setPrefSize(fieldWidth, fieldHeight);
		userId.setStyle(cssStyle);
		userId.setPromptText("User ID");
		
		/* User name input field */
		TextField userNameInput = new TextField();
		userNameInput.setFont(Font.font("Open Sans", FontWeight.EXTRA_BOLD,12));
		userNameInput.setPrefSize(fieldWidth, fieldHeight);
		userNameInput.setStyle(cssStyle);
		userNameInput.setText(UserMain.getCurrentUserName()); //Sets the field to the currently logged in users name.
		userNameInput.setEditable(false); //And disables the editing (since a normal user is only supposed 
										  //to be able to delete their own account.
		
		/* Confirmation Button */
		Button confirmationBtn = new Button("Confirm");
		confirmationBtn.setFont(Font.font("Open Sans", FontWeight.NORMAL,12));
		confirmationBtn.setPrefSize(100, fieldHeight);
		confirmationBtn.setStyle(cssStyle);
		
		confirmationBtn.setOnAction(actionEvent -> {

			/* Checks if a user has been selected from the ComboBox */
			if(!userList.getSelectionModel().isEmpty()) {
				alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirmation Dialog");
				alert.setHeaderText(null);
				alert.setContentText("Are you sure that you want to delete the user \"" + 
						userList.getSelectionModel().getSelectedItem().toString() + "\"?");

				Optional<ButtonType> result = alert.showAndWait();
				if(result.get() == ButtonType.OK) {
					try {
						if(UserHandler.deleteUser(userList.getSelectionModel().getSelectedItem().toString())){
							try {
								MainMenu.backToMainMenu();
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							}
							//TODO set the username to login after deletion
							//UserMain.setCurrentUserName("Login");
							UserMain.setIsLogged(0);

						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			
			/* If the user is an admin and no available user has been selected for deletion, display an alert. */
			else if(userList.getSelectionModel().isEmpty()) {
				alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Dialog");
				alert.setHeaderText(null);
				alert.setContentText("You need to select a user to delete!");
				alert.showAndWait();
			}
			
            /**
             * Maybe we add some sort of a message if update was successful!!
             * it will be easy because most of the update methods are boolean
             * Amin
             */
			
		});
		
		/* GridPane with text fields for the user to be handled */
        GridPane textFields = new GridPane();
        textFields.setAlignment(Pos.BASELINE_CENTER);
        textFields.setPadding(new Insets(0));
        textFields.setHgap(20);
        textFields.setVgap(20);
		textFields.setPadding(new Insets(-25));
		
		if(UserMain.getIsAdmin() == 1) {
			textFields.add(selectUserLabel, 0, 0);
            GridPane.setHalignment(selectUserLabel, HPos.CENTER);
        	textFields.add(userList, 0, 1);
    		textFields.add(confirmationBtn, 0, 2);
            GridPane.setHalignment(confirmationBtn, HPos.RIGHT);
            textFields.setPadding(new Insets(-45));
		}
		
		else {
			textFields.add(userNameInput, 0, 0);
			textFields.add(confirmationBtn, 0, 1);
        	GridPane.setHalignment(confirmationBtn, HPos.RIGHT);
		}
		
		VBox emptySpace = new VBox();
		emptySpace.setPadding(new Insets(10));
        
		VBox root = new VBox(textFields);
        root.setStyle("-fx-background-color: #587dd1");
        root.setAlignment(Pos.BASELINE_CENTER);

		Scene outputScene = new Scene(root);
		return outputScene;
	}
	
	/**
	 * Displays the user name updating dialog window.
	 * @return The user name updating dialog window.
	 * @throws FileNotFoundException
	 *
	 * Update the functionality **not in use
	 * Amin
	 */
	public static Scene updateUsernameDialog() throws FileNotFoundException, SQLException {
		ObservableList<String> users = FXCollections.observableArrayList(UserHandler.viewUsersList());

		// Labels and TextFields \\
		Label selectUserLabel = new Label("Select a user");
		selectUserLabel.setFont(Font.font("Open Sans", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 22));

		/* List of available users */
		final ComboBox userList = new ComboBox(users);
		userList.setPrefSize(fieldWidth, fieldHeight);
		userList.setStyle(cssStyle);
		userList.setPromptText("Users");
		userList.setPrefSize(200, 25);
		
		/* User name input field */
		TextField userNameInput = new TextField();
		userNameInput.setFont(Font.font("Open Sans", FontWeight.EXTRA_BOLD,12));
		userNameInput.setPrefSize(200, 25);
		userNameInput.setStyle(cssStyle);
		userNameInput.setPromptText("Username");
		/* Sets the stored name to that of the currently logged in user and disables editing */
		userNameInput.setText(UserMain.getCurrentUserName()); 
		userNameInput.setEditable(false);
		
		/* The new username input field */
		TextField userNameNewInput = new TextField();
		userNameNewInput.setFont(Font.font("Open Sans", FontWeight.EXTRA_BOLD,12));
		userNameNewInput.setPrefSize(200, 25);
		userNameNewInput.setStyle(cssStyle);
		userNameNewInput.setPromptText("New Username");
		
		/* Confirmation Button */
		Button confirmationBtn = new Button("Confirm");
		confirmationBtn.setFont(Font.font("Open Sans", FontWeight.NORMAL,12));
		confirmationBtn.setPrefSize(100, 25);
		confirmationBtn.setStyle(cssStyle);
		
		confirmationBtn.setOnAction(actionEvent -> {
			
			/* If the user is an admin and a user has been selected */
			if(UserMain.getIsAdmin() == 1 && !userList.getSelectionModel().isEmpty()) {
				alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirmation Dialog");
				alert.setHeaderText(null);
				alert.setContentText("Are you sure that you want to change the user \"" + 
					 userList.getSelectionModel().getSelectedItem().toString() + "'s\" username to \"" +
					 userNameNewInput.getText() + "\"" + "?");
				
				/* Closes the dialog window */
	            try {
	            	ManagementMenu.closeWindow();
	            } catch (FileNotFoundException e) {
	                e.printStackTrace();
	            }
			}
			
			/* If no user has been selected from the list */
			else {
				if(UserMain.getIsAdmin() == 1) {
					alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information Dialog");
					alert.setHeaderText(null);
					alert.setContentText("You need to select a user to change it's name!");
					alert.showAndWait();
				}
				
				/*
				else { If the user isn't an Admin (Yet to be added)
					alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information Dialog");
					alert.setHeaderText(null);
					alert.setContentText("You have to!");
					alert.showAndWait();
				}*/
				
			}
			
			/* NAME UPDATE FUNCTIONALITY HERE */
			// we won't need this for now.. just leave it in the code because we will implement updateUsername later.. Amin
			
		});
		
		/* GridPane with text fields for the user to be handled */
        GridPane textFields = new GridPane();
        textFields.setAlignment(Pos.BASELINE_CENTER);
        textFields.setPadding(new Insets(0));
        textFields.setHgap(20);
        textFields.setVgap(20);
        
		
        /* If the current user is an Admin */
        if(UserMain.getIsAdmin() == 1) {
        	
        	textFields.add(selectUserLabel, 0, 0);
            GridPane.setHalignment(selectUserLabel, HPos.CENTER);
        	textFields.add(userList, 0, 1);
        	textFields.add(userNameNewInput, 0, 2);
    		textFields.add(confirmationBtn, 0, 3);
            GridPane.setHalignment(confirmationBtn, HPos.RIGHT);
            textFields.setPadding(new Insets(-70));
            
        }
        
        /*If the user isn't an admin (Yet to be added).
        else {
        	textFields.setPadding(new Insets(-55));
        	textFields.add(userNameInput, 0, 0);
        	textFields.add(userNameNewInput, 0, 1);
    		textFields.add(confirmationBtn, 0, 2);
            GridPane.setHalignment(confirmationBtn, HPos.RIGHT);
        }*/
        
        
		VBox root = new VBox(textFields);
        root.setStyle("-fx-background-color: #587dd1");
        root.setAlignment(Pos.BASELINE_CENTER);

		Scene outputScene = new Scene(root);
		return outputScene;
	}
	
	/**
	 * Displays the user email updating dialog window.
	 * @return The user email updating dialog window.
	 * @throws FileNotFoundException
	 */
	public static Scene updatePasswordDialog() throws FileNotFoundException, SQLException {
		ObservableList<String> users = FXCollections.observableArrayList(UserHandler.viewUsersList());

		// Labels and TextFields \\
		Label selectUserLabel = new Label("Select a user");
		selectUserLabel.setFont(Font.font("Open Sans", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 22));

		/* List of available users */
		final ComboBox userList = new ComboBox(users);
		userList.setPrefSize(200, 25);
		userList.setStyle(cssStyle);
		userList.setPromptText("Users");
		
		/* Pasword update field */
		PasswordField passwordInput = new PasswordField();
		passwordInput.setFont(Font.font("Open Sans", FontWeight.EXTRA_BOLD,12));
		passwordInput.setPrefSize(200, 25);
		passwordInput.setStyle(cssStyle);
		passwordInput.setPromptText("New password");
		
		/* Pasword update field */
		PasswordField passwordConfirmationInput = new PasswordField();
		passwordConfirmationInput.setFont(Font.font("Open Sans", FontWeight.EXTRA_BOLD,12));
		passwordConfirmationInput.setPrefSize(200, 25);
		passwordConfirmationInput.setStyle(cssStyle);
		passwordConfirmationInput.setPromptText("Repeat password");
		
		/* Confirmation Button */
		Button confirmationBtn = new Button("Confirm");
		confirmationBtn.setFont(Font.font("Open Sans", FontWeight.NORMAL,12));
		confirmationBtn.setPrefSize(100, 25);
		confirmationBtn.setStyle(cssStyle);
		
		confirmationBtn.setOnAction(actionEvent -> {
			
			if(UserMain.getIsAdmin() == 1 && !userList.getSelectionModel().isEmpty()) {
				alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirmation Dialog");
				alert.setHeaderText(null);
				alert.setContentText("Are you sure that you want to change the user " + "\"" + 
						 userList.getSelectionModel().getSelectedItem().toString() + "'s\" password?");
				
				/* Changes the password of the selected user if OK is pressed */
				Optional<ButtonType> result = alert.showAndWait();
				if(result.get() == ButtonType.OK) {
					/* PASSWORD UPDATE FUNCTIONALITY HERE */
	               if (User.isPasswordValid(passwordInput.getText())) {
	                    UserHandler.updatePassword(passwordInput.getText(), 
	                    		userList.getSelectionModel().getSelectedItem().toString());
	                }
					
	               /* Closes the dialog window */
	                try {
	                	ManagementMenu.closeWindow();
	                } catch (FileNotFoundException e) {
	                    e.printStackTrace();
	                }
				}
			}
			
			/* If no user has been selected (and the current user is an Admin) display the following info dialog */
			if(UserMain.getIsAdmin() == 1 && userList.getSelectionModel().isEmpty()) {
				alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Dialog");
				alert.setHeaderText(null);
				alert.setContentText("You need to select a user to change it's password!");
				alert.showAndWait();
			}
			
			/* If the current user isn't an Admin, check for confirmation the nchange *THIS* users password */
			else {
				alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirmation Dialog");
				alert.setHeaderText(null);
				alert.setContentText("Are you sure that you want to change your password?");
				alert.showAndWait();
				
				Optional<ButtonType> result = alert.showAndWait();
				if(result.get() == ButtonType.OK) {
					/* PASSWORD UPDATE FUNCTIONALITY HERE */
					if (User.isPasswordValid(passwordInput.getText()) /*&&
	            		   passwordInput.getText().equals(passwordConfirmationInput.getText())*/) {

						UserHandler.updatePassword(passwordInput.getText(),UserMain.getCurrentUserName());

						/* Closes the dialog window */
						try {
							ManagementMenu.closeWindow();
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
					}

					/* Empty fields or non-identical passwords */
					else {
						alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Information Dialog");
						alert.setHeaderText(null);
						alert.setContentText("The passwords either didn't match or one or more fields are empty!");
						alert.showAndWait();
					}
				}
			}
		});
		
		/* GridPane with text fields for the user to be handled */
        GridPane textFields = new GridPane();
        textFields.setAlignment(Pos.BASELINE_CENTER);
        textFields.setPadding(new Insets(0));
        textFields.setHgap(20);
        textFields.setVgap(20);
		
		
		 /* If the current user is an Admin */
        if(UserMain.getIsAdmin() == 1) {
        	
        	textFields.add(selectUserLabel, 0, 0);
            GridPane.setHalignment(selectUserLabel, HPos.CENTER);
        	textFields.add(userList, 0, 1);
        	textFields.add(passwordInput, 0, 2);
    		textFields.add(confirmationBtn, 0, 3);
            GridPane.setHalignment(confirmationBtn, HPos.RIGHT);
            textFields.setPadding(new Insets(-95));
        }
        
        else {
        	textFields.add(passwordInput, 0, 0);
        	textFields.add(passwordConfirmationInput, 0, 1);
    		textFields.add(confirmationBtn, 0, 2);
            GridPane.setHalignment(confirmationBtn, HPos.RIGHT);
            textFields.setPadding(new Insets(-75));
        }
        
		VBox root = new VBox(textFields);
        root.setStyle("-fx-background-color: #587dd1");
        root.setAlignment(Pos.CENTER);

		Scene outputScene = new Scene(root);
		return outputScene;
	}
	
	/**
	 * Displays the user name updating dialog window.
	 * @return The user name updating dialog window.
	 * @throws FileNotFoundException
	 */
	public static Scene updateEmailDialog() throws FileNotFoundException, SQLException {
		ObservableList<String> users = FXCollections.observableArrayList(UserHandler.viewUsersList());

		// Labels and TextFields \\
		Label selectUserLabel = new Label("Select a user");
		selectUserLabel.setFont(Font.font("Open Sans", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 22));
		
		/* List of available users */
		final ComboBox userList = new ComboBox(users);
		userList.setPrefSize(fieldWidth, 25);
		userList.setStyle(cssStyle);
		userList.setPromptText("Users");
		
		/* Email input field */
		TextField emailInput = new TextField();
		emailInput.setFont(Font.font("Open Sans", FontWeight.EXTRA_BOLD,12));
		emailInput.setPrefSize(fieldWidth, 25);
		emailInput.setStyle(cssStyle);
		emailInput.setPromptText("New email");
		
		/* Email input field */
		TextField emailConfirmationInput = new TextField();
		emailConfirmationInput.setFont(Font.font("Open Sans", FontWeight.EXTRA_BOLD,12));
		emailConfirmationInput.setPrefSize(fieldWidth, 25);
		emailConfirmationInput.setStyle(cssStyle);
		emailConfirmationInput.setPromptText("Repeat email");
		
		/* Confirmation Button */
		Button confirmationBtn = new Button("Confirm");
		confirmationBtn.setFont(Font.font("Open Sans", FontWeight.NORMAL,12));
		confirmationBtn.setPrefSize(100, 25);
		confirmationBtn.setStyle(cssStyle);
		
		confirmationBtn.setOnAction(actionEvent -> {
			
			if(UserMain.getIsAdmin() == 1) {
				alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirmation Dialog");
				alert.setHeaderText(null);
				alert.setContentText("Are you sure that you want to change the user " + "\"" + 
						 userList.getSelectionModel().getSelectedItem().toString() + "'s\" email?");
				
				Optional<ButtonType> result = alert.showAndWait();
				if(result.get() == ButtonType.OK) {
					/* EMAIL UPDATE FUNCTIONALITY HERE */

	                UserHandler.updateEmail(emailInput.getText(),userList.getSelectionModel().getSelectedItem().toString()
	                						);
					
					/* Closes the dialog window */
	                try {
	                	ManagementMenu.closeWindow();
	                } catch (FileNotFoundException e) {
	                    e.printStackTrace();
	                }
				}
			}
			
			else {
				alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirmation Dialog");
				alert.setHeaderText(null);
				alert.setContentText("Are you sure that you want to change your email?");
				
				Optional<ButtonType> result = alert.showAndWait();
				if(result.get() == ButtonType.OK) {
					/* EMAIL UPDATE FUNCTIONALITY HERE */

	                UserHandler.updateEmail(emailInput.getText(),UserMain.getCurrentUserName());
					
					/* Closes the dialog window */
	                try {
	                	ManagementMenu.closeWindow();
	                } catch (FileNotFoundException e) {
	                    e.printStackTrace();
	                }
				}
			}
			
			
		});
		
		/* GridPane with text fields for the user to be handled */
        GridPane textFields = new GridPane();
        textFields.setAlignment(Pos.BASELINE_CENTER);
        textFields.setPadding(new Insets(0));
        textFields.setHgap(15);
        textFields.setVgap(15);
		
		
		/* If the current user is an Admin */
        if(UserMain.getIsAdmin() == 1) {
        	textFields.setPadding(new Insets(-75));
        	textFields.add(selectUserLabel, 0, 0);
            GridPane.setHalignment(selectUserLabel, HPos.CENTER);
        	textFields.add(userList, 0, 1);
        	textFields.add(emailInput, 0, 2);
    		textFields.add(confirmationBtn, 0, 3);
            GridPane.setHalignment(confirmationBtn, HPos.RIGHT);
        }
        
        else {
        	textFields.setPadding(new Insets(-45));
        	textFields.add(emailInput, 0, 0);
        	textFields.add(emailConfirmationInput, 0, 1);
    		textFields.add(confirmationBtn, 0, 2);
            GridPane.setHalignment(confirmationBtn, HPos.RIGHT);
        }
		
		VBox emptySpace = new VBox();
		emptySpace.setPadding(new Insets(10));
        
		VBox root = new VBox(emptySpace, textFields);
        root.setStyle("-fx-background-color: #587dd1");
        root.setAlignment(Pos.BASELINE_CENTER);

		Scene outputScene = new Scene(root);
		return outputScene;
	}
	
}
