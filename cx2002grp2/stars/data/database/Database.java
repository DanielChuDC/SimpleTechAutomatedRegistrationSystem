package cx2002grp2.stars.data.database;

import cx2002grp2.stars.data.database.event_handler.OnItemAddedSubject;
import cx2002grp2.stars.data.database.event_handler.OnItemDeletedSubject;
import cx2002grp2.stars.util.OnExitObserver;

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
     * @return the deleted item if the deletion happens, or null if the item to be
     *         deleted doesn't exist in the database.
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
     * @return the number of items in the database (>= 0).
     */
    public abstract int size();
}
