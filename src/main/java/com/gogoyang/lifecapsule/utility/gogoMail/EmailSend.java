package com.gogoyang.lifecapsule.utility.gogoMail;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class EmailSend {

    /**
     * 判断发送是否成功
     *
     * @param emailEntity
     * @return
     */
    public static boolean EmailSendTest(EmailEntity emailEntity) {
        try {
            //配置文件
            Properties properties = new Properties();
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.host", emailEntity.getHost());
            properties.put("mail.smtp.port", 25);

            //使用starrtls安全连接
            properties.put("mail.smtp.starrttls.enable", "true");

            //创建会话
            VerifyEmail verifyEmail = new VerifyEmail(emailEntity.getUserName(), emailEntity.getPassword());
            Session mailSession = Session.getInstance(properties, verifyEmail);
            mailSession.setDebug(true);

            //创建信息对象
            Message message = new MimeMessage(mailSession);
            InternetAddress from = new InternetAddress(emailEntity.getFromAddress());
            InternetAddress to = new InternetAddress(emailEntity.getToAddress());
            message.setFrom(from);
            message.setRecipient(MimeMessage.RecipientType.TO, to);
            message.setSubject(emailEntity.getSubject());
            message.setSentDate(new Date());
            message.setContent(emailEntity.getContext(), emailEntity.getContextType());
            message.saveChanges();

            //发送邮件
            Transport transport = mailSession.getTransport("smtp");
            transport.connect(emailEntity.getHost(), emailEntity.getUserName(), emailEntity.getPassword());
            System.out.println("trans:" + transport);
            transport.sendMessage(message, message.getAllRecipients());
            System.out.println("sent successful");

            return true;
        } catch (MessagingException ex) {
            ex.printStackTrace();
            System.out.println("send failed");
            return false;
        }
    }
}
