package cx2002grp2.stars.data.database.event_handler;

@FunctionalInterface
public interface OnItemDeletedObserver<ItemType> {
    void doOnItemDeleted(ItemType deletedItem);
}
