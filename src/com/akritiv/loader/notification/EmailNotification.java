/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.akritiv.loader.notification;

import com.adapter.loader.AkritivBean;
import com.adapter.loader.Load;
import java.io.File;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.log4j.Logger;


/**
 *
 * @author dell
 */
public class EmailNotification {

    static Logger logger = Logger.getLogger(EmailNotification.class);
    public static void sendEmail( final AkritivBean akritivLoader ) {

        akritivLoader.setEndTime( new Date());
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", akritivLoader.getMailTransportProtocol());
        props.setProperty("mail.host", akritivLoader.getMailHost());
        props.put("mail.smtp.auth", akritivLoader.getMailSmtpAuth());
        props.put("mail.smtp.port", akritivLoader.getMailSmtpPort());
        props.put("mail.smtp.ssl.enable", akritivLoader.getUseSSL());
        if ("true".equalsIgnoreCase(akritivLoader.getUseSSL())) {
            props.put("mail.smtp.socketFactory.port", akritivLoader.getMailSmtpSocketFactoryPort());
            props.put("mail.smtp.socketFactory.class", akritivLoader.getMailSmtpSocketFactoryClass());
            props.put("mail.smtp.socketFactory.fallback", akritivLoader.getMailSmtpSocketFactoryFallback());
        }
        if ( "YES".equalsIgnoreCase(akritivLoader.getUseAkritivSmtp())  ){
            props.put("mail.smtp.starttls.enable", "true");
        } else
            props.put("mail.smtp.starttls.enable", akritivLoader.getMailSmtpStartTLS());
        
        props.put("mail.smtp.quitwait", akritivLoader.getMailSmtpQuitwait());

        // Recipient's email ID needs to be mentioned.
        String from ="notifications@akritiv.com";

        // Sender's email ID needs to be mentioned
        String to [] = akritivLoader.getNotificationSubscribers().split(",");
        InternetAddress addresses [] = new InternetAddress[ to.length ];
        int i = 0;
        for ( String s : to ){
            try {
                addresses[i] = new InternetAddress(s);
            } catch (AddressException ex) {

                
            }

            i++;
        }

       final String username = ("YES".equalsIgnoreCase(akritivLoader.getUseAkritivSmtp()) )?"notifications@akritiv.com":akritivLoader.getMyEmail();
       final String password = ("YES".equalsIgnoreCase(akritivLoader.getUseAkritivSmtp()))?"@kritiv*notifications*123":akritivLoader.getMyPassword();
       Session session = Session.getInstance(props);
       if ( "true".equalsIgnoreCase(akritivLoader.getMailSmtpAuth())){
       session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username,password );
            }
        });
       }
        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(username));

            // Set To: header field of the header.
            message.addRecipients(Message.RecipientType.TO, addresses);

            // Set Subject: header field
            message.setSubject(akritivLoader.getSubject());

            // Create the message part
          

            // Fill the message
            String s = "Data Load Detail\n";
            s = s + "Organization : " + akritivLoader.getOrganizationName()+"\n";
            if ( akritivLoader.getException() != null ) {
                
                s = s + "Exception : " + akritivLoader.getException().getMessage()+"\n";
            }

            if ( akritivLoader.getProcess()!= null ) {

                s = s + "Process Name:  " + akritivLoader.getProcess().getProcessName()+"\n";
            }
           

            s = s + "Start Time : " + akritivLoader.getStartTime()+"\n";
            s = s + "End Time : " + akritivLoader.getEndTime()+"\n";
            message.setText(s);

            MimeBodyPart mbp = new MimeBodyPart();
            Multipart mp = new MimeMultipart();
            mbp.setText(s );
            mp.addBodyPart(mbp);

            if (akritivLoader.getAttachements() != null ){

                for( File attachement : akritivLoader.getAttachements() ) {

                    if ( attachement == null ) {

                        continue;
                        
                    }
                    
                    MimeBodyPart part = new MimeBodyPart();
                    FileDataSource fds = new FileDataSource(attachement);
                    part.setDataHandler(new DataHandler(fds));
                    part.setFileName(fds.getName());
                    mp.addBodyPart(part);
                    
                }
            }


            message.setContent(mp);
            // set the Date: header
            message.setSentDate(new Date());

            // Send message
            Transport.send(message);
            
        } catch (Exception mex) {

            mex.printStackTrace();
            
        }
       
    }

    public static void sendEmail( AkritivBean akritivLoader , Exception e ) {

       
        System.out.println(e.getMessage());
        akritivLoader.setSubject("[ERROR]- Exception in Data Load Process For "+akritivLoader.getOrganizationName());
        akritivLoader.setException(e);
        File files []  = new File []{ Load.getLogFile() };
        akritivLoader.setAttachements(files);
        sendEmail(akritivLoader);
        

    }


    public static void sendInitErrorEmail(  Exception e ) {


       
        AkritivBean loader = new AkritivBean();
        loader.setException(e);
        sendEmail( loader );


    }

    public static void sendSuccessEmail( AkritivBean akritivLoader  ) {

        if ( akritivLoader.getProcessedWithError().size() > 0 ) {

            akritivLoader.setSubject("[ERROR]- Data Load Process For "+akritivLoader.getOrganizationName());

        }else{

            akritivLoader.setSubject("[SUCCESS]- Data Load Process For "+akritivLoader.getOrganizationName());

        }
        
        File files []  = new File []{ Load.getLogFile() };
        akritivLoader.setAttachements(files);
        sendEmail(akritivLoader);


    }

    public static void sendEmail(AkritivBean akritivLoader , Load load , boolean error ) {

        if (error) {


        
            System.out.println(load.getProcessName() + " Completed With Error");
            akritivLoader.setSubject("[ERROR]- Data Load Process "+load.getProcessName()+" For "+akritivLoader.getOrganizationName());

            File files []  = new File []{ Load.getLogFile() , load.getErrorFile() };           

            akritivLoader.setAttachements(files);
            akritivLoader.setProcess(load);
            sendEmail(akritivLoader);

        } else {

            System.out.println(load.getProcessName() + " Completed successfuly");
            akritivLoader.setSubject("[SUCCESS]- Data Load Process "+load.getProcessName()+" For "+akritivLoader.getOrganizationName());

            File files []  = new File []{ Load.getLogFile() };

            akritivLoader.setAttachements(files);
            akritivLoader.setProcess(load);
            sendEmail(akritivLoader);

        }
        
    }

    public static void smtpTest( AkritivBean akritivLoader ){

        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", akritivLoader.getMailTransportProtocol());
        props.setProperty("mail.host", akritivLoader.getMailHost());
        props.put("mail.smtp.auth", akritivLoader.getMailSmtpAuth());
        props.put("mail.smtp.port", akritivLoader.getMailSmtpPort());

        if ("true".equalsIgnoreCase(akritivLoader.getUseSSL())) {
            props.put("mail.smtp.socketFactory.port", akritivLoader.getMailSmtpSocketFactoryPort());
            props.put("mail.smtp.socketFactory.class", akritivLoader.getMailSmtpSocketFactoryClass());
            props.put("mail.smtp.socketFactory.fallback", akritivLoader.getMailSmtpSocketFactoryFallback());
        }
        props.put("mail.smtp.quitwait", akritivLoader.getMailSmtpQuitwait());
        if ( "YES".equalsIgnoreCase(akritivLoader.getUseAkritivSmtp())  ){
            props.put("mail.smtp.starttls.enable", "true");
        }
        else
            props.put("mail.smtp.starttls.enable", akritivLoader.getMailSmtpStartTLS());
        
        props.put("mail.smtp.ssl.enable", akritivLoader.getMailSmtpStartTLS());

        // Recipient's email ID needs to be mentioned.
        String from ="notifications@akritiv.com";

        // Sender's email ID needs to be mentioned
        String to [] = akritivLoader.getNotificationSubscribers().split(",");
        InternetAddress addresses [] = new InternetAddress[ to.length ];
        int i = 0;
        for ( String s : to ){
            try {
                addresses[i] = new InternetAddress(s);
            } catch (AddressException ex) {


            }

            i++;
        }

       final String username = ("YES".equalsIgnoreCase(akritivLoader.getUseAkritivSmtp()) )?"notifications@akritiv.com":akritivLoader.getMyEmail();
       final String password = ("YES".equalsIgnoreCase(akritivLoader.getUseAkritivSmtp()))?"@kritiv*notifications*123":akritivLoader.getMyPassword();

       Session session = Session.getInstance(props);
       if ( "true".equalsIgnoreCase(akritivLoader.getMailSmtpAuth())){
       session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username,password );
            }
        });
       }
        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(username));

            // Set To: header field of the header.
            message.addRecipients(Message.RecipientType.TO, addresses);

            // Set Subject: header field
            message.setSubject("Akritiv Loader SMTP Test Email");

            // Create the message part

            // Fill the message
            String s = "Your SMTP settings are working  fine.\n";

            s = s + "mail.transport.protocol : " + akritivLoader.getMailTransportProtocol()+"\n";
            s = s + "mail.host : " + akritivLoader.getMailHost()+"\n";
            s = s + "mail.smtp.auth : " +akritivLoader.getMailSmtpAuth()+"\n";
            s = s + "mail.smtp.port : " + akritivLoader.getMailSmtpPort()+"\n";
            s = s + "mail.smtp.socketFactory.port : " + akritivLoader.getMailSmtpSocketFactoryPort()+"\n";
            s = s + "mail.smtp.socketFactory.class : " + akritivLoader.getMailSmtpSocketFactoryClass()+"\n";
            s = s + "mail.smtp.socketFactory.fallback : " + akritivLoader.getMailSmtpSocketFactoryFallback()+"\n";
            s = s + "mail.smtp.quitwait : " + akritivLoader.getMailSmtpQuitwait()+"\n";

            message.setText(s);
            // set the Date: header
            message.setSentDate(new Date());
            // Send message
            Transport.send(message);
            System.out.println("Sent email");

        } catch (MessagingException mex) {

            mex.printStackTrace();
        }
    }
}
