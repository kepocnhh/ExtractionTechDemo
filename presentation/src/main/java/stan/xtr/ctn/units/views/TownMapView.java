package stan.xtr.ctn.units.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import stan.xtr.ctn.core.map.Town;

public class TownMapView
        extends View
{
    private final Paint backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint townBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final float density;

    private int elementSize;
    private int xOffset = 0;
    private int yOffset = 0;
    private float startedTrackingXOffset;
    private float startedTrackingX;
    private float startedTrackingYOffset;
    private float startedTrackingY;
    private int backgroundColor = Color.BLACK;
    private Town town;
    private int townBackgroundColor = Color.RED;

    public TownMapView(Context context)
    {
        super(context);
        density = context.getResources().getDisplayMetrics().density;
        elementSize = px(32);
        recalculate();
        setOnClickListener(new OnClickListener()
        {
            public void onClick(View view)
            {
            }
        });
    }

    public void setBackgroundColor(int color)
    {
        backgroundColor = color;
        recalculate();
    }
    public void setTownBackgroundColor(int color)
    {
        townBackgroundColor = color;
        recalculate();
    }
    private void recalculate()
    {
        backgroundPaint.setColor(backgroundColor);
        townBackgroundPaint.setColor(townBackgroundColor);
    }
    public void draw(Town t)
    {
        town = t;
        recalculate();
        invalidate();
    }

    public boolean onTouchEvent(MotionEvent ev)
    {
        if(ev.getPointerCount() > 1)
        {
            return false;
        }
        else
        {
            return onTouchEvent(ev.getAction(), ev.getX(), ev.getY());
        }
    }
    private boolean onTouchEvent(int action, float x, float y)
    {
        log("touch x: " + x + " y: " + y);
        switch(action)
        {
            case MotionEvent.ACTION_DOWN:
                return onTouchDown(x, y);
            case MotionEvent.ACTION_MOVE:
                return onTouchMove(x, y);
            case MotionEvent.ACTION_UP:
                break;
        }
        return false;
    }
    private boolean onTouchDown(float x, float y)
    {
        log("touch DOWN");
        startedTrackingXOffset = xOffset;
        startedTrackingX = x;
        startedTrackingYOffset = yOffset;
        startedTrackingY = y;
        return true;
    }
    private boolean onTouchMove(float x, float y)
    {
        log("touch MOVE");
        xOffset = (int)(startedTrackingXOffset + (x - startedTrackingX));
        yOffset = (int)(startedTrackingYOffset + (y - startedTrackingY));
        invalidate();
        return true;
    }

    protected void onDraw(Canvas canvas)
    {
        canvas.drawRect(0, 0, getWidth(), getHeight(), backgroundPaint);
        if(town != null)
        {
            onDrawTown(canvas, town);
        }
    }
    private void onDrawTown(Canvas canvas, Town town)
    {
        canvas.drawRect(xOffset, yOffset, xOffset + town.size().width()*elementSize, yOffset + town.size().height()*elementSize, townBackgroundPaint);
    }

    private int px(float dp)
    {
        if(dp < 0)
        {
            return 0;
        }
        return (int)Math.ceil(density * dp);
    }
    private void log(String m)
    {
        Log.e(getClass().getSimpleName(), m);
    }
}