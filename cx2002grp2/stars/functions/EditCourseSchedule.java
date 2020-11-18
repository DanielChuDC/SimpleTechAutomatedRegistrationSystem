package cx2002grp2.stars.functions;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
                System.out.println("Unexpected time format, enter again.");
            }
        }
        return ret;
    }

    private static final Set<Integer> ALL_ODD_WEEKS = Set.of(1, 3, 5, 7, 9, 11, 13);
    private static final Set<Integer> ALL_EVEN_WEEKS = Set.of(2, 4, 6, 8, 10, 12);
    private static final Set<Integer> ALL_WEEKS = Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13);

    /**
     * Ask user to enter teaching week.
     * 
     * @return the teaching weeks selected by user.
     */
    private Set<Integer> enterTeachingWk(Collection<Integer> initialSet) {
        int week = 0;
        Set<Integer> teachWk = new TreeSet<>(initialSet);
        List<String> options = List.of("Week 1~13", "All even weeks", "All odd weeks", "Enter one week to add",
                "Enter one week to remove", "Clean all Selected weeks", "Confirm week Selection");

        while (true) {
            System.out.println();
            System.out.println("Current selected teaching weeks: " + teachWk);

            System.out.println("Select teaching week(s) to add: ");
            int weekSelection = selectFunction(options);

            switch (weekSelection) {
                case 1:
                    teachWk.addAll(ALL_WEEKS);
                    break;
                case 2:
                    teachWk.addAll(ALL_EVEN_WEEKS);
                    break;
                case 3:
                    teachWk.addAll(ALL_ODD_WEEKS);
                    break;
                case 4:
                    week = enterInt("Enter a week to be added: ", 1, 13);
                    teachWk.add(week);
                    break;
                case 5:
                    week = enterInt("Enter a week to be removed: ", 1, 13);
                    teachWk.remove(week);
                    break;
                case 6:
                    teachWk.clear();
                    break;
                case 7:
                    return teachWk;
            }

        }
    }

    /**
     * Check if the given time interval clash with any schedule in a schedule list.
     * 
     * @param scheduleList the schedule to be checked against
     * @param except       the schedule that does not participate in checking
     * @param begin        the begin time of the class
     * @param end          the begin time of the class
     * @param day          the day of the class
     * @param teachWk      the teaching week of the class
     * @return the schedule that clash with the given time interval. If no clash
     *         happens, return null
     */
    private Schedule checkClash(Iterable<Schedule> scheduleList, Schedule except, LocalTime begin, LocalTime end,
            DayOfWeek day, Set<Integer> teachWk) {

        for (Schedule s1 : scheduleList) {

            if (s1.getDayOfWeek() != day) {
                continue;
            }

            if (s1.getBeginTime().compareTo(end) >= 0 || begin.compareTo(s1.getEndTime()) >= 0) {
                continue;
            }

            Set<Integer> weekIntersection = new HashSet<>(s1.teachingWeeks());
            weekIntersection.retainAll(teachWk);
            if (weekIntersection.isEmpty()) {
                continue;
            }

            return s1;
        }

        return null;
    };

    /**
     * Ask user to edit the time information of a schedule
     * 
     * @param initialSchedule the schedule being edited
     * @return true if the schedule is edited. Otherwise, return false.
     */
    private boolean editTimeInfo(Schedule initialSchedule) {
        CourseIndex index = initialSchedule.getCourseIndex();
        DayOfWeek dayOfWeek;
        LocalTime beginTime;
        LocalTime endTime;
        Set<Integer> teachWk;

        while (true) {
            System.out.println();
            beginTime = enterTime("Enter the begin time of schedule: ");
            endTime = enterTime("Enter the end time of schedule: ");

            if (beginTime.compareTo(endTime) >= 0) {
                System.out.println("Begin time must be earlier than end time.");
                if (!askYesNo("Enter time again?")) {
                    System.out.println("Schedule addition terminated.");
                    return false;
                }
            }

            dayOfWeek = selectEnum("Choose the day of new schedule: ", DayOfWeek.values());

            teachWk = enterTeachingWk(initialSchedule.teachingWeeks());

            Schedule clashingSchedule = checkClash(index.getScheduleList(), initialSchedule, beginTime, endTime,
                    dayOfWeek, teachWk);

            if (clashingSchedule == null) {
                break;
            }

            System.out.println("The entered class time clashes with the following schedule: ");
            tbPrinter().printScheduleList(List.of(clashingSchedule));

            if (!askYesNo("Enter time info again?")) {
                System.out.println("Schedule creation terminated.");
                return false;
            }
        }

        initialSchedule.setBeginTime(beginTime);
        initialSchedule.setEndTime(endTime);
        initialSchedule.setDayOfWeek(dayOfWeek);
        initialSchedule.teachingWeeks().clear();
        initialSchedule.teachingWeeks().addAll(teachWk);

        return true;
    }

    /**
     * Interact with user for adding schedule for given index.
     * 
     * @param user  the user of this function.
     * @param index the index to add schedule.
     */
    private void addSchedule(User user, CourseIndex index) {

        ClassType classType;
        String group;
        String venue;
        String remark;

        while (true) {
            System.out.println("Current Schedule:");
            tbPrinter().printIndexAndSchedule(index);

            Schedule schedule = new Schedule(index, null, null, null, null, null, null, null);

            if (!editTimeInfo(schedule)) {
                return;
            }

            classType = selectEnum("Choose class type: ", ClassType.values());
            schedule.setClassType(classType);

            System.out.print("Enter the class group: ");
            group = sc().nextLine();
            schedule.setGroup(group);

            System.out.print("Enter the class venue: ");
            venue = sc().nextLine();
            schedule.setVenue(venue);

            System.out.print("Enter any remark: ");
            remark = sc().nextLine();
            schedule.setRemark(remark);

            System.out.println("The following schedule will be added: ");
            tbPrinter().printScheduleList(List.of(schedule));

            if (!askYesNo("Confirm addition?")) {
                schedule.setCourseIndex(null);
            }

            if (!askYesNo("Add another schedule under index " + index.getIndexNo() + "?")) {
                return;
            }
        }

    }

    /**
     * Interact with user for deleting schedule from given index.
     * 
     * @param user  the user of this function.
     * @param index the index to delete schedule.
     */
    private void delSchedule(User user, CourseIndex index) {
        if (index.getScheduleList().isEmpty()) {
            System.out.println("The index has no schedule, deletion cannot be performed.");
            return;
        }

        while (true) {
            System.out.println("Current Schedule:");
            tbPrinter().printIndexAndSchedule(index);

            int scheduleSelection = enterInt("Enter the row No. of schedule to be deleted: ", 1,
                    index.getScheduleList().size());

            Schedule schedule = index.getScheduleList().get(scheduleSelection);

            System.out.println("The following schedule will be delete: ");
            tbPrinter().printScheduleList(List.of(schedule));

            if (askYesNo("Confirm deletion?")) {
                schedule.setCourseIndex(null);
            }

            if (!askYesNo("Delete another schedule under index " + index.getIndexNo() + "?")) {
                return;
            }
        }

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
