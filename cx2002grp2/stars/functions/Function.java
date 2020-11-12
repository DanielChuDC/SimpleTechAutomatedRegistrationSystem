package cx2002grp2.stars.functions;

import cx2002grp2.stars.data.dataitem.User;

/**
 * The common interface for all the functions.
 */
public interface Function {
    /**
     * All the {@link Function} instances.
     * <p>
     * More exactly, return all the {@link AbstractFunction} instance.
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
