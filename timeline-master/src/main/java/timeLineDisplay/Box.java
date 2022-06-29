package timeLineDisplay;

import javafx.scene.image.Image;

public class Box {
    private String title;
    private Image boxImage;
    private String desc;
    private String boxDate;
    private int boxId;
    private String imagePath;
    /**
     * Creates a Box without an Image
     * (temporarily switched boxDate to String).
     * @param title
     * @param desc
     * @param boxDate
     */
    public Box(String title, String desc, String boxDate,int boxId ) {
        this.title = title;
        this.desc = desc;
        this.boxDate = boxDate;
        this.boxId= boxId;
    }

    public Box(String title, String desc, String boxDate, int boxId, String imagePath) {
        this.title = title;
        this.desc = desc;
        this.boxDate = boxDate;
        this.boxId = boxId;
        this.imagePath = imagePath;
    }

    public Box(String title, String desc, String boxDate) {
        this.title = title;
        this.desc = desc;
        this.boxDate = boxDate;

    }
    
    /**
     * Creates a Box with an Image 
     * (temporarily switched boxDate to String).
     * @param title
     * @param boxImage
     * @param desc
     * @param boxDate
     */
    public Box(String title, Image boxImage,  String desc, String boxDate) {
        this.title = title;
        this.boxImage = boxImage;
        this.desc = desc;
        this.boxDate = boxDate;
    }





    public void setTitle(String title) {
        this.title = title;
    }

    public void setBoxImage(Image boxImage) {
        this.boxImage = boxImage;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public void setBoxDate(String boxDate) {
        this.boxDate = boxDate;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getTitle() {
        return title;
    }

    public Image getBoxImage() {
        return boxImage;
    }

    public String getDesc() {
        return desc;
    }


    public String getBoxDate() { //Integer
        return boxDate;
    }

    public int getBoxId() {
        return boxId;
    }

    public String getImagePath() {
        return imagePath;
    }

    //method made by Adam Nyman
    //compares the date of two boxes (b1 and b2)
    //if b1 > b2 it returns 1
    //if b2 > b1 it returns -1
    //if b1 = b2 it returns 0
    public int boxDateCompare(Box b2) {
        String date1 = boxDate;
        String date2 = b2.getBoxDate();
        boolean date1ac = false;
        boolean date2ac = false;
        //checks if the date is written on YYYY-MM-DD (and is thereby A.C.)
        if(date1.charAt(4) == '-' && date1.charAt(7) == '-') date1ac = true;
        if(date2.charAt(4) == '-' && date2.charAt(7) == '-') date2ac = true;
        //if both are A.C. they are compared and if both of them are B.C. they are also compared
        //otherwise we just say that one is earlier then the other
        if(date2ac && date1ac) return compareAC(date1, date2);
        else if(date1ac && !date2ac) return 1;
        else if(!date1ac && date2ac) return -1;
        else if(!date2ac && !date2ac) return compareBC(date1,date2);
        else {
            return 0;
        }
    }

    //method made by Adam Nyman
    public int compareAC(String date1, String date2) {
        //gets the year of both dates
        int year1 = 0;
        int year2 = 0;
        int y1;
        int y2;
        int mult; 

        for(int i = 0; i < 4; i++) {
        	mult = (int) Math.pow(10, 3-i);
        	
            y1 = Character.getNumericValue(date1.charAt(i));
            y1 = y1*mult;
            year1 = year1 + y1;
            
            y2 = Character.getNumericValue(date2.charAt(i));
            y2 = y2*mult;
            year2 = year2 + y2;
            
        }
        
        //gets the month of both dates
        int month1 = 0;
        int month2 = 0;
        int m1;
        int m2;

        for(int i = 5; i < 7; i++) {
        	mult = (int) Math.pow(10,6-i); 
        	
            m1 = Character.getNumericValue(date1.charAt(i));
            m1 = m1*mult;
            month1 = month1 + m1;
            
            m2 = Character.getNumericValue(date2.charAt(i));
            m2 = m2*mult;
            month2 = month2 + m2;
            
        }

        //gets the day of both dates
        int day1 = 0;
        int day2 = 0;
        int d1;
        int d2;

        for(int i = 8; i < 10; i++) {
        	mult = (int) Math.pow(10, 9-i); 
        	
            d1 = Character.getNumericValue(date1.charAt(i));
            d1 = d1*mult;
            day1 = day1 + d1;
           
            d2 = Character.getNumericValue(date2.charAt(i));
            d2 = d2*mult;
            day2 = day2 + d2;
            
        }
       
        //The comparison itself
        if(year1 > year2) return 1;
        else if(year2 > year1) return -1;
        else {
            if(month1 > month2) return 1;
            else if(month2 > month1) return -1;
            else {
                if(day1 > day2) return 1;
                else if(day2 > day1) return -1;
                else return 0;
            }
        }
    }

    //method created by Adam Nyman
    //compares years that are before christ
    public int compareBC(String date1, String date2) {
        //if on of the date has more integers that one is earlier in time
        if(date1.length() > date2.length()) return -1;
        else if(date2.length() > date2.length()) return 1;
            //if the number of integers are the same, the integers must be compared
        else {
            for(int i= 0; i < date1.length(); i++) {
                if(date1.charAt(i) > date2.charAt(i)) return -1;
                else if(date2.charAt(i) > date1.charAt(i)) return 1;
            }
            return 0;
        }
    }

}
