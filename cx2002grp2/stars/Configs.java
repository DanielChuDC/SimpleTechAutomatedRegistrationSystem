package cx2002grp2.stars;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import cx2002grp2.stars.data.database.AbstractSingleKeyDatabase;
import cx2002grp2.stars.data.dataitem.SingleKeyItem;
import cx2002grp2.stars.util.EmailNotificationSender;

/**
 * A collection of the global configurations.
 * <p>
 * All the configuration is backed by a config database.
 */
public class Configs extends AbstractSingleKeyDatabase<String, Configs.OneConfig> {

    /**
     * database file that course database is storing.
     */
    private static final String DB_FILE_PATH = "tables/config.csv";

    /**
     * the configuration database.
     */
    private static Configs configDB = new Configs();

    /**
     * construct a configuration database with data loaded.
     */
    private Configs() {
        loadData();
    }

    // Keys of configuration stored in the config file.
    private static final String ACCESS_START_TIME_KEY = "access_start_time";
    private static final String ACCESS_END_TIME_KEY = "access_end_time";
    private static final String SYSTEM_EMAIL_KEY = "system_email";
    private static final String SYSTEM_EMAIL_PASSWD_KEY = "system_email_passwd";
    private static final String MAX_AU_KEY = "default_max_au";
    private static final String DEFAULT_LOG_LEVEL_KEY = "default_log_level";
    private static final String MAX_REG_COUNT_KEY = "max_registration_count";

    // Buffered values.
    private static LocalDateTime accessEndTimeBuf;
    private static LocalDateTime accessStartTimeBuf;
    private static String systemEmailAddrBuf;
    private static String systemEmailPasswdBuf;
    private static int maxAuBuf;
    private static int maxRegCntBuf;
    private static Level defaultLogLevelBuf;

    /**
     * initialize all the buffered value.
     */
    public static void init() {
        accessStartTimeBuf = (LocalDateTime) getConfig(ACCESS_START_TIME_KEY);
        accessEndTimeBuf = (LocalDateTime) getConfig(ACCESS_END_TIME_KEY);
        systemEmailAddrBuf = (String) getConfig(SYSTEM_EMAIL_KEY);
        systemEmailPasswdBuf = (String) getConfig(SYSTEM_EMAIL_PASSWD_KEY);
        maxAuBuf = (Integer) getConfig(MAX_AU_KEY);
        maxRegCntBuf = (Integer) getConfig(MAX_REG_COUNT_KEY);
        defaultLogLevelBuf = (Level) getConfig(DEFAULT_LOG_LEVEL_KEY);
    }

    /**
     * Get the configuration with the given key.
     * 
     * @param key the key value of the given configuration.
     * @return the value of the corresponding configuration. The key cannot be found
     *         in the config database, return null.
     */
    public static Object getConfig(String key) {
        OneConfig config = configDB.getByKey(key);
        if (config == null)
            return null;
        return config.getValue();
    }

    /**
     * Set the configuration with the given key and value.
     * 
     * @param key the key of the given configuration.
     * @param val the value to be set in the corresponding configuration.
     */
    public static void setConfig(String key, Object val) {
        configDB.addItem(new OneConfig(key, val));
    }

    /**
     * Get the access start time
     * 
     * @return the access start time
     */

    public static LocalDateTime getAccessStartTime() {
        return accessStartTimeBuf;
    }

    /**
     * Set the access start time
     * 
     * @param time the new access start time.
     * @throws NullPointerException if the new value is null.
     */
    public static void setAccessStartTime(LocalDateTime time) {
        Objects.requireNonNull(time);

        accessStartTimeBuf = time;
        setConfig(ACCESS_START_TIME_KEY, time);
    }

    /**
     * Get the access end time
     * 
     * @return the access end time
     */
    public static LocalDateTime getAccessEndTime() {
        return accessEndTimeBuf;
    }

    /**
     * Set the access end time
     * 
     * @param time the new access end time.
     * @throws NullPointerException if the new value is null.
     */
    public static void setAccessEndTime(LocalDateTime time) {
        Objects.requireNonNull(time);

        accessEndTimeBuf = time;
        setConfig(ACCESS_END_TIME_KEY, time);
    }

    public static boolean isStudentAccessTime() {
        LocalDateTime now = LocalDateTime.now();
        return now.compareTo(getAccessStartTime()) >= 0 && now.compareTo(getAccessEndTime()) < 0;
    }

    /**
     * Get the system email address
     * 
     * @return the system email address
     */

    public static String getSystemEmailAddr() {
        return systemEmailAddrBuf;
    }

    /**
     * Set the system email address
     * 
     * @param newEmail the new system email address.
     * @throws NullPointerException if the new value is null.
     */
    public static void setSystemEmailAddr(String newEmail) {
        Objects.requireNonNull(newEmail);

        systemEmailAddrBuf = newEmail;
        setConfig(SYSTEM_EMAIL_KEY, newEmail);
    }

