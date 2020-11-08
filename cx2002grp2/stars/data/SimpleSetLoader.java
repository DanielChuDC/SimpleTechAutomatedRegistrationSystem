package cx2002grp2.stars.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cx2002grp2.stars.CSV;
import cx2002grp2.stars.data.converter.Converter;

public class SimpleSetLoader<ItemType> {
    public void load(String inputCsvFilePath, Set<ItemType> outputSet, Converter<ItemType> converter) {

        CSV.Reader input = new CSV.Reader(inputCsvFilePath);

        List<List<String>> data = input.readData();

        for (List<String> row : data) {
            ItemType item = converter.fromStringList(row);
            outputSet.add(item);
        }
    }

    public void save(Set<ItemType> inputSet, String outputCsvFilePath, Converter<ItemType> converter) {
        CSV.Writer output = new CSV.Writer(outputCsvFilePath);

        List<List<String>> data = new ArrayList<>(inputSet.size());

        for (ItemType item : inputSet) {
            List<String> row = converter.toStringList(item);
            data.add(row);
        }

        output.writeData(data);
    }
}
