package stan.xtr.ctn.modules.locations;

import stan.xtr.ctn.core.locations.Colony;

public class ColonyData
    implements Colony
{
    static public Colony create(long id, long parentId, String name)
    {
        return new ColonyData(id, parentId, name);
    }

    private final long id;
    private final long parentId;
    private final String name;

    private ColonyData(long id, long parentId, String name)
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
        return o != null && o instanceof Colony && ((Colony)o).id() == id;
    }
    public int hashCode()
    {
        return Long.valueOf(id).intValue();
    }
}