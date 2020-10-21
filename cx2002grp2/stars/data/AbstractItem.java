package cx2002grp2.stars.data;

import cx2002grp2.stars.util.ToStringRowInterface;

public abstract class AbstractItem implements ToStringRowInterface {
    
    @Override
    public abstract Iterable<String> toStringRow();
}
