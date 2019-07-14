package com.gogoyang.lifecapsule.utility.gogoMail;


import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class VerifyEmail extends Authenticator {
    String userName = "";
    String password = "";

    public VerifyEmail() {
        super();
    }

    public VerifyEmail(String userName, String password) {
        super();
        this.userName = userName;
        this.password = password;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(userName, password);
    }
}
