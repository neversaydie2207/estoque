package com.fullstack.sic.util.mail;

import java.io.IOException;
import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import org.springframework.mail.javamail.JavaMailSenderImpl;

public class MailSenderProducer
{
	@Produces
	@ApplicationScoped
	public JavaMailSenderImpl getJavaMailSenderImpl() throws IOException
	{
		
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		
		mailSender.setUsername("contato@paypercash.com.br");
		mailSender.setPassword("140213@2016");
		mailSender.setHost("mail.paypercash.com.br");
		
		/*
		mailSender.setUsername("pronatec@seduc.ce.gov.br");
		mailSender.setPassword("Pronatecseduc");
		mailSender.setHost("smtp.seduc.ce.gov.br");
		*/
		
		Properties props = new Properties();
		
		
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.userset", "true");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.ssl.trust", "*");
		props.put("mail.smtp.port", "587");
		props.put("mail.mime.charset", "UTF-8");

		props.put("mail.mime.address.strict", "false");

		props.put("mail.smtp.requiresAuthentication", "true");
		props.put("mail.transport.protocol", "smtps");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.socketFactory.fallback", "false");
		props.put("mail.smtp.socketFactory.port", "587");

		props.put("mail.debug", "false");

		mailSender.setJavaMailProperties(props);
		
		return mailSender;
		
	}

}
