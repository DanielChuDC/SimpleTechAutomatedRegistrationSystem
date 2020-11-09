package cx2002grp2.stars.data.dataitem;

import java.util.Collections;
import java.util.Set;

/**
 * 
 */
public class Course extends SingleStringKeyItem {
    
	private String courseCode;
	private Set<CourseIndex> indexList;
	private String courseName;
	private String school;
	private double au;

    @Override
	public String getKey() {
        return getCourseCode();
	}

    @Override
	public void setKey(String newKey) {
        setCourseCode(newKey);
	}

    /**
     * 
     * @return
     */
	public String getCourseCode() {
		return this.courseCode;
	}

	/**
	 * 
	 * @param courseCode
	 */
	public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
	}

	public String getCourseName() {
		return this.courseName;
	}

	/**
	 * 
	 * @param courseName
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
    }
    
    /**
     * 
     * @return
     */
	public String getSchool() {
		return this.school;
	}

	/**
	 * 
	 * @param school
	 */
	public void setSchool(String school) {
		this.school = school;
	}

	public double getAu() {
		return this.au;
	}

	/**
	 * 
	 * @param au
	 */
	public void setAu(double au) {
		this.au = au;
	}

    /**
     * 
     * @return
     */
	public Set<CourseIndex> getIndexList() {
        return Collections.unmodifiableSet(indexList);
	}

	/**
	 * 
	 * @param courseIndex
	 */
	public boolean addIndex(CourseIndex courseIndex) {
        return false;
	}

	/**
	 * 
	 * @param courseIndex
	 */
	public boolean delIndex(CourseIndex courseIndex) {
        return false;
	}

}