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
        // TODO - implement User.setDomain
        throw new UnsupportedOperationException();
    }
}