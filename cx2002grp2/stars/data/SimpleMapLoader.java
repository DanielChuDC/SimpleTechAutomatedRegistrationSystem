package cx2002grp2.stars.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cx2002grp2.stars.CSV;
import cx2002grp2.stars.data.converter.Converter;
import cx2002grp2.stars.data.dataitem.SingleStringKeyItem;

/**
 * 
 */
public class SimpleMapLoader<ItemType extends SingleStringKeyItem> {
    /**
     * 
     * @param inputCsvFilePath
     * @param outputMap
     * @param converter
     */
    public void load(String inputCsvFilePath, Map<String, ItemType> outputMap, Converter<ItemType> converter) {

        CSV.Reader input = new CSV.Reader(inputCsvFilePath);

        List<List<String>> data = input.readData();

        for (List<String> row : data) {
            ItemType item = converter.fromStringList(row);
            outputMap.put(item.getKey(), item);
        }
    }

    /**
     * 
     * @param inputMap
     * @param outputCsvFilePath
     * @param converter
     */
    public void save(Map<String, ItemType> inputMap, String outputCsvFilePath, Converter<ItemType> converter) {
        CSV.Writer output = new CSV.Writer(outputCsvFilePath);

        List<List<String>> data = new ArrayList<>(inputMap.size());

        for (Map.Entry<String, ItemType> entry : inputMap.entrySet()) {
            List<String> row = converter.toStringList(entry.getValue());
            data.add(row);
        }

        output.writeData(data);
    }
}
