package cx2002grp2.stars.data.database;

import java.util.Collection;
import java.util.HashSet;

import cx2002grp2.stars.MainApp;
import cx2002grp2.stars.util.OnExitObserver;
import cx2002grp2.stars.data.database.event_handler.OnItemAddedObserver;
import cx2002grp2.stars.data.database.event_handler.OnItemAddedSubject;
import cx2002grp2.stars.data.database.event_handler.OnItemDeletedObserver;
import cx2002grp2.stars.data.database.event_handler.OnItemDeletedSubject;

/**
 * 
 */
public abstract class AbstractDatabase<ItemType>
        implements OnExitObserver, OnItemDeletedSubject<ItemType>, OnItemAddedSubject<ItemType>, Iterable<ItemType> {
    public AbstractDatabase() {
        MainApp.getApp().addOnExitObserver(this);

    }

    @Override
    public void doOnExit() {
        saveData();
    }

    /**
     * 
     */
    protected abstract void loadData();

    /**
     * 
     */
    protected abstract void saveData();

    /**
     * 
     * @param item
     * @return
     */
    public abstract boolean add(ItemType item);

    /**
     * 
     * @param item
     * @return
     */
    public abstract boolean del(ItemType item);

    /**
     * 
     * @param item
     * @return
     */
    public abstract boolean contains(ItemType item);

    
    private Collection<OnItemDeletedObserver<? super ItemType>> onItemDeletedObservers = new HashSet<>();

    @Override
    public void addOnItemDeletedObserver(OnItemDeletedObserver<? super ItemType> observer) {
        onItemDeletedObservers.add(observer);
    }

    @Override
    public void delOnItemDeletedObserver(OnItemDeletedObserver<? super ItemType> observer) {
        onItemDeletedObservers.remove(observer);
    }

    protected void signalItemDeleted(ItemType deletedItem) {
        onItemDeletedObservers.forEach(ob -> ob.doOnItemDeleted(deletedItem));
    }


    private Collection<OnItemAddedObserver<? super ItemType>> onItemAddedObservers = new HashSet<>();

    @Override
    public void addOnItemAddedObserver(OnItemAddedObserver<? super ItemType> observer) {
        onItemAddedObservers.add(observer);
    }

    @Override
    public void delOnItemAddedObserver(OnItemAddedObserver<? super ItemType> observer) {
        onItemAddedObservers.remove(observer);
    }

    protected void signalItemAdded(ItemType addedItem) {
        onItemDeletedObservers.forEach(ob -> ob.doOnItemDeleted(addedItem));
    }
}
