package com.PMSystems.util;

import com.PMSystems.mail.*;
import com.PMSystems.logger.*;

import javax.mail.internet.*;
import javax.activation.*;
import java.io.*;
import java.util.*;
import javax.mail.*;




/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class AlertMail {

    private static MailResources resources;
    private static String[] smtpHosts;
    private static Properties info;
    private static Session session;

    static{
        try{
            resources = new MailResources();
            smtpHosts = resources.getSMTPHosts();
            info = new Properties();
            info.put("mail.smtp.host", smtpHosts[0]);
            info.put("mail.smtp.port", "" + resources.getSMTPPort());
            session = Session.getInstance(info, null);

        } catch(Exception ex) {
            WebServerLogger.getLogger().log(ex);
            ex.printStackTrace();
        }
    }

    private AlertMail() {
    }

    public static String[] getNotificationAddresses() {
        return resources.getNotificationAddress();
    }
    public static String getSystemEmailAddress() {
        return resources.getSystemAddress();
    }


    /**
     * Email will be sent in newly launched thread.
     * In this case confirmation of email sent/failed can't be relayed back to user.
     *
     * @param subject
     * @param messageText
     * @param fromEmail
     * @param toEmail
     * @param isHTML
     * @return
     */
    public static void sendMailInSeparateThread(final String subject, final String messageText
        ,final String fromEmail ,final String[] toEmail, final boolean isHTML) {

        //--- Sending email in separate thread ---
        new Thread(new Runnable() {
            public void run() {
                AlertMail.sendMail(subject,messageText,"",fromEmail,toEmail,"",false,isHTML,false);
            }
        }).start();
    }

    /**
     * Email will be sent in newly launched thread.
     * In this case confirmation of email sent/failed can't be relayed back to user.
     *
     * @param subject
     * @param messageText
     * @param fromEmail
     * @param toEmail
     * @param isAttachment
     * @param isHTML
     */
    public static void sendMailInSeparateThread(final String subject, final String messageText
        ,final String fromEmail ,final String[] toEmail, final boolean isAttachment
        , final boolean isHTML) {

        //--- Sending email in separate thread ---
        new Thread(new Runnable() {
            public void run() {
                AlertMail.sendMail(subject,messageText,"",fromEmail,toEmail,"",isAttachment,isHTML,false);
            }
        }).start();
    }

    /**
     *
     * @param subject
     * @param messageText
     * @param fromEmail
     * @param toEmail
     * @param isHTML
     * @return
     */
    public static boolean sendMail(String subject, String messageText,String fromEmail,String[] toEmail, boolean isHTML) {
        return sendMail(subject,messageText,"",fromEmail,toEmail,"",false,isHTML,false);
    }

    /**
     *
     * @param subject
     * @param messageText
     * @param fileName
     * @param fromEmail
     * @param toEmail
     * @param isAttachment
     * @param isHTML
     * @return
     */
    public static boolean sendMail(String subject, String messageText, String fileName, String fromEmail,String[] toEmail,
                          boolean isAttachment, boolean isHTML) {

          return sendMail(subject,messageText,fileName,fromEmail,toEmail,"",isAttachment,isHTML,false);
    }




    public static boolean sendMail(String subject, String messageText, String fileName, String fromEmail,
                                   String[] toEmail, boolean isAttachment, boolean isHTML, boolean bcc) {
        return sendMail(subject,messageText,fileName,fromEmail,toEmail,"",isAttachment,isHTML,bcc);
    }

    /**
     *  For Single Message
     * @param subject
     * @param messageText
     * @param fromEmail
     * @param toEmail
     * @param replyTo
     * @return
     */
    public static boolean sendMail(String subject, String messageText, String fromEmail, String[] toEmail,String replyTo) {
        return sendMail(subject,messageText,"",fromEmail,toEmail,replyTo,false,true,false);
    }


    private static boolean sendMail(String subject, String messageText, String fileName, String fromEmail,
                                   String[] toEmail, String replyTo, boolean isAttachment, boolean isHTML, boolean bcc) {

        fileName = Default.toDefault(fileName);

        try {
            InternetAddress[] toAddress = new InternetAddress[toEmail.length];
            String from = fromEmail;

            // Define message
            MimeMessage message = new MimeMessage(session);
            message.setHeader("Content-Type", "text/html; charset=utf-8");
            message.setFrom(new InternetAddress(Default.extractEmailWithoutName(from), Default.getPersonalNameInEmail(from), "utf-8"));
            //message.setFrom(new InternetAddress(from));
            message.setSubject(subject, "utf-8");

            for(int i=0;i<toAddress.length;i++) {
                toAddress[i] = new InternetAddress(toEmail[i]);
            }

            //--- Add ReplyTo .---
            if(!replyTo.equals("")){
                InternetAddress[] replyToAddress = new InternetAddress[1];
                replyToAddress[0] = new InternetAddress(replyTo);
                message.setReplyTo(replyToAddress);
            }

            if(bcc) {
              //sending a copy to system address.
              message.addRecipients(Message.RecipientType.BCC, toAddress);
            } else {
                message.addRecipients(Message.RecipientType.TO, toAddress);
            }

            message.setSentDate(new java.util.Date());

            Multipart multiPart = new MimeMultipart();

            //--- Check if message is Plain Text or Html.---
            if(isHTML==true) {
                MimeBodyPart bodyPart = new MimeBodyPart();
                bodyPart.setContent(messageText, "text/html; charset=utf-8");
                multiPart.addBodyPart(bodyPart);
            } else {
                MimeBodyPart bodyPart = new MimeBodyPart();
                bodyPart.setContent(messageText, "text/plain; charset=utf-8");
                multiPart.addBodyPart(bodyPart);
            }

            //--- File attachment if any ---
            if (isAttachment && !fileName.equals("")) {
                File file = new File(fileName);
                if(file.exists()) {
                    MimeBodyPart bodyPart = new MimeBodyPart();
                    bodyPart.setFileName(file.getName());
                    bodyPart.setDataHandler(new DataHandler(new FileDataSource(file)));
                    multiPart.addBodyPart(bodyPart);
                }
            }


            //--- Add Mulitpart to message if exists any.---
            message.setContent(multiPart);

            //--- Send message ---
            Transport.send(message);
            System.out.println("[AlertMail] EMAIL SENT From:"+fromEmail+", To: "+Default.toCSV(Default.toVecFromArray(toEmail))
                               +", Subj:"+subject+", Host:"+smtpHosts[0]);

        } catch (Exception ex) {
            ex.printStackTrace();
            WebServerLogger.getLogger().log(ex);
            return false;
        }
        return true;
    }

}