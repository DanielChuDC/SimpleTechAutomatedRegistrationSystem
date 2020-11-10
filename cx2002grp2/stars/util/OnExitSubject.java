package cx2002grp2.stars.util;

/**
 * 
 */
public interface OnExitSubject {
    public void addOnExitObserver(OnExitObserver observer);
    public void delOnExitObserver(OnExitObserver observer);
}
