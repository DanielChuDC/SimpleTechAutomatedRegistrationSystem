package cx2002grp2.stars.data.dataitem;

public interface SingleStringKeyItem {
    /**
     * The method used to get the key value of an item.
     * 
     * @return
     */
    String getKey();

    /**
     * The method used to change the key value of an item.
     * <p>
     * This method doesn't need to do any check about database integrity like
     * whether the new key is a existing key.
     * <p>
     * This method is supposed to be call by database class object only. All the
     * other objects should use the changeKey methods in the databse class to change
     * the key value of an item.
     * 
     * @param newKey
     */
    void setKey(String newKey);
}
