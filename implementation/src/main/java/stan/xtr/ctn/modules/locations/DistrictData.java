package stan.xtr.ctn.modules.locations;

import stan.xtr.ctn.core.locations.District;

public class DistrictData
    implements District
{
    static public District create(long id, long parentId, String name)
    {
        return new DistrictData(id, parentId, name);
    }

    private final long id;
    private final long parentId;
    private final String name;

    private DistrictData(long id, long parentId, String name)
    {
        this.id = id;
        this.parentId = parentId;
        if(name == null)
        {
            throw new RuntimeException("Property name must be not null!");
        }
        this.name = name;
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

    public String toString()
    {
        return "{" + id + "," + parentId + ",\"" + name + "\"}";
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