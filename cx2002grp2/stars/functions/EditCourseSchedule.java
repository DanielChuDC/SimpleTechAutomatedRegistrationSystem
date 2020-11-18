package cx2002grp2.stars.functions;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import cx2002grp2.stars.database.CourseIndexDB;
import cx2002grp2.stars.dataitem.CourseIndex;
import cx2002grp2.stars.dataitem.Schedule;
import cx2002grp2.stars.dataitem.User;
import cx2002grp2.stars.dataitem.Schedule.ClassType;
import cx2002grp2.stars.dataitem.User.Domain;

public class EditCourseSchedule extends AbstractFunction {

    /**
     * An instance of function, for Singleton pattern.
     */
    private static EditCourseSchedule instance = new EditCourseSchedule();

    /**
     * An getter of function instance, for Singleton pattern.
     * 
     * @return an instance of function.
     */
    public static EditCourseSchedule getInstance() {
        return instance;
    }

    /**
     * private constructor reserve for Singleton pattern
     */
    private EditCourseSchedule() {

    }

    @Override
    public boolean accessible(User user) {
        return user.getDomain() == Domain.STAFF;
    }

    @Override
    public String name() {
        return "Manage Course Schedule";
    }

    @Override
    protected void implementation(User user) {
        CourseIndex index = null;

        while (true) {
            System.out.println("Enter the course index of the schedules to be managed:");
            String indexNo = sc().nextLine().trim();
            index = CourseIndexDB.getDB().getByKey(indexNo);
            if (index == null) {
                System.out.println("Course index does not exist.");
                if (!askYesNo("Try again?")) {
                    return;
                }
            } else {
                break;
            }
        }
    }

    public void manageSchedule(User user, CourseIndex index) {
        String closingFunctionName = "Close Manager for Index: " + index.getIndexNo();
        while (true) {
            System.out.println("Managing schedule for course index: " + index.getIndexNo());
            int funcSelect = selectFunction("Print Schedule Table", "Add Schedule", "Edit Schedule", "Delete Schedule",
                    closingFunctionName);
            switch (funcSelect) {
                case 1:
                    tbPrinter().printIndexAndSchedule(index);
                    break;
                case 2:
                    addSchedule(user, index);
                    break;
                case 3:
                    delSchedule(user, index);
                    break;
                case 4:
                    editSchedule(user, index);
                    break;
                case 5:
                    return;
            }
            tbPrinter().printBreakLine();
        }
    }

    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");

    /**
     * Ask user to enter a time.
     * 
     * @param hintMsg hinting message printed before asking user to input.
     * @return a time entered by the user.
     */
    private LocalTime enterTime(String hintMsg) {
        LocalTime ret = null;
        while (true) {
            System.out.println(hintMsg);
            System.out.print("Time format: hhmm (e.g. 0830, 1700): ");
            String input = sc().nextLine().trim();
            try {
                ret = LocalTime.parse(input, timeFormatter);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid time format.");
            }
        }
        return ret;
    }

    /**
     * Interact with user for adding schedule for given index.
     * 
     * @param user  the user of this function.
     * @param index the index to add schedule.
     */
    private void addSchedule(User user, CourseIndex index) {
        System.out.println("Current Schedule:");
        tbPrinter().printIndexAndSchedule(index);

        ClassType classType;
        String group;
        DayOfWeek dayOfWeek;
        String venue;
        LocalTime beginTime;
        LocalTime endTime;
        String remark;

        classType = selectEnum("Choose class type: ", ClassType.values());

        while (true) {
            dayOfWeek = selectEnum("Choose the day of schedule: ", DayOfWeek.values());

        }

        System.out.print("Enter the group: ");
        group = sc().nextLine();

        System.out.print("Enter the venue: ");
        venue = sc().nextLine();

        System.out.print("Enter any remark: ");
        remark = sc().nextLine();

        Schedule schedule = new Schedule(index, classType, group, dayOfWeek, venue, remark, beginTime, endTime);

    }

    /**
     * Interact with user for deleting schedule from given index.
     * 
     * @param user  the user of this function.
     * @param index the index to add schedule.
     */
    private void delSchedule(User user, CourseIndex index) {

    }

    /**
     * Interact with user for editing schedule of given index.
     * 
     * @param user  the user of this function.
     * @param index the index to add schedule.
     */
    private void editSchedule(User user, CourseIndex index) {

    }
}
