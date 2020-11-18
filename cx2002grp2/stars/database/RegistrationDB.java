package cx2002grp2.stars.database;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import cx2002grp2.stars.dataconverter.Converter;
import cx2002grp2.stars.dataconverter.ConverterFactory;
import cx2002grp2.stars.dataitem.Course;
import cx2002grp2.stars.dataitem.CourseIndex;
import cx2002grp2.stars.dataitem.Registration;
import cx2002grp2.stars.dataitem.Student;
import cx2002grp2.stars.dataitem.Registration.Status;
import cx2002grp2.stars.util.Pair;

/**
 * Database storing {@link Registration}.
 * <p>
 * The database is implemented with Singleton pattern.
 * <p>
 * The following data syncing process will be handled automatically:
 * <ul>
 * <li>When a {@link CourseIndex} is deleted from {@link CourseIndexDB}, all the
 * registration under the deleted {@link CourseIndex} will deleted from this
 * database.
 * <li>When a {@link Student} is deleted from {@link StudentDB}, all the
 * registration under the deleted {@link Student} index will deleted from this
 * database.
 * <li>When a registration is deleted, it will be dropped automatically.
 * </ul>
 */
public class RegistrationDB extends AbstractDatabase<Registration> {

    /**
     * database file that registration database is storing
     */
    private static final String DB_FILE_PATH = "tables/registration.csv";

    /**
     * A unique instance of database, for Singleton pattern.
     */
    private static RegistrationDB instance = new RegistrationDB();

    /**
     * Get instance of database, for Singleton pattern.
     * 
     * @return the singleton registration database.
     */
    public static RegistrationDB getDB() {
        return instance;
    }

    /**
     * Converter for converting registration item from and into string list.
     */
    private Converter<Registration> converter = ConverterFactory.registrationConverter();

    /**
     * Loader used to load database from and into file.
     */
    private SimpleDatabaseLoader loader = SimpleDatabaseLoader.getLoader();

    /**
     * A map used to mapping from a student username and course code to a data item.
     */
    private SortedMap<Pair<String, String>, Registration> regMap = new TreeMap<>(
            Pair.pairComparator(String.CASE_INSENSITIVE_ORDER, String.CASE_INSENSITIVE_ORDER));

    /**
     * Construct a database with data loaded and setup syncing process.
     */
    protected RegistrationDB() {
        loadData();

        // When a CourseIndex is deleted from CourseIndexDB, all the registration under
        // the deleted CourseIndex will deleted from this database.
        StudentDB.getDB().addOnItemDeletedObserver(this::doOnStudentDeleted);
        // When a Student is deleted from StudentDB, all the registration under the
        // deleted Student index will deleted from this database.
        CourseIndexDB.getDB().addOnItemDeletedObserver(this::doOnIndexDeleted);

        // The key stored in this database need to be synchronized with the key stored
        // in the StudentDB and CourseDB
        StudentDB.getDB().addOnKeyChangedObserver(this::doOnStudentKeyChanged);
        CourseDB.getDB().addOnKeyChangedObserver(this::doOnCourseCodeKeyChanged);
    }

    /**
     * {@inheritDoc}
     * 
     * @return The original item in database if there is an item has the same
     *         student username and course code with the added item , or null if no
     *         replacement happens
     * @throws NullPointerException If the inserted registration, student username
     *                              or course code is null.
     */
    @Override
    public Registration addItem(Registration reg) {
        Pair<String, String> keyPair = makeKey(reg);

        Registration ret = regMap.put(keyPair, reg);

        if (ret != null) {
            signalItemAdded(ret);
        }

        return ret;
    }

    /**
     * {@inheritDoc}
     * <p>
     * When a registration is deleted, it will be dropped automatically.
     */
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
            ret.drop();
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
     * Get a registration with the student username and course index.
     * 
     * @param indexNo  the course index of registration to be got.
     * @param username the student username of registration to be got.
     * @return a registration with given student username and course index. If such
     *         registration does not exist, return null.
     */
    public Registration getByIndex(String indexNo, String username) {
        CourseIndex index = CourseIndexDB.getDB().getByKey(indexNo);

        if (index == null || index.getCourse() == null) {
            return null;
        }

        Registration ret = getByCourseCode(index.getCourse().getCourseCode(), username);

        if (ret == null || ret.getCourseIndex() == null || !ret.getCourseIndex().getKey().equals(indexNo)) {
            return null;
        }

        return ret;
    }

    /**
     * Get a registration with the student username and course code.
     * 
     * @param courseCode the course code of registration to be got.
     * @param username   the student username of registration to be got.
     * @return a registration with given student username and course code. If such
     *         registration does not exist, return null.
     */
    public Registration getByCourseCode(String courseCode, String username) {
        if (courseCode == null || username == null) {
            return null;
        }

        Pair<String, String> keyPair = new Pair<>(courseCode, username);

        return regMap.get(keyPair);
    }

