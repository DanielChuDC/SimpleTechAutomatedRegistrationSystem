package cx2002grp2.stars.functions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import cx2002grp2.stars.Configs;
import cx2002grp2.stars.data.database.UserDB;
import cx2002grp2.stars.data.dataitem.User;
import cx2002grp2.stars.data.dataitem.User.Domain;

/**
 * a function which is used to edit the access time of student.
 * using singleton pattern to make sure only one object is created.
 * this function can be accessed by staff.
 */
public class EditStudentAccessPeriod extends AbstractFunction {

    /**
     * An instance of function, for Singleton pattern.
     */
    private static Function instance = new EditStudentAccessPeriod();

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
    private EditStudentAccessPeriod() {

    }

    @Override
    public boolean accessible(User user) {
        return user.getDomain() == Domain.STAFF;
    }

    @Override
    public String name() {
        return "Edit Student Access Period";
    }

    @Override
    protected void implementation(User user) {
        String pattern = "yyyy-MM-dd HH:mm";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);


        System.out.print("Please input new start date and time (Format: " + pattern + "): \n");
        String input = this.sc().nextLine();
        LocalDateTime newStartDateTime;
        try {
            newStartDateTime = LocalDateTime.parse(input, formatter);
        }
        catch (Exception e){
            System.out.println("Invalid input format. Please try again.");
            return;
        }


        System.out.print("Please input new end date and time (Format: " + pattern + "): \n");
        input = this.sc().nextLine();
        LocalDateTime newEndDateTime;
        try {
            newEndDateTime = LocalDateTime.parse(input, formatter);
        }
        catch (Exception e){
            System.out.println("Invalid input format. Please try again.");
            return;
        }

        if (newStartDateTime.isAfter(newEndDateTime)) {
            System.out.println("Start date time is after the end date time. Please try again.");
            return;
        }

        if (this.askYesNo("Change access time from " + newStartDateTime.format(formatter) +
                    " to " + newEndDateTime.format(formatter) + "?")) {
            Configs.setAccessStartTime(newStartDateTime);
            Configs.setAccessEndTime(newEndDateTime);
        }
        else {
            System.out.println("Action cancelled.");
        }

        
    }
    
    public static void main(String[] args) {
        EditStudentAccessPeriod.getInstance().run(UserDB.getDB().getByKey("testuser1"));
        Configs.saveConfig();
    }
}
