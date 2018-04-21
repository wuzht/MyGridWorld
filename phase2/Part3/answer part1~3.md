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

