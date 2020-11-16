package cx2002grp2.stars.data.converter;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import cx2002grp2.stars.data.database.CourseIndexDB;
import cx2002grp2.stars.data.database.StudentDB;
import cx2002grp2.stars.data.dataitem.CourseIndex;
import cx2002grp2.stars.data.dataitem.Registration;
import cx2002grp2.stars.data.dataitem.Student;
import cx2002grp2.stars.data.dataitem.Registration.Status;

/**
 * Concrete implementation for {@link Converter converter} of
 * {@link Registration Registration}
 */
public class RegistrationConverter implements Converter<Registration> {

    /**
     * Size of row of the table storing the registration.
     */
    private static final int ROW_SIZE = 4;
    /**
     * Position of field in the row of table.
     */
    private static final int STUD_POS = 0, INDEX_POS = 1, TIME_POS = 3, STAT_POS = 2;

    @Override
    public List<String> toStringList(Registration item) {
        if (item.isDropped()) {
            return null;
        }

        String[] row = new String[ROW_SIZE];

        row[STUD_POS] = item.getStudent().getUsername();
        row[INDEX_POS] = item.getCourseIndex().getIndexNo();
        row[TIME_POS] = item.getRegisterDateTime().toString();
        row[STAT_POS] = item.getStatus().name();

        return Arrays.asList(row);
    }

    @Override
    public Registration fromStringList(List<String> strings) {
        String username = strings.get(STUD_POS);
        Student student = StudentDB.getDB().getByKey(username);

        String index = strings.get(INDEX_POS);
        CourseIndex courseIndex = CourseIndexDB.getDB().getByKey(index);

        LocalDateTime registerDateTime = LocalDateTime.parse(strings.get(TIME_POS));

        Status status = Status.valueOf(strings.get(STAT_POS));

        return new Registration(student, courseIndex, registerDateTime, status);
    }

}
