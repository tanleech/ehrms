/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapuraglobal.hrms.servlet.helper;

import java.io.IOException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author sapura-mac-pro-cto-C02PC1MWG3QT
 */
public class Emailer {
    
    private String smtp,user,password,subject,msgApply,orig,msgApprv;
    public Emailer()
    {
        
        Properties prop = new Properties();
	try {

                 prop = new Properties();
                 prop.load(getClass().getResourceAsStream("email.properties"));
		// load a properties file
		smtp = prop.getProperty("smtp");
		user = prop.getProperty("user");
		password = prop.getProperty("password");
                subject = prop.getProperty("subject");
                msgApply = prop.getProperty("messageApply");
                msgApprv = prop.getProperty("messageApprv");
                
                orig = prop.getProperty("user");

	} catch (IOException ex) {
		ex.printStackTrace();
	}
        
        }
    public void send(String type, String to)
    {
      // Setup mail server
      // Get system properties
      Properties prop = System.getProperties();
      prop.setProperty("mail.smtp.host", smtp);
      prop.setProperty("mail.user", user);
      prop.setProperty("mail.password", password);

      // Get the default Session object.
      Session session = Session.getDefaultInstance(prop);

      try{
         MimeMessage message = new MimeMessage(session);

         // Set From: header field of the header.
         message.setFrom(new InternetAddress(orig));

         // Set To: header field of the header.
         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

         // Set Subject: header field
         message.setSubject(subject);

         // Now set the actual message
         String msg = "";
         if(type.equalsIgnoreCase("APPLY"))
         {
             msg = msgApply;
         }
         else
         {
             msg = msgApprv;
         }
         message.setText(msg);
         // Send message
         Transport.send(message);
        }catch (MessagingException mex) {
         mex.printStackTrace();
      }
   } 
    }
        
