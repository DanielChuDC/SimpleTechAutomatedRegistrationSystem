package cx2002grp2.stars;

import cx2002grp2.stars.data.dataitem.CourseIndex;
import cx2002grp2.stars.data.dataitem.Registration;
import cx2002grp2.stars.data.dataitem.Student;

/**
 * TablePrinter
 */
public class TablePrinter {

	/**
	 * Print student's basic infomation, including username, matric No, full name,
	 * programme, years of study.
	 * 
	 * @param student
	 */
	public void printStudentBrief(Student student) {

	}

	/**
	 * Print student and all its registration, include:
	 * <ul>
	 * <li>Print student's username, full name
	 * <li>Print total AU
	 * <li>Print all his registration in table manner
	 * <li>Registration table needs a header
	 * <li>Each row of table includes: course code, AU, course index, registration state
	 * </ul>
	 * 
	 * @param student
	 */
	public void printStudentAndReg(Student student) {

	}

	/**
	 * Print a index and its schedule, including:
	 * <ul>
	 * <li>Print course index number, course name, school
	 * <li>Print available vaccecy number
	 * <li>Print number of people in waitlist
	 * <li>Print course schedule in table manner
	 * <li>The schedule table needs a header
	 * <li>Each row of table includes: class type, group, day of week, begin and end
	 * time, venue and remark
	 * </ul>
	 * 
	 * @param index
	 */
	public void printIndexAndSchedule(CourseIndex index) {

	}

	/**
	 * Print a registration, including:
	 * <ul>
	 * <li>Print student's username and full name
	 * <li>Print course index number, course name
	 * <li>Print course schedule in table manner
	 * <li>Print registration state
	 * </ul>
	 * 
	 * @param registration
	 */
	public void printRegDetail(Registration registration) {

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
	public void printStudentInRegList(Iterable<Registration> regs) {

	}

}