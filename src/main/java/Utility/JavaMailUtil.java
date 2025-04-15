package Utility;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class JavaMailUtil {

    // ✅ Replace with your actual Gmail credentials
    private static final String EMAIL_ID = "sagarchauhan08439@gmail.com";
    private static final String PASSWORD = "Sagar@260523"; // Use Gmail App Password

    // 📩 Sends a simple welcome message
    public static void sendMail(String recipientMailId) throws MessagingException {
        System.out.println("Preparing to send Mail");

        Properties properties = getMailProperties();

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_ID, PASSWORD);
            }
        });

        Message message = prepareMessage(session, EMAIL_ID, recipientMailId);

        Transport.send(message);
        System.out.println("Message Sent Successfully!");
    }

    // 📩 Sends custom subject and HTML content
    public static void sendMail(String recipient, String subject, String htmlTextMessage) throws MessagingException {
        System.out.println("Preparing to send Mail");

        Properties properties = getMailProperties();

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_ID, PASSWORD);
            }
        });

        Message message = prepareMessage(session, EMAIL_ID, recipient, subject, htmlTextMessage);

        Transport.send(message);
        System.out.println("Message Sent Successfully!");
    }

    // 📦 Email configuration
    private static Properties getMailProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", "587");
        return properties;
    }

    // 📨 Message for welcome
    private static Message prepareMessage(Session session, String senderEmail, String recipientEmail) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject("Welcome to Ellison Electronics");
            message.setText("Hey " + recipientEmail + ", Thanks for signing up with us!");
            return message;
        } catch (Exception ex) {
            Logger.getLogger(JavaMailUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    // 📨 Message for custom HTML email
    private static Message prepareMessage(Session session, String senderEmail, String recipientEmail, String subject, String htmlTextMessage) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject(subject);
            message.setContent(htmlTextMessage, "text/html");
            return message;
        } catch (Exception ex) {
            Logger.getLogger(JavaMailUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
