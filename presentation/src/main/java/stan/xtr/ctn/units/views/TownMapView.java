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
    private final Paint techTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final float density;

    private int elementSize;
    private int elementSizeStandard;
    private int xOffset = 0;
    private int yOffset = 0;
    private TouchEvent lastTouchEvent;
    private float startedTrackingXOffset;
    private float startedTrackingYOffset;
    private float startedTrackingZoom;
    private float startedTrackingZoomDistance;
    private float zoom = 1;
    private int backgroundColor = Color.BLACK;
    private Town town;
    private int townBackgroundColor = Color.RED;

    public TownMapView(Context context, Town t)
    {
        super(context);
        density = context.getResources().getDisplayMetrics().density;
        elementSizeStandard = px(32);
        elementSize = elementSizeStandard;
        setTechProperties();
        if(t == null)
        {
            throw new IllegalArgumentException("Property town must be exist!");
        }
        town = t;
        lastTouchEvent = TouchEvent.NONE;
        recalculate();
        setOnClickListener(new OnClickListener()
        {
            public void onClick(View view)
            {
            }
        });
    }
    private void setTechProperties()
    {
        techTextPaint.setColor(Color.WHITE);
        techTextPaint.setTextSize(px(12));
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

    public boolean onTouchEvent(MotionEvent ev)
    {
        if(ev.getPointerCount() > 1)
        {
            if(ev.getPointerCount() == 2)
            {
                lastTouchEvent = TouchEvent.ZOOM;
                return onTouchZoom(ev.getAction(), ev.getX(0), ev.getY(0), ev.getX(1), ev.getY(1));
            }
            lastTouchEvent = TouchEvent.UNKNOWN;
            return false;
        }
        else
        {
            return onTouchEvent(ev.getAction(), ev.getX(), ev.getY());
        }
    }
    private boolean onTouchZoom(int action, float x1, float y1, float x2, float y2)
    {
        log("touch ZOOM"
                +"\n\t"+"x1: " + x1 + " y1: " + y1
                +"\n\t"+"x2: " + x2 + " y2: " + y2);
//        switch(action)
        switch(action & MotionEvent.ACTION_MASK)
        {
            case MotionEvent.ACTION_POINTER_DOWN:
                return onTouchZoomDown(x1, y1, x2, y2);
            case MotionEvent.ACTION_MOVE:
                return onTouchZoomMove(x1, y1, x2, y2);
//            case MotionEvent.ACTION_UP:
//                break;
        }
        return false;
    }
    private boolean onTouchZoomDown(float x1, float y1, float x2, float y2)
    {
        log("touch ZOOM DOWN");
        startedTrackingZoom = zoom;
        startedTrackingZoomDistance = distance(x1, y1, x2, y2);
        startedTrackingXOffset = xOffset - (x1/2+x2/2);
        startedTrackingYOffset = yOffset - (y1/2+y2/2);
        return true;
    }
    private boolean onTouchZoomMove(float x1, float y1, float x2, float y2)
    {
        log("touch ZOOM MOVE");
        float newZoom = startedTrackingZoom * (distance(x1, y1, x2, y2) / startedTrackingZoomDistance);
        zoom = newZoom < 1 ? 1 : newZoom > 4 ? 4 : newZoom;
        elementSize = (int)(elementSizeStandard * zoom);
        checkSize(getWidth(), getHeight());
        onTouchMoveCalculate(x1/2+x2/2, y1/2+y2/2);
        invalidate();
        return true;
    }
    private float distance(float x1, float y1, float x2, float y2)
    {
        return (float)Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
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
        startedTrackingXOffset = xOffset - x;
        startedTrackingYOffset = yOffset - y;
        lastTouchEvent = TouchEvent.MOVE;
        return true;
    }
    private boolean onTouchMove(float x, float y)
    {
        log("touch MOVE");
        if(lastTouchEvent != TouchEvent.MOVE)
        {
            startedTrackingXOffset = xOffset - x;
            startedTrackingYOffset = yOffset - y;
        }
        onTouchMoveCalculate(x, y);
        invalidate();
        lastTouchEvent = TouchEvent.MOVE;
        return true;
    }
    private void onTouchMoveCalculate(float x, float y)
    {
        log("move x: " + x + " y: " + y);
        onTouchMoveCalculateX(x);
        onTouchMoveCalculateY(y);
    }
    private void onTouchMoveCalculateX(float x)
    {
        int width = town.size().width()*elementSize;
        if(width + elementSizeStandard*4 < getWidth())
        {
            xOffset = getWidth()/2 - width/2;
        }
        else
        {
            xOffset = (int)(startedTrackingXOffset + x);
            if(xOffset > elementSizeStandard*2)
            {
                xOffset = elementSizeStandard*2;
                startedTrackingXOffset = xOffset - x;
            }
            else if(xOffset < getWidth() - (width + elementSizeStandard*2))
            {
                xOffset = getWidth() - (width + elementSizeStandard*2);
                startedTrackingXOffset = xOffset - x;
            }
        }
    }
    private void onTouchMoveCalculateY(float y)
    {
        int height = town.size().height()*elementSize;
        if(height + elementSizeStandard*4 < getHeight())
        {
            yOffset = getHeight()/2 - height/2;
        }
        else
        {
            yOffset = (int)(startedTrackingYOffset + y);
            if(yOffset > elementSizeStandard*2)
            {
                yOffset = elementSizeStandard*2;
                startedTrackingYOffset = yOffset - y;
            }
            else if(yOffset < getHeight() - (height + elementSizeStandard*2))
            {
                yOffset = getHeight() - (height + elementSizeStandard*2);
                startedTrackingYOffset = yOffset - y;
            }
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        checkSize(width, height);
        setMeasuredDimension(width, height);
    }
    private void checkSize(int width, int height)
    {
        int townWidth = town.size().width()*elementSize;
        if(townWidth + elementSizeStandard*4 < width)
        {
            xOffset = width/2 - townWidth/2;
        }
        int townHeight = town.size().height()*elementSize;
        if(townHeight + elementSizeStandard*4 < height)
        {
            yOffset = height/2 - townHeight/2;
        }
    }
    protected void onDraw(Canvas canvas)
    {
        canvas.drawRect(0, 0, getWidth(), getHeight(), backgroundPaint);
        if(town != null)
        {
            onDrawTown(canvas, town);
        }
        onDrawTech(canvas);
    }
    private void onDrawTown(Canvas canvas, Town town)
    {
        canvas.drawRect(xOffset, yOffset, xOffset + town.size().width()*elementSize, yOffset + town.size().height()*elementSize, townBackgroundPaint);
    }
    private void onDrawTech(Canvas canvas)
    {
        canvas.drawText("zoom: " + zoom, px(6), px(18), techTextPaint);
    }

    private enum TouchEvent
    {
        MOVE,
        ZOOM,
        UNKNOWN,
        NONE,
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