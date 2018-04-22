/* 
 * @author Zhengtao Wu
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import info.gridworld.grid.AbstractGrid;
import info.gridworld.grid.Location;

/**
 * A <code>LinkedListSparseBoundedGrid</code> is a rectangular grid with a finite number of
 * rows and columns. <br />
 * The implementation of this class is with LinkedList
 */
public class LinkedListSparseBoundedGrid<E> extends AbstractGrid<E>
{
    private ArrayList<LinkedList> occupantArray; // the array storing the grid elements
    private int rowNum;
    private int colNum;
    
    /**
     * Constructs an empty bounded grid with the given dimensions.
     * (Precondition: <code>rows > 0</code> and <code>cols > 0</code>.)
     * @param rows number of rows in BoundedGrid
     * @param cols number of columns in BoundedGrid
     */
    public LinkedListSparseBoundedGrid(int rows, int cols)
    {
        if (rows <= 0) {
            throw new IllegalArgumentException("rows <= 0");
        }
        if (cols <= 0) {
            throw new IllegalArgumentException("cols <= 0");
        }
        rowNum = rows;
        colNum = cols;
        occupantArray = new ArrayList<LinkedList>();
        for (int i = 0; i < rows; ++i) {
        	occupantArray.add(new LinkedList<OccupantInCol>());
        }  
    }

    public int getNumRows()
    {
        return rowNum;
    }

    public int getNumCols()
    {
        // Note: according to the constructor precondition, numRows() > 0, so
        // theGrid[0] is non-null.
        return colNum;
    }

    public boolean isValid(Location loc)
    {
        return 0 <= loc.getRow() && loc.getRow() < getNumRows()
                && 0 <= loc.getCol() && loc.getCol() < getNumCols();
    }

    public ArrayList<Location> getOccupiedLocations()
    {
        ArrayList<Location> theLocations = new ArrayList<Location>();

        // Look at all grid locations.
        for (int r = 0; r < getNumRows(); r++)
        {
            LinkedList<OccupantInCol> currentRow = occupantArray.get(r);
            if (currentRow != null) {
            	for (OccupantInCol oc : currentRow) {
            		theLocations.add(new Location(r, oc.getColNum()));
            	}
            }
        }

        return theLocations;
    }

    public E get(Location loc)
    {
        if (!isValid(loc)) {
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        }
        LinkedList<OccupantInCol> row = occupantArray.get(loc.getRow());
        if (row != null) {
        	for (OccupantInCol oc : row) {
        		if (oc.getColNum() == loc.getCol()) {
        			return (E)oc.getOccupant();
        		}
        	}
        }
        return null;
    }

    public E put(Location loc, E obj)
    {
        if (!isValid(loc)) {
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        }
        if (obj == null) {
            throw new NullPointerException("obj == null");
        }

        E oldOccupant = remove(loc);
        // Add the object to the grid.
        occupantArray.get(loc.getRow()).add(new OccupantInCol(obj, loc.getCol()));
        
        return oldOccupant;
    }

    public E remove(Location loc)
    {
        if (!isValid(loc)) {
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        }
        // Remove the object from the grid.
        E r = this.get(loc);
        if (r == null) {
        	return null;
        }
        
        LinkedList<OccupantInCol> row = occupantArray.get(loc.getRow());
        
        if (row != null) {
        	Iterator<OccupantInCol> it = row.iterator();
        	while (it.hasNext()) {
        		if (it.next().getColNum() == loc.getCol()) {
        			it.remove();
        			break;
        		}
        	}
        }
        return r;
    }
}

