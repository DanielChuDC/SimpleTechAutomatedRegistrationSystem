package cx2002grp2.stars;

import java.time.LocalDateTime;

import cx2002grp2.stars.data.dataitem.*;
import cx2002grp2.stars.data.dataitem.Registration.Status;

public class CourseAllocator {

	/**
	 * 
	 * @param student
	 * @param courseIndex
	 */
	public CourseAllocator.Result registerCourse(Student student, CourseIndex courseIndex) {
		// TODO - implement method

		Registration registration = new Registration
			(student, courseIndex.getCourse(), LocalDateTime.now(), Status.REGISTERED);

		// check AU before adding
		double maxAU = Configs.getMaxAu();
		double registeredAU = 0;
		for (Registration reg : student.getRegistrationList()) {
			registeredAU += reg.getCourse().getAu();
		}

		if (registeredAU + registration.getCourse().getAu() > maxAU) {
			return new Result(false, "FAIL");
		}

		// check vacancy of corresponding courseIndex
		CourseIndex newCourseIndex = registration.getCourseIndex();
		if (newCourseIndex.getAvailableVacancy() <= 0) {
			registration.setStatus(Status.WAITLIST);
		}
		else {
			registration.setStatus(Status.REGISTERED);
		}

		// to be continued...

		return new Result(false, "FAIL");
	}

	/**
	 * 
	 * @param student
	 * @param courseIndex
	 */
	public CourseAllocator.Result dropCourse(Student student, CourseIndex courseIndex) {
		// TODO - implement method
		return new Result(false, "FAIL");
	}

	/**
	 * 
	 * @param courseIndex
	 * @param newVcc
	 */
	public CourseAllocator.Result changeVacancy(CourseIndex courseIndex, int newVcc) {
		// TODO - implement method
		return new Result(false, "FAIL");
	}

	/**
	 * 
	 * @param currentReg
	 * @param newIndex
	 */
	public CourseAllocator.Result changeIndex(Registration currentReg, CourseIndex newIndex) {
		// TODO - implement method
		return new Result(false, "FAIL");
	}

	/**
	 * 
	 * @param reg1
	 * @param reg2
	 */
	public CourseAllocator.Result swapRegistration(Registration reg1, Registration reg2) {
		// TODO - implement method
		return new Result(false, "FAIL");
	}

	public class Result {

		private boolean isSuccessful;
		private String message;

		public Result(boolean isSuccessful, String message) {
			this.isSuccessful = isSuccessful;
			this.message = message;
		}

		public boolean getIsSuccessful() {
			return this.isSuccessful;
		}

		public String getMessage() {
			return this.message;
		}

	}

}