package cx2002grp2.stars.database;

/**
 * The interface of subject of item addition event.
 */
public interface OnItemAddedSubject<ItemType> {
    /**
     * Add a observer for item addition event.
     * 
     * @param observer a observer of item addition event.
     */
    void addOnItemAddedObserver(OnItemAddedObserver<? super ItemType> observer);

    /**
     * Delete a observer for item addition event.
     * 
     * @param observer a observer of item addition event.
     */
    void delOnItemAddedObserver(OnItemAddedObserver<? super ItemType> observer);
}
