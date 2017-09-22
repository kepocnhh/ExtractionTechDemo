package stan.xtr.ctn.data.local.access;

import stan.xtr.ctn.data.local.models.UnitsModels;

public interface UnitsAccess
{
    UnitsModels.Humans humans();
    UnitsModels.Transports transports();
}