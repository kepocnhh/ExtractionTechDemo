package stan.xtr.ctn.data.local.models;

import java.util.List;

import stan.xtr.ctn.core.positions.Location;
import stan.xtr.ctn.core.units.Units;
import stan.xtr.ctn.data.Pair;

public interface PositionsModels
{
    interface Locations
    {
        Pair<stan.xtr.ctn.core.locations.Locations.Type, Long> get(Units.Type unitType, long unitId);
        List<Pair<Units.Type, Long>> get(stan.xtr.ctn.core.locations.Locations.Type locationType, long locationId);
        void update(Location location);
    }
}