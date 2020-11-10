package cx2002grp2.stars.data.database.event_handler;

public interface OnItemAddedSubject<ItemType> {
    void addOnItemAddedObserver(OnItemAddedObserver<? super ItemType> observer);
    void delOnItemAddedObserver(OnItemAddedObserver<? super ItemType> observer);
}
