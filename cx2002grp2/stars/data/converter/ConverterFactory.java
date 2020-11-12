package cx2002grp2.stars.data.converter;

import cx2002grp2.stars.data.dataitem.*;

public class ConverterFactory {

    private static Converter<User> converterForUser = new UserConverter();

    public static Converter<User> userConverter() {
        return converterForUser;
    }

    private static Converter<Student> converterForStudent = new StudentConverter();

    public static Converter<Student> studentConverter() {
        return converterForStudent;
    }

    private static Converter<Course> converterForCourse = new CourseConverter();

    public static Converter<Course> courseConverter() {
        return converterForCourse;
    }

    private static Converter<CourseIndex> converterForCourseIndex = new CourseIndexConverter();

    public static Converter<CourseIndex> courseIndexConverter() {
        return converterForCourseIndex;
    }

    private static Converter<Schedule> converterForSchedule = new ScheduleConverter();

    public static Converter<Schedule> scheduleConverter() {
        return converterForSchedule;
    }

    private static Converter<Registration> converterForRegistration = new RegistrationConverter();

    public static Converter<Registration> registrationConverter() {
        return converterForRegistration;
    }
}
