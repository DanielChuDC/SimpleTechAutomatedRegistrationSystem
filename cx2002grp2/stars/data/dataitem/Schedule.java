package cx2002grp2.stars.data.dataitem;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;
import java.util.TreeSet;

/**
 * A class saving information of a specific class schedule.
 * <p>
 * This class contains information of a class schedule: CourseIndex, classType, tutorial group, 
 * day of week, venue, remark, beginTime and endTime, teaching weeks.
 * <p>
 * All of attributes can be get and set through methods.
 * <p>
 * When an attribute is changed, related information in other class will also change.
 */
public class Schedule {

	public enum ClassType {
		LEC, TUT, SEM, LAB, DES, PRJ
	}

	private CourseIndex courseIndex;
	private Schedule.ClassType classType;
	private String group;
	private DayOfWeek dayOfWeek;
	private String venue;
	private String remark;
	private LocalTime beginTime;
	private LocalTime endTime;
	private Set<Integer> teachingWeeks;

	public Schedule(CourseIndex courseIndex, ClassType classType, String group, DayOfWeek dayOfWeek, String venue,
			String remark, LocalTime beginTime, LocalTime endTime) {
		this.courseIndex = courseIndex;
		this.classType = classType;
		this.group = group;
		this.dayOfWeek = dayOfWeek;
		this.venue = venue;
		this.remark = remark;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.teachingWeeks = new TreeSet<>();
	}

	/**
	 * get {@link CourseIndex} of this {@link Schedule}.
	 * 
	 * @return {@link CourseIndex} of this {@link Schedule}
	 */
	public CourseIndex getCourseIndex() {
		return this.courseIndex;
	}

	/**
	 * set new CourseIndex of this {@link Schedule}.
	 * <p> 
	 * update related courseIndex.
	 * @param courseIndex the new courseIndex to be set
	 */
	public void setCourseIndex(CourseIndex courseIndex) {
		// TODO - implement Schedule.setCourseIndex

		if (courseIndex == this.courseIndex)
			return;

		if (this.courseIndex != null)
			this.courseIndex.delSchedule(this);

		this.courseIndex = courseIndex;
		if (this.courseIndex != null)
			this.courseIndex.addSchedule(this);
	}

	/**
	 * get {@link Schedule.ClassType} of this {@link Schedule}.
	 * 
	 * @return {@link Schedule.ClassType} of this {@link Schedule}
	 */
	public Schedule.ClassType getClassType() {
		return this.classType;
	}

	/**
	 * set new {@link Schedule.ClassType} of this {@link Schedule}
	 * 
	 * @param classType the new {@link Schedule.ClassType} to be set
	 */
	public void setClassType(Schedule.ClassType classType) {
		this.classType = classType;
	}

	/**
	 * get tutorial group of this Schedule.
	 * 
	 * @return a string representing tutorial group of this Schedule
	 */
	public String getGroup() {
		return this.group;
	}

	/**
	 * set new tutorial group of this Schedule.
	 * 
	 * @param group a string representing new tutorial group of this Schedule
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 * get DayOfWeek of this Schedule.
	 * 
	 * @return DayOfWeek of this Schedule
	 */
	public DayOfWeek getDayOfWeek() {
		return this.dayOfWeek;
	}

	/**
	 * set new DayOfWeek of this Schedule.
	 * 
	 * @param dayOfWeek new DayOfWeek of this Schedule
	 */
	public void setDayOfWeek(DayOfWeek dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	/**
	 * get venue of this Schedule.
	 * 
	 * @return a string representing venue of this Schedule
	 */
	public String getVenue() {
		return this.venue;
	}

	/**
	 * set venue of this Schedule.
	 * 
	 * @param venue a string representing new venue of this Schedule
	 */
	public void setVenue(String venue) {
		this.venue = venue;
	}

	/**
	 * get remark of this Schedule.
	 * 
	 * @return a string representing remark of this Schedule
	 */
	public String getRemark() {
		return this.remark;
	}

	/**
	 * set new remark of this Schedule.
	 * 
	 * @param remark a string representing new remark of this Schedule
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * get begin time of this Schedule.
	 * 
	 * @return begin time of this Schedule
	 */
	public LocalTime getBeginTime() {
		return this.beginTime;
	}

	/**
	 * set new begin time of this Schedule.
	 * 
	 * @param beginTime new begin time of this Schedule
	 */
	public void setBeginTime(LocalTime beginTime) {
		this.beginTime = beginTime;
	}

	/**
	 * get end time of this Schedule.
	 * 
	 * @return end time of this Schedule
	 */
	public LocalTime getEndTime() {
		return this.endTime;
	}

	/**
	 * set end time of this Schedule.
	 * 
	 * @param endTime new end time of this Schedule
	 */
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	/**
	 * get set of teaching weeks of this Schedule.
	 * 
	 * @return set of teaching weeks of this Schedule
	 */
	public Set<Integer> teachingWeeks() {
		return this.teachingWeeks;
	}
}