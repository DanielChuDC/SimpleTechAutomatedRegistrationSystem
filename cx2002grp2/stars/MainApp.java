package cx2002grp2.stars;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import cx2002grp2.stars.data.database.CourseDB;
import cx2002grp2.stars.data.database.CourseIndexDB;
import cx2002grp2.stars.data.database.UserDB;
import cx2002grp2.stars.data.dataitem.User;
import cx2002grp2.stars.data.dataitem.User.Domain;
import cx2002grp2.stars.functions.Function;
import cx2002grp2.stars.data.database.StudentDB;
import cx2002grp2.stars.data.database.RegistrationDB;
import cx2002grp2.stars.util.OnExitObserver;
import cx2002grp2.stars.util.OnExitSubject;

/**
 * The main entry of the application.
 * <p>
 * It is implemented with Singleton pattern.
 */
public class MainApp implements OnExitSubject {

    /**
     * A unique instance of MainApp.
     * <p>
     * It is for Singleton pattern.
     */
    private static final MainApp app = new MainApp();

    /**
     * Get the unique instance of main app.
     * <p>
     * It is for Singleton pattern.
     * 
     * @return the unique instance of main app.
     */
    public static MainApp getApp() {
        return app;
    }

    /**
     * Private constructor reserve for Singleton pattern.
     */
    private MainApp() {

    }

    /**
     * Whether the app is initialized.
     */
    private boolean initialized = false;

    /**
     * Initialize the app.
     * <p>
     * All the initialization function for all the tools will be put here.
     * 
     * @param args the argument passed to main function.
     */
    public void initialize(String[] args) {
        // Force the initialization order of database to avoid long run recursive
        // initialization call
        CourseDB.getDB();
        CourseIndexDB.getDB();
        UserDB.getDB();
        StudentDB.getDB();
        RegistrationDB.getDB();

        // Initialize configurations.
        Configs.init();

        initialized = true;
    }

    /**
     * Run the main app.
     * <p>
     * Initialization checking will be performed.
     * 
     * @throws RuntimeException if the main app is run without initialization, that
     *                          is, {@link MainApp#run()} is called before
     *                          {@link MainApp#initialize(String[]) is called.}
     */
    public final void run() {
        // Initialization check
        if (!initialized) {
            throw new RuntimeException("Running MainApp without initialization.");
        }

        // Using try so that the signaling the termination will happen anyway.
        try {
            mainBody();
        } finally {
            signalOnExit();
        }

    }

    /**
     * The formatting string used for printing function list.
     */
    private static final String FUNC_LIST_FMT = "%-3s : %s\n";

    /**
     * The main function body.
     */
    private void mainBody() {
        System.out.println("====================================\n" + "        Welcome to MySTARS.         \n"
                + "====================================\n");

        Authenticator auth = Authenticator.getInstance();
        Scanner stdin = new Scanner(System.in);

        while (true) {
            // Login
            System.out.println("Login: ");
            User user = auth.login();
            if (user == null) {
                System.out.println("Login failed.");
            }

            // Print warning if student login out of access period.
            if (user.getDomain() == Domain.STUDENT && !Configs.isStudentAccessTime()) {
                String beginTime = Configs.getAccessStartTime().format(DateTimeFormatter.RFC_1123_DATE_TIME);
                String endTime = Configs.getAccessEndTime().format(DateTimeFormatter.RFC_1123_DATE_TIME);

                System.out.println("Warning: it is not student access period. The access period is:");
                System.out.printf("%s ~ %s\n", beginTime, endTime);
            }

            // Get available function list.
            List<Function> funcList = auth.accessibleFunctions(user);

            while (true) {
                // Print available function list.
                System.out.printf(FUNC_LIST_FMT, "No.", "Function");
                System.out.printf(FUNC_LIST_FMT, 0, "Terminate Program"); // Terminating function is always available.
                for (int i = 0; i < funcList.size(); ++i) {
                    System.out.printf(FUNC_LIST_FMT, i + 1, funcList.get(i).name());
                }

                // User selection
                System.out.println("Please enter the No. of function to be run.");
                int selection = -1;
                while (true) {
                    System.out.print("Your selection: ");

                    String inStr = stdin.nextLine();

                    try {
                        selection = Integer.parseInt(inStr);
                    } catch (NumberFormatException e) {
                        selection = -1;
                    }

                    if (selection < 0 || selection > funcList.size()) {
                        if (funcList.isEmpty()) {
                            System.out.println("You can only enter 0 and terminate the program.");
                        } else {
                            System.out.println("Please enter an integer between 0 and " + funcList.size());
                        }
                    } else {
                        break;
                    }
                }

                // User selected to terminate the program. Break the main loop.
                if (selection == 0) {
                    break;
                }

                System.out.println("You selected function: "+funcList.get(selection-1).name());

                funcList.get(selection-1).run(user);
            }
        }
    }


    /**
     * The collection of observers that are observing on the program termination
     * event.
     */
    Collection<OnExitObserver> onExitObservers = new HashSet<>();

    @Override
    public void addOnExitObserver(OnExitObserver observer) {
        this.onExitObservers.add(observer);
    }

    @Override
    public void delOnExitObserver(OnExitObserver observer) {
        this.onExitObservers.remove(observer);
    }

    /**
     * Notify all the observers that are observing on the program termination event.
     */
    private void signalOnExit() {
        onExitObservers.forEach(ob -> ob.doOnExit());
    }

    /**
     * An example main function.
     * 
     * @param args arguments passed in.
     */
    public static void main(String[] args) {
        MainApp app = MainApp.getApp();
        app.initialize(args);
        app.run();
        System.out.println("Program end");
    }
}