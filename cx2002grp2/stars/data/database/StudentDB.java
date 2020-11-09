package cx2002grp2.stars.data.database;

import cx2002grp2.stars.data.dataitem.Student;
import cx2002grp2.stars.data.dataitem.User;

public class StudentDB extends SingleStringKeyDatabase<Student> {

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

	protected void loadData() {
		// TODO - implement StudentDB.loadData
	}

	protected void saveData() {
		// TODO - implement StudentDB.saveData
	}

}