package cx2002grp2.stars.functions;

import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Scanner;

import cx2002grp2.stars.Configs;
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
        System.err.println("Constructing: "+this.getClass().getSimpleName());
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
     * The actual implementation of function without user accessibility checking.
     * 
     * @param user the user who run the function.
     */
    abstract protected void implementation(User user);

    public static void main(String[] args) {
        Function.init();
        System.out.println(Function.allFunctions());
    }
}