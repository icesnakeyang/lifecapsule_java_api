package com.gogoyang.lifecapsule.business.admin.email;

import com.gogoyang.lifecapsule.utility.gogoMail.EmailEntity;
import com.gogoyang.lifecapsule.utility.gogoMail.EmailSend;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminEmailBusinessService implements IAdminEmailBusinessService {
    @Override
    public Map sendEmail(Map in) throws Exception {
        String recipientEmail = in.get("recipientEmail").toString();
        String subject = in.get("subject").toString();
        String context = in.get("context").toString();

        EmailEntity emailEntity = new EmailEntity();
        emailEntity.setHost("smtp.yeah.net");
        emailEntity.setPort(25);
        emailEntity.setUserName("gogoyangsoft");
        emailEntity.setPassword("33455432ccdeedd");
        emailEntity.setFromAddress("gogoyangsoft@yeah.net");
        emailEntity.setToAddress(recipientEmail);
        emailEntity.setSubject(subject);
        emailEntity.setContext(context);
        emailEntity.setContextType("text/html;charset=utf-8");

        boolean isSend = EmailSend.EmailSendTest(emailEntity);

        Map out = new HashMap();
        out.put("send", isSend);
        return out;
    }
}
