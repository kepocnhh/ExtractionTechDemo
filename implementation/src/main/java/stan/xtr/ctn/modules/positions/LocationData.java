package stan.xtr.ctn.modules.positions;

import stan.xtr.ctn.core.locations.Locations;
import stan.xtr.ctn.core.positions.Location;
import stan.xtr.ctn.core.units.Units;
import stan.xtr.ctn.data.Pair;
import stan.xtr.ctn.modules.data.PairData;

public class LocationData
    implements Location
{
    static public Location create(Units.Type unitType, long unitId, Locations.Type locationType, long locationId)
    {
        return new LocationData(unitType, unitId, locationType, locationId);
    }

    private final Pair<Units.Type, Long> unit;
    private final Pair<Locations.Type, Long> location;

    private LocationData(Units.Type unitType, long unitId, Locations.Type locationType, long locationId)
    {
        unit = PairData.create(unitType, unitId);
        location = PairData.create(locationType, locationId);
    }

    public Pair<Units.Type, Long> unit()
    {
        return unit;
    }
    public Pair<Locations.Type, Long> location()
    {
        return location;
    }
    public String toString()
    {
        return "{" + unit().first().name() + "," + unit().second() + "," + location().first().name() + "," + location().second() + "}";
    }
    public boolean equals(Object o)
    {
        return o != null && o instanceof Location && equals((Location)o);
    }
    private boolean equals(Location location)
    {
        return location.unit().first() == unit.first() && location.unit().second().equals(unit.second());
    }
    public int hashCode()
    {
        return unit.second().intValue();
    }
}