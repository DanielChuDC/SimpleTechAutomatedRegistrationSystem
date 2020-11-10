package cx2002grp2.stars.data.database;

public interface OnItemDeletedSubject<ItemType> {
    void addOnItemDeletedObserver(OnItemDeletedObserver<? super ItemType> observer);
    void delOnItemDeletedObserver(OnItemDeletedObserver<? super ItemType> observer);
}
