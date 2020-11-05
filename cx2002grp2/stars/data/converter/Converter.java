package cx2002grp2.stars.data.converter;

import java.util.List;

public interface Converter<ItemType> {
    List<String> toStringList(ItemType item);
    ItemType fromStringList(List<String> strings);
}
