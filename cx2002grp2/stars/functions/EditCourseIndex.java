package cx2002grp2.stars.functions;

import java.security.AccessControlException;
import java.util.Objects;

import cx2002grp2.stars.database.CourseDB;
import cx2002grp2.stars.database.CourseIndexDB;
import cx2002grp2.stars.database.UserDB;
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
        int funcNo = selectFunction("Create Course Index", "Edit Course Index", "Delete Course Index");
        switch (funcNo) {
            case 1:
                addCourseIndex(user);
                break;
            case 2:
                break;
            case 3:
                break;
        }
    }

    /**
     * Interact with user for adding course index.
     * 
     * @param user the user of this function
     */
    private void addCourseIndex(User user) {
        // Input course
        Course course;
        while (true) {
            System.out.println("Enter the course code under with the new index to be added:");
            String courseCode = sc().nextLine();
            course = CourseDB.getDB().getByKey(courseCode);
            if (course == null) {
                System.out.println("Course code does not exist.");
                if (!askYesNo("Try again?")) {
                    return;
                }
            } else {
                break;
            }
        }

        while (true) {
            addCourseIndex(user, course);
            if (!askYesNo("Add another index for course " + course.getCourseCode() + "?")) {
                return;
            }
        }
    }

    /**
     * Interact with user for deleting course index.
     * 
     * @param user the user of this function
     */
    private void deleteCourseIndex(User user) {
        CourseIndex index;

        while (true) {
            System.out.println("Enter the course index to be deleted:");
            String indexNo = sc().nextLine().trim();
            index = CourseIndexDB.getDB().getByKey(indexNo);
            if (index == null) {
                System.out.println("Course index does not exist.");
                if (!askYesNo("Try again?")) {
                    return;
                }
            } else {
                break;
            }
        }

        System.out.println("The index to be deleted: ");
        tbPrinter().printIndexAndSchedule(index);

        tbPrinter().printBreakLine("WARNING", '=');
        System.out.println("Deleting an index will results in all the registrations under it being deleted.");
        tbPrinter().printBreakLine("", '=');

        if (!askYesNo("Confirm delete index "+index.getIndexNo()+"?")) {
            System.out.println("Deletion cancelled.");
        }

        CourseIndexDB.getDB().delItem(index);

    }

    /**
     * Add course index under the given course.
     * 
     * @param user   the user trying to run the function
     * @param course the course under which the new index is add
     * @return true if a new course index is added, otherwise, return false.
     * @throws NullPointerException     if any argument is null
     * @throws IllegalArgumentException if any argument does not exist in the
     *                                  database
     * @throws AccessControlException   if the user have no access to this function.
     */
    public boolean addCourseIndex(User user, Course course) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(course);

        if (!UserDB.getDB().hasItem(user)) {
            throw new IllegalArgumentException("The input user does not exist in the database.");
        }
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
                if (!askYesNo("Enter another index?")) {
                    return false;
                }
            } else {
                break;
            }

        }
        int maxVcc = enterInt("Enter max vacancy of course index: ", 0, Integer.MAX_VALUE);

        System.out.println("The following course index will be created: ");
        System.out.printf("Course Code: %s, Index: %s, Max Vcc: %d\n", course.getCourseCode(), indexNo, maxVcc);

        if (!askYesNo("Confirm?")) {
            System.out.println("Index creation cancelled.");
            return false;
        }

        try {
            CourseIndex newIndex = new CourseIndex(indexNo, course, maxVcc);
            CourseIndexDB.getDB().addItem(newIndex);
            return true;
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            System.out.println("Index creation failed.");
            return false;
        }

    }

}
