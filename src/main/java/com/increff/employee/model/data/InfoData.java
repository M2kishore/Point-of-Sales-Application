package com.increff.employee.model.data;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value="session", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class InfoData implements Serializable {

	private static final long serialVersionUID = 1L;

	private String message;
	private String signupMessage;
	private String email;

	public InfoData() {
		signupMessage = "";
		message = "";
		email = "No email";
	}

	public String getSignupMessage() {
		return signupMessage;
	}

	public void setSignupMessage(String signupMessage) {
		this.signupMessage = signupMessage;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
