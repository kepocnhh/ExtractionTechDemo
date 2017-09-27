package stan.xtr.ctn.units.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class TownMap
        extends View
{
    private final float density;

    public TownMap(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        density = context.getResources().getDisplayMetrics().density;
        setOnClickListener(new OnClickListener()
        {
            public void onClick(View view)
            {
            }
        });
    }
}