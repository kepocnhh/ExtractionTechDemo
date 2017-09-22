package stan.xtr.ctn.modules.units;

import stan.xtr.ctn.core.units.Transport;

public class TransportData
    implements Transport
{
    static public Transport create(long id, Type type, int capacity)
    {
        return new TransportData(id, type, capacity);
    }

    private final long id;
    private final Type type;
    private final int capacity;

    private TransportData(long id, Type type, int capacity)
    {
        this.id = id;
        if(type == null)
        {
            throw new RuntimeException("Property type must be not null!");
        }
        this.type = type;
        this.capacity = capacity;
    }

    public long id()
    {
        return id;
    }
    public Type type()
    {
        return type;
    }
    public int capacity()
    {
        return capacity;
    }

    public String toString()
    {
        return "{" + id + "," + type.name() + "," + capacity + "}";
    }
    public boolean equals(Object o)
    {
        return o != null && o instanceof Transport && ((Transport)o).id() == id;
    }
    public int hashCode()
    {
        return Long.valueOf(id).intValue();
    }
}