import java.awt.Color;

import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Rock;

public final class JumperRunner {
	private JumperRunner() {
		
	}
	
	public static void main(String[] args) {
        ActorWorld world = new ActorWorld();
        world.add(new Jumper(Color.YELLOW));
        world.add(new Rock());
        world.add(new Rock());
        world.add(new Rock());
        world.add(new Rock());
        world.add(new Flower());
        world.add(new Flower());
        world.add(new Bug());
        world.show();
	}
}
