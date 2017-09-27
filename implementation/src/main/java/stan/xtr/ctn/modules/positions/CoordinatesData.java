package stan.xtr.ctn.modules.positions;

import stan.xtr.ctn.core.positions.Coordinates;

public class CoordinatesData
    implements Coordinates
{
    static public Coordinates create(int x, int y)
    {
        return new CoordinatesData(x, y);
    }

    private final int x;
    private final int y;

    private CoordinatesData(int x, int y)
    {
        if(x < 1)
        {
            throw new IllegalArgumentException("Property x must be positive!");
        }
        this.x = x;
        if(y < 1)
        {
            throw new IllegalArgumentException("Property y must be positive!");
        }
        this.y = y;
    }

    public int x()
    {
        return x;
    }
    public int y()
    {
        return y;
    }

    public String toString()
    {
        return "{" + x + ","+y+"}";
    }
    public boolean equals(Object o)
    {
        return o != null && o instanceof Coordinates && equals((Coordinates)o);
    }
    private boolean equals(Coordinates coordinates)
    {
        return coordinates.x() == x && coordinates.y() == y;
    }
    public int hashCode()
    {
        return x + y * 13;
    }
}