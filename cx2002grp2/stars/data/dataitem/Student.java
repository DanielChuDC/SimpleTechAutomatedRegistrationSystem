package cx2002grp2.stars.data.dataitem;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import cx2002grp2.stars.Configs;
import cx2002grp2.stars.data.Gender;
import cx2002grp2.stars.data.dataitem.Registration.Status;

/**
 * Student
 */
public class Student extends User {
    
	private String matricNo;
	private Gender gender;
	private String fullName;
	private String nationality;
	private int yearOfStudy;
	private String programme;
	private Set<Registration> registrationList;

	public Student(User user, String matricNo, Gender gender, String fullName, String nationality, int yearOfStudy, String programme) {
		super(user);

		this.matricNo = matricNo;
		this.gender = gender;
		this.fullName = fullName;
		this.nationality = nationality;
		this.yearOfStudy = yearOfStudy;
		this.programme = programme;
		this.registrationList = new HashSet<>();
	}

	@Override
	public Domain getDomain() {
		return User.Domain.STUDENT;
	}

	@Override
	public void setDomain(Domain domain) {
		throw new UnsupportedOperationException("The domain of a student cannot change.");
	}

	/**
	 * 
	 * @return
	 */
	public String getMatricNo() {
		return this.matricNo;
	}

	/**
	 * 
	 * @param matricNo
	 */
	public void setMatricNo(String matricNo) {
		this.matricNo = matricNo;
	}

	/**
	 * 
	 * @return
	 */
	public Gender getGender() {
		return this.gender;
	}

	/**
	 * 
	 * @param gender
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	/**
	 * 
	 * @return
	 */
	public String getFullName() {
		return this.fullName;
	}

	/**
	 * 
	 * @param fullName
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * 
	 * @return
	 */
	public String getNationality() {
		return this.nationality;
	}

	/**
	 * 
	 * @param nationality
	 */
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	/**
	 * 
	 * @return
	 */
	public int getYearOfStudy() {
		return yearOfStudy;
	}

	/**
	 * 
	 * @param yearOfStudy
	 */
	public void setYearOfStudy(int yearOfStudy) {
		this.yearOfStudy = yearOfStudy;
	}

	/**
	 * 
	 * @return
	 */
	public String getProgramme() {
		return programme;
	}

	/**
	 * 
	 * @param programme
	 */
	public void setProgramme(String programme) {
		this.programme = programme;
	}

    /**
     * 
     * @return
     */
	public Collection<Registration> getRegistrationList() {
        return Collections.unmodifiableSet(registrationList);
    }

	/**
	 * 
	 * @param registration
	 */
	public boolean addRegistration(Registration registration) {
		// TODO - implement Student.addRegistration

		if (this.registrationList.contains(registration))
			return false;

		// add this new registration
		this.registrationList.add(registration);

        return true;
	}

	/**
	 * 
	 * @param registration
	 */
	public boolean delRegistration(Registration registration) {
		// TODO - implement Student.delRegistration

		if (!this.registrationList.contains(registration)) {
			return false;
		}

		this.registrationList.remove(registration);
		
		

        return true;
	}

}
