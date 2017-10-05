package stan.xtr.ctn.units.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

import stan.xtr.ctn.core.locations.District;
import stan.xtr.ctn.core.locations.ExtractionBase;
import stan.xtr.ctn.core.map.Town;
import stan.xtr.ctn.utils.ValueAnimator;

public class TownMapView
        extends View
{
    private final Paint backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint townBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint extractionBaseBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint districtBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint techLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
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
    private final Town town;
    private final ExtractionBase base;
    private final List<District> districts;

    private ValueAnimator.Animator xOffsetAnimator;
    private ValueAnimator.Animator yOffsetAnimator;

    public TownMapView(Context context, Town t, ExtractionBase b, List<District> ds)
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
        if(b == null)
        {
            throw new IllegalArgumentException("Property base must be exist!");
        }
        base = b;
        if(ds == null)
        {
            throw new IllegalArgumentException("Property districts must be exist!");
        }
        districts = ds;
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
        techLinePaint.setColor(Color.WHITE);
        techTextPaint.setColor(Color.WHITE);
        techTextPaint.setTextSize(px(12));
    }

    public void setBackgroundColor(int color)
    {
        backgroundPaint.setColor(color);
        recalculate();
    }
    public void setTownBackgroundColor(int color)
    {
        townBackgroundPaint.setColor(color);
        recalculate();
    }
    public void setExtractionBaseBackgroundColor(int color)
    {
        extractionBaseBackgroundPaint.setColor(color);
        recalculate();
    }
    public void setDistrictBackgroundColor(int color)
    {
        districtBackgroundPaint.setColor(color);
        recalculate();
    }
    private void recalculate()
    {
    }

    public boolean onTouchEvent(MotionEvent ev)
    {
        if(xOffsetAnimator != null)
        {
            xOffsetAnimator.cancel();
        }
        if(yOffsetAnimator != null)
        {
            yOffsetAnimator.cancel();
        }
        if(ev.getPointerCount() > 1)
        {
            if(ev.getPointerCount() == 2)
            {
                return onTouchZoom(ev.getAction(), ev.getX(0), ev.getY(0), ev.getX(1), ev.getY(1));
            }
            lastTouchEvent = TouchEvent.UNKNOWN;
            return onTouchUnknown(ev.getAction(), ev.getX(), ev.getY());
        }
        else
        {
            return onTouchEvent(ev.getAction(), ev.getX(), ev.getY());
        }
    }
    public boolean onTouchUnknown(int action, float x, float y)
    {
        log("touch UNKNOWN"
                +"\n\t" + "x: " + x + " y: " + y);
        switch(action & MotionEvent.ACTION_MASK)
        {
            case MotionEvent.ACTION_UP:
                return onTouchUnknownUp(x, y);
        }
        return false;
    }
    public boolean onTouchUnknownUp(float x, float y)
    {
        log("touch UNKNOWN UP /\\");
        fullCheck(x, y);
        invalidate();
        return true;
    }
    private void fullCheck(float x, float y)
    {
        if(!checkWidth(getWidth()))
        {
            checkXOffset(town.size().width()*elementSize, x);
        }
        if(!checkHeight(getHeight()))
        {
            checkYOffset(town.size().height()*elementSize, y);
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
            case MotionEvent.ACTION_POINTER_UP:
                return onTouchZoomUp(x1, y1, x2, y2);
        }
        return false;
    }
    private boolean onTouchZoomDown(float x1, float y1, float x2, float y2)
    {
        log("touch ZOOM DOWN");
        updateStartedZoom(x1, y1, x2, y2);
        lastTouchEvent = TouchEvent.ZOOM;
        return true;
    }
    private boolean onTouchZoomMove(float x1, float y1, float x2, float y2)
    {
        log("touch ZOOM MOVE");
        if(lastTouchEvent != TouchEvent.ZOOM)
        {
            updateStartedZoom(x1, y1, x2, y2);
        }
        float newZoom = round(startedTrackingZoom * (distance(x1, y1, x2, y2) / startedTrackingZoomDistance), 100);
        if(newZoom < 1)
        {
            updateZoom(1, (int)(x1/2+x2/2), (int)(y1/2+y2/2));
            updateStartedZoom(x1, y1, x2, y2);
        }
        else if(newZoom > 4)
        {
            updateZoom(4, (int)(x1/2+x2/2), (int)(y1/2+y2/2));
            updateStartedZoom(x1, y1, x2, y2);
        }
        else
        {
            updateZoom(newZoom, (int)(x1/2+x2/2), (int)(y1/2+y2/2));
        }
        elementSize = (int)(elementSizeStandard * zoom);
        invalidate();
        lastTouchEvent = TouchEvent.ZOOM;
        return true;
    }
    private void updateStartedZoom(float x1, float y1, float x2, float y2)
    {
        startedTrackingZoom = zoom;
        startedTrackingXOffset = xOffset - (x1/2+x2/2);
        startedTrackingYOffset = yOffset - (y1/2+y2/2);
        startedTrackingZoomDistance = distance(x1, y1, x2, y2);
    }
    private void updateZoom(float newZoom, int xT, int yT)
    {
        zoom = newZoom;
        xOffset = (int)(xT+(startedTrackingXOffset/startedTrackingZoom)*zoom);
        yOffset = (int)(yT+(startedTrackingYOffset/startedTrackingZoom)*zoom);
    }
    private float round(float f, int i)
    {
        return ((float)((int)(f*i)))/i;
    }
    private boolean onTouchZoomUp(float x1, float y1, float x2, float y2)
    {
        log("touch ZOOM UP /\\");
//        fullCheck(x1/2+x2/2, y1/2+y2/2);
//        invalidate();
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
                return onTouchUp(x, y);
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
    private boolean onTouchUp(float x, float y)
    {
        log("touch UP /\\");
        fullCheck(x, y);
        invalidate();
        return true;
    }
    private void onTouchMoveCalculate(float x, float y)
    {
        log("move x: " + x + " y: " + y);
//        onTouchMoveCalculateX(x);
//        onTouchMoveCalculateY(y);
        xOffset = (int)(startedTrackingXOffset + x);
        yOffset = (int)(startedTrackingYOffset + y);
    }
    private void onTouchMoveCalculateX(float x)
    {
        int width = town.size().width()*elementSize;
        if(width + elementSizeStandard*4 < getWidth())
        {
//            xOffset = getWidth()/2 - width/2;
        }
        else
        {
            xOffset = (int)(startedTrackingXOffset + x);
            checkXOffset(width, x);
        }
    }
    private void checkXOffset(int width, final float x)
    {
        if(xOffset > elementSizeStandard*2)
        {
            if(xOffsetAnimator != null)
            {
                xOffsetAnimator.cancel();
            }
            xOffsetAnimator = ValueAnimator.create(250, xOffset, elementSizeStandard*2, new ValueAnimator.Updater<Integer>()
            {
                public void update(final Integer value)
                {
                    post(new Runnable()
                    {
                        public void run()
                        {
                            xOffset = value;
                            startedTrackingXOffset = xOffset - x;
                            invalidate();
                        }
                    });
                }
            });
            xOffsetAnimator.animate();
//            xOffset = elementSizeStandard*2;
//            startedTrackingXOffset = xOffset - x;
        }
        else if(xOffset < getWidth() - (width + elementSizeStandard*2))
        {
            if(xOffsetAnimator != null)
            {
                xOffsetAnimator.cancel();
            }
            xOffsetAnimator = ValueAnimator.create(250, xOffset, getWidth() - (width + elementSizeStandard*2), new ValueAnimator.Updater<Integer>()
            {
                public void update(final Integer value)
                {
                    post(new Runnable()
                    {
                        public void run()
                        {
                            xOffset = value;
                            startedTrackingXOffset = xOffset - x;
                            invalidate();
                        }
                    });
                }
            });
            xOffsetAnimator.animate();
//            xOffset = getWidth() - (width + elementSizeStandard*2);
//            startedTrackingXOffset = xOffset - x;
        }
    }
    private void onTouchMoveCalculateY(float y)
    {
        int height = town.size().height()*elementSize;
        if(height + elementSizeStandard*4 < getHeight())
        {
//            yOffset = getHeight()/2 - height/2;
        }
        else
        {
            yOffset = (int)(startedTrackingYOffset + y);
            checkYOffset(height, y);
        }
    }
    private void checkYOffset(int height, final float y)
    {
        if(yOffset > elementSizeStandard*2)
        {
            if(yOffsetAnimator != null)
            {
                yOffsetAnimator.cancel();
            }
            yOffsetAnimator = ValueAnimator.create(250, yOffset, elementSizeStandard*2, new ValueAnimator.Updater<Integer>()
            {
                public void update(final Integer value)
                {
                    post(new Runnable()
                    {
                        public void run()
                        {
                            yOffset = value;
                            startedTrackingYOffset = yOffset - y;
                            invalidate();
                        }
                    });
                }
            });
            yOffsetAnimator.animate();
//            yOffset = elementSizeStandard*2;
//            startedTrackingYOffset = yOffset - y;
        }
        else if(yOffset < getHeight() - (height + elementSizeStandard*2))
        {
            if(yOffsetAnimator != null)
            {
                yOffsetAnimator.cancel();
            }
            yOffsetAnimator = ValueAnimator.create(250, yOffset, getHeight() - (height + elementSizeStandard*2), new ValueAnimator.Updater<Integer>()
            {
                public void update(final Integer value)
                {
                    post(new Runnable()
                    {
                        public void run()
                        {
                            yOffset = value;
                            startedTrackingYOffset = yOffset - y;
                            invalidate();
                        }
                    });
                }
            });
            yOffsetAnimator.animate();
//            yOffset = getHeight() - (height + elementSizeStandard*2);
//            startedTrackingYOffset = yOffset - y;
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
        checkWidth(width);
        checkHeight(height);
    }
    private boolean checkWidth(int width)
    {
        int townWidth = town.size().width()*elementSize;
        if(townWidth + elementSizeStandard*4 < width)
        {
            if(xOffsetAnimator != null)
            {
                xOffsetAnimator.cancel();
            }
            xOffsetAnimator = ValueAnimator.create(250, xOffset, width/2 - townWidth/2, new ValueAnimator.Updater<Integer>()
            {
                public void update(final Integer value)
                {
                    post(new Runnable()
                    {
                        public void run()
                        {
                            xOffset = value;
                            invalidate();
                        }
                    });
                }
            });
            xOffsetAnimator.animate();
//            xOffset = width/2 - townWidth/2;
            return true;
        }
        return false;
    }
    private boolean checkHeight(int height)
    {
        int townHeight = town.size().height()*elementSize;
        if(townHeight + elementSizeStandard*4 < height)
        {
            if(yOffsetAnimator != null)
            {
                yOffsetAnimator.cancel();
            }
            yOffsetAnimator = ValueAnimator.create(250, yOffset, height/2 - townHeight/2, new ValueAnimator.Updater<Integer>()
            {
                public void update(final Integer value)
                {
                    post(new Runnable()
                    {
                        public void run()
                        {
                            yOffset = value;
                            invalidate();
                        }
                    });
                }
            });
            yOffsetAnimator.animate();
//            yOffset = height/2 - townHeight/2;
            return true;
        }
        return false;
    }
    protected void onDraw(Canvas canvas)
    {
        canvas.drawRect(0, 0, getWidth(), getHeight(), backgroundPaint);
        onDrawTown(canvas, town);
        onDrawExtractionBase(canvas, base);
        onDrawDistricts(canvas, districts);
        onDrawTech(canvas);
    }
    private void onDrawTown(Canvas canvas, Town town)
    {
        canvas.drawRect(xOffset, yOffset, xOffset + town.size().width()*elementSize, yOffset + town.size().height()*elementSize, townBackgroundPaint);
        onDrawTownTech(canvas, town);
    }
    private void onDrawTownTech(Canvas canvas, Town town)
    {
        for(int x=1; x<town.size().width(); x++)
        {
            canvas.drawLine(xOffset + elementSize*x, yOffset, xOffset + elementSize*x, yOffset + town.size().height()*elementSize, techLinePaint);
        }
        for(int y=1; y<town.size().height(); y++)
        {
            canvas.drawLine(xOffset, yOffset + elementSize*y, xOffset + town.size().width()*elementSize, yOffset + elementSize*y, techLinePaint);
        }
//        for(int x=0; x<town.size().width(); x++)
//        {
//            for(int y=0; y<town.size().height(); y++)
//            {
//                canvas.drawText(x + "," + y, xOffset + elementSize*x + elementSize/3, yOffset + elementSize*y + elementSize/3, techTextPaint);
//            }
//        }
    }
    private void onDrawExtractionBase(Canvas canvas, ExtractionBase base)
    {
        canvas.drawRect(xOffset + base.coordinates().x()*elementSize,
                yOffset + base.coordinates().y()*elementSize,
                xOffset + base.coordinates().x()*elementSize + (base.size().width()/4)*elementSize,
                yOffset + base.coordinates().y()*elementSize + (base.size().height()/4)*elementSize, extractionBaseBackgroundPaint);
        onDrawExtractionBaseTech(canvas, base);
    }
    private void onDrawExtractionBaseTech(Canvas canvas, ExtractionBase base)
    {
        canvas.drawText(base.name(),
                xOffset + base.coordinates().x()*elementSize + elementSize/3,
                yOffset + base.coordinates().y()*elementSize + elementSize/2,
                techTextPaint);
    }
    private void onDrawDistricts(Canvas canvas, List<District> districts)
    {
        for(District district: districts)
        {
            onDrawDistrict(canvas, district);
        }
    }
    private void onDrawDistrict(Canvas canvas, District district)
    {
        canvas.drawRect(xOffset + district.coordinates().x()*elementSize,
                yOffset + district.coordinates().y()*elementSize,
                xOffset + district.coordinates().x()*elementSize + (district.size().width()/4)*elementSize,
                yOffset + district.coordinates().y()*elementSize + (district.size().height()/4)*elementSize, districtBackgroundPaint);
        onDrawDistrictTech(canvas, district);
    }
    private void onDrawDistrictTech(Canvas canvas, District district)
    {
        canvas.drawText(district.name(),
                xOffset + district.coordinates().x()*elementSize + elementSize/3,
                yOffset + district.coordinates().y()*elementSize + elementSize/2,
                techTextPaint);
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