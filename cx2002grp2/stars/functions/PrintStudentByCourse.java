package cx2002grp2.stars.functions;

import java.util.Collection;

import cx2002grp2.stars.data.database.CourseDB;
import cx2002grp2.stars.data.dataitem.Registration;
import cx2002grp2.stars.data.database.RegistrationDB;
import cx2002grp2.stars.data.dataitem.Course;
import cx2002grp2.stars.data.dataitem.User;
import cx2002grp2.stars.data.dataitem.User.Domain;

public class PrintStudentByCourse extends AbstractFunction {
    /**
     * An instance of function, for Singleton pattern.
     */
    private static Function instance = new PrintStudentByCourse();

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
    private PrintStudentByCourse() {

    }

    @Override
    public boolean accessible(User user) {
        return user.getDomain() == Domain.STAFF;
    }

    @Override
    public String name() {
        return "Print student list by course";
    }

    @Override
    protected void implementation(User user) {
        String courseCode;
        while (true) {
            // Enter course code
            System.out.println("Enter course code: ");
            courseCode = sc().nextLine();
    
            // check if course exist
            Course course = CourseDB.getDB().getByKey(courseCode);
            if (course == null) {
                System.out.println("Invalid course code.");
                if (!askYesNo("Try again?")) {
                    return;
                }
            } else break;

        }

        // get Registration list
        Collection<Registration> regs = RegistrationDB.getDB().getRegOfCourseCode(courseCode, true);

        
        // print list of students
        tbPrinter().printStudentInRegList(regs);

    }
}
