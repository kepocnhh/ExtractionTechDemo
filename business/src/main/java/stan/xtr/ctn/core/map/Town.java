package stan.xtr.ctn.core.map;

import stan.xtr.ctn.core.positions.Size;

public interface Town
{
    long id();
    long parentId();
    String name();
    Size size();
}