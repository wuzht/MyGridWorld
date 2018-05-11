# Answer Part 1~5

> GridWorld

# Part 1

## Set 1 - Step 1

1. No. When there is a Rock, Actor, Bug or the Border in front of it, it will not move to a new location.

2. The bug moves forward if it can move.

3. The bug rotates 45 degrees to the right until it can move.

4. A bug leaves behind a flower with the same color as the bug when it moves.

5. It moves forward if it can move. Otherwise, it will rotate 45 degrees to the right until it can move. I.e., when the bug is facing the edge(in a direction of 90 degrees or 45 degrees), it can not move, it will rotate 45 degrees to the right until it can move.

6. It rotates 45 degrees to the right.

7. No, it does not move.

8. The color of the flowers turns to black gradually.

9. No.

10. No. There can be only one actor in the same location in the grid at the same time.



## Set 1 - Step 2

1. ​

   | Degrees | Compass Direction |
   | ------- | ----------------- |
   | 0       | North             |
   | 45      | Northeast         |
   | 90      | East              |
   | 135     | Southeast         |
   | 180     | South             |
   | 225     | Southwest         |
   | 270     | West              |
   | 315     | Northwest         |
   | 360     | North             |



2. ​

   (1) It can be moved to anywhere as long as the target coordinate is inside the grids. If there is an actor in the target gird, this actor will be erased while the bug you moved will be there.
   (2) The bug can move as far as expected as long as the target coordinate is valid(inside the grids).
   (3) A message dialog window with exception information will pop out if you try to move the bug outside the grid. And the bug will not move.


3. ​

   void setColor(java.awt.Color)

4. ​

   The bug kept moving.





# Part 2

## Set 2

### 1

The instance variable `sideLength` defines the number of steps that the BoxBug moves on each side of the box.

* In one of the sides of the box, if the steps < sideLength and the bug can move, the bug moves forward:

  ```java
  // @file: projects/boxBug/BoxBug.java
  // @line: 45~49
  if (steps < sideLength && canMove())
  {
      move();
      steps++;
  }
  ```

* Otherwise, the bug turn 90 degrees to the right without moving, and `steps`is set to 0:

  ```java
  // @file: projects/boxBug/BoxBug.java
  // @line: 52~54
  turn();
  turn();
  steps = 0;
  ```

### 2

The instance variable `steps` records how many steps the BoxBug has moved on the current side of the box.

- In one of the sides of the box, if the steps < sideLength and the bug can move, the bug moves forward:

  ```java
  // @file: projects/boxBug/BoxBug.java
  // @line: 45~49
  if (steps < sideLength && canMove())
  {
      move();
      steps++;
  }
  ```

- Otherwise, the bug turn 90 degrees to the right without moving, and `steps` is set to 0:

  ```java
  // @file: projects/boxBug/BoxBug.java
  // @line: 52~54
  turn();
  turn();
  steps = 0;
  ```

### 3

When the `steps` becomes equal to `sideLength`, the bug has to turn 90 degrees to the right to move into the next side of the box. However, the method `turn` can only let the bug turn 45 degrees to right, hence, it has to call the method `turn` twice to make a 90 degrees turn.

The method `turn` is shown below:

```java
// @file: framework/info/gridworld/actor/Bug.java
// @line: 62~65
    public void turn()
    {
        setDirection(getDirection() + Location.HALF_RIGHT);
    }
```
### 4

Because the `BoxBug` class extends the `Bug` class, and the `Bug` class has the method `move`. So the `BoxBug` inherits the method `move` from `Bug` class.

The method `move` in `Bug` class is shown below:

```java
// @file: framework/info/gridworld/actor/Bug.java
// @line: 67~84
	/**
     * Moves the bug forward, putting a flower into the location it previously
     * occupied.
     */
    public void move()
    {
        Grid<Actor> gr = getGrid();
        if (gr == null)
            return;
        Location loc = getLocation();
        Location next = loc.getAdjacentLocation(getDirection());
        if (gr.isValid(next))
            moveTo(next);
        else
            removeSelfFromGrid();
        Flower flower = new Flower(getColor());
        flower.putSelfInGrid(gr, loc);
    }
```
### 5

Yes. When constructing a BoxBug, the argument `length` is passed, and `sideLength` is assigned by `length`. Since then, the `sideLength` is fixed and can not be changed.

The constructor of BoxBug is shown below:

```java
// @file: projects/boxBug/BoxBug.java
// @line: 34~38
public BoxBug(int length)
{
    steps = 0;
    sideLength = length;
}
```
### 6

Yes. If the BoxBug can not move forward, i.e, there is another Actor like a Rock, a Bug or Border in front of the BoxBug, it will turn and start a new box path.

```java
// @file: projects/boxBug/BoxBug.java
// @line: 52~54
turn();
turn();
steps = 0;
```
### 7

* When a BoxBug is constructed, the value of `steps` is initialized to zero:

  ```java
  // @file: projects/boxBug/BoxBug.java
  // @line: 34~38
  public BoxBug(int length)
  {
      steps = 0;
      sideLength = length;
  }
  ```

* When the `steps` of a BoxBug is equal to its `sideLength`, i.e., it has finished moving in one side of the box path, 

* Or the BoxBug can not move forward and it has to start a new box path:

  ```java
  // @file: projects/boxBug/BoxBug.java
  // @line: 43~56
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
      }
  }
  ```



# Part 3

## Set 3

### 1

 `loc1.getRow()`

```java
// @file: framework/info/gridworld/gird/Location.java
// @line: 110~113
public int getRow()
{
    return row;
}
```



### 2

`false`

```java
// @file: framework/info/gridworld/gird/Location.java
// @line: 205~212
public boolean equals(Object other)
{
    if (!(other instanceof Location))
        return false;

    Location otherLoc = (Location) other;
    return getRow() == otherLoc.getRow() && getCol() == otherLoc.getCol();
}
```



### 3

`(4, 4)`

```java
// @file: framework/info/gridworld/gird/Location.java
// @line: 130~169
public Location getAdjacentLocation(int direction)
{
    // reduce mod 360 and round to closest multiple of 45
    int adjustedDirection = (direction + HALF_RIGHT / 2) % FULL_CIRCLE;
    if (adjustedDirection < 0)
        adjustedDirection += FULL_CIRCLE;

    adjustedDirection = (adjustedDirection / HALF_RIGHT) * HALF_RIGHT;
    int dc = 0;
    int dr = 0;
    if (adjustedDirection == EAST)
        dc = 1;
    else if (adjustedDirection == SOUTHEAST)
    {
        dc = 1;
        dr = 1;
    }
    else if (adjustedDirection == SOUTH)
        dr = 1;
    else if (adjustedDirection == SOUTHWEST)
    {
        dc = -1;
        dr = 1;
    }
    else if (adjustedDirection == WEST)
        dc = -1;
    else if (adjustedDirection == NORTHWEST)
    {
        dc = -1;
        dr = -1;
    }
    else if (adjustedDirection == NORTH)
        dr = -1;
    else if (adjustedDirection == NORTHEAST)
    {
        dc = 1;
        dr = -1;
    }
    return new Location(getRow() + dr, getCol() + dc);
}
```



