package cx2002grp2.stars.data.database;

import java.util.Collection;
import java.util.HashSet;

import cx2002grp2.stars.MainApp;

/**
 * 
 */
public abstract class AbstractDatabase<ItemType>
        implements Database<ItemType> {

    public AbstractDatabase() {
        MainApp.getApp().addOnExitObserver(this);
    }

    /**
     * 
     */
    protected abstract void loadData();

    /**
     * 
     */
    protected abstract void saveData();

    @Override
    public void doOnExit() {
        saveData();
    }
    
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
     * 
     * @param deletedItem
     */
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

    /**
     * 
     * @param addedItem
     */
    protected void signalItemAdded(ItemType addedItem) {
        onItemDeletedObservers.forEach(ob -> ob.doOnItemDeleted(addedItem));
    }
}
