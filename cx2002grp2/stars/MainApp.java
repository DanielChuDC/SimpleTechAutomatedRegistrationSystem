package cx2002grp2.stars;

import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashSet;
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
     * formatter for access time period.
     */
    private static final DateTimeFormatter ACCESS_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss");

    /**
     * The formatting string used for printing function list.
     */
    private static final String FUNC_LIST_FMT = "%3s: %s\n";

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
     * Private constructor reserved for Singleton pattern.
     */
    private MainApp() {

    }

    /**
     * Whether the app is initialized.
     */
    private boolean initialized = false;

    /**
     * Whether the app is exiting.
     */
    private boolean exiting = false;

    /**
     * Print a break line with content in the middle
     * 
     * @param content the content at the middle of break line
     */
    private void printBreakLine(String content) {
        TablePrinter.getPrinter().printBreakLine(content, '=');
    }

    /**
     * Initialize the app.
     * <p>
     * All the initialization function for all the tools will be put here.
     * 
     * @param args the argument passed to main function.
     */
    public void initialize(String[] args) {
        // Initialize all functions
        Function.init();

        // Initialize configurations.
        Configs.init();

        // Force the initialization order of database to avoid long run recursive
        // initialization call
        CourseDB.getDB();
        CourseIndexDB.getDB();
        UserDB.getDB();
        StudentDB.getDB();
        RegistrationDB.getDB();

        // signal on exit when the virtual machine is terminating.
        Runtime.getRuntime().addShutdownHook(new Thread(this::signalOnExit));

        initialized = true;
    }

    /**
     * Run the main app.
     * <p>
     * Initialization checking will be performed.
     * 
     * @throws RuntimeException if the main app is run without initialization, that
     *                          is, {@link MainApp#run()} is called before
     *                          {@link MainApp#initialize(String[])} is called.
     */
    public final void run() {
        // Initialization check
        if (!initialized) {
            throw new RuntimeException("Running MainApp without initialization.");
        }

        mainBody();
    }

    /**
     * The main function body.
     */
    private void mainBody() {

        Authenticator auth = Authenticator.getInstance();

        @SuppressWarnings("resource")
        Scanner stdin = new Scanner(System.in);

        printBreakLine("");
        printBreakLine("Welcome to MySTARS.");
        printBreakLine("");
        System.out.println();

        User user = null;
        // Login process
        while (true) {
            // Login
            printBreakLine("Login");
            user = auth.login();
            if (user == null) {
                System.out.println("Login Failed");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                continue;
            }
            printBreakLine("Login Successfully");
            break;
        }

        // Print warning if student login out of access period.
        if (user.getDomain() == Domain.STUDENT && !Configs.isStudentAccessTime()) {
            String beginTime = Configs.getAccessStartTime().format(ACCESS_TIME_FORMATTER);
            String endTime = Configs.getAccessEndTime().format(ACCESS_TIME_FORMATTER);

            printBreakLine("WARNING");
            System.out.println("It is not student access period. Registration functions are not available.");
            System.out.printf("The access period is: %s ~ %s\n", beginTime, endTime);
            printBreakLine("");
        }

        // Get available function list.
        List<Function> funcList = auth.accessibleFunctions(user);

        while (true) {
            // Print available function list.
            System.out.println("Available function list: ");
            System.out.printf(FUNC_LIST_FMT, 0, "Logout"); // Logout function is always available.
            for (int i = 0; i < funcList.size(); ++i) {
                System.out.printf(FUNC_LIST_FMT, i + 1, funcList.get(i).name());
            }
            System.out.println();

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
                        System.out.println("You can only enter 0 and logout.");
                    } else {
                        System.out.println("Please enter an integer between 0 and " + funcList.size());
                    }
                } else {
                    break;
                }
            }

            // User selected to logout. Break the main loop.
            if (selection == 0) {
                printBreakLine("Logout");
                break;
            }

            System.out.println("You selected function: " + funcList.get(selection - 1).name());
            printBreakLine("Function Starts");
            funcList.get(selection - 1).run(user);
            System.out.println();
            printBreakLine("Function Finished");
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
        // one program can only exit once
        if (!exiting) {
            exiting = true;
            printBreakLine("Exiting");
            onExitObservers.forEach(ob -> ob.doOnExit());
        }
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