package stan.xtr.ctn.data.local.models;

import java.util.List;

import stan.xtr.ctn.core.locations.District;
import stan.xtr.ctn.core.locations.ExtractionBase;
import stan.xtr.ctn.core.map.Town;

public interface LocationsModels
{
    interface Districts
    {
        District get(long id);
        List<District> getAllFrom(Town town);
        void add(District district);
    }
    interface ExtractionBases
    {
        ExtractionBase get(long id);
        List<ExtractionBase> getAllFrom(Town town);
        void add(ExtractionBase base);
    }
}