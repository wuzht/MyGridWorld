/**
 * MazeBug.java
 * @author Mr.W
 * @version 1.0
 */
package info.gridworld.maze;

import info.gridworld.actor.Actor;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Rock;
import info.gridworld.grid.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import javax.swing.JOptionPane;


/**
 * A <code>MazeBug</code> can find its way in a maze. <br />
 * The implementation of this class is testable on the AP CS A and AB exams.
 */
public class MazeBug extends Bug {
	// 0 north    1 east    2 south    3 west
	private int[] directionProbabitility = {1, 1, 1, 1};
	
	public Location next;
	public Location last;
	public boolean isEnd = false;
	public Stack<ArrayList<Location>> crossLocation = new Stack<ArrayList<Location>>();
	public Integer stepCount = 0;
	boolean hasShown = false;//final message has been shown

	/**
	 * Constructs a box bug that traces a square of a given side length
	 * 
	 * @param length
	 *            the side length
	 */
	public MazeBug() {
		setColor(Color.GREEN);
		last = new Location(0, 0);
	}

	/**
	 * Moves to the next location of the square.
	 */
	public void act() {
		boolean willMove = canMove();
		if (isEnd == true) {
		//to show step count when reach the goal		
			if (hasShown == false) {
				String msg = stepCount.toString() + " steps";
				JOptionPane.showMessageDialog(null, msg);
				hasShown = true;
			}
		} else if (willMove) {
			move();
			//increase step count when move 
			stepCount++;
		} 
	}

	/**
	 * Find all positions that can be move to.
	 * 
	 * @param loc
	 *            the location to detect.
	 * @return List of positions.
	 */
	public ArrayList<Location> getValid(Location loc) {
		Grid<Actor> gr = getGrid();
		if (gr == null) {
			return null;
		}	
		ArrayList<Location> valid = new ArrayList<Location>();
		
		// the MazeBug can only move to the following 4 directions
		int[] directions = {Location.EAST, Location.SOUTH, Location.WEST, Location.NORTH};
		for (int d : directions) {
			Location adjLoc = loc.getAdjacentLocation(d);
			if (gr.isValid(adjLoc)) {
				Actor neighbor = gr.get(adjLoc);
				if (neighbor == null || neighbor instanceof Flower) {
					valid.add(adjLoc);
				}
				// if comes across a red rock, then the game ends.
				else if (neighbor.getColor().equals(Color.RED) && neighbor instanceof Rock) {
					isEnd = true;
				}
			}
		}
		return valid;
	}

	/**
	 * Tests whether this bug can move forward into a location that is empty or
	 * contains a flower.
	 * 
	 * @return true if this bug can move.
	 */
	public boolean canMove() {
        Grid<Actor> gr = getGrid();
        if (gr == null || isEnd == true) {
            return false;
        }
        
        ArrayList<Location> allLocs = getValid(getLocation());
        // add the current location to the first
        allLocs.add(0, this.getLocation());
        ArrayList<Location> emptyLocs = getEmptyLocations(allLocs);
        
        // can move to an empty location
        if (emptyLocs.size() != 0) {
        	next = randomlyChooseLocation(emptyLocs);
        	crossLocation.push(allLocs);
        	
        	int dir = this.getLocation().getDirectionToward(next);
        	directionProbabitility[dir / 90]++;
        }
        // go back
        else if (crossLocation.size() != 0) {
        	allLocs = crossLocation.pop();
        	// get the last location
        	next = allLocs.get(0);
        	
        	int dir = this.getLocation().getDirectionToward(next);
        	// go back, 0->2, 1->3, 2->0, 3->1
        	directionProbabitility[(dir / 90 + 2) % 4]--;	
        }
        
        return true;
    }
	
	/**
	 * get the empty locations from some locations
	 * @param locs
	 * @return the empty locations
	 */
	private ArrayList<Location> getEmptyLocations(ArrayList<Location> locs) {
		ArrayList<Location> retLocs = new ArrayList<Location>();
		for (Location loc : locs) {
			if (this.getGrid().get(loc) == null) {
				retLocs.add(loc);
			}
		}
		return retLocs;
	}
	
	/**
	 * randomly choose a location from some locations
	 * base on the direction probability
	 * @param locs
	 * @return the chosen location
	 */
	private Location randomlyChooseLocation(ArrayList<Location> locs) {
		
		int total = 0;
		int[] directionsToChoose = new int [4];
		for (int i = 0; i < directionsToChoose.length; i++) {
			directionsToChoose[i] = 0;
		}
		for (Location loc : locs) {
			int tempDir = this.getLocation().getDirectionToward(loc);
			int tempDirPro = directionProbabitility[tempDir / 90];
			directionsToChoose[tempDir / 90] = tempDirPro;
			total += tempDirPro;
		}
		Random ran = new Random();
		// choose the location with the direction probability
		int num = ran.nextInt(total);
		int resultDir = 0;
		int secondNum = directionsToChoose[0] + directionsToChoose[1];
		int thirdNum = secondNum + directionsToChoose[2];
		if (num < directionsToChoose[0]) {
			resultDir = 0;
		}
		else if (num < secondNum) {
			resultDir = 1;
		}
		else if (num < thirdNum){
			resultDir = 2;
		}
		else {
			resultDir = 3;
		}
		
		Location ret = locs.get(0);
		for (Location loc : locs) {
			if (this.getLocation().getDirectionToward(loc) / 90 == resultDir) {
				ret = loc;
				break;
			}
		}
		return ret;
	}
	
	/**
	 * Moves the bug forward, putting a flower into the location it previously
	 * occupied.
	 */
	public void move() {
		Grid<Actor> gr = getGrid();
		if (gr == null) {
			return;
		}
		Location loc = getLocation();
		if (gr.isValid(next)) {
			setDirection(getLocation().getDirectionToward(next));
			moveTo(next);
		} else {
			removeSelfFromGrid();
		}
		Flower flower = new Flower(getColor());
		flower.putSelfInGrid(gr, loc);
	}
}
