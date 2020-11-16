package cx2002grp2.stars;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import cx2002grp2.stars.data.database.CourseDB;
import cx2002grp2.stars.data.database.CourseIndexDB;
import cx2002grp2.stars.data.database.RegistrationDB;
import cx2002grp2.stars.data.database.StudentDB;
import cx2002grp2.stars.data.dataitem.Course;
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
	private static final String EMPTY_TABLE_MSG = "This table currently have no content.\n";
	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("hh:mm");
	private static final int BREAK_LINE_LENGTH = 100;

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
		final String TABLE_FORMAT = "%-20s | %-20s | %-20s | %-20s\n";
		
		System.out.printf(GENERAL_INFO_FORMAT, "Username: " + student.getUsername(),
				"Full Name: " + student.getFullName(), "Total Registered AU: " + roundedAu(student.getRegisteredAU()));

		System.out.printf(TABLE_FORMAT, "Course Code", "Course Index", "Registration State", "AU");
		printBreakLine();

		if (student.getRegistrationList().isEmpty()) {
			System.out.printf(EMPTY_TABLE_MSG);
		} else {
			// System.out.printf("%-30s\n", "Registration List");

			for (Registration reg : student.getRegistrationList()) {
				System.out.printf(TABLE_FORMAT, reg.getCourse().getCourseCode(), reg.getCourseIndex(), reg.getStatus(),
						roundedAu(reg.getCourse().getAu()));
			}
		}
		printBreakLine();
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
		printBreakLine();

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
		printBreakLine();
		for (Registration registration : regs) {
			Student student = registration.getStudent();
			System.out.printf(TABLE_FORMAT, student.getUsername(), student.getGender(), student.getNationality());
		}
		printBreakLine();
	}

	/**
	 * Print the schedule table of a index without information of index.
	 * 
	 * @param index the index which the schedule is under.
	 */
	private void printScheduleTable(CourseIndex index) {
		if (index.getScheduleList().isEmpty()) {
			System.out.printf(EMPTY_TABLE_MSG);
		} else {
			final String TABLE_FORMAT = "%-3s | %-4s | %-10s | %-10s | %-11s | %-15s | %-15s | %-10s\n";
			System.out.printf(TABLE_FORMAT, "No.", "Type", "Group", "Day", "Time", "Venue", "Teaching Wk", "Remark");
			printBreakLine();
			int rowNumber = 0;
			for (Schedule schedule : index.getScheduleList()) {
				System.out.printf(TABLE_FORMAT, ++rowNumber, schedule.getClassType(), schedule.getGroup(),
						schedule.getDayOfWeek().toString(),
						schedule.getBeginTime().format(TIME_FORMATTER) + "-"
								+ schedule.getEndTime().format(TIME_FORMATTER),
						schedule.getVenue(), schedule.teachWkStr(), schedule.getRemark());
			}
			printBreakLine();
		}
	}

	/**
	 * Print student list in a table manner.
	 * <p>
	 * A header is required to be printed
	 * <p>
	 * Each row contains the username, full name and matriculation No. of a student.
	 *
	 * @param studList the student list to be printed.
	 */
	public void printStudentList(Iterable<Student> studList) {
		final String TABLE_FORMAT = "%-15s | %-15s | %-30s\n";
		System.out.printf(TABLE_FORMAT, "Username", "Full Name", "Matric No.");
		printBreakLine();
		for (Student student : studList) {
			System.out.printf(TABLE_FORMAT, student.getUsername(), student.getMatricNo(), student.getFullName());
		}
		printBreakLine();
	}

	/**
	 * Print course list in a table manner.
	 * <p>
	 * A header is required to be printed
	 * <p>
	 * Each row contains the course code, school, AU and course name of a course.
	 *
	 * @param courseList the course list to be printed.
	 */
	public void printCourseList(Iterable<Course> courseList) {
		final String TABLE_FORMAT = "%-10s | %-10s | %-10s | %-50s\n";
		System.out.printf(TABLE_FORMAT, "Code", "School", "AU", "Course Name");
		printBreakLine();
		for (Course course : courseList) {
			System.out.printf(TABLE_FORMAT, course.getCourseCode(), course.getSchool(), roundedAu(course.getAu()),
					course.getCourseName());
		}
		printBreakLine();
	}

	/**
	 * Print a break line
	 */
	public void printBreakLine() {
		printBreakLine("");
	}

	/**
	 * Print a break line with content in the middle
	 * 
	 * @param content the content at the middle of break line
	 */
	public void printBreakLine(String content) {
		printBreakLine(content, '-');
	}

	/**
	 * Print a break line with content in the middle with the given breakLine
	 * character.
	 * 
	 * @param content       the content at the middle of break line
	 * @param breakLineChar the character used for break line
	 */
	public void printBreakLine(String content, char breakLineChar) {
		String oneBreakLineChar = String.valueOf(breakLineChar);

		if (content.isEmpty()) {
			System.out.println(String.join("", Collections.nCopies(BREAK_LINE_LENGTH, oneBreakLineChar)));
			return;
		}

		int leftLen = (BREAK_LINE_LENGTH - content.length()) / 2 - 1;
		int rightLen = BREAK_LINE_LENGTH - content.length() - leftLen - 2;
		String leftStr = String.join("", Collections.nCopies(leftLen, oneBreakLineChar));
		String rightStr = String.join("", Collections.nCopies(rightLen, oneBreakLineChar));
		System.out.printf("%s %s %s\n", leftStr, content, rightStr);
	}

	/**
	 * Function used to round the AU before printing
	 * 
	 * @param au the AU to be rounded
	 * @return a string representing the AU with 2 digit precision.
	 */
	private String roundedAu(double au) {
		return String.format("%.2f", au);
	}

	public static void main(String[] args) {
		getPrinter().printStudentAndReg(StudentDB.getDB().pickOne());
	}

}
