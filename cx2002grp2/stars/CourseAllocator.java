package cx2002grp2.stars;

import java.time.LocalDateTime;

import cx2002grp2.stars.data.dataitem.*;
import cx2002grp2.stars.data.dataitem.Registration.Status;

/**
 * Manager for all the events that may cause the registration information changes. 
 */
public class CourseAllocator {

	private static CourseAllocator defaultAllocator = new CourseAllocator();

	public static CourseAllocator getInstance() {
		return defaultAllocator;
	}

	/**
	 * Register a the given course index for the given student.
	 * <p>
	 * Possible reasons of failure:
	 * <ol>
	 * <li>The student or the course does not exist in the database.
	 * <li>The registered course index has no vacancy (the student will be put on
	 * the waitlist instead).
	 * <li>The student reaches the limit of number of registration.
	 * <li>The student makes duplicated registration on the same course.
	 * <li>The new registration has time slot clashing with the existing courses.
	 * </ol>
	 * 
	 * @param student     the student to register the course.
	 * @param courseIndex the course index to be registered.
	 * @return the allocation result
	 */
	public CourseAllocator.Result registerCourse(Student student, CourseIndex courseIndex) {
		// TODO - implement method

		// Check existence
		// Course course = courseIndex.getCourse();

		// // check AU before adding
		// double maxAU = Configs.getMaxAu();
		// double registeredAU = 0;
		// for (Registration reg : student.getRegistrationList()) {
		// 	registeredAU += reg.getCourse().getAu();
		// }

		// if (registeredAU + course.getAu() > maxAU) {
		// 	return new Result(false, "The AU");
		// }

		// // // check vacancy of corresponding courseIndex
		// // CourseIndex newCourseIndex = registration.getCourseIndex();
		// // if (newCourseIndex.getAvailableVacancy() <= 0) {
		// // registration.setStatus(Status.WAITLIST);
		// // }
		// // else {
		// // registration.setStatus(Status.REGISTERED);
		// // }

		// // to be continued...
		// Registration registration = new Registration(student, courseIndex, LocalDateTime.now(), Status.REGISTERED);

		return new Result(false, "Function not implemented.");
	}

	/**
	 * Drop a course for a student.
	 * <p>
	 * Possible reasons of failure:
	 * <ol>
	 * <li>The student or the course does not exist in the database.
	 * <li>The student didn't register for the given index.
	 * </ol>
	 * 
	 * @param student     the student to drop the course index
	 * @param courseIndex the course index to be dropped
	 * @return the result of dropping.
	 */
	public CourseAllocator.Result dropCourse(Student student, CourseIndex courseIndex) {
		// TODO - implement method
		return new Result(false, "Function not implemented.");
	}

	/**
	 * Change the maximum vacancy of the given course index.
	 * <p>
	 * Possible reasons of failure:
	 * <ol>
	 * <li>The course index does not exist in the database.
	 * <li>The new vacancy is not a positive number. (newMaxVcc smaller than 1)
	 * <li>The number of student that has already registered the course is more than
	 * the newMaxVcc.
	 * </ol>
	 * 
	 * @param courseIndex the course index which the max vacancy to be changed.
	 * @param newMaxVcc   the new max vacancy
	 * @return the result of changing
	 */
	public CourseAllocator.Result changeMaxVacancy(CourseIndex courseIndex, int newMaxVcc) {
		// TODO - implement method
		return new Result(false, "Function not implemented.");
	}

	/**
	 * Change the index of an registration to another index.
	 * <ol>
	 * <li>The registration or the course index does not exist in the database.
	 * <li>The course code of the index does not match course code of the
	 * registration.
	 * <li>The status of registration is not {@link Registration.Status#REGISTERED}.
	 * <li>The new registration has time slot clashing with the existing courses.
	 * </ol>
	 * 
	 * @param currentReg the registration whose index to be changed.
	 * @param newIndex   the new index of the registration.
	 * @return the result of changing registration.
	 */
	public CourseAllocator.Result changeIndex(Registration currentReg, CourseIndex newIndex) {
		// TODO - implement method
		return new Result(false, "Function not implemented.");
	}

	/**
	 * Swap the two registration of two student.
	 * <ol>
	 * <li>The registrations does not exist in the database.
	 * <li>Two registrations are the same.
	 * <li>The course codes of two registrations do not match.
	 * <li>The status of registrations are not {@link Registration.Status#REGISTERED}.
	 * <li>The new registration has time slot clashing with the existing courses.
	 * </ol>
	 * 
	 * @param reg1 one registration
	 * @param reg2 the other registration.
	 * @return the result of swapping registration.
	 */
	public CourseAllocator.Result swapRegistration(Registration reg1, Registration reg2) {
		// TODO - implement method
		return new Result(false, "Function not implemented.");
	}

	/**
	 * Generate result for invalid input
	 * @return a result showing invalid input.
	 */
	private Result invalidInputResult(String value) {
		return new Result(false, "Invalid input: "+value);
	}

	/**
	 * A class representing the allocation result of registration.
	 * <p>
	 * The following information will be included:
	 * <ol>
	 * <li>Whether the attempt to allocation is successful.
	 * <li>The message produced by the allocator. If an allocation failed, the
	 * message will show the reason of failure.
	 * </ol>
	 */
	public static class Result {

		public static final Result SUCCESSFUL = new Result(true, "Successful.");

		private boolean isSuccessful;
		private String message;

		public Result(boolean isSuccessful, String message) {
			this.isSuccessful = isSuccessful;
			this.message = message;
		}

		/**
		 * Get whether the attempt of allocation is successful.
		 * 
		 * @return whether the attempt of allocation is successful.
		 */
		public boolean isSuccessful() {
			return this.isSuccessful;
		}

		/**
		 * Get the message produce by the allocator.
		 * <p>
		 * If the allocation fail (that is {@link Result#isSuccessful()} return false),
		 * the message is guaranteed to be a non-empty string.
		 * 
		 * @return the message produce by the allocator.
		 */
		public String message() {
			return this.message;
		}

	}

}