package cx2002grp2.stars.data.database;

import cx2002grp2.stars.data.database.event_handler.OnKeyChangedSubject;
import cx2002grp2.stars.data.dataitem.SingleKeyItem;

public interface SingleKeyDatabase<KeyType, ItemType extends SingleKeyItem<KeyType>>
        extends Database<ItemType>, OnKeyChangedSubject<KeyType, ItemType> {

    /**
     * Get the item in the database with the given key.
     * 
     * @param key the key value used to identify the item.
     * @return the item with the given key value. If such item doesn't exist in the database, return null.
     */
    public ItemType getByKey(KeyType key);

    /**
     * Delete the item in the database with the given key.
     * 
     * @param key the key value used to identify the item to be deleted.
     * @return the item being deleted. If such item doesn't exist in the database, return null.
     */
    public ItemType delByKey(KeyType key);

    /**
     * Check whether an item with the given key exists in the database.
     * 
     * @param key the key value used to identify the item to be checked. 
     * @return true if the item exists in the database. Otherwise, return false.
     */
    public boolean hasKey(KeyType key);

    /**
     * 
     * @param oldKey
     * @param newKey
     * @return
     */
    public boolean changeKey(KeyType oldKey, KeyType newKey);

    /**
     * 
     * @param oldItem
     * @param newKey
     * @return
     */
    public boolean changeKey(ItemType oldItem, KeyType newKey);
}
