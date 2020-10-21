package cx2002grp2.stars.functions;

import cx2002grp2.stars.data.User;

/**
 * Function
 */
public abstract class AbstractFunction {

    abstract public void run(User user);

    abstract public boolean accessable(User user);

    @Override
    abstract public String toString();
}