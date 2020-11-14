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
import cx2002grp2.stars.NotificationSender;
import cx2002grp2.stars.data.dataitem.User;

public class EmailNotificationSender implements NotificationSender {

    @Override
    public void sendNotification(User targetUser, String msg) {
        String senderEmail;
        String senderPassword;
        String receiverEmail;

        // throw new UnsupportedOperationException();
    }

    public void sendWaitlistNotification(Registration reg, String msg) {
        // get senderEmail and password from config
        String senderEmail;
        String senderPassword;
        Student student = reg.getStudent();
        Course course = reg.getCourse();
        CourseIndex idx = reg.getCourseIndex();
        String receiverEmail = student.getEmail();
        String subject = "Course " + course.getCourseCode() + " " + course.getCourseName() + " Added";
        String content = "Dear " + student.getFullName + "\n" + "You have been added to the course "
                + course.getCourseCode() + " " + course.getCourseName() + ".\n"
                + "Your index number for this course is " + idx.getIndexNo();

        String line = "";
        String data[];
        BufferedReader br = new BufferedReader(new FileReader("../../../tables/config.csv"));
        while ((line = br.readLine()) != null) {

            if (line.startsWith("system_email")) {
                data = line.split(",");
                senderEmail = data[1];
            }
            if (line.startsWith("system_email_passwd")) {
                data = line.split(",");
                senderPassword = data[0];
            }

        }

        // TODO: async - test first
        System.out.println("Sending email");
        sendEmail(senderEmail, senderPassword, receiverEmail, subject, content);
        System.out.println("Email has been sent");

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
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // public static Session getInstance(Properties prop, Authenticator auth)
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiverEmail));
            message.setSubject(subject);
            message.setText(content);

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
