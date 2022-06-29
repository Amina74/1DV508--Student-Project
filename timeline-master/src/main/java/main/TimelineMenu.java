package main;
import com.sun.tools.javac.Main;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import timelineHandling.EventsHandler;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.stage.FileChooser;
import timeLineDisplay.*;
import timelineHandling.TimelineHandler;
import userHandling.User;
import userHandling.UserHandler;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Created by Bartlomiej Minierski
 * 2020-04-20.
 */
public class TimelineMenu {
    private static boolean onOff;

    private static String imageDirString;     //Variable used to store the image path
    private static String fileName;		      //The image file name
    private static Path imageDirPath;	      //The image file path
    private static Random rng = new Random(); //Randomizer used for image name generation.
    private static Path standardDirPath = Paths.get("eventImages");

    public static String currentTimeline;       	 //current list
    private static String currentTimelineTitle;      // the title of current TimeLine Object for editor

    private static TimeLine currentTimelineObject ;  // current TimeLine Object

    private static String currentBoxTitle ;          // title of current Box Object
    private static String currentBoxDate;			 // date of current Box Object
    private static int currentBoxID ;               // current Box Object

    private static Alert alert; //Used to create alert dialog windows.

    //list of selected timelines in view mode
    private static ObservableList<String> selectedTimelines = FXCollections.observableArrayList();

    //list of available timelines in view mode
    public static ObservableList<String> availableTimelines = FXCollections.observableArrayList();

    //list of available timelines in timeline edit mode
    public static ObservableList<String> availableUsersTimelines = FXCollections.observableArrayList();
    private static ObservableList<TimeLine> availableUsersTimelinesObj = FXCollections.observableArrayList();

    //list of available timelines in event edit mode
    private static ObservableList<String> availablEvents = FXCollections.observableArrayList();
    private static ObservableList<Box> availablEventsObj = FXCollections.observableArrayList();

    private static String cssLightBlue = "-fx-background-color: #aac0f2";
    private static String cssDarkBlue = "-fx-background-color: #587dd1";
    private static String cssStyle = "-fx-border-color: #000000; -fx-border-width: 0.5px; -fx-background-color: #FFFFFF;"
            + "-fx-text-inner-color: #808080;";

    private static int pos = 0; //The ScrollPane's starting position
    private static final int minPos = 0; //The minimum positional value the ScrollPane can have
    private static final int maxPos = 100; //The maximum positional value the ScrollPane can have

