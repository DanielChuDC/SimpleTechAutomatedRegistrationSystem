package cx2002grp2.stars.data.dataitem;

import java.time.LocalDateTime;

/**
 * 
 */
public class Registration {

    public enum Status { REGISTERED, WAITLIST, EXAMPED }
    
	private Student student;
	private CourseIndex courseIndex;
	private LocalDateTime registerDateTime;
	private Registration.Status status;

	public Student getStudent() {
        return this.student;
	}

	public CourseIndex getCourseIndex() {
        return this.courseIndex;
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

    @Override
    public boolean equals(Object obj) {
        // TODO - implements equals
        return false;
    }

    @Override
    public int hashCode() {
        // TODO - implements hashCode
        return super.hashCode();
    }
}
