package cx2002grp2.stars.data.converter;

import java.util.List;

public interface Converter<ItemType> {
    /**
     * 
     * @param item
     * @return
     */
    List<String> toStringList(ItemType item);

    /**
     * 
     * @param strings
     * @return
     */
    ItemType fromStringList(List<String> strings);
}
