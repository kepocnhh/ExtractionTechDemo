package stan.xtr.ctn.modules.units;

import stan.xtr.ctn.core.units.Human;

public class HumanData
    implements Human
{
    static public Human create(long id, String name)
    {
        return new HumanData(id, name);
    }

    private final long id;
    private final String name;

    private HumanData(long id, String name)
    {
        this.id = id;
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
    public String name()
    {
        return name;
    }

    public String toString()
    {
        return "{" + id + ",\"" + name + "\"}";
    }
    public boolean equals(Object o)
    {
        return o != null && o instanceof Human && ((Human)o).id() == id;
    }
    public int hashCode()
    {
        return Long.valueOf(id).intValue();
    }
}