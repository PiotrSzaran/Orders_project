package pl.szaran.service;

import pl.szaran.exceptions.ExceptionCode;
import pl.szaran.exceptions.MyException;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public interface EmailService {

    static void send(String recipient, String subject, String htmlMessage) {

        if (recipient == null) {
            throw new MyException(ExceptionCode.SERVICE, "send email - recipient is null");
        }

        if (subject == null) {
            throw new MyException(ExceptionCode.SERVICE, "send email - subject is null");
        }

        if (htmlMessage == null) {
            throw new MyException(ExceptionCode.SERVICE, "send email - html message is null");
        }

        try {

            final String emailAddress = ""; //pełny adres
            final String emailPassword = ""; //hasło

            System.out.println("Sending email ...");
            Session session = createSession(emailAddress, emailPassword);
            MimeMessage message = new MimeMessage(session);
            prepareEmailMessage(emailAddress, message, recipient, subject, htmlMessage);
            Transport.send(message);
            System.out.println("Email has been sent ...");

        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ExceptionCode.SERVICE, "send email exception");
        }
    }

    private static void prepareEmailMessage(String emailAddress, MimeMessage message, String recipient, String subject, String htmlMessage) {

        try {
            message.setContent(htmlMessage, "text/html; charset=utf-8");
            message.setFrom(new InternetAddress(emailAddress));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            MimeBodyPart messageBodyPart = new MimeBodyPart();

            final String attachmentName = "customers.json"; //todo dorobic opcje wyboru zalacznika

            Multipart multipart = new MimeMultipart();
            DataSource source = new FileDataSource(attachmentName);

            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(attachmentName);
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);

        } catch (Exception e) {
            throw new MyException(ExceptionCode.SERVICE, "prepare email message exception");
        }
    }

    private static Session createSession(String emailAddress, String emailPassword) {
        Properties properties = new Properties();
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");

        return Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailAddress, emailPassword);
            }
        });
    }
}