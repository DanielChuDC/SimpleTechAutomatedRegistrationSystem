package cx2002grp2.stars.functions;

import java.util.Collection;

import cx2002grp2.stars.data.database.CourseIndexDB;
import cx2002grp2.stars.data.database.RegistrationDB;
import cx2002grp2.stars.data.dataitem.CourseIndex;
import cx2002grp2.stars.data.dataitem.Registration;
import cx2002grp2.stars.data.dataitem.User;
import cx2002grp2.stars.data.dataitem.User.Domain;

/**
 * A example function.
 */
public class PrintStudentByIndex extends AbstractFunction {

    /**
     * An instance of function, for Singleton pattern.
     */
    private static Function instance = new PrintStudentByIndex();

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
    private PrintStudentByIndex() {

    }

    @Override
    public boolean accessible(User user) {
        return normalStudentAccessible(user) || user.getDomain() == Domain.STAFF;
    }

    @Override
    public String name() {
        return "Print student list by index number";
    }

    @Override
    protected void implementation(User user) {
        // Enter index number
        System.out.println("Enter course code: ");
        String indexNo = sc().nextLine();
        
        // Check if index number exist
        CourseIndex index = CourseIndexDB.getDB().getByKey(indexNo);
        if (index == null) {
            System.out.println("Invalid index.");
            return;
        }


        // Get registration list
        Collection<Registration> regs = RegistrationDB.getDB().getRegOfIndex(indexNo);

        // print list of students
        tbPrinter().printStudentInRegList(regs);
    }
}