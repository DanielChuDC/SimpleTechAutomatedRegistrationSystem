package cx2002grp2.stars.data.database;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import cx2002grp2.stars.data.converter.Converter;
import cx2002grp2.stars.data.converter.ConverterFactory;
import cx2002grp2.stars.data.dataitem.Course;
import cx2002grp2.stars.data.dataitem.CourseIndex;
import cx2002grp2.stars.data.dataitem.Registration;
import cx2002grp2.stars.data.dataitem.Student;
import cx2002grp2.stars.util.Pair;

public class RegistrationDB extends AbstractDatabase<Registration> {

    private static final String DB_FILE_PATH = "tables/registration.csv";

    private static RegistrationDB instance = new RegistrationDB();

    private Converter<Registration> converter = ConverterFactory.registrationConverter();

    private SimpleDatabaseLoader loader = SimpleDatabaseLoader.getLoader();

    private Map<Pair<String, String>, Registration> regMap = new HashMap<>();

    public static RegistrationDB getDB() {
        return instance;
    }

    protected RegistrationDB() {
        loadData();

        StudentDB.getDB().addOnItemDeletedObserver(this::doOnStudentDeleted);
        StudentDB.getDB().addOnKeyChangedObserver(this::doOnStudentKeyChanged);
        CourseIndexDB.getDB().addOnItemDeletedObserver(this::doOnIndexDeleted);
        CourseDB.getDB().addOnKeyChangedObserver(this::doOnCourseCodeKeyChanged);
    }

    @Override
    public Registration addItem(Registration reg) {
        Pair<String, String> keyPair = makeKey(reg);

        Registration ret = regMap.put(keyPair, reg);

        if (ret != null) {
            signalItemAdded(ret);
        }

        return ret;
    }

    @Override
    public Registration delItem(Registration reg) {
        Pair<String, String> keyPair;

        try {
            keyPair = makeKey(reg);
        } catch (NullPointerException e) {
            return null;
        }

        Registration ret = regMap.remove(keyPair);

        if (ret != null) {
            signalItemDeleted(ret);
        }

        return ret;
    }

    @Override
    public boolean hasItem(Registration reg) {
        Pair<String, String> keyPair;

        try {
            keyPair = makeKey(reg);
        } catch (NullPointerException e) {
            return false;
        }

        return regMap.containsKey(keyPair);
    }

    @Override
    public int size() {
        return regMap.size();
    }

    /**
     * 
     * @param indexNo
     * @param username
     */
    public Registration getByIndex(String indexNo, String username) {
        CourseIndex index = CourseIndexDB.getDB().getByKey(indexNo);

        if (index == null || index.getCourse() == null) {
            return null;
        }

        return getByCourseCode(index.getCourse().getCourseCode(), username);
    }

    /**
     * 
     * @param courseCode
     * @param username
     */
    public Registration getByCourseCode(String courseCode, String username) {
        if (courseCode == null || username == null) {
            return null;
        }

        Pair<String, String> keyPair = new Pair<>(username, courseCode);

        return regMap.get(keyPair);
    }

    /**
     * 
     * @param index
     */
    public Collection<Registration> getRegOfIndex(String index) {
        CourseIndex courseIndex = CourseIndexDB.getDB().getByKey(index);
        if (courseIndex == null) {
            return null;
        }
        return courseIndex.getAllRegistration();
    }

    /**
     * 
     * @param username
     */
    public Collection<Registration> getRegOfStudent(String username) {
        Student student = StudentDB.getDB().getByKey(username);
        if (student == null) {
            return null;
        }
        return student.getRegistrationList();
    }

    @Override
    public Iterator<Registration> iterator() {
        return Collections.unmodifiableCollection(regMap.values()).iterator();
    }

    @Override
    protected void loadData() {
        loader.load(DB_FILE_PATH, this, converter);
    }

    @Override
    protected void saveData() {
        loader.save(this, DB_FILE_PATH, converter);
    }

    private void doOnStudentDeleted(Student deletedStudent) {
        deletedStudent.getRegistrationList().forEach(reg -> delItem(reg));
    }

    private void doOnIndexDeleted(CourseIndex deletedIndex) {
        deletedIndex.getAllRegistration().forEach(reg -> delItem(reg));
    }

    private void doOnStudentKeyChanged(String oldStudent, Student newStudent) {
        for (Registration reg : newStudent.getRegistrationList()) {
            Pair<String, String> newKeyPair = makeKey(reg);
            Pair<String, String> oldKeyPair = new Pair<>(oldStudent, newKeyPair.val2());

            changeKey(oldKeyPair, newKeyPair);
        }
    }

    private void doOnCourseCodeKeyChanged(String oldCourseCode, Course newCourse) {
        for (CourseIndex index : newCourse.getIndexList()) {
            for (Registration reg : index.getAllRegistration()) {
                Pair<String, String> newKeyPair = makeKey(reg);
                Pair<String, String> oldKeyPair = new Pair<>(newKeyPair.val1(), oldCourseCode);

                changeKey(oldKeyPair, newKeyPair);
            }
        }
    }

    private Pair<String, String> makeKey(Registration reg) {
        String studKey = reg.getStudent().getKey();
        String courseKey = reg.getCourse().getKey();

        Objects.requireNonNull(studKey);
        Objects.requireNonNull(courseKey);

        return new Pair<>(studKey, courseKey);
    }

    private boolean changeKey(Pair<String, String> oldKeyPair, Pair<String, String> newKeyPair) {
        Registration changed = regMap.remove(oldKeyPair);

        if (changed == null) {
            return false;
        }

        regMap.put(newKeyPair, changed);

        return true;
    }

    public static void main(String[] args) {
        for (Registration reg: RegistrationDB.getDB()) {
            System.out.println(reg.getCourse().getCourseCode());
        }
    }

}