### 4

`135`(degrees)

```java
// @file: framework/info/gridworld/gird/Location.java
// @line: 178~195
public int getDirectionToward(Location target)
{
    int dx = target.getCol() - getCol();
    int dy = target.getRow() - getRow();
    // y axis points opposite to mathematical orientation
    int angle = (int) Math.toDegrees(Math.atan2(-dy, dx));

    // mathematical angle is counterclockwise from x-axis,
    // compass angle is clockwise from y-axis
    int compassAngle = RIGHT - angle;
    // prepare for truncating division by 45 degrees
    compassAngle += HALF_RIGHT / 2;
    // wrap negative angles
    if (compassAngle < 0)
        compassAngle += FULL_CIRCLE;
    // round to nearest multiple of 45
    return (compassAngle / HALF_RIGHT) * HALF_RIGHT;
}
```



### 5

When the `getAdjacentLocation` method is called, the parameter `direction` is passed to this method. And this method returns the adjacent location in the compass direction which is closest to the  `direction` in the parameter list.

```java
// @file: framework/info/gridworld/gird/Location.java
// @line: 130~169
public Location getAdjacentLocation(int direction)
{
    // reduce mod 360 and round to closest multiple of 45
    int adjustedDirection = (direction + HALF_RIGHT / 2) % FULL_CIRCLE;
    if (adjustedDirection < 0)
        adjustedDirection += FULL_CIRCLE;

    adjustedDirection = (adjustedDirection / HALF_RIGHT) * HALF_RIGHT;
    int dc = 0;
    int dr = 0;
    if (adjustedDirection == EAST)
        dc = 1;
    else if (adjustedDirection == SOUTHEAST)
    {
        dc = 1;
        dr = 1;
    }
    else if (adjustedDirection == SOUTH)
        dr = 1;
    else if (adjustedDirection == SOUTHWEST)
    {
        dc = -1;
        dr = 1;
    }
    else if (adjustedDirection == WEST)
        dc = -1;
    else if (adjustedDirection == NORTHWEST)
    {
        dc = -1;
        dr = -1;
    }
    else if (adjustedDirection == NORTH)
        dr = -1;
    else if (adjustedDirection == NORTHEAST)
    {
        dc = 1;
        dr = -1;
    }
    return new Location(getRow() + dr, getCol() + dc);
}
```



## Set 4

###1

Let `myGrid` be a `Grid` object,

* Obtain a count of the objects in a grid:

  Then the count of the objects is `myGrid.getOccupiedLocations().size()`

  **Reasons:**

  For the `BoundedGrid`:

  ```java
  // @file: framework/info/gridworld/gird/BoundedGrid.java
  // @line: 66~83
  public ArrayList<Location> getOccupiedLocations()
  {
      ArrayList<Location> theLocations = new ArrayList<Location>();

      // Look at all grid locations.
      for (int r = 0; r < getNumRows(); r++)
      {
          for (int c = 0; c < getNumCols(); c++)
          {
              // If there's an object at this location, put it in the array.
              Location loc = new Location(r, c);
              if (get(loc) != null)
                  theLocations.add(loc);
          }
      }

      return theLocations;
  }
  ```

  For the `UnboundedGrid`:

  ```java
  // @file: framework/info/gridworld/gird/UnboundedGrid.java
  // @line: 58~64
  public ArrayList<Location> getOccupiedLocations()
  {
      ArrayList<Location> a = new ArrayList<Location>();
      for (Location loc : occupantMap.keySet())
          a.add(loc);
      return a;
  }
  ```

  ​


* Obtain a count of the empty locations in a bounded grid:

  Then the count of the empty locations is `myGrid.getNumRows() * myGrid.getNumCols() - myGrid.getOccupiedLocations().size()`

  **Reasons:**

  (Notice that the reason of method `getOccupiedLocations()` is explained above)

  ```java
  // @file: framework/info/gridworld/gird/BoundedGrid.java
  // @line: 48~58
  public int getNumRows()
  {
      return occupantArray.length;
  }

  public int getNumCols()
  {
      // Note: according to the constructor precondition, numRows() > 0, so
      // theGrid[0] is non-null.
      return occupantArray[0].length;
  }
  ```

  ​

### 2

Let `myGrid` be a `Grid` object.

`myGrid.isValid(new Location(10, 10))`

The method `isValid` in `Grid` returns true if the location is in the grid, returns false otherwise.

**Reasons:**

For the `BoundedGrid`:

```java
// @file: framework/info/gridworld/gird/BoundedGrid.java
// @line: 60~64
public boolean isValid(Location loc)
{
    return 0 <= loc.getRow() && loc.getRow() < getNumRows()
        && 0 <= loc.getCol() && loc.getCol() < getNumCols();
}
```

For the `UnboundedGrid`, it is unbounded, so `isValid` always returns true:

```java
// @file: framework/info/gridworld/gird/UnboundedGrid.java
// @line: 53~56
public boolean isValid(Location loc)
{
    return true;
}
```



### 3

Because `Grid` is an interface. In Java, The methods in an interface is declared, which must be implemented in another class. The implementations of these methods can be found in `AbstractGrid`, `BoundedGrid` and `UnboundedGrid`

For example, the method `getNumRows()` is declared in `Grid`:

```java
// @file: framework/info/gridworld/gird/Grid.java
// @line: 35
int getNumRows();
```

And the implementation of this method is in `BoundedGrid` and `UnboundedGrid`, respectively:

```java
// @file: framework/info/gridworld/gird/BoundedGrid.java
// @line: 48~51
public int getNumRows()
{
    return occupantArray.length;
}
```

```java
// @file: framework/info/gridworld/gird/UnboundedGrid.java
// @line: 43~46
public int getNumRows()
{
    return -1;
}
```



### 4

No. In those methods that return multiple objects, we do not know the number of the returning objects. If we return them in an array, we must count the number of the objects, then create an array to be filled in, which we need to traval the whole grid first. Since the ArrayList is able to grow dynamically with the `add` method, which is more efficient and convenient. Hence, returning the objects in an ArrayList is better.

For example, the `getNeighbors` method is shown below:

```java
// @file: framework/info/gridworld/gird/AbstractGrid.java
// @line: 28~34
public ArrayList<E> getNeighbors(Location loc)
{
    ArrayList<E> neighbors = new ArrayList<E>();
    for (Location neighborLoc : getOccupiedAdjacentLocations(loc))
        neighbors.add(get(neighborLoc));
    return neighbors;
}
```

Obviously, returning the objects in an ArrayList is better with the reasons explained above.



## Set 5

### 1

location, direction, color

```java
// @file: framework/info/gridworld/actor/Actor.java
// @line: 32~34
private Location location;
private int direction;
private Color color;
```



### 2

Its direction is initialized to `NORTH` and its color is initialized to blue

