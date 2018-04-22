/* 
 * @author Zhengtao Wu
 */

import info.gridworld.grid.AbstractGrid;
import info.gridworld.grid.Location;
import java.util.*;

/**
 * A <code>UnboundedGrid2</code> is a rectangular grid with a infinite number of
 * rows and columns. <br />
 * The implementation of this class is testable on the AP CS AB exam.
 */
public class UnboundedGrid2<E> extends AbstractGrid<E>
{
    private Object[][] occupantArray;
    private int gridLength;

    // Constructor
    public UnboundedGrid2()
    {
    	gridLength = 16;
        occupantArray = new Object[gridLength][gridLength];
    }

    public int getNumRows()
    {
        return -1;
    }

    public int getNumCols()
    {
        return -1;
    }

    public boolean isValid(Location loc)
    {
    	return loc.getRow() >= 0 && loc.getCol() >= 0;
    }

    public ArrayList<Location> getOccupiedLocations()
    {
        ArrayList<Location> theLocations = new ArrayList<Location>();

        // Look at all grid locations.
        for (int r = 0; r < gridLength; r++)
        {
            for (int c = 0; c < gridLength; c++)
            {
                // If there's an object at this location, put it in the array.
                Location loc = new Location(r, c);
                if (get(loc) != null)
                    theLocations.add(loc);
            }
        }

        return theLocations;
    }

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

    public E remove(Location loc)
    {
        if (!isValid(loc))
        {
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        }
        
        // Remove the object from the grid.
        E r = get(loc);
        if (loc.getRow() < gridLength && loc.getCol() < gridLength)
        {
        	occupantArray[loc.getRow()][loc.getCol()] = null;
        }
        return r;
    }
}