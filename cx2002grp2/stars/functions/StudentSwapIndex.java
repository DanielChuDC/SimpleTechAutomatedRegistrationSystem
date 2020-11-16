package cx2002grp2.stars.functions;

public class StudentSwapIndex extends AbstractFunction{
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
        // user1 - current user // user2 - user to swop with
        // ask for user1's index number

        // check if user1 is taking the index number

        // second student login - login(domain)
        // max 3 tries before exiting function? 

        // check if user2 is taking the index number

        // if all checks successful, table printer the swopping 
        
        // ask to confirm, swap indexes if yes. if no, return;
        // print successful
        System.out.println("MATRIC_NUMBER-Index Number INDEX has been successfuly swopped with MATRIC_NUMBER-Index Number INDEX.");
    }
}
