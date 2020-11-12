package cx2002grp2.stars.data.dataitem;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * A class saving information of a registration.
 * <p>
 * This class contains information of a registration: student, course, course
 * index, registration date and time, registration status.
 * <p>
 * All of attributes can be get and set through methods.
 * <p>
 * When an attribute is changed, related information in other class will also
 * change.
 * <p>
 * This class can be safely deleted by drop() with all relationships removed.
 */
public class Registration {

	/**
	 * Enum representing the registration status
	 */
	public enum Status {
		REGISTERED, WAITLIST
	}

	private Student student;
	private Course course;
	private CourseIndex courseIndex;
	private LocalDateTime registerDateTime;
	private Status status;
	private boolean isDropped = false;

	private Registration() {
	}

	/**
	 * Make a dropped registration with the given register data time and register
	 * status.
	 * 
	 * @param registerDateTime the register data time of the dropped registration.
	 * @param status           the register status of the dropped registration.
	 */
	public static Registration makeDropped(LocalDateTime registerDateTime, Status status) {
		Registration droppedReg = new Registration();
		droppedReg.student = null;
		droppedReg.course = null;
		droppedReg.courseIndex = null;
		droppedReg.status = status;
		droppedReg.registerDateTime = registerDateTime;
		droppedReg.isDropped = true;
		return droppedReg;
	}

	public Registration(Student student, Course course, LocalDateTime registerDateTime, Status status) {
		Objects.requireNonNull(student);
		Objects.requireNonNull(course);

		this.student = student;
		this.course = course;
		this.courseIndex = null;
		this.registerDateTime = registerDateTime;
		this.status = status;

		this.student.addRegistration(this);
	}

	public Registration(Student student, CourseIndex courseIndex, LocalDateTime registerDateTime, Status status) {
		this(student, courseIndex.getCourse(), registerDateTime, status);

		this.setCourseIndex(courseIndex);
	}

	/**
	 * get student of this registration.
	 * 
	 * @return student of this registration
	 */
	public Student getStudent() {
		return this.student;
	}

	/**
	 * get course of this registration.
	 * 
	 * @return course of this registration
	 */
	public Course getCourse() {
		return this.course;
	}

	/**
	 * get course index of this registration.
	 * 
	 * @return course index of this registration
	 */
	public CourseIndex getCourseIndex() {
		return this.courseIndex;
	}

	/**
	 * set course index of this registration.
	 * <p>
	 * update related course index.
	 * 
	 * @param courseIndex course index of this registration
	 * @throws IllegalArgumentException if the new course index has different course
	 *                                  code with the original index.
	 */
	public void setCourseIndex(CourseIndex courseIndex) {
		if (courseIndex != null && !courseIndex.getCourse().getCourseCode().equals(getCourse().getCourseCode())) {
			throw new IllegalArgumentException(
					"The new course index must have the same course code with the original index.");
		}
		// TODO - handle relationship about registration

		if (this.getCourseIndex() == courseIndex) {
			return;
		}

		if (this.getCourseIndex() != null) {
			this.getCourseIndex().delRegistration(this);
		}

		this.courseIndex = courseIndex;
		if (this.courseIndex != null)
			this.getCourseIndex().addRegistration(this);
	}

	/**
	 * get register datetime of this registration.
	 * 
	 * @return register datetime of this registration
	 */
	public LocalDateTime getRegisterDateTime() {
		return this.registerDateTime;
	}

	/**
	 * set register datetime of this registration
	 * 
	 * @param registerDateTime register datetime of this registration
	 */
	public void setRegisterDateTime(LocalDateTime registerDateTime) {
		this.registerDateTime = registerDateTime;
	}

	/**
	 * get status of this registration
	 * 
	 * @return status of this registration
	 */
	public Registration.Status getStatus() {
		return this.status;
	}

	/**
	 * set status of this registration
	 * 
	 * @param status status of this registration
	 */
	public void setStatus(Registration.Status status) {
		this.status = status;
	}

	/**
	 * safely delete this registration.
	 * <p>
	 * drop all relationships this registration involves, then set all attributes to
	 * null, safely remove this registration.
	 */
	public void drop() {
		// TODO - remove the relationship
		this.isDropped = true;

		this.courseIndex.delRegistration(this);
		this.student.delRegistration(this);

		this.course = null;
		this.courseIndex = null;
		this.registerDateTime = null;
		this.status = null;
		this.student = null;
	}

	/**
	 * check whether this registration is dropped.
	 * 
	 * @return true if this registration is dropped
	 */
	public boolean isDropped() {
		return isDropped;
	}

	@Override
	public String toString() {
		return "{" + student + ", " + courseIndex + "}";
	}
}
