package com.amdocs.phonedepot.service;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

/**
 * @author Dhanapal
 */
@Service
public class MailService {
	private static final Logger logger = LoggerFactory.getLogger(MailService.class);
	
	public String sendTextEmail(String varFrom, String varTo, String varSubject, String varContent) throws IOException {
		    // the sender email should be the same as we used to Create a Single Sender Verification
		    Email from = new Email(varFrom);
            Email to = new Email(varTo);
		    String subject = varSubject;
		    Content content = new Content("text/plain", varContent);
		    Mail mail = new Mail(from, subject, to, content);
		
		    SendGrid sg = new SendGrid("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");
		    Request request = new Request();
		    try {
		      request.setMethod(Method.POST);
		      request.setEndpoint("mail/send");
		      request.setBody(mail.build());
		      Response response = sg.api(request);
		      logger.info(response.getBody());
		      return response.getBody();	     
		    } catch (IOException ex) {
		      throw ex;
		    }	   
	}
}