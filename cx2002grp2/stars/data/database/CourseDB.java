package cx2002grp2.stars.data.database;

import cx2002grp2.stars.data.converter.Converter;
import cx2002grp2.stars.data.converter.ConverterFactory;
import cx2002grp2.stars.data.dataitem.Course;
import cx2002grp2.stars.data.SimpleDatabaseLoader;

public class CourseDB extends AbstractSingleKeyDatabase<String, Course> {

    private static final String DB_FILE_PATH = "tables/course.csv";

    private static CourseDB instance = new CourseDB();

    protected CourseDB() {
        loadData();
    }

    public static CourseDB getDB() {
        return instance;
    }

    @Override
    protected void loadData() {
        Converter<Course> converter = ConverterFactory.forCourse();
        SimpleDatabaseLoader<Course> loader = new SimpleDatabaseLoader<Course>();

        loader.load(DB_FILE_PATH, this, converter);
    }

    @Override
    protected void saveData() {
        Converter<Course> converter = ConverterFactory.forCourse();
        SimpleDatabaseLoader<Course> loader = new SimpleDatabaseLoader<Course>();

        loader.save(this, DB_FILE_PATH, converter);
    }

}
