/* 
 * @author Zhengtao Wu
 */

import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Critter;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Location;

import java.awt.Color;

/**
 * This class runs a world that contains BlusterCritterRunner. <br />
 * This class is not tested on the AP CS A and AB exams.
 */
public final class BlusterCritterRunner
{
	private BlusterCritterRunner() {
		
	}
	
    public static void main(String[] args)
    {
        ActorWorld world = new ActorWorld();
        for (int i = 0; i < 8; ++i) {
        	world.add(new Critter());
        }
        BlusterCritter alice = new BlusterCritter(3);
        alice.setColor(Color.YELLOW);
        world.add(alice);
        world.show();
    }
}