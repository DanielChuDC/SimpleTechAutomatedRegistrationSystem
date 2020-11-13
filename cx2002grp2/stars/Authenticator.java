package cx2002grp2.stars;

import java.io.Console;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import cx2002grp2.stars.data.database.UserDB;
import cx2002grp2.stars.data.dataitem.User;
import cx2002grp2.stars.functions.Function;

/**
 * 
 */
public class Authenticator {
    private static Authenticator instance = new Authenticator();

    public static Authenticator getInstance() {
        return instance;
    }

    private MessageDigest digester;

    private Authenticator() {
        // TODO - Add initialization code if necessary.

        // Get SHA-256 Hasher
        try {
            digester = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param username username given
     * @param password password given
     * @return user with correct credentials or null if wrong
     * @throws IllegalArgumentException If the username does not exist in database or if password is wrong
     */
    public User login(String username, String password) {
        // TODO - implement method

        User.Domain[] allDomain = User.Domain.values();

        // get user from database using the username
        User user = UserDB.getDB().getByKey(username);
        if (user == null) {
            throw new IllegalArgumentException("Account with that username does not exist");
        }

        // hash given password and check if hash is same
        String encoded = hash(password);

        if (encoded != user.getHashedPassword()) {
            throw new IllegalArgumentException("Wrong password");
        }

        return user;
    }

    /**
     * 
     * @param useDomain
     * @return
     */
    public User login(User.Domain useDomain) {
        // TODO - implement method

        Console console = System.console();

        return null;
    }

    /**
     * 
     * @param user
     * @return
     */
    public List<Function> accessibleFunctions(User user) {
        // TODO - implement method
        List<Function> result = new ArrayList<>();

        // push into result accessible functions
        for (Function func : Function.allFunctions()) {

        }

        return result;
    }

    /**
     * Create an account with the given username and password.
     * 
     * @param username the username of new account.
     * @param password the password of new account.
     * @return a new user with the given username and password.
     * @throws IllegalArgumentException If the username already exists in the
     *                                  user database.
     */
    public User createAccount(String username, String password, User.Domain domain, String email, String phoneNo) {
        // TODO - implement method

        // Check if username already exist
        User user_exist = UserDB.getDB().getByKey(username);
        if (user_exist != null) {
            throw new IllegalArgumentException("Account with that username already exists");
        }

        // Encode password using hash function
        String encoded = hash(password);

        // Create new user
        User user = new User(username, password, domain, email, phoneNo);

        return user;
    }

    /**
     * Change the password of a user to the given password.
     * 
     * @param user     the user whose password need to be changed.
     * @param password the new password in plain text.
     * @return whether the password is changed successfully.
     */
    public boolean changePassword(User user, String password) {
        // TODO - implement method

        // Encode password using hash function
        String encoded = hash(password);

        // Check if user exists, if no return false
        if (user.getKey()!=null) {
            // Set new password for user
            user.setHashedPassword(encoded);
            return true;
        }

        return false;
    }

    /**
     * Hashing function used to hash string.
     * <p>
     * SHA-256 is used to hash the string and the result is encoded into Base64
     * string.
     * 
     * @param plainText the plain text to be hashed.
     * @return
     */
    private String hash(String plainText) {

        // Convert the string into bytes
        byte[] bytesToHash = plainText.getBytes(StandardCharsets.UTF_8);

        // Do hashing
        byte[] hashedBytes = digester.digest(bytesToHash);

        // Encoding the bytes with Base64 for textual storage
        String encoded = Base64.getEncoder().encodeToString(hashedBytes);

        return encoded;
    }
}