package cx2002grp2.stars.data.dataitem;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import cx2002grp2.stars.data.Gender;

/**
 * A class saving information of a student.
 * <p>
 * This class contains information of a class schedule: matricNo, gender, full
 * name, nationality, year of study, programme, list of registration.
 * <p>
 * All of attributes can be get and set through methods.
 * <p>
 * When an attribute is changed, related information in other class will also
 * change.
 */
public class Student extends User {

	private String matricNo;
	private Gender gender;
	private String fullName;
	private String nationality;
	private int yearOfStudy;
	private String programme;
	private Set<Registration> registrationList;

	public Student(User user, String matricNo, Gender gender, String fullName, String nationality, int yearOfStudy,
			String programme) {
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
	 * get MatricNo of this student.
	 * 
	 * @return MatricNo of this student
	 */
	public String getMatricNo() {
		return this.matricNo;
	}

	/**
	 * set MatricNo of this student.
	 * 
	 * @param matricNo MatricNo of this student
	 */
	public void setMatricNo(String matricNo) {
		this.matricNo = matricNo;
	}

	/**
	 * get gender of this student.
	 * 
	 * @return gender of this student
	 */
	public Gender getGender() {
		return this.gender;
	}

	/**
	 * set gender of this student.
	 * 
	 * @param gender gender of this student
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	/**
	 * get full name of this student.
	 * 
	 * @return full name of this student
	 */
	public String getFullName() {
		return this.fullName;
	}

	/**
	 * set full name of this student.
	 * 
	 * @param fullName full name of this student
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * get nationality of this student.
	 * 
	 * @return nationality of this student
	 */
	public String getNationality() {
		return this.nationality;
	}

	/**
	 * set nationality of this student.
	 * 
	 * @param nationality nationality of this student
	 */
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	/**
	 * get year of study of this student.
	 * 
	 * @return year of study of this student
	 */
	public int getYearOfStudy() {
		return yearOfStudy;
	}

	/**
	 * set year of study of this student.
	 * 
	 * @param yearOfStudy year of study of this student
	 */
	public void setYearOfStudy(int yearOfStudy) {
		this.yearOfStudy = yearOfStudy;
	}

	/**
	 * get programme of this student.
	 * 
	 * @return programme of this student
	 */
	public String getProgramme() {
		return programme;
	}

	/**
	 * set programme of this student.
	 * 
	 * @param programme programme of this student
	 */
	public void setProgramme(String programme) {
		this.programme = programme;
	}

	/**
	 * get a collection of registration.
	 * 
	 * @return a collection of registration
	 */
	public Collection<Registration> getRegistrationList() {
		return Collections.unmodifiableSet(registrationList);
	}

	/**
	 * add a registration into registration list.
	 * <p>
	 * update related registration.
	 * 
	 * @param registration a registration to be added into registration list.
	 * @return false if this registration already exists in the registration list, else true 
	 */
	public boolean addRegistration(Registration registration) {
		if (this.registrationList.contains(registration))
			return false;

		// add this new registration
		this.registrationList.add(registration);

		return true;
	}

	/**
	 * delete a registration from registration list.
	 * <p>
	 * delete related registration.
	 * 
	 * @param registration a registration to be deleted from registration list
	 * @return false if this registration doesn't exist in the registration list, else true 
	 */
	public boolean delRegistration(Registration registration) {
		if (!this.registrationList.contains(registration)) {
			return false;
		}

		this.registrationList.remove(registration);

		registration.drop();

		return true;
	}

	public double getRegisteredAU() {
		double registeredAU = 0;
        for (Registration reg : this.registrationList) {
			if (reg.getStatus() == Status.REGISTERED)
				registeredAU += reg.getCourse().getAu();
		}

		return registeredAU;
    }
}
