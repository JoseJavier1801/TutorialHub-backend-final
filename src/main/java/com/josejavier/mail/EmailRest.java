package com.josejavier.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailRest {

    private final EmailPort emailPort;

    @Autowired
    public EmailRest(EmailPort emailPort) {
        this.emailPort = emailPort;
    }

    @PostMapping("/sendEmail")
    public boolean sendEmail(@RequestBody EmailBody emailBody) {
        return emailPort.sendEmail(emailBody);
    }
}
