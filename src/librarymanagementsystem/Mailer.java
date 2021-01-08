/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;

/**
 *
 * @author root
 */
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class Mailer {

    public static void send(String from, String password, String to, String sub, String msg) {
        //Get properties object    
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        //get Session   
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });
        //compose message    
        try {
          //  InternetHeaders headers = new InternetHeaders();
            //headers.addHeader("Content-type", "text/html; charset=UTF-8");
          //  String html = "Test\n \n<a href='http://test.com'>Test.com</a>";
            
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.addHeader("Content-type", "text/html; charset=UTF-8");
            message.setSubject(sub);
           // message.setText(msg);
            message.setContent(msg, "text/html; charset=utf-8");
            //send message  
            Transport.send(message);
            System.out.println("message sent successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
}

 class JavaMailAPI {

    public static void main(String[] args) {
        //from,password,to,subject,message  
        Mailer.send(Utils.EMAIL, Utils.PASSWORD, "to@gmail.com", "Library Password", "Hey \n Library Admin Password Generated . \n Password is ");
        //change from, password and to  
    }
}
