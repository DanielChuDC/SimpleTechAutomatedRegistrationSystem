package cx2002grp2.stars;

import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.TreeSet;

import cx2002grp2.stars.data.dataitem.CourseIndex;
import cx2002grp2.stars.data.dataitem.Registration;
import cx2002grp2.stars.data.dataitem.Schedule;
import cx2002grp2.stars.data.dataitem.Student;

/**
 * Printer for well structured information.
 * <p>
 * The printer may assume that the console has a width of at least 100
 * characters.
 */
public class TablePrinter {

	private static TablePrinter defaultPrinter = new TablePrinter();

	/**
	 * A factory method to produce a printer.
	 * 
	 * @return a instance of TablePrinter.
	 */
	public static TablePrinter getPrinter() {
		return defaultPrinter;
	}

	private static final String GENERAL_INFO_FORMAT = "%-30s %-30s %-30s \n";
	private static final String SPLITTER = "-------------------------------------------------------------------\n";
	private static final String EMPTY_TABLE_MSG = "This table currently have no content.\n";
	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("hh:mm");

	/**
	 * Print student's basic information.
	 * <p>
	 * Print username, matric No, full name, programme, years of study in at most 2
	 * lines.
	 *
	 * @param student the student to be printed
	 */
	public void printStudentBrief(Student student) {
		System.out.printf(GENERAL_INFO_FORMAT, "Username: " + student.getUsername(), "Matric: " + student.getMatricNo(),
				"Full Name: " + student.getFullName());
		System.out.printf(GENERAL_INFO_FORMAT, "Programme: " + student.getProgramme(),
				"Years of study: " + student.getYearOfStudy(), "");
	}

	/**
	 * Print student and all its registration.
	 * <p>
	 * including:
	 * <ul>
	 * <li>Print student's username, full name and total registered AU in one row.
	 * <li>If no registrations are available, print hinting message.
	 * <li>Print all his registration in table manner.
	 * <li>Registration table needs a header
	 * <li>Each row includes: course code, course index, registration state and AU
	 * </ul>
	 * 
	 * @param student the student whose information and registrations are printed.
	 */
	public void printStudentAndReg(Student student) {
		System.out.printf(GENERAL_INFO_FORMAT, "Username: " + student.getUsername(),
				"Full Name: " + student.getFullName(), "Total Registered AU: " + (int) student.getRegisteredAU());
		if (student.getRegistrationList().isEmpty()) {
			System.out.printf(EMPTY_TABLE_MSG);
		} else {
			// System.out.printf("%-30s\n", "Registration List");
			final String TABLE_FORMAT = "%-20s | %-20s | %-20s | %-20s\n";

			System.out.printf(SPLITTER);
			System.out.printf(TABLE_FORMAT, "Course Code", "Course Index", "Registration State", "AU");
			System.out.printf(SPLITTER);
			for (Registration reg : student.getRegistrationList()) {
				System.out.printf(TABLE_FORMAT, reg.getCourse().getCourseCode(), reg.getCourseIndex(), reg.getStatus(),
						(int) reg.getCourse().getAu());
			}
			System.out.printf(SPLITTER);
		}
	}

	/**
	 * Print a index and its schedule.
	 * <p>
	 * including:
	 * <ul>
	 * <li>Print school, course code and course name in one row
	 * <li>Print course index number, available vacancy number and waitlist length
	 * in one row
	 * <li>If no schedule are available, print hinting message.
	 * <li>Print course schedule in table manner
	 * <li>The schedule table needs a header
	 * <li>Each row of table includes: row number (starts from 1), class type,
	 * group, day of week, begin and end time, venue and remark.
	 * </ul>
	 * 
	 * @param index the index whose information and schedule information are
	 *              printed.
	 */
	public void printIndexAndSchedule(CourseIndex index) {
		System.out.printf(GENERAL_INFO_FORMAT, "School: " + index.getCourse().getSchool(),
				"Course Code: " + index.getCourse().getCourseCode(),
				"Course Name: " + index.getCourse().getCourseName());
		System.out.printf(GENERAL_INFO_FORMAT, "Index Number: " + index.getIndexNo(),
				"Vacancy: " + index.getAvailableVacancy(), "Waitlist Length: " + index.getWaitList().size());
		System.out.printf(SPLITTER);

		this.printScheduleTable(index);
	}

	/**
	 * Print a registration.
	 * <p>
	 * including:
	 * <ul>
	 * <li>Print student's username, course code and course index number in one row
	 * <li>Print registration status, available vacancy number and waitlist length
	 * in one row
	 * <li>If no schedule are available, print hinting message.
	 * <li>Print course schedule in table manner
	 * <li>The schedule table needs a header
	 * <li>Each row of table includes: row number (starts from 1), class type,
	 * group, day of week, begin and end time, venue and remark.
	 * </ul>
	 * 
	 * @param registration the index whose information are printed.
	 */
	public void printRegDetail(Registration registration) {
		System.out.printf(GENERAL_INFO_FORMAT, "Username: " + registration.getStudent().getUsername(),
				"Course Code: " + registration.getCourse().getCourseCode(),
				"Course Index: " + registration.getCourseIndex().getIndexNo());
		System.out.printf(GENERAL_INFO_FORMAT, "Status: " + registration.getStatus(),
				"Vacancy: " + registration.getCourseIndex().getAvailableVacancy(),
				"WaitList Length: " + registration.getCourseIndex().getWaitList().size());

		this.printScheduleTable(registration.getCourseIndex());
	}

	/**
	 * Print students in a registration list in a table manner.
	 * <p>
	 * A header is required to be printed
	 * <p>
	 * Each row contains the username, gender and nationality of a student.
	 *
	 * @param regs the registration list whose item's students are to be printed.
	 */
	public void printStudentInRegList(Iterable<Registration> regs) {
		final String TABLE_FORMAT = "%-15s | %-15s | %-15s\n";
		System.out.printf(TABLE_FORMAT, "Username", "Gender", "Nationality");
		System.out.printf(SPLITTER);
		for (Registration registration : regs) {
			Student student = registration.getStudent();
			System.out.printf(TABLE_FORMAT, student.getUsername(), student.getGender(), student.getNationality());
		}
		System.out.printf(SPLITTER);
	}

	/**
	 * Print the schedule table of a index without information of index.
	 * 
	 * @param index the index which the schedule is under.
	 */
	private void printScheduleTable(CourseIndex index) {
		if (index.getRegisteredList().isEmpty()) {
			System.out.printf(EMPTY_TABLE_MSG);
		} else {
			final String TABLE_FORMAT = "%-3s | %-3s | %-10s | %-10s | %-11s | %-15s | %-15s | %-10s\n";
			System.out.printf(TABLE_FORMAT, "No.", "Type", "Group", "Day", "Time", "Venue", "Teaching Wk", "Remark");
			System.out.printf(SPLITTER);
			int rowNumber = 0;
			for (Schedule schedule : index.getScheduleList()) {
				System.out.printf(TABLE_FORMAT, ++rowNumber, schedule.getClassType(), schedule.getGroup(),
						schedule.getDayOfWeek().toString(),
						schedule.getBeginTime().format(TIME_FORMATTER) + "-"
								+ schedule.getEndTime().format(TIME_FORMATTER),
						schedule.getVenue(), schedule.teachWkStr(), schedule.getRemark());
			}
			System.out.printf(SPLITTER);
		}
	}

	public static void main(String[] args) {
		Set<Integer> set = new TreeSet<Integer>();
		set.add(1);
		set.add(2);
		// set.add(3);
		set.add(4);
	}

}
