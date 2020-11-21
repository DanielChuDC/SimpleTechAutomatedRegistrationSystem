package cx2002grp2.stars.database;

import cx2002grp2.stars.dataitem.SingleKeyItem;

/**
 * A database managing the data item with {@link SingleKeyItem single key
 * value}.
 * <p>
 * The following operations are supported in addition to what specified by
 * {@link Database Database&lt;ItemType&gt;}:
 * <ul>
 * <li>Getting item with the given key value.
 * <li>Deleting item with the given key value.
 * <li>Check if a item with the given key value exists in the database.
 * <li>Change the key value, and notify the database interested in key changing
 * event.
 * </ul>
 * <p>
 * Key change event is supported by the database through interface
 * {@link OnKeyChangedSubject OnKeyChangedSubject&lt;KeyType, ItemType&gt;}
 * @see AbstractSingleKeyDatabase
 */
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
     * @return the deleted item if deletion happens. Otherwise, return null.
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
     * If key is changed, all the {@link OnKeyChangedObserver} observing this
     * database will be notified
     * 
     * @param oldKey the original key value of the item.
     * @param newKey the new key value to be set.
     * @return true if the key value of original value is change, otherwise, return
     *         false.
     */
    public boolean changeKey(KeyType oldKey, KeyType newKey);

    /**
     * Change the key value of item to newKey.
     * <p>
     * The key of the item won't be changed in two cases:
     * <ol>
     * <li>The item does not exist in the database.
     * <li>The new key already exists in the database.
     * <li>The original key value is the same to the new key value, that is,
     * oldKey.equals(newKey) is true.
     * </ol>
     * <p>
     * If key is changed, all the {@link OnKeyChangedObserver} observing this
     * database will be notified
     * 
     * @param oldItem the original item.
     * @param newKey  the new key value to be set.
     * @return true if the key value of original value is change, otherwise, return
     *         false.
     */
    public boolean changeKey(ItemType oldItem, KeyType newKey);
}
