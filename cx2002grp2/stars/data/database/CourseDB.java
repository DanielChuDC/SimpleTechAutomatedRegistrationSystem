package cx2002grp2.stars.data.database;

import cx2002grp2.stars.data.converter.CourseConverter;
import cx2002grp2.stars.data.dataitem.Course;
import cx2002grp2.stars.data.SimpleMapLoader;

public class CourseDB extends SingleStringKeyDatabase<Course> {

    private static final String DB_FILE_PATH = "tables/course.csv";

    @Override
    protected void loadData() {
        CourseConverter converter = new CourseConverter();
        SimpleMapLoader<Course> loader = new SimpleMapLoader<Course>();

        loader.load(DB_FILE_PATH, this.getDataMap(), converter);
    }

    @Override
    protected void saveData() {
        CourseConverter converter = new CourseConverter();
        SimpleMapLoader<Course> loader = new SimpleMapLoader<Course>();

        loader.save(this.getDataMap(), DB_FILE_PATH, converter);
    }
    
}
