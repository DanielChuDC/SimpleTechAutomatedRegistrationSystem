package cx2002grp2.stars.database;

/**
 * Common interface for all the database.
 * <p>
 * A database represent a set of data item. The data item can be added into, and
 * deleted from the database. Checking the size of database and checking if a
 * data item exists in the database are also supported.
 * <p>
 * The database support iterating by implementing {@link Iterable
 * Iterable&lt;ItemType&gt;}.
 * <p>
 * To handle the data synchronization issue among databases, observer pattern
 * are used. All the database can be observed for some events. Two basic events
 * are item addition and item deletion, which are specified through
 * {@link OnItemDeletedSubject OnItemDeletedSubject&lt;ItemType&gt;} and
 * {@link OnItemAddedSubject OnItemAddedSubject&lt;ItemType&gt;}.
 * 
 * @param <ItemType> The data type of item stored by the database.
 * @see AbstractDatabase
 * @see SingleKeyDatabase
 * @see cx2002grp2.stars.dataitem
 */
public interface Database<ItemType>
        extends OnItemDeletedSubject<ItemType>, OnItemAddedSubject<ItemType>, Iterable<ItemType> {
    /**
     * Add a new item into the database or replace an existing item in the database.
     * <p>
     * If an item is added, the database shall inform all
     * {@link OnItemAddedObserver} that are observing on it by calling
     * {@link OnItemAddedObserver#doOnItemAdded(Object)}
     * 
     * @param item the item to be added.
     * @return The original item if the replacement happens, or null if no
     *         replacement happens
     */
    public ItemType addItem(ItemType item);

    /**
     * Delete an item in the database.
     * <p>
     * If an item is deleted, the database shall inform all
     * {@link OnItemDeletedObserver} that are observing on it by calling
     * {@link OnItemDeletedObserver#doOnItemDeleted(Object)}
     * 
     * @param item the item to be deleted.
     * @return the deleted item if the deletion happens. Other wise, return null.
     */
    public ItemType delItem(ItemType item);

    /**
     * Check if an item exists in the database.
     * 
     * @param item the item to be checked.
     * @return true if the item exists in the database. Otherwise, return false.
     */
    public boolean hasItem(ItemType item);

    /**
     * The size of the database.
     * 
     * @return the number of items in the database (&gt;= 0).
     */
    public int size();
    
    /**
     * Save data in database into file.
     */
    public void saveData();
}
