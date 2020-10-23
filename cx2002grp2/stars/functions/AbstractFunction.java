package cx2002grp2.stars.functions;

import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.List;

import cx2002grp2.stars.data.User;

/**
 * Function
 */
public abstract class AbstractFunction {

    static final List<AbstractFunction> allFunctions = new ArrayList<>();

    static Iterable<AbstractFunction> getAllFunctions() {
        return allFunctions;
    }

    AbstractFunction() {
        allFunctions.add(this);
    }

    public final void run(User user) {
        if (!accessable(user)) {
            throw new AccessControlException("User " + user + " has not access to function: " + name());
        }

        implementation(user);
    }

    abstract protected void implementation(User user);

    abstract public boolean accessable(User user);

    abstract public String name();

    @Override
    public String toString() {
        return name();
    }
}