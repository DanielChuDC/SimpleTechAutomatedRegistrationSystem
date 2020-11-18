package cx2002grp2.stars.functions;

import java.security.AccessControlException;
import java.util.Objects;

import cx2002grp2.stars.CourseAllocator.Result;
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
    private static EditCourseIndex instance = new EditCourseIndex();

    /**
     * An getter of function instance, for Singleton pattern.
     * 
     * @return an instance of function.
     */
    public static EditCourseIndex getInstance() {
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
        return "Manage Course Indexes";
    }

    @Override
    protected void implementation(User user) {
        tbPrinter().printBreakLine("<<< Course Index Manager >>>", '-');
        while (true) {
            int funcNo = selectFunction("Print All Indexes", "Print Indexes of Course", "Create Course Index",
                    "Edit Course Index", "Delete Course Index", "Leave Course Index Manager");
            switch (funcNo) {
                case 1:
                    tbPrinter().printCourseIndexList(CourseIndexDB.getDB());
                    break;
                case 2:
                    printIndexOfCourse(user);
                    break;
                case 3:
                    addCourseIndex(user);
                    break;
                case 4:
                    editCourseIndex(user);
                    break;
                case 5:
                    deleteCourseIndex(user);
                    break;
                case 6:
                    return;
            }
        }
    }

    /**
     * Ask user to enter course.
     * <p>
     * Existence of course will be checked.
     * 
     * @param hintingMsg hinting message to be printed before asking user to enter
     *                   course code.
     * @return the course selected by the user. If user fail to select a course,
     *         return null.
     */
    private Course enterCourse(String hintingMsg) {
        // Input course
        Course course = null;
        while (true) {
            System.out.println(hintingMsg);
            String courseCode = sc().nextLine();
            course = CourseDB.getDB().getByKey(courseCode);
            if (course == null) {
                System.out.println("Course code does not exist.");
                if (!askYesNo("Try again?")) {
                    return null;
                }
            } else {
                break;
            }
        }
        return course;
    }

    /**
     * Ask user to enter course index.
     * <p>
     * Existence of course index will be checked.
     * 
     * @param hintingMsg hinting message to be printed before asking user to enter
     *                   course index number.
     * @return the course index selected by the user. If user fail to select a
     *         course index, return null.
     */
    private CourseIndex enterIndex(String hintingMsg) {
        CourseIndex index = null;

        while (true) {
            System.out.println(hintingMsg);
            String indexNo = sc().nextLine().trim();
            index = CourseIndexDB.getDB().getByKey(indexNo);
            if (index == null) {
                System.out.println("Course index does not exist.");
                if (!askYesNo("Try again?")) {
                    return null;
                }
            } else {
                break;
            }
        }
        return index;
    }

    /**
     * Print the index under course selected by user
     * 
     * @param user the user of this function.
     */
    private void printIndexOfCourse(User user) {
        // Input course
        Course course = enterCourse("Enter the course code of the indexes to be printed:");
        if (course == null) {
            return;
        }
        tbPrinter().printCourseIndexList(course.getIndexList());
    }

    /**
     * Interact with user for adding course index.
     * 
     * @param user the user of this function
     */
    private void addCourseIndex(User user) {
        // Input course
        Course course = enterCourse("Enter the course code under which the new index to be added:");
        if (course == null) {
            return;
        }

        addCourseIndex(user, course);
    }

    /**
     * Interact with user for deleting course index.
     * 
     * @param user the user of this function
     */
    private void deleteCourseIndex(User user) {
        CourseIndex index = enterIndex("Enter the course index to be deleted:");
        if (index == null) {
            return;
        }

        System.out.println("The index to be deleted: ");
        tbPrinter().printIndexAndSchedule(index);

        tbPrinter().printBreakLine("WARNING", '=');
        System.out.println("Deleting an index will results in all the registrations under it being deleted.");
        tbPrinter().printBreakLine("", '=');

        if (!askYesNo("Confirm delete index " + index.getIndexNo() + "?")) {
            System.out.println("Deletion cancelled.");
        }

        CourseIndexDB.getDB().delItem(index);

    }

    /**
     * Add course index under the given course.
     * 
     * @param user   the user trying to run the function
     * @param course the course under which the new index is add
     * @throws NullPointerException     if any argument is null
     * @throws IllegalArgumentException if any argument does not exist in the
     *                                  database
     * @throws AccessControlException   if the user have no access to this function.
     */
    public void addCourseIndex(User user, Course course) {
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

        while (true) {
            System.out.printf("Creating new index under course:\n%s: %s\n\n", course.getCourseCode(),
                    course.getCourseName());

            String indexNo;
            while (true) {
                System.out.print("Enter new index No.: ");
                indexNo = sc().nextLine().trim();

                CourseIndex existingIndex = CourseIndexDB.getDB().getByKey(indexNo);
                if (existingIndex != null) {
                    System.out.println("The following index already exist: ");
                    tbPrinter().printIndexAndSchedule(existingIndex);
                    if (!askYesNo("Enter another index?")) {
                        System.out.println("Index creation failed.");
                        return;
                    }
                } else {
                    break;
                }

            }
            int maxVcc = enterInt("Enter max vacancy of course index: ", 0, Integer.MAX_VALUE);

            System.out.println();
            System.out.println("The following course index will be created: ");
            System.out.printf("Course Code: %s, Index: %s, Max Vcc: %d\n", course.getCourseCode(), indexNo, maxVcc);

            if (!askYesNo("Confirm creation?")) {
                System.out.println("Index creation cancelled.");
                System.out.println();
                if (askYesNo("Restart index creation under course " + course.getCourseCode() + "?")) {
                    continue;
                }
                return;
            }

            try {
                CourseIndex newIndex = new CourseIndex(indexNo, course, maxVcc);
                CourseIndexDB.getDB().addItem(newIndex);
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
                System.out.println("Index creation failed.");
                return;
            }

            System.out.println("Index " + indexNo + " created");

            if (!askYesNo("Add another index for course " + course.getCourseCode() + "?")) {
                return;
            }
        }
    }

    /**
     * Interact with user to edit the course index.
     * 
     * @param user the user of this function.
     */
    private void editCourseIndex(User user) {

        CourseIndex index = enterIndex("Please enter the index to be edited:");
        if (index == null) {
            return;
        }

        while (true) {
            tbPrinter().printBreakLine("<<< Index Editor >>>", '-');
            System.out.println("Index being edit: " + index.getIndexNo());
            int funcSelect = selectFunction("Print Index Details and Schedules", "Edit Max Vacancy", "Manage Schedule",
                    "Leave Index Editor");
            switch (funcSelect) {
                case 1:
                    tbPrinter().printIndexAndSchedule(index);
                    break;
                case 2:
                    editMaxVcc(user, index);
                    break;
                case 3:
                    EditCourseSchedule.getInstance().manageSchedule(user, index);
                case 4:
                    return;
            }
        }

    }

    /**
     * Interact with use for changing max vacancy of an index
     * 
     * @param user  the user running the function
     * @param index the index whose max vacancy to be changed.
     */
    private void editMaxVcc(User user, CourseIndex index) {
        int registeredCount = index.getRegisteredList().size();

        System.out.printf("Current Registered Student / Max Vcc: %d / %d\n", registeredCount, index.getMaxVacancy());

        int newVcc = enterInt("Enter new max vacancy: ", 0, Integer.MAX_VALUE);

        Result result = allocator().changeMaxVacancy(index, newVcc);
        if (result.isSuccessful()) {
            System.out.println("Max vacancy changed.");
        } else {
            System.out.println(result.message());
        }
    }
}
