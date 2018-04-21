/* 
 * @author Wu Zhengtao
 */

import info.gridworld.actor.Bug;

/**
 * A <code>SpiralBug</code> traces out a Spiral of a given size. <br />
 */
public class SpiralBug extends Bug
{
    private int steps;
    private int sideLength;

    /**
     * Constructs a circle bug that traces a Spiral of a given side length
     * @param length the side length
     */
    public SpiralBug(int length)
    {
        steps = 0;
        sideLength = length;
    }

    /**
     * Moves to the next location of the square.
     */
    public void act()
    {
        if (steps < sideLength && canMove())
        {
            move();
            steps++;
        }
        else
        {
            turn();
            turn();
            steps = 0;
            // When the SpiralBug turns, the sideLength plus one
            sideLength++;
        }
    }
}
