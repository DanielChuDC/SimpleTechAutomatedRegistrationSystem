package cx2002grp2.stars.data.converter;

import java.util.Arrays;
import java.util.List;

import cx2002grp2.stars.data.Gender;
import cx2002grp2.stars.data.database.UserDB;
import cx2002grp2.stars.data.dataitem.Student;
import cx2002grp2.stars.data.dataitem.User;

/**
 * Concrete implementation for {@link Converter converter} of {@link Student
 * Student}
 */
public class StudentConverter implements Converter<Student> {

    /**
     * Size of row of the table storing the student.
     */
    private static final int ROW_SIZE = 7;
    /**
     * Position of field in the row of table.
     */
    private static final int USER_POS = 0, MATRIC_POS = 1, GENDER_POS = 3, NAME_POS = 2, NATION_POS = 4, YEAR_POS = 5,
            PROG_POS = 6;

    @Override
    public List<String> toStringList(Student item) {
        String[] row = new String[ROW_SIZE];

        row[USER_POS] = item.getUsername();
        row[MATRIC_POS] = item.getMatricNo();
        row[GENDER_POS] = item.getGender().name();
        row[NAME_POS] = item.getFullName();
        row[NATION_POS] = item.getNationality();
        row[YEAR_POS] = String.valueOf(item.getYearOfStudy());
        row[PROG_POS] = item.getProgramme();

        return Arrays.asList(row);
    }

    @Override
    public Student fromStringList(List<String> strings) {
        String username = strings.get(USER_POS);
        User user = UserDB.getDB().getByKey(username);

        String matricNo = strings.get(MATRIC_POS);

        Gender gender = Gender.valueOf(strings.get(GENDER_POS));

        String fullName = strings.get(NAME_POS);

        String nationality = strings.get(NATION_POS);

        int yearOfStudy = Integer.parseInt(strings.get(YEAR_POS));

        String programme = strings.get(PROG_POS);

        return new Student(user, matricNo, gender, fullName, nationality, yearOfStudy, programme);
    }

}
