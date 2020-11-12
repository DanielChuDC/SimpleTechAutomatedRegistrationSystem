package cx2002grp2.stars.util;

/**
 * Interface for observer of app exiting event.
 */
@FunctionalInterface
public interface OnExitObserver {
    /**
     * The action to be done when the app is exiting.
     */
    public void doOnExit();
}
