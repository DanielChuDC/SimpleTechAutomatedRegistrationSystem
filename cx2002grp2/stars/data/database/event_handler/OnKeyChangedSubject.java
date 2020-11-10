package cx2002grp2.stars.data.database.event_handler;

public interface OnKeyChangedSubject<KeyType, ItemType> {
    void addOnKeyChangedObserver(OnKeyChangedObserver<? super KeyType, ? super ItemType> observer);
    void delOnKeyChangedObserver(OnKeyChangedObserver<? super KeyType, ? super ItemType> observer);
}
