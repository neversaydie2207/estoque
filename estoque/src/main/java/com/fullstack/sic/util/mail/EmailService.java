package com.fullstack.sic.util.mail;

import java.io.Serializable;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.primefaces.PrimeFaces;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.fullstack.sic.model.TokenSenha;
import com.fullstack.sic.model.Usuario;
import com.fullstack.sic.service.NegocioException;
import com.sun.mail.util.MailSSLSocketFactory;

public class EmailService implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private MailerSender mailerSender;
	
	public String enviarEmailBemVindoUsuario(Usuario usuario) throws UnsupportedEncodingException
	{
		try
		{
			
			MimeMessage message = mailerSender.novaMensagem();

			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);

			mimeMessageHelper.setTo(usuario.getEmail());
			mimeMessageHelper.setSubject("Bem-Vindo(a) " + usuario.getApelido().substring(0,1).toUpperCase() + "" + usuario.getApelido().substring(1).toLowerCase());

			VelocityEngine ve = new VelocityEngine();

			ServletContext sc = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			
			Properties p = new Properties();
			
			p.setProperty("resource.loader", "webapp");
			p.setProperty("webapp.resource.loader.class",
					"org.apache.velocity.tools.view.WebappResourceLoader");
			
			p.setProperty("webapp.resource.loader.path", "/WEB-INF/velocity/");
			
			p.setProperty("RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS", "org.apache.velocity.runtime.log.Log4JLogChute");
			p.setProperty("runtime.log.logsystem.log4j.logger", "LOGGER_VELOCITY");
			
			ve.setApplicationAttribute("javax.servlet.ServletContext", sc);

			ve.init(p);
			
			VelocityContext context = new VelocityContext();

			context.put("usuario", usuario);

			Template t = ve.getTemplate("ativar-usuario.vm");
			t.setEncoding("UTF-8");
			
			StringWriter writer = new StringWriter();

			t.merge(context, writer);

			mimeMessageHelper.setText(writer.toString(), true);

			mimeMessageHelper.setFrom("contato@paypercash.com.br", "Prefeitura Municipal de Itapipoca");

			MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
			mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
			mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
			mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
			mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
			mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
			CommandMap.setDefaultCommandMap(mc);

			mailerSender.mailSender().setProtocol("smtps");

			MailSSLSocketFactory socketFactory = new MailSSLSocketFactory();
			socketFactory.setTrustAllHosts(true);

			mailerSender.mailSender().getSession().getProperties().put("mail.smtps.socketFactory", socketFactory);

			mailerSender.mailSender().send(message);
		
			usuario = new Usuario();
			
			PrimeFaces.current().ajax().addCallbackParam("isValid", true);
			
			return null; 

		} 
		catch (MessagingException e)
		{
			e.printStackTrace();
			
			throw new NegocioException("Ocorreu um problema no envio de e-mail. Tente novamente mais tarde.");
			
		} 
		catch (GeneralSecurityException e)
		{
			e.printStackTrace();
			
			throw new NegocioException("Ocorreu um problema no envio de e-mail. Tente novamente mais tarde.");
		}
	}
	
	public TokenSenha enviarEmailRecuperaSenhaUsuario(Usuario usuario, TokenSenha tokenRecuperaSenha) throws UnsupportedEncodingException
	{
		try
		{
			
			MimeMessage message = mailerSender.novaMensagem();

			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);

			mimeMessageHelper.setTo(usuario.getEmail());
			mimeMessageHelper.setSubject("LOTUS - Recuperar Senha");

			VelocityEngine ve = new VelocityEngine();

			ServletContext sc = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			
			Properties p = new Properties();
			
			p.setProperty("resource.loader", "webapp");
			p.setProperty("webapp.resource.loader.class",
					"org.apache.velocity.tools.view.WebappResourceLoader");
			
			p.setProperty("webapp.resource.loader.path", "/WEB-INF/velocity/");
			
			p.setProperty("RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS", "org.apache.velocity.runtime.log.Log4JLogChute");
			p.setProperty("runtime.log.logsystem.log4j.logger", "LOGGER_VELOCITY");
			
			ve.setApplicationAttribute("javax.servlet.ServletContext", sc);

			ve.init(p);
			
			VelocityContext context = new VelocityContext();

			context.put("usuario", usuario);
			context.put("token", tokenRecuperaSenha.getToken());

			Template t = ve.getTemplate("cadastrarsenha.vm", "UTF-8");
			t.setEncoding("UTF-8");
			
			StringWriter writer = new StringWriter();

			t.merge(context, writer);

			mimeMessageHelper.setText(writer.toString(), true);

			mimeMessageHelper.setFrom("contato@paypercash.com.br", "Prefeitura Municipal de Itapipoca");

			MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
			mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
			mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
			mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
			mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
			mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
			CommandMap.setDefaultCommandMap(mc);

			mailerSender.mailSender().setProtocol("smtps");

			MailSSLSocketFactory socketFactory = new MailSSLSocketFactory();
			socketFactory.setTrustAllHosts(true);

			mailerSender.mailSender().getSession().getProperties().put("mail.smtps.socketFactory", socketFactory);

			mailerSender.mailSender().send(message);
		
			return tokenRecuperaSenha;

		} 
		catch (MessagingException e)
		{
			e.printStackTrace();
			
			throw new NegocioException("Ocorreu um problema no envio de e-mail. Tente novamente mais tarde.");
			
		} 
		catch (GeneralSecurityException e)
		{
			e.printStackTrace();
			
			throw new NegocioException("Ocorreu um problema no envio de e-mail. Tente novamente mais tarde.");
		}

	}

}
