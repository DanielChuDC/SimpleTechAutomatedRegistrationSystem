package cx2002grp2.stars.functions;

import cx2002grp2.stars.database.CourseIndexDB;
import cx2002grp2.stars.dataitem.CourseIndex;
import cx2002grp2.stars.dataitem.User;
import cx2002grp2.stars.dataitem.User.Domain;

/**
 * a function which is used to check the information of a course index.
 * <p>
 * using singleton pattern to make sure only one object is created.
 * <p>
 * this function can be accessed by student and staff.
 */
public class CheckIndexInfo extends AbstractFunction {

    /**
     * An instance of function, for Singleton pattern.
     */
    private static Function instance = new CheckIndexInfo();

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
    private CheckIndexInfo() {

    }

    @Override
    public boolean accessible(User user) {
        return normalStudentAccessible(user) || user.getDomain() == Domain.STAFF;
    }

    @Override
    public String name() {
        return "Check Index Info (Including Schedule, Vacancy)";
    }

    @Override
    protected void implementation(User user) {
        while (true) {
            System.out.print("Please enter course index: ");
            String rawIndex = this.sc().nextLine();
    
            CourseIndex courseIndex = CourseIndexDB.getDB().getByKey(rawIndex);
            if (courseIndex == null) {
                System.out.println("course Index doesn't exist.");
            } else {
                tbPrinter().printIndexAndSchedule(courseIndex);
            }
            if (!askYesNo("Enter another index?")) {
                return;
            }
        }
    }
    
}
