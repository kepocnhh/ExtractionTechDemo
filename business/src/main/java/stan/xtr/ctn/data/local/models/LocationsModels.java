package stan.xtr.ctn.data.local.models;

import java.util.List;

import stan.xtr.ctn.core.locations.Colony;
import stan.xtr.ctn.core.locations.District;
import stan.xtr.ctn.core.locations.ExtractionBase;
import stan.xtr.ctn.core.locations.Source;
import stan.xtr.ctn.core.map.Town;

public interface LocationsModels
{
    interface ExtractionBases
    {
        ExtractionBase get(long id);
        List<ExtractionBase> getAllFrom(Town town);
        void add(ExtractionBase base);
    }
    interface Districts
    {
        District get(long id);
        List<District> getAllFrom(Town town);
        void add(District district);
    }
    interface Sources
    {
        Source get(long id);
        List<Source> getAllFrom(District district);
        void add(Source source);
    }
    interface Colonies
    {
        Colony get(long id);
        List<Colony> getAllFrom(District district);
        void add(Colony colony);
    }
}