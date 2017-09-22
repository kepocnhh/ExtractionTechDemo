package stan.xtr.ctn.cache.access;

import java.util.ArrayList;
import java.util.List;

import stan.xtr.ctn.core.locations.Locations;
import stan.xtr.ctn.core.positions.Location;
import stan.xtr.ctn.core.units.Units;
import stan.xtr.ctn.data.Pair;
import stan.xtr.ctn.data.local.access.PositionsAccess;
import stan.xtr.ctn.data.local.models.PositionsModels;

public class Positions
    implements PositionsAccess
{
    private final List<Location> locationsList = new ArrayList<>();
    private final PositionsModels.Locations locations = new PositionsModels.Locations()
    {
        public Pair<Locations.Type, Long> get(Units.Type unitType, long unitId)
        {
            for(Location location: locationsList)
            {
                if(location.unit().first() == unitType && location.unit().second() == unitId)
                {
                    return location.location();
                }
            }
            return null;
        }
        public List<Pair<Units.Type, Long>> get(Locations.Type locationType, long locationId)
        {
            List<Pair<Units.Type, Long>> result = new ArrayList<>();
            for(Location location: locationsList)
            {
                if(location.location().first() == locationType && location.location().second() == locationId)
                {
                    result.add(location.unit());
                }
            }
            return result;
        }
        public void update(Location location)
        {
            for(int i=0; i<locationsList.size(); i++)
            {
                if(locationsList.get(i).equals(location))
                {
                    locationsList.set(i, location);
                    return;
                }
            }
            locationsList.add(location);
        }
    };

    public PositionsModels.Locations locations()
    {
        return locations;
    }
}