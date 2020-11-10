package cx2002grp2.stars.data.database;

import java.util.ArrayList;
import java.util.Collection;

import cx2002grp2.stars.data.dataitem.Course;
import cx2002grp2.stars.data.dataitem.CourseIndex;;

public class CourseIndexDB extends AbstractSingleKeyDatabase<String, CourseIndex> {

    private static final String DB_FILE_PATH = "tables/course_index.csv";

    private static CourseIndexDB instance = new CourseIndexDB();

    private CourseIndexDB() {
        loadData();

        CourseDB.getDB().addOnItemDeletedObserver(this::doOnCourseDeleted);
        CourseDB.getDB().addOnItemAddedObserver(this::doOnCourseAdded);
    }

    public static CourseIndexDB getDB() {
        return instance;
    }

    private void doOnCourseDeleted(Course deletedCourse) {
        deletedCourse.getIndexList().forEach(deletedIndex->delItem(deletedIndex));
    }

    private void doOnCourseAdded(Course addedCourse) {
        addedCourse.getIndexList().forEach(addedIndex->addItem(addedIndex));
    }

    /**
     * 
     * @param courseCode
     */
    public Collection<CourseIndex> getIndexOfCourse(String courseCode) {
        // TODO - implement method
        return new ArrayList<>();
    }

    @Override
    protected void loadData() {
        // TODO - implement method
    }

    @Override
    protected void saveData() {
        // TODO - implement method
    }

}
