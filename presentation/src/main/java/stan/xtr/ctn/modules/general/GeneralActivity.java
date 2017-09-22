package stan.xtr.ctn.modules.general;

import stan.xtr.ctn.App;
import stan.xtr.ctn.R;
import stan.xtr.ctn.core.locations.District;
import stan.xtr.ctn.core.locations.ExtractionBase;
import stan.xtr.ctn.core.locations.Locations;
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
import stan.xtr.ctn.modules.positions.LocationData;
import stan.xtr.ctn.modules.structures.HangarData;
import stan.xtr.ctn.modules.structures.HutData;
import stan.xtr.ctn.modules.units.HumanData;
import stan.xtr.ctn.modules.units.TransportData;
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
        testEntities();
    }

    private void testEntities()
    {
        Town town = TownData.create(1, 0, "Chicago");
        App.component().data().local().map().towns().add(town);
        ExtractionBase base = ExtractionBaseData.create(11, town.id(), "Outer Haven");
        App.component().data().local().locations().extractionBases().add(base);
        Hangar hangar = HangarData.create(111, base.id(), 5);
        App.component().data().local().structures().hangars().add(hangar);
        Hut hut = HutData.create(111, base.id(), 5);
        App.component().data().local().structures().huts().add(hut);
        District district = DistrictData.create(11, town.id(), "District11");
        App.component().data().local().locations().districts().add(district);
        App.component().data().local().locations().sources().add(SourceData.create(111, district.id(), "Source111"));
        App.component().data().local().locations().colonies().add(ColonyData.create(111, district.id(), "Colony111"));
        Human human = HumanData.create(1, "Kirk Tiberius");
        App.component().data().local().units().humans().add(human);
        App.component().data().local().units().humans().add(HumanData.create(2, "Lincoln Burrows"));
        App.component().data().local().units().humans().add(HumanData.create(3, "Gregory House"));
        App.component().data().local().units().humans().add(HumanData.create(4, "Cal Lightman"));
        App.component().data().local().units().humans().add(HumanData.create(5, "Rick Sanchez"));
        App.component().data().local().positions().locations().update(LocationData.create(Units.Type.HUMAN, human.id(), Locations.Type.HUT, hut.id()));
        Transport transport = TransportData.create(1, Transport.Type.PASSENGER, 5);
        App.component().data().local().units().transports().add(transport);
        App.component().data().local().positions().locations().update(LocationData.create(Units.Type.TRANSPORT, transport.id(), Locations.Type.HANGAR, hangar.id()));
        logEntities(town);
    }
    private void logEntities(Town town)
    {
        log("town: " + town);
        for(ExtractionBase base: App.component().data().local().locations().extractionBases().getAllFrom(town))
        {
            log(base);
        }
        for(District district: App.component().data().local().locations().districts().getAllFrom(town))
        {
            log("district: " + district
                    +"\n\tsources: "+ App.component().data().local().locations().sources().getAllFrom(district)
                    +"\n\tcolonies: "+ App.component().data().local().locations().colonies().getAllFrom(district));
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
}