/* 
 * @author Zhengtao Wu
 */

import info.gridworld.actor.Actor;
import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Location;

import java.awt.Color;

/**
 * This class runs a world that contains chameleon critters. <br />
 * This class is not tested on the AP CS A and AB exams.
 */
public final class RockHoundRunner
{
	private RockHoundRunner() {
		
	}
	
    public static void main(String[] args)
    {
        ActorWorld world = new ActorWorld();
        world.add(new Rock());
        world.add(new Rock());
        world.add(new Rock(Color.BLUE));
        world.add(new Rock(Color.PINK));
        world.add(new Rock(Color.RED));
        world.add(new Rock(Color.YELLOW));
        world.add(new Rock());
        world.add(new Rock());
        world.add(new Rock(Color.BLUE));
        world.add(new Rock(Color.PINK));
        world.add(new Rock(Color.RED));
        world.add(new Rock(Color.YELLOW));
        world.add(new Actor());
        world.add(new Actor());
        world.add(new Flower());
        world.add(new Flower());
        world.add(new RockHound());
        world.show();
    }
}