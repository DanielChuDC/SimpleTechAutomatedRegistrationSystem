package cx2002grp2.stars.util;

import java.util.HashSet;
import java.util.Set;

/**
 * abstract class OnExitSubject 
 */
public abstract class OnExitSubject {
    Set<OnExitObserver> observers = new HashSet<>();

    public void register(OnExitObserver observer) {
        this.observers.add(observer);
    }

    public void unregister(OnExitObserver observer) {
        this.observers.remove(observer);
    }

    public void signalOnExit() {
        for (OnExitObserver ob: observers) {
            ob.doOnExit();
        }
    }
}
