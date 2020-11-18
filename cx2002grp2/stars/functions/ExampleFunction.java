package cx2002grp2.stars.functions;

import cx2002grp2.stars.database.CourseIndexDB;
import cx2002grp2.stars.dataitem.CourseIndex;
import cx2002grp2.stars.dataitem.Gender;
import cx2002grp2.stars.dataitem.User;
import cx2002grp2.stars.dataitem.User.Domain;

/**
 * A example function.
 */
public class ExampleFunction extends AbstractFunction {

    /**
     * An instance of function, for Singleton pattern.
     */
    private static Function instance = new ExampleFunction();

    /**
     * An getter of function instance, for Singleton pattern.
     * 
     * @return an instance of function.
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
        return normalStudentAccessible(user) || user.getDomain() == Domain.STAFF;
    }

    @Override
    public String name() {
        return "Test function";
    }

    @Override
    protected void implementation(User user) {
        int n = selectFunction("Func A", "Func B", "Func C");
        System.out.println("You selected "+n);
    }
}
