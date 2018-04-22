
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

* The return type of the `getOccupiedLocations` method is `ArrayList<Location>`.
* The time complexity (Big-Oh) for this method is O(r * c).

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

* An exception is to be thrown by the `put` method, when 

  * The location `loc` (the location expected to put) in the parameter list is invalid(out of the grid) , an `IllegalArgumentException` expection is thrown.

  or 

  * The object `obj` (the object expected to put) in the parameter list is null  , a `NullPointerException` expection is thrown.

* The time complexity (Big-Oh) for this method is O(1).

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

* The `Location` class must implement the `hashCode` method and the `equals` method so that an instance of `HashMap` can be used for the map.

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

* If a `TreeMap` were used instead, the `Location` class is required of being a nonabstract class and implementing the `Comparable` interface and the `compareTo` method.

* `Location` satisfies all these requirements.

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

* In the `UnboundedGrid`, all locations checked by the `isValid` method always are true, but a location is actually valid only when it is not `null`. And a `null` is legal value for a key in a `Map`, so the `get`, `put` and `remove` methods in `UnboundedGrid` class must check the location and it should throw a `NullPointerException` when the location is `null`.

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

* In the corresponding methods for the `BoundedGrid`, to check whether a location is valid, they invoke the `isValid` method in the `BoundedGrid` class, which returns true if a location is in the grid and returns false if a location is out of the grid.

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

* The average time complexity (Big-Oh) for the three methods: `get`, `put`, and `remove` is O(1). 
* If a `TreeMap` were used instead of a `HashMap`, the average time complexity (Big-Oh) for the three methods: `get`, `put`, and `remove` is O(log n). The n is the number of the occupied locations.

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

* If a `HashMap` is used, the order of the occupied locations is determined by the traversal in the hash table.
* If a `TreeMap` is used, the order of the occupied locations is determined by the inorder traversal in the binary search tree.

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

* Yes, a map implementation can be used for a bounded grid.
* If a `HashMap` is used, the average time complexity for the `getOccupiedLocations` method will drop to O(n). n is the number of occupied locations in the grid.
* When the grid is dense, i.e., most of the locations are occupied, using a two-demensional array will cost less store space. Because an element in a `HashMap` has to be stored with `Location` and the object in it, which costs more store space.

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

* The `UnboundedGrid` class is also implemented with `HashMap`. Thus, most of the method in the `UnboundedGrid` class can be used in the `HashMapSparseBoundedGrid` class without any change.

* The methods of `UnboundedGrid` could be used without change are listed below:

  * `getOccupiedLocations`
  * `get`
  * `put`
  * `remove`

* (Fill in the following chart to compare the expected Big-Oh efficiencies for each implementation of the `SparseBoundedGrid`. Let r = number of rows, c = number of columns, and n = number of occupied locations.)


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

