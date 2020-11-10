package cx2002grp2.stars.data.database;

import java.util.Iterator;
import java.util.Objects;

import cx2002grp2.stars.data.database.event_handler.OnKeyChangedObserver;
import cx2002grp2.stars.data.dataitem.Student;
import cx2002grp2.stars.data.dataitem.User;

public class StudentDB extends AbstractSingleKeyDatabase<String, Student> {

	private static final String DB_FILE_PATH = "tables/student.csv";

	private static StudentDB instance = new StudentDB();

	private StudentDB() {
		loadData();
	}

	public static StudentDB getDB() {
		return instance;
	}

	/**
	 * 
	 * @param user
	 */
	public Student getFromUser(User user) {
		// TODO - implement StudentDB.getFromUser
		return null;
	}

	@Override
	public boolean addItem(Student student) {
        Objects.requireNonNull(student);
		Objects.requireNonNull(student.getKey());

		UserDB userDB = UserDB.getDB();
		
		User user = userDB.getByKey(student.getKey());

		if (user != null && user.getDomain() != User.Domain.STUDENT) {
			throw new IllegalArgumentException("Conflict between databases: the domain of user is not STUDENT.");
		}

		userDB.addItem(student);

		return super.addItem(student);
	}

	@Override
	public boolean delItem(Student item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasItem(Student item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Iterator<Student> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addOnKeyChangedObserver(OnKeyChangedObserver<? super String, ? super Student> observer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delOnKeyChangedObserver(OnKeyChangedObserver<? super String, ? super Student> observer) {
		// TODO Auto-generated method stub

	}

	@Override
	public Student getByKey(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delByKey(String key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasKey(String key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean changeKey(String oldKey, String newKey) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean changeKey(Student oldItem, String newKey) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void loadData() {
		// TODO - implement StudentDB.loadData
	}

	@Override
	protected void saveData() {
		// TODO - implement StudentDB.saveData
	}
}