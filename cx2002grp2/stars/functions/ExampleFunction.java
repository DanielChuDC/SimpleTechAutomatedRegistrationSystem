package cx2002grp2.stars.functions;

import cx2002grp2.stars.data.dataitem.User;

/**
 * A example function, which will simply say "Hello!"
 */
public class ExampleFunction extends AbstractFunction {

    /**
     * An instance of function, for Singleton pattern.
     */
    private static Function instance = new ExampleFunction();

    /**
     * An getter of function instance, for Singleton pattern.
     */
    public static Function getInstance() {
        return instance;
    }

    /**
     * private constructor reserve for Singleton pattern
     */
    private ExampleFunction() {

    }

    @Override
    public boolean accessible(User user) {
        return true;
    }

    @Override
    public String name() {
        return "Say Hello!";
    }

    @Override
    protected void implementation(User user) {
        System.out.println("Hello! " + user.getUsername());
    }
}
