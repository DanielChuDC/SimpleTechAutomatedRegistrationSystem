package cx2002grp2.stars.data.dataitem;

import java.util.Comparator;

public abstract class SingleKeyItem<KeyType> {
    /**
     * The method used to get the key value of an item.
     * 
     * @return the key value of the item.
     */
    public abstract KeyType getKey();

    /**
     * The method used to change the key value of an item.
     * <p>
     * The concrete method don't need to check database issue like checking whether
     * the new key is an existing key in the database. The relevant issue shall be
     * handled by the database classes
     * <p>
     * This method is supposed to be called by database class object only. All the
     * other objects should use the {@link SingleKeyDatabase.changeKey) methods in
     * the databse class to change the key value of an item.
     * 
     * @param newKey the new key used to replace the old key.
     */
    public abstract void setKey(KeyType newKey);

    /**
     * 
     * @param <KeyType>
     * @return
     */
    public static <KeyType extends Comparable<KeyType>>
    Comparator<SingleKeyItem<KeyType>> getComparatorByKey() {
        return (item1, item2) -> item1.getKey().compareTo(item2.getKey());
    }

    /**
     * 
     * @param <KeyType>
     * @return
     */
    public static <KeyType extends Comparable<KeyType>>
    Comparator<SingleKeyItem<KeyType>> getComparatorByKey(Comparator<KeyType> keyComparator) {
        return (item1, item2) -> keyComparator.compare(item1.getKey(), item2.getKey());
    }
}
