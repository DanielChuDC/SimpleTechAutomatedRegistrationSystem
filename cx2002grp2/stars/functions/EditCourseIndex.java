package cx2002grp2.stars.functions;

import cx2002grp2.stars.dataitem.User;
import cx2002grp2.stars.dataitem.User.Domain;

/**
 * Edit the course indexes.
 * <p>
 * Support adding new course index, editing existing course index and deleting
 * course index.
 * <p>
 * Delete a course index will result in all the registration under this course
 * index being dropped.
 */
public class EditCourseIndex extends AbstractFunction {

    /**
     * An instance of function, for Singleton pattern.
     */
    private static Function instance = new EditCourseIndex();

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
    private EditCourseIndex() {

    }

    @Override
    public boolean accessible(User user) {
        return user.getDomain() == Domain.STAFF;
    }

    @Override
    public String name() {
        return "Edit Course Indexes";
    }

    @Override
    protected void implementation(User user) {
        
    }

}
