package stan.xtr.ctn.cache.access;

import java.util.ArrayList;
import java.util.List;

import stan.xtr.ctn.core.map.Town;
import stan.xtr.ctn.data.local.access.MapAccess;
import stan.xtr.ctn.data.local.models.MapModels;

public class Map
    implements MapAccess
{
    private final List<Town> townsList = new ArrayList<>();
    private final MapModels.Towns towns = new MapModels.Towns()
    {
        public Town get(long id)
        {
            for(Town town: townsList)
            {
                if(town.id() == id)
                {
                    return town;
                }
            }
            return null;
        }
        public void add(Town town)
        {
            townsList.add(town);
        }
    };
    public MapModels.Towns towns()
    {
        return towns;
    }
}