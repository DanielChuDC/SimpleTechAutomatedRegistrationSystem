package cx2002grp2.stars.data;

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

    public abstract void saveData();

    public abstract void add(ItemType item);

    public abstract void del(ItemType item);

}
