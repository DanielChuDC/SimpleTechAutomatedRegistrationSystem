package cx2002grp2.stars.data.database;

@FunctionalInterface
public interface OnItemDeletedObserver<ItemType> {
    void doOnItemDeleted(ItemType deletedItem);
}