    /* Menu to view single timeline (javafx by Bartek) */
    public static Scene viewSingleTimeline() throws FileNotFoundException, SQLException {

        //timelineViewer setup(this is for testing right now)
        //and also for showing the placeholder timeline of the bigbang to present

        TimeLineViewer viewer = new TimeLineViewer();

        /*
         * timelineTitle1 is the name of the timeline belonging to a user
         * and that is chosen to be viewed.
         *  TimelineHandler.viewTimeline will generate a timeline from the database
         *  that needs to be send to the viewer.
         */
        String timelineTitle1 = selectedTimelines.get(0);

//public Box(String title, Image boxImage,  String desc, String boxDate) {
        TimeLine StarWars = new TimeLine("1772-01-01","1840-01-01", "Star Wars", null);
        Box epi1 = new Box("The Phantom Menace",new Image(new FileInputStream("eventImages\\epi1.png")), "The invasion of Naboo and the death of Qui Gon Jinn", "1772-01-01" );
        Box epi2 = new Box("The Attack Of The Clones", new Image(new FileInputStream("eventImages\\epi2.png")),"The beginning of the clone wars and the first battle of Geonosis", "1782-01-01");
        Box epi3 = new Box("Revenge Of The Sith", new Image(new FileInputStream("eventImages\\epi3.png")),"The events of Order 66, the creation of Darth Vader and the fall of the Republic", "1785-01-01");
        Box hanSolo = new Box("Han Solo: a Star Wars story",new Image(new FileInputStream("eventImages\\solo.png")), "The Millenium Falcon makes the kessel run in less then 12 parsecs","1794-01-01");
        Box rogueOne = new Box("Rogue One: a Star Wars story",new Image(new FileInputStream("eventImages\\rogue.png")),"The blue prints of the first Death Star are stolen from the archives on Scarif", "1804-01-01");
        Box epi4 = new Box("A New Hope",new Image(new FileInputStream("eventImages\\epi4.png")),"The battle of Yavin and the destruction of the first Death Star", "1804-01-01");
        Box epi5 = new Box("The Empire Strikes Back",new Image(new FileInputStream("eventImages\\epi5.png")),"Battle of Hoth","1807-01-01");
        Box epi6 = new Box("The Return of the Jedi",new Image(new FileInputStream("eventImages\\epi6.png")),"The Battle of Endor, the destruction of the second Death Star and the death of Darth Vader","1808-01-01");
        Box epi7 = new Box("The Force Awakens",new Image(new FileInputStream("eventImages\\epi7.png")),"Destruction of Star Killer Base and the death of Han Solo","1838-01-01");
        Box epi8 = new Box("The Last Jedi",new Image(new FileInputStream("eventImages\\epi8.png")),"Luke is found on Ahch-To and the death of Luke Skywalker and Supreme Leader Snoke","1838-01-01");
        Box epi9 = new Box("The Rise Of Skywalker",new Image(new FileInputStream("eventImages\\epi9.png")),"The battle of Exegol and the death of Darth Sidius","1839-01-01");
        StarWars.addBox(epi1);
        StarWars.addBox(epi2);
        StarWars.addBox(epi3);
        StarWars.addBox(hanSolo);
        StarWars.addBox(rogueOne);
        StarWars.addBox(epi4);
        StarWars.addBox(epi5);
        StarWars.addBox(epi6);
        StarWars.addBox(epi7);
        StarWars.addBox(epi8);
        StarWars.addBox(epi9);

        Image firstCar = new Image(new FileInputStream("eventImages\\T-Ford.png"));
        Image firstPhone = new Image(new FileInputStream("eventImages\\First Telephone.png"));
        Image firstLightBulb = new Image(new FileInputStream("eventImages\\First Practical light bulb.png"));
        Image theGreatStrike = new Image(new FileInputStream("eventImages\\The Great Strike.png"));
        Image firstFlight = new Image(new FileInputStream("eventImages\\first Powered flight.png"));
        Image firstFlashlight = new Image(new FileInputStream("eventImages\\First Flashlight.png"));
        Image firstSteamTurbine = new Image(new FileInputStream("eventImages\\FirstSteam.png"));

        TimeLine industrialRevolution = new TimeLine("1870-01-01", "1914-01-01",
                "Second Industrial Revolution");

        industrialRevolution.addBox(new Box("The First Telephone", firstPhone, "On this day Alexander Graham Bell " +
                "(Edinburgh, Scotland) had created the first telephone.", "1876-04-07"));
        industrialRevolution.addBox(new Box("The Great Railroad Strike",theGreatStrike, "On this day in Martinsburg, West Virginia a" +
                " strike erupted after the Baltimore and Ohio Railroad company cut wages for their workers for the third time in a year." +
                " The strike was supported by circa 100 000 workers and it lasted for 69 days.", "1877-07-14"));
        industrialRevolution.addBox(new Box("The First Practical Incandescent Light Bulb", firstLightBulb, "On this day Thomas Edison " +
                "had created the first practical incandescent light bulb. This allowed factories to stay open even " +
                "when it was dark outside.", "1879-11-04"));
        industrialRevolution.addBox(new Box("The First Modern Steam Turbine", firstSteamTurbine, "This year " +
                "the Irish engineer called Charles Parsons created the first modern steam turbine. This turbine was able " +
                "to generate 7.5 kW. This steam turbine made it possible for cheap electricity production and " +
                "revolutionized naval transport. Charles steam turbine turned out to be very easy to scale up. The year" +
                " 1910 a industrialized version that was able to produce 250 kW had been created. (The picture is of the " +
                "1910 version)", "1884-01-01"));
        industrialRevolution.addBox(new Box("The First Flashlight", firstFlashlight, "Around this date the first " +
                "electrically powered flashlight was created by a British inventor called David Misell. The flashlight " +
                "became highly popular and especially so within the police since the flashlight made it possible to get" +
                " light by the simple press of a button. The flashlight also was odorless and greatly reduced the risk " +
                "of a fire compared to the lantern.", "1899-01-10"));
        industrialRevolution.addBox(new Box("The First Powered Flight",firstFlight, "On this day the two brothers, Orville and Wilbur Wright" +
                " were the first to fly an engine powered aircraft. On the first flight they reached an altitude of 37" +
                " meters.", "1903-12-17"));
        industrialRevolution.addBox(new Box("The First affordable Automobile", firstCar,"On this day Henry Ford (Greendfield " +
                "Township, Michigan) started production on the car model T-Ford. The T-Ford was mass produced on an " +
                "assembly line. These methods of production is still used to this day. In total there were 15 million" +
                " T-Fords created and the price eventually dropped to 290$ for a brand new T-Ford", "1908-10-01"));

        if(UserMain.getIsLogged()==0) {
            if (selectedTimelines.get(0).equals("Star Wars")) {
                viewer.addTimeLine(StarWars);
            } else if (selectedTimelines.get(0).equals("Second Industrial Revolution")) {
                viewer.addTimeLine(industrialRevolution);
            }
        } else if(UserMain.getIsLogged() == 1)
            viewer.addTimeLine(TimelineHandler.viewTimeline(timelineTitle1));

        /* Logo with back to main menu function*/
        Image logo = new Image(new FileInputStream("graphic/tsiwira.png"));
        ImageView logoImageView = new ImageView(logo);
        logoImageView.setFitWidth(50);
        logoImageView.setFitHeight(50);
        Button logoButton = new Button();
        logoButton.setStyle("-fx-background-color: #aac0f2");
        logoButton.setGraphic(logoImageView);
        HBox logoBox = new HBox(logoButton);
        logoBox.setAlignment(Pos.CENTER_LEFT);

        /* sign in button inside HBox */
        Button signIn = new Button();
        if(UserMain.getIsLogged() == 1){
            signIn.setText(UserMain.getCurrentUserName());    //if logged = current username
        } else {
            signIn.setText("username");       //if not logged = "username"
        }
        signIn.setStyle("-fx-background-color: #aac0f2");
        signIn.setFont(Font.font("Open Sans Light Italic", 13.7));
        HBox signInBox = new HBox(signIn);
        signInBox.setAlignment(Pos.CENTER);
        signInBox.setPrefHeight(60);

        /* Opens the sign in questionnaire */
        signIn.setOnAction(actionEvent -> {
            try {
                MainMenu.window.setScene(UserMain.viewAccountCreator());
                MainMenu.window.setMaximized(false);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        /* Back to main menu */
        logoButton.setOnAction(actionEvent -> {
            try {
                MainMenu.backToMainMenu();
                MainMenu.window.setMaximized(false);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        Label titel = new Label(timelineTitle1);
        titel.setMaxWidth(1500);
        titel.setFont(Font.font("Open Sans",FontWeight.BOLD,FontPosture.ITALIC, 48));
        titel.setAlignment(Pos.CENTER);

        BorderPane line1 = new BorderPane();
        line1.setLeft(logoBox);
        line1.setRight(signInBox);

        BorderPane line2 = new BorderPane();
        line2.setCenter(titel);

        BorderPane top = new BorderPane();
        top.setTop(line1);
        top.setBottom(line2);

        VBox theTimeLine = new VBox();
        //printing of the actual canvas and the boxes of the timeline
        if(UserMain.getIsLogged()==1) {
            TimeLineCanvas[] canvas = viewer.getCanvas();
            theTimeLine.setPrefHeight(550);
            theTimeLine.setStyle("-fx-background-color: #aac0f2");
            for (TimeLineCanvas canva : canvas) {
                theTimeLine.getChildren().add(canva.getCanvas());
            }
        }
        else if(UserMain.getIsLogged()==0) {
            TimeLineCanvas[] canvas = viewer.getCanvasOffline();
            theTimeLine.setPrefHeight(550);
            theTimeLine.setStyle("-fx-background-color: #aac0f2");
            for (TimeLineCanvas canva : canvas) {
                theTimeLine.getChildren().add(canva.getCanvas());
            }
        }

        /* BorderPane with timeline and scroll */
        ScrollPane scrollPane = new ScrollPane(theTimeLine);
        scrollPane.setStyle("-fx-background: #aac0f2;" +
                "-fx-border-color: #aac0f2;");
        scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPadding(new Insets(250,0,0,0));

        /* Used to move the menu horizontally based on mouse wheel scrolling */
        scrollPane.setOnScroll(new EventHandler<ScrollEvent>() {
            public void handle(ScrollEvent event) {
                if (event.getDeltaY() > 0)
                    scrollPane.setHvalue(pos == minPos ? minPos : pos--);
                else
                    scrollPane.setHvalue(pos == maxPos ? maxPos : pos++);

            }
        });

        /* back button  */
        Image arrowImage = new Image(new FileInputStream("graphic/arrow.png"));
        ImageView arrowImageView = new ImageView(arrowImage);
        arrowImageView.setFitWidth(50);
        arrowImageView.setFitHeight(50);
        arrowImageView.setStyle("-fx-color: #000000");

        Button backBtn = new Button();
        backBtn.setStyle(cssLightBlue);
        backBtn.setGraphic(arrowImageView);

        backBtn.setOnAction(actionEvent -> {
            try {
                MainMenu.window.setScene(TimelineMenu.selectTimelineView());
                MainMenu.window.setMaximized(false);
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        });

        /* root with top buttons and BorderPane */
        BorderPane root = new BorderPane();
        root.setTop(top);
        root.setCenter(scrollPane);
        root.setBottom(backBtn);
        root.setStyle("-fx-background-color: #aac0f2");
        root.setPadding(new Insets(40,40,0,40));


        return new Scene(root);
    }

    /* Menu to view multiple timeline (javafx by Bartek) */
    public static Scene viewMultiTimeline() throws FileNotFoundException, SQLException {

        //timelineViewer
        TimeLineViewer viewer = new TimeLineViewer();


        TimeLine StarWars = new TimeLine("1772-01-01","1840-01-01", "Star Wars", null);
        Box epi1 = new Box("The Phantom Menace",new Image(new FileInputStream("eventImages\\epi1.png")), "The invasion of Naboo and the death of Qui Gon Jinn", "1772-01-01" );
        Box epi2 = new Box("The Attack Of The Clones", new Image(new FileInputStream("eventImages\\epi2.png")),"The beginning of the clone wars and the first battle of Geonosis", "1782-01-01");
        Box epi3 = new Box("Revenge Of The Sith", new Image(new FileInputStream("eventImages\\epi3.png")),"The events of Order 66, the creation of Darth Vader and the fall of the Republic", "1785-01-01");
        Box hanSolo = new Box("Han Solo: a Star Wars story",new Image(new FileInputStream("eventImages\\solo.png")), "The Millenium Falcon makes the kessel run in less then 12 parsecs","1794-01-01");
        Box rogueOne = new Box("Rogue One: a Star Wars story",new Image(new FileInputStream("eventImages\\rogue.png")),"The blue prints of the first Death Star are stolen from the archives on Scarif", "1804-01-01");
        Box epi4 = new Box("A New Hope",new Image(new FileInputStream("eventImages\\epi4.png")),"The battle of Yavin and the destruction of the first Death Star", "1804-01-01");
        Box epi5 = new Box("The Empire Strikes Back",new Image(new FileInputStream("eventImages\\epi5.png")),"Battle of Hoth","1807-01-01");
        Box epi6 = new Box("The Return of the Jedi",new Image(new FileInputStream("eventImages\\epi6.png")),"The Battle of Endor, the destruction of the second Death Star and the death of Darth Vader","1808-01-01");
        Box epi7 = new Box("The Force Awakens",new Image(new FileInputStream("eventImages\\epi7.png")),"Destruction of Star Killer Base and the death of Han Solo","1838-01-01");
        Box epi8 = new Box("The Last Jedi",new Image(new FileInputStream("eventImages\\epi8.png")),"Luke is found on Ahch-To and the death of Luke Skywalker and Supreme Leader Snoke","1838-01-01");
        Box epi9 = new Box("The Rise Of Skywalker",new Image(new FileInputStream("eventImages\\epi9.png")),"The battle of Exegol and the death of Darth Sidius","1839-01-01");
        StarWars.addBox(epi1);
        StarWars.addBox(epi2);
        StarWars.addBox(epi3);
        StarWars.addBox(hanSolo);
        StarWars.addBox(rogueOne);
        StarWars.addBox(epi4);
        StarWars.addBox(epi5);
        StarWars.addBox(epi6);
        StarWars.addBox(epi7);
        StarWars.addBox(epi8);
        StarWars.addBox(epi9);

        Image firstCar = new Image(new FileInputStream("eventImages\\T-Ford.png"));
        Image firstPhone = new Image(new FileInputStream("eventImages\\First Telephone.png"));
        Image firstLightBulb = new Image(new FileInputStream("eventImages\\First Practical light bulb.png"));
        Image theGreatStrike = new Image(new FileInputStream("eventImages\\The Great Strike.png"));
        Image firstFlight = new Image(new FileInputStream("eventImages\\first Powered flight.png"));
        Image firstFlashlight = new Image(new FileInputStream("eventImages\\First Flashlight.png"));
        Image firstSteamTurbine = new Image(new FileInputStream("eventImages\\FirstSteam.png"));

        TimeLine industrialRevolution = new TimeLine("1870-01-01", "1914-01-01",
                "Second Industrial Revolution");

        industrialRevolution.addBox(new Box("The First Telephone", firstPhone, "On this day Alexander Graham Bell " +
                "(Edinburgh, Scotland) had created the first telephone.", "1876-04-07"));
        industrialRevolution.addBox(new Box("The Great Railroad Strike",theGreatStrike, "On this day in Martinsburg, West Virginia a" +
                " strike erupted after the Baltimore and Ohio Railroad company cut wages for their workers for the third time in a year." +
                " The strike was supported by circa 100 000 workers and it lasted for 69 days.", "1877-07-14"));
        industrialRevolution.addBox(new Box("The First Practical Incandescent Light Bulb", firstLightBulb, "On this day Thomas Edison " +
                "had created the first practical incandescent light bulb. This allowed factories to stay open even " +
                "when it was dark outside.", "1879-11-04"));
        industrialRevolution.addBox(new Box("The First Modern Steam Turbine", firstSteamTurbine, "This year " +
                "the Irish engineer called Charles Parsons created the first modern steam turbine. This turbine was able " +
                "to generate 7.5 kW. This steam turbine made it possible for cheap electricity production and " +
                "revolutionized naval transport. Charles steam turbine turned out to be very easy to scale up. The year" +
                " 1910 a industrialized version that was able to produce 250 kW had been created. (The picture is of the " +
                "1910 version)", "1884-01-01"));
        industrialRevolution.addBox(new Box("The First Flashlight", firstFlashlight, "Around this date the first " +
                "electrically powered flashlight was created by a British inventor called David Misell. The flashlight " +
                "became highly popular and especially so within the police since the flashlight made it possible to get" +
                " light by the simple press of a button. The flashlight also was odorless and greatly reduced the risk " +
                "of a fire compared to the lantern.", "1899-01-10"));
        industrialRevolution.addBox(new Box("The First Powered Flight",firstFlight, "On this day the two brothers, Orville and Wilbur Wright" +
                " were the first to fly an engine powered aircraft. On the first flight they reached an altitude of 37" +
                " meters.", "1903-12-17"));
        industrialRevolution.addBox(new Box("The First affordable Automobile", firstCar,"On this day Henry Ford (Greendfield " +
                "Township, Michigan) started production on the car model T-Ford. The T-Ford was mass produced on an " +
                "assembly line. These methods of production is still used to this day. In total there were 15 million" +
                " T-Fords created and the price eventually dropped to 290$ for a brand new T-Ford", "1908-10-01"));

        if(UserMain.getIsLogged()==0) {
            for (String current: selectedTimelines) {
                if (current.equals("Star Wars")) {
                    viewer.addTimeLine(StarWars);
                } else if (current.equals("Second Industrial Revolution")) {
                    viewer.addTimeLine(industrialRevolution);
                }
            }
        } else if(UserMain.getIsLogged() == 1) {

            //go through each selected timeline and displays them
            for(int i = 0; i < selectedTimelines.size();i++){
                String current = selectedTimelines.get(i);
                viewer.addTimeLine(TimelineHandler.viewTimeline(current));
            }
        }

        /* Logo with back to main menu function*/
        Image logo = new Image(new FileInputStream("graphic/tsiwira.png"));
        ImageView logoImageView = new ImageView(logo);
        logoImageView.setFitWidth(50);
        logoImageView.setFitHeight(50);
        Button logoButton = new Button();
        logoButton.setStyle(cssLightBlue);
        logoButton.setGraphic(logoImageView);
        HBox logoBox = new HBox(logoButton);
        logoBox.setAlignment(Pos.CENTER_LEFT);

        /* sign in button inside HBox */
        Button signIn = new Button();
        if(UserMain.getIsLogged() == 1){
            signIn.setText(UserMain.getCurrentUserName());    //if logged = current username
        } else {
            signIn.setText("Username");       //if not logged = "Username"
        }
        signIn.setStyle("-fx-background-color: #aac0f2");
        signIn.setFont(Font.font("Open Sans Light Italic", 13.7));
        HBox signInBox = new HBox(signIn);
        signInBox.setAlignment(Pos.CENTER);
        signInBox.setPrefHeight(60);


        /* Opens the sign in questionnaire */
        signIn.setOnAction(actionEvent -> {
            try {
                MainMenu.window.setScene(UserMain.viewAccountCreator());
                MainMenu.window.setMaximized(false);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        /* Back to main menu */
        logoButton.setOnAction(actionEvent -> {
            try {
                MainMenu.backToMainMenu();
                MainMenu.window.setMaximized(false);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        /* BorderPane with logo and signIn buttons */
        BorderPane top = new BorderPane();
        top.setLeft(logoBox);
        top.setRight(signIn);
        top.prefHeight(60);

        VBox vboxWithTimelines = new VBox();
        vboxWithTimelines.setSpacing(40);
        vboxWithTimelines.setStyle(cssLightBlue);
        vboxWithTimelines.setAlignment(Pos.TOP_LEFT);
        vboxWithTimelines.setMinHeight(700);

        //printing of the actual canvas and the boxes of the timeline
        if (UserMain.getIsLogged() == 0) {
            TimeLineCanvas[] canvas = viewer.getCanvasOffline();
            for (TimeLineCanvas canva : canvas) {
                vboxWithTimelines.getChildren().add(canva.getCanvas());
            }
        } else if (UserMain.getIsLogged() == 1) {
            TimeLineCanvas[] canvas = viewer.getCanvas();
            for (TimeLineCanvas canva : canvas) {
                vboxWithTimelines.getChildren().add(canva.getCanvas());
            }
        }


        ScrollPane scrollPane = new ScrollPane(vboxWithTimelines);
        scrollPane.setStyle("-fx-background: #aac0f2;" +
                "-fx-border-color: #aac0f2;"); //DON'T CHANGE THE STYLE AGAIN!
        scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setPadding(new Insets(20,20,50,20));

        /* Used to move the menu horizontally based on mouse wheel scrolling */
        scrollPane.setOnScroll(new EventHandler<ScrollEvent>() {
            public void handle(ScrollEvent event) {
                if (event.getDeltaY() > 0)
                    scrollPane.setHvalue(pos == minPos ? minPos : pos--);
                else
                    scrollPane.setHvalue(pos == maxPos ? maxPos : pos++);

            }
        });

        /* BorderPane with timeline and scroll */
        VBox showTimelines = new VBox(vboxWithTimelines,scrollPane);
        showTimelines.setStyle(cssLightBlue);
        showTimelines.setPadding(new Insets(0,10,0,10));


        /* back button  */
        Image arrowImage = new Image(new FileInputStream("graphic/arrow.png"));
        ImageView arrowImageView = new ImageView(arrowImage);
        arrowImageView.setFitWidth(50);
        arrowImageView.setFitHeight(50);
        arrowImageView.setStyle("-fx-color: #000000");

        Button backBtn = new Button();
        backBtn.setStyle(cssLightBlue);
        backBtn.setGraphic(arrowImageView);

        backBtn.setOnAction(actionEvent -> {
            try {
                MainMenu.window.setScene(TimelineMenu.selectTimelineView());
                MainMenu.window.setMaximized(false);
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        });


        /* root with top buttons and BorderPane */
        BorderPane root = new BorderPane();
        root.setTop(top);
        root.setCenter(scrollPane);
        root.setBottom(backBtn);
        root.setStyle("-fx-background-color: #aac0f2");


        return new Scene(root);
    }

    /* Menu to create timeline */
    public static Scene createTimeline() throws FileNotFoundException {
        /* Logo with back to main menu function*/
        Image logo = new Image(new FileInputStream("graphic/tsiwira.png"));
        ImageView logoImageView = new ImageView(logo);
        logoImageView.setFitWidth(70);
        logoImageView.setFitHeight(70);
        Button logoButton = new Button();
        logoButton.setStyle("-fx-background-color: #587dd1");
        logoButton.setGraphic(logoImageView);
        HBox logoBox = new HBox(logoButton);
        logoBox.setAlignment(Pos.TOP_CENTER);

        /* Back to main menu */
        logoButton.setOnAction(actionEvent -> {
            try {
                MainMenu.backToMainMenu();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        /* Timeline title */
        Label timelineTitle = new Label("Timeline title:");
        timelineTitle.setFont(Font.font("Open Sans Bold", 18));
        timelineTitle.setPadding(new Insets(0, 10, 0, 10));
        TextField titleField = new TextField();
        titleField.setStyle("-fx-border-color: #000000; -fx-border-width: 0.5px; -fx-background-color: #FFFFFF");

        /* First Combobox with A.D and B.C. */
        ComboBox<String> chooseEraStart = new ComboBox<>();
        chooseEraStart.getItems().addAll("A.D.", "B.C.");
        chooseEraStart.setValue("A.D.");
        chooseEraStart.setStyle("-fx-font: 12px \"Open Sans Bold\";");

        /* Second Combobox with A.D and B.C. */
        ComboBox<String> chooseEraEnd = new ComboBox<>();
        chooseEraEnd.getItems().addAll("A.D.", "B.C.");
        chooseEraEnd.setValue("A.D.");
        chooseEraEnd.setStyle("-fx-font: 12px \"Open Sans Bold\";");

        /* Timeline start date label and field */
        Label startTimeline = new Label("Timeline start date:");
        startTimeline.setFont(Font.font("Open Sans Bold", 18));
        startTimeline.setPadding(new Insets(0, 10, 0, 10));
        TextField startDateField = new TextField();
        startDateField.setStyle("-fx-border-color: #000000; -fx-border-width: 0.5px; -fx-background-color: #FFFFFF");

        /* Timeline end date label and field*/
        Label endTimeline = new Label("Timeline end date:");
        endTimeline.setFont(Font.font("Open Sans Bold", 18));
        endTimeline.setPadding(new Insets(0, 10, 0, 10));
        TextField endDateField = new TextField();
        endDateField.setStyle("-fx-border-color: #000000; -fx-border-width: 0.5px; -fx-background-color: #FFFFFF");

        /* Timeline keyword label and field */
        Label keywordLabel = new Label("Keyword:");
        keywordLabel.setFont(Font.font("Open Sans Bold", 18));
        keywordLabel.setPadding(new Insets(0, 10, 0, 10));
        TextField keywordField = new TextField();
        keywordField.setStyle("-fx-border-color: #000000; -fx-border-width: 0.5px; -fx-background-color: #FFFFFF");


        /* Grid Pane with Labels, TextFields and ComboBoxes */
        GridPane pane = new GridPane();
        pane.add(timelineTitle, 0, 1);
        pane.add(titleField, 1, 1);
        pane.add(startTimeline, 0, 2);
        pane.add(startDateField, 1, 2);
        pane.add(chooseEraStart, 2, 2);
        pane.add(endTimeline, 0, 3);
        pane.add(endDateField, 1, 3);
        pane.add(chooseEraEnd, 2, 3);
        pane.add(keywordLabel, 0, 4);
        pane.add(keywordField, 1, 4);

        pane.setVgap(5);
        pane.setPadding(new Insets(10, 10, 10, 10));

        /* Create Timeline button with custom size and background */
        Button createButton = new Button("Create Timeline");
        createButton.setStyle("-fx-background-color: #aac0f2");
        createButton.setPrefSize(150, 50);

        HBox gridPaneBox = new HBox(pane);
        gridPaneBox.setAlignment(Pos.CENTER);
        HBox buttonBox = new HBox(createButton);
        buttonBox.setAlignment(Pos.CENTER);

        createButton.setOnAction(actionEvent -> {
            boolean titleChecker = false, endDateChecker = false, startDateChecker = false, keywordChecker = false;
            //Timeline viewer for tests
            // SQL magic :) i final version

            if (TimelineCreator.verifyTitle(titleField.getText())) { // Title is OK.
                titleChecker = true;
            } else if (!TimelineCreator.verifyTitle(titleField.getText())) { // Title is NOT OK.
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Information Dialog");
                alert.setContentText("The Title is either empty or too long.");
                alert.showAndWait();
            }

            if (chooseEraStart.getValue().equals("B.C.")) { // Start Date B.C
                if (TimelineCreator.verifyBCDate(startDateField.getText())) { // Start date is OK.
                    startDateChecker = true;
                } else if (!TimelineCreator.verifyBCDate(startDateField.getText())) { // Start date is NOT OK.
                    alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("Information Dialog");
                    alert.setContentText("Start date is set to B.C. Hence the start date will only allow a year from " +
                            "four digit up until 11 digits long. (No month or day dates).");
                    alert.showAndWait();
                }

            } else if (chooseEraStart.getValue().equals("A.D.")) { // Start date A.C
                if (TimelineCreator.verifyADDate(startDateField.getText())) { // Start date is OK.
                    startDateChecker = true;
                } else if (!TimelineCreator.verifyADDate(startDateField.getText())) { // Start date is NOT OK.
                    alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("Information Dialog");
                    alert.setContentText("Either the dates are invalid or it doesn't match the (YYYY-MM-DD) format.");
                    alert.showAndWait();
                }
            }

            if (chooseEraEnd.getValue().equals("B.C.")) { // End date B.C
                if (TimelineCreator.verifyBCDate(endDateField.getText())) { // End date is OK.
                    endDateChecker = true;
                } else if (!TimelineCreator.verifyBCDate(endDateField.getText())) { // End date is NOT OK.
                    alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("Information Dialog");
                    alert.setContentText("End date is set to B.C. Hence the end date will only allow a (year) from " +
                            "four digit up until 11 digits long. (No month or day dates).");
                    alert.showAndWait();
                }

            } else if (chooseEraEnd.getValue().equals("A.D.")) { // End date A.C
                if (TimelineCreator.verifyADDate(endDateField.getText())) { // End date is OK.
                    endDateChecker = true;
                } else if (!TimelineCreator.verifyADDate(endDateField.getText())) { // End date is NOT OK.
                    alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("Information Dialog");
                    alert.setContentText("Either the dates are invalid or it doesn't match the (YYYY-MM-DD) format.");
                    alert.showAndWait();
                }
            }

            if(!keywordField.getText().isEmpty() && !keywordField.getText().contains(" ")) { // Not empty and contains zero spaces.
                keywordChecker = true;
            } else if(!keywordField.getText().isEmpty() && keywordField.getText().contains(" ")) { // Not empty and contains spaces.
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Information Dialog");
                alert.setContentText("The keyword can't contain any spaces!");
                alert.showAndWait();
            }

            if (titleChecker && startDateChecker && endDateChecker && keywordChecker) { // Everything is OK so create timeline here.
                TimeLine Timeline;

                if(!keywordField.getText().isEmpty()) { // If the field isn't empty, add said keyword.
                    Timeline = new TimeLine(startDateField.getText(), endDateField.getText(), titleField.getText(), keywordField.getText());

                }

                else { // Otherwise create a timeline without one.
                    Timeline = new TimeLine(startDateField.getText(), endDateField.getText(), titleField.getText());
                }

                titleField.clear();
                startDateField.clear();
                endDateField.clear();
                keywordField.clear();
                TimelineHandler.addTimeline(Timeline);

                /* Redirects the user to the editing meny upon successfull creation of a Timeline */
                MainMenu.goToEditMenu();
            }

        });

        VBox rightSide = new VBox(gridPaneBox, buttonBox);
        HBox root = new HBox(logoBox, rightSide);

        StackPane stackPane = new StackPane(root);      //StackPane for custom background
        stackPane.setStyle("-fx-background-color: #587dd1");
        return new Scene(stackPane);

    }

    /* Scene with selection of timelines in view mode */
    public static Scene selectTimelineView() throws FileNotFoundException, SQLException {

        TimeLine StarWars = new TimeLine("1772-01-01","1840-01-01", "Star Wars", null);
        Box epi1 = new Box("The Phantom Menace",new Image(new FileInputStream("eventImages\\epi1.png")), "The invasion of Naboo and the death of Qui Gon Jinn", "1772-01-01" );
        Box epi2 = new Box("The Attack Of The Clones", new Image(new FileInputStream("eventImages\\epi2.png")),"The beginning of the clone wars and the first battle of Geonosis", "1782-01-01");
        Box epi3 = new Box("Revenge Of The Sith", new Image(new FileInputStream("eventImages\\epi3.png")),"The events of Order 66, the creation of Darth Vader and the fall of the Republic", "1785-01-01");
        Box hanSolo = new Box("Han Solo: a Star Wars story",new Image(new FileInputStream("eventImages\\solo.png")), "The Millenium Falcon makes the kessel run in less then 12 parsecs","1794-01-01");
        Box rogueOne = new Box("Rogue One: a Star Wars story",new Image(new FileInputStream("eventImages\\rogue.png")),"The blue prints of the first Death Star are stolen from the archives on Scarif", "1804-01-01");
        Box epi4 = new Box("A New Hope",new Image(new FileInputStream("eventImages\\epi4.png")),"The battle of Yavin and the destruction of the first Death Star", "1804-01-01");
        Box epi5 = new Box("The Empire Strikes Back",new Image(new FileInputStream("eventImages\\epi5.png")),"Battle of Hoth","1807-01-01");
        Box epi6 = new Box("The Return of the Jedi",new Image(new FileInputStream("eventImages\\epi6.png")),"The Battle of Endor, the destruction of the second Death Star and the death of Darth Vader","1808-01-01");
        Box epi7 = new Box("The Force Awakens",new Image(new FileInputStream("eventImages\\epi7.png")),"Destruction of Star Killer Base and the death of Han Solo","1838-01-01");
        Box epi8 = new Box("The Last Jedi",new Image(new FileInputStream("eventImages\\epi8.png")),"Luke is found on Ahch-To and the death of Luke Skywalker and Supreme Leader Snoke","1838-01-01");
        Box epi9 = new Box("The Rise Of Skywalker",new Image(new FileInputStream("eventImages\\epi9.png")),"The battle of Exegol and the death of Darth Sidius","1839-01-01");
        StarWars.addBox(epi1);
        StarWars.addBox(epi2);
        StarWars.addBox(epi3);
        StarWars.addBox(hanSolo);
        StarWars.addBox(rogueOne);
        StarWars.addBox(epi4);
        StarWars.addBox(epi5);
        StarWars.addBox(epi6);
        StarWars.addBox(epi7);
        StarWars.addBox(epi8);
        StarWars.addBox(epi9);

        Image thePhantom = new Image(new FileInputStream("eventImages\\epi1.png"));

        Image firstCar = new Image(new FileInputStream("eventImages\\T-Ford.png"));
        Image firstPhone = new Image(new FileInputStream("eventImages\\First Telephone.png"));
        Image firstLightBulb = new Image(new FileInputStream("eventImages\\First Practical light bulb.png"));
        Image theGreatStrike = new Image(new FileInputStream("eventImages\\The Great Strike.png"));
        Image firstFlight = new Image(new FileInputStream("eventImages\\first Powered flight.png"));
        Image firstFlashlight = new Image(new FileInputStream("eventImages\\First Flashlight.png"));
        Image firstSteamTurbine = new Image(new FileInputStream("eventImages\\FirstSteam.png"));

        TimeLine industrialRevolution = new TimeLine("1870-01-01", "1914-01-01",
                "Second Industrial Revolution");

        industrialRevolution.addBox(new Box("The First Telephone", firstPhone, "On this day Alexander Graham Bell " +
                "(Edinburgh, Scotland) had created the first telephone.", "1876-04-07"));
        industrialRevolution.addBox(new Box("The Great Railroad Strike",theGreatStrike, "On this day in Martinsburg, West Virginia a" +
                " strike erupted after the Baltimore and Ohio Railroad company cut wages for their workers for the third time in a year." +
                " The strike was supported by circa 100 000 workers and it lasted for 69 days.", "1877-07-14"));
        industrialRevolution.addBox(new Box("The First Practical Incandescent Light Bulb", firstLightBulb, "On this day Thomas Edison " +
                "had created the first practical incandescent light bulb. This allowed factories to stay open even " +
                "when it was dark outside.", "1879-11-04"));
        industrialRevolution.addBox(new Box("The First Modern Steam Turbine", firstSteamTurbine, "This year " +
                "the Irish engineer called Charles Parsons created the first modern steam turbine. This turbine was able " +
                "to generate 7.5 kW. This steam turbine made it possible for cheap electricity production and " +
                "revolutionized naval transport. Charles steam turbine turned out to be very easy to scale up. The year" +
                " 1910 a industrialized version that was able to produce 250 kW had been created. (The picture is of the " +
                "1910 version)", "1884-01-01"));
        industrialRevolution.addBox(new Box("The First Flashlight", firstFlashlight, "Around this date the first " +
                "electrically powered flashlight was created by a British inventor called David Misell. The flashlight " +
                "became highly popular and especially so within the police since the flashlight made it possible to get" +
                " light by the simple press of a button. The flashlight also was odorless and greatly reduced the risk " +
                "of a fire compared to the lantern.", "1899-01-10"));
        industrialRevolution.addBox(new Box("The First Powered Flight",firstFlight, "On this day the two brothers, Orville and Wilbur Wright" +
                " were the first to fly an engine powered aircraft. On the first flight they reached an altitude of 37" +
                " meters.", "1903-12-17"));
        industrialRevolution.addBox(new Box("The First affordable Automobile", firstCar,"On this day Henry Ford (Greendfield " +
                "Township, Michigan) started production on the car model T-Ford. The T-Ford was mass produced on an " +
                "assembly line. These methods of production is still used to this day. In total there were 15 million" +
                " T-Fords created and the price eventually dropped to 290$ for a brand new T-Ford", "1908-10-01"));


        availableUsersTimelines.clear();
        selectedTimelines.clear();

        availableUsersTimelines.addAll(TimelineHandler.viewTimelineList());
        if (UserMain.getIsLogged()==0)
            availableUsersTimelines.clear();
        availableUsersTimelines.addAll("Star Wars", "Second Industrial Revolution");

        /* List containing all of the dynamically created buttons */
        List<Button> timelineBtnList = new ArrayList<>();

        List<Button> searchResultBtnList = new ArrayList<Button>(); //TODO remove test list

        /* Labels */
        Label titleLabel = new Label("All Timelines");
        titleLabel.setFont(Font.font("Open Sans", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 22));

        /* Text fields */
        TextField keywordSearchField = new TextField();
        keywordSearchField.setPrefSize(125, 25);
        keywordSearchField.setStyle(cssStyle);
        keywordSearchField.setPromptText("Search timeline");

        /* Images */
        Image logoImage = new Image(new FileInputStream("graphic/tsiwira.png"));
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitWidth(40);
        logoImageView.setFitHeight(40);

        Image arrowImage = new Image(new FileInputStream("graphic/arrow.png"));
        ImageView arrowImageView = new ImageView(arrowImage);
        arrowImageView.setFitWidth(50);
        arrowImageView.setFitHeight(50);
        arrowImageView.setStyle("-fx-color: #000000");

        Image signInImage = new Image(new FileInputStream("graphic/signin.png"));
        ImageView signInImageView = new ImageView(signInImage);
        signInImageView.setFitHeight(30);
        signInImageView.setFitWidth(30);

        Image loopImage = new Image(new FileInputStream("graphic/loop.png"));
        ImageView loopImageView = new ImageView(loopImage);
        loopImageView.setFitHeight(17);
        loopImageView.setFitWidth(20);

        /* Buttons */
        Button backBtn = new Button();
        backBtn.setStyle(cssLightBlue);
        backBtn.setGraphic(arrowImageView);

        Button logoBtn = new Button();
        logoBtn.setStyle(cssLightBlue);
        logoBtn.setGraphic(logoImageView);

        Button searchBtn = new Button();
        searchBtn.setPrefSize(15, 25);
        searchBtn.setStyle(cssStyle);
        searchBtn.setGraphic(loopImageView);

        Button clearBtn = new Button("Clear");
        clearBtn.setPrefSize(75, 25);
        clearBtn.setStyle(cssStyle);
        clearBtn.setDisable(true);
        clearBtn.setVisible(false);

        /* sign in button inside HBox */
        Button signInBtn = new Button();
        if(UserMain.getIsLogged() == 1){
            signInBtn.setText(UserMain.getCurrentUserName());    //if logged = current username
        } else {
            signInBtn.setText("username");         //if not logged = "username"
            availableTimelines.add("Star Wars");
            availableTimelines.add("Second Industrial Revolution");
        }
        signInBtn.setStyle(cssLightBlue);
        signInBtn.setFont(Font.font("Open Sans Light Italic", 13.7));
        HBox signInBox = new HBox(signInBtn);
        signInBox.setAlignment(Pos.CENTER);
        signInBox.setPrefHeight(60);

        /* Opens the sign in questionnaire */
        signInBtn.setOnAction(actionEvent -> {
            try {
                MainMenu.window.setScene(UserMain.viewAccountCreator());
                MainMenu.window.setMaximized(false);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });


        /* Generates and fills a ScrollPane with all available timelines */
        ScrollPane sp = new ScrollPane();
        //sp.setFitToWidth(true);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setPannable(true);
        sp.setPrefHeight(225);
        sp.setStyle("-fx-background: #aac0f2;" +
                "-fx-border-color: #aac0f2;");

        /* The HBox that holds all of the generated contents (Buttons) */
        HBox content = new HBox();
        content.setStyle(cssLightBlue);
        content.setSpacing(35); //The space between the timeline buttons

        /* Creates the buttons for each timeline respectively with an appropriate preview image (if one exists) */
        for(int i = 0; i < availableUsersTimelines.size(); i++) {

            Button btn = new Button(availableUsersTimelines.get(i)); //The buttons (Timelines) name.
            btn.setFont(Font.font("Open sans", FontWeight.NORMAL, 18));
            btn.setTextFill(Color.web("#FFFFFF"));

            /* The image shape and radius limit */
            Circle c = new Circle();
            c.setRadius(90);

            Box[] boxArr = null;
            try{
                boxArr = EventsHandler.getTimeLineEvents(TimelineHandler.checkTimelineId(availableUsersTimelines.get(i)));
                if(boxArr.length <= 0){
                    boxArr = null;
                }
            } catch(IndexOutOfBoundsException e){
                e.printStackTrace();
            }

            /* Event found and image contained */

            if(boxArr != null && boxArr[0].getImagePath() != null){
                Image firstEventImage = new Image(new FileInputStream(boxArr[0].getImagePath())); //The timeline's first event's image
                c.setFill(new ImagePattern(firstEventImage));
                c.setOpacity(0.75);
            }





            /* Otherwise assign a placeholder blue color */
            else {
                /* offline fix */
                if (btn.getText().equals("Star Wars")) {
                    c.setFill(new ImagePattern(thePhantom));
                } else if (btn.getText().equals("Second Industrial Revolution")) {
                    c.setFill(new ImagePattern(firstPhone));
                } else
                    c.setStyle("-fx-fill: #587dd1");

            }
            btn.setGraphic(c);
            btn.setContentDisplay(ContentDisplay.CENTER);
            btn.setStyle(
                    "-fx-background-radius: 90px;" + //90
                            "-fx-min-width: 200;" + //175
                            "-fx-min-height: 200;" +
                            "-fx-max-width: 200;" +
                            "-fx-max-height: 200;");

            timelineBtnList.add(btn);
        }

        /* Adds all of the available timeline buttons to an HBox */
        for(Button btn : timelineBtnList) {
            content.getChildren().add(btn);
        }

        /* And sets the ScrollBox content to said HBox */
        sp.setContent(content);

        /* A loop that checks for user interaction with the timeline buttons generated
         * when first entering the menu (i.e. all available timelines for that particular user).
         */
        for(int i = 0; i < timelineBtnList.size(); i++) {

            Button current = timelineBtnList.get(i);
            current.setOnAction(actionEvent -> {
                String currentTitle = current.getText();
                if (selectedTimelines.contains(currentTitle)){
                    selectedTimelines.remove(currentTitle);
                    System.out.println("removed: " +currentTitle);
                    ColorAdjust effect = new ColorAdjust();
                    effect.setBrightness(0);
                    current.getGraphic().setEffect(effect);
                }
                else {
                    selectedTimelines.add(currentTitle);
                    System.out.println("added:: " +currentTitle);
                    ColorAdjust effect = new ColorAdjust();
                    effect.setBrightness(-0.7);
                    current.getGraphic().setEffect(effect);
                }
            });
        }

        /* Generates a list of selectable Timelines (buttons) based on the keyword query result*/
        searchBtn.setOnAction(actionEvent -> {
            if(!keywordSearchField.getText().isEmpty() || !keywordSearchField.getText().isBlank()){
                /* Clears the 8search) list of Timelines */
                searchResultBtnList.clear();

                /* Sets a local temporary list to hold the query result list */
                ArrayList<String> queryResult = new ArrayList<>();
                queryResult.clear();

                /* Sets the local temporary list to the searchTimeline's result list */
                try {
                    queryResult = TimelineHandler.searchTimeline(keywordSearchField.getText());

                    /* Sets the loop count to the length the resulting query list
                     * if it returned anything (i.e. contains one or more Timelines)
                     */

                } catch(Exception e) {
                    e.printStackTrace();
                }

                /* Creates the buttons for each timeline respectively with an appropriate preview image (if one exists) */
                for(int i = 0; i < queryResult.size(); i++) {
                    Button btn = new Button(queryResult.get(i)); //The buttons (Timelines) name.
                    btn.setFont(Font.font("Open sans", FontWeight.NORMAL, 18));
                    btn.setTextFill(Color.web("#FFFFFF"));

                    /* The image shape and radius limit */
                    Circle c = new Circle();
                    c.setRadius(90);

                    Box[] boxArr = null;
                    try{
                        boxArr = EventsHandler.getTimeLineEvents(TimelineHandler.checkTimelineId(queryResult.get(i)));
                        if(boxArr.length <= 0){
                            boxArr = null;
                        }
                    } catch(IndexOutOfBoundsException | SQLException e){
                        e.printStackTrace();
                    }

                    /* Event found and image contained */
                    if(boxArr != null && boxArr[0].getImagePath() != null){
                        Image firstEventImage = null; //The timeline's first event's image
                        try {
                            firstEventImage = new Image(new FileInputStream(boxArr[0].getImagePath()));
                            c.setFill(new ImagePattern(firstEventImage));
                            c.setOpacity(0.75);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }

                    /* Otherwise assign a placeholder blue color */
                    else {
                        c.setStyle("-fx-fill: #587dd1");
                    }

                    btn.setGraphic(c);
                    btn.setContentDisplay(ContentDisplay.CENTER);
                    btn.setStyle(cssLightBlue + ";" +
                            "-fx-background-radius: 90px;" + //90
                            "-fx-min-width: 200;" + //175
                            "-fx-min-height: 200;" +
                            "-fx-max-width: 200;" +
                            "-fx-max-height: 200;");

                    searchResultBtnList.add(btn);
                }

                content.getChildren().clear();

                for(Button btn : searchResultBtnList) {
                    content.getChildren().add(btn);
                }

                /* And sets the ScrollBox content to said HBox */
                sp.setContent(content);
                clearBtn.setDisable(false); //Enables -
                clearBtn.setVisible(true);  //and makes the clear button visible.

                /* A loop that checks for user interaction with the timeline buttons generated
                 * by the search result.
                 */
                for(int i = 0; i < searchResultBtnList.size(); i++) {
                    final int selectedTimelineIndex = i;
                    final String title = queryResult.get(i);
                    searchResultBtnList.get(i).setOnAction(e -> {
                        currentTimelineTitle = title;

                        try {
                            currentTimelineObject = TimelineHandler.viewTimeline(currentTimelineTitle);
                        } catch(SQLException eX){
                            eX.printStackTrace();
                        }

                        try {
                            MainMenu.window.setScene(TimelineMenu.editTimeline()); //Changes scene to editTimeline
                        } catch(SQLException | IOException eX) {
                            eX.printStackTrace();
                        }
                    });
                }
            }

            else{
                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("You need to enter a keyword in order to search!");
                alert.showAndWait();
            }
        });

        clearBtn.setOnAction(actionEvent -> {
            clearBtn.setDisable(true); //Disables -
            clearBtn.setVisible(false); //and hides the clear button.
            MainMenu.goToEditMenu();   //Refreshes the menu.
        });

        /* Used to move the menu horizontally based on mouse wheel scrolling */
        sp.setOnScroll(new EventHandler<ScrollEvent>() {
            public void handle(ScrollEvent event) {
                if (event.getDeltaY() > 0)
                    sp.setHvalue(pos == minPos ? minPos : pos--);
                else
                    sp.setHvalue(pos == maxPos ? maxPos : pos++);

            }
        });

        /* Back to main menu */
        backBtn.setOnAction(actionEvent -> {
            try {
                MainMenu.backToMainMenu();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        Button view = new Button("View");
        //view.setStyle("-fx-background-color: #aac0f2");
        view.setFont(Font.font("Open Sans Light", 20));
        view.setOnAction(actionEvent -> {
            for (String str : selectedTimelines)
                System.out.println(str);

        /* if just one timeline is selected -> TimelineMenu.viewSingleTimeline()
           else if more than one timeline is selected -> TimelineMenu.viewMultiTimeline()
         */
            try {
                if (selectedTimelines.size() < 2) {
                    MainMenu.window.setScene(TimelineMenu.viewSingleTimeline());
                    MainMenu.window.setMaximized(true);
                } else if (selectedTimelines.size() > 1) {
                    MainMenu.window.setScene(TimelineMenu.viewMultiTimeline());
                    MainMenu.window.setMaximized(true);
                }
            }

            catch (FileNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        });

        HBox logoAndTitle = new HBox(logoBtn, titleLabel);
        logoAndTitle.setAlignment(Pos.BASELINE_CENTER);
        logoAndTitle.setSpacing(15);

        GridPane searchFieldComponents = new GridPane();
        searchFieldComponents.setHgap(0);
        searchFieldComponents.add(keywordSearchField, 0, 0);
        searchFieldComponents.add(searchBtn, 1, 0);

        HBox topRightBox = new HBox(searchFieldComponents, signInBtn);
        topRightBox.setAlignment(Pos.BASELINE_CENTER);
        topRightBox.setSpacing(10);

        BorderPane topBox = new BorderPane();
        topBox.setLeft(logoAndTitle);
        topBox.setRight(topRightBox);
        topBox.setMaxWidth(600);

        Label info = new Label("     *Please select timelines to view");
        info.setFont(Font.font("Open Sans Light",FontPosture.ITALIC, 18));
        info.setPrefWidth(600);

        BorderPane bottomPane = new BorderPane();
        bottomPane.setTop(info);
        bottomPane.setLeft(backBtn);
        bottomPane.setCenter(clearBtn);
        bottomPane.setRight(view);
        bottomPane.setMinWidth(600);
        bottomPane.setMaxWidth(600);

        sp.setMaxSize(600,250);
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20,20,20,20));
        root.setMaxSize(600,440);
        root.setBottom(bottomPane);
        root.setCenter(sp);
        root.setTop(topBox);
        root.setStyle(cssLightBlue);

        StackPane stackPane = new StackPane(root);
        stackPane.setStyle(cssLightBlue);
        return new Scene(stackPane);
    }

    /**
     * A method that generates and returns a GUI with a ScrollPane containing all
     * available Timelines (for the specified user in question).
     * @return A new scene with the Timeline selection GUI
     * @throws FileNotFoundException
     * @throws SQLException
     * @author Melker F�lt
     * @author Hossin Algerf
     */
    public static Scene selectTimelineEdit() throws IOException, SQLException {
        availableUsersTimelines.clear();
        availableUsersTimelines.addAll(TimelineHandler.viewTimelineList());


        /* List containing all of the dynamically created buttons */
        List<Button> timelineBtnList = new ArrayList<>();

        List<Button> searchResultBtnList = new ArrayList<Button>(); //TODO remove test list

        /* Labels */
        Label titleLabel = new Label("Timelines");
        titleLabel.setFont(Font.font("Open Sans", FontWeight.SEMI_BOLD, FontPosture.ITALIC, 22));

        /* Text fields */
        TextField keywordSearchField = new TextField();
        keywordSearchField.setPrefSize(125, 25);
        keywordSearchField.setStyle(cssStyle);
        keywordSearchField.setPromptText("Search timeline");

        /* Images */
        Image logoImage = new Image(new FileInputStream("graphic/tsiwira.png"));
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitWidth(50);
        logoImageView.setFitHeight(50);

        Image arrowImage = new Image(new FileInputStream("graphic/arrow.png"));
        ImageView arrowImageView = new ImageView(arrowImage);
        arrowImageView.setFitWidth(50);
        arrowImageView.setFitHeight(50);
        arrowImageView.setStyle("-fx-color: #000000");

        Image signInImage = new Image(new FileInputStream("graphic/signin.png"));
        ImageView signInImageView = new ImageView(signInImage);
        signInImageView.setFitHeight(30);
        signInImageView.setFitWidth(30);

        Image loopImage = new Image(new FileInputStream("graphic/loop.png"));
        ImageView loopImageView = new ImageView(loopImage);
        loopImageView.setFitHeight(17);
        loopImageView.setFitWidth(20);

        /* Buttons */
        Button backBtn = new Button();
        backBtn.setStyle(cssLightBlue);
        backBtn.setGraphic(arrowImageView);

        Button logoBtn = new Button();
        logoBtn.setStyle(cssLightBlue);
        logoBtn.setGraphic(logoImageView);

        Button searchBtn = new Button();
        searchBtn.setPrefSize(15, 25);
        searchBtn.setStyle(cssStyle);
        searchBtn.setGraphic(loopImageView);

        Button clearBtn = new Button("Clear");
        clearBtn.setPrefSize(75, 25);
        clearBtn.setStyle(cssStyle);
        clearBtn.setDisable(true);
        clearBtn.setVisible(false);

        Button signInBtn = new Button(UserMain.getCurrentUserName());
        signInBtn.setFont(Font.font("Open Sans", FontWeight.NORMAL,12));
        signInBtn.setContentDisplay(ContentDisplay.RIGHT);
        signInBtn.setGraphic(signInImageView);
        signInBtn.setStyle(cssLightBlue);

        Button editButton = new Button("Edit selected");
        editButton.setFont(Font.font("Open Sans Light",12));
        
        /*
        VBox logoBox = new VBox(logoButton);
        logoBox.setAlignment(Pos.TOP_CENTER);*/

        /* Generates and fills a ScrollPane with all available timelines */
        ScrollPane sp = new ScrollPane();
        //sp.setFitToWidth(true);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setPannable(true);
        sp.setPrefHeight(225);
        sp.setStyle("-fx-background: #aac0f2;" +
                "-fx-border-color: #aac0f2;");

        /* The HBox that holds all of the generated contents (Buttons) */
        HBox content = new HBox();
        content.setStyle(cssLightBlue);
        content.setSpacing(35); //The space between the timeline buttons

        /* Creates the buttons for each timeline respectively with an appropriate preview image (if one exists) */
        for(int i = 0; i < availableUsersTimelines.size(); i++) {


            Button btn = new Button(availableUsersTimelines.get(i)); //The buttons (Timelines) name.


            btn.setFont(Font.font("Open sans", FontWeight.NORMAL, 18));
            btn.setTextFill(Color.web("#FFFFFF"));

            /* The image shape and radius limit */
            Circle c = new Circle();
            c.setRadius(90);

            Box[] boxArr = null;
            try{
                boxArr = EventsHandler.getTimeLineEvents(TimelineHandler.checkTimelineId(availableUsersTimelines.get(i)));
                if(boxArr.length <= 0){
                    boxArr = null;
                }
            } catch(IndexOutOfBoundsException e){
                e.printStackTrace();
            }

            /* Event found and image contained */
            if(boxArr != null && boxArr[0].getImagePath() != null){
                Image firstEventImage = new Image(new FileInputStream(boxArr[0].getImagePath())); //The timeline's first event's image
                c.setFill(new ImagePattern(firstEventImage));
                c.setOpacity(0.75);
            }

            /* Otherwise assign a placeholder blue color */
            else {
                c.setStyle("-fx-fill: #587dd1");
            }
            btn.setGraphic(c);
            btn.setContentDisplay(ContentDisplay.CENTER);
            btn.setStyle(cssLightBlue + ";" +
                    "-fx-background-radius: 90px;" + //90
                    "-fx-min-width: 200;" + //175
                    "-fx-min-height: 200;" +
                    "-fx-max-width: 200;" +
                    "-fx-max-height: 200;");

            timelineBtnList.add(btn);
        }

        /* Adds all of the available timeline buttons to an HBox */
        for(Button btn : timelineBtnList) {
            content.getChildren().add(btn);
        }

        /* And sets the ScrollBox content to said HBox */
        sp.setContent(content);

        /* A loop that checks for user interaction with the timeline buttons generated
         * when first entering the menu (i.e. all available timelines for that particular user).
         */
        for(int i = 0; i < timelineBtnList.size(); i++) {
            final int selectedTimelineIndex = i;
            timelineBtnList.get(i).setOnAction(actionEvent -> {
                currentTimelineTitle = availableUsersTimelines.get(selectedTimelineIndex);

                try {
                    currentTimelineObject = TimelineHandler.viewTimeline(currentTimelineTitle);
                } catch(SQLException e){
                    e.printStackTrace();
                }

                try {
                    MainMenu.window.setScene(TimelineMenu.editTimeline()); //Changes scene to editTimeline
                } catch(SQLException | IOException e) {
                    e.printStackTrace();
                }

            });
        }

        /* Generates a list of selectable Timelines (buttons) based on the keyword query result*/
        searchBtn.setOnAction(actionEvent -> {
            if(!keywordSearchField.getText().isEmpty() || !keywordSearchField.getText().isBlank()){
                /* Clears the 8search) list of Timelines */
                searchResultBtnList.clear();

                /* Sets a local temporary list to hold the query result list */
                ArrayList<String> queryResult = new ArrayList<>();
                queryResult.clear();

                /* Sets the local temporary list to the searchTimeline's result list */
                try {
                    queryResult = TimelineHandler.searchTimeline(keywordSearchField.getText());

                    /* Sets the loop count to the length the resulting query list
                     * if it returned anything (i.e. contains one or more Timelines)
                     */

                } catch(Exception e) {
                    e.printStackTrace();
                }

                /* Creates the buttons for each timeline respectively with an appropriate preview image (if one exists) */
                for(int i = 0; i < queryResult.size(); i++) {
                    Button btn = new Button(queryResult.get(i)); //The buttons (Timelines) name.
                    btn.setFont(Font.font("Open sans", FontWeight.NORMAL, 18));
                    btn.setTextFill(Color.web("#FFFFFF"));

                    /* The image shape and radius limit */
                    Circle c = new Circle();
                    c.setRadius(90);

                    Box[] boxArr = null;
                    try{
                        boxArr = EventsHandler.getTimeLineEvents(TimelineHandler.checkTimelineId(queryResult.get(i)));
                        if(boxArr.length <= 0){
                            boxArr = null;
                        }
                    } catch(IndexOutOfBoundsException | SQLException e){
                        e.printStackTrace();
                    }

                    /* Event found and image contained */
                    if(boxArr != null && boxArr[0].getImagePath() != null){
                        Image firstEventImage = null; //The timeline's first event's image
                        try {
                            firstEventImage = new Image(new FileInputStream(boxArr[0].getImagePath()));
                            c.setFill(new ImagePattern(firstEventImage));
                            c.setOpacity(0.75);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }

                    /* Otherwise assign a placeholder blue color */
                    else {
                        c.setStyle("-fx-fill: #587dd1");
                    }

                    btn.setGraphic(c);
                    btn.setContentDisplay(ContentDisplay.CENTER);
                    btn.setStyle(cssLightBlue + ";" +
                            "-fx-background-radius: 90px;" + //90
                            "-fx-min-width: 200;" + //175
                            "-fx-min-height: 200;" +
                            "-fx-max-width: 200;" +
                            "-fx-max-height: 200;");

                    searchResultBtnList.add(btn);
                }

                content.getChildren().clear();

                for(Button btn : searchResultBtnList) {
                    content.getChildren().add(btn);
                }

                /* And sets the ScrollBox content to said HBox */
                sp.setContent(content);
                clearBtn.setDisable(false); //Enables -
                clearBtn.setVisible(true);  //and makes the clear button visible.

                /* A loop that checks for user interaction with the timeline buttons generated
                 * by the search result.
                 */
                for(int i = 0; i < searchResultBtnList.size(); i++) {
                    final int selectedTimelineIndex = i;
                    final String title = queryResult.get(i);
                    searchResultBtnList.get(i).setOnAction(e -> {
                        currentTimelineTitle = title;

                        try {
                            currentTimelineObject = TimelineHandler.viewTimeline(currentTimelineTitle);
                        } catch(SQLException eX){
                            eX.printStackTrace();
                        }

                        try {
                            MainMenu.window.setScene(TimelineMenu.editTimeline()); //Changes scene to editTimeline
                        } catch(SQLException | IOException eX) {
                            eX.printStackTrace();
                        }
                    });
                }
            }

            else{
                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("You need to enter a keyword in order to search!");
                alert.showAndWait();
            }
        });

        clearBtn.setOnAction(actionEvent -> {
            clearBtn.setDisable(true); //Disables -
            clearBtn.setVisible(false); //and hides the clear button.
            MainMenu.goToEditMenu();   //Refreshes the menu.
        });

        /* Used to move the menu horizontally based on mouse wheel scrolling */
        sp.setOnScroll(new EventHandler<ScrollEvent>() {
            public void handle(ScrollEvent event) {
                if (event.getDeltaY() > 0)
                    sp.setHvalue(pos == minPos ? minPos : pos--);
                else
                    sp.setHvalue(pos == maxPos ? maxPos : pos++);

            }
        });

        /* Back to main menu */
        backBtn.setOnAction(actionEvent -> {
            try {
                MainMenu.backToMainMenu();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        VBox emptySpaceLeft = new VBox();
        emptySpaceLeft.setPadding(new Insets(145));

        HBox logoAndTitle = new HBox(logoBtn, titleLabel);
        logoAndTitle.setAlignment(Pos.BASELINE_CENTER);
        logoAndTitle.setSpacing(15);

        GridPane searchFieldComponents = new GridPane();
        searchFieldComponents.setHgap(0);
        searchFieldComponents.add(keywordSearchField, 0, 0);
        searchFieldComponents.add(searchBtn, 1, 0);

        HBox topRightBox = new HBox(searchFieldComponents, signInBtn);
        topRightBox.setAlignment(Pos.BASELINE_CENTER);
        topRightBox.setSpacing(10);

        BorderPane topBox = new BorderPane();
        topBox.setLeft(logoAndTitle);
        topBox.setRight(topRightBox);
        topBox.setMaxWidth(600);

        HBox bottomBox = new HBox(backBtn, clearBtn);
        bottomBox.setSpacing(450);

        VBox rightSide = new VBox(topBox, sp, bottomBox); //topBox, searchField
        rightSide.setAlignment(Pos.CENTER_LEFT);
        rightSide.setStyle(cssLightBlue);
        rightSide.setSpacing(25);

        HBox root = new HBox(rightSide);
        root.setPadding(new Insets(20,20,20,20));
        StackPane stackPane = new StackPane(root);
        stackPane.setStyle(cssLightBlue);
        return new Scene(stackPane);
    }

    /* Edits selected timeline and its events(Boxes) -by Hossin and -*/
    public static Scene editTimeline() throws IOException, SQLException  {

        /* Logo with back to main menu function*/
        Image logo = new Image(new FileInputStream("graphic/tsiwira.png"));
        ImageView logoImageView = new ImageView(logo);
        logoImageView.setFitWidth(50);
        logoImageView.setFitHeight(50);

        Button logoButton = new Button();
        logoButton.setStyle("-fx-background-color: #587dd1");
        logoButton.setGraphic(logoImageView);

        Image arrowImage = new Image(new FileInputStream("graphic/arrow.png"));
        ImageView arrowImageView = new ImageView(arrowImage);
        arrowImageView.setFitWidth(50);
        arrowImageView.setFitHeight(50);
        arrowImageView.setStyle("-fx-color: #000000");

        Button backBtn = new Button();
        backBtn.setStyle(cssDarkBlue);
        backBtn.setGraphic(arrowImageView);

        Image signInImage = new Image(new FileInputStream("graphic/signin.png"));
        ImageView signInImageView = new ImageView(signInImage);
        signInImageView.setFitHeight(30);
        signInImageView.setFitWidth(30);

        /* Changes the scene to the editTimeline menu */
        backBtn.setOnAction(actionEvent -> {
            try {
                MainMenu.window.setScene(TimelineMenu.selectTimelineEdit());
            } catch(IOException | SQLException e) {
                e.printStackTrace();
            }
        });

        Label timelineTitle = new Label(currentTimelineTitle); //("Editing " + currentTimelineTitle);
        timelineTitle.setFont(Font.font("Open Sans",FontPosture.ITALIC, 19));

        HBox logoAndTitle = new HBox(logoButton,timelineTitle);
        logoAndTitle.setAlignment(Pos.CENTER);

        /* sign in button inside HBox */
        Button signIn = new Button(UserMain.getCurrentUserName());
        signIn.setGraphic(signInImageView);
        signIn.setContentDisplay(ContentDisplay.RIGHT);

        signIn.setStyle("-fx-background-color: #587dd1");
        signIn.setFont(Font.font("Open Sans", 13.7));

        /* This should not be a button since the user can't access the editing menu unless 
         * he/she is already signed in to begin with.
        signIn.setOnAction(actionEvent -> {
            try {
                MainMenu.window.setScene(UserMain.viewAccountCreator());
            } catch(FileNotFoundException e) {
                e.printStackTrace();
            }
        });*/

        HBox signInBox = new HBox(signIn);
        signInBox.setPrefHeight(60);
        signInBox.setPadding(new Insets(10, 0, 0, 0));

        /* add event button */
        Button addEvent = new Button("Add event");
        addEvent.setStyle("-fx-background-color: #aac0f2");
        addEvent.setPrefWidth(150);
        addEvent.setPrefHeight(150);
        addEvent.setFont(Font.font("Open Sans", 18));

        addEvent.setOnAction(actionEvent -> {
            try {
                MainMenu.window.setScene(TimelineMenu.eventAdd());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        /* edit event button */
        Button editEvent = new Button("Edit event");
        editEvent.setStyle("-fx-background-color: #aac0f2");
        editEvent.setPrefWidth(150);
        editEvent.setPrefHeight(150);
        editEvent.setFont(Font.font("Open Sans", 18));

        editEvent.setOnAction(actionEvent -> {
            try {
                MainMenu.window.setScene(TimelineMenu.selectEventEdit());
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        });

        /* delete event button */
        Button deleteEvent = new Button("Delete event");
        deleteEvent.setStyle("-fx-background-color: #aac0f2");
        deleteEvent.setPrefWidth(150);
        deleteEvent.setPrefHeight(150);
        deleteEvent.setFont(Font.font("Open Sans Light", 18));

        deleteEvent.setOnAction(actionEvent -> {
            try {
                MainMenu.window.setScene(TimelineMenu.selectEventDelete());
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        });

        /* Buttons for Timeline editing */
        Button deleteTimelineBtn = new Button("Delete timeline");
        deleteTimelineBtn.setStyle("-fx-background-color: #aac0f2");
        deleteTimelineBtn.setPrefWidth(150);
        deleteTimelineBtn.setPrefHeight(75);
        deleteTimelineBtn.setFont(Font.font("Open Sans Light", 12));

        Button editTimelineTitleBtn = new Button("Confirm");
        editTimelineTitleBtn.setStyle("-fx-background-color: #aac0f2");
        editTimelineTitleBtn.setPrefWidth(125);
        editTimelineTitleBtn.setPrefHeight(12);
        editTimelineTitleBtn.setFont(Font.font("Open Sans Light", 12));

        Button addTimelineKeywordBtn = new Button("Confirm");
        addTimelineKeywordBtn.setStyle("-fx-background-color: #aac0f2");
        addTimelineKeywordBtn.setPrefWidth(125);
        addTimelineKeywordBtn.setPrefHeight(25);
        addTimelineKeywordBtn.setFont(Font.font("Open Sans Light", 12));


        /* TextFields for Timeline editing */
        TextField titleField = new TextField();
        titleField.setPrefSize(200, 25);
        titleField.setStyle(cssStyle);
        titleField.setPromptText("New Title");

        TextField keywordField = new TextField();
        keywordField.setPrefSize(200, 25);
        keywordField.setStyle(cssStyle);
        keywordField.setPromptText("New Keyword");

        GridPane timelineEditFields = new GridPane();
        timelineEditFields.setVgap(10);
        timelineEditFields.setHgap(10);

        timelineEditFields.add(titleField, 0, 0);
        timelineEditFields.add(editTimelineTitleBtn, 1, 0);
        GridPane.setHalignment(editTimelineTitleBtn, HPos.RIGHT);

        timelineEditFields.add(keywordField, 0, 1);
        timelineEditFields.add(addTimelineKeywordBtn, 1, 1);
        GridPane.setHalignment(addTimelineKeywordBtn, HPos.RIGHT);
        
        /*
        timelineEditFields.add(deleteTimelineBtn, 2, 1);
        GridPane.setHalignment(deleteTimelineBtn, HPos.RIGHT);*/

        /* Changes the current Timeline's title to the text contained within the TextField */
        editTimelineTitleBtn.setOnAction(actionEvent -> {
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to change this timeline's title to \""+ titleField.getText() +"\"?");

            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK) {

                /*Added the connection to the database*/
                TimelineHandler.updateTimelineTitle(titleField.getText(),currentTimelineObject.getTimelineID());

                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Successfully changed the timeline's title!");
                alert.showAndWait();
                titleField.clear();
                MainMenu.goToEditMenu();
            }
        });

        addTimelineKeywordBtn.setOnAction(actionEvent -> {
            try{
                if(!keywordField.getText().isEmpty())
                    TimelineHandler.updateKeyword(keywordField.getText(),currentTimelineTitle);
                keywordField.clear();
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        /* Deletes the currently selected Timeline */
        deleteTimelineBtn.setOnAction(actionEvent -> {
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this timeline?");

            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK) {
                // TIMELINE DELETION FUNCTIONALLITY HERE
                TimelineHandler.deleteTimeline(currentTimelineTitle);
                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Successfully deleted the timeline!");
                alert.showAndWait();

                /* Returns the user to the main menu since the currently selected Timeline has been removed */
                try {
                    MainMenu.backToMainMenu();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });


        /* HBox/VBoxes for menu layout */
        HBox top = new HBox(logoAndTitle, signInBox);
        top.setAlignment(Pos.CENTER_LEFT);

        HBox middle = new HBox(timelineEditFields, deleteTimelineBtn);
        middle.setPadding(new Insets(0, 0, 0, 40));
        middle.setSpacing(60);
        middle.setAlignment(Pos.CENTER_LEFT);


        HBox buttons = new HBox(addEvent,editEvent,deleteEvent);
        buttons.setSpacing(50);
        buttons.setPadding(new Insets(20,40,20,40));
        buttons.setAlignment(Pos.CENTER_LEFT);

        HBox backButtonBox = new HBox(backBtn);

        VBox bottom = new VBox(buttons, backButtonBox);
        bottom.setSpacing(15);

        //TODO Needs to change the positioning of the signed in users name relative to the length of the title.
        top.setSpacing(300); //((375 - currentTimelineTitle.length()));  
        top.setPadding(new Insets(20,40,20,40));

        VBox root = new VBox(top, middle, bottom);
        root.setSpacing(20); //100

        StackPane stackPane = new StackPane(root);
        stackPane.setStyle("-fx-background-color: #587dd1");
        return new Scene(stackPane);
    }

    /**
     * A method that generates and returns a GUI with a ScrollPane containing all
     * available Events (for the specified Timeline in question).
     * @return A
     * @throws FileNotFoundException
     * @throws SQLException'
     * @author Melker F�lt
     * @author Hossin Algerf
     */
    public static Scene selectEventEdit() throws IOException, SQLException  {
        availablEvents.clear();
        availablEventsObj.clear();

        int timelineId = currentTimelineObject.getTimelineID();
        availablEvents.addAll(EventsHandler.viewEventList(timelineId));

        Box [] events =EventsHandler.getTimeLineEvents(currentTimelineObject.getTimelineID());

        /* List containing all of the dynamically created buttons */

        List<Button> timelineBtnList = new ArrayList<>();

        int indexI = 0; //Local index position used for grid placement (Row).
        int indexJ = 0; //Local index position used for grid placement (Column).

        /* Labels */
        Label titleLabel = new Label("Timeline event editing");
        titleLabel.setFont(Font.font("Open Sans", FontWeight.SEMI_BOLD, FontPosture.ITALIC, 22));

        /* Images */
        Image logoImage = new Image(new FileInputStream("graphic/tsiwira.png"));
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitWidth(50);
        logoImageView.setFitHeight(50);

        Image signInImage = new Image(new FileInputStream("graphic/signin.png"));
        ImageView signInImageView = new ImageView(signInImage);
        signInImageView.setFitHeight(30);
        signInImageView.setFitWidth(30);

        Image arrowImage = new Image(new FileInputStream("graphic/arrow.png"));
        ImageView arrowImageView = new ImageView(arrowImage);
        arrowImageView.setFitWidth(50);
        arrowImageView.setFitHeight(50);
        arrowImageView.setStyle("-fx-color: #000000");

        /* Buttons */
        Button backBtn = new Button();
        backBtn.setStyle(cssLightBlue);
        backBtn.setGraphic(arrowImageView);
        backBtn.setPadding(new Insets(0, 0, 25, 20));
        backBtn.setAlignment(Pos.CENTER);

        Button signInBtn = new Button(UserMain.getCurrentUserName());
        signInBtn.setFont(Font.font("Open Sans", FontWeight.NORMAL,12));
        signInBtn.setContentDisplay(ContentDisplay.RIGHT);
        signInBtn.setGraphic(signInImageView);
        signInBtn.setStyle(cssLightBlue);
        signInBtn.setPadding(new Insets(30, 0, 0, 0));

        /* The ScrollPane that holds the grid containing all event buttons */
        ScrollPane eventSP = new ScrollPane();
        eventSP.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        eventSP.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        eventSP.setVmax(400);
        eventSP.setPrefSize(450, 500);
        eventSP.setStyle("-fx-background: #aac0f2;" +
                "-fx-border-color: #aac0f2;");

        /* The grid that holds all available event buttons */
        GridPane eventGrid = new GridPane();
        //eventGrid.setPadding(new Insets(50));
        eventGrid.setAlignment(Pos.BASELINE_CENTER);
        //eventGrid.setPrefSize(450, 450);
        eventGrid.setHgap(15);
        eventGrid.setVgap(15);

        /* A for-each that adds all available events to the GridPane */
        for(Box event : events ) {
            //Label eventName = new Label(event); //The name of the event

            /* Creates a button for the current event */
            Button eventButton = new Button(event.getTitle());
            eventButton.setFont(Font.font("Open sans", FontWeight.NORMAL, 12));
            eventButton.setTextFill(Color.web("#000000"));

            /* Sets the events image (if one exists) */
            Image eventImage;
            ImageView eventImageView;
            Rectangle r = new Rectangle(75, 75);
            r.setArcHeight(15);
            r.setArcWidth(15);
            if(event.getImagePath()!=null) { //TODO Needs to check if the event contains an image and if so set it below.
                FileInputStream fPS = new FileInputStream(event.getImagePath());
                eventImage = new Image(fPS);//new FileInputStream(event.getImagePath()));

                eventImageView = new ImageView(eventImage);
                eventImageView.setFitHeight(75);
                eventImageView.setFitWidth(75);

                r.setFill(new ImagePattern(eventImage));
                eventButton.setGraphic(r);
                fPS.close();

            }
            else {
                r.setFill(Color.web("#587dd1"));
                eventButton.setGraphic(r);
                eventButton.setStyle(cssLightBlue);
            }




            eventButton.setGraphic(r);
            eventButton.setPrefSize(50, 50);
            eventButton.setContentDisplay(ContentDisplay.TOP);
            eventButton.setStyle(cssLightBlue);

            timelineBtnList.add(eventButton);


            /* Moves the row down one step when the first index of 6 columns are filled */
            if(indexI == 4) {
                indexI = 0;  //Row
                indexJ++;    //Column
            }

            eventGrid.add(eventButton, indexI, indexJ);
            indexI++;
        }

        /* Adds the GridPane generated above to the ScrollPane */
        eventSP.setContent(eventGrid);

        /* Continuously checks for button (event) interaction */
        for(int i = 0; i < events.length; i++) {
            final int selectedTimelineIndex = i;
            timelineBtnList.get(i).setOnAction(actionEvent -> {

                currentBoxTitle = timelineBtnList.get(selectedTimelineIndex).getText();
                currentBoxDate = events[selectedTimelineIndex].getBoxDate(); //TODO Needs a method in EventsHandler that gives event dates.
                currentBoxID = events[selectedTimelineIndex].getBoxId();

                try {
                    MainMenu.window.setScene(TimelineMenu.eventEdit()); //Changes scene to editTimeline
                } catch(FileNotFoundException e) {
                    e.printStackTrace();
                }
            });
        }

        /* Changes the scene to the editTimeline menu */
        backBtn.setOnAction(actionEvent -> {
            try {
                MainMenu.window.setScene(TimelineMenu.editTimeline());
            } catch(IOException | SQLException e) {
                e.printStackTrace();
            }
        });

        /* HBox & VBox containers */
        HBox logoAndTitle = new HBox(logoImageView, titleLabel);
        logoAndTitle.setAlignment(Pos.CENTER_LEFT);
        logoAndTitle.setSpacing(15);
        logoAndTitle.setPadding(new Insets(25, 0, 0, 20));

        HBox topRow = new HBox(logoAndTitle, signInBtn);
        topRow.setSpacing(225);

        HBox scrollPaneBox = new HBox(eventSP);
        scrollPaneBox.setAlignment(Pos.BASELINE_CENTER);

        VBox root = new VBox(topRow, scrollPaneBox, backBtn);

        StackPane stackPane = new StackPane(root);
        stackPane.setStyle(cssLightBlue);
        return new Scene(stackPane);
    }

    /* Scene to edit/remove selected event , or add an event - by Hossin and Melker and- Bartek*/

    public static Scene eventEdit() throws FileNotFoundException {

        /* Logo with back to main menu function*/
        Image logo = new Image(new FileInputStream("graphic/tsiwira.png"));
        ImageView logoImageView = new ImageView(logo);
        logoImageView.setFitWidth(50);
        logoImageView.setFitHeight(50);
        Button logoButton = new Button();
        logoButton.setStyle("-fx-background-color: #587dd1");
        logoButton.setGraphic(logoImageView);
        HBox logoBox = new HBox(logoButton);
        logoBox.setAlignment(Pos.CENTER_LEFT);

        /* Back to main menu */
        logoButton.setOnAction(actionEvent -> {
            try {
                MainMenu.backToMainMenu();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        /* Button with arrow image */
        Image arrow = new Image(new FileInputStream("graphic/arrow.png"));
        ImageView arrowView = new ImageView(arrow);
        arrowView.setFitWidth(30);
        arrowView.setFitHeight(30);

        Button arrowButton = new Button();
        arrowButton.setGraphic(arrowView);
        arrowButton.setStyle("-fx-background-color: #587dd1");

        /* Arrow send you back to the current timeline */
        arrowButton.setOnAction(actionEvent -> {
            try {
                MainMenu.window.setScene(TimelineMenu.selectEventEdit());
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        });

        //////////////////////////////////////////

        /* column in the middle with labels and fields */

        /* The title of the current box */
        Label boxTitle = new Label("Currently editing: " + currentBoxTitle);
        boxTitle.setFont(Font.font("Open Sans", FontWeight.EXTRA_BOLD, 15));

        /* title */
        Label title = new Label("Title:");
        title.setFont(Font.font("Open Sans", 13.7));

        TextField titleField = new TextField();
        titleField.setStyle("-fx-border-color: #000000; -fx-border-width: 0.5px; -fx-background-color: #FFFFFF");
        titleField.setPrefWidth(180);

        titleField.setPromptText("Enter new title");

        HBox currentBoxTitle = new HBox(boxTitle);
        currentBoxTitle.setAlignment(Pos.BASELINE_CENTER);
        currentBoxTitle.setSpacing(15);

        HBox titleBox = new HBox(title,titleField);
        titleBox.setAlignment(Pos.CENTER_RIGHT);
        titleBox.setSpacing(5);

        /* date */
        Label date = new Label("Date:");
        date.setFont(Font.font("Open Sans", 13.7));

        TextField dateField = new TextField();
        dateField.setPrefWidth(113);
        dateField.setStyle("-fx-border-color: transparent; -fx-border-width: 0.5px; -fx-background-color: #FFFFFF");

        dateField.setPromptText("Enter new date");	// See selectEventEdit "currentBoxDate" comment for further info

        ComboBox<String> dateType = new ComboBox<>();
        dateType.getItems().addAll("A.D.", "B.C.");
        dateType.setValue("A.D.");
        dateType.setStyle("-fx-font: 10px \"Open Sans\"");
        dateType.setStyle("-fx-border-color: transparent; -fx-border-width: 0.5px; -fx-background-color: #FFFFFF");
        dateType.setPrefWidth(65);

        BorderPane dateFieldBox = new BorderPane();       //text field and combo box in the same line
        dateFieldBox.setLeft(dateField);
        dateFieldBox.setRight(dateType);
        dateFieldBox.setMinHeight(30);
        dateFieldBox.setMaxHeight(30);
        dateFieldBox.setStyle("-fx-border-color: #000000; -fx-border-width: 0.5px; -fx-background-color: #FFFFFF");

        HBox dateBox = new HBox(date, dateFieldBox);
        dateBox.setAlignment(Pos.CENTER_RIGHT);
        dateBox.setSpacing(5);

        /* Description */
        Label description = new Label("Description:");
        description.setFont(Font.font("Open Sans", 13.7));

        TextField descriptionField = new TextField();
        descriptionField.setStyle("-fx-border-color: #000000; -fx-border-width: 0.5px; -fx-background-color: #FFFFFF");
        descriptionField.setPrefWidth(180);
        descriptionField.setPromptText("Enter new description");

        HBox descriptionBox = new HBox(description,descriptionField);
        descriptionBox.setAlignment(Pos.CENTER_RIGHT);
        descriptionBox.setSpacing(5);

        /**
         * This section handles image storing.
         * @author Melker F�lt
         * @date 2020-05-12
         */

        /* Image directory path label */
        Label imageDirLabel = new Label("");
        imageDirLabel.setFont(Font.font("Open Sans", 12));
        imageDirLabel.setWrapText(true);

        /* Add image button */
        Button addImage = new Button("Upload Image");
        addImage.setPrefSize(120,20);
        addImage.setAlignment(Pos.BASELINE_RIGHT);
        addImage.setStyle("-fx-background-color: #aac0f2");
        addImage.setFont(Font.font("Open Sans", 12));
        addImage.setContentDisplay(ContentDisplay.TOP);

        /* Creates the file chooser used for image selection and limits said selection (png/jpg)*/
        FileChooser fC = new FileChooser();
        fC.setTitle("Image Selector");
        fC.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"));

        addImage.setOnAction(actionEvent -> {
            File file = fC.showOpenDialog(MainMenu.window);
            fC.setInitialFileName("defaultSaveName");
            if(file != null) {
                imageDirString = file.getAbsolutePath();  //Stores the file path for the selected image.
                imageDirPath = Paths.get(imageDirString); //Creates a path based on the absolute path URL.
                //if the file does not exist fileName = file.getName + random number.
                //else fileName = file.getName.
                //Do these changes in edit event menu and evey where the pictures are added to avoid collision
                fileName = file.getName();
                System.out.println(file.getName());
                imageDirLabel.setText(imageDirString); //Sets the label's text to the currently selected image file path.
            }
        });

        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\\


        /* save button */
        Button save = new Button("Save");
        save.setPrefSize(80,20);
        save.setAlignment(Pos.CENTER);
        save.setStyle("-fx-background-color: #aac0f2");
        save.setFont(Font.font("Open Sans", 12));
        save.setContentDisplay(ContentDisplay.TOP);

        HBox saveBox = new HBox(save);
        saveBox.setPrefWidth(180);
        saveBox.setAlignment(Pos.CENTER_RIGHT);

        //////////////////////////////////////////

        /* column on the right side with username and save button */

        /* Label with username and image */
        Image signInImage = new Image(new FileInputStream("graphic/signin.png"));
        ImageView signInImageView = new ImageView(signInImage);
        signInImageView.setFitHeight(30);
        signInImageView.setFitWidth(30);

        Label username = new Label();
        username.setFont(Font.font("Open Sans ", 13.7));
        username.setText(UserMain.getCurrentUserName());
        username.setGraphic(signInImageView);
        username.setContentDisplay(ContentDisplay.RIGHT);
        username.setPadding(new Insets(15, 0, 0, 0));

        /////////////////////////////////////////////////////////////////////////

        //HI HOSSIN! :)
        /* Action events for buttons - place for Hossin and his code */

        // A.D. and B.C. combo box with some simple code in the beginning
        dateType.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println("Selected value : " + newValue);
            }
        });

        //save button
        save.setOnAction(actionEvent -> {

            if(!titleField.getText().isEmpty()){
                EventsHandler.updateEventTitle(titleField.getText(),currentBoxID);
            }

            /* If not empty and fulfills date requirements/format TODO UPDATE CHECK */
            if(!dateField.getText().isEmpty()){
                EventsHandler.updateEventDate(dateField.getText(),currentBoxID);
            }

            if(!descriptionField.getText().isEmpty()){
                EventsHandler.updateEventDesc(descriptionField.getText(),currentBoxID);
            }
            String path = EventsHandler.getPath(currentBoxID);
            if(path!=null) {
                //Updates the database path string
                try{
                    File deleteFile = new File(path);
                    if( deleteFile.delete()){
                        System.out.println("Deleted!");
                    }

                } catch (Exception e){
                    e.printStackTrace();
                }

                /* Tries to add the selected file to the standard image storage folder */

                Path target = standardDirPath.resolve(fileName);

                try {
                    Files.copy(imageDirPath, target);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                EventsHandler.updateImage(target.toString(), currentBoxID);
            }
            else{
                    /* Tries to add the selected file to the standard image storage folder */
                    Path target = standardDirPath.resolve(fileName);

                    try {
                        Files.copy(imageDirPath, target); //Copies the selected image to the standard directory within the application.
                        File oldFileName = new File(target.toString()); //Gets the selected image name.
                        String newFileName = UserMain.getCurrentUserName() + currentTimelineObject.getTitle() + rng.nextInt(1000) + fileName; //Generates a new name based on the current user, timeline random nmbr and the original filename.

                        File newFile = new File(standardDirPath + "\\" + newFileName); //A new file with the updated name (that's going to replace the old image).
                        oldFileName.renameTo(newFile); //Renames the existing file.

                        EventsHandler.updateImage(newFile.toString(), currentBoxID); //Adds the image path to the database.
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

            }
            //clearing all TextFields

            fileName=null;
            titleField.clear();
            descriptionField.clear();
            dateField.clear();
            imageDirString=null;
            imageDirLabel.setText("");


            /*
           titleField.clear();
           dateField.clear();
           descriptionField.clear(); */




        });

        /////////////////////////////////////////////////////////////////////

        VBox leftSide = new VBox(logoBox,arrowButton);
        leftSide.setAlignment(Pos.TOP_LEFT);
        leftSide.setSpacing(300);
        leftSide.setPrefWidth(160);
        leftSide.setPadding(new Insets(30,0,10,20));

        VBox middle = new VBox(currentBoxTitle, titleBox, dateBox, descriptionBox, addImage, saveBox); //keywordBox
        middle.setAlignment(Pos.TOP_CENTER);
        middle.setSpacing(10);
        middle.setPrefWidth(280);
        middle.setPadding(new Insets(150,0,10,0));

        VBox rightSide = new VBox(username);
        rightSide.setAlignment(Pos.TOP_RIGHT);
        rightSide.setSpacing(320);
        rightSide.setPrefWidth(160);
        rightSide.setPadding(new Insets(30,20,10,0));

        HBox root = new HBox(leftSide,middle,rightSide);
        StackPane stackPane = new StackPane(root);      //StackPane for custom background
        stackPane.setStyle("-fx-background-color: #587dd1");
        return new Scene(stackPane);
    }

    /**
     * A method that generates an event selection GUI wherein the user can freely
     * select which event to delete within the selected timeline.
     * @return A new scene with deletion GUI
     * @throws FileNotFoundException
     * @throws SQLException
     * @author Melker F�lt
     */
    public static Scene selectEventDelete() throws FileNotFoundException, SQLException, IOException{
        availablEvents.clear();
        int timelineID= currentTimelineObject.getTimelineID();
        availablEvents.addAll(EventsHandler.viewEventList(timelineID));
        Box[] events = EventsHandler.getTimeLineEvents(timelineID);
        /* List containing all of the dynamically created buttons */

        List<Button> timelineBtnList = new ArrayList<>();

        int indexI = 0; //Local index position used for grid placement (Row).
        int indexJ = 0; //Local index position used for grid placement (Column).

        /* Labels */
        Label titleLabel = new Label("Timeline event deletion");
        titleLabel.setFont(Font.font("Open Sans", FontWeight.SEMI_BOLD, FontPosture.ITALIC, 22));

        /* Images */
        Image logoImage = new Image(new FileInputStream("graphic/tsiwira.png"));
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitWidth(50);
        logoImageView.setFitHeight(50);

        Image signInImage = new Image(new FileInputStream("graphic/signin.png"));
        ImageView signInImageView = new ImageView(signInImage);
        signInImageView.setFitHeight(30);
        signInImageView.setFitWidth(30);

        Image arrowImage = new Image(new FileInputStream("graphic/arrow.png"));
        ImageView arrowImageView = new ImageView(arrowImage);
        arrowImageView.setFitWidth(50);
        arrowImageView.setFitHeight(50);
        arrowImageView.setStyle("-fx-color: #000000");

        /* Buttons */
        Button backBtn = new Button();
        backBtn.setStyle(cssLightBlue);
        backBtn.setGraphic(arrowImageView);
        backBtn.setPadding(new Insets(0, 0, 25, 20));
        backBtn.setAlignment(Pos.CENTER);

        Button signInBtn = new Button(UserMain.getCurrentUserName());
        signInBtn.setFont(Font.font("Open Sans", FontWeight.NORMAL,12));
        signInBtn.setContentDisplay(ContentDisplay.RIGHT);
        signInBtn.setGraphic(signInImageView);
        signInBtn.setStyle(cssLightBlue);
        signInBtn.setPadding(new Insets(30, 0, 0, 0));

        /* The ScrollPane that holds the grid containing all event buttons */
        ScrollPane eventSP = new ScrollPane();
        eventSP.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        eventSP.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        eventSP.setVmax(400);
        eventSP.setPrefSize(450, 500);
        eventSP.setStyle("-fx-background: #aac0f2;" +
                "-fx-border-color: #aac0f2;");

        /* The grid that holds all available event buttons */
        GridPane eventGrid = new GridPane();
        //eventGrid.setPadding(new Insets(50));
        eventGrid.setAlignment(Pos.BASELINE_CENTER);
        //eventGrid.setPrefSize(450, 450);
        eventGrid.setHgap(15);
        eventGrid.setVgap(15);

        /* A for-each that adds all available events to the GridPane */
        for(Box event :events) {
            //Label eventName = new Label(event); //The name of the event

            /* Creates a button for the current event */
            Button eventButton = new Button(event.getTitle());
            eventButton.setTextFill(Color.web("#000000"));

            /* Sets the events image (if one exists) */
            Image eventImage;
            ImageView eventImageView;

            Rectangle r = new Rectangle(75, 75);
            r.setArcHeight(15);
            r.setArcWidth(15);

            if(event.getImagePath()!=null) {
                FileInputStream fPS = new FileInputStream(event.getImagePath());
                eventImage = new Image(fPS);//new FileInputStream(event.getImagePath()));

                eventImageView = new ImageView(eventImage);
                eventImageView.setFitHeight(75);
                eventImageView.setFitWidth(75);
                eventButton.setStyle(cssLightBlue);

                r.setFill(new ImagePattern(eventImage));
                eventButton.setGraphic(r);
                fPS.close();
            }

            else {
                r.setFill(Color.web("#587dd1"));
                eventButton.setGraphic(r);
                eventButton.setStyle(cssLightBlue);
            }


            eventButton.setPrefSize(50, 50);
            eventButton.setContentDisplay(ContentDisplay.TOP);


            timelineBtnList.add(eventButton);

            /* Moves the row down one step when the first index of 6 columns are filled */
            if(indexI == 4) {
                indexI = 0;  //Row
                indexJ++;    //Column
            }

            eventGrid.add(eventButton, indexI, indexJ);
            indexI++;
        }

        /* Adds the GridPane generated above to the ScrollPane */
        eventSP.setContent(eventGrid);

        /* Continuously checks for button (event) interaction */
        for(int i = 0; i < events.length; i++) {
            final int selectedTimelineIndex = i;
            timelineBtnList.get(i).setOnAction(actionEvent -> {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete the event: " +
                        timelineBtnList.get(selectedTimelineIndex).getText() + "?");

                /* If the user confirms the deletion, delete the event and refresh the menu */
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == ButtonType.OK) {

                    try{
                        if(events[selectedTimelineIndex].getImagePath()!=null) {
                            File deleteFile = new File(events[selectedTimelineIndex].getImagePath());
                            if (events[selectedTimelineIndex].getImagePath() != null && deleteFile.delete()) {
                                EventsHandler.deleteEvent(timelineBtnList.get(selectedTimelineIndex).getText());
                            }
                        }
                        else{
                            EventsHandler.deleteEvent(timelineBtnList.get(selectedTimelineIndex).getText());
                        }

                    } catch (Exception e){
                        e.printStackTrace();
                    }

                    try {
                        MainMenu.window.setScene(TimelineMenu.editTimeline());
                    } catch(IOException | SQLException e) {
                        e.printStackTrace();
                    }
                    /*
                    you should be sent to the delete event menu!!
                     */
                    fileName=null;
                    imageDirString=null;


                }
            });
        }

        /* Changes the scene to the editTimeline menu */
        backBtn.setOnAction(actionEvent -> {
            try {
                MainMenu.window.setScene(TimelineMenu.editTimeline());
            } catch(IOException | SQLException e) {
                e.printStackTrace();
            }
        });

        /* HBox & VBox containers */
        HBox logoAndTitle = new HBox(logoImageView, titleLabel);
        logoAndTitle.setAlignment(Pos.CENTER_LEFT);
        logoAndTitle.setSpacing(15);
        logoAndTitle.setPadding(new Insets(25, 0, 0, 20));

        HBox topRow = new HBox(logoAndTitle, signInBtn);
        topRow.setSpacing(225);

        HBox scrollPaneBox = new HBox(eventSP);
        scrollPaneBox.setAlignment(Pos.BASELINE_CENTER);

        VBox root = new VBox(topRow, scrollPaneBox, backBtn);

        StackPane stackPane = new StackPane(root);
        stackPane.setStyle(cssLightBlue);
        return new Scene(stackPane);
    }

    /* Scene to  add an event - by Hossin and Bartek*/
    public static Scene eventAdd() throws FileNotFoundException {

        /* Logo with back to main menu function*/
        Image logo = new Image(new FileInputStream("graphic/tsiwira.png"));
        ImageView logoImageView = new ImageView(logo);
        logoImageView.setFitWidth(50);
        logoImageView.setFitHeight(50);
        Button logoButton = new Button();
        logoButton.setStyle("-fx-background-color: #587dd1");
        logoButton.setGraphic(logoImageView);
        HBox logoBox = new HBox(logoButton);
        logoBox.setAlignment(Pos.CENTER_LEFT);

        /* Back to main menu */
        logoButton.setOnAction(actionEvent -> {
            try {
                MainMenu.backToMainMenu();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        /* Button with arrow image */
        Image arrow = new Image(new FileInputStream("graphic/arrow.png"));
        ImageView arrowView = new ImageView(arrow);
        arrowView.setFitWidth(30);
        arrowView.setFitHeight(30);

        Button arrowButton = new Button();
        arrowButton.setGraphic(arrowView);
        arrowButton.setStyle("-fx-background-color: #587dd1");

        /* Arrow send you back to the current timeline */
        arrowButton.setOnAction(actionEvent -> {
            try {
                MainMenu.window.setScene(TimelineMenu.editTimeline());
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        });

        //////////////////////////////////////////

        /* column in the middle with labels and fields */

        /* title */
        Label title = new Label("Title:");
        title.setFont(Font.font("Open Sans", 12));

        TextField titleField = new TextField();
        titleField.setStyle("-fx-border-color: #000000; -fx-border-width: 0.5px; -fx-background-color: #FFFFFF");
        titleField.setPrefWidth(180);

        VBox titleBox = new VBox(title,titleField);

        /* date */
        Label date = new Label("Date:");
        date.setFont(Font.font("Open Sans", 12));

        TextField dateField = new TextField();
        dateField.setPrefWidth(115);
        dateField.setStyle("-fx-border-color: transparent; -fx-border-width: 0.5px; -fx-background-color: #FFFFFF");

        ComboBox<String> dateType = new ComboBox<>();
        dateType.getItems().addAll("A.D.", "B.C.");
        dateType.setValue("A.D.");
        dateType.setStyle("-fx-font: 10px \"Open Sans\"");
        dateType.setStyle("-fx-border-color: transparent; -fx-border-width: 0.5px; -fx-background-color: #FFFFFF");
        dateType.setPrefWidth(65);

        BorderPane dateFieldBox = new BorderPane();       //text field and combo box in the same line
        dateFieldBox.setLeft(dateField);
        dateFieldBox.setRight(dateType);
        dateFieldBox.setMinHeight(30);
        dateFieldBox.setMaxHeight(30);
        dateFieldBox.setStyle("-fx-border-color: #000000; -fx-border-width: 0.5px; -fx-background-color: #FFFFFF");


        VBox dateBox = new VBox(date, dateFieldBox);

        /* keyword *//* Keywords should not be added to events as discussed previously
        Label keyword = new Label("Keyword");
        keyword.setFont(Font.font("Open Sans", 12));

        TextField keywordField = new TextField();
        keywordField.setStyle("-fx-border-color: #000000; -fx-border-width: 0.5px; -fx-background-color: #FFFFFF");
        keywordField.setPrefWidth(180);

        VBox keywordBox = new VBox(keyword,keywordField); */

        /* description */
        Label description = new Label("Description");
        description.setFont(Font.font("Open Sans", 12));

        TextField descriptionField = new TextField();
        descriptionField.setStyle("-fx-border-color: #000000; -fx-border-width: 0.5px; -fx-background-color: #FFFFFF");
        descriptionField.setPrefWidth(180);

        VBox descriptionBox = new VBox(description,descriptionField);

        /* summary *//*
        Label summary = new Label("Summary");
        summary.setFont(Font.font("Open Sans", 12)); */

        TextField summaryField = new TextField();
        summaryField.setStyle("-fx-border-color: #000000; -fx-border-width: 0.5px; -fx-background-color: #FFFFFF");
        summaryField.setPrefWidth(180);

        //VBox summaryBox = new VBox(summary,summaryField); //A summary and a description is essentially the same thing.

        /**
         * This section handles image storing.
         * @author Melker F�lt
         * @date 2020-05-12
         */

        /* Image directory path label */
        Label imageDirLabel = new Label("");
        imageDirLabel.setFont(Font.font("Open Sans", 12));
        imageDirLabel.setWrapText(true);

        /* Add image button */
        Button addImage = new Button("Add Image");
        addImage.setPrefSize(80,20);
        addImage.setAlignment(Pos.CENTER);
        addImage.setStyle("-fx-background-color: #aac0f2");
        addImage.setFont(Font.font("Open Sans", 12));
        addImage.setContentDisplay(ContentDisplay.TOP);

        /* Creates the file chooser used for image selection and limits said selection (png/jpg)*/
        FileChooser fC = new FileChooser();
        fC.setTitle("Image Selector");
        fC.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("JPEG",".jpeg"));

        addImage.setOnAction(actionEvent -> {
            File file = fC.showOpenDialog(MainMenu.window);
            fC.setInitialFileName("defaultSaveName");
            if(file != null) {
                imageDirString = file.getAbsolutePath();  //Stores the file path for the selected image.
                imageDirPath = Paths.get(imageDirString); //Creates a path based on the absolute path URL.
                fileName = file.getName();

                imageDirLabel.setText(fileName); //Sets the label's text to the currently selected image's name.
            }
        });

        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\\

        HBox addImageBox = new HBox(addImage);
        addImageBox.setPrefWidth(180);
        addImageBox.setAlignment(Pos.CENTER_RIGHT);

        //////////////////////////////////////////

        /* column on the right side with username and save button */

        /* Label with username and image */
        Image signInImage = new Image(new FileInputStream("graphic/signin.png"));
        ImageView signInImageView = new ImageView(signInImage);
        signInImageView.setFitHeight(30);
        signInImageView.setFitWidth(30);

        Label username = new Label();
        username.setFont(Font.font("Open Sans ", 13.7));
        username.setText(UserMain.getCurrentUserName());
        username.setGraphic(signInImageView);
        username.setContentDisplay(ContentDisplay.RIGHT);
        username.setPadding(new Insets(15, 0, 0, 0));

        /* save button */
        Button save = new Button("Save");
        save.setPrefSize(100,30);
        save.setAlignment(Pos.CENTER);
        save.setStyle("-fx-background-color: #aac0f2");
        save.setFont(Font.font("Open Sans",FontWeight.BOLD, 13));
        save.setContentDisplay(ContentDisplay.TOP);

        HBox saveBox = new HBox(save);
        saveBox.setPrefWidth(180);
        saveBox.setAlignment(Pos.CENTER_RIGHT);
        saveBox.setPadding(new Insets(0, 55, 0, 0));

        /////////////////////////////////////////////////////////////////////////


        /* Action events for buttons - place for Hossin and his code */

        // A.D. and B.C. combo box with some simple code in the beginning
        dateType.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println("Selected value : " + newValue);
            }
        });

        /* The save button which, when pressed, gathers all user entered event details and sends it to the database for storage */
        save.setOnAction(actionEvent -> {
            Box event = new Box(titleField.getText(),descriptionField.getText(),dateField.getText());
            int timelineID= currentTimelineObject.getTimelineID();

            /* Checks if a file (image) has been selected (i.e. fileName isn't null) */
            if(fileName!=null) {
                /* Tries to add the selected file to the standard image storage folder */
                Path target = standardDirPath.resolve(fileName);

                try {
                    Files.copy(imageDirPath, target); //Copies the selected image to the standard directory within the application.
                    File oldFileName = new File(target.toString()); //Gets the selected image name.
                    String newFileName = UserMain.getCurrentUserName() + currentTimelineObject.getTitle() + rng.nextInt(1000) + fileName; //Generates a new name based on the current user, timeline random nmbr and the original filename.

                    File newFile = new File(standardDirPath + "\\" + newFileName); //A new file with the updated name (that's going to replace the old image).
                    oldFileName.renameTo(newFile); //Renames the existing file.

                    EventsHandler.addTimelineEvent(event,timelineID, newFile.toString()); //Adds the image path to the database.
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            /* If no image has been added the event is added and the path set to "null" */
            else {
                EventsHandler.addTimelineEvent(event, timelineID, null);
            }

            //Clearing all TextFields
            fileName=null;
            titleField.clear();
            descriptionField.clear();
            dateField.clear();
            imageDirString=null;
            imageDirLabel.setText("");
        });

        /////////////////////////////////////////////////////////////////////

        VBox leftSide = new VBox(logoBox,arrowButton);
        leftSide.setAlignment(Pos.TOP_LEFT);
        leftSide.setSpacing(300);
        leftSide.setPrefWidth(210);
        leftSide.setPadding(new Insets(20,0,10,20));

        VBox middle = new VBox(titleBox, dateBox, descriptionBox, addImageBox, imageDirLabel);
        middle.setAlignment(Pos.TOP_CENTER);
        middle.setSpacing(10);
        middle.setPadding(new Insets(80,0,10,0));

        VBox rightSide = new VBox(username, saveBox);
        rightSide.setAlignment(Pos.TOP_RIGHT);
        rightSide.setSpacing(230);
        rightSide.setPrefWidth(210);
        rightSide.setPadding(new Insets(20,20,10,0));

        HBox root = new HBox(leftSide, middle, rightSide);
        StackPane stackPane = new StackPane(root);      //StackPane for custom background
        stackPane.setStyle("-fx-background-color: #587dd1");
        stackPane.setMaxSize(640,480);
        return new Scene(stackPane);
    }

    /*A method that creates 2 time lines with events, used in edit timeline for testing purposes-Hossin */
    public static void demo() {

        /* create temperory Timelines and add them to a list*/
        TimeLine bigBang = new TimeLine("", "", "Big bang");
        bigBang.addBox(new Box("Big Bang", "The beginning of the universe", "13 800 000 000 years ago"));
        bigBang.addBox(new Box("First life", "The first life on earth", "4 540 000 000 years ago"));
        bigBang.addBox(new Box("Dinosaur era", "When the dinosaurs lived on earth", "230 000 000 years ago"));
        bigBang.addBox(new Box("Today", "The day of this presentation", "2020-04-22"));

        TimeLine school = new TimeLine("24-08-2015", "24-05-2022", "school");
        school.addBox(new Box("start", "school start", "24-05-2022"));
        school.addBox(new Box("end", "school end", "24-05-2022"));

        ArrayList<TimeLine> timeLinesArray = new ArrayList<TimeLine>();
        timeLinesArray.add(bigBang);timeLinesArray.add(school);

        for(TimeLine t : timeLinesArray){

            availableUsersTimelinesObj.add(t);
        }
        for(TimeLine t : availableUsersTimelinesObj){

            availableUsersTimelines.add(t.getTitle());
        }
    }

    /* Scene with single event in view mode - opens when event title is clicked */
    public static Scene eventMenu (Box current)throws FileNotFoundException {

        //left side of the window

        /* Logo with back to main menu function*/
        Image logo = new Image(new FileInputStream("graphic/tsiwira.png"));
        ImageView logoImageView = new ImageView(logo);
        logoImageView.setFitWidth(50);
        logoImageView.setFitHeight(50);
        Button logoButton = new Button();
        logoButton.setGraphic(logoImageView);
        logoButton.setStyle("-fx-background-color: #FFFFFF");
        HBox logoBox = new HBox(logoButton);
        logoBox.setPrefWidth(200);

        /* Back to main menu */
        logoButton.setOnAction(actionEvent -> {
            try {
                MainMenu.backToMainMenu();
                MainMenu.window.setMaximized(false);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        /* Formatted circle on the left side */
        Circle circle = new Circle();
        circle.setRadius(90);

        if(UserMain.getIsLogged()==1) {
            if (current.getImagePath() != null) {
                Image view = new Image(new FileInputStream(current.getImagePath()));
                circle.setFill(new ImagePattern(view));

            } else {
                circle.setStyle("-fx-fill: #aac0f2");
            }
        }
        else {
            circle.setFill(new ImagePattern(current.getBoxImage()));
        }

        /* Button with arrow image */
        Image arrow = new Image(new FileInputStream("graphic/arrow.png"));
        ImageView arrowView = new ImageView(arrow);
        arrowView.setFitWidth(30);
        arrowView.setFitHeight(30);

        Button arrowButton = new Button();
        arrowButton.setGraphic(arrowView);
        arrowButton.setStyle("-fx-background-color: #FFFFFF");

        /* Arrow send you back to the current timeline */
        arrowButton.setOnAction(actionEvent -> {
            try {
                if (selectedTimelines.size() < 2) {
                    MainMenu.window.setScene(TimelineMenu.viewSingleTimeline());
                    MainMenu.window.setMaximized(true);
                }

                else if (selectedTimelines.size() > 1){
                    MainMenu.window.setScene(TimelineMenu.viewMultiTimeline());
                    MainMenu.window.setMaximized(true);
                }
            } catch (FileNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        });

        /* Label with username */
        Label username = new Label();
        username.setFont(Font.font("Open Sans ", 13.7));
        if(UserMain.getIsLogged() == 1){
            username.setText(UserMain.getCurrentUserName());    //if logged = current username
        } else {
            username.setText("Username");       //if not logged = "Username"
        }

        /* Border Pane with arrow and username */
        BorderPane leftBottom = new BorderPane();
        leftBottom.setLeft(arrowButton);
        leftBottom.setCenter(username);

        /* VBox with left side nodes */
        BorderPane leftSide = new BorderPane();
        leftSide.setTop(logoBox);
        leftSide.setCenter(circle);
        leftSide.setBottom(leftBottom);
        leftSide.setMinSize(400,980);
        leftSide.setMaxSize(400,980);
        leftSide.setStyle("-fx-background-color: #FFFFFF");
        leftSide.setPadding(new Insets(20,20,20,20));

        //right side of the window

        /* Separate text dor title and date */
        Text titleText = new Text(current.getTitle());
        titleText.setFont(Font.font("Open Sans", 20));
        titleText.setFill(Paint.valueOf("#587dd1"));
        Text dateText = new Text(" - " +current.getBoxDate());
        dateText.setFont(Font.font("Open Sans", 20));

        /* TextFlow (handles different colors) with title and date */
        TextFlow textFlowPane = new TextFlow();
        textFlowPane.getChildren().addAll(titleText, dateText);
        textFlowPane.setPadding(new Insets(80,0,30,10));

        /* Label with text "description" */
        Label descriptionTitle = new Label("Description");
        descriptionTitle.setFont(Font.font("Open Sans", FontWeight.EXTRA_BOLD,14));
        descriptionTitle.setPadding(new Insets(0,0,0,10));
        descriptionTitle.setMaxWidth(1480);
        descriptionTitle.setWrapText(true);


        /* Label with actual description */
        Text description = new Text(current.getDesc());
        description.setWrappingWidth(1480);

        ScrollPane descSrollPane = new ScrollPane(description);
        descSrollPane.setStyle("-fx-background: #aac0f2; -fx-background-color: #aac0f2");
        descSrollPane.setPrefHeight(200);
        descSrollPane.setPadding(new Insets(0,0,0,10));

        /* VBox with right side nodes */
        VBox rightSide = new VBox(textFlowPane,descriptionTitle,descSrollPane);
        rightSide.setSpacing(25);
        rightSide.setMinWidth(1520);
        rightSide.setPadding(new Insets(20,20,20,0));
        rightSide.setStyle("-fx-background-color: #aac0f2");

        /* root */
        HBox root = new HBox(leftSide,rightSide);
        return new Scene(root);
    }

}
