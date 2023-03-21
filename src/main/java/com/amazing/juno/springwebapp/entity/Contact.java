package com.amazing.juno.springwebapp.entity;

public class Contact {
	private String title;
	private String closing;
	private String appreciation;
	private String buttonContent;
	private String email;
	
	public Contact() {
	}

	public Contact(String title, String closing, String appreciation, String buttonContent, String email) {
		this.title = title;
		this.closing = closing;
		this.appreciation = appreciation;
		this.buttonContent = buttonContent;
		this.email = email;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getClosing() {
		return closing;
	}
	
	public void setClosing(String closing) {
		this.closing = closing;
	}
	
	public String getAppreciation() {
		return appreciation;
	}

	public void setAppreciation(String appreciation) {
		this.appreciation = appreciation;
	}

	public String getButtonContent() {
		return buttonContent;
	}
	
	public void setButtonContent(String buttonContent) {
		this.buttonContent = buttonContent;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "ContactEntity [title=" + title + ", closing=" + closing + ", appreciation=" + appreciation
				+ ", buttonContent=" + buttonContent + ", email=" + email + "]";
	}

	
	
}
