/* 
 * @author Wu Zhengtao
 */

import info.gridworld.actor.Bug;

/**
 * A <code>DancingBug</code> traces out a Dancing. <br />
 */
public class DancingBug extends Bug
{
    // the turning pattern of the DancingBug
    private int[] turningPattern;
    // the steps of the bug
    private int steps;

    /**
     * Constructs a dancing bug
     * @param pattern the turning pattern of the bug
     */
    public DancingBug(int[] pattern)
    {
        steps = 0;
        turningPattern = pattern.clone();
    }

    public void myTurn(int turnNum) {
    	for (int i = 0; i < turnNum; ++i) {
    		turn();
    	}
    }
    /**
     * Moves to the next location.
     */
    public void act()
    {
        myTurn(turningPattern[steps]);
        steps = (steps + 1 == turningPattern.length) ? 0 : steps + 1;
        super.act();
    }
}
