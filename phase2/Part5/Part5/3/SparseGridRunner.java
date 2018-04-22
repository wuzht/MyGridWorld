
/* 
 * @author Zhengtao Wu
 */
import info.gridworld.actor.Actor;
import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Critter;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Location;
import info.gridworld.actor.Flower;

/**
 * This class runs a world with additional grid choices.
 */
public final class SparseGridRunner {
	private SparseGridRunner() {

	}

	public static void main(String[] args) {
		ActorWorld world = new ActorWorld();
		world.addGridClass("LinkedListSparseBoundedGrid");
		world.addGridClass("HashMapSparseBoundedGrid");
		world.addGridClass("UnboundedGrid2");
		world.add(new Location(7, 8), new Critter());
		world.add(new Critter());
		world.add(new Rock());
		world.add(new Flower());
		world.add(new Actor());
		world.add(new Bug());
		world.show();
	}
}