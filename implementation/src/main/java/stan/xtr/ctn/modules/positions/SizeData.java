package stan.xtr.ctn.modules.positions;

import stan.xtr.ctn.core.positions.Size;

public class SizeData
    implements Size
{
    static public Size create(int width, int height)
    {
        return new SizeData(width, height);
    }

    private final int width;
    private final int height;

    private SizeData(int width, int height)
    {
        if(width < 1)
        {
            throw new IllegalArgumentException("Property width must be positive!");
        }
        this.width = width;
        if(height < 1)
        {
            throw new IllegalArgumentException("Property height must be positive!");
        }
        this.height = height;
    }

    public int width()
    {
        return width;
    }
    public int height()
    {
        return height;
    }

    public String toString()
    {
        return "{" + width + ","+height+"}";
    }
    public boolean equals(Object o)
    {
        return o != null && o instanceof Size && equals((Size)o);
    }
    private boolean equals(Size size)
    {
        return size.width() == width && size.height() == height;
    }
    public int hashCode()
    {
        return width + height * 13;
    }
}