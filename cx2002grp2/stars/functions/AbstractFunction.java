package cx2002grp2.stars.functions;

import java.io.File;
import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Scanner;

import cx2002grp2.stars.TablePrinter;
import cx2002grp2.stars.data.dataitem.User;

/**
 * A common implementation for {@link Function}.
 */
public abstract class AbstractFunction implements Function {

    /**
     * All the {@link Function} instances which is a subclass of
     * {@link AbstractFunction}.
     */
    private static final Collection<Function> allFunctions = new ArrayList<>();

    /**
     * All the {@link AbstractFunction} instances.
     * <p>
     * All the {@link AbstractFunction} instance will be added into
     * {@link AbstractFunction#allFunctions} when constructor is called, and
     * {@link AbstractFunction#allFunctions} will be return by this method
     * 
     * @return A unmodifiable iterable which contains all the {@link Function}
     *         instances which is a subclass of {@link AbstractFunction}.
     */
    public static Iterable<Function> allFunctions() {
        return Collections.unmodifiableCollection(allFunctions);
    }

    /**
     * Initialize all functions.
     */
    public static void init() {
        // The package prefix of all function class
        String packagePrefix = "cx2002grp2.stars.functions.";

        // The folder of function class to be stored
        File funcFolder = new File("cx2002grp2/stars/functions");

        // If the path above is invalid, try another path.
        if (!funcFolder.exists()) {
            funcFolder = new File("functions");
            // If still cannot find, throw exception.
            if (!funcFolder.exists()) {
                throw new RuntimeException("Fail to find function package.");
            }
        }

        System.out.println("Function Package found: " + funcFolder.getPath());

        // For all the file in the functions package.
        for (String file : funcFolder.list()) {
            System.out.println(file);

            // For all the file ends with .java, initialize the corresponding class.
            if (file.endsWith(".java")) {
                // cut the suffix of file name to get the class name.
                String className = file.substring(0, file.length() - ".java".length());
                String fullClassName = packagePrefix + className;
                try {
                    // initialize the function class.
                    Class.forName(fullClassName);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    /**
     * Constructor which will add all the instance into
     * {@link AbstractFunction#allFunctions}
     */
    public AbstractFunction() {
        allFunctions.add(this);
    }

    /**
     * Run the function with the given user, forcefully apply accessibility
     * checking.
     * 
     * @param user the user who what to run the function.
     * @throws NullPointerException   if the user is null.
     * @throws AccessControlException if the user has no access to this function,
     *                                that is, {@link Function#accessible(User user)
     *                                this.accessible(user)} return false.
     */
    @Override
    public final void run(User user) {
        Objects.requireNonNull(user);

        if (!accessible(user)) {
            throw new AccessControlException("User " + user + " has not access to function: " + name());
        }

        implementation(user);
    }

    /**
     * A quick way to get a {@link TablePrinter}
     * 
     * @return a table printer
     */
    protected TablePrinter tbPrinter() {
        return TablePrinter.getPrinter();
    }

    /**
     * A function used to read an int.
     * <p>
     * The function will repeat asking user to input an integer until the user enter
     * a integer.
     * 
     * @param hintMsg hinting message to be printed before input.
     * @return a integer read from the user within the given range.
     */
    protected int enterInt(String hintMsg) {
        return enterInt(hintMsg, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /**
     * A function used to read an int within the given range.
     * <p>
     * The function will repeat asking user to input an integer until the user enter
     * a integer within the given range.
     * 
     * @param hintMsg hinting message to be printed before input.
     * @param minInt  the minimal value of the input range (inclusive)
     * @param maxInt  the maximal value of the input range (inclusive)
     * @return a integer read from the user within the given range.
     */
    protected int enterInt(String hintMsg, int minInt, int maxInt) {
        if (minInt > maxInt) {
            throw new IllegalArgumentException("Minimum is greater than maximum.");
        }

        String errorMsg = "Please enter an integer";

        if (minInt != Integer.MIN_VALUE && maxInt != Integer.MAX_VALUE) {
            errorMsg += " between " + minInt + " and " + maxInt;
        } else if (minInt != Integer.MIN_VALUE) {
            errorMsg += " greater than " + minInt;
        } else if (maxInt != Integer.MAX_VALUE) {
            errorMsg += " smaller than " + maxInt;
        }

        Integer ret = null;

        @SuppressWarnings("resource")
        Scanner stdin = new Scanner(System.in);

        while (true) {
            System.out.print(hintMsg);
            String input = stdin.nextLine();

            try {
                ret = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                ret = null;
            }

            if (ret == null || ret < minInt || ret > maxInt) {
                System.out.println(errorMsg);
            } else {
                break;
            }
        }

        return ret;
    }

    /**
     * The actual implementation of function without user accessibility checking.
     * 
     * @param user the user who run the function.
     */
    abstract protected void implementation(User user);

    public static void main(String[] args) {
        AbstractFunction.init();
    }
}