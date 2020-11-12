package cx2002grp2.stars.data.database;

import java.util.ArrayList;
import java.util.List;

import cx2002grp2.stars.CSV;
import cx2002grp2.stars.data.converter.Converter;

/**
 * A simple database loader used to load the database from and into csv files
 */
class SimpleDatabaseLoader {
    /**
     * The default loader
     */
    private static SimpleDatabaseLoader defaultLoader = new SimpleDatabaseLoader();

    /**
     * getter for a loader.
     * 
     * @return a SimpleDatabaseLoader instance
     */
    public static SimpleDatabaseLoader getLoader() {
        return defaultLoader;
    }

    /**
     * Load a the data from the given file into the given database.
     * 
     * @param <ItemType>    the data type of data item to be handle
     * @param inputFilePath the path to the input database file
     * @param outputDB      the database to be loaded
     * @param itemConverter the converter used to convert the string rows read from
     *                      the file into data items.
     */
    public <ItemType> void load(String inputFilePath, Database<ItemType> outputDB, Converter<ItemType> itemConverter) {

        CSV.Reader input = new CSV.Reader(inputFilePath);

        List<List<String>> data = input.readData();

        for (List<String> row : data) {
            ItemType item = itemConverter.fromStringList(row);
            if (item == null) {
                continue;
            }
            outputDB.addItem(item);
        }
    }

    /**
     * Save the data of the given database into the given file.
     * 
     * @param <ItemType>     the data type of data item to be handle
     * @param inputDB        the database to be saved.
     * @param outputFilePath the path to the output database file.
     * @param itemConverter  the converter used to convert data items into the
     *                       string rows to be stored in the file.
     */
    public <ItemType> void save(Database<ItemType> inputDB, String outputFilePath, Converter<ItemType> itemConverter) {
        CSV.Writer output = new CSV.Writer(outputFilePath);

        List<List<String>> data = new ArrayList<>(inputDB.size());

        for (ItemType item : inputDB) {
            List<String> row = itemConverter.toStringList(item);
            if (row == null) {
                continue;
            }
            data.add(row);
        }

        output.writeData(data);
    }
}
