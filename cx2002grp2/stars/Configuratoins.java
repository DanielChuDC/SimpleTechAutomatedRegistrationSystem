package cx2002grp2.stars;

import java.util.Map.Entry;

import javax.swing.text.html.parser.Entity;

import cx2002grp2.stars.data.database.AbstractDatabase;
import cx2002grp2.stars.data.database.SingleStringKeyDatabase;
import cx2002grp2.stars.data.dataitem.SingleStringKeyItem;

/**
 * Config
 */
public class Configuratoins extends SingleStringKeyDatabase<Configuratoins.OneConfig> {
    
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