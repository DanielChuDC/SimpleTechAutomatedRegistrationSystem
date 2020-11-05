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
     * The concrete method should check whether the new key is an existing key in
     * the database before changing the key to a new one.
     * <p>
     * This method is supposed to be call by database class object only. All the
     * other objects should use the changeKey methods in the databse class to change
     * the key value of an item.
     * 
     * @param newKey
     */
    void setKey(String newKey);
}
