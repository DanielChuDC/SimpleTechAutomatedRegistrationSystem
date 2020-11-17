package cx2002grp2.stars.functions;

import cx2002grp2.stars.data.dataitem.User;
import cx2002grp2.stars.data.dataitem.User.Domain;

public class EditStudentAccessPeriod extends AbstractFunction {

    /**
     * An instance of function, for Singleton pattern.
     */
    private static Function instance = new EditStudentAccessPeriod();

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
    private EditStudentAccessPeriod() {

    }

    @Override
    public boolean accessible(User user) {
        return user.getDomain() == Domain.STAFF;
    }

    @Override
    public String name() {
        return "Edit Student Access Period";
    }

    @Override
    protected void implementation(User user) {
        System.out.print("Please input new dat and time: ");

        
    }
    
}
