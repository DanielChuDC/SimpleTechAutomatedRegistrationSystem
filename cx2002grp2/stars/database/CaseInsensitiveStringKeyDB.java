package cx2002grp2.stars.database;

import cx2002grp2.stars.dataitem.SingleKeyItem;

/**
 * A database with case insensitive string as key.
 */
public abstract class CaseInsensitiveStringKeyDB<ItemType extends SingleKeyItem<String>>
        extends AbstractSingleKeyDatabase<String, ItemType> {
    
    /**
     * Construct a database with case insensitive string as key.
     */
    public CaseInsensitiveStringKeyDB() {
        super(String.CASE_INSENSITIVE_ORDER);
    }
}
