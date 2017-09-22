package stan.xtr.ctn.cache.access;

import java.util.ArrayList;
import java.util.List;

import stan.xtr.ctn.core.locations.ExtractionBase;
import stan.xtr.ctn.core.structures.Hangar;
import stan.xtr.ctn.core.structures.Hut;
import stan.xtr.ctn.data.local.access.StructuresAccess;
import stan.xtr.ctn.data.local.models.StructuresModels;

public class Structures
    implements StructuresAccess
{
    private final List<Hangar> hangarsList = new ArrayList<>();
    private final List<Hut> hutsList = new ArrayList<>();

    private final StructuresModels.Hangars hangars = new StructuresModels.Hangars()
    {
        public Hangar get(long id)
        {
            for(Hangar hangar: hangarsList)
            {
                if(hangar.id() == id)
                {
                    return hangar;
                }
            }
            return null;
        }
        public List<Hangar> getAllFrom(ExtractionBase base)
        {
            List<Hangar> result = new ArrayList<>();
            for(Hangar hangar: hangarsList)
            {
                if(hangar.parentId() == base.id())
                {
                    result.add(hangar);
                }
            }
            return result;
        }
        public void add(Hangar hangar)
        {
            hangarsList.add(hangar);
        }
    };
    private final StructuresModels.Huts huts = new StructuresModels.Huts()
    {
        public Hut get(long id)
        {
            for(Hut hut: hutsList)
            {
                if(hut.id() == id)
                {
                    return hut;
                }
            }
            return null;
        }
        public List<Hut> getAllFrom(ExtractionBase base)
        {
            List<Hut> result = new ArrayList<>();
            for(Hut hut: hutsList)
            {
                if(hut.parentId() == base.id())
                {
                    result.add(hut);
                }
            }
            return result;
        }
        public void add(Hut hut)
        {
            hutsList.add(hut);
        }
    };

    public StructuresModels.Hangars hangars()
    {
        return hangars;
    }
    public StructuresModels.Huts huts()
    {
        return huts;
    }
}