package cx2002grp2.stars.data;

import java.util.ArrayList;
import java.util.List;

import cx2002grp2.stars.CSV;
import cx2002grp2.stars.data.converter.Converter;
import cx2002grp2.stars.data.database.AbstractDatabase;

public class SimpleDatabaseLoader {
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
    public <ItemType> void load(String inputFilePath, AbstractDatabase<ItemType> outputDB,
            Converter<ItemType> itemConverter) {

        CSV.Reader input = new CSV.Reader(inputFilePath);

        List<List<String>> data = input.readData();

        for (List<String> row : data) {
            ItemType item = itemConverter.fromStringList(row);
            outputDB.addItem(item);
        }
    }

    /**
     * 
     * @param inputDB
     * @param outputFilePath
     * @param itemConverter
     */
    public <ItemType> void save(AbstractDatabase<ItemType> inputDB, String outputFilePath,
            Converter<ItemType> itemConverter) {
        CSV.Writer output = new CSV.Writer(outputFilePath);

        List<List<String>> data = new ArrayList<>(inputDB.size());

        for (ItemType item : inputDB) {
            List<String> row = itemConverter.toStringList(item);
            data.add(row);
        }

        output.writeData(data);
    }
}
