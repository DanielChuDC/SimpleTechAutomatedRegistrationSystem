package cx2002grp2.stars.data.database;

@FunctionalInterface
public interface OnItemAddedObserver<ItemType> {
    void doOnItemAdded(ItemType newItem);
}
