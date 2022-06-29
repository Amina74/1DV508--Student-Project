package main; //REMOVE THIS
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Bartlomiej Minierski
 * 2020-04-17.
 * <p>
 * IMPORTANT! Create folder "graphic" inside your project folder (where you have .idea, out and src folder)
 * All .png files
 */

public class MainMenu extends Application {
    public static Stage window;
    private static Alert alert;
    private static String currentUserName; //Contains the currently logged in users name. (Semi-temporary) [Melker Fält 2020-04-27]
    
    public static void backToMainMenu() throws FileNotFoundException {
        window.close();
        window.setScene(mainMenuScene());
        window.setWidth(640);
        window.setHeight(480);
        window.setTitle("Timeline");
        window.setResizable(true);
        window.show();
        mainMenuScene();

    }
    
    /**
     * A method that sends the user to the Timeline editing menu.
     * @author Melker Fält
     * @date 2020-05-12
     */
    public static void goToEditMenu() {
    	try {
            window.setScene(TimelineMenu.selectTimelineEdit());
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    	
    }

    public static Scene mainMenuScene() throws FileNotFoundException {
        /* Graphic needed in menu.
         * Custom sizes for each *.png file made in this class
         * Graphic stored in projects graphic folder. */


        Image logo = new Image(new FileInputStream("graphic/tsiwira.png"));
        ImageView logoImageView = new ImageView(logo);
        logoImageView.setFitWidth(70);
        logoImageView.setFitHeight(70);
        
        Image settingsImage = new Image(new FileInputStream("graphic/settings.png"));
        ImageView settingsImageView = new ImageView(settingsImage);
        settingsImageView.setFitWidth(30);
        settingsImageView.setFitHeight(30);
        
        Image signInImage = new Image(new FileInputStream("graphic/signin.png"));
        ImageView signInImageView = new ImageView(signInImage);
        signInImageView.setFitHeight(30);
        signInImageView.setFitWidth(30);

        Image createImage = new Image(new FileInputStream("graphic/create.png"));
        ImageView createImageView = new ImageView(createImage);
        createImageView.setFitHeight(50);
        createImageView.setFitWidth(50);

        Image editImage = new Image(new FileInputStream("graphic/edit.png"));
        ImageView editImageView = new ImageView(editImage);
        editImageView.setFitHeight(50);
        editImageView.setFitWidth(50);

        Image viewImage = new Image(new FileInputStream("graphic/view.png"));
        ImageView viewImageView = new ImageView(viewImage);
        viewImageView.setFitHeight(50);
        viewImageView.setFitWidth(50);
       
        /* Buttons needed in this menu
         * Custom images, size and background. */

        /* sign in button */
        Button signIn = new Button("Sign in", signInImageView);
        signIn.setStyle("-fx-background-color: #587dd1");
        signIn.setFont(Font.font("Open Sans Light Italic", 14));
        signIn.setGraphic(signInImageView);
        signIn.setContentDisplay(ContentDisplay.RIGHT);
        
        /* create timeline button with image and text */
        Button createTimeline = new Button("Create Timeline", createImageView);
        createTimeline.setStyle("-fx-background-color: #aac0f2");
        createTimeline.setPrefWidth(150);
        createTimeline.setPrefHeight(150);
        createTimeline.setFont(Font.font("Open Sans Light", 18));
        createTimeline.setGraphic(createImageView);
        createTimeline.setContentDisplay(ContentDisplay.TOP);

        /* edit timeline button */
        Button editTimeline = new Button("Edit timeline", editImageView);
        editTimeline.setStyle("-fx-background-color: #aac0f2");
        editTimeline.setPrefWidth(150);
        editTimeline.setPrefHeight(150);
        editTimeline.setFont(Font.font("Open Sans Light", 18));
        editTimeline.setGraphic(editImageView);
        editTimeline.setContentDisplay(ContentDisplay.TOP);

        /* view timeline button */
        Button viewTimeline = new Button("View timeline", viewImageView);
        viewTimeline.setStyle("-fx-background-color: #aac0f2");
        viewTimeline.setPrefWidth(150);
        viewTimeline.setPrefHeight(150);
        viewTimeline.setFont(Font.font("Open Sans Light", 18));
        viewTimeline.setGraphic(viewImageView);
        viewTimeline.setContentDisplay(ContentDisplay.TOP);
        
        /* Management menu button */
        Button manageApplication = new Button();
        manageApplication.setGraphic(settingsImageView);
        manageApplication.setStyle("-fx-background-color: #587dd1");

        /*
        Sign out button
         */
        Button signOut = new Button("Sign out");
        signOut.setStyle("-fx-background-color: #587dd1");
        signOut.setFont(Font.font("Open Sans", FontPosture.ITALIC, 14));
        signOut.setGraphic(signInImageView);
        signOut.setContentDisplay(ContentDisplay.LEFT);



        signOut.setOnAction(actionEvent -> {
            TimelineMenu.availableTimelines.clear();
            if(UserMain.getIsLogged()==1) {
                UserMain.setIsLogged(0);
                signIn.setText("Sign in");
                signOut.setVisible(false);
                manageApplication.setVisible(false);
                //TODO clear the timeline view menu!!
            }

        });
        /* Button create timeline action */
       createTimeline.setOnAction(actionEvent -> {
          
    	   if(UserMain.getIsLogged()==1) {
	    	   try {
	               window.setScene(TimelineMenu.createTimeline());
	           } catch (FileNotFoundException e) {
	               e.printStackTrace();
	           }
    	   }
    	   
    	   else {
           	alert = new Alert(AlertType.INFORMATION);
   			alert.setTitle("Information Dialog");
   			alert.setHeaderText(null);
   			alert.setContentText("You need to be signed in to do this!");
   			alert.showAndWait();
           }
        });

        /* Button edit timeline action */
        editTimeline.setOnAction(actionEvent -> {
            
        	if(UserMain.getIsLogged()==1) {
	        	try {
	                window.setScene(TimelineMenu.selectTimelineEdit());
	            } catch (IOException | SQLException e) {
	                e.printStackTrace();
	            }
        	}
        	
        	else {
               	alert = new Alert(AlertType.INFORMATION);
       			alert.setTitle("Information Dialog");
       			alert.setHeaderText(null);
       			alert.setContentText("You need to be signed in to do this!");
       			alert.showAndWait();
               }
        });

        /* Button view timeline action */
       viewTimeline.setOnAction(actionEvent -> {
            try {
                window.setScene(TimelineMenu.selectTimelineView());
            } catch (FileNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        });


        signIn.setOnAction(actionEvent -> {
        	try {
        		window.setScene(UserMain.viewAccountCreator());
        	} catch(FileNotFoundException e) {
        		e.printStackTrace();
        	}
        });

        manageApplication.setOnAction(actionEvent -> {

            if(UserMain.getIsLogged()==1) {
                try {
                    window.setScene(ManagementMenu.viewManagementMenu());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            
            else {
            	alert = new Alert(AlertType.INFORMATION);
    			alert.setTitle("Information Dialog");
    			alert.setHeaderText(null);
    			alert.setContentText("You need to be signed in to do this!");
    			alert.showAndWait();
            }

        });
        GridPane pane = new GridPane();
        
        /* Main Menu layout if a user is logged in [Melker Fält 2020-04-27] */ 
        if(UserMain.getIsLogged() == 1) {
        	/* Clear everything */
        	pane.getChildren().clear();
        	
        	signIn.setText(UserMain.getCurrentUserName()); //Sets the "Sign in" text to the current users username.
	        pane.add(logoImageView, 0, 0);
	        pane.add(signIn, 2, 0);
	        GridPane.setHalignment(signIn, HPos.RIGHT);
	
	        pane.add(createTimeline, 0, 1);
	        pane.add(viewTimeline, 1, 1);
	        pane.add(editTimeline, 2, 1);
	        
	        /* The management button is added */
	        pane.add(manageApplication, 2, 2);
	        pane.add(signOut,0,2);
	        GridPane.setHalignment(manageApplication, HPos.RIGHT);
	
	        pane.setAlignment(Pos.CENTER);
	        pane.setPadding(new Insets(25, 10, 10, 10));
	        pane.setHgap(30);
	        pane.setVgap(75);
        }
        
        /* Main Menu layout if no user is logged in [Melker Fält 2020-04-27]*/
        else {
        	/* Clear everything */
        	pane.getChildren().clear();
        	
        	pane.add(logoImageView, 0, 0);
 	        pane.add(signIn, 2, 0);
 	        GridPane.setHalignment(signIn, HPos.RIGHT);
 	
 	        pane.add(createTimeline, 0, 1);
 	        pane.add(viewTimeline, 1, 1);
 	        pane.add(editTimeline, 2, 1);
 	
 	        pane.setAlignment(Pos.CENTER);
 	        pane.setPadding(new Insets(10, 10, 10, 10));
 	        pane.setHgap(30);
 	        pane.setVgap(100); //100
        }
        
        VBox root = new VBox(pane);        //VBox with title and buttons
        root.setAlignment(Pos.CENTER);
        root.setSpacing(50);
        root.setPadding(new Insets(0,20,20,20));
        root.setStyle("-fx-background-color: #587dd1");         //custom color as background
        
        return new Scene(root);
    }

    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        backToMainMenu();
    }
}
