package cx2002grp2.stars.data.converter;

import cx2002grp2.stars.data.dataitem.*;

/**
 * Factory class for creating data item converter.
 * @see Converter
 */
public class ConverterFactory {

    /**
     * Default {@link User User} item converter.
     */
    private static Converter<User> converterForUser = new UserConverter();

    /**
     * Get a {@link User User} item converter.
     * 
     * @return a {@link User User} item converter.
     * @see Converter
     */
    public static Converter<User> userConverter() {
        return converterForUser;
    }

    /**
     * Default {@link Student Student} item converter.
     */
    private static Converter<Student> converterForStudent = new StudentConverter();

    /**
     * Get a {@link Student Student} item converter.
     * 
     * @return a {@link Student Student} item converter.
     * @see Converter
     */
    public static Converter<Student> studentConverter() {
        return converterForStudent;
    }

    /**
     * Default {@link Course Course} item converter.
     */
    private static Converter<Course> converterForCourse = new CourseConverter();

    /**
     * Get a {@link Course Course} item converter.
     * 
     * @return a {@link Course Course} item converter.
     * @see Converter
     */
    public static Converter<Course> courseConverter() {
        return converterForCourse;
    }

    /**
     * Default {@link CourseIndex CourseIndex} item converter.
     */
    private static Converter<CourseIndex> converterForCourseIndex = new CourseIndexConverter();

    /**
     * Get a {@link CourseIndex CourseIndex} item converter.
     * 
     * @return a {@link CourseIndex CourseIndex} item converter.
     * @see Converter
     */
    public static Converter<CourseIndex> courseIndexConverter() {
        return converterForCourseIndex;
    }

    /**
     * Default {@link Schedule Schedule} item converter.
     */
    private static Converter<Schedule> converterForSchedule = new ScheduleConverter();

    /**
     * Get a {@link Schedule Schedule} item converter.
     * 
     * @return a {@link Schedule Schedule} item converter.
     * @see Converter
     */
    public static Converter<Schedule> scheduleConverter() {
        return converterForSchedule;
    }

    /**
     * Default {@link Registration Registration} item converter.
     */
    private static Converter<Registration> converterForRegistration = new RegistrationConverter();

    /**
     * Get a {@link Registration Registration} item converter.
     * 
     * @return a {@link Registration Registration} item converter.
     * @see Converter
     */
    public static Converter<Registration> registrationConverter() {
        return converterForRegistration;
    }
}
