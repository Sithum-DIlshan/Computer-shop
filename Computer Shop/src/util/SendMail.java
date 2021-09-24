package util;

import db.DbConnection;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class SendMail {
    public static void sendMail(String id) {

        try {
            ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement(
                    "SELECT * FROM Customer WHERE customerId = '" + id + "'").executeQuery();


            Properties properties = new Properties();

            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");

            String myAccountEmail = "jagathasaman@gmail.com";
            String password = "jagatha123@@";

            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(myAccountEmail, password);
                }
            });

            Message message = prepareMessage(session, myAccountEmail, rst.getString(6));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private static Message prepareMessage(Session session, String myAccountEmail, String recipient) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("Order Detail");
            message.setText("Order Placed Successfully");

            return message;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
