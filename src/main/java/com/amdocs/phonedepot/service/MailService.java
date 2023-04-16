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

	public void sendVerificationEmail(String fromAddress, String toAddress, String name, String verifyLink) throws IOException {
		// the sender email should be the same as we used to Create a Single Sender Verification
		Email from = new Email(fromAddress);
		Email to = new Email(toAddress);

		String subject = "Welcome to PhoneDepot by Amdocs Inc.";
		Content content = new Content("text/html", "<html><body><p>Welcome to PhoneDepot:</p> " +
				"<P>Hi " + name + "!</p>" +
				"<P>Thanks for signing Up with Phonedepot. Phonedepot is an eCommerce company that sells smartphone and smartphone accessories.</P>" +
				"<P>Please click on the below link to verify your Email: " + verifyLink + "</p>" +
				"<P>Note: The link is valid for only 2 minutes.</p>" +
				"</body></html>");

		Mail mail = new Mail(from, subject, to, content);
	
		SendGrid sg = new SendGrid("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");
		Request request = new Request();
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			Response response = sg.api(request);
			logger.info(response.getBody());
		} catch (IOException ex) {
			throw ex;
		}	   
	}
}