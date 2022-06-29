package timeLineDisplay;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import main.MainMenu;
import main.TimelineMenu;
import main.UserMain;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class TimeLineCanvas extends Application {
    private ArrayList<VBox> topArray = new ArrayList<>();
    private ArrayList<VBox> bottomArray = new ArrayList<>();
    private ArrayList<Box> boxes = new ArrayList<>();
    private HBox upperLine = new HBox();
    private HBox lowerLine = new HBox();
    private String title; 
    
    private int initialDate;
    private int finalDate;


    /**
     * A method that adds a new box to this timeline.
     * Author: Melker Fält
     * Date: 2020-04-15
     * @param
     * @param
     *
     *
     *
     */
    public void setTitle(String newTitle) {
    	title = newTitle; 
    }

    public void addBoxDetailed(Box aBox, int pos) throws FileNotFoundException {





    	/* Label with formatted event date */
        Label eventDate = new Label(aBox.getBoxDate());
        eventDate.setAlignment(Pos.CENTER);
        eventDate.setMinSize(150,20);
        eventDate.setMaxSize(150,20);
        eventDate.setFont(Font.font("Open Sans", 18));
        eventDate.setStyle("-fx-text-fill: #587dd1");

        /* Label with formatted event description */
        Label eventDescription = new Label(aBox.getDesc());
        eventDescription.setWrapText(true);
        eventDescription.setAlignment(Pos.CENTER);
        eventDescription.setMinSize(150,50);
        eventDescription.setMaxSize(150,50);
        eventDescription.setFont(Font.font("Open Sans", 12.9));

        /* Button with event title */
        Label eventTitle = new Label(aBox.getTitle());
        eventTitle.setFont(Font.font("Open Sans", 19.7));
        eventTitle.setAlignment(Pos.CENTER);
        eventTitle.setMinSize(150,30);
        eventTitle.setMaxSize(150,30);

        /* Formatted fleche in horizontal position */
    	Image fleche = new Image(new FileInputStream("graphic/fleche.png"));
    	ImageView flecheHorizontal= new ImageView(fleche);
        flecheHorizontal.setPreserveRatio(true);
        flecheHorizontal.setFitWidth(100);

        /* Formatted fleche in vertical position */
    	ImageView flecheVertical = new ImageView(fleche);
        flecheVertical.setPreserveRatio(true);
        flecheVertical.setFitWidth(100);
        flecheVertical.setRotate(90);

        /* Formatted circle as event icon */
        Button circle = new Button();
        Circle c = new Circle(40);
        //
        circle.setMinSize(80,80);
        circle.setMaxSize(80,80);
        //
if(UserMain.getIsLogged()==1) {
    if (aBox.getImagePath() != null) {

        Image tempImage = new Image(new FileInputStream(aBox.getImagePath()));
        // ImageView tempIV = new ImageView (tempImage);
        c.setFill(new ImagePattern(tempImage));
        circle.setGraphic(c);
        circle.setStyle("-fx-background-color: #aac0f2;" +
                "-fx-background-radius: 90px;" + //90
                "-fx-min-width: 80;" + //175
                "-fx-min-height: 80;" +
                "-fx-max-width: 80;" +
                "-fx-max-height: 80;");

    } else {
        ImageView tempIV = new ImageView(aBox.getBoxImage());
        circle.setShape(c);
        circle.setStyle("-fx-background-color: #587dd1");
    }
}
else{
    Image tempImage = aBox.getBoxImage();
    c.setFill(new ImagePattern(tempImage));
    circle.setGraphic(c);
    circle.setStyle("-fx-background-color: #aac0f2;" +
            "-fx-background-radius: 90px;" + //90
            "-fx-min-width: 80;" + //175
            "-fx-min-height: 80;" +
            "-fx-max-width: 80;" +
            "-fx-max-height: 80;");

}


        circle.setOnAction(actionEvent -> {
            try {
                MainMenu.window.setScene(TimelineMenu.eventMenu(aBox));
                MainMenu.window.setMaximized(true);
                MainMenu.window.centerOnScreen();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });


        /* Stack Pane with circle, flecheVertical and event date on custom places */
        StackPane buttonPane = new StackPane();
        buttonPane.setMinSize(100,140);
        buttonPane.setMaxSize(100,140);
        buttonPane.getChildren().addAll(flecheVertical,circle,eventDate);

        /* Whole Scene is build with help of 5 HBoxes inside VBox */

        /* first row of the scene*/
        HBox topTitleBox = new HBox();
        topTitleBox.setMaxHeight(30);
        topTitleBox.setMinHeight(30);
        topTitleBox.setAlignment(Pos.CENTER);

        /* second row of the scene*/
        HBox topDescriptionBox = new HBox();
        topDescriptionBox.setMaxHeight(50);
        topDescriptionBox.setMinHeight(50);
        topDescriptionBox.setAlignment(Pos.CENTER);

        /* third row of the scene*/
        HBox eventButtonBox = new HBox(buttonPane);     //StackPane with circle button and fleche
        eventButtonBox.setMaxHeight(200);
        eventButtonBox.setMinHeight(200);
        eventButtonBox.setAlignment(Pos.CENTER);

        /* fourth row of the scene */
        HBox bottomTitleBox = new HBox();
        bottomTitleBox.setMaxHeight(30);
        bottomTitleBox.setMinHeight(30);
        bottomTitleBox.setAlignment(Pos.CENTER);

        /* fifth row of the scene */
        HBox bottomDescriptionBox = new HBox();
        bottomDescriptionBox.setMaxHeight(50);
        bottomDescriptionBox.setMinHeight(50);
        bottomDescriptionBox.setAlignment(Pos.CENTER);

        /* When box position is even topTitle and topDescription boxes stays empty.
         * eventButtonBox has date on the top, button in the middle and flecheVertical in bottom.
         * bottomTitleBox and bottomDescription are filled with respective nodes
         */
        if (pos % 2 == 0){
            StackPane.setAlignment(circle,Pos.CENTER);
            StackPane.setAlignment(flecheVertical,Pos.BOTTOM_CENTER);
            StackPane.setAlignment(eventDate,Pos.TOP_CENTER);
            bottomTitleBox.getChildren().add(eventTitle);
            bottomDescriptionBox.getChildren().add(eventDescription);
        }
        /* When box position is odd bottomTitle and bottomDescription boxes stays empty.
         * eventButtonBox has date in the bottom, button in the middle and flecheVertical on the top.
         * bottomTitleBox and bottomDescription are filled with respective nodes
         */
        else if (pos % 2 == 1){
            StackPane.setAlignment(circle,Pos.CENTER);
            StackPane.setAlignment(flecheVertical,Pos.TOP_CENTER);
            StackPane.setAlignment(eventDate,Pos.BOTTOM_CENTER);
            topTitleBox.getChildren().addAll(eventTitle);
            topDescriptionBox.getChildren().add(eventDescription);
        }

        /*Vbox with each line of single event */
        VBox eventVBox = new VBox(topTitleBox,topDescriptionBox,eventButtonBox,bottomTitleBox,bottomDescriptionBox);
        eventVBox.setPrefSize(150,360);
        eventVBox.setAlignment(Pos.CENTER);

        /*Hbox with eventVBox and fleche */
        HBox hBox = new HBox();
        hBox.setPrefSize(150,360);
        hBox.setAlignment(Pos.CENTER);

        /* First event on the timeline don't have fleche on the left side */
        if (pos == 0)
            hBox.getChildren().add(eventVBox);
        else
            hBox.getChildren().addAll(flecheHorizontal,eventVBox);

        /* root (event) added to the Timeline */
        VBox root = new VBox(hBox);
        topArray.add(root);
    	upperLine.getChildren().add(topArray.get(pos));    	
    }

    public void addBoxSimple(Box aBox, int pos) throws FileNotFoundException {
       // ImageView tempIV = new ImageView(aBox.getBoxImage());

        /* Label with formatted event date */
        Label eventDate = new Label(aBox.getBoxDate());
        eventDate.setAlignment(Pos.CENTER);
        eventDate.setMinSize(150,20);
        eventDate.setMaxSize(150,20);
        eventDate.setFont(Font.font("Open Sans", 12));
        eventDate.setStyle("-fx-text-fill: #587dd1");

        /* Formatted fleche in horizontal position */
        Image fleche = new Image(new FileInputStream("graphic/fleche.png"));
        ImageView flecheHorizontal= new ImageView(fleche);
        flecheHorizontal.setPreserveRatio(true);
        flecheHorizontal.setFitWidth(100);


        /* Formatted circle as event button */
        Button circle = new Button();
        Circle c = new Circle(40);
        //
        circle.setMinSize(80,80);
        circle.setMaxSize(80,80);
        //
if(UserMain.getIsLogged()==1) {
    if (aBox.getImagePath() != null) {

        Image tempImage = new Image(new FileInputStream(aBox.getImagePath()));
        // ImageView tempIV = new ImageView (tempImage);
        c.setFill(new ImagePattern(tempImage));
        circle.setGraphic(c);
        circle.setStyle("-fx-background-color: #aac0f2;" +
                "-fx-background-radius: 90px;" + //90
                "-fx-min-width: 80;" + //175
                "-fx-min-height: 80;" +
                "-fx-max-width: 80;" +
                "-fx-max-height: 80;");

    } else {
        ImageView tempIV = new ImageView(aBox.getBoxImage());
        circle.setShape(c);
        circle.setStyle("-fx-background-color: #587dd1");
    }
}
else {
    Image tempImage = aBox.getBoxImage();
    c.setFill(new ImagePattern(tempImage));
    circle.setGraphic(c);
    circle.setStyle("-fx-background-color: #aac0f2;" +
            "-fx-background-radius: 90px;" + //90
            "-fx-min-width: 80;" + //175
            "-fx-min-height: 80;" +
            "-fx-max-width: 80;" +
            "-fx-max-height: 80;");

}

        circle.setOnAction(actionEvent -> {
            try {
                MainMenu.window.setScene(TimelineMenu.eventMenu(aBox));
                MainMenu.window.setMaximized(true);
                MainMenu.window.centerOnScreen();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        /* Stack Pane with circle, flecheVertical and event date on custom places */
        StackPane buttonPane = new StackPane();
        buttonPane.setMinSize(100,120);
        buttonPane.setMaxSize(100,120);
        buttonPane.getChildren().addAll(circle,eventDate);

        Label titel = new Label(aBox.getTitle());
        titel.setFont(Font.font("Open Sans", 10));
        titel.setMaxWidth(100);
        titel.setMaxHeight(20);
        titel.setAlignment(Pos.CENTER);

        VBox eventBox = new VBox();
        eventBox.setAlignment(Pos.CENTER);

        if (pos % 2 == 0){
            StackPane.setAlignment(circle,Pos.CENTER);
            StackPane.setAlignment(eventDate,Pos.TOP_CENTER);
            eventBox.getChildren().addAll(buttonPane,titel);

        }

        else if (pos % 2 == 1){
            StackPane.setAlignment(circle,Pos.CENTER);
            StackPane.setAlignment(eventDate,Pos.BOTTOM_CENTER);
            eventBox.getChildren().addAll(titel,buttonPane);
        }

        /*Hbox with buttonPane and fleche */
        HBox hBox = new HBox();
        //hBox.setPrefSize(150,360);
        hBox.setAlignment(Pos.CENTER);

        Label timelineTitle = new Label(title);               
        timelineTitle.setFont(Font.font("Open Sans", FontWeight.EXTRA_BOLD, 13.7));

        /* First event on the timeline don't have fleche on the left side
         * First event on the timeline have timeline title on the left side */
        if (pos == 0)
            hBox.getChildren().addAll(timelineTitle, eventBox);
        else
            hBox.getChildren().addAll(flecheHorizontal,eventBox);

        /* root (event) added to the Timeline */
        VBox root = new VBox(hBox);
        topArray.add(root);
        upperLine.getChildren().add(topArray.get(pos));
    }
    
    // decides upper or bottom position of box placement in setBox method.(not used)
    // private boolean upOrDown = true;
    public void setBox() {
        int i = -1;
        int previousDate = 0;
        for (Box o : boxes) {
            int date2 = Integer.parseInt(String.valueOf(o.getBoxDate()).substring(4, 8)); // Get the last 4 digits of date.
            if (date2 == initialDate) {
                i++;
                topArray.add(new VBox(new Text(o.getTitle()), new ImageView(o.getBoxImage()),
                        new Text(o.getDesc()), new Text(o.getDesc()),
                        new Text(String.valueOf(o.getBoxDate()))));
                upperLine.getChildren().add(topArray.get(i));
                previousDate = date2;
            } else if (date2 > initialDate && date2 <= finalDate) {
                while (previousDate != date2) {
                    i++;
                    previousDate += 1;
                    topArray.add(new VBox());
                    topArray.get(i).setMinSize(100,100);
                    upperLine.getChildren().add(topArray.get(i));
                }
                i++;
                topArray.add(new VBox(new Text(o.getTitle()), new ImageView(o.getBoxImage()),
                        new Text(o.getDesc()),
                        new Text(String.valueOf(o.getBoxDate()))));
                upperLine.getChildren().add(topArray.get(i));
            }
        }
    }
    
    public void getInitialDateAndFinalDate() {
        int smallest = 0;
        int largest = 0;
        for (Box o: boxes) {
            int date2 = Integer.parseInt(String.valueOf(o.getBoxDate()).substring(4, 8)); // Get the last 4 digits of date.

            // sort the years
            if (smallest == 0) {
                smallest = date2;
            } else if (date2 < smallest) {
                smallest = date2;
            } else if (date2 > largest) {
                largest = date2;
            }
        }
        initialDate = smallest;
        finalDate = largest;
    }


    public static void main(String[] args) {

        launch(args);
    }
    
    /**
     * gets the topArray and creates an Canvas of it. 
     * method author Adam Nyman
     */
    public VBox getCanvas() {
    	HBox theBoxes = new HBox();
    	theBoxes.setStyle("-fx-background-color: #aac0f2");

        //background for entire row when resolution 1920x1080 - without this line background was white for timelines with less events
    	theBoxes.setMinWidth(1920);

    	for(int i = 0; i < topArray.size(); i++) {
    		theBoxes.getChildren().add(topArray.get(i)); 
    	} 
    	VBox theCanvas = new VBox(theBoxes);
    	theCanvas.setStyle("-fx-background-color: #aac0f2");
    	return theCanvas; 
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        //Oliver part of this stage

    	/*
        boxes.add(new Box("Hello World", test1,
                "testOne", "TestTitleOne", 12042010));

        boxes.add(new Box("Hello World Two", test2,
                "testTwo", "TestTitleTwo", 12042015));

        boxes.add(new Box("Hello World Three", test3,
                "testThree", "TestTitleThree", 12042020));*/

        getInitialDateAndFinalDate();
        setBox();

        // Note from Hossin : I think this part needs to be a loop that creates multiple Vboxes and assigns The position
        // of each vVboxlayoutX of the Vbox node,maybe using Anchore or stack pane or something
        // because this lists all Boxes in a single Vbox .
        VBox lineContainer = new VBox();
        lineContainer.getChildren().addAll(upperLine, lowerLine);
        lineContainer.setLayoutX(400 - lineContainer.getLayoutBounds().getMinX());
        
        lineContainer.setAlignment(Pos.CENTER); //Added an alignment change (Melker F�lt).
        ///////////////


        //Hossin part of this stage
        // just for testing
        //TimeLine exaTimeline = new TimeLine(10102015,10102016,"school");
        //TimeLine exaTimeline1 = new TimeLine(10102016,10102022,"school");


        Line line = new Line();
        Line line2 = new Line();

        // make window fill the screen part 1
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        ////

        Canvas canvas = new Canvas(1500,900);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        /*
        gc.setStroke();
        gc.setLineWidth( 1);
        gc.strokeLine(exaTimeline.getStartDate()-100000000, 100, exaTimeline.getEndDate()-10000000, 100);
        gc.strokeLine(exaTimeline1.getStartDate()-100000000, 300, exaTimeline1.getEndDate()-10000000, 300);
        //additional  plain line for testing
        gc.strokeLine(250, 500, 600, 500);
        */
        
        StackPane root = new StackPane();



        root.getChildren().addAll(canvas, lineContainer);

        // not used for now
       // BorderPane all = new BorderPane();
//
       // all.setTop(canvas);
       // all.setCenter(lineContainer);


        Scene scene = new Scene(root);


        // make window fill the screen part 2

        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());
        ////

      // stage.addEventHandler(ScrollEvent.SCROLL,event -> {
      //     double delta = event.getDeltaY();
      //     root.translateZProperty().set(root.getTranslateZ() + delta);
      // });
        stage.addEventHandler(ScrollEvent.SCROLL,event -> {
        double zoomFactor = 1.05;
        double deltaY = event.getDeltaY();

        if (deltaY < 0){
            zoomFactor = 0.95;
        }
        root.setScaleX(root.getScaleX() * zoomFactor);
        root.setScaleY(root.getScaleY() * zoomFactor);
        event.consume();
    });

        stage.setTitle("Time lines");

        stage.setScene(scene);

        stage.show();

    }

}

