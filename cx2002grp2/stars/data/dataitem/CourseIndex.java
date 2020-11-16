package cx2002grp2.stars.data.dataitem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import cx2002grp2.stars.data.dataitem.Registration.Status;
import cx2002grp2.stars.util.RegistrationComparator;

/**
 * A class saving information of a course index.
 * <p>
 * This class contains information of a course index: indexNo, course, max
 * vacancy, list of Schedule, set of registration.
 * <p>
 * All of attributes can be get and set through methods.
 * <p>
 * When an attribute is changed, related information in other class will also
 * change.
 */
public class CourseIndex implements SingleKeyItem<String> {

	private String indexNo;
	private Course course;
	private int maxVacancy;
	private List<Schedule> scheduleList;
	private SortedSet<Registration> registrationList;
	private SortedSet<Registration> waitlistSet;
	private Set<Registration> registeredSet;

	private static final Registration splitterRegistration = Registration.makeDropped(LocalDateTime.MIN,
			Status.WAITLIST);

	public CourseIndex(String indexNo, Course course, int maxVacancy) {
		Objects.requireNonNull(indexNo);

		this.indexNo = indexNo;
		this.maxVacancy = maxVacancy;
		this.scheduleList = new ArrayList<>();
		this.registrationList = new TreeSet<>(new RegistrationComparator());
		this.registeredSet = registrationList.headSet(splitterRegistration);
		this.waitlistSet = registrationList.tailSet(splitterRegistration);

		setCourse(course);

	}

	@Override
	public String getKey() {
		return this.indexNo;
	}

	@Override
	public void setKey(String newKey) {
		Objects.requireNonNull(newKey);
		this.indexNo = newKey;
	}

	/**
	 * get indexNo of this CourseIndex.
	 * 
	 * @return indexNo of this CourseIndex
	 */
	public String getIndexNo() {
		return getKey();
	}

	/**
	 * set new indexNo of this CourseIndex.
	 * 
	 * @param indexNo new indexNo of this CourseIndex
	 * @throws NullPointerException if course index is null
	 */
	public void setIndexNo(String indexNo) {
		setKey(indexNo);
	}

	/**
	 * get Course of this CourseIndex.
	 * 
	 * @return Course of this CourseIndex
	 */
	public Course getCourse() {
		return this.course;
	}

	/**
	 * set new Course of this CourseIndex.
	 * <p>
	 * update related Course.
	 * 
	 * @param course new Course of this CourseIndex
	 */
	public void setCourse(Course course) {
		if (this.course == course) {
			return;
		}

		if (this.course != null) {
			this.course.delIndex(this);
		}

		this.course = course;

		if (this.course != null) {
			this.course.addIndex(this);
		}

	}

	/**
	 * get max vacancy of this CourseIndex.
	 * 
	 * @return max vacancy of this CourseIndex
	 */
	public int getMaxVacancy() {
		return this.maxVacancy;
	}

	/**
	 * get available vacancy of this CourseIndex.
	 * 
	 * @return available vacancy of this CourseIndex
	 */
	public int getAvailableVacancy() {
		return this.getMaxVacancy() - this.getRegisteredList().size();
	}

	/**
	 * set new max vacancy of this CourseIndex.
	 * 
	 * @param vacancy new max vacancy of this CourseIndex
	 */
	public void setMaxVacancy(int vacancy) {
		this.maxVacancy = vacancy;
	}

	/**
	 * get list of Schedule of this CourseIndex.
	 * 
	 * @return list of Schedule of this CourseIndex
	 */
	public List<Schedule> getScheduleList() {
		scheduleList.sort(Schedule.getComparator());
		return Collections.unmodifiableList(scheduleList);
	}

	/**
	 * add a Schedule into this CourseIndex.
	 * <p>
	 * update related Schedule.
	 * 
	 * @param schedule a Schedule to be added into this CourseIndex
	 * @return false if this schedule already exists in the course index, else true
	 */
	public boolean addSchedule(Schedule schedule) {
		if (this.getScheduleList().contains(schedule)) {
			return false;
		}

		this.scheduleList.add(schedule);

		schedule.setCourseIndex(this);

		return true;
	}

	/**
	 * delete a Schedule of this CourseIndex.
	 * <p>
	 * delete related Schedule.
	 * 
	 * @param schedule a Schedule to be deleted from this CourseIndex
	 * @return false if this schedule doesn't exist in the course index, else true
	 */
	public boolean delSchedule(Schedule schedule) {
		if (!this.scheduleList.contains(schedule))
			return false;

		this.scheduleList.remove(schedule);
		schedule.setCourseIndex(null);

		return true;
	}

	/**
	 * get a set of registration of this CourseIndex.
	 * 
	 * @return a set of registration of this CourseIndex
	 */
	public Collection<Registration> getAllRegistration() {
		return Collections.unmodifiableSet(registrationList);
	}

	/**
	 * get a set of registration with status REGISTERED of this CourseIndex.
	 * 
	 * @return a set of registration with status REGISTERED of this CourseIndex
	 */
	public Collection<Registration> getRegisteredList() {
		return Collections.unmodifiableSet(registeredSet);
	}

	/**
	 * get a set of registration with status WAITLIST of this CourseIndex.
	 * 
	 * @return a set of registration with status WAITLIST of this CourseIndex
	 */
	public SortedSet<Registration> getWaitList() {
		return Collections.unmodifiableSortedSet(waitlistSet);
	}

	/**
	 * get the earliest registration with status WAITLIST of this CourseIndex.
	 * 
	 * @return the earliest registration with status WAITLIST of this CourseIndex
	 */
	public Registration getEarliestWaitList() {
		return this.getWaitList().first();
	}

	/**
	 * add a registration into this CourseIndex.
	 * <p>
	 * update related registration.
	 * 
	 * @param registration a registration to be added into this CourseIndex
	 * @return false if this registration already exists in the course index, else
	 *         true
	 */
	public boolean addRegistration(Registration registration) {
		if (this.registrationList.contains(registration))
			return false;

		this.registrationList.add(registration);

		registration.setCourseIndex(this);

		return true;
	}

	/**
	 * delete a registration from this CourseIndex.
	 * <p>
	 * delete related registration.
	 * 
	 * @param registration a registration to be deleted from this CourseIndex
	 * @return false if this registration doesn't exist in the course index, else
	 *         true
	 */
	public boolean delRegistration(Registration registration) {

		if (!this.registrationList.contains(registration))
			return false;

		this.registrationList.remove(registration);

		registration.drop();

		return true;
	}

	/**
	 * Change the status of registration under this course index.
	 * <p>
	 * Other classes are compulsory to call this function to change the status of
	 * registration, so that the index can maintain the waitlist.
	 * 
	 * @param reg    the registration to change status.
	 * @param status the new status.
	 */
	public void changeRegistrationStatus(Registration reg, Status status) {
		Objects.requireNonNull(reg);
		Objects.requireNonNull(status);
		
		if (reg.getCourseIndex() != this) {
			throw new IllegalArgumentException("Course index mismatch.");
		}

		if (reg.getStatus() == status) {
			return;
		}

		registrationList.remove(reg);
		reg.setStatus(status);
		registrationList.add(reg);
	}

	@Override
	public String toString() {
		return getIndexNo();
	}
}