```java
// @file: framework/info/gridworld/actor/Actor.java
// @line: 39~45
public Actor()
{
    color = Color.BLUE;
    direction = Location.NORTH;
    grid = null;
    location = null;
}
```



### 3

Because an Actor has its own state(`color`, `direction`, `direction`and so on) and behavior(e.g: `getColor()`, ` setDirection()`), while an interface is not allowed to have some variables or implement methods.

The states of an Actor:

```java
// @file: framework/info/gridworld/actor/Actor.java
// @line: 31~34
private Grid<Actor> grid;
private Location location;
private int direction;
private Color color;
```

Some of the implemented methods of an Actor:

```java
// @file: framework/info/gridworld/actor/Actor.java
// @line: 51~54
public Color getColor()
{
    return color;
}
// @file: framework/info/gridworld/actor/Actor.java
// @line: 80~85
public void setDirection(int newDirection)
{
    direction = newDirection % Location.FULL_CIRCLE;
    if (direction < 0)
        direction += Location.FULL_CIRCLE;
}
```



### 4

1. (Q: Can an actor put itself into a grid twice without first removing itself? ) No, an actor can not put itself into a grid twice without first removing itself. If the BugRunner.java is written below and compiled:

   ```java
   // @file: projects/firstProject/BugRunner.java
   // @line: 31~40
   public static void main(String[] args)
   {
       ActorWorld world = new ActorWorld();
       Bug myBug = new Bug();
       world.add(myBug);
       myBug.putSelfInGrid(myBug.getGrid(), myBug.getLocation());
       //world.add(new Bug());
       world.add(new Rock());
       world.show();
   }
   ```

   It throws an Exception of ` java.lang.IllegalStateException: This actor is already contained in a grid.`

2. (Q: Can an actor remove itself from a grid twice? ) No. if an actor is removed once, it can not be removed twice. If the BugRunner.java is written below and compiled:

   ```java
   // @file: projects/firstProject/BugRunner.java
   // @line: 31~40
   public static void main(String[] args)
   {
       ActorWorld world = new ActorWorld();
       Bug myBug = new Bug();
       world.add(myBug);
       myBug.putSelfInGrid(myBug.getGrid(), myBug.getLocation());
       //world.add(new Bug());
       world.add(new Rock());
       world.show();
   }
   ```

   It throws an Exception of ` java.lang.IllegalStateException: This actor is not contained in a grid.`

3. (Q: Can an actor be placed into a grid, remove itself, and then put itself back? ) Yes, it can. If the BugRunner.java is written below and compiled:

   ```java
   // @file: projects/firstProject/BugRunner.java
   // @line: 31~40
   public static void main(String[] args)
   {
       ActorWorld world = new ActorWorld();
       Bug myBug = new Bug();
       world.add(myBug);
       Grid myGrid = myBug.getGrid();
       Location myLocation = myBug.getLocation();
       //world.add(new Bug());
       world.add(new Rock());
       world.show();
       myBug.removeSelfFromGrid();
       myBug.putSelfInGrid(myGrid, myLocation);
   }
   ```

   The program runs normally without any error.



###5

We can use the method `setDirection`. Let `ac` be an `Actor`, then we can turn it 90 degrees to the right with the code shown below:

```java
ac.setDirection(ac.getDirection() + Location.RIGHT);
```

or

```java
ac.setDirection(ac.getDirection() + Location.EAST);
```

or

```java
ac.setDirection(ac.getDirection() + 90);
```

The method `setDirection`:

```java
// @file: framework/info/gridworld/actor/Actor.java
// @line: 80~85
public void setDirection(int newDirection)
{
    direction = newDirection % Location.FULL_CIRCLE;
    if (direction < 0)
        direction += Location.FULL_CIRCLE;
}
```



## Set 6

### 1

```java
// @file: framework/info/gridworld/actor/Bug.java
// @line: 96~99
Location loc = getLocation();
Location next = loc.getAdjacentLocation(getDirection());
if (!gr.isValid(next))
    return false;
```

The statements above in the canMove method ensure that a bug does not try to move out of its grid.



### 2

```java
// @file: framework/info/gridworld/actor/Bug.java
// @line: 100~101
Actor neighbor = gr.get(next);
return (neighbor == null) || (neighbor instanceof Flower);
```

The statements above in the canMove method ensure that a bug moves only when the next location has nothing or has a flower. So it determines that a bug will not walk into a rock.

### 3

`isValid` and `get`. The `isValid` method ensures that the next location of the bug is valid(inside the grid). The `get` method get the object in the next location to see if the next location has nothing or has a flower.

```java
// @file: framework/info/gridworld/actor/Bug.java
// @line: 98
if (!gr.isValid(next))
    
// @file: framework/info/gridworld/grid/Grid.java
// @line: 50
boolean isValid(Location loc);
```

```java
// @file: framework/info/gridworld/actor/Bug.java
// @line: 100
Actor neighbor = gr.get(next);

// @file: framework/info/gridworld/grid/Grid.java
// @line: 79
E get(Location loc);
```



### 4

`getAdjacentLocation`. This method is invoked by the `canMove` method in order to get the next location of the bug based on its current direction.

```java
// @file: framework/info/gridworld/actor/Bug.java
// @line: 97
Location next = loc.getAdjacentLocation(getDirection());
    
// @file: framework/info/gridworld/grid/Location.java
// @line: 130
public Location getAdjacentLocation(int direction)
```



### 5

* `getGrid`

  ```java
  // @file: framework/info/gridworld/actor/Bug.java
  // @line: 93
  Grid<Actor> gr = getGrid();
      
  // @file: framework/info/gridworld/actor/Actor.java
  // @line: 92~95
  public Grid<Actor> getGrid()
  {
      return grid;
  }
  ```

* `getLocation`

  ```java
  // @file: framework/info/gridworld/actor/Bug.java
  // @line: 96
  Location loc = getLocation();
      
  // @file: framework/info/gridworld/actor/Actor.java
  // @line: 102~105
  public Location getLocation()
  {
      return location;
  }
  ```

* `getDirection`

  ```java
  // @file: framework/info/gridworld/actor/Bug.java
  // @line: 97
  Location next = loc.getAdjacentLocation(getDirection());
      
  // @file: framework/info/gridworld/actor/Actor.java
  // @line: 69~72
  public int getDirection()
  {
      return direction;
  }
  ```

  ​

### 6

The bug will remove itself from the grid.

```java
// @file: framework/info/gridworld/actor/Bug.java
// @line: 79~81
if (gr.isValid(next))
    moveTo(next);
else
    removeSelfFromGrid();
```



### 7

Yes, the variable `loc` is needed. `loc` records the bug's location before it moves:

```java
// @file: framework/info/gridworld/actor/Bug.java
// @line: 76
Location loc = getLocation();
```

And `loc` will be used later to put a flower in the bug's old location after it moves:

```java
// @file: framework/info/gridworld/actor/Bug.java
// @line: 83
flower.putSelfInGrid(gr, loc);
```



### 8

Because of the code below in `move` method:

