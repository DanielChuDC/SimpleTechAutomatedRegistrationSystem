package cx2002grp2.stars.data.converter;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import cx2002grp2.stars.data.database.CourseIndexDB;
import cx2002grp2.stars.data.dataitem.CourseIndex;
import cx2002grp2.stars.data.dataitem.Schedule;
import cx2002grp2.stars.data.dataitem.Schedule.ClassType;

/**
 * Concrete implementation for {@link Converter converter} of {@link Schedule
 * Schedule}
 */
public class ScheduleConverter implements Converter<Schedule> {

    /**
     * Size of row of the table storing the schedule.
     */
    private static final int ROW_SIZE = 9;
    /**
     * Position of field in the row of table.
     */
    private static final int INDEX_POS = 0, TYPE_POS = 1, GROUP_POS = 2, DAY_POS = 3, VENUE_POS = 6, REMARK_POS = 8,
            BEGIN_POS = 4, END_POS = 5, WEEKS_POS = 7;

    @Override
    public List<String> toStringList(Schedule item) {
        try {
            if (item.getCourseIndex() == null) {
                return null;
            }

            String[] row = new String[ROW_SIZE];

            row[INDEX_POS] = item.getCourseIndex().getIndexNo();
            row[TYPE_POS] = item.getClassType().name();
            row[GROUP_POS] = item.getGroup();
            row[DAY_POS] = item.getDayOfWeek().name();
            row[VENUE_POS] = item.getVenue();
            row[REMARK_POS] = item.getRemark();
            row[BEGIN_POS] = item.getBeginTime().toString();
            row[END_POS] = item.getEndTime().toString();

            row[WEEKS_POS] = item.teachingWeeks().stream().map(String::valueOf).collect(Collectors.joining(","));

            return Arrays.asList(row);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Schedule fromStringList(List<String> strings) {
        try {
            String indexNo = strings.get(INDEX_POS);

            CourseIndexDB.getDB();

            CourseIndex courseIndex = CourseIndexDB.getDB().getByKey(indexNo);

            ClassType classType = ClassType.valueOf(strings.get(TYPE_POS));

            DayOfWeek dayOfWeek = DayOfWeek.valueOf(strings.get(DAY_POS));

            String group = strings.get(GROUP_POS);
            String venue = strings.get(VENUE_POS);
            String remark = strings.get(REMARK_POS);
            LocalTime beginTime = LocalTime.parse(strings.get(BEGIN_POS));
            LocalTime endTime = LocalTime.parse(strings.get(END_POS));

            String teachWkStr = strings.get(WEEKS_POS);

            Schedule ret = new Schedule(courseIndex, classType, group, dayOfWeek, venue, remark, beginTime, endTime);

            if (!teachWkStr.isBlank()) {
                for (String wkStr : teachWkStr.split(",")) {
                    ret.teachingWeeks().add(Integer.valueOf(wkStr));
                }
            }

            return ret;
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
    }
}
