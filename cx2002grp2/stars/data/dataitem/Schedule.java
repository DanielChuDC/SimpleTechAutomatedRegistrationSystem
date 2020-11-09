package cx2002grp2.stars.data.dataitem;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

/**
 * Schedule
 */
public class Schedule {
    public enum ClassType { LEC, TUT, SEM, LAB, DES, PRJ }
    
	private CourseIndex courseIndex;
	private Schedule.ClassType classType;
	private String group;
	private DayOfWeek dayOfWeek;
	private String venue;
	private String remark;
	private LocalTime beginTime;
	private LocalTime endTime;
	private Set<Integer> teachingWeeks;

    /**
     * 
     */
	public CourseIndex getCourseIndex() {
        return this.courseIndex;
	}

	/**
	 * 
	 * @param courseIndex
	 */
	public void setCourseIndex(CourseIndex courseIndex) {
        // TODO - implement Schedule.setCourseIndex
	}

	public Schedule.ClassType getClassType() {
		return this.classType;
	}

	/**
	 * 
	 * @param classType
	 */
	public void setClassType(Schedule.ClassType classType) {
		this.classType = classType;
	}

	public String getGroup() {
		return this.group;
	}

	/**
	 * 
	 * @param group
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	public DayOfWeek getDayOfWeek() {
		return this.dayOfWeek;
	}

	/**
	 * 
	 * @param dayOfWeek
	 */
	public void setDayOfWeek(DayOfWeek dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public String getVenue() {
		return this.venue;
	}

	/**
	 * 
	 * @param venue
	 */
	public void setVenue(String venue) {
		this.venue = venue;
	}

	public String getRemark() {
		return this.remark;
	}

	/**
	 * 
	 * @param remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public LocalTime getBeginTime() {
		return this.beginTime;
	}

	/**
	 * 
	 * @param beginTime
	 */
	public void setBeginTime(LocalTime beginTime) {
		this.beginTime = beginTime;
	}

	public LocalTime getEndTime() {
		return this.endTime;
	}

	/**
	 * 
	 * @param endTime
	 */
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public Set<Integer> teachingWeeks() {
        return this.teachingWeeks;
	}
}