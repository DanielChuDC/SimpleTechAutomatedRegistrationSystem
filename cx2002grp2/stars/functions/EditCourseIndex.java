package cx2002grp2.stars.functions;

import java.security.AccessControlException;
import java.util.Objects;

import cx2002grp2.stars.database.CourseDB;
import cx2002grp2.stars.database.CourseIndexDB;
import cx2002grp2.stars.dataitem.Course;
import cx2002grp2.stars.dataitem.CourseIndex;
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

    /**
     * Add course index under the given course.
     * 
     * @param user   the user trying to run the function
     * @param course the course under which the new index is add
     * @throws NullPointerException
     */
    public void addCourseIndex(User user, Course course) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(course);

        if (!CourseDB.getDB().hasItem(course)) {
            throw new IllegalArgumentException("The input course does not exist in the database.");
        }

        if (!accessible(user)) {
            throw new AccessControlException("User " + user + " has not access to function: " + name());
        }

        System.out.printf("Creating new index under course:\n%s: %s\n", course.getCourseCode(), course.getCourseName());

        String indexNo;
        while (true) {
            System.out.print("Enter new index No.: ");
            indexNo = sc().nextLine().trim();
            CourseIndex existingIndex = CourseIndexDB.getDB().getByKey(indexNo);
            if (existingIndex != null) {
                System.out.println("The following index already exist: ");
                tbPrinter().printIndexAndSchedule(existingIndex);
                if (askYesNo("Enter another index?")) {
                    continue;
                } else {
                    return;
                }
            } else {
                break;
            }
        }

    }

}
