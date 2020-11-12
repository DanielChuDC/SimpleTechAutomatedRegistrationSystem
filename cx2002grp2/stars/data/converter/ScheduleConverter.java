package cx2002grp2.stars.data.converter;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import cx2002grp2.stars.data.database.CourseIndexDB;
import cx2002grp2.stars.data.dataitem.CourseIndex;
import cx2002grp2.stars.data.dataitem.Schedule;
import cx2002grp2.stars.data.dataitem.Schedule.ClassType;

public class ScheduleConverter implements Converter<Schedule> {

    private static final int ROW_SIZE = 9;
    private static final int INDEX_POS = 0;
    private static final int TYPE_POS = 1;
    private static final int GROUP_POS = 2;
    private static final int DAY_POS = 3;
    private static final int VENUE_POS = 6;
    private static final int REMARK_POS = 8;
    private static final int BEGIN_POS = 4;
    private static final int END_POS = 5;
    private static final int WEEKS_POS = 7;

    @Override
    public List<String> toStringList(Schedule item) {
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
    }

    @Override
    public Schedule fromStringList(List<String> strings) {
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
        Set<Integer> teachingWeeks = new TreeSet<>();

        if (!teachWkStr.isBlank()) {
            for (String wkStr : teachWkStr.split(",")) {
                teachingWeeks.add(Integer.valueOf(wkStr));
            }
        }

        return new Schedule(courseIndex, classType, group, dayOfWeek, venue, remark, beginTime, endTime);
    }

    public static void main(String[] args) {
    }
}
