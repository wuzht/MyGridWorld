/* 
 * @author Zhengtao Wu
 */

import info.gridworld.actor.Actor;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.Color;

/**
 * A <code>Jumper</code> is an actor that can jump and turn. It does not
 * leave anything behind it when it jumps. <br />
 */
public class Jumper extends Actor
{
    /**
     * Constructs a red jumper.
     */
    public Jumper()
    {
        setColor(Color.RED);
    }

    /**
     * Constructs a jumper of a given color.
     * @param jumperColor the color for this jumper
     */
    public Jumper(Color jumperColor)
    {
        setColor(jumperColor);
    }

    /**
     * Moves if it can jump, turns otherwise.
     */
    public void act()
    {
        if (canJump()) {
            jump();
        }
        else {
            turn();
        }
    }

    /**
     * Turns the bug 45 degrees to the right without changing its location.
     */
    public void turn()
    {
        setDirection(getDirection() + Location.HALF_RIGHT);
    }

    /**
     * Jumps the jumper forward, not leaving anything behind
     */
    public void jump()
    {
        Grid<Actor> gr = getGrid();
        if (gr == null) {
            return;
        }
        Location loc = getLocation();
        Location next = loc.getAdjacentLocation(getDirection());
        Location desLoc = next.getAdjacentLocation(getDirection());
        if (gr.isValid(desLoc)) {
            moveTo(desLoc);
        }
        else {
            removeSelfFromGrid();
        }
    }

    /**
     * Tests whether this bug can jump forward into a location that is empty or
     * contains a flower.
     * @return true if this jumper can jump.
     */
    public boolean canJump()
    {
        Grid<Actor> gr = getGrid();
        if (gr == null) {
            return false;
        }
        Location loc = getLocation();
        Location next = loc.getAdjacentLocation(getDirection());
        if (!gr.isValid(next)) {
            return false;
        }
        Actor neighbor = gr.get(next);
        if (!((neighbor == null) || (neighbor instanceof Flower) || (neighbor instanceof Rock))) {
        	return false;
        }
        Location desLoc = next.getAdjacentLocation(getDirection());
        if (!gr.isValid(desLoc)) {
        	return false;
        }
        neighbor = gr.get(desLoc);
        return (neighbor == null) || (neighbor instanceof Flower);
        // ok to jump into empty location or onto flower
        // not ok to jump onto any other actor
    }
}
