package cx2002grp2.stars.data.database.event_handler;

@FunctionalInterface
public interface OnKeyChangedObserver<KeyType, ItemType> {
    void doOnKeyChange(KeyType oldKey, ItemType newItem);
}
