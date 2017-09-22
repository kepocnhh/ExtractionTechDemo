package stan.xtr.ctn.cache;

import stan.xtr.ctn.cache.access.Locations;
import stan.xtr.ctn.cache.access.Map;
import stan.xtr.ctn.cache.access.Positions;
import stan.xtr.ctn.cache.access.Structures;
import stan.xtr.ctn.cache.access.Units;
import stan.xtr.ctn.data.local.DAO;
import stan.xtr.ctn.data.local.access.LocationsAccess;
import stan.xtr.ctn.data.local.access.MapAccess;
import stan.xtr.ctn.data.local.access.PositionsAccess;
import stan.xtr.ctn.data.local.access.StructuresAccess;
import stan.xtr.ctn.data.local.access.UnitsAccess;

public class RuntimeCache
    implements DAO
{
    private final MapAccess map = new Map();
    private final LocationsAccess locations = new Locations();
    private final StructuresAccess structures = new Structures();
    private final UnitsAccess units = new Units();
    private final PositionsAccess positions = new Positions();

    public MapAccess map()
    {
        return map;
    }
    public LocationsAccess locations()
    {
        return locations;
    }
    public StructuresAccess structures()
    {
        return structures;
    }
    public UnitsAccess units()
    {
        return units;
    }
    public PositionsAccess positions()
    {
        return positions;
    }
}