package cx2002grp2.stars.functions;

import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

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
     * (Shall be) all the {@link Function} instances.
     * <p>
     * All the {@link AbstractFunction} instance will be added into
     * {@link AbstractFunction#allFunctions} when constructor is called.
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
        allFunctions.add(this);
    }

    /**
     * Run the function with the given user, forcefully apply accessibility checking.
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
     * The actual implementation of function without user accessibility checking.
     * 
     * @param user the user who run the function.
     */
    abstract protected void implementation(User user);
}