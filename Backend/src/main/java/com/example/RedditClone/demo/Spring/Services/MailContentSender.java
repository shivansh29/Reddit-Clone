package com.example.RedditClone.demo.Spring.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailContentSender {

	private final TemplateEngine template;

	@Autowired
	public MailContentSender(TemplateEngine template) {
		
		this.template = template;
	}
	
	public String build( String message) {
		Context ct=new Context();

	    ct.setVariable("message", message);
	    return template.process("mailTemplate", ct);
		
	}
	
	
}
