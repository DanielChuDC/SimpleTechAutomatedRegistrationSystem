package cx2002grp2.stars.data.database;

import java.util.ArrayList;

import cx2002grp2.stars.data.dataitem.Registration;

public class RegistrationDB extends AbstractDatabase<Registration> {

    private static final String DB_FILE_PATH = "tables/registration.csv";

	private static RegistrationDB instance = new RegistrationDB();

	public static RegistrationDB getDB() {
        return instance;
	}

	private RegistrationDB() {
        loadData();
	}

	/**
	 * 
	 * @param registration
	 */
    @Override
	public boolean add(Registration registration) {
        // TODO - implement RegistrationDB.add
        return false;
	}

	/**
	 * 
	 * @param registration
	 */
    @Override
	public boolean del(Registration registration) {
		// TODO - implement RegistrationDB.del
        return false;
	}

	/**
	 * 
	 * @param registration
	 */
    @Override
	public boolean contains(Registration registration) {
        // TODO - implement RegistrationDB.contains
        return false;
	}

	/**
	 * 
	 * @param courseCode
	 * @param username
	 */
	public Registration getByCourseCode(String courseCode, String username) {
        // TODO - implement method
        return null;
	}

	/**
	 * 
	 * @param indexNo
	 * @param username
	 */
	public Registration getByCourseIndex(String indexNo, String username) {
        // TODO - implement method
        return null;
	}

	/**
	 * 
	 * @param index
	 */
	public Iterable<Registration> getRegOfIndex(String index) {
        // TODO - implement RegistrationDB.getRegOfIndex
        return new ArrayList<>();
	}

	/**
	 * 
	 * @param username
	 */
	public Iterable<Registration> getRegOfStudent(String username) {
        // TODO - implement RegistrationDB.getRegOfStudent
        return new ArrayList<>();
	}

	protected void loadData() {
		// TODO - implement RegistrationDB.loadData
	}

	protected void saveData() {
		// TODO - implement RegistrationDB.saveData
	}

}