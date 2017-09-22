package stan.xtr.ctn.data.local.models;

import java.util.List;

import stan.xtr.ctn.core.locations.ExtractionBase;
import stan.xtr.ctn.core.structures.Hangar;
import stan.xtr.ctn.core.structures.Hut;

public interface StructuresModels
{
    interface Hangars
    {
        Hangar get(long id);
        List<Hangar> getAllFrom(ExtractionBase base);
        void add(Hangar hangar);
    }
    interface Huts
    {
        Hut get(long id);
        List<Hut> getAllFrom(ExtractionBase base);
        void add(Hut hut);
    }
}