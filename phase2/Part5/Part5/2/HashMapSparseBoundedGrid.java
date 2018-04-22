/* 
 * @author Zhengtao Wu
 */

import java.util.ArrayList;

import info.gridworld.grid.AbstractGrid;
import info.gridworld.grid.Location;

import java.util.*;

/**
 * An <code>HashMapSparseBoundedGrid</code> is a rectangular grid with an bounded number of rows and
 * columns. <br />
 * The implementation of this class is testable on the AP CS AB exam.
 */
public class HashMapSparseBoundedGrid<E> extends AbstractGrid<E>
{
    private Map<Location, E> occupantMap;
    private int rowNum;
    private int colNum;

    /**
     * Constructs an HashMapSparseBoundedGrid.
     */
    public HashMapSparseBoundedGrid(int rows, int cols)
    {
        if (rows <= 0) {
            throw new IllegalArgumentException("rows <= 0");
        }
        if (cols <= 0) {
            throw new IllegalArgumentException("cols <= 0");
        }
        rowNum = rows;
        colNum = cols;
        occupantMap = new HashMap<Location, E>();
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
        ArrayList<Location> a = new ArrayList<Location>();
        for (Location loc : occupantMap.keySet()) {
            a.add(loc);
        }
        return a;
    }

    public E get(Location loc)
    {
        if (loc == null) {
            throw new NullPointerException("loc == null");
        }
        return occupantMap.get(loc);
    }

    public E put(Location loc, E obj)
    {
        if (loc == null) {
            throw new NullPointerException("loc == null");
        }
        if (obj == null) {
            throw new NullPointerException("obj == null");
        }
        return occupantMap.put(loc, obj);
    }

    public E remove(Location loc)
    {
        if (loc == null) {
            throw new NullPointerException("loc == null");
        }
        return occupantMap.remove(loc);
    }
}
