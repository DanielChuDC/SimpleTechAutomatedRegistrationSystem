package cx2002grp2.stars.data.dataitem;

public abstract class SingleStringKeyItem {
    /**
     * The method used to get the key value of an item.
     * 
     * @return the key value of the item.
     */
    public abstract String getKey();

    /**
     * The method used to change the key value of an item.
     * <p>
     * The concrete method don't need to check database issue like checking whether
     * the new key is an existing key in the database. The relevant issue shall be
     * handled by the database classes
     * <p>
     * This method is supposed to be call by database class object only. All the
     * other objects should use the changeKey methods in the databse class to change
     * the key value of an item.
     * 
     * @param newKey the new key used to replace the old key.
     */
    public abstract void setKey(String newKey);

    /**
     * Return whether two item are equal to each other by comparing their keys.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        if (!(obj instanceof SingleStringKeyItem)) {
            return false;
        }

        SingleStringKeyItem other = (SingleStringKeyItem) obj;

        return this.getKey().equals(other.getKey());
    }

    /**
     * Return the hash code of the key
     */
    @Override
    public int hashCode() {
        return getKey().hashCode();
    }
}
