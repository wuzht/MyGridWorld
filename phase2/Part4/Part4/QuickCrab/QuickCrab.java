/* 
 * @author Zhengtao Wu
 */

import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.util.ArrayList;

/**
 * A <code>QuickCrab</code> <br />
 * The implementation of this class is testable on the AP CS A and AB exams.
 */
public class QuickCrab extends CrabCritter
{
    /**A QuickCrab moves to one of the two locations, 
     * randomly selected, that are two spaces to its right or left, 
     * if that location and the intervening location are both empty. 
     * Otherwise, a QuickCrab moves like a CrabCritter.
     * @return list of empty locations immediately to the right and to the left
     */
    public ArrayList<Location> getMoveLocations()
    {
    	// Get the current states
    	Grid<Actor> gr = this.getGrid();
        ArrayList<Location> locs = new ArrayList<Location>();
        Location loc = this.getLocation();
        int dir = this.getDirection();
        
        // Left
        Location leftLoc = loc.getAdjacentLocation(dir + Location.LEFT);
        if (gr.isValid(leftLoc) && gr.get(leftLoc) == null) {
        	Location leftTwoLoc = leftLoc.getAdjacentLocation(dir + Location.LEFT);
        	if (gr.isValid(leftTwoLoc) && gr.get(leftTwoLoc) == null) {
        		locs.add(leftTwoLoc);
        	}
        }
        
        // Right
        Location rightLoc = loc.getAdjacentLocation(dir + Location.RIGHT);
        if (gr.isValid(rightLoc) && gr.get(rightLoc) == null) {
        	Location rightTwoLoc = rightLoc.getAdjacentLocation(dir + Location.RIGHT);
        	if (gr.isValid(rightTwoLoc) && gr.get(rightTwoLoc) == null) {
        		locs.add(rightTwoLoc);
        	}
        }
        
        // A QuickCrab moves like a CrabCritter.
        if (locs.size() == 0) {
        	locs = super.getMoveLocations();
        }
        
        return locs;
    }
}
