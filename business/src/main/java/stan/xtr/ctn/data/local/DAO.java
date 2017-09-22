package stan.xtr.ctn.data.local;

import stan.xtr.ctn.data.local.access.LocationsAccess;
import stan.xtr.ctn.data.local.access.MapAccess;
import stan.xtr.ctn.data.local.access.PositionsAccess;
import stan.xtr.ctn.data.local.access.StructuresAccess;
import stan.xtr.ctn.data.local.access.UnitsAccess;

public interface DAO
{
    MapAccess map();
    LocationsAccess locations();
    StructuresAccess structures();
    UnitsAccess units();
    PositionsAccess positions();
}