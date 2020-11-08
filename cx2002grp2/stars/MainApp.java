package cx2002grp2.stars;

import cx2002grp2.stars.util.OnExitSubject;

/**
 * MainApp
 */
public class MainApp extends OnExitSubject {

    private static final MainApp app = new MainApp();

    public static MainApp getApp() {
        return app;
    }

    private MainApp() {

    }

    private boolean initialized = false;

    public void initialize(String[] args) {
        initialized = true;
    }

    public final void run() {
        if (!initialized) {
            throw new RuntimeException("Running MainApp without initialization.");
        }

        try {
            mainBody();
        } catch (Exception e) {
            signalOnExit();
            throw e;
        }

        signalOnExit();
    }

    private void mainBody() {
    }

    public static void main(String[] args) {
        MainApp app = MainApp.getApp();
        app.initialize(args);
        app.run();
        System.out.println("Program end");
    }
}