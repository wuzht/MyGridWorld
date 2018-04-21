import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import info.gridworld.actor.Actor;
import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Location;


public class JumperTest {
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testAct() {
		ActorWorld world = new ActorWorld();
		Jumper jumper = new Jumper();
		world.add(new Location(5, 5), jumper);
		world.add(new Location(4, 5), new Rock());
		jumper.act();
		assertEquals(new Location(3, 5), jumper.getLocation());
		world.add(new Location(1, 5), new Rock());
		jumper.act();
		assertEquals(new Location(3, 5), jumper.getLocation());
		assertEquals(Location.NORTHEAST, jumper.getDirection());
	}

	@Test
	public void testJumper() {
		Jumper jumper = new Jumper();
		assertEquals(Color.RED, jumper.getColor());
	}

	@Test
	public void testJumperColor() {
		Jumper jumper1 = new Jumper(Color.YELLOW);
		assertEquals(Color.YELLOW, jumper1.getColor());
		Jumper jumper2 = new Jumper(Color.BLUE);
		assertEquals(Color.BLUE, jumper2.getColor());
	}

	@Test
	public void testTurn() {
		ActorWorld world = new ActorWorld();
		Jumper jumper = new Jumper();
		world.add(new Location(7, 7), jumper);
		jumper.turn();
		assertEquals(Location.NORTHEAST, jumper.getDirection());
		jumper.turn();
		assertEquals(Location.EAST, jumper.getDirection());
	}

	@Test
	public void testJump() {
		ActorWorld world = new ActorWorld();
		Jumper jumper = new Jumper();
		world.add(new Location(3, 7), jumper);
		jumper.jump();
		assertEquals(new Location(1, 7), jumper.getLocation());
		// The target location is out of the grid, so jumper remove itself from the grid
		jumper.jump();
		Assert.assertTrue(jumper.getLocation() == null);
	}

	@Test
	public void testCanJump() {
		ActorWorld world = new ActorWorld();
		Jumper jumper = new Jumper();
		// False. Facing to the edge of the grid
		world.add(new Location(0, 0), jumper);
		Assert.assertFalse(jumper.canJump());
		// False. The target location is out of the grid
		jumper.moveTo(new Location(0, 1));
		Assert.assertFalse(jumper.canJump());
		// True.
		jumper.moveTo(new Location(3, 3));
		Assert.assertTrue(jumper.canJump());
		// True. Facing to a rock, and nothing in the target location
		world.add(new Location(2, 3), new Rock());
		Assert.assertTrue(jumper.canJump());
		// False. There is a rock in the target location
		world.add(new Location(1, 3), new Rock());
		Assert.assertFalse(jumper.canJump());
		// True. Facing to a flower, and nothing in the target location
		jumper.moveTo(new Location(9, 9));
		world.add(new Location(8, 9), new Flower());
		Assert.assertTrue(jumper.canJump());
		// True. There is a flower in the target location
		world.add(new Location(7, 9), new Flower());
		Assert.assertTrue(jumper.canJump());
		// False. There is an actor in front of it
		jumper.moveTo(new Location(5, 9));
		world.add(new Location(4, 9), new Actor());
		Assert.assertFalse(jumper.canJump());	
	}

}
