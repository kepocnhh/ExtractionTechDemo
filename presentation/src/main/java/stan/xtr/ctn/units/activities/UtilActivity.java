package stan.xtr.ctn.units.activities;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public abstract class UtilActivity
        extends Activity
{
    private View.OnClickListener clickListener;
    private String tag;

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(getContentView());
        tag = "["+getClass().getSimpleName()+"]";
        clickListener = new View.OnClickListener()
        {
            public void onClick(View view)
            {
                click(view);
            }
        };
        initViews();
        init();
    }

    final protected void setClickListener(View first, View... views)
    {
        if(first != null)
        {
            first.setOnClickListener(clickListener);
        }
        for(View v : views)
        {
            if(v != null)
            {
                v.setOnClickListener(clickListener);
            }
        }
    }
    protected <VIEW extends View> VIEW view(int id)
    {
        return (VIEW)findViewById(id);
    }
    protected void click(View view)
    {
    }

    final protected void toast(final String message)
    {
        runOnUiThread(new Runnable()
        {
            public void run()
            {
                Toast.makeText(UtilActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
    final protected void replace(final int id, final Fragment fragment)
    {
        runOnUiThread(new Runnable()
        {
            public void run()
            {
                getFragmentManager().beginTransaction().replace(id, fragment).commit();
            }
        });
    }
    protected void log(String message)
    {
        Log.e(tag, message);
    }

    abstract protected int getContentView();
    abstract protected void initViews();
    abstract protected void init();
}