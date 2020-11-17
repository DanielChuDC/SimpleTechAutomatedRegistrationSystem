package cx2002grp2.stars.data.database;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import cx2002grp2.stars.MainApp;
import cx2002grp2.stars.util.OnExitObserver;

/**
 * A common implementation for all database.
 * <p>
 * Implemented functions:
 * <ul>
 * <li>saving data when app terminates, either in normal way or caused by
 * exception.
 * <li>Implement interface {@link OnItemAddedSubject
 * OnItemAddedSubject&lt;ItemType&gt;} and the corresponding signaling method
 * {@link AbstractDatabase#signalItemAdded(Object)}
 * <li>Implement interface {@link OnItemDeletedSubject
 * OnItemDeletedSubject&lt;ItemType&gt;} and the corresponding signaling method
 * {@link AbstractDatabase#signalItemDeleted(Object)}
 * </ul>
 * @see AbstractSingleKeyDatabase
 */
public abstract class AbstractDatabase<ItemType> implements Database<ItemType>, OnExitObserver {

    /**
     * Construct a database observing on the exiting event of MainApp.
     */
    public AbstractDatabase() {
        System.err.println("Constructing: "+this.getClass().getSimpleName());
        MainApp.getApp().addOnExitObserver(this);
    }

    /**
     * Load data from files into database.
     */
    protected abstract void loadData();

    /**
     * Save data in database into file.
     */
    @Override
    public abstract void saveData();

    /**
     * {@inheritDoc}
     * <p>
     * Saving data when the program terminate.
     */
    @Override
    public void doOnExit() {
        saveData();
        System.err.println(this.getClass().getSimpleName()+": data automatically saved");
    }

    public ItemType pickOne() {
        Random r = new Random();
        Iterator<ItemType> iter = iterator();

        for (int i = r.nextInt(size()); i > 0; --i) {
            iter.next();
        }

        return iter.next();
    }

    /**
     * The collection of observers that are observing on the item deletion event of
     * this database.
     */
    private Collection<OnItemDeletedObserver<? super ItemType>> onItemDeletedObservers = new HashSet<>();

    @Override
    public void addOnItemDeletedObserver(OnItemDeletedObserver<? super ItemType> observer) {
        onItemDeletedObservers.add(observer);
    }

    @Override
    public void delOnItemDeletedObserver(OnItemDeletedObserver<? super ItemType> observer) {
        onItemDeletedObservers.remove(observer);
    }

    /**
     * Notify all the observers that are observing on the item deletion event of
     * this database.
     * 
     * @param deletedItem the deleted item that are passed to all the observer.
     */
    protected void signalItemDeleted(ItemType deletedItem) {
        onItemDeletedObservers.forEach(ob -> ob.doOnItemDeleted(deletedItem));
    }

    /**
     * The collection of observers that are observing on the item addition event of
     * this database.
     */
    private Collection<OnItemAddedObserver<? super ItemType>> onItemAddedObservers = new HashSet<>();

    @Override
    public void addOnItemAddedObserver(OnItemAddedObserver<? super ItemType> observer) {
        onItemAddedObservers.add(observer);
    }

    @Override
    public void delOnItemAddedObserver(OnItemAddedObserver<? super ItemType> observer) {
        onItemAddedObservers.remove(observer);
    }

    /**
     * Notify all the observers that are observing on the item addition event of
     * this database.
     * 
     * @param addedItem the added item that are passed to all the observer.
     */
    protected void signalItemAdded(ItemType addedItem) {
        onItemDeletedObservers.forEach(ob -> ob.doOnItemDeleted(addedItem));
    }
}
