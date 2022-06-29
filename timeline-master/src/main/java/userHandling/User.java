package userHandling;

import java.util.InputMismatchException;

/**
 * Created by Bartlomiej Minierski
 * 2020-04-08.
 */
public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private int type; //"1" Admin. "0" user

    public User (int id, String theUsername, String thePassword, String theEmail) throws InputMismatchException{
        super();
        this.id = id;
        if(isUserValid(theUsername))
            this.username = theUsername;
        else throw new InputMismatchException("Username need to have at least 3 characters");
        if(isPasswordValid(thePassword))
            this.password = thePassword;
        else throw new InputMismatchException("Password need to have at least 5 characters");
        this.email = theEmail;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String theUsername) throws InputMismatchException {
        if(isUserValid(theUsername))
            this.username = theUsername;
        else throw new InputMismatchException("Username need to have at least 3 characters");
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String thePassword) throws InputMismatchException {
        if (isPasswordValid(thePassword))
            this.password = thePassword;
        else throw new InputMismatchException("Password need to have at least 5 characters");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static boolean isUserValid(String input){
        if (input.length() >= 3)
            return true;
        else return false;
    }
    public static boolean isPasswordValid(String input){
        if (input.length() >= 5)
            return true;
        else return false;
    }


}
