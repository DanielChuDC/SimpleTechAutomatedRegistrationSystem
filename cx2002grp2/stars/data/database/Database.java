package cx2002grp2.stars.data.database;

import cx2002grp2.stars.util.OnExitObserver;

/**
 * Common interface for all the database.
 * <p>
 * A database represent a set of data item. The data item can be added into, and
 * deleted from the database. Checking the size of database and checking if a
 * data item exists in the database are also supported.
 * <p>
 * The database support iterating by impelmenting {@link Iterable}
 * <p>
 * TODO - Finish Doc
 * 
 * @param <ItemType> The data type of item stored by the database.
 * @see cx2002grp2.stars.data.dataitem
 */
public interface Database<ItemType>
        extends OnExitObserver, OnItemDeletedSubject<ItemType>, OnItemAddedSubject<ItemType>, Iterable<ItemType> {
    /**
     * Add a new item into the database or replace an existing item in the database.
     * 
     * @param item the item to be added.
     * @return The original item if the replacement happens, or null if no
     *         replacement happens
     * @throws NullPointerException     If the inserted item is null, or any
     *                                  required information of the item (like key)
     *                                  is null.
     * @throws IllegalArgumentException If the inserted item is consided illegal by
     *                                  the database for some reason
     */
    public abstract ItemType addItem(ItemType item);

    /**
     * Delete am item in the database.
     * 
     * @param item the item to be deleted.
     * @return the deleted item if the deletion happens. Other wise, return null.
     */
    public abstract ItemType delItem(ItemType item);

    /**
     * Check if an item exists in the database.
     * 
     * @param item the item to be checked.
     * @return true if the item exists in the database. Otherwise, return false.
     */
    public abstract boolean hasItem(ItemType item);

    /**
     * The size of the database.
     * 
     * @return the number of items in the database (&gt;= 0).
     */
    public abstract int size();
}
