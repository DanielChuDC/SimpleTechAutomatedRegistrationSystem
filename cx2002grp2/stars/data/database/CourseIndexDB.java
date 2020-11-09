package cx2002grp2.stars.data.database;

import java.util.ArrayList;

import cx2002grp2.stars.data.SimpleMapLoader;
import cx2002grp2.stars.data.converter.CourseIndexConverter;
import cx2002grp2.stars.data.dataitem.CourseIndex;;

public class CourseIndexDB extends SingleStringKeyDatabase<CourseIndex> {

    private static final String DB_FILE_PATH = "tables/course_index.csv";

    private static CourseIndexDB instance = new CourseIndexDB();

    private CourseIndexDB() {
        loadData();
    }

    public static CourseIndexDB getDB() {
        return instance;
    }

    /**
     * 
     * @param courseCode
     */
    public Iterable<CourseIndex> getIndexOfCourse(String courseCode) {
        // TODO - implement method
        return new ArrayList<>();
    }

    @Override
    protected void loadData() {
        CourseIndexConverter converter = new CourseIndexConverter();
        SimpleMapLoader<CourseIndex> loader = new SimpleMapLoader<CourseIndex>();

        loader.load(DB_FILE_PATH, this.getDataMap(), converter);
    }

    @Override
    protected void saveData() {
        CourseIndexConverter converter = new CourseIndexConverter();
        SimpleMapLoader<CourseIndex> loader = new SimpleMapLoader<CourseIndex>();

        loader.save(this.getDataMap(), DB_FILE_PATH, converter);
    }

}
