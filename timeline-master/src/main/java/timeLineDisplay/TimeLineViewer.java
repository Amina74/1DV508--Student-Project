package timeLineDisplay;

import main.UserMain;
import timelineHandling.EventsHandler;

import java.io.FileNotFoundException;
import java.sql.SQLException;

/**
 * class for viewing the different timelines 
 * @author Adam Nyman
 * date: 2020-04-19 
 */
public class TimeLineViewer {
	public static TimeLine[] selectedTimeLine = new TimeLine[5];  //the timelines that are currently viewed
	int size = 0;  				//the "size" of the array above (the amount of Timelines currently viewed)
	
	//takes an timeline as a parameter
	//this timeline is added to the array of timelines currently viewed 
	public void addTimeLine(TimeLine theTimeLine){
		if(size < selectedTimeLine.length) {
			selectedTimeLine[size] = theTimeLine;
			size++; 
		}
	}
	
	//removes an timeline from the viewed window on the given index
	public void removeTimeLine(int index) {
		if (index == size) {
			selectedTimeLine[index] = null;
			size--; 
		}
		
		else if(index < size && index >= 0) {
			while (index < size) {
				selectedTimeLine[index] = selectedTimeLine[index + 1]; 
				index++; 
			}
			selectedTimeLine[index] = null;
			size--; 
		}
		
		else {
			throw new IndexOutOfBoundsException(); 
		}
	}
	
	//Returns a copy of the selectedTimeLine array 
	public TimeLine[] getTimeLines() {
		TimeLine[] temp = new TimeLine[size]; 
		for(int i = 0; i < size; i++) {
			temp[i] = selectedTimeLine[i]; 
		}
		return temp; 
	}
	
	//Makes an array of multiple Timelinecanvases from the timelines selected. 
	//This array will later be used to display the timelines in a window. 
	public TimeLineCanvas[] getCanvas() throws FileNotFoundException {
		TimeLineCanvas[] allCanvas = new TimeLineCanvas[size]; //creates an canvasarray that should contain all timelines
		for(int i = 0; i < size; i++) {
			allCanvas[i] = new TimeLineCanvas();
			allCanvas[i].setTitle(selectedTimeLine[i].getTitle());
		} //creates canvas for every timeline
		TimeLine current;  
		Box[] currentBoxes = new Box[1];

		if (allCanvas.length == 1) {
			//when single timelines selected -> TimeLineCanvas.addBoxSDetailed calls
			//adds the boxes of every individual timeline to the canvas for that specific timeline
			for (int index = 0; index < size; index++) {
				current = selectedTimeLine[index];

					int timelineID = current.getTimelineID();
					//tries for empty boxes array
					try {
						//adding boxes from database
						currentBoxes = EventsHandler.getTimeLineEvents(timelineID);
						currentBoxes = sortBoxes(currentBoxes); 

						for (Box event : currentBoxes) {
							String path = event.getImagePath();
							if (path != null) {
								event.setImagePath(path);
							}
						}


						//sortBoxes(currentBoxes);
						// currentBoxes = current.getBoxes();
					} catch (IndexOutOfBoundsException | SQLException e) {
						currentBoxes[0] = new Box("no boxes available", null, null, null);
					}

				for (int boxIndex = 0; boxIndex < currentBoxes.length; boxIndex++) {
					allCanvas[index].addBoxDetailed(currentBoxes[boxIndex], boxIndex);	//add detailed box from canvas
				}
			}
		}
		else if (allCanvas.length>1){
			//when multiple timelines selected -> TimeLineCanvas.addBoxSimple calls
			for (int index = 0; index < size; index++) {
				current = selectedTimeLine[index];
				//tries for empty boxes array
				try {
					int timelineID=current.getTimelineID();
					currentBoxes = EventsHandler.getTimeLineEvents(timelineID);
					currentBoxes = sortBoxes(currentBoxes); 
					for (Box event : currentBoxes){
						String path=event.getImagePath();
						if (path!=null){
							event.setImagePath(path);
						}
					}
					//sortBoxes(currentBoxes);
				} catch (IndexOutOfBoundsException | SQLException e) {
					currentBoxes[0] = new Box("no boxes available", null, null, null);
				}
				for (int boxIndex = 0; boxIndex < currentBoxes.length; boxIndex++) {
					allCanvas[index].addBoxSimple(currentBoxes[boxIndex], boxIndex);	//add simple box from canvas
				}
			}
		}
		return allCanvas; 
	}

	public TimeLineCanvas[] getCanvasOffline() throws FileNotFoundException {
		TimeLineCanvas[] allCanvas = new TimeLineCanvas[size]; //creates an canvasarray that should contain all timelines
		for(int i = 0; i < size; i++) {
			allCanvas[i] = new TimeLineCanvas();
			allCanvas[i].setTitle(selectedTimeLine[i].getTitle());
		} //creates canvas for every timeline
		TimeLine current;
		Box[] currentBoxes = new Box[1];

		//adds the boxes of every individual timeline to the canvas for that specific timeline
		for(int index = 0; index < size; index++) {
			current = selectedTimeLine[index];
			//tries for empty boxes array
			try {
				currentBoxes = current.getBoxes();
				currentBoxes = sortBoxes(currentBoxes); 
			}
			catch(IndexOutOfBoundsException e){currentBoxes[0] = new Box("no boxes available",null,null, null);}
			if(allCanvas.length > 1) {
				for (int boxIndex = 0; boxIndex < currentBoxes.length; boxIndex++) {
					allCanvas[index].addBoxSimple(currentBoxes[boxIndex], boxIndex);
				}
			}
			else if(allCanvas.length == 1) {
				for (int boxIndex = 0; boxIndex < currentBoxes.length; boxIndex++) {
					allCanvas[index].addBoxDetailed(currentBoxes[boxIndex], boxIndex);
				}
			}
		}
		return allCanvas;
	}


	//sorts the boxes using the boxDateCompare method within the box class
	public Box[] sortBoxes(Box[] boxes) { 
		Box temp; 			  	//temp variable
		Box earliest;	      	//the box that currently has the earliest date
		int earliestIndex = 0;	//the index of the box called earliest
		
		for(int currentBox = 0; currentBox < boxes.length; currentBox++) {
			earliest = boxes[currentBox];
			earliestIndex = currentBox;
			for(int i = currentBox; i < boxes.length; i++) {
				if(boxes[i].boxDateCompare(earliest) < 0) {
					earliest = boxes[i];
					earliestIndex = i;
				}
			}

			temp = boxes[currentBox];
			boxes[currentBox] = boxes[earliestIndex];
			boxes[earliestIndex] = temp;
		}
		return boxes;

	}
	
}
