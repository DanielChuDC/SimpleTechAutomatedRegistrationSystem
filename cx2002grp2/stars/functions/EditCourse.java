package cx2002grp2.stars.functions;

import java.util.List;

import cx2002grp2.stars.database.CourseDB;
import cx2002grp2.stars.database.RegistrationDB;
import cx2002grp2.stars.dataitem.Course;
import cx2002grp2.stars.dataitem.User;
import cx2002grp2.stars.dataitem.User.Domain;

public class EditCourse extends AbstractFunction {

    /**
     * An instance of function, for Singleton pattern.
     */
    private static Function instance = new EditCourse();

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
    private EditCourse() {

    }

    @Override
    public boolean accessible(User user) {
        return user.getDomain() == Domain.STAFF;
    }

    @Override
    public String name() {
        return "Manage Courses";
    }

    @Override
    protected void implementation(User user) {
        while (true) {
            System.out.println("Please select the action: ");
            System.out.println("1: Add a Course");
            System.out.println("2: Edit a Course");
            System.out.println("3: Delete a course");
            System.out.println("4: Print all courses");
            System.out.println("5: Exit course manager");

            int selection = this.enterInt("", 1, 5);
            switch (selection) {
                case 1:
                    if (this.addCourse()) {
                        System.out.println("Successfully added.");
                    }
                    break;
                case 2:
                    if (this.editCourse()) {
                        System.out.println("Successfully edited.");
                    }
                    break;
                case 3:
                    if (this.deleteCourse()) {
                        System.out.println("Successfully deleted.");
                    }
                    break;
                case 4:
                    tbPrinter().printCourseList(CourseDB.getDB());
                    break;
                case 5:
                    return;
            }
        }
    }

    private boolean addCourse() {
        System.out.println("You selected: Add a Course");

        while (true) {
            System.out.println("Please input the Course Code:");
            String newCourseCode = this.sc().nextLine();

            System.out.println("Please input the Course Name:");
            String newCourseName = this.sc().nextLine();

            System.out.println("Please input the School the course belongs to :");
            String school = this.sc().nextLine();

            int AU = this.enterInt("Please input the AU of this course:", 1, 6);

            Course newCourse;
            try {
                newCourse = new Course(newCourseCode, newCourseName, school, AU);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.out.println("Please try again.");
                if (askYesNo("Try again?"))
                    continue;
                else
                    return false;
            }

            if (CourseDB.getDB().hasKey(newCourseCode)) {
                System.out.println("The course code already exists in the database. Please try again.");
                if (askYesNo("Try again?"))
                    continue;
                else
                    return false;
            }

            CourseDB.getDB().addItem(newCourse);
            return true;
        }

    }

    private boolean editCourseCode(Course course) {
        this.tbPrinter().printBreakLine();
        System.out.println("You selected: course code");

        while (true) {
            System.out.println("Please input the Course Code:");
            String newCourseCode = this.sc().nextLine();

            System.out.println("Old course code: " + course.getCourseCode());
            System.out.println("New course code: " + newCourseCode);
            if (this.askForApplyChange()) {
                if (CourseDB.getDB().changeKey(course, newCourseCode)) {
                    break;
                } else {
                    System.out.println("Action Failed. There is duplicated course code in Database.");
                    if (askYesNo("Try again?"))
                        continue;
                    else
                        return false;
                }
            } else {
                System.out.println("Action Cancelled.");
                if (askYesNo("Try again?"))
                    continue;
                else
                    return false;
            }
        }

        return true;

    }

    private boolean editCourseName(Course course) {
        this.tbPrinter().printBreakLine();
        System.out.println("You selected: course name");

        while (true) {
            System.out.println("Please input the course name:");
            String newCourseName = this.sc().nextLine();

            System.out.println("Old course name: " + course.getCourseName());
            System.out.println("New course name: " + newCourseName);
            if (this.askForApplyChange()) {
                course.setCourseName(newCourseName);
                break;
            } else {
                System.out.println("Action Cancelled.");
                if (askYesNo("Try again?"))
                    continue;
                else
                    return false;
            }
        }

        return true;

    }

    private boolean editSchool(Course course) {
        this.tbPrinter().printBreakLine();
        System.out.println("You selected: school");

        while (true) {
            System.out.println("Please input the school:");
            String newSchool = this.sc().nextLine();

            System.out.println("Old school: " + course.getSchool());
            System.out.println("New school: " + newSchool);
            if (this.askForApplyChange()) {
                course.setSchool(newSchool);
                break;
            } else {
                System.out.println("Action Cancelled.");
                if (askYesNo("Try again?"))
                    continue;
                else
                    return false;
            }
        }

        return true;

    }

    private boolean editAU(Course course) {
        this.tbPrinter().printBreakLine();
        System.out.println("You selected: AU");
        while (true) {
            int AU = this.enterInt("Please input the AU of this course: ", 1, 6);

            System.out.println("Old AU: " + (int) course.getAu());
            System.out.println("New AU: " + AU);
            if (this.askForApplyChange()) {
                course.setAu(AU);
                break;
            } else {
                System.out.println("Action Cancelled.");
                if (askYesNo("Try again?"))
                    continue;
                else
                    return false;
            }
        }

        return true;
    }

    private boolean editCourse() {
        System.out.println("You selected: Edit a Course");
        
        System.out.println("Please input the Course Code:");
        String courseCode = this.sc().nextLine();
        
        if (!CourseDB.getDB().hasKey(courseCode)) {
            System.out.println("The course code doesn't exist in the database. Please try again.");
            return false;
        }

        Course course = CourseDB.getDB().getByKey(courseCode);
        
        System.out.println("Current Course Info: ");
        tbPrinter().printCourseList(List.of(course));

        System.out.println("Please select the attribute to edit:");
        System.out.println("1: course code");
        System.out.println("2: course name");
        System.out.println("3: school");
        System.out.println("4: AU");

        int selection = this.enterInt("");
        switch (selection) {
            case 1:
                return editCourseCode(course);
            case 2:
                return editCourseName(course);
            case 3:
                return editSchool(course);
            case 4:
                return editAU(course);
        }

        return false;
    }

    private boolean deleteCourse() {
        System.out.println("You selected: Delete a Course");
        Course deleted = null;

        while (true) {
            System.out.println("Please input the Course Code:");
            String courseCode = this.sc().nextLine();
            deleted = CourseDB.getDB().getByKey(courseCode);

            if (deleted == null) {
                System.out.println("The course code doesn't exist in the database. Please try again.");
                if (askYesNo("Try again?"))
                    continue;
                else
                    return false;
            }

            System.out.println("WARNING: This action will delete ALL information related to\n"
                    + "this course, including ALL course indexes, schedules and registrations!!!");

            System.out.println("Number of indexes under this course: " + deleted.getIndexList().size());
            System.out.println("Number of registrations under this course: "
                    + RegistrationDB.getDB().getRegOfCourseCode(deleted.getCourseCode(), false).size());

            if (this.askYesNo("Confirm deletion?")) {
                CourseDB.getDB().delByKey(courseCode);
                break;
            } else {
                System.out.println("Action Cancelled.");
                if (askYesNo("Try again?"))
                    continue;
                else
                    return false;
            }
        }

        return true;
    }

}
