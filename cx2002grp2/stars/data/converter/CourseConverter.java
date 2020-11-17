package cx2002grp2.stars.data.converter;

import java.util.Arrays;
import java.util.List;

import cx2002grp2.stars.data.dataitem.Course;

/**
 * Concrete implementation for {@link Converter converter} of {@link Course
 * Course}
 */
public class CourseConverter implements Converter<Course> {

    /**
     * Size of row of the table storing the course.
     */
    private static final int ROW_SIZE = 4;
    /**
     * Position of field in the row of table.
     */
    private static final int CODE_POS = 0, TITLE_POS = 1, AU_POS = 2, SCHOOL_POS = 3;

    @Override
    public List<String> toStringList(Course item) {
        String[] row = new String[ROW_SIZE];

        row[CODE_POS] = item.getCourseCode();
        row[TITLE_POS] = item.getCourseName();
        row[AU_POS] = String.valueOf(item.getAu());
        row[SCHOOL_POS] = item.getSchool();

        return Arrays.asList(row);
    }

    @Override
    public Course fromStringList(List<String> strings) {
        String courseCode, courseName, school;
        double au;

        courseCode = strings.get(CODE_POS);
        courseName = strings.get(TITLE_POS);
        school = strings.get(SCHOOL_POS);
        au = Double.parseDouble(strings.get(AU_POS));

        return new Course(courseCode, courseName, school, au);
    }

}
