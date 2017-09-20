package stan.xtr.ctn.modules.general;

import stan.xtr.ctn.App;
import stan.xtr.ctn.R;
import stan.xtr.ctn.core.map.Town;
import stan.xtr.ctn.modules.locations.DistrictData;
import stan.xtr.ctn.modules.locations.ExtractionBaseData;
import stan.xtr.ctn.modules.map.TownData;
import stan.xtr.ctn.units.activities.UtilActivity;

public class GeneralActivity
    extends UtilActivity
{
    protected int getContentView()
    {
        return R.layout.general_screen;
    }
    protected void initViews()
    {
    }
    protected void init()
    {
        App.component().data().local().map().towns().add(TownData.create(1, 1, "Chicago"));
        App.component().data().local().locations().extractionBases().add(ExtractionBaseData.create(1, 1, "Outer Haven"));
        App.component().data().local().locations().districts().add(DistrictData.create(1, 1, "District11"));
        Town town = App.component().data().local().map().towns().get(1);
        log("town: " + town);
        log("extraction bases: " + App.component().data().local().locations().extractionBases().getAllFrom(town));
        log("districts: " + App.component().data().local().locations().districts().getAllFrom(town));
    }
}