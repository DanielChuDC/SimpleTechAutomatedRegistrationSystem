package cx2002grp2.stars.data.database;

/**
 * Interface for observer of item deletion event.
 */
@FunctionalInterface
public interface OnItemDeletedObserver<ItemType> {
    /**
     * Action to be done when an item is deleted.
     * 
     * @param deletedItem the deleted item.
     */
    void doOnItemDeleted(ItemType deletedItem);
}