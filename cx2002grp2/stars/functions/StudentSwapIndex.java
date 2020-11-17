package cx2002grp2.stars.functions;

import java.util.Collection;
import java.util.Set;

import cx2002grp2.stars.Authenticator;
import cx2002grp2.stars.data.database.CourseIndexDB;
import cx2002grp2.stars.data.database.RegistrationDB;
import cx2002grp2.stars.data.dataitem.CourseIndex;
import cx2002grp2.stars.data.dataitem.User;
import cx2002grp2.stars.data.dataitem.User.Domain;
import cx2002grp2.stars.data.dataitem.Registration;
import cx2002grp2.stars.data.dataitem.Student;

public class StudentSwapIndex extends AbstractFunction{
    /**
     * An instance of function, for Singleton pattern.
     */
    private static Function instance = new StudentSwapIndex();

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
    private StudentSwapIndex() {
        
    }

    @Override
    public boolean accessible(User user) {
        return normalStudentAccessible(user) || user.getDomain() == Domain.STUDENT;
    }

    @Override
    public String name() {
        return "Swop index number with another student";
    }

    @Override
    protected void implementation(User user) {
        Authenticator auth = Authenticator.getInstance();
        String indexNo1, username1, indexNo2, username2;
        CourseIndex index1, index2;
        Registration reg1, reg2;
        
        // user1 - current user // user2 - user to swop with
        User user2 = null;

        while (true) {
            // ask for user1's index number
            System.out.println("Enter first (current) user index number to swop:");
            indexNo1 = sc().nextLine();
            username1 = user.getUsername();
    
            // check if indexNo exist and if user1 is taking the index number
            index1 = CourseIndexDB.getDB().getByKey(indexNo1);
            reg1 = RegistrationDB.getDB().getByIndex(indexNo1, username1);
            if (reg1 == null || index1 == null) {
                System.out.println("Invalid index or the index entered is not taken by the student");
                if (!askYesNo("Try again?")) {
                    return;
                }
            } else break;

        }

        // second student login - login(domain)
        while (true) {
            System.out.println("Login second user (user to swop index with)");
            user2 = auth.login(Domain.STUDENT);
            if (user2 != null) break;
            
            if (!askYesNo("Try logging in second user again?")) return;
        }

        while (true) {
            // ask for user2's index number
            System.out.println("Enter second user index number to swop:");
            indexNo2 = sc().nextLine();
            username2 = user2.getUsername();
    
            // check if indexNo exist and if user1 is taking the index number
            index2 = CourseIndexDB.getDB().getByKey(indexNo2);
            reg2 = RegistrationDB.getDB().getByIndex(indexNo2, username2);
            if (reg2 == null || index2 == null) {
                System.out.println("Invalid index or the index entered is not taken by the student");
                if (!askYesNo("Try again?")) {
                    return;
                }
            } else break;

        }
        

        // if all checks successful, table printer the swopping
        System.out.println("Student #1");
        tbPrinter().printRegDetail(reg1); 
        System.out.println("Student #2");
        tbPrinter().printRegDetail(reg2); 
        
        
        // ask to confirm, swap indexes (updateDB) if yes. if no, return;
        if (!askYesNo("Confirm to Swop Index Number?")) {
            System.out.println("Failed to swop index number. Exiting function...");
            return;
        } else {

            // print successful
            System.out.printf("%s-Index Number %s has been successfuly swopped with %s-Index Number %s.%n", username1, indexNo1, username2, indexNo2);
        }
        

    }
}
