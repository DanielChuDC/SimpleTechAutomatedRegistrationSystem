package cx2002grp2.stars;

import cx2002grp2.stars.data.dataitem.User;

public interface NotificationSender {
    /**
     * 
     * @param targetUser
     * @param msg
     */
    void sendNotification(User targetUser, String msg);
    void sendWaitlistNotification(Registration reg, String msg);
}
