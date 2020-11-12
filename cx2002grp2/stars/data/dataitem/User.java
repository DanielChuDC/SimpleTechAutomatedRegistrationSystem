package cx2002grp2.stars.data.dataitem;

/**
 * A class saving information of a user.
 * <p>
 * This class contains information of a user: username, hashed password, domain,
 * email, phone No.
 * <p>
 * All of attributes can be get and set through methods.
 * <p>
 * When an attribute is changed, related information in other class will also
 * change.
 */
public class User implements SingleKeyItem<String> {

    public enum Domain {
        STUDENT, STAFF
    }

    private String username;
    private String hashedPassword;
    private User.Domain domain;
    private String email;
    private String phoneNo;

    public User(User other) {
        this(other.username, other.hashedPassword, other.domain, other.email, other.phoneNo);
    }

    public User(String username, String hashedPassword, User.Domain domain, String email, String phoneNo) {
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.domain = domain;
        this.email = email;
        this.phoneNo = phoneNo;
    }

    @Override
    public String getKey() {
        return this.username;
    }

    /**
     * 
     * @param newKey
     */
    @Override
    public void setKey(String newKey) {
        username = newKey;
    }

    public String getUsername() {
        return getKey();
    }

    /**
     * 
     * @param username
     */
    public void setUsername(String username) {
        setKey(username);
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