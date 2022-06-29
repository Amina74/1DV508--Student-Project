package timeLineDisplay;

import timeLineDisplay.Box;

import java.util.ArrayList;

public class TimeLine {
    private String startDate;
    private String endDate;
    private String title;
    private String keyword;
    private int timelineID;
   // private timeLineDisplay.User owner; to add or modify or remove later
    private ArrayList<Box> boxes= new ArrayList<>();

    /* Timeline without keyword with ID */
    public TimeLine(String startDate, String endDate, String title, int timelineID) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.timelineID = timelineID;

    }
    
    /* Timeline without keyword without ID */
    public TimeLine(String startDate, String endDate, String title) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.timelineID = timelineID;

    }
    
    /* Timeline with keyword */
    public TimeLine(String startDate, String endDate, String title, /*int timelineID, */ String keyword) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.keyword = keyword;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setKeyword(String keyword) {
    	this.keyword = keyword;
    }
    
   // public void setOwner(timeLineDisplay.User owner) {
   //     this.owner = owner;
   // }

    public void addBox(Box box) {

        boxes.add(box);
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getTitle() {
        return title;
    }
    
    public String getKeyword() {
    	return keyword;
    }

    public int getTimelineID(){return timelineID;}

   // public timeLineDisplay.User getOwner() {
   //     return owner;
   // }

  //returns an array of the boxes of the timeline  
    public Box[] getBoxes() {
        Box[] temp = new Box[boxes.size()]; 
        for(int i = 0; i < boxes.size(); i++) {
        	temp[i] = boxes.get(i);
        }
        if(temp[0] == null) {throw new IndexOutOfBoundsException("boxes array is empty");}
        return temp;
    }

    /**
     * was added to test to see a list of timelines for a specific user
     * @return
     */
    public String toString (){
        StringBuilder sb = new StringBuilder();
        sb.append("[ ").append(getTitle()+" >><< "+getStartDate()+" >><< "+getEndDate()+" >><<"+getTimelineID()).append(" ]");
return sb.toString();
}
}
