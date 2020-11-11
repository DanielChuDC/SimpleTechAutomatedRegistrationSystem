package cx2002grp2.stars;

import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import cx2002grp2.stars.data.database.AbstractSingleKeyDatabase;
import cx2002grp2.stars.data.dataitem.SingleKeyItem;
import cx2002grp2.stars.util.EmailNotificationSender;

/**
 * Config
 */
public class Configs extends AbstractSingleKeyDatabase<String, Configs.OneConfig> {
    
    private static Configs configDB = new Configs();
    
    private Configs() {
        loadData();
    }

    public static void init() {

    }

    /**
     * 
     * @param key
     * @return
     */
    public static Object getConfig(String key) {
        OneConfig config = configDB.getByKey(key);
        if (config == null) return null;
        return config.getValue();
    }

    /**
     * 
     * @param key
     * @param val
     */
    public static void setConfig(String key, Object val) {
        configDB.addItem(new OneConfig(key, val));
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
    
    public static boolean isStudentAccessTime() {
        LocalDateTime now = LocalDateTime.now();
        return now.compareTo(getAccessStartTime()) >= 0 &&
               now.compareTo(getAccessEndTime()) < 0;
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

    public static int getMaxAu() {
        // TODO - implement method
        return 23;
    }

    public static void setMaxAu(int newAu) {
        // TODO - implement method
    }

    private static final NotificationSender defaultSender = new EmailNotificationSender();
    public static NotificationSender getNotificationSender() {
        return defaultSender;
    }

    public static Level getDefualtLogLevel() {
        // TODO - implement method
        return Level.ALL;
    }

    public static Logger getLogger(String loggerName) {
        Logger logger = Logger.getLogger(loggerName);
        logger.setLevel(getDefualtLogLevel());
        return logger;
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
    public static class OneConfig implements SingleKeyItem<String> {
        private String key;
        private Object value;

        public OneConfig(String key, Object value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String getKey() {
            return this.key;
        }

        @Override
        public void setKey(String newKey) {
            this.key = newKey;
        }

        public Object getValue() {
            return this.value;
        }

        public Object setValue(Object value) {
            Object oldVal = this.value;
            this.value = value;
            return oldVal;
        }
    }
}