package cx2002grp2.stars.data.dataitem;

import cx2002grp2.stars.data.database.SingleKeyDatabase;
import java.util.Comparator;

/**
 * An item that can be identified with one key value.
 * <p>
 * In our project, only {@link Registration} is not a single key item, because
 * it must be identify with both the student and the course index/course code.
 */
public interface SingleKeyItem<KeyType> {
    /**
     * The method used to get the key value of an item.
     * 
     * @return the key value of the item.
     */
    public KeyType getKey();

    /**
     * The method used to change the key value of an item.
     * <p>
     * The concrete method don't need to check database issue like checking whether
     * the new key is an existing key in the database. The relevant issue shall be
     * handled by the database classes
     * <p>
     * This method is supposed to be called by database class object only. All the
     * other objects should use the
     * {@link SingleKeyDatabase#changeKey(Comparable, Comparable)
     * SingleKeyDatabase.changeKey} methods in the database class to change the key
     * value of an item.
     * 
     * @param newKey the new key used to replace the old key.
     * @throws NullPointerException if the new key is null.
     */
    public void setKey(KeyType newKey);

    /**
     * get the comparator of corresponding SingleKeyItem.
     * 
     * @param <KeyType> the type of compared SingleKeyItem
     * @return the comparator of corresponding SingleKeyItem
     */
    public static <KeyType extends Comparable<KeyType>> Comparator<? extends SingleKeyItem<KeyType>> getComparatorByKey() {
        return (item1, item2) -> item1.getKey().compareTo(item2.getKey());
    }

    /**
     * get self-defined comparator of corresponding SingleKeyItem.
     * 
     * @param <KeyType>     the type of compared SingleKeyItem
     * @param keyComparator the self-defined comparator of compared SingleKeyItem
     * @return the comparator of corresponding SingleKeyItem
     */
    public static <KeyType> Comparator<? extends SingleKeyItem<KeyType>> getComparatorByKey(
            Comparator<? super KeyType> keyComparator) {
        return (item1, item2) -> keyComparator.compare(item1.getKey(), item2.getKey());
    }
}
