package cx2002grp2.stars;

import java.io.Console;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;

import cx2002grp2.stars.data.database.UserDB;
import cx2002grp2.stars.data.dataitem.User;
import cx2002grp2.stars.functions.Function;

/**
 * The controller used to manage stuff about user account and password.
 */
public class Authenticator {
    /**
     * Default instance of authenticator
     */
    private static Authenticator defaultInstance = new Authenticator();

    /**
     * Get an instance of authenticator.
     * 
     * @return an instance of authenticator.
     */
    public static Authenticator getInstance() {
        return defaultInstance;
    }

    /**
     * Message digester used to hash password
     */
    private MessageDigest digester;

    /**
     * Construct an authenticator
     */
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
     * Full login process.
     * <p>
     * Interact with user and ask user to select domain, enter username and
     * password.
     * <p>
     * Existence of user and correctness of password will be checked.
     * 
     * @return the user login if the user exist in the selected domain and
     *         credentials are correct. Otherwise, return null
     */
    public User login() {
        // TODO - check registration time - if outside of time, display approriate error message

        User.Domain[] allDomain = User.Domain.values();
        boolean validDomain = false;
        @SuppressWarnings("resource")
        Scanner in = new Scanner(System.in);
        User.Domain domain = null;
        User user = null;

        // input domain
        while (validDomain == false) {
            System.out.println("Enter domain - 1 for STUDENT, 2 for STAFF");

            int domainNo;
            String inStr = in.nextLine();
            try {
                domainNo = Integer.parseInt(inStr);
            } catch (NumberFormatException e) {
                domainNo = -1;
            }

            if (domainNo == 1) domain = allDomain[0];
            else if (domainNo == 2) domain = allDomain[1];
            else {
                System.out.println("Invalid domain choice. Login again.");
                continue;
            }
            validDomain = true;
        }

        user = login(domain);

        return user;
    }

    /**
     * Login process for a specific domain.
     * <p>
     * Interact with user and ask user to enter username and password.
     * <p>
     * Existence of user and correctness of password will be checked.
     * 
     * @param useDomain the specified domain.
     * @return the user login if the user exist in the specified domain and
     *         credentials are correct. Otherwise, return null
     */
    public User login(User.Domain useDomain) {
        // TODO - check registration time - if outside of time, display approriate error message

        @SuppressWarnings("resource")
        Scanner in = new Scanner(System.in);
        Console console = System.console();
        User user = null;

        // input username, password and domain
        // read username
        System.out.println("Enter username: ");
        String username = in.nextLine();

        // read password - must not echo characters
        char[] passwordEntry = console.readPassword("Enter password: ");
        String password = String.valueOf(passwordEntry);

        // get user from database using the username
        // if user does not exist in db, or if hashed password not the same,
        // or if domain does not match, login again
        user = UserDB.getDB().getByKey(username);
        String encoded = hash(password);
        if (user == null || encoded.equals(user.getHashedPassword()) || user.getDomain() != useDomain) {
            System.out.println("Invalid username or password. Login again.");
        }

        return user;
    }

    /**
     * Get the functions that are accessible to the given user.
     * 
     * @param user a user
     * @return A list of functions that are accessible to the given user.
     */
    public List<Function> accessibleFunctions(User user) {
        List<Function> result = new ArrayList<>();

        // push into result accessible functions
        for (Function func : Function.allFunctions()) {
            if (func.accessible(user)) {
                result.add(func);
            }
        }

        return result;
    }

    /**
     * Create an account with the given username and password.
     * 
     * @param username the username of new account.
     * @param password the password of new account.
     * @return a new user with the given username and password.
     * @throws IllegalArgumentException If the username already exists in the user
     *                                  database.
     */
    public User createAccount(String username, String password, User.Domain domain, String email, String phoneNo) {
        // Check if username already exist
        User user_exist = UserDB.getDB().getByKey(username);

        if (user_exist != null) {
            throw new IllegalArgumentException("Account with that username already exists");
        }

        // Encode password using hash function
        String encoded = hash(password);

        // Create new user
        User user = new User(username, encoded, domain, email, phoneNo);

        // Add user into database
        UserDB.getDB().addItem(user);

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

        // Encode password using hash function
        String encoded = hash(password);

        // Check if user exists, if no return false
        if (user != null && user.getKey() != null) {
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
     * @return the hash code of input plain text encoded with Base64
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