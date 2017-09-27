package stan.xtr.ctn.modules.structures;

import stan.xtr.ctn.core.structures.Hangar;

public class HangarData
    implements Hangar
{
    static public Hangar create(long id, long parentId, int capacity)
    {
        return new HangarData(id, parentId, capacity);
    }

    private final long id;
    private final long parentId;
    private final int capacity;

    private HangarData(long id, long parentId, int capacity)
    {
        this.id = id;
        this.parentId = parentId;
        if(capacity < 1)
        {
            throw new IllegalArgumentException("Property capacity must be positive!");
        }
        this.capacity = capacity;
    }

    public long id()
    {
        return id;
    }
    public long parentId()
    {
        return parentId;
    }
    public int capacity()
    {
        return capacity;
    }

    public String toString()
    {
        return "{" + id + "," + parentId + "," + capacity + "}";
    }
    public boolean equals(Object o)
    {
        return o != null && o instanceof Hangar && ((Hangar)o).id() == id;
    }
    public int hashCode()
    {
        return Long.valueOf(id).intValue();
    }
}