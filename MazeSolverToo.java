/**
 * This class uses a priority queue, and an A* search algorithm to intelligently find the end tile of a hexagonal-tile maze.
 * @author Iulia Murariu
 */
 
import java.io.*;

public class MazeSolverToo {
	
	/**
	 * This main method uses a maze.txt file to create a maze object, and then finds the end tile of the maze using a priority queue and A*.
	 * @param args
	 */
	public static void main (String[] args) throws UnknownMazeCharacterException, FileNotFoundException, IOException {
		try{
			//checks if a maze file was inserted by the user
            if(args.length<1){
            	throw new IllegalArgumentException("No Maze Provided");
            }
            
            //stores the maze file into the variable mazeFileName
            String mazeFileName = args[0];
            
            //creating a new maze object using the maze file 
            Maze Maze1 = new Maze(mazeFileName);
            
            //going through the maze slowly to see the steps
            //Maze1.setTimeDelay(300);
            
            //create a hexagon reference 
            Hexagon hexagon;
            
            //get the start hexagon tile from the maze 
            hexagon = Maze1.getStart();
            
            //shows that the maze was started 
            hexagon.setStarted();
            
            //setting the steps for the starting tile
            hexagon.setSteps(0);
            
            //create a new linked priority queue 
            LinkedPriorityQueue<Hexagon> queue = new LinkedPriorityQueue<Hexagon>();
            
            //enqueue starting hexagon 
            queue.enqueue(hexagon);
            
            //a boolean used to check if the end is found 
            boolean found = false;
            
            //the variable numSteps keeps track of the number of steps/dequeued tiles
            int numSteps = 0;
            
            //keeps track of the number of tiles left in the priority queue
            int tilesLeft = 1;
            
            //the length of the shortest path is stored in this variable 
            double shortestSteps = 0;
            
            //this part of the code is only executed if the queue is not empty, and if the end tile isn't found
            while (!queue.isEmpty() && found == false){
            	
            	//the hexagon at front of queue is dequeued and showing that it is the current tile 
            	Hexagon removed = queue.dequeue();
            	
            	//don't want to set the start tile to yellow
            	if (!removed.isStart()){
            		removed.setCurrent();
            		//need to repaint to update the tile colour to yellow representing the current tile
            		Maze1.repaint();
            	} 
            	
            	//since we removed one tile from the qeueue, we subtract one from tilesLeft
            	tilesLeft = tilesLeft - 1;
            	
            	//update the numSteps to keeps track of the current number of steps taken/time we dequeue
            	numSteps = numSteps + 1;
	
            	//if the dequeued hexagon is the end tile, set the boolean found to true since end is found
            	if (removed.isEnd()){
            		found = true;
            		//show that the maze is finished
            		removed.setFinished();
            		shortestSteps = removed.getSteps();
            		}
            	
            	//if the dequeued hexagon is not the end tile, check each of its neighbours
            	//enqueue the neighbours based on their calculated priority
            	else{
            		for(int i = 0; i < 6 ; i++){
            			
            			//retrieving the neighbour of the current hexagon at the given index, and storing it in the neighbour reference variable
            			Hexagon neighbour = removed.getNeighbour(i);
            			
            			//before enqueueing the neighbour, check if it is null, a wall, already dequeued previously or already enqueued
            			if(neighbour!=null && !neighbour.isWall() && !neighbour.isDequeued() && !neighbour.isEnqueued()){
            				
            				//before enqueueing and calculating priority, setting the steps to be the previous tile's steps + 1  
            				neighbour.setSteps(removed.getSteps() + 1);
            				
            				//neighbour's priority is calculated based on the steps taken to get to it, and the estimated distance from it to the end of the maze
            				double priority = neighbour.getSteps() + neighbour.distanceToEnd(Maze1);
            				
            				//finally enqueue the neighbour if it has met the conditions described 
            				queue.enqueue(neighbour, priority);
            				
            				//make the tile appear blue on the maze to signify that it is enqueued
            				neighbour.setEnqueued();
            				
            				//update the number of tiles in the queue
            				tilesLeft = tilesLeft +1;
            				}
            			
            		//after examining it's neighbours, we need to set the removed hexagon as dequeued as long as it isn't the start hexagon
            		//want to be able to see the start hexagon as a light pink colour instead
            		if (!removed.isStart()){
            			removed.setDequeued();
            			} 
            		}
            	}
            	//update the tile colours by repainting the maze window
            	Maze1.repaint();
            }
          
            //if the end has been found, congratulate the user, show them the tiles left in the queue, describe the number of steps taken to find the end, and show them the shortest path length
            if (found == true){
            	System.out.println("Congratulations! You found the end!");
            	System.out.println("You took " + numSteps + " steps to get to the end!");
            	System.out.println("There are " + tilesLeft + " tiles left in the queue.");
            	System.out.println("The shortest path takes " + shortestSteps + " steps to find the end.");
            }
            //if the end has not been found, tell the user, display the number of tiles left in the queue, describe the number of steps taken to get to the current tile 
            else{
            	System.out.println("Sorry! You couldn't find the end of the maze.");
            	System.out.println("There are " + tilesLeft + " tiles left in the queue.");
            	System.out.println("You took " + numSteps + " steps to get here.");
            }
          
		}
		//InvalidNeighbourIndexException - checks if program tries to access an invalid neighboring hexagon.
        catch(InvalidNeighbourIndexException e){
        	System.out.println(e.getMessage() + " Oops! You tried to access a neighbour that doesn't exist!");
        }
		//UnkonwnMazeCharacterException - notifies the user if an unknown character was encountered during maze building.
		catch( UnknownMazeCharacterException e){
			System.out.println(e.getMessage() + " Sorry! During maze building an unknown character was found.");
		}
		//EmptyCollectionException - makes sure program does not try to dequeue a tile if the queue is empty.
        catch(EmptyCollectionException e){
        	System.out.println(e.getMessage() + " There are no hexagons on your queue!");
        }
		//IllegalArgumentException - makes sure that a valid argument providing the maze file name was entered.
		catch(IllegalArgumentException e){
            	System.out.println(e.getMessage() + " Try adding it in again!");
            }
		//FileNotFoundException - notifies the user if the maze file provided could not be found.
		catch(FileNotFoundException e){
			System.out.println(e.getMessage() + " Oops! Couldn't find the file you're looking for.");
		}
		//IOException - checks and notifies if invalid input was entered.
		catch(IOError e){
			System.out.println(e.getMessage() + " Sorry! You provided invalid input!");
		}
	}
}
