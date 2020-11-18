package cx2002grp2.stars.dataitem;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A class saving information of a course.
 * <p>
 * This class contains information of a course: course code, course name,
 * school, AU, list of course index.
 * <p>
 * All of attributes can be get and set through methods.
 * <p>
 * When an attribute is changed, related information in other class will also
 * change.
 */
public class Course implements SingleKeyItem<String> {

	private String courseCode;
	private String courseName;
	private String school;
	private double au;
	private Set<CourseIndex> indexList;

	public Course(String courseCode, String courseName, String school, double au) {
		Objects.requireNonNull(courseCode);

		this.courseCode = courseCode.toUpperCase();
		this.courseName = courseName;
		this.school = school;
		this.au = au;
		this.indexList = new HashSet<>();
	}

	@Override
	public String getKey() {
		return this.courseCode;
	}

	@Override
	public void setKey(String newKey) {
		Objects.requireNonNull(newKey);
		this.courseCode = newKey;
	}

	/**
	 * get course code of this course.
	 * 
	 * @return course code of this course
	 */
	public String getCourseCode() {
		return getKey();
	}

	/**
	 * set course code of this course.
	 * 
	 * @param courseCode course code of this course
	 * @throws NullPointerException if course code is null
	 */
	public void setCourseCode(String courseCode) {
		setKey(courseCode);
	}

	/**
	 * get course name of this course.
	 * 
	 * @return course name of this course
	 */
	public String getCourseName() {
		return this.courseName;
	}

	/**
	 * set course name of this course.
	 * 
	 * @param courseName course name of this course
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * get school of this course.
	 * 
	 * @return school of this course
	 */
	public String getSchool() {
		return this.school;
	}

	/**
	 * set school of this course.
	 * 
	 * @param school school of this course
	 */
	public void setSchool(String school) {
		this.school = school;
	}

	/**
	 * get AU of this course.
	 * 
	 * @return AU of this course
	 */
	public double getAu() {
		return this.au;
	}

	/**
	 * set AU of this course.
	 * 
	 * @param au AU of this course
	 */
	public void setAu(double au) {
		this.au = au;
	}

	/**
	 * get list of course index of this course.
	 * 
	 * @return list of course index of this course
	 */
	public Collection<CourseIndex> getIndexList() {
		return Collections.unmodifiableSet(indexList);
	}

	/**
	 * add a course index into this course's list of course index.
	 * <p>
	 * update related course index.
	 * 
	 * @param courseIndex a course index to be added into the list of course index
	 * @return false if this course index already exists in the course, else true
	 */
	public boolean addIndex(CourseIndex courseIndex) {
		if (this.indexList.contains(courseIndex)) {
			return false;
		}

		this.indexList.add(courseIndex);
		courseIndex.setCourse(this);
		return true;
	}

	/**
	 * delete a course index from this course's list of course index.
	 * <p>
	 * update related course index.
	 * 
	 * @param courseIndex a course index to be deleted from this course's list of
	 *                    course index.
	 * @return false if this course index doesn't exist in the course, else true
	 */
	public boolean delIndex(CourseIndex courseIndex) {
		if (!this.indexList.contains(courseIndex))
			return false;

		this.indexList.remove(courseIndex);
		courseIndex.setCourse(null);
		return true;
	}

	@Override
	public String toString() {
		return getCourseCode();
	}
}