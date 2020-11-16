package cx2002grp2.stars.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import cx2002grp2.stars.Configs;
import cx2002grp2.stars.NotificationSender;
import cx2002grp2.stars.data.dataitem.User;
import cx2002grp2.stars.data.dataitem.Course;
import cx2002grp2.stars.data.dataitem.CourseIndex;
import cx2002grp2.stars.data.dataitem.Registration;
import cx2002grp2.stars.data.dataitem.Student;

public class EmailNotificationSender implements NotificationSender {

    @Override
    public void sendNotification(User targetUser, String msg) {
        String senderEmail = Configs.getSystemEmailAddr();
        String senderPassword = Configs.getSystemEmailPasswd();
        String receiverEmail = targetUser.getEmail();

        System.out.println("Sending email to " + receiverEmail);
        sendEmail(senderEmail, senderPassword, receiverEmail, "", msg);
        System.out.println("Email has been sent to " + receiverEmail);

        // throw new UnsupportedOperationException();
    }

    /**
     * Setup notification for when student is added into a course from waitlist
     * 
     * @param reg The registration object containing student and course information
     */
    public void sendWaitlistNotification(Registration reg) {
        // get senderEmail and password from config
        String senderEmail = Configs.getSystemEmailAddr();
        String senderPassword = Configs.getSystemEmailPasswd();
        Student student = reg.getStudent();
        Course course = reg.getCourse();
        CourseIndex idx = reg.getCourseIndex();
        String receiverEmail = student.getEmail();
        String subject = "Course " + course.getCourseCode() + " " + course.getCourseName() + " Added";
        String content = "Dear " + student.getFullName() + "\n" + "You have been added to the course "
                + course.getCourseCode() + " " + course.getCourseName() + ".\n"
                + "Your index number for this course is " + idx.getIndexNo();


        // TODO: (Optional) async - test first
        System.out.println("Sending email to " + receiverEmail);
        sendEmail(senderEmail, senderPassword, receiverEmail, subject, content);
        System.out.println("Email has been sent to " + receiverEmail);

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
        
        // Get system properties
        Properties props = new Properties();

        // Setup mail server
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.enable", "true");


        // public static Session getInstance(Properties prop, Authenticator auth)
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        // Used to debug SMTP issues
        session.setDebug(true);

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

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
