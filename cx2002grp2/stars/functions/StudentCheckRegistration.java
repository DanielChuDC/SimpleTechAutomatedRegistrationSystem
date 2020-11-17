package cx2002grp2.stars.functions;

import cx2002grp2.stars.data.database.StudentDB;
import cx2002grp2.stars.data.dataitem.Student;
import cx2002grp2.stars.data.dataitem.User;

/**
 * a function which is used to check the registrations of a student.
 * <p>
 * using singleton pattern to make sure only one object is created.
 * this function can be accessed by student.
 */
public class StudentCheckRegistration extends AbstractFunction {

    /**
     * An instance of function, for Singleton pattern.
     */
    private static Function instance = new StudentCheckRegistration();

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
    private StudentCheckRegistration() {

    }



    @Override
    public boolean accessible(User user) {
        return normalStudentAccessible(user);
    }

    @Override
    public String name() {
        return "Check Registration";
    }

    @Override
    protected void implementation(User user) {
        Student student = StudentDB.getDB().getFromUser(user);
        if (student == null) {
            System.out.println("Student doesn't exist. Please try again.");
            return;
        }

        this.tbPrinter().printStudentAndReg(student);
    }
    
}