```java
// @file: framework/info/gridworld/actor/Bug.java
// @line: 82~83
Flower flower = new Flower(getColor());
flower.putSelfInGrid(gr, loc);
```

First, it construct a flower with the bug's color. Then it is dropped.



### 9

* If a bug removes itself from the grid by invoking the `removeSelfFromGrid` method alone, which is inherited from the `Actor` class, it will not place a flower into its previous location.

  ```java
  // @file: framework/info/gridworld/actor/Actor.java
  // @line: 133~146
  public void removeSelfFromGrid()
  {
      if (grid == null)
          throw new IllegalStateException(
          "This actor is not contained in a grid.");
      if (grid.get(location) != this)
          throw new IllegalStateException(
          "The grid contains a different actor at location "
          + location + ".");

      grid.remove(location);
      grid = null;
      location = null;
  }
  ```

  No flower is dropped.

* If the `removeSelfFromGrid` method is invoked in the `move` method in `Bug` class, it will place a flower into its previous location.

  ```java
  // @file: framework/info/gridworld/actor/Bug.java
  // @line: 78~83
  if (gr.isValid(next))
      moveTo(next);
  else
      removeSelfFromGrid();
  Flower flower = new Flower(getColor());
  flower.putSelfInGrid(gr, loc);
  ```

  ​

### 10

Notice that `loc` is the bug's previous location. And the statements that place the flower is shown below:

```java
// @file: framework/info/gridworld/actor/Bug.java
// @line: 82~83
Flower flower = new Flower(getColor());
flower.putSelfInGrid(gr, loc);
```



###11

Four times. Since each one turns 45 degrees to the right:

```java
// @file: framework/info/gridworld/actor/Bug.java
// @line: 62~65
public void turn()
{
    setDirection(getDirection() + Location.HALF_RIGHT);
}
```



# Part 4

## Set 7

### 1

- `act`

  ```java
  // @file: framework/info/gridworld/actor/Critter.java
  // @line: 38
  public void act()
  ```

- `getActors`

  ```java
  // @file: framework/info/gridworld/actor/Critter.java
  // @line: 56
  public ArrayList<Actor> getActors()
  ```

- `processActors`

  ```java
  // @file: framework/info/gridworld/actor/Critter.java
  // @line: 71
  public void processActors(ArrayList<Actor> actors)
  ```

- `getMoveLocations`

  ```java
  // @file: framework/info/gridworld/actor/Critter.java
  // @line: 88
  public ArrayList<Location> getMoveLocations()
  ```

- `selectMoveLocation`

  ```java
  // @file: framework/info/gridworld/actor/Critter.java
  // @line: 104
  public Location selectMoveLocation(ArrayList<Location> locs)
  ```

- `selectMoveLocation`

  ```java
  // @file: framework/info/gridworld/actor/Critter.java
  // @line: 125
  public void makeMove(Location loc)
  ```



### 2

`getActors`, `processActors`, `getMoveLocations`, `selectMoveLocation`, `makeMove`

which can be found in the `act` method: 

```java
// @file: framework/info/gridworld/actor/Critter.java
// @line: 42~46
ArrayList<Actor> actors = getActors();
processActors(actors);
ArrayList<Location> moveLocs = getMoveLocations();
Location loc = selectMoveLocation(moveLocs);
makeMove(loc);
```

### 3

Yes. If a subclass of Critter needs to select Actors differently from Critter class, the `getActors` method should be overridden.

```java
// @file: framework/info/gridworld/actor/Critter.java
// @line: 56~59
public ArrayList<Actor> getActors()
{
    return getGrid().getNeighbors(getLocation());
}
```

### 4

Firstly, the Critter gets a list of Actors to process, it could eat them all, change their color or make them move and so on.

```java
// @file: framework/info/gridworld/actor/Critter.java
// @line: 71~78
public void processActors(ArrayList<Actor> actors)
{
    for (Actor a : actors)
    {
        if (!(a instanceof Rock) && !(a instanceof Critter))
            a.removeSelfFromGrid();
    }
}
```

### 5

- `getMoveLocations`

  Firstly, the method `act` invokes the method `getMoveLocations`, which returns a list(`moveLocs`) of all the empty neighboring locations arround the Critter.

  ```java
  // @file: framework/info/gridworld/actor/Critter.java
  // @line: 44
  ArrayList<Location> moveLocs = getMoveLocations();
  
  // @file: framework/info/gridworld/actor/Critter.java
  // @line: 88~91
  public ArrayList<Location> getMoveLocations()
  {
      return getGrid().getEmptyAdjacentLocations(getLocation());
  }
  ```

- `selectMoveLocation`

  Secondly, the method `act` invokes the method `selectMoveLocation` to select a location `loc` randomly from the location list(`moveLocs`) above.

  ```java
  // @file: framework/info/gridworld/actor/Critter.java
  // @line: 45
  Location loc = selectMoveLocation(moveLocs);
  
  // @file: framework/info/gridworld/actor/Critter.java
  // @line: 104~111
  public Location selectMoveLocation(ArrayList<Location> locs)
  {
      int n = locs.size();
      if (n == 0)
          return getLocation();
      int r = (int) (Math.random() * n);
      return locs.get(r);
  }
  ```

- `makeMove`

  Finally,  the method `act` invokes the method `makeMove` to move the critter to the location `loc`.  

  ```java
  // @file: framework/info/gridworld/actor/Critter.java
  // @line: 46
  makeMove(loc);
  
  // @file: framework/info/gridworld/actor/Critter.java
  // @line: 125~131
  public void makeMove(Location loc)
  {
      if (loc == null)
          removeSelfFromGrid();
      else
          moveTo(loc);
  }
  ```

  ​

### 6

`Critter` extends `Actor`, and `Actor` has its constructor. If we don't write a constructor in `Critter`, Java will create a default constructor for it by calling the constructor in `Actor`, which will construct a critter with blue color and facing north.

```java
// @file: framework/info/gridworld/actor/Actor.java
// @line: 39~45
public Actor()
{
    color = Color.BLUE;
    direction = Location.NORTH;
    grid = null;
    location = null;
}
```

## Set 8

### 1

The `act` method calls methods `getActors`, `processActors`, `getMoveLocations`, `selectMoveLocation` and `makeMove`.

```java
// @file: framework/info/gridworld/actor/Critter.java
// @line: 42~46
ArrayList<Actor> actors = getActors();
processActors(actors);
ArrayList<Location> moveLocs = getMoveLocations();
Location loc = selectMoveLocation(moveLocs);
makeMove(loc);
```

Although `ChameleonCritter` does not override the `act` method, it does override the methods `processActors` and `makeMove`, making the `act` method operates differerntly.

```java
// @file: projects/critters/ChameleonCritter.java
// @line: 36~45
public void processActors(ArrayList<Actor> actors)
{
    int n = actors.size();
    if (n == 0)
        return;
    int r = (int) (Math.random() * n);

    Actor other = actors.get(r);
    setColor(other.getColor());
}

// @file: projects/critters/ChameleonCritter.java
// @line: 50~54
public void makeMove(Location loc)
{
    setDirection(getLocation().getDirectionToward(loc));
    super.makeMove(loc);
}
```

