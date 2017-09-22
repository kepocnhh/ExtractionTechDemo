package stan.xtr.ctn.data.local.models;

import stan.xtr.ctn.core.units.Human;
import stan.xtr.ctn.core.units.Transport;

public interface UnitsModels
{
    interface Humans
    {
        Human get(long id);
        void add(Human human);
    }
    interface Transports
    {
        Transport get(long id);
        void add(Transport transport);
    }
}