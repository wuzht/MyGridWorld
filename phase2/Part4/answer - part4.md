
# Part 4

## Set 7

### 1

* `act`

  ```java
  // @file: framework/info/gridworld/actor/Critter.java
  // @line: 38
  public void act()
  ```

* `getActors`

  ```java
  // @file: framework/info/gridworld/actor/Critter.java
  // @line: 56
  public ArrayList<Actor> getActors()
  ```

* `processActors`

  ```java
  // @file: framework/info/gridworld/actor/Critter.java
  // @line: 71
  public void processActors(ArrayList<Actor> actors)
  ```

* `getMoveLocations`

  ```java
  // @file: framework/info/gridworld/actor/Critter.java
  // @line: 88
  public ArrayList<Location> getMoveLocations()
  ```

* `selectMoveLocation`

  ```java
  // @file: framework/info/gridworld/actor/Critter.java
  // @line: 104
  public Location selectMoveLocation(ArrayList<Location> locs)
  ```

* `selectMoveLocation`

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

* `getMoveLocations`

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

* `selectMoveLocation`

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

* `makeMove`

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



###6

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

* Similarities: 

  * They both randomly choose a location from the list of possible move locations returned by the method `getMoveLocations`.

    ```java
    // @file: framework/info/gridworld/actor/Critter.java
    // @line: 44~46
    ArrayList<Location> moveLocs = getMoveLocations();
    Location loc = selectMoveLocation(moveLocs);
    makeMove(loc);
    ```

  * When they move, they do not turn.

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

* Differences:

  * A CrabCritter can only moves to its left or its right, while a Critter can move to any adjacent locations.

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

  * When a CrabCritter doesn't move, it will turn left or right randomly, while a Critter does not turn.

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





