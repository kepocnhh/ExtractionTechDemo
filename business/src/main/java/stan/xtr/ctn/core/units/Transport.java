package stan.xtr.ctn.core.units;

public interface Transport
{
    long id();
    Type type();
    int capacity();

    enum Type
    {
        CARGO,
        PASSENGER,
    }
}