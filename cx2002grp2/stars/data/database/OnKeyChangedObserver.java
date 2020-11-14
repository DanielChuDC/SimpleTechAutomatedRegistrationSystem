package cx2002grp2.stars.data.database;

/**
 * Interface for observer of item key changing event.
 */
@FunctionalInterface
public interface OnKeyChangedObserver<KeyType, ItemType> {
    /**
     * The action to be done when the key of an item changed.
     * 
     * @param oldKey the original key.
     * @param newItem the item with the new key value after change.
     */
    void doOnKeyChange(KeyType oldKey, ItemType newItem);
}
