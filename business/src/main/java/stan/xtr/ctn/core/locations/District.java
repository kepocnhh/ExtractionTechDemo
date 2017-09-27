package stan.xtr.ctn.core.locations;

import stan.xtr.ctn.core.positions.Coordinates;
import stan.xtr.ctn.core.positions.Size;

public interface District
{
    long id();
    long parentId();
    String name();
    Size size();
    Coordinates coordinates();
}