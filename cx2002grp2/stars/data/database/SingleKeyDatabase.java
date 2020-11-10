package cx2002grp2.stars.data.database;

import cx2002grp2.stars.data.dataitem.SingleKeyItem;

public interface SingleKeyDatabase<KeyType, ItemType extends SingleKeyItem<KeyType>>
        extends Database<ItemType>, OnKeyChangedSubject<KeyType, ItemType> {

    /**
     * Get the item in the database with the given key.
     * 
     * @param key the key value used to identify the item.
     * @return the item with the given key value. If such item doesn't exist in the
     *         database, return null.
     */
    public ItemType getByKey(KeyType key);

    /**
     * Delete the item in the database with the given key.
     * 
     * @param key the key value used to identify the item to be deleted.
     * @return the item being deleted. If such item doesn't exist in the database,
     *         return null.
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
     * Change the key value of item with original key value oldKey to newKey.
     * <p>
     * The key of the item won't be changed in two cases:
     * <ol>
     * <li>The item does not exist in the database.
     * <li>The original key value is the same to the new key value, that is,
     * oldKey.equals(newKey) is true.
     * </ol>
     * <p>
     * If key is changed, all the {@link OnKeyChangedObserver} observering this
     * database will be notified
     * 
     * @param oldKey the original key value of the item.
     * @param newKey the new key value to be set.
     * @return true if the key value of original value is change, otherwise, return
     *         false.
     */
    public boolean changeKey(KeyType oldKey, KeyType newKey);

    /**
     * 
     * Change the key value of item with original key value oldKey to newKey.
     * 
     * @param oldItem the original item.
     * @param newKey  the new key value to be set.
     * @return If the key value of original value is change.
     */
    public boolean changeKey(ItemType oldItem, KeyType newKey);
}
