package cx2002grp2.stars.data.database;

import java.util.ArrayList;
import java.util.List;

import cx2002grp2.stars.CSV;
import cx2002grp2.stars.data.converter.Converter;

class SimpleDatabaseLoader {
    private static SimpleDatabaseLoader loader = new SimpleDatabaseLoader();

    public static SimpleDatabaseLoader getLoader() {
        return loader;
    }

    /**
     * 
     * @param inputFilePath
     * @param outputDB
     * @param itemConverter
     */
    public <ItemType> void load(String inputFilePath, Database<ItemType> outputDB,
            Converter<ItemType> itemConverter) {

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
     * 
     * @param inputDB
     * @param outputFilePath
     * @param itemConverter
     */
    public <ItemType> void save(Database<ItemType> inputDB, String outputFilePath,
            Converter<ItemType> itemConverter) {
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
