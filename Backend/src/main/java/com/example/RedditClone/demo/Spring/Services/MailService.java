package com.example.RedditClone.demo.Spring.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.RedditClone.demo.Spring.exception.SpringRedditException;
import com.example.RedditClone.demo.Spring.model.NotificationEmail;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MailService {
	
	private final MailContentSender contentSender;
	private final JavaMailSender mailSender ;
	
	
	//@Autowired
	public MailService(MailContentSender contentSender, JavaMailSender mailSender) {
		
		this.contentSender = contentSender;
		this.mailSender = mailSender;
	}



	@Async
	public void sendMail(NotificationEmail email) {
	
		  MimeMessagePreparator messagePreparator = mimeMessage -> {
	            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
	            messageHelper.setFrom("springreddit@email.com");
	            messageHelper.setTo(email.getRecipient());
	            messageHelper.setSubject(email.getSubject());
	            messageHelper.setText(contentSender.build(email.getBody()));
	        };
	        try {
	            mailSender.send(messagePreparator);
	            //Log.info("Activation email sent!!");
	            System.out.println("Activation email sent");
	        } catch (MailException e) {
	          //  Log.error("Exception occurred when sending mail", e);
	        	e.printStackTrace();
	            throw new SpringRedditException("Exception occurred when sending mail to " + email.getRecipient());
	        }
	}
}
