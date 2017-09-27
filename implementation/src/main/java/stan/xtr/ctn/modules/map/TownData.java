package stan.xtr.ctn.modules.map;

import stan.xtr.ctn.core.map.Town;
import stan.xtr.ctn.core.positions.Size;

public class TownData
    implements Town
{
    static public Town create(long id, long parentId, String name, Size size)
    {
        return new TownData(id, parentId, name, size);
    }

    private final long id;
    private final long parentId;
    private final String name;
    private final Size size;

    private TownData(long id, long parentId, String name, Size size)
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

    public String toString()
    {
        return "{" + id + "," + parentId + ",\"" + name + "\","+size+"}";
    }
    public boolean equals(Object o)
    {
        return o != null && o instanceof Town && ((Town)o).id() == id;
    }
    public int hashCode()
    {
        return Long.valueOf(id).intValue();
    }
}