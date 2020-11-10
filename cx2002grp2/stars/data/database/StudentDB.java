package cx2002grp2.stars.data.database;

import cx2002grp2.stars.data.dataitem.Student;
import cx2002grp2.stars.data.dataitem.User;

public class StudentDB extends SingleStringKeyDatabase<Student> {

    private static final String DB_FILE_PATH = "tables/student.csv";

	private static StudentDB instance = new StudentDB();

	private StudentDB() {
		loadData();
		
		UserDB.getDB().addOnKeyChangedObserver((oldKey, newUser)->changeKey(oldKey, newUser.getKey()));
		UserDB.getDB().addOnItemDeletedObserver(this::doOnUserDeleted);
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
	
	/**
	 * 
	 * @param deletedUser
	 */
	private void doOnUserDeleted(User deletedUser) {
		if (this.contains(deletedUser.getKey())) {
			this.del(deletedUser.getKey());
		}
	}

	protected void loadData() {
		// TODO - implement StudentDB.loadData
	}

	protected void saveData() {
		// TODO - implement StudentDB.saveData
	}

}