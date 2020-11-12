package cx2002grp2.stars.data.dataitem;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 
 */
public class Registration {

	public enum Status {
		REGISTERED, WAITLIST, INVALID
	}

	private Student student;
	private Course course;
	private CourseIndex courseIndex;
	private LocalDateTime registerDateTime;
	private Status status;
	private boolean isDropped = false;


	public Registration(Student student, Course course, LocalDateTime registerDateTime, Status status) {
		Objects.requireNonNull(student);
		// TODO - handle relationship about registration
		this.student = student;
		this.course = course;
		this.courseIndex = null;
		this.registerDateTime = registerDateTime;
		this.status = status;
	}

	public Registration(Student student, CourseIndex courseIndex, LocalDateTime registerDateTime, Status status) {
		// TODO - handle relationship about registration
		this(student, courseIndex.getCourse(), registerDateTime, status);

		this.setCourseIndex(courseIndex);
	}

	public Student getStudent() {
		return this.student;
	}

	public Course getCourse() {
		return this.course;
	}

	public CourseIndex getCourseIndex() {
		return this.courseIndex;
	}

	public void setCourseIndex(CourseIndex courseIndex) {
		if (courseIndex != null &&
			!courseIndex.getCourse().getCourseCode().equals(getCourse().getCourseCode())) {
			throw new IllegalArgumentException("The new course index must have the same course code with the original index.");
		}
		// TODO - handle relationship about registration

		if (this.getCourseIndex() == courseIndex) {
			return;
		}

		if (this.getCourseIndex() != null) {
			this.getCourseIndex().delRegistration(this);
		}
		
		this.courseIndex = courseIndex;
		this.getCourseIndex().delRegistration(this);
	}

	public LocalDateTime getRegisterDateTime() {
		return this.registerDateTime;
	}

	/**
	 * 
	 * @param registerDateTime
	 */
	public void setRegisterDateTime(LocalDateTime registerDateTime) {
		this.registerDateTime = registerDateTime;
	}

	public Registration.Status getStatus() {
		return this.status;
	}

	/**
	 * 
	 * @param status
	 */
	public void setStatus(Registration.Status status) {
		this.status = status;
	}

	/**
	 * 
	 */
	public void drop() {
		// TODO - remove the relationship
	}

	/**
	 * 
	 * @return
	 */
	public boolean isDropped() {
		return isDropped;
	}
}
