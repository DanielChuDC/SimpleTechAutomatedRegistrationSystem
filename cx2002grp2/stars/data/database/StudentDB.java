package cx2002grp2.stars.data.database;

import java.util.Iterator;
import java.util.Objects;

import cx2002grp2.stars.data.dataitem.Student;
import cx2002grp2.stars.data.dataitem.User;

public class StudentDB extends AbstractSingleKeyDatabase<String, Student> {

	private static final String DB_FILE_PATH = "tables/student.csv";

	private static StudentDB instance = new StudentDB();

	protected StudentDB() {
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
	public Student addItem(Student item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Student delItem(Student item) {
		// TODO Auto-generated method stub
		return null;
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
	public void doOnExit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void addOnItemDeletedObserver(OnItemDeletedObserver<? super Student> observer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delOnItemDeletedObserver(OnItemDeletedObserver<? super Student> observer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addOnItemAddedObserver(OnItemAddedObserver<? super Student> observer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delOnItemAddedObserver(OnItemAddedObserver<? super Student> observer) {
		// TODO Auto-generated method stub

	}

	@Override
	public Student getByKey(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Student delByKey(String key) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub

	}

	@Override
	protected void saveData() {
		// TODO Auto-generated method stub

	}

}