package stan.xtr.ctn.data.local.models;

import stan.xtr.ctn.core.map.Town;

public interface MapModels
{
    interface Towns
    {
        Town get(long id);
        void add(Town town);
    }
}