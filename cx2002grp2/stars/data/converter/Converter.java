package cx2002grp2.stars.data.converter;

import java.util.List;

/**
 * Common interface for all the data item converter.
 * <p>
 * A converter support converting a data item from and into a list of String, so
 * that the data can be easily read from or written into CSV file row by row.
 */
public interface Converter<ItemType> {
    /**
     * Convert the given item into a list of String, which can be used to be
     * converted back to the original item with
     * {@link Converter#fromStringList(List)}
     * 
     * @param item the item to be converted.
     * @return the converted row. If the converter considers the item as invalid for
     *         some reason, return null.
     */
    List<String> toStringList(ItemType item);

    /**
     * Convert a string list into a data item.
     * 
     * @param strings the string list to be converted.
     * @return the converted item. If the converter considers the string list as
     *         invalid for some reason, return null. If the given string list (not
     *         null) is list produced by {@link Converter#toStringList(Object)}, the
     *         function should never return null.
     */
    ItemType fromStringList(List<String> strings);
}
