package cx2002grp2.stars.database;

/**
 * Interface for observer of item addition event.
 */
@FunctionalInterface
public interface OnItemAddedObserver<ItemType> {
    /**
     * Action to be done when an item is added.
     * 
     * @param newItem the newly added item.
     */
    void doOnItemAdded(ItemType newItem);
}
