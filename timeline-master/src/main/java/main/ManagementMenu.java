package main; //REMOVE THIS

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Optional;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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

/**
 * @author Melker F�lt
 * @date 2020-04-21
 */

public class ManagementMenu
{
    //Method coverts the textfield input to integer and return int.
    public static int isInt (String tx) {
        int id = Integer.parseInt(tx);
        return id;
    }
    private static int fieldWidth = 200; //The primary length used for fields.
    private static int fieldHeight = 45; //The primary height used for fields.
    private static int currentPage = 1;  //The current page (Admin view only).
    private static String cssStyle = "-fx-border-color: #000000; -fx-border-width: 0.5px; -fx-background-color: #FFFFFF;"
            + "-fx-text-inner-color: #808080;";
    private static Alert alert;
    private static Stage dialogWindow;

    /**
     * A method that closes any opened dialog windows.
     * @throws FileNotFoundException
     */
    public static void closeWindow() throws FileNotFoundException{
        dialogWindow.close();
    }

    /**
     * The main stage for the management menu.
     * @return The menu scene.
     * @throws FileNotFoundException
     */
    public static Scene viewManagementMenu() throws FileNotFoundException{
        /* Title labels */
        Label title = new Label("User Management");
        title.setFont(Font.font("Open Sans", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 22));

        Label titleDelete = new Label("Delete Account");
        titleDelete.setFont(Font.font("Open Sans", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 12));

        Label titlePromote = new Label("Promote Account");
        titlePromote.setFont(Font.font("Open Sans", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 12));

        Label titleUpdateUserName = new Label("Update User's Username");
        titleUpdateUserName.setFont(Font.font("Open Sans", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 12));

        Label titleUpdateEmail = new Label("Update User's Email");
        titleUpdateEmail.setFont(Font.font("Open Sans", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 12));

        Label titleUpdatePassword = new Label("Update User's Password");
        titleUpdatePassword.setFont(Font.font("Open Sans", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 12));

        Label currentPageLabel = new Label(Integer.toString(currentPage));
        currentPageLabel.setFont(Font.font("Open Sans", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 12));


        /* Images */
        Image timelineLogo = new Image(new FileInputStream("graphic/tsiwira.png"));
        ImageView logoIV = new ImageView(timelineLogo);
        logoIV.setFitHeight(75);
        logoIV.setFitWidth(75);

        /*		// SEMI TEMPORARY \\ */

        Image nextPageImg = new Image(new FileInputStream("graphic/next.png"));
        ImageView nextPageIV = new ImageView(nextPageImg);
        nextPageIV.setFitHeight(12);
        nextPageIV.setFitWidth(12);

        Image prevPageImg = new Image(new FileInputStream("graphic/prev.png"));
        ImageView prevPageIV = new ImageView(prevPageImg);
        prevPageIV.setFitHeight(12);
        prevPageIV.setFitWidth(12);

        // Buttons \\

        /* Main menu button */
        Button mainMenuBtn = new Button();
        mainMenuBtn.setGraphic(logoIV);
        mainMenuBtn.setStyle("-fx-background-color: #587dd1");

        /* Increments the current menu page by one (Admin only) */
        Button nextPageBtn = new Button();
        nextPageBtn.setGraphic(nextPageIV);
        //nextPageBtn.setStyle("-fx-background-color: #587dd1");

        /* Reduces the current menu page by one (Admin only) */
        Button prevPageBtn = new Button();
        prevPageBtn.setGraphic(prevPageIV);
        //previousPageBtn.setStyle("-fx-background-color: #587dd1");

        /* Promotion confirmation button */
        Button promoteUserBtn = new Button("Promote user");
        promoteUserBtn.setFont(Font.font("Open Sans", FontWeight.NORMAL,12));
        promoteUserBtn.setPrefSize(fieldWidth, fieldHeight);
        promoteUserBtn.setStyle(cssStyle);

        /* Deletion confirmation button */
        Button deleteUserBtn = new Button("Delete user");
        deleteUserBtn.setFont(Font.font("Open Sans", FontWeight.NORMAL,12));
        deleteUserBtn.setPrefSize(fieldWidth, fieldHeight);
        deleteUserBtn.setStyle(cssStyle);

        /* Name update confirmation button */
        Button updateUserNameBtn = new Button("Update user's username");
        updateUserNameBtn.setFont(Font.font("Open Sans", FontWeight.NORMAL,12));
        updateUserNameBtn.setPrefSize(fieldWidth, fieldHeight);
        updateUserNameBtn.setStyle(cssStyle);

        /* Password update confirmation button */
        Button updatePasswordBtn = new Button("Update user's password");
        updatePasswordBtn.setFont(Font.font("Open Sans", FontWeight.NORMAL,12));
        updatePasswordBtn.setPrefSize(fieldWidth, fieldHeight);
        updatePasswordBtn.setStyle(cssStyle);

        /* Email update confirmation button */
        Button updateEmailBtn = new Button("Update user's email");
        updateEmailBtn.setFont(Font.font("Open Sans", FontWeight.NORMAL,12));
        updateEmailBtn.setPrefSize(fieldWidth, fieldHeight);
        updateEmailBtn.setStyle(cssStyle);

        /* GridPane with text fields for the user to be handled */
        GridPane textFields = new GridPane();
        textFields.setAlignment(Pos.CENTER);
        textFields.setPadding(new Insets(20.0,20.0,20.0,20.0));
        textFields.setHgap(20);
        textFields.setVgap(20);

        /* If the current user is a "normal" user */
        if(UserMain.getIsAdmin()==0) {

            /* Initial Menu Setup (User) */

            textFields.add(deleteUserBtn, 0, 0);
            GridPane.setHalignment(deleteUserBtn, HPos.CENTER);


            textFields.add(updatePasswordBtn, 0, 1);
            GridPane.setHalignment(updatePasswordBtn, HPos.CENTER);


            textFields.add(updateEmailBtn, 0, 2);
            GridPane.setHalignment(updateEmailBtn, HPos.CENTER);

        }

        /* If the current user is an Admin */
        else{

            /* Initial Menu Setup (Admin) */
            textFields.add(promoteUserBtn, 0, 0);
            GridPane.setHalignment(promoteUserBtn, HPos.CENTER);

            textFields.add(deleteUserBtn, 0, 1);
            GridPane.setHalignment(deleteUserBtn, HPos.CENTER);

            textFields.add(updateUserNameBtn, 0, 2);
            GridPane.setHalignment(updateUserNameBtn, HPos.CENTER);

            textFields.add(updatePasswordBtn, 0, 3);
            GridPane.setHalignment(updatePasswordBtn, HPos.CENTER);

            textFields.add(updateEmailBtn, 0, 4);
            GridPane.setHalignment(updateEmailBtn, HPos.CENTER);


        }

        // BUTTON ACTIONS \\

        /* Sends the user back to the main menu when pressed */
        mainMenuBtn.setOnAction(actionEvent -> {
            try {
                MainMenu.backToMainMenu();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        /* Promotes the user with the name given in the TextField */
        promoteUserBtn.setOnAction(actionEvent -> {
            try{
                dialogWindow = new Stage();
                dialogWindow.close();
                dialogWindow.setScene(ManagementMenuDialog.promoteUserDialog());
                dialogWindow.setWidth(350);
                dialogWindow.setHeight(250);
                dialogWindow.setTitle("User Promotion");
                dialogWindow.setResizable(false);
                dialogWindow.show();

            } catch (FileNotFoundException | SQLException e){
                e.printStackTrace();
            }
        });

        /* Deletes the user with the name given in the TextField */
        deleteUserBtn.setOnAction(actionEvent -> {

            /* Only needs to open a dialog window with options if it's the Admin */
            if(UserMain.getIsAdmin() == 1) {
                try{
                    dialogWindow = new Stage();
                    dialogWindow.close();
                    dialogWindow.setScene(ManagementMenuDialog.deleteUserDialog());
                    dialogWindow.setWidth(350);
                    dialogWindow.setHeight(250);
                    dialogWindow.setTitle("User Deletion");
                    dialogWindow.setResizable(false);
                    dialogWindow.show();

                } catch (FileNotFoundException | SQLException e){
                    e.printStackTrace();
                }
            }

            /* Otherwise since it's *THIS* account that will be deleted a simple confirmation will do */
            else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to remove your account?");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == ButtonType.OK) {
                    // REMOVAL FUNCTIONALITY HERE
                    try{
                        if (UserHandler.deleteUser(UserMain.getCurrentUserName())){
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

        });


        /* Updates the user with the names (current/new) given in the respective TextField's */
        updateUserNameBtn.setOnAction(actionEvent -> {
            try{
                dialogWindow = new Stage();
                dialogWindow.close();
                dialogWindow.setScene(ManagementMenuDialog.updateUsernameDialog());
                dialogWindow.setWidth(350);
                dialogWindow.setHeight(250);
                dialogWindow.setTitle("User Renaming");
                dialogWindow.setResizable(false);
                dialogWindow.show();

            } catch (FileNotFoundException | SQLException e){
                e.printStackTrace();
            }
        });

        /* Updates the password of the user with the name given in the TextField */
        updatePasswordBtn.setOnAction(actionEvent -> {
            try{
                dialogWindow = new Stage();
                dialogWindow.close();
                dialogWindow.setScene(ManagementMenuDialog.updatePasswordDialog());
                dialogWindow.setWidth(350);
                dialogWindow.setHeight(250);
                dialogWindow.setTitle("Password Changing");
                dialogWindow.setResizable(false);
                dialogWindow.show();

            } catch (FileNotFoundException | SQLException e){
                e.printStackTrace();
            }
        });

        /* Updates the mail of the user with the name given in the TextField */
        updateEmailBtn.setOnAction(actionEvent -> {
            try{
                dialogWindow = new Stage();
                dialogWindow.close();
                dialogWindow.setScene(ManagementMenuDialog.updateEmailDialog());
                dialogWindow.setWidth(350);
                dialogWindow.setHeight(250);
                dialogWindow.setTitle("Email Changing");
                dialogWindow.setResizable(false);
                dialogWindow.show();

            } catch (FileNotFoundException | SQLException e){
                e.printStackTrace();
            }

        });

        /* Stylized left hand side of the menu */
        VBox emptySpaceLeft = new VBox();
        emptySpaceLeft.setPadding(new Insets(100));

        VBox emptySpaceRight = new VBox();

        /* Adjusting placement depending on user type */
        if(UserMain.getIsAdmin()==0) {
            emptySpaceRight.setPadding(new Insets(25));
        }

        else {
            emptySpaceRight.setPadding(new Insets(15));
        }

        VBox inputFields = new VBox(emptySpaceRight, textFields);
        inputFields.setPadding(new Insets(0));

        inputFields.setAlignment(Pos.TOP_CENTER);

        VBox menuBtn = new VBox(mainMenuBtn);
        menuBtn.setPadding(new Insets(0, 0, 35, 0));
        menuBtn.setAlignment(Pos.BASELINE_CENTER);

        HBox pageBtns = new HBox(prevPageBtn, currentPageLabel, nextPageBtn);
        pageBtns.setAlignment(Pos.CENTER);
        pageBtns.setSpacing(12);

        VBox leftSide = new VBox(menuBtn, title, emptySpaceLeft);
        leftSide.setPadding(new Insets(10, 10, 640, 10));
        leftSide.setStyle("-fx-background-color: #587dd1"); //Sets the background color to blue.
        leftSide.setSpacing(10);
        leftSide.setMaxWidth(220);

        HBox allContent = new HBox(leftSide, inputFields);
        allContent.setSpacing(80);

        /* The scene's root*/
        VBox root = new VBox(allContent);
        root.setAlignment(Pos.BASELINE_LEFT);
		
		/* Used for local instances of ONLY the UserMain menu.
		Scene logRegistrationScene = new Scene (root);
		mainScene.setTitle("User Management");
		mainScene.setScene(logRegistrationScene);
		mainScene.setWidth(640);
		mainScene.setHeight(480);
		mainScene.setResizable(false);
        mainScene.show();
        */


        Scene outputScene = new Scene(root);
        return outputScene;
    }

}
