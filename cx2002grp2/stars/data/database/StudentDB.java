package cx2002grp2.stars.data.database;

import java.util.Objects;

import cx2002grp2.stars.data.converter.Converter;
import cx2002grp2.stars.data.converter.ConverterFactory;
import cx2002grp2.stars.data.dataitem.Student;
import cx2002grp2.stars.data.dataitem.User;

/**
 * Database storing {@link Student}.
 * <p>
 * The database is implemented with Singleton pattern.
 * <p>
 * The following data syncing process will be handled automatically:
 * <ul>
 * <li>When a student is added, a the corresponding user will be added into
 * {@link UserDB user database}
 * <li>When a user is deleted, the corresponding student will be deleted from
 * student database, if it exist.
 * <li>When a student is deleted, the corresponding user will be deleted from
 * user database.
 * <li>If the client try to change the username of a student to another username
 * that conflicts with an existing username in {@link UserDB user database}, the
 * attempt of changing will be rejected.
 * <li>If the client try to add a student that is found to be not a student in
 * the {@link UserDB user database}, in another word, the domain of the user found is
 * not {@link User.Domain#STUDENT}, then exception will be thrown.
 * </ul>
 * 
 * @see UserDB
 */
public class StudentDB extends AbstractSingleKeyDatabase<String, Student> {
	/**
	 * database file that student database is storing
	 */
	private static final String DB_FILE_PATH = "tables/student.csv";
	/**
	 * A unique instance of database, for Singleton pattern.
	 */
	private static StudentDB instance = new StudentDB();

	/**
	 * Get instance of database, for Singleton pattern.
	 * 
	 * @return the singleton student database.
	 */
	public static StudentDB getDB() {
		return instance;
	}

	/**
	 * Converter for converting student item from and into string list.
	 */
	private Converter<Student> converter = ConverterFactory.studentConverter();
	/**
	 * Loader used to load database from and into file.
	 */
	private SimpleDatabaseLoader loader = SimpleDatabaseLoader.getLoader();

	/**
	 * Construct a database with data loaded.
	 */
	protected StudentDB() {
		loadData();

		UserDB userDB = UserDB.getDB();

		// When a user is deleted, delete it from student database too, if it exists.
		userDB.addOnItemDeletedObserver(user -> delByKey(user.getUsername()));
		// When a key in user database changed, change the key maintained by the
		// database.
		userDB.addOnKeyChangedObserver((oldKey, newItem) -> changeKey(oldKey, newItem.getKey()));
	}

	/**
	 * Get a student from the given user information.
	 * 
	 * @param user the user used to get a student.
	 * @return a student with the same username to the given user. If such student
	 *         does not exist, return null.
	 */
	public Student getFromUser(User user) {
		if (user == null) {
			return null;
		}

		return getByKey(user.getKey());
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * When a student is added into the database, the student will be added into the
	 * {@link UserDB user database} simultaneously.
	 * 
	 * @param student the student to be inserted into database.
	 * @throws IllegalArgumentException if in the user database, there is a user
	 *                                  with the same user name as input student,
	 *                                  but the domain of that user is not
	 *                                  {@link User.Domain#STUDENT}, the incoming
	 *                                  student is considered as an illegal argument
	 */
	@Override
	public Student addItem(Student student) {
		Objects.requireNonNull(student);
		Objects.requireNonNull(student.getKey());

		User user = UserDB.getDB().getByKey(student.getKey());

		if (user != null && user.getDomain() != student.getDomain()) {
			throw new IllegalArgumentException("Incapable Domain Replacement: Username: " + student.getUsername()
					+ ", original domain: " + user.getDomain() + ", new domain: " + student.getDomain());
		}

		UserDB.getDB().addItem(student);

		return super.addItem(student);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * When a student is deleted from database, the correspond user will be deleted
	 * from the user database simultaneously.
	 * 
	 * @param studentUsername the student to be deleted from database.
	 */
	@Override
	public Student delByKey(String studentUsername) {
		Student deleted = super.delByKey(studentUsername);

		UserDB.getDB().delItem(deleted);

		return deleted;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * When a student is deleted from database, the correspond user will be deleted
	 * from the user database simultaneously.
	 * 
	 * @param student the student to be deleted into database.
	 */
	@Override
	public Student delItem(Student student) {
		if (student == null) {
			return null;
		}
		return delByKey(student.getKey());
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Changing the key of a student will affect a user database too. Therefore, the
	 * key changing may happen only if the key can change in user database. That is,
	 * the third case when the key won't change:
	 * <ol start="3">
	 * <li>The new key of student conflicts with an existing key in the
	 * {@link UserDB user database }
	 * </ol>
	 */
	@Override
	public boolean changeKey(String oldKey, String newKey) {
		boolean canChange = UserDB.getDB().changeKey(oldKey, newKey);

		// Fail to change key in the user database means the key in student database
		// cannot change, too.
		if (!canChange) {
			return false;
		}

		return super.changeKey(oldKey, newKey);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Changing the key of a student will affect a user database too. Therefore, the
	 * key changing may happen only if the key can change in user database. That is,
	 * the third case when the key won't change:
	 * <ol start="3">
	 * <li>The new key of student conflicts with an existing key in the
	 * {@link UserDB user database }
	 * </ol>
	 */
	@Override
	public boolean changeKey(Student oldItem, String newKey) {
		return changeKey(oldItem.getKey(), newKey);
	}

	@Override
	protected void loadData() {
		loader.load(DB_FILE_PATH, this, converter);
	}

	@Override
	public void saveData() {
		loader.save(this, DB_FILE_PATH, converter);
	}

}