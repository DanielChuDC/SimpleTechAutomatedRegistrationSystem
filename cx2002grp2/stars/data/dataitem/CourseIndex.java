package cx2002grp2.stars.data.dataitem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * 
 */
public class CourseIndex extends SingleStringKeyItem {
    
	private String indexNo;
	private Course course;
	private int maxVacancy;
	private List<Schedule> scheduleList;
	private SortedSet<Registration> registrationList;

	public CourseIndex(String indexNo, Course course, int maxVacancy) {
		this.indexNo = indexNo;
		this.course = course;
		this.maxVacancy = maxVacancy;
		this.scheduleList = new ArrayList<>();
		this.registrationList = new TreeSet<>();
	}

    @Override
	public String getKey() {
        return getIndexNo();
	}

    @Override
	public void setKey(String newKey) {
        setIndexNo(newKey);
	}

    /**
     * 
     * @return
     */
	public String getIndexNo() {
		return this.indexNo;
	}

	/**
	 * 
	 * @param indexNo
	 */
	public void setIndexNo(String indexNo) {
        this.indexNo = indexNo;
	}

	public Course getCourse() {
        return this.course;
	}

	/**
	 * 
	 * @param course
	 */
	public void setCourse(Course course) {
        // TODO - implement CourseIndex.setCourse
	}

    /**
     * 
     * @return
     */
	public int getMaxVacancy() {
		return this.maxVacancy;
	}

    /**
     * 
     * @return
     */
	public int getAvailableVacancy() {
        // TODO - implement CourseIndex.getAvailableVacancy
        return 0;
	}

	/**
	 * 
	 * @param vacancy
	 */
	public void setMaxVacancy(int vacancy) {
		this.maxVacancy = vacancy;
	}

    /**
     * 
     * @return
     */
	public List<Schedule> getScheduleList() {
        return Collections.unmodifiableList(scheduleList);
	}

	/**
	 * 
	 * @param schedule
	 */
	public boolean addSchedule(Schedule schedule) {
		// TODO - implement CourseIndex.addSchedule
		return false;
	}

	/**
	 * 
	 * @param schedule
	 */
	public boolean delSchedule(Schedule schedule) {
        // TODO - implement CourseIndex.delSchedule
        return false;
	}

    /**
     * 
     * @return
     */
	public Set<Registration> getAllRegistration() {
        return Collections.unmodifiableSet(registrationList);
	}

    /**
     * 
     * @return
     */
	public Set<Registration> getRegisteredList() {
        // TODO - implement CourseIndex.getRegisteredList
        return new HashSet<>();
	}

    /**
     * 
     * @return
     */
	public Set<Registration> getWaitList() {
		// TODO - implement CourseIndex.getWaitList
        return new HashSet<>();
	}

    /**
     * 
     * @return
     */
	public Registration getEarliestWaitList() {
		// TODO - implement CourseIndex.getEarliestWaitList
        return null;
	}

	/**
	 * 
	 * @param registration
	 */
	public boolean addRegistration(Registration registration) {
        // TODO - implement CourseIndex.addRegistration
        return false;
	}

	/**
	 * 
	 * @param registration
	 */
	public boolean delRegistration(Registration registration) {
        // TODO - implement CourseIndex.delRegistration
        return false;
	}
	
}