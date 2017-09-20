package stan.xtr.ctn;

import android.app.Application;

import stan.xtr.ctn.cache.RuntimeCache;
import stan.xtr.ctn.components.AppComponent;
import stan.xtr.ctn.components.MainComponent;

public class App
        extends Application
{
    static private AppComponent component;
    static public AppComponent component()
    {
        return component;
    }

    public void onCreate()
    {
        super.onCreate();
        component = new MainComponent(new RuntimeCache());
    }
}