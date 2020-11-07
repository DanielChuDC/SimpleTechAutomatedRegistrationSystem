package cx2002grp2.stars.data.database;

import cx2002grp2.stars.MainApp;
import cx2002grp2.stars.util.OnExitObserver;

/**
 * 
 */
public abstract class AbstractDatabase<ItemType> implements OnExitObserver {
    AbstractDatabase() {
        MainApp.getApp().register(this);
    }

    @Override
    public void doOnExit() {
        saveData();
    }

    protected abstract void loadData();

    protected abstract void saveData();

    public abstract boolean add(ItemType item);

    public abstract boolean del(ItemType item);

    public abstract boolean contains(ItemType item);
}
