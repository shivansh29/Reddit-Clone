package com.example.RedditClone.demo.Spring.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationEmail {

	    private String subject;
	    private String recipient;
	    private String body;
	    
	    
	    
		
		public String getSubject() {
			return subject;
		}
		public void setSubject(String subject) {
			this.subject = subject;
		}
		public String getRecipient() {
			return recipient;
		}
		public void setRecipient(String recipient) {
			this.recipient = recipient;
		}
		public String getBody() {
			return body;
		}
		public void setBody(String body) {
			this.body = body;
		}
		
	    
	    
	    
}
