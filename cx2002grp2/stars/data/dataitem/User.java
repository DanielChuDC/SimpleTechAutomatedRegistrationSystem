package cx2002grp2.stars.data.dataitem;

import java.util.Objects;

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

    /**
     * this enumeration represents the login domain of user.
     */
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
        Objects.requireNonNull(username);
        Objects.requireNonNull(hashedPassword);
        
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

    @Override
    public void setKey(String newKey) {
        username = newKey;
    }

    /**
     * get username of this user.
     * 
     * @return username of this user
     */
    public String getUsername() {
        return getKey();
    }

    /**
     * set username of this user.
     * 
     * @param username username of this user
     */
    public void setUsername(String username) {
        setKey(username);
    }

    /**
     * get hashed password of this user.
     * 
     * @return hashed password of this user
     */
    public String getHashedPassword() {
        return this.hashedPassword;
    }

    /**
     * set hashed password of this user.
     * 
     * @param newPassed hashed password of this user
     */
    public void setHashedPassword(String newPassed) {
        this.hashedPassword = newPassed;
    }

    /**
     * get domain of this user.
     * 
     * @return domain of this user
     */
    public User.Domain getDomain() {
        return this.domain;
    }

    /**
     * set domain of this user.
     * 
     * @param domain domain of this user
     */
    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    /**
     * get email of this user.
     * 
     * @return email of this user
     */
    public String getEmail() {
        return email;
    }

    /**
     * set email of this user.
     * 
     * @param email email of this user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * get phone no of this user.
     * 
     * @return phone no of this user
     */
    public String getPhoneNo() {
        return phoneNo;
    }

    /**
     * set phone no of this user.
     * 
     * @param phoneNo phone no of this user
     */
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    @Override
    public String toString() {
        return getUsername();
    }
}