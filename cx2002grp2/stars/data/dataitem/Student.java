package cx2002grp2.stars.data.dataitem;

import java.util.Collections;
import java.util.Set;
import java.util.SortedSet;

import cx2002grp2.stars.data.Gender;

/**
 * Student
 */
public class Student extends User {
    
	private String matricNo;
	private Gender gender;
	private String fullName;
    private String nationality;
    private String email;
    private String phoneNo;
	private Set<Registration> registrationList;

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
    public String getEmail() {
        return email;
    }

    /**
     * 
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    /**
     * 
     * @param phoneNo
     */
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    /**
     * 
     * @return
     */
	public Set<Registration> getRegistrationList() {
        return Collections.unmodifiableSet(registrationList);
    }

	/**
	 * 
	 * @param regitration
	 */
	public boolean addRegistration(Registration regitration) {
        // TODO - implement Student.addRegistration
        return false;
	}

	/**
	 * 
	 * @param registration
	 */
	public boolean delRegistration(Registration registration) {
        // TODO - implement Student.delRegistration
        return false;
	}

}
