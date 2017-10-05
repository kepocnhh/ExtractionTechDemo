package stan.xtr.ctn.modules.general;

import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import stan.xtr.ctn.App;
import stan.xtr.ctn.R;
import stan.xtr.ctn.core.locations.Colony;
import stan.xtr.ctn.core.locations.District;
import stan.xtr.ctn.core.locations.ExtractionBase;
import stan.xtr.ctn.core.locations.Locations;
import stan.xtr.ctn.core.locations.Source;
import stan.xtr.ctn.core.map.Town;
import stan.xtr.ctn.core.structures.Hangar;
import stan.xtr.ctn.core.structures.Hut;
import stan.xtr.ctn.core.units.Human;
import stan.xtr.ctn.core.units.Transport;
import stan.xtr.ctn.core.units.Units;
import stan.xtr.ctn.data.Pair;
import stan.xtr.ctn.modules.locations.ColonyData;
import stan.xtr.ctn.modules.locations.DistrictData;
import stan.xtr.ctn.modules.locations.ExtractionBaseData;
import stan.xtr.ctn.modules.locations.SourceData;
import stan.xtr.ctn.modules.map.TownData;
import stan.xtr.ctn.modules.positions.CoordinatesData;
import stan.xtr.ctn.modules.positions.LocationData;
import stan.xtr.ctn.modules.positions.SizeData;
import stan.xtr.ctn.modules.structures.HangarData;
import stan.xtr.ctn.modules.structures.HutData;
import stan.xtr.ctn.modules.units.HumanData;
import stan.xtr.ctn.modules.units.TransportData;
import stan.xtr.ctn.units.activities.UtilActivity;
import stan.xtr.ctn.units.views.TownMapView;

public class GeneralActivity
    extends UtilActivity
{
    private FrameLayout main_frame;
    private TownMapView townMap;

    protected int getContentView()
    {
        return R.layout.general_screen;
    }
    protected void initViews()
    {
        main_frame = view(R.id.main_frame);
    }
    protected void init()
    {
        Town town = TownData.create(1, 0, "Chicago", SizeData.create(8, 8));
        test(town);
//        log(town);
        townMap = new TownMapView(this, town,
                App.component().data().local().locations().extractionBases().getAllFrom(town).get(0),
                App.component().data().local().locations().districts().getAllFrom(town));
        townMap.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        main_frame.addView(townMap);
        draw(town);
    }

    private void draw(Town town)
    {
//        townMap.setBackgroundColor(Color.parseColor("#00ff00"));
        townMap.setTownBackgroundColor(Color.parseColor("#004B8A"));
        townMap.setExtractionBaseBackgroundColor(Color.parseColor("#E16428"));
        townMap.setDistrictBackgroundColor(Color.parseColor("#00AF43"));
    }

    private void test(Town town)
    {
        App.component().data().local().map().towns().add(town);
        test(ExtractionBaseData.create(11, town.id(), "Outer Haven", SizeData.create(8, 8), CoordinatesData.create(5, 5)));
        test(DistrictData.create(11, town.id(), "District11", SizeData.create(12, 12), CoordinatesData.create(1, 1)));
    }
    private void test(ExtractionBase base)
    {
        App.component().data().local().locations().extractionBases().add(base);
        test(HangarData.create(111, base.id(), 5));
        test(HutData.create(111, base.id(), 5));
    }
    private void test(Hangar hangar)
    {
        App.component().data().local().structures().hangars().add(hangar);
        Transport transport = TransportData.create(1, Transport.Type.PASSENGER, 5);
        App.component().data().local().units().transports().add(transport);
        App.component().data().local().positions().locations().update(LocationData.create(Units.Type.TRANSPORT, transport.id(), Locations.Type.HANGAR, hangar.id()));
    }
    private void test(Hut hut)
    {
        App.component().data().local().structures().huts().add(hut);
        Human human = HumanData.create(1, "Kirk Tiberius");
        App.component().data().local().units().humans().add(human);
        App.component().data().local().units().humans().add(HumanData.create(2, "Lincoln Burrows"));
        App.component().data().local().units().humans().add(HumanData.create(3, "Gregory House"));
        App.component().data().local().units().humans().add(HumanData.create(4, "Cal Lightman"));
        App.component().data().local().units().humans().add(HumanData.create(5, "Rick Sanchez"));
        App.component().data().local().positions().locations().update(LocationData.create(Units.Type.HUMAN, human.id(), Locations.Type.HUT, hut.id()));

    }
    private void test(District district)
    {
        App.component().data().local().locations().districts().add(district);
        App.component().data().local().locations().sources().add(SourceData.create(111, district.id(), "Source111"));
        App.component().data().local().locations().colonies().add(ColonyData.create(111, district.id(), "Colony111"));
    }
    private void log(Town town)
    {
        log("town: " + town);
        for(ExtractionBase base: App.component().data().local().locations().extractionBases().getAllFrom(town))
        {
            log(base);
        }
        for(District district: App.component().data().local().locations().districts().getAllFrom(town))
        {
            log(district);
        }
    }
    private void log(ExtractionBase base)
    {
        log("\textraction base: " + base);
        for(Hangar hangar: App.component().data().local().structures().hangars().getAllFrom(base))
        {
            log(hangar);
        }
        for(Hut hut: App.component().data().local().structures().huts().getAllFrom(base))
        {
            log(hut);
        }
    }
    private void log(Hangar hangar)
    {
        log("\t\thangar: " + hangar);
        for(Pair<Units.Type, Long> transport: App.component().data().local().positions().locations().get(Locations.Type.HANGAR, hangar.id()))
        {
            log("\t\t\ttransport: " + App.component().data().local().units().transports().get(transport.second()));
        }
    }
    private void log(Hut hut)
    {
        log("\t\thut: " + hut);
        for(Pair<Units.Type, Long> human: App.component().data().local().positions().locations().get(Locations.Type.HUT, hut.id()))
        {
            log("\t\t\thuman: " + App.component().data().local().units().humans().get(human.second()));
        }
    }
    private void log(District district)
    {
        log("\tdistrict: " + district);
        for(Source source: App.component().data().local().locations().sources().getAllFrom(district))
        {
            log(source);
        }
        for(Colony colony: App.component().data().local().locations().colonies().getAllFrom(district))
        {
            log(colony);
        }
    }
    private void log(Source source)
    {
        log("\t\tsource: " + source);
    }
    private void log(Colony colony)
    {
        log("\t\tcolony: " + colony);
    }
}