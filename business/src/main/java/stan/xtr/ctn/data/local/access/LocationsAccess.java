package stan.xtr.ctn.data.local.access;

import stan.xtr.ctn.data.local.models.LocationsModels;

public interface LocationsAccess
{
    LocationsModels.ExtractionBases extractionBases();
    LocationsModels.Districts districts();
    LocationsModels.Sources sources();
    LocationsModels.Colonies colonies();
}