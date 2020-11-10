package cx2002grp2.stars.data.converter;

import cx2002grp2.stars.data.dataitem.*;

public class ConverterFactory {

    private static Converter<User> converterForUser = new UserConverter();

    public static Converter<User> forUser() {
        return converterForUser;
    }

    private static Converter<Student> converterForStudent = new StudentConverter();

    public static Converter<Student> forStudent() {
        return converterForStudent;
    }

    private static Converter<Course> converterForCourse = new CourseConverter();

    public static Converter<Course> forCourse() {
        return converterForCourse;
    }

    private static Converter<CourseIndex> converterForCourseIndex = new CourseIndexConverter();

    public static Converter<CourseIndex> forCourseIndex() {
        return converterForCourseIndex;
    }

    private static Converter<Schedule> converterForSchedule = new ScheduleConverter();

    public static Converter<Schedule> forSchedule() {
        return converterForSchedule;
    }

    private static Converter<Registration> converterForRegistration = new RegistrationConverter();

    public static Converter<Registration> forRegistration() {
        return converterForRegistration;
    }
}
