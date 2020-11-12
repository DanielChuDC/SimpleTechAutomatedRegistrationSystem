package cx2002grp2.stars.data.database;

import cx2002grp2.stars.data.converter.Converter;
import cx2002grp2.stars.data.converter.ConverterFactory;
import cx2002grp2.stars.data.dataitem.User;

public class UserDB extends AbstractSingleKeyDatabase<String, User> {

    private static final String DB_FILE_PATH = "tables/user.csv";

    private static UserDB instance = new UserDB();

	private Converter<User> converter = ConverterFactory.userConverter();

	private SimpleDatabaseLoader loader = SimpleDatabaseLoader.getLoader();

    protected UserDB() {
        loadData();
    }

    public static UserDB getDB() {
        return instance;
    }

	@Override
	protected void loadData() {
		loader.load(DB_FILE_PATH, this, converter);
	}

	@Override
	protected void saveData() {
		loader.save(this, DB_FILE_PATH, converter);
	}
}