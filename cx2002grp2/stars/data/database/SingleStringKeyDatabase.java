package cx2002grp2.stars.data.database;

import java.util.HashMap;
import java.util.Map;

import cx2002grp2.stars.data.dataitem.SingleStringKeyItem;

public abstract class SingleStringKeyDatabase<ItemType extends SingleStringKeyItem> extends AbstractDatabase<ItemType> {
    private Map<String, ItemType> data = new HashMap<>();

    @Override
    public void add(ItemType item) {
        data.put(item.getKey(), item);
    }

    @Override
    public boolean del(ItemType item) {
        return del(item.getKey());
    }

    @Override
    public boolean contains(ItemType item) {
        return contains(item.getKey());
    }

    public ItemType get(String key) {
        return data.get(key);
    }

    public boolean del(String key) {
        if (contains(key)) {
            data.remove(key);
            return true;
        }
        return false;
    }

    public boolean contains(String key) {
        return data.containsKey(key);
    }

    public boolean changeKey(String oldKey, String newKey) {
        if (this.contains(newKey)) {
            return false;
        }

        if (oldKey.equals(newKey)) {
            return false;
        }

        ItemType item = get(oldKey);
        del(oldKey);
        item.setKey(newKey);
        add(item);

        return true;
    }

    public boolean changeKey(ItemType oldItem, String newKey) {
        return changeKey(oldItem.getKey(), newKey);
    }

    protected Map<String, ItemType> getDataset() {
        return this.data;
    }
 }