    /**
     * Get a collection of registration that under given index.
     * 
     * @param index the index used to get the registration.
     * @return a collection of registration that under given course index. If the
     *         index cannot be found in the database system, return null.
     */
    public Collection<Registration> getRegOfIndex(String index) {
        CourseIndex courseIndex = CourseIndexDB.getDB().getByKey(index);
        if (courseIndex == null) {
            return null;
        }
        return courseIndex.getAllRegistration();
    }

    /**
     * Get a collection of registration under the student of given username.
     * 
     * @param username the username used to get the registration.
     * @return a collection of registration under the given username. If the
     *         username cannot be found in the database system, return null.
     */
    public Collection<Registration> getRegOfStudent(String username) {
        Student student = StudentDB.getDB().getByKey(username);
        if (student == null) {
            return null;
        }
        return student.getRegistrationList();
    }

    /**
     * Get a collection of registration under the given course code.
     * <p>
     * The method supports filtering out the registration that are not in status
     * {@link Registration.Status#REGISTERED}
     * 
     * @param courseCode     the course code used to get the registration.
     * @param onlyRegistered only return the registration with status
     *                       {@link Registration.Status#REGISTERED}
     * @return a collection of registration under the given course code.If the
     *         course cannot be found in the database system, return null.
     */
    public Collection<Registration> getRegOfCourseCode(String courseCode, boolean onlyRegistered) {
        Course course = CourseDB.getDB().getByKey(courseCode);

        if (course == null) {
            return null;
        }

        Pair<String, String> lowerBound = new Pair<>(courseCode, "");
        Pair<String, String> upperBound = new Pair<>(courseCode, "~");
        Map<Pair<String, String>, Registration> wanted = regMap.subMap(lowerBound, upperBound);

        Collection<Registration> ret = wanted.values();

        if (onlyRegistered) {
            ret = ret.stream().filter(reg -> !reg.isDropped() && reg.getStatus() == Status.REGISTERED)
                    .collect(Collectors.toUnmodifiableList());
        }

        return Collections.unmodifiableCollection(ret);
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
    public void saveData() {
        loader.save(this, DB_FILE_PATH, converter);
    }

    /**
     * Delete all the registration under the deleted student.
     * 
     * @param deletedStudent the deleted student.
     */
    private void doOnStudentDeleted(Student deletedStudent) {
        deletedStudent.getRegistrationList().forEach(reg -> delItem(reg));
    }

    /**
     * Delete all the registration under the deleted index.
     * 
     * @param deletedIndex the deleted index.
     */
    private void doOnIndexDeleted(CourseIndex deletedIndex) {
        deletedIndex.getAllRegistration().forEach(reg -> delItem(reg));
    }

    /**
     * Sync the key when the key of the student change.
     * 
     * @param oldStudent the old key of student
     * @param newStudent the new student with the new key.
     */
    private void doOnStudentKeyChanged(String oldStudent, Student newStudent) {
        for (Registration reg : newStudent.getRegistrationList()) {
            Pair<String, String> newKeyPair = makeKey(reg);
            Pair<String, String> oldKeyPair = new Pair<>(newKeyPair.val1(), oldStudent);

            changeKey(oldKeyPair, newKeyPair);
        }
    }

    /**
     * Sync the key when the key of the course change.
     * 
     * @param oldCourseCode the old key of course
     * @param newCourse     the new course with the new key.
     */
    private void doOnCourseCodeKeyChanged(String oldCourseCode, Course newCourse) {
        for (CourseIndex index : newCourse.getIndexList()) {
            for (Registration reg : index.getAllRegistration()) {
                Pair<String, String> newKeyPair = makeKey(reg);
                Pair<String, String> oldKeyPair = new Pair<>(oldCourseCode, newKeyPair.val2());

                changeKey(oldKeyPair, newKeyPair);
            }
        }
    }

    /**
     * Make a key pair from a given registration.
     * 
     * @param reg the given registration.
     * @return the key pair that can be used to identify a registration.
     * @throws NullPointerException if registration is null, its course is null, its
     *                              student is null, the key of its course is null,
     *                              or the key of its student is null.
     * @see Pair
     */
    private Pair<String, String> makeKey(Registration reg) {
        String studKey = reg.getStudent().getKey();
        String courseKey = reg.getCourse().getKey();

        Objects.requireNonNull(studKey);
        Objects.requireNonNull(courseKey);

        return new Pair<>(courseKey, studKey);
    }

    /**
     * Change the key of the map an element to a new one.
     * 
     * @param oldKeyPair the old key.
     * @param newKeyPair the new key.
     * @return whether the key is changed.
     */
    private boolean changeKey(Pair<String, String> oldKeyPair, Pair<String, String> newKeyPair) {
        if (regMap.containsKey(newKeyPair)) {
            throw new IllegalArgumentException("New Key " + newKeyPair + " conflicts with an existing key.");
        }

        Registration changedReg = regMap.remove(oldKeyPair);

        if (changedReg == null) {
            return false;
        }

        regMap.put(newKeyPair, changedReg);

        return true;
    }
}