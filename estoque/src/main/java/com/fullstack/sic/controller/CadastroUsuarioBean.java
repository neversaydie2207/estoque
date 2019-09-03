package com.fullstack.sic.controller;

import java.io.Serializable;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.fullstack.sic.model.Grupo;
import com.fullstack.sic.model.Sexo;
import com.fullstack.sic.model.Usuario;
import com.fullstack.sic.repository.Sexos;
import com.fullstack.sic.security.Encoder;
import com.fullstack.sic.service.CadastroUsuarioService;
import com.fullstack.sic.service.NegocioException;
import com.fullstack.sic.util.comunicacao.TelegramUtil;
import com.fullstack.sic.util.jsf.FacesUtil;
import com.fullstack.sic.util.mail.MailerSender;
import com.sun.mail.util.MailSSLSocketFactory;

@Named
@ViewScoped
public class CadastroUsuarioBean implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Inject
	private Sexos serviceSexo;

	@Inject
	private CadastroUsuarioService usuarioService;

	@Inject
	private Encoder encoder;

	@Inject
	private MailerSender mailerSender;

	private Usuario usuario;

	private String confereSenha;

	private String codigoConfirmacao;

	private Boolean enabledButtomTermos;

	private Grupo grupoSelecionado;

	private List<Sexo> listSexo;

	private String cep;

	/* Adaptacao Velocity */
	@Autowired
	private VelocityEngine velocityEngine;

	/*
	 * METODOS INICIAIS
	 */

	public void initCadastroUsuarios()
	{
		if (FacesUtil.isNotPostback())
		{

			if (codigoConfirmacao != null)
			{
				validarEmail();
			} 
			else
			{
				usuario = new Usuario();
				setEnabledButtomTermos(false);

				listarSexo();
			}
		}
	}

	/* METODOS DE LISTAGEM */
	public void listarSexo()
	{
		listSexo = serviceSexo.buscarSexos();
	}

	/* METODOS DIVERSOS */
	public void validarEmail()
	{
		usuario = usuarioService.confirmarEmail(codigoConfirmacao);
	}

	public void aceitarTermos()
	{
		enabledButtomTermos = true;
	}
	
	public String cadastrarUsuario() throws UnsupportedEncodingException
	{
		UUID uuid = UUID.randomUUID();

		if (!usuario.getSenha().equals(getConfereSenha()))
		{
			FacesUtil.addErrorMessage("Senhas não conferem! :(");

			PrimeFaces.current().ajax().addCallbackParam("isValid", false);

			return null;
		}

		String passwordcrypt = encoder.encodePassword(getUsuario().getSenha(), null);

		grupoSelecionado = usuarioService.buscarGrupo("VISITANTES");

		// usuario.getGrupos().add(grupoSelecionado);

		usuario.setGrupo(grupoSelecionado);

		usuario.setEmail(usuario.getEmail());
		usuario.setSenha(passwordcrypt);

		usuario.setAtivo(false);
		usuario.setCc(uuid.toString());

		usuario = usuarioService.salvar(usuario);

		if (usuario.getId() != null)
		{
			try
			{

				MimeMessage message = mailerSender.novaMensagem();

				MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);

				mimeMessageHelper.setTo(usuario.getEmail());
				mimeMessageHelper.setSubject("Bem-Vindo(a) " + usuario.getApelido().substring(0, 1).toUpperCase() + ""
						+ usuario.getApelido().substring(1).toLowerCase());

				VelocityEngine ve = new VelocityEngine();

				ServletContext sc = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
						.getContext();

				Properties p = new Properties();

				p.setProperty("resource.loader", "webapp");
				p.setProperty("webapp.resource.loader.class", "org.apache.velocity.tools.view.WebappResourceLoader");

				p.setProperty("webapp.resource.loader.path", "/WEB-INF/velocity/");

				p.setProperty("RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS",
						"org.apache.velocity.runtime.log.Log4JLogChute");
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

			} catch (MessagingException e)
			{
				e.printStackTrace();

				throw new NegocioException("Ocorreu um problema no envio de e-mail. Tente novamente mais tarde.");

			} catch (GeneralSecurityException e)
			{
				e.printStackTrace();

				throw new NegocioException("Ocorreu um problema no envio de e-mail. Tente novamente mais tarde.");
			}

		} else
		{
			usuario = new Usuario();
			PrimeFaces.current().ajax().addCallbackParam("isValid", false);
			return null;
		}

	}

	// Para fins de testes
	public void testeEnviarEmail() throws UnsupportedEncodingException
	{

		try
		{

			MimeMessage message = mailerSender.novaMensagem();

			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);

			mimeMessageHelper.setTo("giordano221@gmail.com");
			mimeMessageHelper.setSubject("Bem-Vindo(a) Giordano");

			VelocityEngine ve = new VelocityEngine();

			ServletContext sc = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

			Properties p = new Properties();

			p.setProperty("resource.loader", "webapp");
			p.setProperty("webapp.resource.loader.class", "org.apache.velocity.tools.view.WebappResourceLoader");

			p.setProperty("webapp.resource.loader.path", "/WEB-INF/velocity/");

			p.setProperty("RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS",
					"org.apache.velocity.runtime.log.Log4JLogChute");
			p.setProperty("runtime.log.logsystem.log4j.logger", "LOGGER_VELOCITY");

			ve.setApplicationAttribute("javax.servlet.ServletContext", sc);

			ve.init(p);

			VelocityContext context = new VelocityContext();

			context.put("nome", "Giordano M Galdino");

			Template t = ve.getTemplate("teste.vm"); // ERRO AQUI

			StringWriter writer = new StringWriter();

			t.merge(context, writer);

			mimeMessageHelper.setText(writer.toString(), true);

			mimeMessageHelper.setFrom("contato@paypercash.com.br", "PayPerCash");

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

			FacesUtil.addInfoMessage("Email Enviado");

		} catch (MessagingException e)
		{
			String message = "Erro ao Enviar E-Mail Cadastro Novo Usuario" + "\n\n++++++++++++" + e.getMessage();

			String[] contato = { "98117598" };

			TelegramUtil.enviarMensagem(message, contato);

			e.printStackTrace();

			throw new NegocioException("Ocorreu um problema no envio de e-mail. Tente novamente mais tarde.");

		} catch (GeneralSecurityException e)
		{
			String message = "Erro Envio E-Mail Cadastro Novo Usuario" + "\n\n++++++++++++" + e.getMessage();

			String[] contato = { "98117598" };

			TelegramUtil.enviarMensagem(message, contato);

			e.printStackTrace();

			throw new NegocioException("Ocorreu um problema no envio de e-mail. Tente novamente mais tarde.");
		}

	}

	/*
	 * METODOS DE LISTAGEM
	 */

	public Usuario getUsuario()
	{
		return usuario;
	}

	public void setUsuario(Usuario usuario)
	{
		this.usuario = usuario;
	}

	public String getConfereSenha()
	{
		return confereSenha;
	}

	public void setConfereSenha(String confereSenha)
	{
		this.confereSenha = confereSenha;
	}

	public String getCodigoConfirmacao()
	{
		return codigoConfirmacao;
	}

	public void setCodigoConfirmacao(String codigoConfirmacao)
	{
		this.codigoConfirmacao = codigoConfirmacao;
	}

	public Boolean getEnabledButtomTermos()
	{
		return enabledButtomTermos;
	}

	public void setEnabledButtomTermos(Boolean enabledButtomTermos)
	{
		this.enabledButtomTermos = enabledButtomTermos;
	}

	public Grupo getGrupoSelecionado()
	{
		return grupoSelecionado;
	}

	public void setGrupoSelecionado(Grupo grupoSelecionado)
	{
		this.grupoSelecionado = grupoSelecionado;
	}

	public List<Sexo> getListSexo()
	{
		return listSexo;
	}

	public void setListSexo(List<Sexo> listSexo)
	{
		this.listSexo = listSexo;
	}

	public VelocityEngine getVelocityEngine()
	{
		return velocityEngine;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine)
	{
		this.velocityEngine = velocityEngine;
	}

	public String getCep()
	{
		return cep;
	}

	public void setCep(String cep)
	{
		this.cep = cep;
	}

}
