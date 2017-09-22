package stan.xtr.ctn.cache.access;

import java.util.ArrayList;
import java.util.List;

import stan.xtr.ctn.core.units.Human;
import stan.xtr.ctn.core.units.Transport;
import stan.xtr.ctn.data.local.access.UnitsAccess;
import stan.xtr.ctn.data.local.models.UnitsModels;

public class Units
    implements UnitsAccess
{
    private final List<Human> humansList = new ArrayList<>();
    private final List<Transport> transportsList = new ArrayList<>();
    private final UnitsModels.Humans humans = new UnitsModels.Humans()
    {
        public Human get(long id)
        {
            for(Human human: humansList)
            {
                if(human.id() == id)
                {
                    return human;
                }
            }
            return null;
        }
        public void add(Human human)
        {
            humansList.add(human);
        }
    };
    private final UnitsModels.Transports transports = new UnitsModels.Transports()
    {
        public Transport get(long id)
        {
            for(Transport transport: transportsList)
            {
                if(transport.id() == id)
                {
                    return transport;
                }
            }
            return null;
        }
        public void add(Transport transport)
        {
            transportsList.add(transport);
        }
    };

    public UnitsModels.Humans humans()
    {
        return humans;
    }
    public UnitsModels.Transports transports()
    {
        return transports;
    }
}