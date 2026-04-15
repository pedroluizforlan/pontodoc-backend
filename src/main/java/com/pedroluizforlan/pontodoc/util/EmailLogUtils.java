package com.pedroluizforlan.pontodoc.util;

import com.pedroluizforlan.pontodoc.model.Collaborator;
import com.pedroluizforlan.pontodoc.model.EmailLog;

public class EmailLogUtils {

    public static EmailLog createEmailLog(Collaborator collaborator, EmailLog.EmailType type, String subject, String emailBody){
        EmailLog emailLog = new EmailLog();
        emailLog.setCollaborator(collaborator);
        emailLog.setEmailSubject(subject);
        emailLog.setEmailType(type);
        emailLog.setEmailBody(emailBody);
        return emailLog;
    }

}
