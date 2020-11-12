package cx2002grp2.stars;

import java.io.Console;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

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
     * 
     * @return
     */
    public User login() {
        // TODO - implement method

        User.Domain[] allDomain = User.Domain.values();

        return null;
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
    public User createAccount(String username, String password) {
        // TODO - implement method
        return null;
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