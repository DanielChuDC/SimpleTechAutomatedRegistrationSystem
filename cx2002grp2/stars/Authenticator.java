package cx2002grp2.stars;

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
    /**
     * 
     * @param useDomain
     * @return
     */
    public User login() {
        return null;
    }

    /**
     * 
     * @param useDomain
     * @return
     */
    public User login(User.Domain useDomain) {
        return null;
    }

    /**
     * 
     * @param user
     */
    public List<Function> accessibleFunctions(User user) {
        return new ArrayList<>();
    }

    private String hashPasswd(String passwd) {
        // Get SHA-256 Hasher
        MessageDigest digester;
        try {
            digester = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        // Convert the string to bytes, and do the hashing
        byte[] bytesToHash = passwd.getBytes(StandardCharsets.UTF_8);
        byte[] hashedBytes = digester.digest(bytesToHash);

        // Encoding the bytes with Base64 for textual storage
        String encoded = Base64.getEncoder().encodeToString(hashedBytes);

        return encoded;
    }
}