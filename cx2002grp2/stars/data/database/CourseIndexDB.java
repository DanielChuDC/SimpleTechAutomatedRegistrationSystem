package cx2002grp2.stars.data.database;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cx2002grp2.stars.CSV;
import cx2002grp2.stars.data.converter.Converter;
import cx2002grp2.stars.data.converter.ConverterFactory;
import cx2002grp2.stars.data.dataitem.Course;
import cx2002grp2.stars.data.dataitem.CourseIndex;
import cx2002grp2.stars.data.dataitem.Schedule;;

public class CourseIndexDB extends AbstractSingleKeyDatabase<String, CourseIndex> {

    private static final String COURSE_INDEX_DB_FILE_PATH = "tables/course_index.csv";
    private static final String SCHEDULE_DB_FILE_PATH = "tables/schedule.csv";

    private static CourseIndexDB instance = new CourseIndexDB();
    Converter<CourseIndex> converter = ConverterFactory.courseIndexConverter();
    Converter<Schedule> scheConverter = ConverterFactory.scheduleConverter();
    SimpleDatabaseLoader loader = SimpleDatabaseLoader.getLoader();

    public static CourseIndexDB getDB() {
        return instance;
    }

    protected CourseIndexDB() {
        instance = this;

        loadData();

        CourseDB.getDB().addOnItemDeletedObserver(this::doOnCourseDeleted);
        CourseDB.getDB().addOnItemAddedObserver(this::doOnCourseAdded);
    }

    private void doOnCourseDeleted(Course deletedCourse) {
        deletedCourse.getIndexList().forEach(deletedIndex -> delItem(deletedIndex));
    }

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
     *         no course can be found in the database with the given course code,
     *         return null.
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

        CSV.Reader reader = new CSV.Reader(SCHEDULE_DB_FILE_PATH);

        List<List<String>> table = reader.readData();

        for (List<String> row : table) {
            // converter will set the course index field for schedule, which means the
            // relationship between schedule and course index shall be build automatically.
            // Therefore, no further relationship handling.
            scheConverter.fromStringList(row);
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

        List<List<String>> table = new ArrayList<>(size() * 2);

        for (CourseIndex index : this) {
            for (Schedule sche : index.getScheduleList()) {
                List<String> row = scheConverter.toStringList(sche);
                table.add(row);
            }
        }

        CSV.Writer writer = new CSV.Writer(SCHEDULE_DB_FILE_PATH);

        writer.writeData(table);
    }

    public static void main(String[] args) {
        CourseIndexDB indexDB = CourseIndexDB.getDB();

        int i = 0;
        for (CourseIndex index: indexDB) {
            System.out.println(index.getIndexNo());
            if (i++ > 10) {
                break;
            }
        }
    }

}