    /**
     * Get the system email password
     * 
     * @return the system email password
     */
    public static String getSystemEmailPasswd() {
        return systemEmailPasswdBuf;
    }

    /**
     * Set the system email password
     * 
     * @param newPasswd the new system email password.
     * @throws NullPointerException if the new value is null.
     */
    public static void setSystemEmailPasswd(String newPasswd) {
        Objects.requireNonNull(newPasswd);

        systemEmailPasswdBuf = newPasswd;
        setConfig(SYSTEM_EMAIL_PASSWD_KEY, newPasswd);
    }

    /**
     * Get the maximum allowed AU
     * 
     * @return the maximum allowed AU
     */

    public static int getMaxAu() {
        return maxAuBuf;
    }

    /**
     * Set the maximum allowed AU
     * 
     * @param newAu the new maximum allowed AU.
     */
    public static void setMaxAu(int newAu) {
        maxAuBuf = newAu;
        setConfig(MAX_AU_KEY, Integer.valueOf(newAu));
    }

    /**
     * Get the maximum allowed registration count
     * 
     * @return the maximum allowed registration count
     */
    public static int getMaxRegistrationCount() {
        return maxRegCntBuf;
    }

    /**
     * Set the maximum allowed registration count
     * 
     * @param newMaxRegCnt the new the maximum allowed registration count
     */
    public static void setMaxRegistrationCount(int newMaxRegCnt) {
        maxRegCntBuf = newMaxRegCnt;
        setConfig(MAX_REG_COUNT_KEY, Integer.valueOf(maxRegCntBuf));
    }

    /**
     * The default notification sender.
     */
    private static final NotificationSender defaultSender = new EmailNotificationSender();

    /**
     * Get a notification sender to be used system-wise.
     * 
     * @return a notification sender to be used system-wise.
     */
    public static NotificationSender getNotificationSender() {
        return defaultSender;
    }

    /**
     * Get the logger level produced by the {@link Configs#getLogger(String)}
     * 
     * @return the logger level produced by the {@link Configs#getLogger(String)}
     */
    public static Level getDefaultLogLevel() {
        return defaultLogLevelBuf;
    }

    /**
     * Produce a logger.
     * <p>
     * The logger will be config with level {@link Configs#getDefaultLogLevel()}
     * 
     * @param loggerName the name of logger
     * @return the created logger
     */
    public static Logger getLogger(String loggerName) {
        Logger logger = Logger.getLogger(loggerName);
        logger.setLevel(getDefaultLogLevel());
        return logger;
    }

    @Override
    protected void loadData() {
        CSV.Reader reader = new CSV.Reader(DB_FILE_PATH);

        List<List<String>> configTable = reader.readData();

        for (List<String> configRow : configTable) {
            String configKey = configRow.get(0);
            String configValStr = configRow.get(1);
            Object configVal = configValStr;

            switch (configKey) {
                case ACCESS_START_TIME_KEY:
                    configVal = LocalDateTime.parse(configValStr);
                    break;
                case ACCESS_END_TIME_KEY:
                    configVal = LocalDateTime.parse(configValStr);
                    break;
                case SYSTEM_EMAIL_KEY:
                    break;
                case SYSTEM_EMAIL_PASSWD_KEY:
                    break;
                case MAX_AU_KEY:
                    configVal = Integer.valueOf(configValStr);
                    break;
                case MAX_REG_COUNT_KEY:
                    configVal = Integer.valueOf(configValStr);
                    break;
                case DEFAULT_LOG_LEVEL_KEY:
                    configVal = Level.parse(configValStr);
                    break;
            }

            addItem(new OneConfig(configKey, configVal));
        }
    }

    @Override
    public void saveData() {
        List<List<String>> configTable = new ArrayList<>();

        for (OneConfig config : this) {
            List<String> configRow = new ArrayList<>(2);
            configRow.add(config.getKey());
            configRow.add(config.getValue().toString());
            configTable.add(configRow);
        }

        CSV.Writer writer = new CSV.Writer(DB_FILE_PATH);

        writer.writeData(configTable);
    }

    /**
     * Represent single record of configuration.
     */
    public static class OneConfig implements SingleKeyItem<String> {
        /**
         * Key value of configuration
         */
        private String key;
        /**
         * Value of configuration
         */
        private Object value;

        /**
         * Construct a configuration with the given key and value.
         * 
         * @param key   key of configuration
         * @param value value of configuration
         */
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

        /**
         * Get the value of configuration
         * 
         * @return the value of configuration
         */
        public Object getValue() {
            return this.value;
        }

        /**
         * Set the value of configuration
         * 
         * @param value the new value of configuration
         */
        public void setValue(Object value) {
            this.value = value;
        }
    }

    /**
     * manually save the configuration data.
     */
    public static void saveConfig() {
        configDB.saveData();
    }

    public static void main(String[] args) {
        Configs.init();
        System.out.println(Configs.getAccessEndTime());
        Configs.setAccessEndTime(LocalDateTime.now());
        System.out.println(Configs.getAccessEndTime());
    }
}