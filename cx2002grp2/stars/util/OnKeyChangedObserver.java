package cx2002grp2.stars.util;

/**
 * 
 */
public interface OnKeyChangedObserver {
    void doOnKeyChanged(int databaseId, String oldKey, String newKey);
}
