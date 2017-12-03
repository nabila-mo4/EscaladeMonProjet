package com.escalade.servlets;

import javax.servlet.http.HttpServlet;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtilitaire extends HttpServlet{
	
	public static void envoiEmail(String hote, String port,
            final String utilisateur, final String motPasse, String adresse,
            String objet, String contenu) throws AddressException,
            MessagingException {
 
       
        Properties properties = new Properties();
        properties.put("mail.smtp.host", hote);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
 
       
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(utilisateur, motPasse);
            }
        };
 
        Session session = Session.getInstance(properties, auth);
 
      
        Message msg = new MimeMessage(session);
 
        msg.setFrom(new InternetAddress(utilisateur));
        InternetAddress[] toAddresses = { new InternetAddress(adresse) };
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(objet);
        msg.setSentDate(new Date());
        msg.setText(contenu);
 
       
        Transport.send(msg);
 
    }
	
	
	
	

}
