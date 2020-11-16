package cx2002grp2.stars.functions;

public class StudentSwapIndex {
    /**
     * An instance of function, for Singleton pattern.
     */
    private static Function instance = new StudentSwapIndex();

    /**
     * An getter of function instance, for Singleton pattern.
     * 
     * @return an instance of function.
     */
    public static Function getInstance() {
        return instance;
    }

    /**
     * private constructor reserve for Singleton pattern
     */
    private StudentSwapIndex() {

    }

    @Override
    public boolean accessible(User user) {
        return normalStudentAccessible(user) || user.getDomain() == Domain.STAFF;
    }

    @Override
    public String name() {
        return "Test function";
    }

    @Override
    protected void implementation(User user) {
        
    }
}
