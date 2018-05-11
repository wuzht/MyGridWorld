/* 
 * @author Zhengtao Wu
 */

import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.Color;
import java.util.ArrayList;

/**
 * A <code>QuickCrab</code> <br />
 * The implementation of this class is testable on the AP CS A and AB exams.
 */
public class KingCrab extends CrabCritter
{
    public KingCrab() {
    	setColor(Color.YELLOW);
    }
    
    /**
     * Processes the elements of <code>actors</code>. New actors may be added
     * to empty locations. Implemented to "eat" (i.e. remove) selected actors
     * that are not rocks or critters. Override this method in subclasses to
     * process actors in a different way. <br />
     * Postcondition: (1) The state of all actors in the grid other than this
     * critter and the elements of <code>actors</code> is unchanged. (2) The
     * location of this critter is unchanged.
     * @param actors the actors to be processed
     */
    public void processActors(ArrayList<Actor> actors)
    {
        for (Actor a : actors)
        {
            if (!moveActorAway(a)) {
                a.removeSelfFromGrid();
            }
        }
    }
    
    /*
     * Return true if the actor is moved away,
     * Return false otherwise.
     */
    private boolean moveActorAway(Actor actor) {
    	ArrayList<Location> locs = this.getGrid().getEmptyAdjacentLocations(actor.getLocation());
    	for (Location loc : locs ) {
    		if (actorCanMoveAwayTo(loc)) {
    			actor.moveTo(loc);
    			return true;
    		}
    	}
    	return false;
    }
    
    private boolean actorCanMoveAwayTo(Location loc) {
    	int x1 = this.getLocation().getRow();
    	int y1 = this.getLocation().getCol();
    	int x2 = loc.getRow();
    	int y2 = loc.getCol();
    	double distance = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    	if ((int)distance > 1) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
}
