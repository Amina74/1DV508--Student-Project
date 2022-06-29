package timelineHandling;

import main.UserMain;
import timeLineDisplay.Box;
import userHandling.ConnectionMaker;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class EventsHandler {


    /**
     * Connect and store a timeline event to the SQL database.
     * @param box The event that is going to be stored.
     */
    public static void addTimelineEvent(Box box, int timelineID, String path){

        String title = box.getTitle();
        String desc = box.getDesc();
        String date = box.getBoxDate();
        try {
            // SQL query. Adding event title,desc and date to the events table
            String sql = "insert into events (event_title, event_desc, event_date,event_image, fk_timeline_id) ";
            sql += " values (?,?,?,?,?)";

            // Connection and inserting the box title in the 'events' table
            PreparedStatement ps = ConnectionMaker.prepareStatement(sql);

            // Inserting the locally stored varaibles to the query
            ps.setString(1,title);
            ps.setString(2,desc);
            ps.setString(3,date);
            ps.setString(4,path);
            ps.setInt(5,timelineID);

            // Executing and uploading the data to the database
            ps.execute();

        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Connect and store a timeline event with pic to the SQL database.
     * @param box The event that is going to be stored.
     */
    /*public static void addTimelineEventWithPic(Box box, int timelineID, String path){

        String title = box.getTitle();
        String desc = box.getDesc();
        String date = box.getBoxDate();
        //edit this to be provided as a string path
        try {
            // SQL query. Adding event title,desc and date to the events table
            String sql = "insert into events (event_title, event_desc, event_date, event_image, fk_timeline_id) ";
            sql += " values (?,?,?,?,?)";

            // Connection and inserting the box title in the 'events' table
            PreparedStatement ps = ConnectionMaker.prepareStatement(sql);

            // Inserting the locally stored varaibles to the query
            ps.setString(1,title);
            ps.setString(2,desc);
            ps.setString(3,date);
            ps.setString(4,path);

            ps.setInt(5,timelineID);

            // Executing and uploading the data to the database
            ps.execute();

        } catch(SQLException e){
            e.printStackTrace();
        }
    }*/
    /**
     * Retrvive all data related to a specific event from the MYSQL database using the event_title as
     * the identifier.
     * @param
     */
    public static Box [] getTimeLineEvents(int timelineID) throws SQLException {
        //adjust this so it can give back timelines with pictures !!
        // by creating a string that is null then using the box constructor that has images to creat a box
        int numberOFEvents=0;
        String countQuery = "SELECT  event_id FROM events WHERE fk_timeline_id = ?";
        PreparedStatement psCount = ConnectionMaker.prepareStatement(countQuery);
        psCount.setInt(1,timelineID);
        ResultSet rsCount = psCount.executeQuery();
        while (rsCount.next()){
            numberOFEvents++;
        }

        // SQL query. Retriving all the event data from the SQL database.
            String query = "SELECT event_title, event_id, event_date, event_desc, event_image FROM events WHERE fk_timeline_id = ?";

            // Connection and inserting the box title in the 'events' table
            PreparedStatement ps = ConnectionMaker.prepareStatement(query);

            // Add the eventtitle to the sql query
            ps.setInt(1,timelineID);

            // The data retrived from the database is stored in the ResultSet data type.
            ResultSet rs = ps.executeQuery();
            Box [] events = new Box [numberOFEvents];
            String eventDate=null;
            String eventDesc=null;
            String eventTitle=null;
           String eventImage=null;
           int eventID=0;
int i =0;

           while(rs.next()){
                eventID=rs.getInt("event_id");
                eventDate=rs.getString("event_date");
                eventDesc= rs.getString("event_desc");
                eventTitle= rs.getString("event_title");
                eventImage = rs.getString("event_image");
               Box event = new Box(eventTitle,eventDesc,eventDate,eventID,eventImage);
               events[i]= event;
               i++;
            }


           return events;

    }
    /**
     * viewEventList will check the database for timeline titles and return it
     * according to the user and what timelines they have
     *
     * @return list with the names of timelines for an owner
     * @throws SQLException
     * @author Amin Marteni
     */
    public static ArrayList<String> viewEventList(int timelineID) throws SQLException {

        // Declare a PreparedStatement and a ResultSet
        PreparedStatement st;
        ResultSet rs = null;

        //to test we just take a fixed userID
        // int userID = 1;

        //Initializing SQL insert statement as a String .
        String sql = "select event_title from events  where fk_timeline_id = ?";

        //Establishing connection to the server via ConnectionMaker
        st = ConnectionMaker.prepareStatement(sql);
        st.setInt(1, timelineID);

        //Executing the statement to get a result set
        rs = st.executeQuery();

        ArrayList<String> eventTitle = new ArrayList<>();

        while (rs.next()) {
            eventTitle.add(rs.getString("event_title"));
        }
        return eventTitle;
    }

    /**
     * Deletes a single event from the MYSQL Database and all associated data
     * such as date, title, desc and the id.
     * @param eventTitle The ID of the event that will be removed from the database
     */
    public static void deleteEvent(String eventTitle) {
        try {
            // SQL statement to delete all stored data connected to the eventTitle
            String sql = "Delete  FROM events WHERE event_title =? ";

            //Establishing a connection with the database and sending the statement.
            PreparedStatement ps = ConnectionMaker.prepareStatement(sql);

            //Setting the parameters in the statement
            ps.setString(1, eventTitle);

            //Executing the statement.
            ps.execute();

            //Closing the statement.
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /***
     * Updates a specific event's date in the SQL database
     * @param date The new event date
     * @param eventID The ID of the event
     */
    public static void updateEventDate(String date, int eventID){
        try {
            // SQL statement that will be sent to the database to update the event date
            String sql = "Update events set event_date=?";
            sql += " where event_id= ?";

            //Establishing a connection with the database and sending the statement.
            PreparedStatement ps = ConnectionMaker.prepareStatement(sql);

            // 3. Set the parameters
            ps.setString(1, date);
            ps.setInt(2, eventID);
            ps.executeUpdate();
        } catch(SQLException e){

            e.printStackTrace();
        }

    }
    //Method to update the event description.

    public static void updateEventDesc(String desc, int eventID) {
        try {
            //Prepare statement
            String sql = "UPDATE events SET event_desc = ? WHERE event_id= ?";

            //Create statement
            PreparedStatement ps = ConnectionMaker.prepareStatement(sql);

            //Set parameters
            ps.setString(1, desc);
            ps.setInt(2, eventID);

            //Execute statement
            ps.executeUpdate();

            //Close statement
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static void updateEventTitle(String title, int eventID) {
        try {
            //Prepare statement
            String sql = "UPDATE events SET event_title = ? WHERE event_id= ?";

            //Create statement
            PreparedStatement ps = ConnectionMaker.prepareStatement(sql);

            //Set parameters
            ps.setString(1, title);
            ps.setInt(2, eventID);

            //Execute statement
            ps.executeUpdate();

            //Close statement
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateImage (String path , int id){
        try {
            //Prepare statement
            String sql = "UPDATE events SET event_image = ? WHERE event_id= ?";

            //Create statement
            PreparedStatement ps = ConnectionMaker.prepareStatement(sql);

            //Set parameters
            ps.setString(1, path);
            ps.setInt(2, id);

            //Execute statement
            ps.executeUpdate();

            //Close statement
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getPath (int id ) {
        String sql = "Select * from events where event_id =?";
        try {
            PreparedStatement ps = ConnectionMaker.prepareStatement(sql);
            ResultSet rs = null;
            ps.setInt(1, id);

            rs= ps.executeQuery();

            if (rs.next()) {
                String path = rs.getString("event_image");
                return path;
            }

            ps.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

       /*public static void addTimelineEvent(Box box){
        String title = box.getTitle();
        try {
            // SQL query. Adding evet title to the 'events' table to the 'events_title' variable.
            String sql = "insert into events (event_title) ";
            sql += " values (?)";

            // Connection and inserting the box title in the 'events' table
            PreparedStatement ps = ConnectionMaker.prepareStatement(sql);

            //

            ps.setString(1,title);
            ps.execute();

        } catch(SQLException e){
            e.printStackTrace();
        }
    }*/
}
