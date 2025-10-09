package com.banksimulation.service.serviceimpl;


import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import com.banksimulation.service.EmailService;
import java.util.Properties;

public class EmailServiceImpl implements EmailService{

    public boolean emailSender(String from,String to,String subject, String text) {
        boolean flag=false;
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        String username=from;
        String password="aukuowrocrdmdxol";

        Session session=Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username,password);
            }
        });
        try {
            session.setDebug(true);
            Message message=new MimeMessage(session);
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setFrom(new InternetAddress(from));
            message.setSubject(subject);
            message.setText(text);
            Transport.send(message);
            flag=true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

}
