package cx2002grp2.stars;

import java.io.Console;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import cx2002grp2.stars.data.dataitem.User;
import cx2002grp2.stars.functions.AbstractFunction;
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

        for (Function func: AbstractFunction.allFunctions()) {

        }

        return result;
    }

    /**
     * Hashing function used to hash a password.
     * <p>
     * SHA-256 is used to hash the password and the result is encoded into Base64
     * string.
     * 
     * @param password the password to be hashed.
     * @return
     */
    private String hashPasswprd(char[] password) {

        // Convert the string into bytes
        byte[] bytesToHash = String.valueOf(password).getBytes(StandardCharsets.UTF_8);

        // Do hashing
        byte[] hashedBytes = digester.digest(bytesToHash);

        // Encoding the bytes with Base64 for textual storage
        String encoded = Base64.getEncoder().encodeToString(hashedBytes);

        return encoded;
    }
}