Hence, `act` cause a ChameleonCritter to act differently from a Critter even though ChameleonCritter does not override `act`.



### 2

The `ChameleonCritter` class override the `makeMove` method. Firstly, it turns its direction towards the new location, and then invokes the `makeMove` method in its super class `Critter` to move to that new location, behaving like a `Critter`.

```java
// @file: projects/critters/ChameleonCritter.java
// @line: 50~54
public void makeMove(Location loc)
{
    setDirection(getLocation().getDirectionToward(loc));
    super.makeMove(loc);
}
```



### 3

Change the method `makeMove` as below:

```java
// @file: projects/critters/ChameleonCritter.java
// @line: 50~54
public void makeMove(Location loc)
{
    Location previousLoc = this.getLocation();
    setDirection(getLocation().getDirectionToward(loc));
    super.makeMove(loc);
    // When it did not move, it does not drop flower
    if (!previousLoc.equals(loc)) {
        Flower myFlower = new Flower(this.getColor());
        myFlower.putSelfInGrid(this.getGrid(), previousLoc);
    }
}
```

Before it moves, record its location `previousLoc`. And then if it moves, drop a flower with its color in `previousLoc  `.



### 4

Because `ChameleonCritter` class wants to process the same list of actors as its base class `Critter`, it does not need the override the `getActors` method.

```java
// @file: framework/info/gridworld/actor/Critter.java
// @line: 56~59
public ArrayList<Actor> getActors()
{
    return getGrid().getNeighbors(getLocation());
}
```



### 5

The `Actor` class contains the `getLocation` method, which is inherited by its subclasses.

```java
// @file: framework/info/gridworld/actor/Actor.java
// @line: 102~105
public Location getLocation()
{
    return location;
}
```



### 6

By calling the method `getGrid`, which is inherited by its subclasses.

```java
// @file: framework/info/gridworld/actor/Actor.java
// @line: 92~95
public Grid<Actor> getGrid()
{
    return grid;
}
```



## Set 9

### 1

Because the `processActors` method processed the list of actor returned by the `getActors`  method:

```java
// @file: framework/info/gridworld/actor/Critter.java
// @line: 42~43
ArrayList<Actor> actors = getActors();
processActors(actors);
```

and the `CrabCritter` class override the `getActors` method, only adding Actors which are in the locations immediately in the front, to the right-front, or to the left-front of the CrabCritter: 

```java
// @file: projects/critters/CrabCritter.java
// @line: 44~57
public ArrayList<Actor> getActors()
{
    ArrayList<Actor> actors = new ArrayList<Actor>();
    int[] dirs =
    { Location.AHEAD, Location.HALF_LEFT, Location.HALF_RIGHT };
    for (Location loc : getLocationsInDirections(dirs))
    {
        Actor a = getGrid().get(loc);
        if (a != null)
            actors.add(a);
    }

    return actors;
}
```

Hence, `CrabCritter` does not override the `processActors` method.



### 2

The `CrabCritter` class override the `getActors` method, adding Actors which are in the locations immediately in the front, to the right-front, or to the left-front of the CrabCritter: 

```java
// @file: projects/critters/CrabCritter.java
// @line: 44~57
public ArrayList<Actor> getActors()
{
    ArrayList<Actor> actors = new ArrayList<Actor>();
    int[] dirs =
    { Location.AHEAD, Location.HALF_LEFT, Location.HALF_RIGHT };
    for (Location loc : getLocationsInDirections(dirs))
    {
        Actor a = getGrid().get(loc);
        if (a != null)
            actors.add(a);
    }

    return actors;
}
```

Any neighbors in those three directions which are added by the `getActors` method above will be eaten when `processActors` method is invoked, while the neighbors in the other locations will not be eaten.



### 3

The array `directions` which are in method `getLocationsInDirections` parameter list indicates the the possible directions that a CrabCritter can eat or move. The `getLocationsInDirections` method returns a list of valid adjacent locations based on the `directions`.

```java
// @file: projects/critters/CrabCritter.java
// @line: 101~114
public ArrayList<Location> getLocationsInDirections(int[] directions)
{
    ArrayList<Location> locs = new ArrayList<Location>();
    Grid gr = getGrid();
    Location loc = getLocation();

    for (int d : directions)
    {
        Location neighborLoc = loc.getAdjacentLocation(getDirection() + d);
        if (gr.isValid(neighborLoc))
            locs.add(neighborLoc);
    }
    return locs;
}
```



### 4

(4, 3), (4, 4) and (4, 5).

```java
// @file: projects/critters/CrabCritter.java
// @line: 44~57
public ArrayList<Actor> getActors()
{
    ArrayList<Actor> actors = new ArrayList<Actor>();
    int[] dirs =
    { Location.AHEAD, Location.HALF_LEFT, Location.HALF_RIGHT };
    for (Location loc : getLocationsInDirections(dirs))
    {
        Actor a = getGrid().get(loc);
        if (a != null)
            actors.add(a);
    }

    return actors;
}
```



### 5

- Similarities: 

  - They both randomly choose a location from the list of possible move locations returned by the method `getMoveLocations`.

    ```java
    // @file: framework/info/gridworld/actor/Critter.java
    // @line: 44~46
    ArrayList<Location> moveLocs = getMoveLocations();
    Location loc = selectMoveLocation(moveLocs);
    makeMove(loc);
    ```

  - When they move, they do not turn.

    ```java
    // @file: framework/info/gridworld/actor/Critter.java
    // @line: 125~131
    public void makeMove(Location loc)
    {
        if (loc == null)
            removeSelfFromGrid();
        else
            moveTo(loc);
    }
    
    // @file: projects/critters/CrabCritter.java
    // @line: 90
    super.makeMove(loc);
    ```

- Differences:

  - A CrabCritter can only moves to its left or its right, while a Critter can move to any adjacent locations.

    ```java
    // @file: projects/critters/CrabCritter.java
    // @line: 62~72
    public ArrayList<Location> getMoveLocations()
    {
        ArrayList<Location> locs = new ArrayList<Location>();
        int[] dirs =
        { Location.LEFT, Location.RIGHT };
        for (Location loc : getLocationsInDirections(dirs))
            if (getGrid().get(loc) == null)
                locs.add(loc);
    
        return locs;
    }
    
    // @file: framework/info/gridworld/actor/Critter.java
    // @line: 88~91
    public ArrayList<Location> getMoveLocations()
    {
        return getGrid().getEmptyAdjacentLocations(getLocation());
    }
    ```

  - When a CrabCritter doesn't move, it will turn left or right randomly, while a Critter does not turn.

    ```java
    // @file: projects/critters/CrabCritter.java
    // @line: 79~88
    if (loc.equals(getLocation()))
    {
        double r = Math.random();
        int angle;
        if (r < 0.5)
            angle = Location.LEFT;
        else
            angle = Location.RIGHT;
        setDirection(getDirection() + angle);
    }
    
    // @file: framework/info/gridworld/actor/Critter.java
    // @line: 125~131
    public void makeMove(Location loc)
    {
        if (loc == null)
            removeSelfFromGrid();
        else
            moveTo(loc);
    }
    ```

    ​

