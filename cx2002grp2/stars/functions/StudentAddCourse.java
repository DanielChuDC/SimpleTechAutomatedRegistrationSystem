package cx2002grp2.stars.functions;

import cx2002grp2.stars.CourseAllocator;
import cx2002grp2.stars.data.database.CourseDB;
import cx2002grp2.stars.data.dataitem.Course;
import cx2002grp2.stars.data.dataitem.User;

public class StudentAddCourse extends AbstractFunction {

    /**
     * An instance of function, for Singleton pattern.
     */
    private static Function instance = new StudentAddCourse();

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
    private StudentAddCourse() {

    }

    @Override
    public boolean accessible(User user) {
        return normalStudentAccessible(user);
    }

    @Override
    public String name() {
        return "Add a course";
    }

    @Override
    protected void implementation(User user) {
        System.out.print("Please enter the course code: ");
        String rawCourseCode = this.sc().nextLine();

        Course course = CourseDB.getDB().getByKey(rawCourseCode);
        if (course == null) {
            System.out.println("Course Code doesn't exist. Please try again.");
            return;
        }
        
        CourseAllocator courseAllocator = new 
    }
    
}
