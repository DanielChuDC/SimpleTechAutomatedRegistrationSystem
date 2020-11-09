package cx2002grp2.stars;

import cx2002grp2.stars.data.dataitem.User;
import cx2002grp2.stars.util.EmailNotificationSender;

public interface NotificationSender {
    /**
     * 
     * @return
     */
    public static NotificationSender getSender() {
        return new EmailNotificationSender();
    }

    /**
     * 
     * @param targetUser
     * @param msg
     */
    void sendNotification(User targetUser, String msg);
}