### 6

It is determined by checking if the target location equals to the CrabCritter's current location. If they are equal, the crab doesn't move, so it turns. Otherwise, it moves.

```java
// @file: projects/critters/CrabCritter.java
// @line: 77~91
public void makeMove(Location loc)
{
    if (loc.equals(getLocation()))
    {
        double r = Math.random();
        int angle;
        if (r < 0.5)
            angle = Location.LEFT;
        else
            angle = Location.RIGHT;
        setDirection(getDirection() + angle);
    }
    else
        super.makeMove(loc);
}
```



### 7

The `CrabCritter` class inherits from `Critter` class with the method `processActors`, it only eats the actors which are not a Rock or a Critter, and a CrabCritter is a Critter, so they don't eat each other.

```java
// @file: framework/info/gridworld/actor/Critter.java
// @line: 71~78
public void processActors(ArrayList<Actor> actors)
{
    for (Actor a : actors)
    {
        if (!(a instanceof Rock) && !(a instanceof Critter))
            a.removeSelfFromGrid();
    }
}
```



# Part 5

## Set 10

### 1

The `isValid` method is specified in the `Grid` interface.

```java
// @file: framework/info/gridworld/grid/Grid.java
// @line: 50
boolean isValid(Location loc);
```

The `BoundedGird` class and `UnboundedGrid` class provide an implementation of this method, respectively.

```java
// @file: framework/info/gridworld/grid/BoundedGird.java
// @line: 60~64
public boolean isValid(Location loc)
{
    return 0 <= loc.getRow() && loc.getRow() < getNumRows()
        && 0 <= loc.getCol() && loc.getCol() < getNumCols();
}

// @file: framework/info/gridworld/grid/UnboundedGrid.java
// @line: 53~56
public boolean isValid(Location loc)
{
    return true;
}
```



### 2

- The `getValidAdjacentLocations` method calls the `isValid` method. 

  ```java
  // @file: framework/info/gridworld/grid/AbstractGrid.java
  // @line: 44
  if (isValid(neighborLoc))
  ```

- The `getEmptyAdjacentLocations` method and the `getOccupiedAdjacentLocations` do not call the `isValid` method directly, but they call the `getValidAdjacentLocations` method, which calls `isValid`.

  ```java
  // @file: framework/info/gridworld/grid/AbstractGrid.java
  // @line: 54
  for (Location neighborLoc : getValidAdjacentLocations(loc))
      
  // @file: framework/info/gridworld/grid/AbstractGrid.java
  // @line: 65    
  for (Location neighborLoc : getValidAdjacentLocations(loc))
  ```

- The `getNeighbors` method does not call the `isValid` method directly, but it calls `getOccupiedAdjacentLocations`, which calls  `getValidAdjacentLocations` , which calls `isValid`.

  ```java
  // @file: framework/info/gridworld/grid/Grid.java
  // @line: 31
  for (Location neighborLoc : getOccupiedAdjacentLocations(loc))
  ```

  ​

### 3

- The `get` method and the `getOccupiedAdjacentLocations` method.

  ```java
  // @file: framework/info/gridworld/grid/Grid.java
  // @line: 31~32
  for (Location neighborLoc : getOccupiedAdjacentLocations(loc))
      neighbors.add(get(neighborLoc));
  ```

- The `AbstractGrid` class implements the `getOccupiedAdjacentLocations` method.

  ```java
  // @file: framework/info/gridworld/grid/Grid.java
  // @line: 62~71
  public ArrayList<Location> getOccupiedAdjacentLocations(Location loc)
  {
      ArrayList<Location> locs = new ArrayList<Location>();
      for (Location neighborLoc : getValidAdjacentLocations(loc))
      {
          if (get(neighborLoc) != null)
              locs.add(neighborLoc);
      }
      return locs;
  }
  ```

- The `BoundedGrid` and `UnboundedGrid` classes implement the `get` method, respectively.

  ```java
  // @file: framework/info/gridworld/grid/BoundedGrid.java
  // @line: 85~91
  public E get(Location loc)
  {
      if (!isValid(loc))
          throw new IllegalArgumentException("Location " + loc
                                             + " is not valid");
      return (E) occupantArray[loc.getRow()][loc.getCol()]; // unavoidable warning
  }
  ```

  ```java
  // @file: framework/info/gridworld/grid/UnboundedGrid.java
  // @line: 66~71
  public E get(Location loc)
  {
      if (loc == null)
          throw new NullPointerException("loc == null");
      return occupantMap.get(loc);
  }
  ```

  ​

### 4

- The `get` method returns the object at a given location in this grid, these object could be various of types as well as `null`.

  ```java
  // @file: framework/info/gridworld/grid/Grid.java
  // @line: 79
  E get(Location loc);
  ```

- In the `getEmptyAdjacentLocations` method, it calls the `get` method in order to see whether the neighbor location is null to add `Location` to the list. And finally return a list of locations. And the type of object returned by `get` is unconcerned.

  ```java
  // @file: framework/info/gridworld/grid/AbstractGrid.java
  // @line: 79
  public ArrayList<Location> getEmptyAdjacentLocations(Location loc)
  {
      ArrayList<Location> locs = new ArrayList<Location>();
      for (Location neighborLoc : getValidAdjacentLocations(loc))
      {
          if (get(neighborLoc) == null)
              locs.add(neighborLoc);
      }
      return locs;
  }
  ```



### 5

If so, the `getValidAdjacentLocations` method will return a list of valid locations only in the north, east, south and west. The number of possible valid adjacent locations was originally 8, and now is 4.

```java
// @file: framework/info/gridworld/grid/AbstractGrid.java
// @line: 36~49
public ArrayList<Location> getValidAdjacentLocations(Location loc)
{
    ArrayList<Location> locs = new ArrayList<Location>();

    int d = Location.NORTH;
    for (int i = 0; i < Location.FULL_CIRCLE / Location.HALF_RIGHT; i++)
    {
        Location neighborLoc = loc.getAdjacentLocation(d);
        if (isValid(neighborLoc))
            locs.add(neighborLoc);
        d = d + Location.HALF_RIGHT;
    }
    return locs;
}
```



## Set 11

### 1

In the constructor of `BoundedGrid` class, if the number of rows <= 0 or the number of cols <= 0, it will throw an `IllegalArgumentException`.

```java
// @file: framework/info/gridworld/grid/BoundedGrid.java
// @line: 39~46
public BoundedGrid(int rows, int cols)
{
    if (rows <= 0)
        throw new IllegalArgumentException("rows <= 0");
    if (cols <= 0)
        throw new IllegalArgumentException("cols <= 0");
    occupantArray = new Object[rows][cols];
}
```



### 2

`occupantArray[0].length;`

