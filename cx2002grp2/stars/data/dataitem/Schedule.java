package cx2002grp2.stars.data.dataitem;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * A class saving information of a specific class schedule.
 * <p>
 * This class contains information of a class schedule: CourseIndex, classType,
 * tutorial group, day of week, venue, remark, beginTime and endTime, teaching
 * weeks.
 * <p>
 * All of attributes can be get and set through methods.
 * <p>
 * When an attribute is changed, related information in other class will also
 * change.
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
		this.classType = classType;
		this.group = group;
		this.dayOfWeek = dayOfWeek;
		this.venue = venue;
		this.remark = remark;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.teachingWeeks = new TreeSet<>();

		this.setCourseIndex(courseIndex);
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
	 * 
	 * @param courseIndex the new courseIndex to be set
	 */
	public void setCourseIndex(CourseIndex courseIndex) {
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
	 * <p>
	 * The method will return the reference to teaching week set. Modifying the set
	 * returned will affect the teaching week stored in the schedule.
	 * 
	 * @return set of teaching weeks of this Schedule
	 */
	public Set<Integer> teachingWeeks() {
		return this.teachingWeeks;
	}

	private static final List<Integer> ALL_ODD_WEEKS = List.of(1, 3, 5, 7, 9, 11, 13);
	private static final List<Integer> ALL_EVEN_WEEKS = List.of(2, 4, 6, 8, 10, 12);

	/**
	 * Get teaching week expressed with string in simplified format.
	 * 
	 * @return a string representing the teaching week in simplified format.
	 */
	public String teachWkStr() {
		List<Integer> wkList = new ArrayList<>(teachingWeeks);
		wkList.sort(Integer::compare);

		// Checking special cases
		if (wkList.equals(ALL_ODD_WEEKS)) {
			return "All Odd Weeks";
		}
		
		if (wkList.equals(ALL_EVEN_WEEKS)) {
			return "All Even Weeks";
		}

		StringBuilder ret = new StringBuilder();

		int consecutiveBeg = 0;
		boolean isFirst = true;

		for (int i = 1; i <= wkList.size(); ++i) {
			// If the series is not consecutive or the parsing comes to the end.
			if (i == wkList.size() || wkList.get(i) != wkList.get(i - 1) + 1) {
				if (!isFirst) {
					ret.append(",");
				}
				if (consecutiveBeg == i-1) {
					ret.append(wkList.get(consecutiveBeg));
				} else {
					ret.append(wkList.get(consecutiveBeg)).append('~').append(wkList.get(i-1));
				}
				isFirst = false;
				consecutiveBeg = i;
			}
		}

		return ret.toString();
	}
	/**
	 * Get a compare of the schedule by comparing the class type, day of week and
	 * begin time lexicologically.
	 * <p>
	 * That is, the class type will be compare first. If the result is not equal,
	 * return the result. Otherwise, the day of week will be compared. If the result
	 * is not equal, return the result. Otherwise, the begin time will be compared,
	 * and the result will be return anyway.
	 * 
	 * @return a compare of the schedule by comparing the class type, day of week
	 *         and begin time lexicologically.
	 */
	public static Comparator<Schedule> getComparator() {
		return (schedule1, schedule2) -> {
			int result = schedule1.getClassType().compareTo(schedule2.getClassType());
			if (result != 0) {
				return result;
			}
			result = schedule1.getDayOfWeek().compareTo(schedule2.getDayOfWeek());
			if (result != 0) {
				return result;
			}
			return schedule1.getBeginTime().compareTo(schedule2.getBeginTime());
		};
	}

}