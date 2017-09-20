package stan.xtr.ctn.data.local;

import stan.xtr.ctn.data.local.access.LocationsAccess;
import stan.xtr.ctn.data.local.access.MapAccess;

public interface DAO
{
    MapAccess map();
    LocationsAccess locations();
}