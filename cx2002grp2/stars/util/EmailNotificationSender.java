package cx2002grp2.stars.util;

import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import cx2002grp2.stars.Configs;
import cx2002grp2.stars.NotificationSender;
import cx2002grp2.stars.dataitem.Course;
import cx2002grp2.stars.dataitem.CourseIndex;
import cx2002grp2.stars.dataitem.Registration;
import cx2002grp2.stars.dataitem.Student;
import cx2002grp2.stars.dataitem.User;

/**
 * Notification sender implemented with email.
 * <p>
 * Most of the output of this class will not be printed. Instead, it will be
 * logged into log file "email.log"
 */
public class EmailNotificationSender implements NotificationSender {

    private Logger log;

    public EmailNotificationSender() {
        // Initialize logger.
        log = Configs.getLogger(this.getClass().getName());
        log.setLevel(Level.ALL);

        // Disable printing onto screen
        log.setUseParentHandlers(false);

        // Set logging file.
        try {
            log.addHandler(new FileHandler("email.log", 1 << 20 /* 1 MB */, 5, true));
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
            System.err.println("Email logger initialization failed. No output will be recorded.");
        }

        log.info("Finished initialization.");
    }

    @Override
    public void sendNotification(User targetUser, String subject, String msg) {
        String senderEmail = Configs.getSystemEmailAddr();
        String senderPassword = Configs.getSystemEmailPasswd();
        String receiverEmail = targetUser.getEmail();

        asyncSendEmail(senderEmail, senderPassword, receiverEmail, subject, msg);
    }

    /**
     * Setup notification for when student is added into a course from waitlist
     * 
     * @param reg The registration object containing student and course information
     */
    public void sendWaitlistNotification(Registration reg) {
        // get senderEmail and password from config
        Student student = reg.getStudent();
        Course course = reg.getCourse();
        CourseIndex idx = reg.getCourseIndex();
        String subject = "Course " + course.getCourseCode() + " " + course.getCourseName() + " Added";
        String content = "Dear " + student.getFullName() + ",\n" + "You have been added to the course "
                + course.getCourseCode() + " " + course.getCourseName() + ".\n"
                + "Your index number for this course is " + idx.getIndexNo();

        sendNotification(student, subject, content);
    }

    /**
     * Send a email from given sender account to the given receiver containing the
     * given subject and content.
     * 
     * @param senderEmail    the sender's email account.
     * @param senderPassword the sender's password.
     * @param receiverEmail  the receiver's email account.
     * @param subject        the subject of email.
     * @param content        the content of email.
     */
    private void sendEmail(String senderEmail, String senderPassword, String receiverEmail, String subject,
            String content) {

        log.info("Starts sending email to " + receiverEmail);

        // Get system properties
        Properties props = new Properties();

        // Setup mail server
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        // props.put("mail.smtp.ssl.enable", "true");

        // public static Session getInstance(Properties prop, Authenticator auth)
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        // Used to debug SMTP issues
        // session.setDebug(true);

        try {
            // Create a default MimeMessage object
            Message message = new MimeMessage(session);

            // Set who is sending, receiving, subject and message
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiverEmail));
            message.setSubject(subject);
            message.setText(content);

            // Send message
            Transport.send(message);

            log.info("Email sent to " + receiverEmail);
        } catch (Exception e) {
            String failMessage = e.getLocalizedMessage() + "\nFailed to send the email to " + receiverEmail;
            System.err.println(failMessage);
            log.severe(failMessage);
        }
    }

    /**
     * Async send a email from given sender account to the given receiver containing
     * the given subject and content.
     * <P>
     * {@link EmailNotificationSender#sendEmail(String, String, String, String, String)}
     * will be called.
     * 
     * @param senderEmail    the sender's email account.
     * @param senderPassword the sender's password.
     * @param receiverEmail  the receiver's email account.
     * @param subject        the subject of email.
     * @param content        the content of email.
     */
    private void asyncSendEmail(String senderEmail, String senderPassword, String receiverEmail, String subject,
            String content) {
        Runnable task = () -> sendEmail(senderEmail, senderPassword, receiverEmail, subject, content);
        Thread thread = new Thread(task);
        thread.start();
    }
}
