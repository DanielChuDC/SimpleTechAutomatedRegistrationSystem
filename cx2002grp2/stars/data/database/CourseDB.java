package cx2002grp2.stars.data.database;

import cx2002grp2.stars.data.converter.Converter;
import cx2002grp2.stars.data.converter.ConverterFactory;
import cx2002grp2.stars.data.dataitem.Course;

public class CourseDB extends AbstractSingleKeyDatabase<String, Course> {

    private static final String DB_FILE_PATH = "tables/course.csv";

    private static CourseDB instance = new CourseDB();

    private Converter<Course> converter = ConverterFactory.courseConverter();

    private SimpleDatabaseLoader loader = SimpleDatabaseLoader.getLoader();

    protected CourseDB() {
        loadData();
    }

    public static CourseDB getDB() {
        return instance;
    }

    @Override
    protected void loadData() {
        loader.load(DB_FILE_PATH, this, converter);
    }

    @Override
    protected void saveData() {
        loader.save(this, DB_FILE_PATH, converter);
    }

}
