package cx2002grp2.stars.util;

/**
 * Interface for subject of app exiting event.
 */
public interface OnExitSubject {
    /**
     * Add a observer for app exiting event.
     * 
     * @param observer a observer of app exiting event.
     */
    public void addOnExitObserver(OnExitObserver observer);

    /**
     * Delete a observer for app exiting event.
     * 
     * @param observer a observer of app exiting event.
     */
    public void delOnExitObserver(OnExitObserver observer);
}
