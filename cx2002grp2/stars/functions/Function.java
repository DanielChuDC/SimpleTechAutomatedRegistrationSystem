package cx2002grp2.stars.functions;

import java.security.AccessControlException;

import cx2002grp2.stars.data.dataitem.User;

/**
 * The common interface for all the functions.
 */
public interface Function {
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
        return AbstractFunction.allFunctions();
    }

    /**
     * Run the function with the given user.
     * 
     * @param user the user who what to run the function.
     * @throws NullPointerException   if the user is null.
     * @throws AccessControlException if the user has no access to this function,
     *                                that is, {@link Function#accessible(User user)
     *                                this.accessible(user)} return false.
     */
    public void run(User user);

    /**
     * Check whether the given user has access to the Function.
     * <p>
     * This method shall be implemented as final method, so that the accessibility
     * to the function cannot be change by any subclass.
     * 
     * @param user the user to check the accessiblility.
     * @return true if the user has access to the function. Otherwise, return false.
     */
    abstract public boolean accessible(User user);

    /**
     * The name of the function.
     * 
     * @return the name of the function.
     */
    abstract public String name();
}
