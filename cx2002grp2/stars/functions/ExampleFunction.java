package cx2002grp2.stars.functions;

import cx2002grp2.stars.data.Gender;
import cx2002grp2.stars.data.database.CourseIndexDB;
import cx2002grp2.stars.data.dataitem.CourseIndex;
import cx2002grp2.stars.data.dataitem.User;
import cx2002grp2.stars.data.dataitem.User.Domain;

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
        int num = enterInt("Enter an integer: ");
        System.out.println(num + "*2 = " + (num * 2));

        System.out.println();

        Gender gender = selectEnum("Choose your gender: ", Gender.values());
        System.out.println("Your gender is: "+gender);

        System.out.println();

        System.out.println("Select a course index.");
        String indexNo = sc().nextLine();
        CourseIndex index = CourseIndexDB.getDB().getByKey(indexNo);
        if (index == null) {
            System.out.println("invalid index.");
        } else {
            tbPrinter().printIndexAndSchedule(index);
        }
    }
}
