/* 
 * @author Zhengtao Wu
 */

import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Rock;

/**
 * This class runs a world that contains crab critters. <br />
 * This class is not tested on the AP CS A and AB exams.
 */
public final class QuickCrabRunner
{
	private QuickCrabRunner() {
		
	}
	
    public static void main(String[] args)
    {
        ActorWorld world = new ActorWorld();
        for (int i = 0; i < 4; ++i) {
        	world.add(new Rock());
        	world.add(new Flower());
        	world.add(new Bug());
        	world.add(new QuickCrab());
        }
        world.show();
    }
}