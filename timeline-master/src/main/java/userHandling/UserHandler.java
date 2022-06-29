package userHandling;

import main.UserMain;

import java.sql.*;
import java.util.ArrayList;

/*
 * Important that you do not change the SQL statements and the namings
 * to go with your server, database, table and table content. It will be better if you make the changes in your
 * mySQL server and its contents because we do'nt want this code to be changed every time someone pushes something the repository.
 * (This also goes to all the parts in the project where mySQL server is used)
 * Thank you!!
 */


/**
 * @Class UserHandler will have most functions to deal with everything around the user like:
 * Registration, login and verifying the validity of the input provided by the users and admins.
 * It will also handel admin and registered-user functionality
 *
 */

public class UserHandler {
    /**
     * The method "addUser" is a method responsible for updating the database in the user registration process by adding new users
     * @param theUsername
     * @param thePassword
     * @param theEmail
     * @return
     */

    public static void addUser(String theUsername, String thePassword, String theEmail, String PasswordVerification) {
        //Checking if all the data entered is valid
        if(verifyFields(theUsername, thePassword, theEmail,PasswordVerification) && checkUsername(theUsername) && checkPassword(thePassword,PasswordVerification) ) {
            try {
                //Initializing SQL insert statement as a String .
                String sql = "insert into user (theUsername, thePassword, theEmail, type) ";
                sql += " values (?, ?, ?, ?)";

                //Creating a statement from the class ConnectionMaker and sending the insert statement there.
                PreparedStatement ps = ConnectionMaker.prepareStatement(sql);

                //Setting the variables in there order in the statement.
                ps.setString(1, theUsername);
                ps.setString(2, thePassword);
                ps.setString(3, theEmail);
                ps.setInt(4,0);



                //Execute and close the statement.
                ps.executeUpdate();
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static boolean logIn(String usernameOut, String passwordOut) {

        PreparedStatement ps;
        try {
            //Connect to the database
            ps = ConnectionMaker.prepareStatement("SELECT `theUsername`, `thePassword` FROM `user` WHERE `theUsername` = ? AND `thePassword` = ?"); //Select which columns we want to compare
            ps.setString(1, usernameOut);   // Add values for the username and then send to compare.
            ps.setString(2, String.valueOf(passwordOut)); //Add values for the password and then send to compare.
            ResultSet result = ps.executeQuery();

            if (result.next()) {    //If the execution pass and the username and password belongs to each other then login is successful.
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



    /**
     * verifyFields is a method to check if the registration fields are empty.
     * @param theUsername
     * @param thePassword
     * @param theEmail
     * @param passwordVerification
     * @return boolean
     * @author Amin Marteni
     */
    public static boolean verifyFields(String theUsername, String thePassword, String theEmail, String passwordVerification){

        // check empty fields
        if(theUsername.equals("") || thePassword.equals("") || theEmail.equals("")
                || passwordVerification.equals("")) {
            return false;
        }
        // if everything is ok return true
        else{
            return true;
        }
    }

    /**
     * The method checkPassword will check if the verification of the password is correct.
     * @param thePassword
     * @param passwordVerification
     * @return true or false
     * @author Amin Marteni
     */
    public static boolean checkPassword (String thePassword,String passwordVerification){
        // check if the two password are equals or not
        if(thePassword.equals(passwordVerification))
        {
            return true;
        }
        else
            return false;
    }

    /**
     *  checkUsername is a function to check if the entered username already exists in the database
     * @param username
     * @return true or false
     * @author Amin Marteni
     */
    public static boolean checkUsername(String username) {
        // Declare a PreparedStatement and a ResultSet
        PreparedStatement st;
        ResultSet rs;

        //Initializing SQL Select statement as a String.
        String query = "SELECT * FROM user WHERE theUsername = ?";

        try {
            //Establishing connection to the server via ConnectionMaker
            st = ConnectionMaker.prepareStatement(query);

            //Setting the variables in there order in the statement.
            st.setString(1, username);

            //Executing the statement to get a result set
            rs = st.executeQuery();

            //If the result set has the username in it it will return false
            if (rs.next()) {
                return false;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return true;
    }
    /**
     *  checkAdmin is a function that checks the database if the entered username or id
     *  belong to an Admin or a registered-user
     * @param username
     * @return true or false
     * @author Amin Marteni
     */
    public static int checkAdmin (String username){
        // Declare a PreparedStatement and a ResultSet
        PreparedStatement st;
        ResultSet rs;

        //Check if username already exists
        if (!checkUsername(username)) {
            //Initializing SQL Select statement as a String.
            String query = "SELECT type FROM user WHERE theUsername=?";


            try {
                //Establishing connection to the server via ConnectionMaker
                st = ConnectionMaker.prepareStatement(query);
                st.setString(1,username);
                //Executing the statement to get a result set
                rs = st.executeQuery();
                // new variable isAdmin=1 when user is admin.
                int isAdmin;

                //If the result set has the username in it it will return false
                if (rs.next()) {

                    isAdmin=rs.getInt("type");
                    return isAdmin;
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return 0;

    }



    /**
     *Function to elevate a users right to admin rights
     * @param usernameOut The user in which will be given admin rights      */
    public static boolean setUserTypeToAdmin(String usernameOut){
        PreparedStatement ps;
        if(!checkUsername(usernameOut)&& checkAdmin(usernameOut)==0) {
            try {
                String query = "Update user set type = ? where theUsername = ?";
                ps = ConnectionMaker.prepareStatement(query);
                ps.setInt(1, 1);
                ps.setString(2, usernameOut);
                ps.executeUpdate();


            } catch (SQLException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    /**
     * @function deleteUser will be used for both admins and registered users. It will return true when deleted.
     * 1)Admins will be able to delete their accounts from account manager menu,
     * they will also be able to delete other user's account from user management menu.
     * 2) registered-users will only be able to delete their accounts from account manager menu.
     * @param username provided by the user.
     * @return true or false
     */
    public static boolean deleteUser(String username) throws SQLException {
        //get the user_id
        int userID = checkUserId(username);

/*
creating a timeline list then fill it from the database with timeline IDs
Iterate over the IDs then send their values to the events table as foreign key to reach all events then delete them.
 */
        ArrayList <Integer> timelineIdList = new ArrayList<>();
        String countQuery = "SELECT  timeline_id FROM timelines WHERE fk_user_id = ?";
        PreparedStatement psCountTimelines = ConnectionMaker.prepareStatement(countQuery);
        psCountTimelines.setInt(1,userID);
        ResultSet rsCount = psCountTimelines.executeQuery();
        while (rsCount.next()){
            timelineIdList.add(rsCount.getInt("timeline_id"));
        }

        for ( int i = 0; i<timelineIdList.size(); i++){
            int timelineId = timelineIdList.get(i);
            String sql1 = "Delete from events where fk_timeline_id =?";
            PreparedStatement ps1 = ConnectionMaker.prepareStatement(sql1);
            ps1.setInt(1,timelineId);
            ps1.execute();
        }

        //Check if username exists
        if (!checkUsername(username)) {



            try {
                // SQL statements that will be sent to the database to delete the user and timelines from it.

                String sql2 = "Delete from timelines where fk_user_id =?";
                String sql3 = "Delete from user where theUsername =? ";

                //Establishing a connection with the database and sending the statement.

                PreparedStatement ps2 = ConnectionMaker.prepareStatement(sql2);
                PreparedStatement ps3 = ConnectionMaker.prepareStatement(sql3);

                //Setting the parameters in the statement

                ps2.setInt(1, userID);
                ps3.setString(1, username);

                //Executing the statement.
                ps2.execute();
                ps3.execute();

                //Closing the statement.
                ps3.close();
                ps2.close();
            } catch (SQLException e) {
                e.printStackTrace();

            }
            return true;
        }
        return false;
    }



    /**
     *  @function updatePassword will be used for both admins and registered users.
     *  It will return true when the password is updated.
     *  1)Admins will be able to update their passwords from account manager menu,
     *   they will also be able to update other user's password from user management menu.
     *  2) registered-users will only be able to update their password from account manager menu.
     * @param password provided by the user.
     * @param username provided by the user.
     * @return true or false
     * @author Amin Marteni
     */
    public static boolean updatePassword(String password, String username) {
        //Check if username exists
        if (!checkUsername(username)) {
            try {
                // SQL statement that will be sent to the database to update the password.
                String sql = "Update  user set thePassword=?";
                sql += " where theUsername= ?";

                //Establishing a connection with the database and sending the statement.
                PreparedStatement ps = ConnectionMaker.prepareStatement(sql);

                // 3. Set the parameters
                ps.setString(1, password);
                ps.setString(2, username);

                //Executing the statement.
                ps.executeUpdate();
            } catch (SQLException e) {

                e.printStackTrace();
            }
            return true;
        }
        else
            return false;
    }

    /**
     * @function updateEmail will be used for both admins and registered users.
     *    It will return true when the email is updated.
     *    1)Admins will be able to update their email from account manager menu,
     *     they will also be able to update other user's email from user management menu.
     *    2) registered-users will only be able to update their email from account manager menu.
     * @param email provided by the user.
     * @param username provided by the user.
     * @author Amin Marteni
     */
    public static boolean updateEmail(String email, String username) {
        //Check if username exists
        if (!checkUsername(username)) {
            try {
                // SQL statement that will be sent to the database to update the email.
                String sql = "Update  user set theEmail=?";
                sql += " where theUsername= ?";

                //Establishing a connection with the database and sending the statement.
                PreparedStatement ps = ConnectionMaker.prepareStatement(sql);

                // 3. Set the parameters
                ps.setString(1, email);
                ps.setString(2, username);
                ps.executeUpdate();
            } catch(SQLException e){

                e.printStackTrace();
            }
            return true;
        }
        else
            return false;
    }

    /**
     * checking user id after logging in to be used later in checking timelines to specific user
     * @param username
     * @return UserID
     * @author Amin Marteni
     */
    public static int checkUserId (String username){
        // Declare a PreparedStatement and a ResultSet
        PreparedStatement st;
        ResultSet rs = null;
        //Check if username already exists
        if (!checkUsername(username)) {
            //Initializing SQL Select statement as a String.
            String query = "SELECT user_id FROM user WHERE theUsername=?";

            try {
                //Establishing connection to the server via ConnectionMaker
                st = ConnectionMaker.prepareStatement(query);
                st.setString(1, username);

                //Executing the statement to get a result set
                rs = st.executeQuery();
                int id;

                while (rs.next()){
                    id=rs.getInt("user_id");
                    return id;
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return 0;
    }

    public static ArrayList<String> viewUsersList() throws SQLException {

        // Declare a PreparedStatement and a ResultSet
        PreparedStatement st;
        ResultSet rs = null;
        int userID = UserMain.getUserID();
        //to test we just take a fixed userID
        // int userID = 1;

        //Initializing SQL insert statement as a String .
        String sql = "select theUsername from user";


        //Establishing connection to the server via ConnectionMaker
        st = ConnectionMaker.prepareStatement(sql);
        //st.setInt(1, userID);

        //Executing the statement to get a result set
        rs = st.executeQuery();

        ArrayList<String> usernameList = new ArrayList<>();

        while (rs.next()) {
            usernameList.add(rs.getString("theUsername"));
        }
        return usernameList;
    }










    //The following is just some extra functionality for later use if needed


   /*


   public static int checkUserId (String username){
       // Declare a PreparedStatement and a ResultSet
       PreparedStatement st;
       ResultSet rs = null;
       //Check if username already exists
       if (!checkUsername(username)) {
           //Initializing SQL Select statement as a String.
           String query = "SELECT user_id FROM user WHERE theUsername=?";

           try {
               //Establishing connection to the server via ConnectionMaker
               st = ConnectionMaker.prepareStatement(query);
               st.setString(1, username);

               //Executing the statement to get a result set
               rs = st.executeQuery();
               int id;

               while (rs.next()){
                   id=rs.getInt("user_id");
                   return id;
               }

           } catch (SQLException ex) {
               ex.printStackTrace();
           }
           }

        return 0;
    }



       /**
       * @function updateUsername will be used by admins only to change user's username or their own from the user manager menu.
       * It will return true if the update was successful.
       * @param username provided by the user.
       * @param id provided by the user.
       * @author Amin Marteni

     public static boolean updateUsername(String username, int id) {
         //Check if username exists
         if (!checkUsername(username)) {
             try {
                 // SQL statement that will be sent to the database to update the username.
                 String sql = "Update  user set theUsername=?";
                 sql += " where user_id= ?";

                 //Establishing a connection with the database and sending the statement.
                 PreparedStatement ps = ConnectionMaker.prepareStatement(sql);

                 //Setting the parameters in the statement
                 ps.setString(1, username);
                 ps.setInt(2, id);

                 //Executing the statement.
                 ps.executeUpdate();

             } catch (SQLException e) {

                 e.printStackTrace();
             }
             return true;
         }
         else
             return false;

     }

     public static User selectUser(int id) {
          String sql = "select * from User where id = ?";
          // 1. Get a connection to database
          // 2. Create a statement
          PreparedStatement ps;
          try {
              ps = ConnectionMaker.prepareStatement(sql);
              // 3. Set the parameters
              ps.setInt(1, id);

              ResultSet r = ps.executeQuery();
              if (r.next()) {
                  User me = new User(r.getInt("id"),r.getString("username"), r.getString("password"),
                          r.getString("email"));
                  return me;
              }
          } catch (SQLException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
          }
          return null;
      }




      public static User CheckAdmin() {
          String sql = "select * from users where type=1";
          // 1. Get a connection to database
          // 2. Create a statement
          PreparedStatement ps;
          try {
              ps = ConnectionMaker.prepareStatement(sql);

              ResultSet q = ps.executeQuery();
              if (!q.next()) {
                  Scanner read = new Scanner(System.in);
                  System.out.println("The system found no Admin, please enter information about admin.");
                  read.nextLine();
                  System.out.println("Please enter User name:");
                  String username = read.nextLine();
                  System.out.println("Please enter email:");
                  String email = read.nextLine();
                  System.out.println("Please password:");
                  String password = read.nextLine();
                  UserHandler.addUser(username, email, password);
              }

          } catch (SQLException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
          }
          return null;

      }*/
}
