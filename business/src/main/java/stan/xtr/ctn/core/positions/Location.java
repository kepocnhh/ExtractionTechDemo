package stan.xtr.ctn.core.positions;

import stan.xtr.ctn.core.locations.Locations;
import stan.xtr.ctn.core.units.Units;
import stan.xtr.ctn.data.Pair;

public interface Location
{
    Pair<Units.Type, Long> unit();
    Pair<Locations.Type, Long> location();
}