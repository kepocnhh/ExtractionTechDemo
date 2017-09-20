package stan.xtr.ctn.cache;

import stan.xtr.ctn.cache.access.Locations;
import stan.xtr.ctn.cache.access.Map;
import stan.xtr.ctn.data.local.DAO;
import stan.xtr.ctn.data.local.access.LocationsAccess;
import stan.xtr.ctn.data.local.access.MapAccess;

public class RuntimeCache
    implements DAO
{
    private final MapAccess map = new Map();
    private final LocationsAccess locations = new Locations();

    public MapAccess map()
    {
        return map;
    }
    public LocationsAccess locations()
    {
        return locations;
    }
}