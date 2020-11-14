package cx2002grp2.stars.data.converter;

import java.util.Arrays;
import java.util.List;

import cx2002grp2.stars.data.database.CourseDB;
import cx2002grp2.stars.data.dataitem.Course;
import cx2002grp2.stars.data.dataitem.CourseIndex;

/**
 * Concrete implementation for {@link Converter converter} of {@link CourseIndex
 * CourseIndex}
 */
public class CourseIndexConverter implements Converter<CourseIndex> {

    /**
     * Size of row of the table storing the course index.
     */
    private static final int ROW_SIZE = 3;
    /**
     * Position of course index field in one row of table.
     */
    private static final int INDEX_POS = 0;
    /**
     * Position of course code field in one row of table.
     */
    private static final int COURSE_POS = 1;
    /**
     * Position of max Vacancy field in one row of table.
     */
    private static final int MAXVCC_POS = 2;

    @Override
    public List<String> toStringList(CourseIndex item) {
        if (item.getCourse() == null) {
            return null;
        }
        String[] row = new String[ROW_SIZE];

        row[INDEX_POS] = item.getIndexNo();
        row[COURSE_POS] = item.getCourse().getCourseCode();
        row[MAXVCC_POS] = String.valueOf(item.getMaxVacancy());

        return Arrays.asList(row);
    }

    @Override
    public CourseIndex fromStringList(List<String> strings) {

        String indexNo = strings.get(INDEX_POS);

        String courseCode = strings.get(COURSE_POS);
        Course course = CourseDB.getDB().getByKey(courseCode);

        int maxVacancy = Integer.parseInt(strings.get(MAXVCC_POS));

        return new CourseIndex(indexNo, course, maxVacancy);
    }

}
