package cx2002grp2.stars.data.database;

/**
 * The interface of subject of item deletion event.
 */
public interface OnItemDeletedSubject<ItemType> {
    /**
     * Add a observer for item deletion event.
     * 
     * @param observer a observer of item deletion event.
     */
    void addOnItemDeletedObserver(OnItemDeletedObserver<? super ItemType> observer);
    /**
     * Delete a observer for item deletion event.
     * 
     * @param observer a observer of item deletion event.
     */
    void delOnItemDeletedObserver(OnItemDeletedObserver<? super ItemType> observer);
}