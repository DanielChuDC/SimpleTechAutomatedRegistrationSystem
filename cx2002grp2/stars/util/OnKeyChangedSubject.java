package cx2002grp2.stars.util;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 */
public abstract class OnKeyChangedSubject {
    
    Set<OnKeyChangedObserver> observers = new HashSet<>();
    
    public void register(OnKeyChangedObserver observer) {
        this.observers.add(observer);
    }
    
    public void unregister(OnKeyChangedObserver observer) {
        this.observers.remove(observer);
    }

    protected void signalOnKeyChanged(int databaseId, String oldKey, String newKey) {
        for (OnKeyChangedObserver ob: observers) {
            ob.doOnKeyChanged(databaseId, oldKey, newKey);
        }
    }
}
