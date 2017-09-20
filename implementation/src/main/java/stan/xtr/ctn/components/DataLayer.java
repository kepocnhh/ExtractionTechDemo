package stan.xtr.ctn.components;

import stan.xtr.ctn.data.local.DAO;

public interface DataLayer
{
    DAO local();

    class Impl
        implements DataLayer
    {
        static public DataLayer create(DAO local)
        {
            return new Impl(local);
        }

        private final DAO local;

        private Impl(DAO local)
        {
            this.local = local;
        }

        public DAO local()
        {
            return local;
        }
    }
}