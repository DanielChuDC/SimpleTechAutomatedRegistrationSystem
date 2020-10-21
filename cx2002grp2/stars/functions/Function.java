package cx2002grp2.stars.functions;

import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.List;

import cx2002grp2.stars.data.User;

/**
 * Function
 */
public abstract class Function {

    static final List<Function> allFunctions = new ArrayList<>();

    static List<Function> getAllFunctions() {
        // Return the copy of list to avoid unintended modification.
        return allFunctions.subList(0, allFunctions.size());
    }

    Function() {
        allFunctions.add(this);
    }

    public final void run(User user) {
        if (!accessable(user)) {
            throw new AccessControlException("User has not access to function: " + toString());
            // throw new RuntimeException("User has not access to function: " + toString());
        }

        implementation(user);
    }

    abstract protected void implementation(User user);

    abstract public boolean accessable(User user);

    @Override
    abstract public String toString();
}