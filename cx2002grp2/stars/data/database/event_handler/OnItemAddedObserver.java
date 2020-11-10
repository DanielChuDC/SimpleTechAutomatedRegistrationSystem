package cx2002grp2.stars.data.database.event_handler;

@FunctionalInterface
public interface OnItemAddedObserver<ItemType> {
    void doOnItemAdded(ItemType newItem);
}
