package cx2002grp2.stars.data.dataitem;

/**
 * User
 */
public class User extends SingleStringKeyItem {

    public enum Domain {
        STUDENT, STAFF
    }

    private String username;
    private String hashedPassword;
    private User.Domain domain;
    private String email;
    private String phoneNo;

    public User(User another) {
        // TODO - implement method
    }

    @Override
    public String getKey() {
        return getUsername();
    }

    /**
     * 
     * @param newKey
     */
    @Override
    public void setKey(String newKey) {
        setUsername(newKey);
    }

    public String getUsername() {
        return this.username;
    }

    /**
     * 
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashedPassword() {
        return this.hashedPassword;
    }

    /**
     * 
     * @param newPassed
     */
    public void setHashedPassword(String newPassed) {
        this.hashedPassword = newPassed;
    }

    public User.Domain getDomain() {
        return this.domain;
    }

    /**
     * 
     * @param domain
     */
    public void setDomain(Domain domain) {
        this.domain = domain;
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

	/**
	 * 
	 * @return
	 */
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


}