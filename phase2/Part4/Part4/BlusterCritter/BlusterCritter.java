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
 * A <code>RockHound</code> takes on the color of neighboring actors as
 * it moves through the grid. <br />
 * The implementation of this class is testable on the AP CS A and AB exams.
 */
public class BlusterCritter extends Critter
{
	// Change 20% of color value in each step
	private static final double COLOR_CHANGING_FACTOR = 0.2;
	// courageValue is a value that indicates the courage of the BlusterCritter
	private int courageValue;
	
	// The constructor of BlusterCritter
	public BlusterCritter(int c) {
		super();
		courageValue = c;
	}
	
    /**
     * Gets the actors for processing. A BlusterCritter looks at 
     * all of the neighbors within two steps of its current location<br />
     * Postcondition: The state of all actors is unchanged.
     * @return a list of actors that this critter wishes to process.
     */
    public ArrayList<Actor> getActors()
    {
        ArrayList<Actor> actors = new ArrayList<Actor>();
        Grid<Actor> gr = this.getGrid();
        Location loc = this.getLocation();
        for (int i = loc.getRow() - 2; i <= loc.getRow() + 2; ++i) {
        	for (int j = loc.getCol() - 2; j <= loc.getCol() + 2; ++j) {
        		Location tempLoc = new Location(i, j);
        		if (gr.isValid(tempLoc) && !tempLoc.equals(loc)) {
        			Actor tempAc = gr.get(tempLoc);
        			if (tempAc instanceof Critter ) {
        				actors.add(tempAc);
        			}
        		}
        	}
        }

        return actors;
    }
      
    /**
     * Randomly selects a neighbor and changes this critter's color to be the
     * same as that neighbor's. If there are no neighbors, no action is taken.
     */
    public void processActors(ArrayList<Actor> actors)
    {
        if (actors.size() < courageValue) {
        	brighten();
        }
        else {
        	darken();
        }
    }
    
    /**
     * Brighten the color.
     */
    private void brighten() {
    	int colorMaxValue = 255;
    	Color c = getColor();
        int red = (int) (c.getRed() * (1 + COLOR_CHANGING_FACTOR));
        if (red > colorMaxValue) {
        	red = colorMaxValue;
        }
        int green = (int) (c.getGreen() * (1 + COLOR_CHANGING_FACTOR));
        if (green > colorMaxValue) {
        	green = colorMaxValue;
        }
        int blue = (int) (c.getBlue() * (1 + COLOR_CHANGING_FACTOR));
        if (blue > colorMaxValue) {
        	blue = colorMaxValue;
        }
        setColor(new Color(red, green, blue));
    }
    
    /**
     * Darken the color.
     */
    private void darken() {
    	Color c = getColor();
        int red = (int) (c.getRed() * (1 - COLOR_CHANGING_FACTOR));
        int green = (int) (c.getGreen() * (1 - COLOR_CHANGING_FACTOR));
        int blue = (int) (c.getBlue() * (1 - COLOR_CHANGING_FACTOR));

        setColor(new Color(red, green, blue));
    }
}
