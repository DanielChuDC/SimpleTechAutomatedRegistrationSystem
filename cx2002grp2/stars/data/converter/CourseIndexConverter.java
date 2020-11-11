package cx2002grp2.stars.data.converter;

import java.util.Arrays;
import java.util.List;

import cx2002grp2.stars.data.database.CourseDB;
import cx2002grp2.stars.data.dataitem.Course;
import cx2002grp2.stars.data.dataitem.CourseIndex;

public class CourseIndexConverter implements Converter<CourseIndex> {

    private static final int ROW_SIZE = 3;
    private static final int INDEX_POS = 0;
    private static final int COURSE_POS = 1;
    private static final int MAXVCC_POS = 2;

    @Override
    public List<String> toStringList(CourseIndex item) {
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
