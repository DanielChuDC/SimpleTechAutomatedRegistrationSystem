package cx2002grp2.stars.dataconverter;

import java.util.Arrays;
import java.util.List;

import cx2002grp2.stars.database.CourseDB;
import cx2002grp2.stars.dataitem.Course;
import cx2002grp2.stars.dataitem.CourseIndex;

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
     * Position of field in the row of table.
     */
    private static final int INDEX_POS = 0, COURSE_POS = 1, MAX_VCC_POS = 2;

    @Override
    public List<String> toStringList(CourseIndex item) {
        try {
            if (item.getCourse() == null) {
                return null;
            }
            String[] row = new String[ROW_SIZE];

            row[INDEX_POS] = item.getIndexNo();
            row[COURSE_POS] = item.getCourse().getCourseCode();
            row[MAX_VCC_POS] = String.valueOf(item.getMaxVacancy());

            return Arrays.asList(row);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public CourseIndex fromStringList(List<String> strings) {

        try {
            String indexNo = strings.get(INDEX_POS);

            String courseCode = strings.get(COURSE_POS);
            Course course = CourseDB.getDB().getByKey(courseCode);

            int maxVacancy = Integer.parseInt(strings.get(MAX_VCC_POS));

            return new CourseIndex(indexNo, course, maxVacancy);
        } catch (Exception e) {
            return null;
        }
    }

}
