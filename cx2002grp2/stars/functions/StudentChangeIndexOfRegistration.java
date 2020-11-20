package cx2002grp2.stars.functions;

import cx2002grp2.stars.CourseAllocator.Result;
import cx2002grp2.stars.database.CourseIndexDB;
import cx2002grp2.stars.database.RegistrationDB;
import cx2002grp2.stars.database.StudentDB;
import cx2002grp2.stars.dataitem.CourseIndex;
import cx2002grp2.stars.dataitem.Registration;
import cx2002grp2.stars.dataitem.Student;
import cx2002grp2.stars.dataitem.User;

/**
 * a function which is used to change course index of a registration.
 * <p>
 * using singleton pattern to make sure only one object is created.
 * <p>
 * this function can be accessed by student.
 */
public class StudentChangeIndexOfRegistration extends AbstractFunction {

    /**
     * An instance of function, for Singleton pattern.
     */
    private static Function instance = new StudentChangeIndexOfRegistration();

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
    private StudentChangeIndexOfRegistration() {

    }
    
    @Override
    public boolean accessible(User user) {
        return normalStudentAccessible(user);
    }

    @Override
    public String name() {
        return "Change Index Number of Course";
    }

    @Override
    protected void implementation(User user) {
        Student student = StudentDB.getDB().getFromUser(user);
        if (student == null) {
            System.out.println("Student doesn't exist. Please try again.");
            return;
        }

        System.out.print("Please enter old course index: ");
        String rawIndex = this.sc().nextLine();

        Registration currentReg = RegistrationDB.getDB().getByIndex(rawIndex, user.getUsername());
        if (currentReg == null) {
            System.out.println("Current Registration of this course doesn't exist. Please try again.");
            return;
        }


        System.out.print("Please enter new course index: ");
        rawIndex = this.sc().nextLine();

        CourseIndex newCourseIndex = CourseIndexDB.getDB().getByKey(rawIndex);
        if (newCourseIndex == null) {
            System.out.println("course Index doesn't exist. Please try again.");
            return;
        }

        System.out.println("Old Index: ");
        this.tbPrinter().printIndexAndSchedule(currentReg.getCourseIndex());
        System.out.println("New Index: ");
        this.tbPrinter().printIndexAndSchedule(newCourseIndex);

        if (this.askYesNo("Change course index from " + currentReg.getCourseIndex() + " to " +
                    newCourseIndex.getIndexNo() + "?")) {
            Result result = this.allocator().changeIndex(currentReg, newCourseIndex);
            System.out.println(result.message());
        }
        else {
            System.out.println("Action cancelled.");
        }

    }
    
}
