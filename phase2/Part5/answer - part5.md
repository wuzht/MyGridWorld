
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

* The `getValidAdjacentLocations` method calls the `isValid` method. 

  ```java
  // @file: framework/info/gridworld/grid/AbstractGrid.java
  // @line: 44
  if (isValid(neighborLoc))
  ```

* The `getEmptyAdjacentLocations` method and the `getOccupiedAdjacentLocations` do not call the `isValid` method directly, but they call the `getValidAdjacentLocations` method, which calls `isValid`.

  ```java
  // @file: framework/info/gridworld/grid/AbstractGrid.java
  // @line: 54
  for (Location neighborLoc : getValidAdjacentLocations(loc))
      
  // @file: framework/info/gridworld/grid/AbstractGrid.java
  // @line: 65    
  for (Location neighborLoc : getValidAdjacentLocations(loc))
  ```

* The `getNeighbors` method does not call the `isValid` method directly, but it calls `getOccupiedAdjacentLocations`, which calls  `getValidAdjacentLocations` , which calls `isValid`.

  ```java
  // @file: framework/info/gridworld/grid/Grid.java
  // @line: 31
  for (Location neighborLoc : getOccupiedAdjacentLocations(loc))
  ```

  ​

### 3

* The `get` method and the `getOccupiedAdjacentLocations` method.

  ```java
  // @file: framework/info/gridworld/grid/Grid.java
  // @line: 31~32
  for (Location neighborLoc : getOccupiedAdjacentLocations(loc))
      neighbors.add(get(neighborLoc));
  ```

* The `AbstractGrid` class implements the `getOccupiedAdjacentLocations` method.

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

* The `BoundedGrid` and `UnboundedGrid` classes implement the `get` method, respectively.

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

### 5

### 6

### 7

### 8

