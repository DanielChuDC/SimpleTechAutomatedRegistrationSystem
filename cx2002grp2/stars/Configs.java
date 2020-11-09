package cx2002grp2.stars;

import java.time.LocalDateTime;
import java.util.Map.Entry;

import cx2002grp2.stars.data.database.SingleStringKeyDatabase;
import cx2002grp2.stars.data.dataitem.SingleStringKeyItem;

/**
 * Config
 */
public class Configs extends SingleStringKeyDatabase<Configs.OneConfig> {
    
    private static Configs instance = new Configs();
    
    private Configs() {
        loadData();
    }

    /**
     * 
     * @param key
     * @return
     */
    public static Object getConfig(String key) {
        return instance.get(key).getValue();
    }

    /**
     * 
     * @param key
     * @param val
     */
    public static void setConfig(String key, Object val) {
        instance.get(key).setValue(val);
    }

    public static LocalDateTime getAccessStartTime() {
        // TODO - implement Configs.getAccessStartTime
        return LocalDateTime.MIN;
	}

	/**
	 * 
	 * @param time
	 */
    public static void setAccessStartTime(LocalDateTime time) {
        // TODO - implement Configs.setAccessStartTime
	}

	public static LocalDateTime getAccessEndTime() {
        // TODO - implement Configs.getAccessEndTime
        return LocalDateTime.MAX;
	}

	/**
	 * 
	 * @param time
	 */
    public static void setAccessEndTime(LocalDateTime time) {
        // TODO - implement Configs.setAccessEndTime
	}

	public static String getSystemEmailAddr() {
        // TODO - implement Configs.getSystemEmailAddr
        return "";
	}

	/**
	 * 
	 * @param newEmail
	 */
	public static void setSystemEmailAddr(String newEmail) {
		// TODO - implement Configs.setSystemEmailAddr
	}

	public static String getSystemEmailPasswd() {
        // TODO - implement Configs.getSystemEmailPasswd
        return "";
	}

    /**
     * 
     * @param newPasswd
     */
	public static void setSystemEmailPasswd(String newPasswd) {
		// TODO - implement Configs.setSystemEmailPasswd
    }

	protected void loadData() {
		// TODO - implement Configs.loadData
	}

	protected void saveData() {
		// TODO - implement Configs.saveData
	}

    /**
     * 
     */
    public static class OneConfig extends SingleStringKeyItem implements Entry<String, Object> {
        private String key;
        private Object value;

        public OneConfig(String key, Object value) {
            this.key = key;
            this.value = value;
        }

        public OneConfig(Entry<String, ?> other) {
            this(other.getKey(), other.getValue());
        }

        @Override
        public String getKey() {
            return this.key;
        }

        @Override
        public void setKey(String newKey) {
            this.key = newKey;
        }

        @Override
        public Object getValue() {
            return this.value;
        }

        @Override
        public Object setValue(Object value) {
            Object oldVal = this.value;
            this.value = value;
            return oldVal;
        }
    }
}