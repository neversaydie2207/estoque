package com.fullstack.sic.util.mail;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;

@RequestScoped
public class MailerSender implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Inject //Criar um produtor
	private JavaMailSenderImpl mailSender;
	
	
	public MimeMessage novaMensagem()
	{
		return mailSender.createMimeMessage();
	}
	
	public JavaMailSenderImpl mailSender()
	{
		return mailSender;
	}

}
