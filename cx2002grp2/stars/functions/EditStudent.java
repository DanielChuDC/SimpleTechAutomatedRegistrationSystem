package cx2002grp2.stars.functions;

import java.io.Console;

import cx2002grp2.stars.Authenticator;
import cx2002grp2.stars.database.StudentDB;
import cx2002grp2.stars.database.UserDB;
import cx2002grp2.stars.dataitem.Gender;
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
        return "Manage Student Accounts";
    }

    @Override
    protected void implementation(User user) {
        while (true) {
            // ask user if he wants to add, update or delete student (input 1, 2, 3 or 4)
            int n = selectFunction("Add Student", "Edit Student", "Delete Student", "Print All Students",
                    "Exit Student Account Manager");
            System.out.println("You selected " + n);

            switch (n) {
                case 1:
                    System.out.println("You selected to add student.");
                    if (addStudent()) {
                        tbPrinter().printBreakLine();
                        System.out.println("List of all students after addition:");
                        tbPrinter().printStudentList(StudentDB.getDB());
                        System.out.println("Successfully added student.");
                    }
                    break;
                case 2:
                    System.out.println("You selected to edit student.");
                    if (editStudent())
                        System.out.println("Successfully edited student.");
                    break;
                case 3:
                    System.out.println("You selected to delete student.");
                    if (delStudent()) {
                        System.out.println("Successfully deleted student.");
                    }
                    break;
                case 4:
                    tbPrinter().printStudentList(StudentDB.getDB());
                    break;
                case 5:
                    return;
            }
        }

    }

    /**
     * add a new student into database.
     * <p>
     * This function will ask user to input basic information of a student,
     * including username, matric number, full name, nationality etc.
     * <p>
     * The username cannot be the same as other user.
     * 
     * @return true if student is successfully added.
     */
    private boolean addStudent() {
        Authenticator auth = Authenticator.getInstance();
        Console console = System.console();
        String username;
        // User class attributes
        while (true) {
            System.out.println("Enter username of student: ");
            username = sc().nextLine();

            // check if user exist
            if (UserDB.getDB().hasKey(username)) {
                System.out.println("Account with that username already exists.");
                if (askYesNo("Try again?"))
                    continue;
                else
                    return false;
            }

            break;
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
        int yearOfStudy = enterInt("Enter student's year of study: ", 1, 4);
        System.out.println("Enter student's programme: ");
        String programme = sc().nextLine();

        // confirm
        if (!askYesNo("Confirm to add student?")) {
            System.out.println("Failed to add student. Exiting function...");
            return false;
        }

        // create account (will create user in the process)
        User newUser = auth.createAccount(username, password, Domain.STUDENT, email, phoneNo);

        // create Student and add to DB
        Student newStudent = new Student(newUser, matricNo, gender, fullName, nationality, yearOfStudy, programme);
        StudentDB.getDB().addItem(newStudent);

        // Print details of created student
        System.out.println("Created Student:");
        tbPrinter().printStudentBrief(newStudent);
        return true;

    }

    /**
     * Edit a student which already exists in the database.
     * <p>
     * This function will ask user to input username, then ask what to edit about
     * the student.
     * <p>
     * User can edit student attributes.
     * <p>
     * 
     * @return true if information is successfully edited.
     */
    private boolean editStudent() {
        Student student;

        // ask for username
        while (true) {
            System.out.println("Enter username of student: ");
            String username = sc().nextLine();

            student = StudentDB.getDB().getByKey(username);

            // check if username exist. if no, return.
            if (student == null) {
                System.out.println("Student account with that username does not exist.");
                if (askYesNo("Try again?"))
                    continue;
                else
                    return false;
            } else
                break;
        }

        // print details of student
        System.out.println("Selected student:");
        tbPrinter().printStudentBrief(student);

        // Ask what to edit
        System.out.println("Please select the attribute to edit");
        int n = selectFunction("username", "password", "matric number", "full name", "nationality", "year of study",
                "programme", "gender", "email", "phone number");

        switch (n) {
            case 1:
                return editUsername(student);
            case 2:
                return editPassword(student);
            case 3:
                return editMatricNo(student);
            case 4:
                return editFullName(student);
            case 5:
                return editNationality(student);
            case 6:
                return editYOS(student);
            case 7:
                return editProgramme(student);
            case 8:
                return editGender(student);
            case 9:
                return editEmail(student);
            case 10:
                return editPhoneNo(student);
        }

        return false;
    }

    /**
     * Delete a student from database.
     * <p>
     * This function will ask user to input username, then ask whether to delete the
     * student.
     * 
     * @return true if selected student is successfully deleted.
     */
    private boolean delStudent() {
        Student student;
        while (true) {
            System.out.println("Enter username of student: ");
            String username = sc().nextLine();

            student = StudentDB.getDB().getByKey(username);

            if (student == null) {
                System.out.println("Student account with that username does not exist.");
                if (askYesNo("Try again?"))
                    continue;
                else
                    return false;
            }
            break;
        }

        // print details of student (tblPrinter().printStudentBrief())
        System.out.println("Selected student:");
        tbPrinter().printStudentBrief(student);

        // confirm deletion
        if (!askYesNo("Confirm to delete student?")) {
            System.out.println("Failed to delete student. Exiting function...");
            return false;
        }

        // delete from DB
        StudentDB.getDB().delItem(student);
        return true;
    }

    /**
     * Edit the username of a student.
     * <p>
     * This function will ask user to input new username, then ask whether to make
     * change.
     * 
     * @param course the student to be set a new username.
     * @return true if username is successfully edited.
     */
    private boolean editUsername(Student student) {
        tbPrinter().printBreakLine();
        System.out.println("You selected to edit student's username.");

        while (true) {
            System.out.println("Enter new username:");
            String newUsername = this.sc().nextLine();

            System.out.println("Old username: " + student.getUsername());
            System.out.println("New username: " + newUsername);
            if (this.askForApplyChange()) {
                if (UserDB.getDB().changeKey(student, newUsername)) {
                    break;
                } else {
                    System.out.println("Action Failed. There is duplicated username in Database.");
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

    /**
     * Edit the full name of a student.
     * <p>
     * This function will ask user to input new full name, then ask whether to make
     * change.
     * 
     * @param course the student to be set a new full name.
     * @return true if full name is successfully edited.
     */
    private boolean editFullName(Student student) {
        tbPrinter().printBreakLine();
        System.out.println("You selected to edit student's full name.");

        while (true) {
            System.out.println("Enter new full name:");
            String newFullName = sc().nextLine();

            System.out.println("Old full name: " + student.getFullName());
            System.out.println("New full name: " + newFullName);
            if (this.askForApplyChange()) {
                student.setFullName(newFullName);
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

    /**
     * Edit the matric number of a student.
     * <p>
     * This function will ask user to input new matric number, then ask whether to
     * make change.
     * 
     * @param course the student to be set a new matric number.
     * @return true if matric number is successfully edited.
     */
    private boolean editMatricNo(Student student) {
        tbPrinter().printBreakLine();
        System.out.println("You selected to edit student's matric number.");
        while (true) {
            System.out.println("Enter new matric number:");
            String newMatricNo = sc().nextLine();

            System.out.println("Old matric number: " + student.getMatricNo());
            System.out.println("New matric number: " + newMatricNo);
            if (this.askForApplyChange()) {
                student.setMatricNo(newMatricNo);
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

    /**
     * Edit the nationality of a student.
     * <p>
     * This function will ask user to input new nationality, then ask whether to
     * make change.
     * 
     * @param course the student to be set a new nationality.
     * @return true if nationality is successfully edited.
     */
    private boolean editNationality(Student student) {
        tbPrinter().printBreakLine();
        System.out.println("You selected to edit student's nationality.");

        while (true) {
            System.out.println("Enter new nationality:");
            String newNationality = sc().nextLine();

            System.out.println("Old nationality: " + student.getNationality());
            System.out.println("New nationality: " + newNationality);
            if (this.askForApplyChange()) {
                student.setNationality(newNationality);
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

    /**
     * Edit the password of a student.
     * <p>
     * This function will ask user to input new password, then ask whether to make
     * change.
     * 
     * @param course the student to be set a new password.
     * @return true if password is successfully edited.
     */
    private boolean editPassword(Student student) {
        tbPrinter().printBreakLine();
        System.out.println("You selected to edit student's password.");
        Authenticator auth = Authenticator.getInstance();

        while (true) {
            System.out.println("Enter new password:");
            String newPassword = sc().nextLine();

            // will not print out old password for "security purposes"
            System.out.println("New password: " + newPassword);
            if (this.askForApplyChange()) {
                auth.changePassword(student, newPassword);
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

    /**
     * Edit the programme of a student.
     * <p>
     * This function will ask user to input new programme, then ask whether to make
     * change.
     * 
     * @param course the student to be set a new programme.
     * @return true if programme is successfully edited.
     */
    private boolean editProgramme(Student student) {
        tbPrinter().printBreakLine();
        System.out.println("You selected to edit student's programme.");

        while (true) {
            System.out.println("Enter new programme:");
            String newProgramme = sc().nextLine();

            System.out.println("Old programme: " + student.getProgramme());
            System.out.println("New programme: " + newProgramme);
            if (this.askForApplyChange()) {
                student.setProgramme(newProgramme);
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

    /**
     * Edit the year of study of a student.
     * <p>
     * This function will ask user to input new year of study, then ask whether to
     * make change.
     * 
     * @param course the student to be set a new year of study.
     * @return true if year of study is successfully edited.
     */
    private boolean editYOS(Student student) {
        tbPrinter().printBreakLine();
        System.out.println("You selected to edit student's year of study.");

        while (true) {
            System.out.println("Enter new year of study:");
            int newYOS = sc().nextInt();

            System.out.println("Old year of study: " + student.getYearOfStudy());
            System.out.println("New year of study: " + newYOS);
            if (this.askForApplyChange()) {
                student.setYearOfStudy(newYOS);
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

    /**
     * Edit the gender of a student.
     * <p>
     * This function will ask user to input new gender from the list of genders,
     * then ask whether to make change.
     * 
     * @param course the student to be set a new gender.
     * @return true if gender is successfully edited.
     */
    private boolean editGender(Student student) {
        tbPrinter().printBreakLine();
        System.out.println("You selected to edit student's gender.");

        while (true) {
            // print out gender to choose from
            Gender newGender = selectEnum("Select new gender: ", Gender.values());

            System.out.println("Old gender: " + student.getGender());
            System.out.println("New gender: " + newGender);
            if (this.askForApplyChange()) {
                student.setGender(newGender);
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

    /**
     * Edit the email of a student.
     * <p>
     * This function will ask user to input new email, then ask whether to make
     * change.
     * 
     * @param course the student to be set a new email.
     * @return true if email is successfully edited.
     */
    private boolean editEmail(Student student) {
        tbPrinter().printBreakLine();
        System.out.println("You selected to edit student's email.");

        while (true) {
            System.out.println("Enter new email:");
            String newEmail = sc().nextLine();

            System.out.println("Old email: " + student.getEmail());
            System.out.println("New email: " + newEmail);
            if (this.askForApplyChange()) {
                student.setEmail(newEmail);
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

    /**
     * Edit the phone number of a student.
     * <p>
     * This function will ask user to input new phone number, then ask whether to
     * make change.
     * 
     * @param course the student to be set a new phone number.
     * @return true if phone number is successfully edited.
     */
    private boolean editPhoneNo(Student student) {
        tbPrinter().printBreakLine();
        System.out.println("You selected to edit student's phone number.");

        while (true) {
            System.out.println("Enter new phone number:");
            String newPhoneNo = sc().nextLine();

            System.out.println("Old phone number: " + student.getPhoneNo());
            System.out.println("New phone number: " + newPhoneNo);
            if (this.askForApplyChange()) {
                student.setPhoneNo(newPhoneNo);
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
