package cx2002grp2.stars.functions;

import cx2002grp2.stars.CourseAllocator.Result;
import cx2002grp2.stars.data.database.CourseIndexDB;
import cx2002grp2.stars.data.database.StudentDB;
import cx2002grp2.stars.data.dataitem.CourseIndex;
import cx2002grp2.stars.data.dataitem.Student;
import cx2002grp2.stars.data.dataitem.User;

/**
 * a function which is used to drop a course.
 * <p>
 * using singleton pattern to make sure only one object is created.
 * <p>
 * this function can be accessed by student.
 */
public class StudentDropCourse extends AbstractFunction {

    /**
     * An instance of function, for Singleton pattern.
     */
    private static Function instance = new StudentDropCourse();

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
    private StudentDropCourse() {

    }

    @Override
    public boolean accessible(User user) {
        return normalStudentAccessible(user);
    }

    @Override
    public String name() {
        return "Drop a course";
    }

    @Override
    protected void implementation(User user) {
        Student student = StudentDB.getDB().getFromUser(user);
        if (student == null) {
            System.out.println("Student doesn't exist. Please try again.");
            return;
        }

        System.out.print("Please enter course index: ");
        String rawIndex = this.sc().nextLine();

        CourseIndex courseIndex = CourseIndexDB.getDB().getByKey(rawIndex);
        if (courseIndex == null) {
            System.out.println("course Index doesn't exist. Please try again.");
            return;
        }

        this.tbPrinter().printIndexAndSchedule(courseIndex);

        if (this.askYesNo("Drop course index " + rawIndex + "?")) {
            Result result = this.allocator().dropCourse(student, courseIndex);   
            System.out.println(result.message());
        }
        else {
            System.out.println("Action cancelled.");
        }

    }
    
}
