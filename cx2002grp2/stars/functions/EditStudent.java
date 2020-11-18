package cx2002grp2.stars.functions;

import java.io.Console;
import java.util.Collection;

import cx2002grp2.stars.Authenticator;
import cx2002grp2.stars.database.CourseDB;
import cx2002grp2.stars.database.RegistrationDB;
import cx2002grp2.stars.database.StudentDB;
import cx2002grp2.stars.database.UserDB;
import cx2002grp2.stars.dataitem.Course;
import cx2002grp2.stars.dataitem.Gender;
import cx2002grp2.stars.dataitem.Registration;
import cx2002grp2.stars.dataitem.Student;
import cx2002grp2.stars.dataitem.User;
import cx2002grp2.stars.dataitem.User.Domain;

/**
 * a function which is used to edit (add/update/delete) student.
 * <p>
 * using singleton pattern to make sure only one object is created.
 * <p>
 * this function can only be accessed by staff.
 */
public class EditStudent extends AbstractFunction {
    /**
     * An instance of function, for Singleton pattern.
     */
    private static Function instance = new EditStudent();

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
    private EditStudent() {

    }

    @Override
    public boolean accessible(User user) {
        return user.getDomain() == Domain.STAFF;
    }

    @Override
    public String name() {
        return "Edit Student";
    }

    @Override
    protected void implementation(User user) {
        // ask user if he wants to add, update or delete student (input 1, 2 or 3)
        int n = selectFunction("Add Student", "Edit Student", "Delete Student");
        System.out.println("You selected "+n);
        // 

    }

    private void addStudent() {
      Authenticator auth = Authenticator.getInstance();
      Console console = System.console();
      // User class attributes
      System.out.println("Enter username of student: ");
      String username = sc().nextLine();

      //// check username early 
      User user_exist = UserDB.getDB().getByKey(username);

        if (user_exist != null) {
            System.out.println("Account with that username already exists. Exiting function..");
            return;
        }

      char[] passwordEntry = console.readPassword("Enter password of student: \n");
      String password = String.valueOf(passwordEntry);
      System.out.println("Enter email of student: ");
      String email = sc().nextLine();
      System.out.println("Enter phone number of student: ");
      String phoneNo = sc().nextLine();

      // Student class attributes
      System.out.println("Enter matric number of student: ");
      String matricNo = sc().nextLine();
      Gender gender = selectEnum("Enter gender of student: ", Gender.values());
      System.out.println("Enter full name of student: ");
      String fullName = sc().nextLine();
      System.out.println("Enter nationality of student: ");
      String nationality = sc().nextLine();
      System.out.println("Enter student's year of study: ");
      int yearOfStudy = sc().nextInt();
      System.out.println("Enter student's programme: ");
      String programme = sc().nextLine();
      
      // confirm
      if (!askYesNo("Confirm to add student?")) {
        System.out.println("Failed to add student. Exiting function...");
        return;
      }

      // create account (will create user in the process)
      User newUser = auth.createAccount(username, password, Domain.STUDENT, email, phoneNo);

      // create Student and add to DB
      Student newStudent = new Student(newUser, matricNo, gender, fullName, nationality, yearOfStudy, programme);
      StudentDB.getDB().addItem(newStudent);

      // Print details of created student
      System.out.println("Student created successfully: ");
      tbPrinter().printStudentBrief(newStudent);
      
    }

    private void editStudent() {
      // ask for username

      // check if username exist. if no, return.

      // print details of student

      // Ask what to edit

      // changing username need to changekey in db?
    }

    private void delStudent() {
      System.out.println("Enter username of student: ");
      String username = sc().nextLine();

      //// check if username exist. if no, return.
      Student student = StudentDB.getDB().getByKey(username);

      if (student == null) {
          System.out.println("Student account with that username does not exist. Exiting function..");
          return;
      }

      // print details of student (tblPrinter().printStudentBrief())
      System.out.println("You selected student:");
      tbPrinter().printStudentBrief(student);
    
      // confirm deletion
      if (!askYesNo("Confirm to delete student?")) {
        System.out.println("Failed to delete student. Exiting function...");
        return;
      }

      // delete from DB
      StudentDB.getDB().delItem(student);
      System.out.println("Student deleted successfully.");
    }
}
