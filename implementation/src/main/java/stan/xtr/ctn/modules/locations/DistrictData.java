package stan.xtr.ctn.modules.locations;

import stan.xtr.ctn.core.locations.District;
import stan.xtr.ctn.core.positions.Coordinates;
import stan.xtr.ctn.core.positions.Size;

public class DistrictData
    implements District
{
    static public District create(long id, long parentId, String name, Size size, Coordinates coordinates)
    {
        return new DistrictData(id, parentId, name, size, coordinates);
    }

    private final long id;
    private final long parentId;
    private final String name;
    private final Size size;
    private final Coordinates coordinates;

    private DistrictData(long id, long parentId, String name, Size size, Coordinates coordinates)
    {
        this.id = id;
        this.parentId = parentId;
        if(name == null)
        {
            throw new IllegalArgumentException("Property name must be not null!");
        }
        this.name = name;
        if(size == null)
        {
            throw new IllegalArgumentException("Property size must be not null!");
        }
        this.size = size;
        if(coordinates == null)
        {
            throw new IllegalArgumentException("Property coordinates must be not null!");
        }
        this.coordinates = coordinates;
    }

    public long id()
    {
        return id;
    }
    public long parentId()
    {
        return parentId;
    }
    public String name()
    {
        return name;
    }
    public Size size()
    {
        return size;
    }
    public Coordinates coordinates()
    {
        return coordinates;
    }

    public String toString()
    {
        return "{" + id + "," + parentId + ",\"" + name + "\","+size+","+coordinates+"}";
    }
    public boolean equals(Object o)
    {
        return o != null && o instanceof District && ((District)o).id() == id;
    }
    public int hashCode()
    {
        return Long.valueOf(id).intValue();
    }
}