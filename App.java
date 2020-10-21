import java.util.LinkedList;
import java.util.List;

import cx2002grp2.stars.MainApp;

public class App {
    public static void main(String[] args) {
        MainApp app = MainApp.getApp();
        app.initialize(args);
        app.run();
        System.out.println("Program end");
    }
}
