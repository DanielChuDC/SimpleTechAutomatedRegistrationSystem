package cx2002grp2.stars.data.database;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import cx2002grp2.stars.data.database.event_handler.OnKeyChangedObserver;
import cx2002grp2.stars.data.database.event_handler.OnKeyChangedSubject;
import cx2002grp2.stars.data.dataitem.SingleStringKeyItem;

public abstract class SingleStringKeyDatabase<ItemType extends SingleStringKeyItem> extends AbstractDatabase<ItemType>
        implements OnKeyChangedSubject<String, ItemType> {
    private Map<String, ItemType> data = new HashMap<>();

    @Override
    public boolean add(ItemType item) {
        if (data.get(item.getKey()) != null) {
            return false;
        }

        data.put(item.getKey(), item);
        signalItemAdded(item);
        return true;
    }

    @Override
    public boolean del(ItemType item) {
        return del(item.getKey());
    }

    @Override
    public boolean contains(ItemType item) {
        return contains(item.getKey());
    }

    @Override
    public Iterator<ItemType> iterator() {
        return data.values().iterator();
    }

    /**
     * 
     * @param key
     * @return
     */
    public ItemType get(String key) {
        return data.get(key);
    }

    /**
     * 
     * @param key
     * @return
     */
    public boolean del(String key) {
        if (contains(key)) {
            ItemType deletedItem = data.remove(key);
            signalItemDeleted(deletedItem);
            return true;
        }
        return false;
    }

    /**
     * 
     * @param key
     * @return
     */
    public boolean contains(String key) {
        return data.containsKey(key);
    }

    /**
     * 
     * @param oldKey
     * @param newKey
     * @return
     */
    public boolean changeKey(String oldKey, String newKey) {
        if (this.contains(newKey)) {
            return false;
        }

        if (oldKey.equals(newKey)) {
            return false;
        }

        ItemType item = get(oldKey);

        data.remove(oldKey);
        item.setKey(newKey);
        data.put(item.getKey(), item);

        signalKeyChanged(oldKey, item);

        return true;
    }

    /**
     * 
     * @param oldItem
     * @param newKey
     * @return
     */
    public boolean changeKey(ItemType oldItem, String newKey) {
        return changeKey(oldItem.getKey(), newKey);
    }

    /**
     * 
     * @return
     */
    protected Map<String, ItemType> getDataMap() {
        return this.data;
    }

    /**
     * 
     * @param newData
     */
    protected void setDataMap(Map<String, ItemType> newData) {
        if (data == null) {
            throw new NullPointerException();
        }
        this.data = newData;
    }
    
    private Collection<OnKeyChangedObserver<? super String, ? super ItemType>> onKeyChangedObservers = new HashSet<>();

    @Override
    public void addOnKeyChangedObserver(OnKeyChangedObserver<? super String, ? super ItemType> observer) {
        onKeyChangedObservers.add(observer);
    }

    @Override
    public void delOnKeyChangedObserver(OnKeyChangedObserver<? super String, ? super ItemType> observer) {
        onKeyChangedObservers.remove(observer);
    }

    protected void signalKeyChanged(String newKey, ItemType oldItem) {
        onKeyChangedObservers.forEach(ob -> ob.doOnKeyChange(newKey, oldItem));
    }
}
