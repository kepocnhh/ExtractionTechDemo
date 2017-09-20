package stan.xtr.ctn.components;

import stan.xtr.ctn.data.local.DAO;

public class MainComponent
    implements AppComponent
{
    private final DataLayer data;

    public MainComponent(DAO local)
    {
        data = DataLayer.Impl.create(local);
    }

    public DataLayer data()
    {
        return data;
    }
}