package cx2002grp2.stars.functions;

import java.io.File;

import cx2002grp2.stars.data.dataitem.User;

/**
 * The common interface for all the functions.
 */
public interface Function {
    /**
     * Run the function with the given user.
     * 
     * @param user the user who what to run the function.
     */
    public void run(User user);

    /**
     * Check whether the given user has access to the Function.
     * <p>
     * This method shall be implemented as final method, so that the accessibility
     * to the function cannot be change by any subclass.
     * 
     * @param user the user to check the accessibility.
     * @return true if the user has access to the function. Otherwise, return false.
     */
    abstract public boolean accessible(User user);

    /**
     * The name of the function.
     * 
     * @return the name of the function.
     */
    abstract public String name();

    /**
     * All the {@link Function} instances.
     * <p>
     * More exactly, return all the {@link AbstractFunction} instance.
     * 
     * @return A unmodifiable iterable which contains all the {@link Function}
     *         instances which is a subclass of {@link AbstractFunction}.
     */
    public static Iterable<Function> allFunctions() {
        return AbstractFunction.allFunctions();
    }

    /**
     * Scan the function package folder and initialize all function classes.
     */
    public static void init() {
        // The package prefix of all function class
        String packagePrefix = "cx2002grp2.stars.functions.";

        // The folder of function class to be stored
        File funcFolder = new File("cx2002grp2/stars/functions");

        // If the path above is invalid, try another path.
        if (!funcFolder.exists()) {
            funcFolder = new File("functions");
            // If still cannot find, throw exception.
            if (!funcFolder.exists()) {
                throw new RuntimeException("Fail to find function package's location.");
            }
        }

        // For all the file in the functions package folder.
        for (String file : funcFolder.list()) {
            // For all the file ends with .java, initialize the corresponding class.
            if (file.endsWith(".java")) {
                // cut the suffix of file name to get the class name.
                String className = file.substring(0, file.length() - ".java".length());
                String fullClassName = packagePrefix + className;
                try {
                    // initialize the function class.
                    Class.forName(fullClassName);
                } catch (ClassNotFoundException e) {
                }
            }
        }

    }
}
