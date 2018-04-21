/* 
 * @author Wu Zhengtao
 */

import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Location;

import java.awt.Color;

/**
 * This class runs a world that contains dancing bugs. <br />
 * This class is not tested on the AP CS A and AB exams.
 */
public final class DancingBugRunner
{
	private DancingBugRunner() {
		
	}
	
    public static void main(String[] args)
    {
    	int[] arr = {4, 1, 2, 3, 5, 1, 8};
        ActorWorld world = new ActorWorld();
        DancingBug alice = new DancingBug(arr);
        alice.setColor(Color.ORANGE);
        world.add(new Location(7, 8), alice);
        world.show();
    }
}