package cx2002grp2.stars.database;

/**
 * The interface of subject of item key change event.
 */
public interface OnKeyChangedSubject<KeyType, ItemType> {
    /**
     * Add a observer for item key change event.
     * 
     * @param observer a observer of item key change event.
     */
    void addOnKeyChangedObserver(OnKeyChangedObserver<? super KeyType, ? super ItemType> observer);
    /**
     * Delete a observer for item key change event.
     * 
     * @param observer a observer of item key change event.
     */
    void delOnKeyChangedObserver(OnKeyChangedObserver<? super KeyType, ? super ItemType> observer);
}