`occupantArray[0].length` indicates the number of columns in row 0 of `occupantArray`, and the constructor of `BoundedGrid` make sure that the gird has at least 1 row and 1 column.

```java
// @file: framework/info/gridworld/grid/BoundedGrid.java
// @line: 53~58
public int getNumCols()
{
    // Note: according to the constructor precondition, numRows() > 0, so
    // theGrid[0] is non-null.
    return occupantArray[0].length;
}
```



### 3

The row of a valid location should be greater or equal to 0 and less than the number of rows of the BoundedGrid.

The col of a valid location should be greater or equal to 0 and less than the number of cols of the BoundedGrid.

```java
// @file: framework/info/gridworld/grid/BoundedGrid.java
// @line: 60~64
public boolean isValid(Location loc)
{
    return 0 <= loc.getRow() && loc.getRow() < getNumRows()
        && 0 <= loc.getCol() && loc.getCol() < getNumCols();
}
```



### 4

- The return type of the `getOccupiedLocations` method is `ArrayList<Location>`.
- The time complexity (Big-Oh) for this method is O(r * c).

```java
// @file: framework/info/gridworld/grid/BoundedGrid.java
// @line: 66~83
public ArrayList<Location> getOccupiedLocations()
{
    ArrayList<Location> theLocations = new ArrayList<Location>();

    // Look at all grid locations.
    for (int r = 0; r < getNumRows(); r++)
    {
        for (int c = 0; c < getNumCols(); c++)
        {
            // If there's an object at this location, put it in the array.
            Location loc = new Location(r, c);
            if (get(loc) != null)
                theLocations.add(loc);
        }
    }

    return theLocations;
}
```



### 5

- The return type of the `get` method is `E`.
- The time complexity (Big-Oh) for this method is O(1).

```java
// @file: framework/info/gridworld/grid/BoundedGrid.java
// @line: 85~91
public E get(Location loc)
{
    if (!isValid(loc))
        throw new IllegalArgumentException("Location " + loc
                                           + " is not valid");
    return (E) occupantArray[loc.getRow()][loc.getCol()]; // unavoidable warning
}
```



### 6

- An exception is to be thrown by the `put` method, when 

  - The location `loc` (the location expected to put) in the parameter list is invalid(out of the grid) , an `IllegalArgumentException` expection is thrown.

  or 

  - The object `obj` (the object expected to put) in the parameter list is null  , a `NullPointerException` expection is thrown.

- The time complexity (Big-Oh) for this method is O(1).

```java
// @file: framework/info/gridworld/grid/BoundedGrid.java
// @line: 93~105
public E put(Location loc, E obj)
{
    if (!isValid(loc))
        throw new IllegalArgumentException("Location " + loc
                                           + " is not valid");
    if (obj == null)
        throw new NullPointerException("obj == null");

    // Add the object to the grid.
    E oldOccupant = get(loc);
    occupantArray[loc.getRow()][loc.getCol()] = obj;
    return oldOccupant;
}
```



### 7

- The return type of the `remove` method is `E`.
- When an attempt is made to remove an item from an empty location, `null` is returned, without any error, because an empty location stores a `null`.
- The time complexity (Big-Oh) for this method is O(1).

```java
// @file: framework/info/gridworld/grid/BoundedGrid.java
// @line: 107~117
public E remove(Location loc)
{
    if (!isValid(loc))
        throw new IllegalArgumentException("Location " + loc
                                           + " is not valid");

    // Remove the object from the grid.
    E r = get(loc);
    occupantArray[loc.getRow()][loc.getCol()] = null;
    return r;
}
```



### 8

Yes, this is an efficient implementation. The most inefficient method is the `getOccupiedLocations` method with a time complexity of O(r * c). The time complexity of the other methed are O(1).

Hence, the  `getOccupiedLocations` method should be considered to have a better implementation. When most of the locations in the grid are unoccupied in the 2-demensional array `occupantArray`, it could consider to use a `HashMap`.

```java
// @file: framework/info/gridworld/grid/BoundedGrid.java
// @line: 66
public ArrayList<Location> getOccupiedLocations()
// @line: 85
public E get(Location loc)
// @line: 93
public E put(Location loc, E obj)
// @line: 107
public E remove(Location loc)
```



## Set 12

### 1

- The `Location` class must implement the `hashCode` method and the `equals` method so that an instance of `HashMap` can be used for the map.

  ```java
  // @file: framework/info/gridworld/grid/Location.java
  // @line: 218~221
  public int hashCode()
  {
      return getRow() * 3737 + getCol();
  }
  ```

  ```java
  // @file: framework/info/gridworld/grid/Location.java
  // @line: 205~212
  public boolean equals(Object other)
  {
      if (!(other instanceof Location))
          return false;
  
      Location otherLoc = (Location) other;
      return getRow() == otherLoc.getRow() && getCol() == otherLoc.getCol();
  }
  ```

- If a `TreeMap` were used instead, the `Location` class is required of being a nonabstract class and implementing the `Comparable` interface and the `compareTo` method.

- `Location` satisfies all these requirements.

  ```java
  // @file: framework/info/gridworld/grid/Location.java
  // @line: 28
  public class Location implements Comparable
  ```

  ```java
  // @file: framework/info/gridworld/grid/Location.java
  // @line: 234~246
  public int compareTo(Object other)
  {
      Location otherLoc = (Location) other;
      if (getRow() < otherLoc.getRow())
          return -1;
      if (getRow() > otherLoc.getRow())
          return 1;
      if (getCol() < otherLoc.getCol())
          return -1;
      if (getCol() > otherLoc.getCol())
          return 1;
      return 0;
  }
  ```



### 2

- In the `UnboundedGrid`, all locations checked by the `isValid` method always are true, but a location is actually valid only when it is not `null`. And a `null` is legal value for a key in a `Map`, so the `get`, `put` and `remove` methods in `UnboundedGrid` class must check the location and it should throw a `NullPointerException` when the location is `null`.

  ```java
  // @file: framework/info/gridworld/grid/UnboundedGrid.java
  // @line: 53~56
  public boolean isValid(Location loc)
  {
      return true;
  }
  
  // @line: 66~87
  public E get(Location loc)
  {
      if (loc == null)
          throw new NullPointerException("loc == null");
      return occupantMap.get(loc);
  }
  
  public E put(Location loc, E obj)
  {
      if (loc == null)
          throw new NullPointerException("loc == null");
      if (obj == null)
          throw new NullPointerException("obj == null");
      return occupantMap.put(loc, obj);
  }
  
  public E remove(Location loc)
  {
      if (loc == null)
          throw new NullPointerException("loc == null");
      return occupantMap.remove(loc);
  }
  ```

  ​

- In the corresponding methods for the `BoundedGrid`, to check whether a location is valid, they invoke the `isValid` method in the `BoundedGrid` class, which returns true if a location is in the grid and returns false if a location is out of the grid.

  ```java
  // @file: framework/info/gridworld/grid/BoundedGrid.java
  // @line: 60~64
  public boolean isValid(Location loc)
  {
      return 0 <= loc.getRow() && loc.getRow() < getNumRows()
          && 0 <= loc.getCol() && loc.getCol() < getNumCols();
  }
  ```

  ​

