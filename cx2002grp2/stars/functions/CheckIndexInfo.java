package cx2002grp2.stars.functions;

import cx2002grp2.stars.data.database.CourseIndexDB;
import cx2002grp2.stars.data.dataitem.CourseIndex;
import cx2002grp2.stars.data.dataitem.User;
import cx2002grp2.stars.data.dataitem.User.Domain;

/**
 * a function which is used to check the information of a course index.
 * using singleton pattern to make sure only one object is created.
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
        return "Check Index Info";
    }

    @Override
    protected void implementation(User user) {
        System.out.print("Please enter course index: ");
        String rawIndex = this.sc().nextLine();

        CourseIndex courseIndex = CourseIndexDB.getDB().getByKey(rawIndex);
        if (courseIndex == null) {
            System.out.println("course Index doesn't exist. Please try again.");
            return;
        }
            
        tbPrinter().printIndexAndSchedule(courseIndex);
    }
    
}
