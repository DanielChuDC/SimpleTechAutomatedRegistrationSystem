package cx2002grp2.stars.database;

import cx2002grp2.stars.dataconverter.Converter;
import cx2002grp2.stars.dataconverter.ConverterFactory;
import cx2002grp2.stars.dataitem.User;

/**
 * Database storing {@link User}.
 * <p>
 * The database is implemented with Singleton pattern.
 */
public class UserDB extends AbstractSingleKeyDatabase<String, User> {

	/**
	 * database file that user database is storing
	 */
	private static final String DB_FILE_PATH = "tables/user.csv";
	/**
	 * A unique instance of database, for Singleton pattern.
	 */
	private static UserDB instance = new UserDB();

	/**
	 * Get instance of database, for Singleton pattern.
	 * 
	 * @return the singleton user database.
	 */
	public static UserDB getDB() {
		return instance;
	}

	/**
	 * Converter for converting user item from and into string list.
	 */
	private Converter<User> converter = ConverterFactory.userConverter();

	/**
	 * Loader used to load database from and into file.
	 */
	private SimpleDatabaseLoader loader = SimpleDatabaseLoader.getLoader();

	/**
	 * Construct a database with data loaded.
	 */
	protected UserDB() {
		loadData();
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