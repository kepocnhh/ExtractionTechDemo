package stan.xtr.ctn.cache.access;

import java.util.ArrayList;
import java.util.List;

import stan.xtr.ctn.core.locations.Colony;
import stan.xtr.ctn.core.locations.District;
import stan.xtr.ctn.core.locations.ExtractionBase;
import stan.xtr.ctn.core.locations.Source;
import stan.xtr.ctn.core.map.Town;
import stan.xtr.ctn.data.local.access.LocationsAccess;
import stan.xtr.ctn.data.local.models.LocationsModels;

public class Locations
    implements LocationsAccess
{
    private final List<ExtractionBase> extractionBasesList = new ArrayList<>();
    private final List<District> districtsList = new ArrayList<>();
    private final List<Source> sourcesList = new ArrayList<>();
    private final List<Colony> coloniesList = new ArrayList<>();
    private final LocationsModels.ExtractionBases extractionBases = new LocationsModels.ExtractionBases()
    {
        public ExtractionBase get(long id)
        {
            for(ExtractionBase base: extractionBasesList)
            {
                if(base.id() == id)
                {
                    return base;
                }
            }
            return null;
        }
        public List<ExtractionBase> getAllFrom(Town town)
        {
            List<ExtractionBase> result = new ArrayList<>();
            for(ExtractionBase base: extractionBasesList)
            {
                if(base.parentId() == town.id())
                {
                    result.add(base);
                }
            }
            return result;
        }
        public void add(ExtractionBase base)
        {
            extractionBasesList.add(base);
        }
    };
    private final LocationsModels.Districts districts = new LocationsModels.Districts()
    {
        public District get(long id)
        {
            for(District district: districtsList)
            {
                if(district.id() == id)
                {
                    return district;
                }
            }
            return null;
        }
        public List<District> getAllFrom(Town town)
        {
            List<District> result = new ArrayList<>();
            for(District district: districtsList)
            {
                if(district.parentId() == town.id())
                {
                    result.add(district);
                }
            }
            return result;
        }
        public void add(District district)
        {
            districtsList.add(district);
        }
    };
    private final LocationsModels.Sources sources = new LocationsModels.Sources()
    {
        public Source get(long id)
        {
            for(Source source: sourcesList)
            {
                if(source.id() == id)
                {
                    return source;
                }
            }
            return null;
        }
        public List<Source> getAllFrom(District district)
        {
            List<Source> result = new ArrayList<>();
            for(Source source: sourcesList)
            {
                if(source.parentId() == district.id())
                {
                    result.add(source);
                }
            }
            return result;
        }
        public void add(Source source)
        {
            sourcesList.add(source);
        }
    };
    private final LocationsModels.Colonies colonies = new LocationsModels.Colonies()
    {
        public Colony get(long id)
        {
            for(Colony colony: coloniesList)
            {
                if(colony.id() == id)
                {
                    return colony;
                }
            }
            return null;
        }
        public List<Colony> getAllFrom(District district)
        {
            List<Colony> result = new ArrayList<>();
            for(Colony colony: coloniesList)
            {
                if(colony.parentId() == district.id())
                {
                    result.add(colony);
                }
            }
            return result;
        }
        public void add(Colony colony)
        {
            coloniesList.add(colony);
        }
    };

    public LocationsModels.ExtractionBases extractionBases()
    {
        return extractionBases;
    }
    public LocationsModels.Districts districts()
    {
        return districts;
    }
    public LocationsModels.Sources sources()
    {
        return sources;
    }
    public LocationsModels.Colonies colonies()
    {
        return colonies;
    }
}