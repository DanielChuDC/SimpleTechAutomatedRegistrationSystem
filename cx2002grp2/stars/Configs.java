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
 * Config
 */
public class Configs extends AbstractSingleKeyDatabase<String, Configs.OneConfig> {

    private static final String DB_FILE_PATH = "tables/config.csv";

    private static Configs configDB = new Configs();

    private Configs() {
        loadData();
    }

    private static final String ACCESS_START_TIME_KEY = "access_start_time";
    private static final String ACCESS_END_TIME_KEY = "access_end_time";
    private static final String SYSTEM_EMAIL_KEY = "system_email";
    private static final String SYSTEM_EMAIL_PASSWD_KEY = "system_email_passwd";
    private static final String MAX_AU_KEY = "default_max_au";
    private static final String DEFAULT_LOG_LEVEL_KEY = "default_log_level";

    private static LocalDateTime accessEndTimeBuf;
    private static LocalDateTime accessStartTimeBuf;
    private static String systemEmailAddrBuf;
    private static String systemEmailPasswdBuf;
    private static int maxAuBuf;
    private static Level defualtLogLevelBuf;

    public static void init() {
        accessStartTimeBuf = (LocalDateTime) getConfig(ACCESS_START_TIME_KEY);
        accessEndTimeBuf = (LocalDateTime) getConfig(ACCESS_END_TIME_KEY);
        systemEmailAddrBuf = (String) getConfig(SYSTEM_EMAIL_KEY);
        systemEmailPasswdBuf = (String) getConfig(SYSTEM_EMAIL_PASSWD_KEY);
        maxAuBuf = (Integer) getConfig(MAX_AU_KEY);
        defualtLogLevelBuf = (Level) getConfig(DEFAULT_LOG_LEVEL_KEY);
    }

    /**
     * 
     * @param key
     * @return
     */
    public static Object getConfig(String key) {
        OneConfig config = configDB.getByKey(key);
        if (config == null)
            return null;
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
        return accessStartTimeBuf;
    }

    /**
     * 
     * @param time
     */
    public static void setAccessStartTime(LocalDateTime time) {
        Objects.requireNonNull(time);

        accessStartTimeBuf = time;
        setConfig(ACCESS_START_TIME_KEY, time);
    }

    public static LocalDateTime getAccessEndTime() {
        return accessEndTimeBuf;
    }

    /**
     * 
     * @param time
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

    public static String getSystemEmailAddr() {
        return systemEmailAddrBuf;
    }

    /**
     * 
     * @param newEmail
     */
    public static void setSystemEmailAddr(String newEmail) {
        Objects.requireNonNull(newEmail);

        systemEmailAddrBuf = newEmail;
        setConfig(SYSTEM_EMAIL_KEY, newEmail);
    }

    public static String getSystemEmailPasswd() {
        return systemEmailPasswdBuf;
    }

    /**
     * 
     * @param newPasswd
     */
    public static void setSystemEmailPasswd(String newPasswd) {
        Objects.requireNonNull(newPasswd);

        systemEmailPasswdBuf = newPasswd;
        setConfig(SYSTEM_EMAIL_PASSWD_KEY, newPasswd);
    }

    public static int getMaxAu() {
        return maxAuBuf;
    }

    public static void setMaxAu(int newAu) {
        maxAuBuf = newAu;
        setConfig(MAX_AU_KEY, Integer.valueOf(newAu));
    }

    private static final NotificationSender defaultSender = new EmailNotificationSender();

    public static NotificationSender getNotificationSender() {
        return defaultSender;
    }

    public static Level getDefualtLogLevel() {
        return defualtLogLevelBuf;
    }

    public static Logger getLogger(String loggerName) {
        Logger logger = Logger.getLogger(loggerName);
        logger.setLevel(getDefualtLogLevel());
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
                case DEFAULT_LOG_LEVEL_KEY:
                    configVal = Level.parse(configValStr);
                    break;
            }

            addItem(new OneConfig(configKey, configVal));
        }
    }

    @Override
    protected void saveData() {
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