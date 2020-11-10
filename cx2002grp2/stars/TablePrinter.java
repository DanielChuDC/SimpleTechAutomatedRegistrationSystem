package cx2002grp2.stars;

import cx2002grp2.stars.data.dataitem.CourseIndex;
import cx2002grp2.stars.data.dataitem.Registration;
import cx2002grp2.stars.data.dataitem.Student;

/**
 * Printer for well structured information.
 * <p>
 * The printer may assume that the console has a width of at least 100
 * characters.
 */
public class TablePrinter {

	/**
	 * Print student's basic infomation, including username, matric No, full name,
	 * programme, years of study in at most 2 lines.
	 * 
	 * @param student the student to be printed
	 */
	public static void printStudentBrief(Student student) {

	}

	/**
	 * Print student and all its registration, including:
	 * <ul>
	 * <li>Print student's username, full name and his total AU in one row.
	 * <li>If no registrations are available, print hinting message.
	 * <li>Print all his registration in table manner.
	 * <li>Registration table needs a header
	 * <li>Each row includes: course code, course index, registration state and AU
	 * </ul>
	 * 
	 * @param student the student whose information and registrations are printed.
	 */
	public static void printStudentAndReg(Student student) {

	}

	/**
	 * Print a index and its schedule, including:
	 * <ul>
	 * <li>Print school, course code and course name in one row
	 * <li>Print course index number, available vaccecy number and waitlist length in one row
	 * <li>Print course schedule in table manner
	 * <li>The schedule table needs a header
	 * <li>Each row of table includes: class type, group, day of week, begin and end time, remark
	 * time, venue and remark
	 * </ul>
	 * 
	 * @param index
	 */
	public static void printIndexAndSchedule(CourseIndex index) {

	}

	/**
	 * Print a registration, including:
	 * <ul>
	 * <li>Print student's username, course code and course index number in one row
	 * <li>Print registration state, available vaccecy number and waitlist length in one row
	 * <li>Print course schedule in table manner
	 * <li>The schedule table needs a header
	 * <li>Each row of table includes: class type, group, day of week, begin and end time, remark
	 * </ul>
	 * 
	 * @param registration
	 */
	public static void printRegDetail(Registration registration) {

	}

	/**
	 * Print student in a registration list in a table manner.
	 * <p>
	 * A header is required to be printed
	 * <p>
	 * Each row contains the username, gender and nationality of a student.
	 * 
	 * @param regs
	 */
	public static void printStudentInRegList(Iterable<Registration> regs) {

	}

}