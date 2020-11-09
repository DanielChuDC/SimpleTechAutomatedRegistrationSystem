package cx2002grp2.stars.data.database;

import cx2002grp2.stars.MainApp;
import cx2002grp2.stars.util.OnExitObserver;

/**
 * 
 */
public abstract class AbstractDatabase<ItemType> implements OnExitObserver {
    public AbstractDatabase() {
        MainApp.getApp().register(this);
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
}
