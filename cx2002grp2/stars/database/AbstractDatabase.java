package cx2002grp2.stars.database;

import cx2002grp2.stars.MainApp;
import cx2002grp2.stars.util.OnExitObserver;

/**
 * AutoSaveData
 */
public abstract class AbstractDatabase implements OnExitObserver {
    AbstractDatabase() {
        MainApp.getApp().register(this);
    }

    @Override
    public void doOnExit() {
        saveData();
    }

    public abstract void saveData();

}
