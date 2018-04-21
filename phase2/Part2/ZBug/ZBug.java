/* 
 * @author Wu Zhengtao
 */

import info.gridworld.actor.Bug;
import info.gridworld.grid.Location;

/**
 * A <code>ZBug</code> traces out a Z of a given size. <br />
 */
public class ZBug extends Bug
{
    private int steps;
    private int sideLength;
    
    // The currentPath of the bug moving
    private int currentPath;

    /**
     * Constructs a circle bug that traces a Z of a given side length
     * @param length the side length
     */
    public ZBug(int length)
    {
        steps = 0;
        sideLength = length;
        currentPath = 0;
        
        // Let the bug face to east
        turn();
        turn();
    }

    /**
     * Moves to the next location of the square.
     */
    public void act()
    {        
        if (currentPath == 3) {
        	return;
        }
        if (steps < sideLength && canMove()) {
        	move();
        	steps++;
        }
        else if (canMove()) {
        	if (currentPath == 0) {
        		// Let the bug face to southwest
        		setDirection(Location.SOUTHWEST);
        		currentPath++;
        	}
        	else if (currentPath == 1) {
        		// Let the bug face to east
        		setDirection(Location.EAST);
        		currentPath++;
        	}
        	else if (currentPath == 2) {
        		// The bug should stop
        		currentPath++;
        	}
        	// set the steps to 0 and start a new path
        	steps = 0;
        }
    }
}
