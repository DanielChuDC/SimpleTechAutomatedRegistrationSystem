package cx2002grp2.stars.data.converter;

import java.util.List;

import cx2002grp2.stars.data.dataitem.Course;

public class CourseConverter implements Converter<Course> {

    @Override
    public List<String> toStringList(Course course) {
        // TODO add converter from course to table row.
        throw new UnsupportedOperationException("The function haven't been implemented");
    }

    @Override
    public Course fromStringList(List<String> strings) {
        // TODO add converter from table row to course.
        throw new UnsupportedOperationException("The function haven't been implemented");
    }
    
}
