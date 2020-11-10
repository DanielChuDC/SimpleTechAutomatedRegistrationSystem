package cx2002grp2.stars.functions;

import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.List;

import cx2002grp2.stars.data.dataitem.User;

/**
 * 
 */
public abstract class Function {

    private static final List<Function> allFunctions = new ArrayList<>();

    public static Iterable<Function> getAllFunctions() {
        return allFunctions;
    }

    public Function() {
        allFunctions.add(this);
    }

    public final void run(User user) {
        if (!accessible(user)) {
            throw new AccessControlException("User " + user + " has not access to function: " + name());
        }

        implementation(user);
    }

    abstract protected void implementation(User user);

    abstract public boolean accessible(User user);

    abstract public String name();

    @Override
    public String toString() {
        return name();
    }
}