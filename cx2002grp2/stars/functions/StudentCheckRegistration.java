package cx2002grp2.stars.functions;

import cx2002grp2.stars.data.dataitem.User;

public class StudentCheckRegistration extends AbstractFunction {

    /**
     * An instance of function, for Singleton pattern.
     */
    private static Function instance = new StudentCheckRegistration();

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
    private StudentCheckRegistration() {

    }



    @Override
    public boolean accessible(User user) {
        return normalStudentAccessible(user);
    }

    @Override
    public String name() {
        return "Check Registration";
    }

    @Override
    protected void implementation(User user) {
        

    }
    
}
