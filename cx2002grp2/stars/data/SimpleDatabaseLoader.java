package cx2002grp2.stars.data;

import java.util.ArrayList;
import java.util.List;

import cx2002grp2.stars.CSV;
import cx2002grp2.stars.data.converter.Converter;
import cx2002grp2.stars.data.database.AbstractDatabase;

public class SimpleDatabaseLoader<ItemType> {
    /**
     * 
     * @param inputCsvFilePath
     * @param outputDB
     * @param itemConverter
     */
    public void load(String inputCsvFilePath, AbstractDatabase<ItemType> outputDB, Converter<ItemType> itemConverter) {

        CSV.Reader input = new CSV.Reader(inputCsvFilePath);

        List<List<String>> data = input.readData();

        for (List<String> row : data) {
            ItemType item = itemConverter.fromStringList(row);
            outputDB.addItem(item);
        }
    }

    /**
     * 
     * @param inputDB
     * @param outputCsvFilePath
     * @param itemConverter
     */
    public void save(AbstractDatabase<ItemType> inputDB, String outputCsvFilePath, Converter<ItemType> itemConverter) {
        CSV.Writer output = new CSV.Writer(outputCsvFilePath);

        List<List<String>> data = new ArrayList<>(inputDB.size());

        for (ItemType item : inputDB) {
            List<String> row = itemConverter.toStringList(item);
            data.add(row);
        }

        output.writeData(data);
    }
}