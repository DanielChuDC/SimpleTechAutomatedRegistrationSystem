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
        
        // user1 - current user // user2 - user to swop with
        User user2 = null;
        int tries = 0;
        // ask for user1's index number
        System.out.println("Enter first (current) user index number to swop:");
        String indexNo1 = sc().nextLine();
        String username1 = user.getUsername();

        // check if indexNo exist and if user1 is taking the index number
        CourseIndex index1 = CourseIndexDB.getDB().getByKey(indexNo1);
        Registration reg1 = RegistrationDB.getDB().getByIndex(indexNo1, username1);
        if (reg1 == null || index1 == null) {
            System.out.println("Invalid index or the index entered is not taken by the student");
            return;
        }

        // second student login - login(domain)
        // max 3 tries before exiting function
        while (user2 == null) {
            System.out.println("Login second user (user to swop index with)");
            user2 = auth.login(Domain.STUDENT);
            tries++;
            if (tries == 3 && user2 == null) {
                System.out.println("Maximum tries of login reached. Exiting function...");
                return;
            } 
        }

        // ask for user2's index number
        System.out.println("Enter second user index number to swop:");
        String indexNo2 = sc().nextLine();
        String username2 = user2.getUsername();

        // check if indexNo exist and if user2 is taking the index number
        CourseIndex index2 = CourseIndexDB.getDB().getByKey(indexNo2);
        Registration reg2 = RegistrationDB.getDB().getByIndex(indexNo2, username2);
        if (reg2 == null || index2 == null) {
            System.out.println("Invalid index or the index entered is not taken by the student");
            return;
        }

        // if all checks successful, table printer the swopping
        System.out.println("Student #1");
        tbPrinter().printRegDetail(reg1); 
        System.out.println("Student #2");
        tbPrinter().printRegDetail(reg2); 
        
        // TODO: implement with askYesNo()
        // ask to confirm, swap indexes (updateDB) if yes. if no, return;
        // System.out.println("Confirm to Swop Index Number?\n" + "1 for yes");
        int num = enterInt("Confirm to Swop Index Number?\n" + "Enter 1 for yes, 2 for no and exit function.", 1, 2);
        if (num == 2) {
            System.out.println("Failed to swop index number. Exiting function...");
            return;
        } else if (num == 1) {
            // TODO: swop in DB
        }

        // print successful
        System.out.printf("%s-Index Number %s has been successfuly swopped with %s-Index Number %s.%n", username1, indexNo1, username2, indexNo2);
    }
}
