package com.fullstack.sic.controller;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.UUID;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import com.fullstack.sic.model.TokenSenha;
import com.fullstack.sic.model.Usuario;
import com.fullstack.sic.security.Encoder;
import com.fullstack.sic.service.CadastroUsuarioService;
import com.fullstack.sic.service.RecuperaSenhaService;
import com.fullstack.sic.util.jsf.FacesUtil;
import com.fullstack.sic.util.mail.EmailService;

/**
 * @author Giordano Mesquita
 *
 */

@Named
@ViewScoped
public class RecuperaSenhaBean implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Inject
	private CadastroUsuarioService usuarioService;

	@Inject
	private RecuperaSenhaService recuperaSenhaService;

	@Inject
	private Encoder encoder;

	@Inject
	private EmailService emailService;

	private TokenSenha tokenRecuperaSenha;

	private String tokenConfirmacao;

	private String email;
	private String cpf;

	private String novaSenha;
	private String confereSenha;
	
	private boolean tokenValidado;

	public void initTokenRecuperarSenha()
	{
		tokenRecuperaSenha = new TokenSenha();
		tokenValidado = false;
	
	}

	public void validarToken()
	{
		TokenSenha tokenValido = recuperaSenhaService.validarToken(tokenConfirmacao);

		if (tokenConfirmacao != null)
		{
			if (tokenValido != null)
			{
				if (tokenValido.isHabilitado())
				{
					Usuario usuario = usuarioService.porEmailAtivo(tokenValido.getEmail());
					
					usuario = salvarNovaSenha(usuario);

					if (usuario != null)
					{
						tokenValido.setValidado(true);
						tokenValido.setDataValidacao(new Date());
						tokenValido.setHabilitado(false);

						recuperaSenhaService.salvar(tokenValido);
						
						FacesUtil.addInfoMessage("Senha Atualizada com Sucesso!");
						tokenValidado = true;
						
						PrimeFaces.current().ajax().addCallbackParam("isValid", true);
						
					} 
					else
					{
						PrimeFaces.current().ajax().addCallbackParam("isValid", false);
						FacesUtil.addInfoMessage("Usuário não localizado!");
					}
				} 
				else
				{
					PrimeFaces.current().ajax().addCallbackParam("isValid", false);
					FacesUtil.addErrorMessage(
							"Esta solicitação foi cancelada. Por favor, caso deseje uma nova senha, recomece o processo "
									+ "de solicitação novamente.");
					
				}
			} 
			else
			{
				PrimeFaces.current().ajax().addCallbackParam("isValid", false);
				FacesUtil.addErrorMessage("Esta solicitação não encontra-se mais disponível.");
				
			}

		}
		else
		{
			PrimeFaces.current().ajax().addCallbackParam("isValid", false);
			FacesUtil.addErrorMessage("Operação Não Disponível! Por favor, entre em contato com o Suporte Técnico.");
		}

	}

	/**
	 * @param usuario
	 */
	private Usuario salvarNovaSenha(Usuario usuario)
	{
		if (!novaSenha.equals(confereSenha))
		{
			FacesUtil.addErrorMessage("Senhas Não Conferem!");
			return null;
		} else
		{
			String passwordcrypt = encoder.encodePassword(novaSenha, null);
			usuario.setSenha(passwordcrypt);

			return usuarioService.salvar(usuario);

		}
	}

	public void enviarTokenOnEmail() throws UnsupportedEncodingException
	{
		// Usuario usuarioRegistrado = usuarioService.porEmailOnCpf(email, cpf);
		Usuario usuarioRegistrado = usuarioService.porEmailAtivo(email);

		if (usuarioRegistrado != null)
		{
			TokenSenha tokenCriado = criarTokenOnEnviarEmail(usuarioRegistrado);

			if (tokenCriado.getId() != null)
			{
				PrimeFaces.current().ajax().addCallbackParam("isValid", true);
				FacesUtil.addInfoMessage("Instruções de recuperação de senha enviadas ao e-mail cadastrado!");
			} 
			else
			{
				PrimeFaces.current().ajax().addCallbackParam("isValid", false);
				FacesUtil.addInfoMessage(
						"Ocorreu um erro ao enviar instruções ao e-mail cadastrado! Por favor, tente novamente.");
			}

		}
		else
		{
			PrimeFaces.current().ajax().addCallbackParam("isValid", false);
			FacesUtil.addInfoMessage("Usuario(a) com E-Mail informado não foi localizado(a)");
		}
	}

	public TokenSenha criarTokenOnEnviarEmail(Usuario usuario) throws UnsupportedEncodingException
	{
		UUID uuid = UUID.randomUUID();

		tokenRecuperaSenha = recuperaSenhaService.verificarTokenPorEmail(usuario.getEmail());

		if (tokenRecuperaSenha == null)
			tokenRecuperaSenha = new TokenSenha();

		tokenRecuperaSenha.setToken(uuid.toString());
		tokenRecuperaSenha.setEmail(usuario.getEmail().toLowerCase());
		// tokenRecuperaSenha.setCpf(usuario.getCpf());
		tokenRecuperaSenha.setDataSolicitacao(new Date());
		tokenRecuperaSenha.setHabilitado(true);
		tokenRecuperaSenha.setValidado(false);
		tokenRecuperaSenha.setDataValidacao(null);

		tokenRecuperaSenha = recuperaSenhaService.salvar(tokenRecuperaSenha);

		if (tokenRecuperaSenha.getId() != null && usuario != null)
		{
			return emailService.enviarEmailRecuperaSenhaUsuario(usuario, tokenRecuperaSenha);
		} 
		else
		{
			FacesUtil.addErrorMessage("Ocorreu um problema ao gerar Token de Recuperação de Senha!");
			return null;
		}

	}

	// METODOS SET/GETS
	public TokenSenha getTokenRecuperaSenha()
	{
		return tokenRecuperaSenha;
	}

	public void setTokenRecuperaSenha(TokenSenha tokenRecuperaSenha)
	{
		this.tokenRecuperaSenha = tokenRecuperaSenha;
	}

	public String getTokenConfirmacao()
	{
		return tokenConfirmacao;
	}

	public void setTokenConfirmacao(String tokenConfirmacao)
	{
		this.tokenConfirmacao = tokenConfirmacao;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getCpf()
	{
		return cpf;
	}

	public void setCpf(String cpf)
	{
		this.cpf = cpf;
	}

	public String getNovaSenha()
	{
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha)
	{
		this.novaSenha = novaSenha;
	}

	public String getConfereSenha()
	{
		return confereSenha;
	}

	public void setConfereSenha(String confereSenha)
	{
		this.confereSenha = confereSenha;
	}

	public boolean isTokenValidado()
	{
		return tokenValidado;
	}

	public void setTokenValidado(boolean tokenValidado)
	{
		this.tokenValidado = tokenValidado;
	}

}
