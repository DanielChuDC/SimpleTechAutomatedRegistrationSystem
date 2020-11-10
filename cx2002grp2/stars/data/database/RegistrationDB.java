package cx2002grp2.stars.data.database;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import cx2002grp2.stars.data.dataitem.Course;
import cx2002grp2.stars.data.dataitem.CourseIndex;
import cx2002grp2.stars.data.dataitem.Registration;
import cx2002grp2.stars.data.dataitem.Student;

public class RegistrationDB extends AbstractDatabase<Registration> {

    private static final String DB_FILE_PATH = "tables/registration.csv";

    private static RegistrationDB instance = new RegistrationDB();

    public static RegistrationDB getDB() {
        return instance;
    }

    private RegistrationDB() {
        loadData();

        StudentDB.getDB().addOnItemDeletedObserver(this::doOnStudentDeleted);
        StudentDB.getDB().addOnKeyChangedObserver(this::doOnStudentKeyChanged);
        CourseIndexDB.getDB().addOnItemDeletedObserver(this::doOnIndexDeleted);
        CourseDB.getDB().addOnKeyChangedObserver(this::doOnCourseCodeKeyChanged);
    }

    private void doOnStudentDeleted(Student deletedStudent) {
        deletedStudent.getRegistrationList().forEach(reg->delItem(reg));
    }

    private void doOnIndexDeleted(CourseIndex deletedIndex) {
        deletedIndex.getAllRegistration().forEach(reg->delItem(reg));
    }

    private void doOnStudentKeyChanged(String oldKey, Student newStudent) {
        // impelement methods
    }

    private void doOnCourseCodeKeyChanged(String oldCourseCode, Course newCourse) {
        // impelement methods
    }

    @Override
    public boolean addItem(Registration registration) {
        // TODO - implement RegistrationDB.add
        return false;
    }

    @Override
    public boolean delItem(Registration registration) {
        // TODO - implement RegistrationDB.del
        return false;
    }

    @Override
    public boolean hasItem(Registration registration) {
        // TODO - implement RegistrationDB.contains
        return false;
    }

    @Override
    public int size() {
        // TODO - implement method
        return 0;
    }

    /**
     * 
     * @param indexNo
     * @param username
     */
    public Registration getByIndex(String indexNo, String username) {
        // TODO - implement RegistrationDB.get
        return null;
    }

    /**
     * 
     * @param courseCode
     * @param username
     */
    public Registration getByCourseCode(String courseCode, String username) {
        // TODO - implement RegistrationDB.get
        return null;
    }

    /**
     * 
     * @param index
     */
    public Collection<Registration> getRegOfIndex(String index) {
        // TODO - implement RegistrationDB.getRegOfIndex
        return Collections.emptyList();
    }

    /**
     * 
     * @param username
     */
    public Collection<Registration> getRegOfStudent(String username) {
        // TODO - implement RegistrationDB.getRegOfStudent
        return Collections.emptyList();
    }

    @Override
    public Iterator<Registration> iterator() {
        // TODO Auto-generated method stub
        return Collections.emptyIterator();
    }

    @Override
    protected void loadData() {
        // TODO - implement RegistrationDB.loadData
    }

    @Override
    protected void saveData() {
        // TODO - implement RegistrationDB.saveData
    }
}