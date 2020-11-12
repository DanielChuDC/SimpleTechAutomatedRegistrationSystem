package cx2002grp2.stars.data.database;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cx2002grp2.stars.CSV;
import cx2002grp2.stars.data.converter.Converter;
import cx2002grp2.stars.data.converter.ConverterFactory;
import cx2002grp2.stars.data.dataitem.Course;
import cx2002grp2.stars.data.dataitem.CourseIndex;
import cx2002grp2.stars.data.dataitem.Schedule;

/**
 * Database storing {@link CourseIndex}.
 * <p>
 * The database is implemented with Singleton pattern.
 * <p>
 * The following data syncing process will be handled automatically:
 * <ul>
 * <li>When a course is added, all the course indexes under the added course
 * will be added into this database.
 * <li>When a course is deleted, all the course indexes under the deleted course
 * will deleted from this database.
 * </ul>
 */
public class CourseIndexDB extends AbstractSingleKeyDatabase<String, CourseIndex> {

    /**
     * database file that course index database is storing
     */
    private static final String COURSE_INDEX_DB_FILE_PATH = "tables/course_index.csv";
    /**
     * database file that course schedule database is storing
     */
    private static final String SCHEDULE_DB_FILE_PATH = "tables/schedule.csv";

    /**
     * A unique instance of database, for Singleton pattern.
     */
    private static CourseIndexDB instance = new CourseIndexDB();

    /**
     * Get instance of database, for Singleton pattern.
     * @return the singleton course index database.
     */
    public static CourseIndexDB getDB() {
        return instance;
    }

    /**
     * Converter for converting course index item from and into string list.
     */
    private Converter<CourseIndex> converter = ConverterFactory.courseIndexConverter();
    /**
     * Converter for converting course schedule item from and into string list.
     */
    private Converter<Schedule> scheduleConverter = ConverterFactory.scheduleConverter();
    /**
     * Loader used to load database from and into file.
     */
    private SimpleDatabaseLoader loader = SimpleDatabaseLoader.getLoader();

    /**
     * Construct a database with data loaded and setup syncing process.
     */
    protected CourseIndexDB() {
        loadData();

        // When a course is added, all the course indexes under the added course will be
        // added into this database.
        CourseDB.getDB().addOnItemAddedObserver(this::doOnCourseAdded);

        // When a course is deleted, all the course indexes under the deleted course
        // will deleted from this database.
        CourseDB.getDB().addOnItemDeletedObserver(this::doOnCourseDeleted);
    }

    /**
     * Delete all the indexes under the deleted course from this database.
     * @param deletedCourse deleted course
     */
    private void doOnCourseDeleted(Course deletedCourse) {
        deletedCourse.getIndexList().forEach(deletedIndex -> delItem(deletedIndex));
    }

    /**
     * added all the indexes under the added course from this database.
     * @param addedCourse added course
     */
    private void doOnCourseAdded(Course addedCourse) {
        addedCourse.getIndexList().forEach(addedIndex -> addItem(addedIndex));
    }

    /**
     * {@inheritDoc}
     * <p>
     * If the course code of the added index is not null, add the course code into
     * the course code database to guarantee referential integrity.
     */
    @Override
    public CourseIndex addItem(CourseIndex item) {
        if (item != null && item.getCourse() != null) {
            CourseDB.getDB().addItem(item.getCourse());
        }

        return super.addItem(item);
    }

    /**
     * Get all the indexes under the given course code.
     * 
     * @param courseCode a course code of course
     * @return a collection of all the indexes under the given course code. If the
     *         no course can be found with the given course code, return null.
     */
    public Collection<CourseIndex> getIndexOfCourse(String courseCode) {
        Course course = CourseDB.getDB().getByKey(courseCode);
        if (course == null) {
            return null;
        }
        return course.getIndexList();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Both course index and course schedule will be loaded.
     */
    @Override
    protected void loadData() {
        loader.load(COURSE_INDEX_DB_FILE_PATH, this, converter);

        // set the instance to this so that the convert won't get a null from getDB()
        // when loading schedule.
        instance = this;

        CSV.Reader reader = new CSV.Reader(SCHEDULE_DB_FILE_PATH);

        List<List<String>> table = reader.readData();

        for (List<String> row : table) {
            // converter will set the course index field for schedule, which means the
            // relationship between schedule and course index shall be build automatically.
            // Therefore, no further relationship handling.
            scheduleConverter.fromStringList(row);
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Both course index and course schedule will be saved.
     */
    @Override
    protected void saveData() {
        loader.save(this, COURSE_INDEX_DB_FILE_PATH, converter);

        List<List<String>> table = new ArrayList<>();

        for (CourseIndex index : this) {
            for (Schedule schedule : index.getScheduleList()) {
                List<String> row = scheduleConverter.toStringList(schedule);
                table.add(row);
            }
        }

        CSV.Writer writer = new CSV.Writer(SCHEDULE_DB_FILE_PATH);

        writer.writeData(table);
    }
}
