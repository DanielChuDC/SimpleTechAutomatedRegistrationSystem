package cx2002grp2.stars;

import cx2002grp2.stars.data.dataitem.Registration;
import cx2002grp2.stars.data.dataitem.User;

/**
 * Common interface for all the notification sender.
 */
public interface NotificationSender {
    /**
     * Send the given text message to the given user.
     * 
     * @param targetUser the target user to send the notification.
     * @param subject    the subject of notification.
     * @param msg        the notification to be sent
     */
    void sendNotification(User targetUser, String subject, String msg);

    /**
     * Send an registration approval message for the given registration.
     * 
     * @param reg the registration being approved.
     */
    void sendWaitlistNotification(Registration reg);
}
