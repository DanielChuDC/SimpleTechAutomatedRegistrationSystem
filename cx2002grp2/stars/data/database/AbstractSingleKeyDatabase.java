package cx2002grp2.stars.data.database;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import cx2002grp2.stars.data.dataitem.SingleKeyItem;

/**
 * A common implementation for the {@link SingleKeyDatabase}.
 * <p>
 * The following functions are implemented in addition to what implemented in
 * {@link AbstractDatabase AbstractDatabase&lt;ItemType&gt;}:
 * <ul>
 * <li>Adding item.
 * <li>Deleting item, through either item itself or its key.
 * <li>Checking if the item exist, through either item itself or its key.
 * <li>Changing key.
 * <li>Get size of database
 * <li>Support iteration
 * <li>Implement interface {@link OnKeyChangedSubject
 * OnKeyChangedSubject&lt;KeyType, ItemType&gt;}} and the corresponding
 * signaling method
 * {@link AbstractSingleKeyDatabase#signalKeyChanged(Object, SingleKeyItem)}
 * </ul>
 */
public abstract class AbstractSingleKeyDatabase<KeyType extends Comparable<KeyType>, ItemType extends SingleKeyItem<KeyType>>
        extends AbstractDatabase<ItemType> implements SingleKeyDatabase<KeyType, ItemType> {

    /**
     * A map used to mapping from a key to a data item.
     */
    private Map<KeyType, ItemType> data = new TreeMap<>();

    /**
     * {@inheritDoc}
     * 
     * @return The original item in database if there is an item has the same key
     *         with the added item , or null if no replacement happens
     * @throws NullPointerException If the inserted item is null, or its key value
     *                              is null.
     */
    @Override
    public ItemType addItem(ItemType item) {
        Objects.requireNonNull(item);
        Objects.requireNonNull(item.getKey());

        ItemType oldItem = data.put(item.getKey(), item);

        if (oldItem == null) {
            signalItemAdded(item);
        }

        return oldItem;
    }

    @Override
    public ItemType delItem(ItemType item) {
        if (item == null) {
            return null;
        }

        return delByKey(item.getKey());
    }

    @Override
    public boolean hasItem(ItemType item) {
        if (item == null) {
            return false;
        }
        return hasKey(item.getKey());
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public Iterator<ItemType> iterator() {
        return Collections.unmodifiableCollection(data.values()).iterator();
    }

    @Override
    public ItemType getByKey(KeyType key) {
        if (key == null) {
            return null;
        }

        return data.get(key);
    }

    @Override
    public ItemType delByKey(KeyType key) {
        if (key == null) {
            return null;
        }

        ItemType deletedItem = data.remove(key);

        return deletedItem;
    }

    @Override
    public boolean hasKey(KeyType key) {
        if (key == null) {
            return false;
        }

        return data.containsKey(key);
    }

    @Override
    public boolean changeKey(KeyType oldKey, KeyType newKey) {
        Objects.requireNonNull(oldKey);
        Objects.requireNonNull(newKey);

        if (oldKey.equals(newKey)) {
            return false;
        }

        ItemType item = getByKey(oldKey);

        if (item == null) {
            return false;
        }

        data.remove(oldKey);
        item.setKey(newKey);
        data.put(item.getKey(), item);

        signalKeyChanged(oldKey, item);

        return true;
    }

    @Override
    public boolean changeKey(ItemType oldItem, KeyType newKey) {
        Objects.requireNonNull(oldItem);
        Objects.requireNonNull(newKey);

        return changeKey(oldItem.getKey(), newKey);
    }

    /**
     * 
     * @return
     */
    protected Map<KeyType, ItemType> getDataMap() {
        return this.data;
    }

    /**
     * 
     * @param newData
     */
    protected void setDataMap(Map<KeyType, ItemType> newData) {
        this.data = newData;
    }

    private Collection<OnKeyChangedObserver<? super KeyType, ? super ItemType>> onKeyChangedObservers = new HashSet<>();

    @Override
    public void addOnKeyChangedObserver(OnKeyChangedObserver<? super KeyType, ? super ItemType> observer) {
        onKeyChangedObservers.add(observer);
    }

    @Override
    public void delOnKeyChangedObserver(OnKeyChangedObserver<? super KeyType, ? super ItemType> observer) {
        onKeyChangedObservers.remove(observer);
    }

    /**
     * 
     * @param oldKey
     * @param newItem
     */
    protected void signalKeyChanged(KeyType oldKey, ItemType newItem) {
        onKeyChangedObservers.forEach(ob -> ob.doOnKeyChange(oldKey, newItem));
    }
}
