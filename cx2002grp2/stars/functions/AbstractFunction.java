package cx2002grp2.stars.functions;

import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Scanner;

import cx2002grp2.stars.Configs;
import cx2002grp2.stars.CourseAllocator;
import cx2002grp2.stars.TablePrinter;
import cx2002grp2.stars.data.dataitem.User;
import cx2002grp2.stars.data.dataitem.User.Domain;

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
     * Constructor which will add all the instance into
     * {@link AbstractFunction#allFunctions}
     */
    public AbstractFunction() {
        System.err.println("Constructing: " + this.getClass().getSimpleName());
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

    private Scanner stdinSc = new Scanner(System.in);

    /**
     * A quick way to get a Scanner for System.in
     * <p>
     * It is the same to {@link sc()}
     * 
     * @return a scanner of System.in
     */
    protected Scanner stdin() {
        return stdinSc;
    }

    /**
     * A quick way to get a Scanner for System.in.
     * <p>
     * It is the same to {@link stdin()}
     * 
     * @return a scanner of System.in
     */
    protected Scanner sc() {
        return stdin();
    }

    /**
     * A quick way to get a {@link TablePrinter}
     * 
     * @return an instance {@link TablePrinter}
     */
    protected TablePrinter tbPrinter() {
        return TablePrinter.getPrinter();
    }

    /**
     * A quick way to get an {@link CourseAllocator}
     * 
     * @return an instance of {@link CourseAllocator}
     */
    protected CourseAllocator allocator() {
        return CourseAllocator.getInstance();
    }

    /**
     * Ask user to enter yes or no.
     * 
     * @param hintMsg hinting message to be print before asking.
     * @return true if user enter yes, false if user enter no.
     */
    boolean askYesNo(String hintMsg) {
        System.out.println(hintMsg);
        while (true) {
            System.out.print("Please enter yes(y) or no(n): ");
            String input = sc().nextLine();
            input = input.trim().toLowerCase();
            switch (input) {
                case "y":
                case "yes":
                    return true;
                case "n":
                case "no":
                    return false;
            }
        }
    }

    /**
     * Ask user to enter yes of no before apply changes.
     * 
     * @return true if user enter yes, false if user enter no.
     */
    boolean askForApplyChange() {
        return askYesNo("Apply changes?");
    }

    /**
     * Common accessibility checking for student's functions.
     * <p>
     * student access time period and user domain will be checked.
     * 
     * @param user the user to be checked.
     * @return true if the user can access function. Otherwise, return false.
     */
    protected boolean normalStudentAccessible(User user) {
        return Configs.isStudentAccessTime() && user.getDomain() == Domain.STUDENT;
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
     * Formatting string for enum value list used by
     * {@link AbstractFunction#selectEnum(String, Enum[])}.
     */
    private static final String ENUM_LIST_FORMAT = "%-2d for %s\n";

    /**
     * Ask user to select one enum values for an array of enum.
     * 
     * @param <T>     the type of enum to be selected.
     * @param hintMsg the hinting message to be print before asking user to input
     * @param values  the values in which the user will select.
     * @return the enum value selected by user.
     */
    protected <T extends Enum<T>> T selectEnum(String hintMsg, T[] values) {
        System.out.println(hintMsg);
        for (int i = 0; i < values.length; ++i) {
            System.out.printf(ENUM_LIST_FORMAT, i + 1, values[i]);
        }
        int selection = enterInt("Enter your choice: ", 1, values.length);
        return values[selection - 1];
    }

    /**
     * The actual implementation of function without user accessibility checking.
     * 
     * @param user the user who run the function.
     */
    abstract protected void implementation(User user);
}