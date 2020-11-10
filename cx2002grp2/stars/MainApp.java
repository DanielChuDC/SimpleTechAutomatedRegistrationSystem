package cx2002grp2.stars;

import java.util.Collection;
import java.util.HashSet;

import cx2002grp2.stars.data.database.CourseDB;
import cx2002grp2.stars.data.database.CourseIndexDB;
import cx2002grp2.stars.data.database.RegistrationDB;
import cx2002grp2.stars.data.database.StudentDB;
import cx2002grp2.stars.data.database.UserDB;
import cx2002grp2.stars.util.OnExitObserver;
import cx2002grp2.stars.util.OnExitSubject;

/**
 * MainApp
 */
public class MainApp implements OnExitSubject {

    private static final MainApp app = new MainApp();

    public static MainApp getApp() {
        return app;
    }

    private MainApp() {

    }

    private boolean initialized = false;

    /**
     * 
     * @param args
     */
    public void initialize(String[] args) {
        CourseDB.getDB();
        CourseIndexDB.getDB();
        RegistrationDB.getDB();
        StudentDB.getDB();
        UserDB.getDB();

        initialized = true;
    }

    /**
     * 
     */
    public final void run() {
        if (!initialized) {
            throw new RuntimeException("Running MainApp without initialization.");
        }

        try {
            mainBody();
        } finally {
            signalOnExit();
        }

    }

    private void mainBody() {
    }


    
    Collection<OnExitObserver> onExitbservers = new HashSet<>();

    @Override
    public void addOnExitObserver(OnExitObserver observer) {
        this.onExitbservers.add(observer);
    }

    @Override
    public void delOnExitObserver(OnExitObserver observer) {
        this.onExitbservers.remove(observer);
    }

    private void signalOnExit() {
        onExitbservers.forEach(ob->ob.doOnExit());
    }
    
    public static void main(String[] args) {
        MainApp app = MainApp.getApp();
        app.initialize(args);
        app.run();
        System.out.println("Program end");
    }
}