### 3

- The average time complexity (Big-Oh) for the three methods: `get`, `put`, and `remove` is O(1). 
- If a `TreeMap` were used instead of a `HashMap`, the average time complexity (Big-Oh) for the three methods: `get`, `put`, and `remove` is O(log n). The n is the number of the occupied locations.

```java
// @file: framework/info/gridworld/grid/UnboundedGrid.java
// @line: 33
private Map<Location, E> occupantMap;

// @line: 66~87
public E get(Location loc)
{
    if (loc == null)
        throw new NullPointerException("loc == null");
    return occupantMap.get(loc);
}

public E put(Location loc, E obj)
{
    if (loc == null)
        throw new NullPointerException("loc == null");
    if (obj == null)
        throw new NullPointerException("obj == null");
    return occupantMap.put(loc, obj);
}

public E remove(Location loc)
{
    if (loc == null)
        throw new NullPointerException("loc == null");
    return occupantMap.remove(loc);
}
```



### 4

Usually, the `getOccupiedLocations` method will return the occupied locations in a different order.

- If a `HashMap` is used, the order of the occupied locations is determined by the traversal in the hash table.
- If a `TreeMap` is used, the order of the occupied locations is determined by the inorder traversal in the binary search tree.

```java
// @file: framework/info/gridworld/grid/UnboundedGrid.java
// @line: 33
private Map<Location, E> occupantMap;

// @line: 58~64
public ArrayList<Location> getOccupiedLocations()
{
    ArrayList<Location> a = new ArrayList<Location>();
    for (Location loc : occupantMap.keySet())
        a.add(loc);
    return a;
}
```



### 5

- Yes, a map implementation can be used for a bounded grid.
- If a `HashMap` is used, the average time complexity for the `getOccupiedLocations` method will drop to O(n). n is the number of occupied locations in the grid.
- When the grid is dense, i.e., most of the locations are occupied, using a two-demensional array will cost less store space. Because an element in a `HashMap` has to be stored with `Location` and the object in it, which costs more store space.

```java
// @file: framework/info/gridworld/grid/UnboundedGrid.java
// @line: 33
private Map<Location, E> occupantMap;

// @line: 58~64
public ArrayList<Location> getOccupiedLocations()
{
    ArrayList<Location> a = new ArrayList<Location>();
    for (Location loc : occupantMap.keySet())
        a.add(loc);
    return a;
}
```

```java
// @file: framework/info/gridworld/grid/BoundedGrid.java
// @line: 31
private Object[][] occupantArray; // the array storing the grid elements
```



## Coding Exercises

### 2

- The `UnboundedGrid` class is also implemented with `HashMap`. Thus, most of the method in the `UnboundedGrid` class can be used in the `HashMapSparseBoundedGrid` class without any change.

  ```java
  // @file: framework/info/gridworld/grid/UnboundedGrid.java
  // @line: 33
  private Map<Location, E> occupantMap;
  ```

- The methods of `UnboundedGrid` could be used without change are listed below:

  - `getOccupiedLocations`
  - `get`
  - `put`
  - `remove`

  ```java
  // @file: framework/info/gridworld/grid/UnboundedGrid.java
  // @line: 58~87
  public ArrayList<Location> getOccupiedLocations()
  {
      ArrayList<Location> a = new ArrayList<Location>();
      for (Location loc : occupantMap.keySet())
          a.add(loc);
      return a;
  }
  
  public E get(Location loc)
  {
      if (loc == null)
          throw new NullPointerException("loc == null");
      return occupantMap.get(loc);
  }
  
  public E put(Location loc, E obj)
  {
      if (loc == null)
          throw new NullPointerException("loc == null");
      if (obj == null)
          throw new NullPointerException("obj == null");
      return occupantMap.put(loc, obj);
  }
  
  public E remove(Location loc)
  {
      if (loc == null)
          throw new NullPointerException("loc == null");
      return occupantMap.remove(loc);
  }
  ```

- (Fill in the following chart to compare the expected Big-Oh efficiencies for each implementation of the `SparseBoundedGrid`. Let r = number of rows, c = number of columns, and n = number of occupied locations.)

| Methods                      | SparseGridNode version | LinkedList<OccupantInCol> version | HashMap version | TreeMap version |
| ---------------------------- | ---------------------- | --------------------------------- | --------------- | --------------- |
| getNeighbors                 | O(c)                   | O(c)                              | O(1)            | O(log n)        |
| getEmptyAdjacentLocations    | O(c)                   | O(c)                              | O(1)            | O(log n)        |
| getOccupiedAdjacentLocations | O(c)                   | O(c)                              | O(1)            | O(log n)        |
| getOccupiedLocations         | O(r + n)               | O(r + n)                          | O(n)            | O(n)            |
| get                          | O(c)                   | O(c)                              | O(1)            | O(log n)        |
| put                          | O(c)                   | O(c)                              | O(1)            | O(log n)        |
| remove                       | O(c)                   | O(c)                              | O(1)            | O(log n)        |



### 3

- The Big-Oh efficiency of the `get` method is O(1).

```java
// @file: projects/UnboundedGrid2.java
// @line: 60~72
public E get(Location loc)
{
    if (!isValid(loc))
    {
        throw new IllegalArgumentException("Location " + loc
                                           + " is not valid");
    }
    if (loc.getRow() >= gridLength || loc.getCol() >= gridLength)
    {
        return null;
    }
    return (E) occupantArray[loc.getRow()][loc.getCol()]; // unavoidable warning
}
```

- The efficiency of the put method when the row and column index values are within the current array bounds is O(1).

```java
// @file: projects/UnboundedGrid2.java
// @line: 74~95
public E put(Location loc, E obj)
{
    if (!isValid(loc))
    {
        throw new IllegalArgumentException("Location " + loc
                                           + " is not valid");
    }
    if (obj == null)
    {
        throw new NullPointerException("obj == null");
    }

    E oldOccupant = get(loc);

    if (loc.getRow() >= gridLength || loc.getCol() >= gridLength)
    {
        doubleSize(loc);
    }
    // Add the object to the grid.
    occupantArray[loc.getRow()][loc.getCol()] = obj;
    return oldOccupant;
}
```

- The efficiency when the array needs to be resized is O(n * n), where n is the `gridLength` in below:

```java
// @file: projects/UnboundedGrid2.java
// @line: 97~114
private void doubleSize(Location loc) {
    int newGridLength = gridLength;
    while (loc.getRow() >= newGridLength || loc.getCol() >= newGridLength)
    {
        newGridLength *= 2;
    }

    Object[][] tempArr = new Object[newGridLength][newGridLength];
    for(int i = 0; i < gridLength; ++i)
    {
        for(int j = 0; j < gridLength; ++j)
        {
            tempArr[i][j] = occupantArray[i][j];
        }
    }
    occupantArray = tempArr;
    gridLength = newGridLength;      
}
```



