package com.fullstack.sic.security;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class MyAuthSuccessHandler implements AuthenticationSuccessHandler
{

	protected Log logger = LogFactory.getLog(this.getClass());

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException
	{
		handle(request, response, authentication);
		clearAuthenticationAttributes(request);
	}

	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException
	{
		String targetUrl = determineTargetUrl(authentication);

		if (response.isCommitted())
		{
			logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
			return;
		}

		redirectStrategy.sendRedirect(request, response, targetUrl);
	}

	/**
	 * Builds the target URL according to the logic defined in the main class
	 * Javadoc.
	 */
	protected String determineTargetUrl(Authentication authentication)
	{
		boolean isPerfilMultiplo = false;
		boolean isAdmin = false;
		boolean isMaster = false;
		boolean isVisitante = false;
		
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		
		if(authorities.size() > 1)
		{
			isPerfilMultiplo = true;
		}
		else
		{
			for (GrantedAuthority grantedAuthority : authorities)
			{
				if (grantedAuthority.getAuthority().equals("ROLE_VISITANTES"))
				{
					isVisitante = true;
					break;
				}
				else if(grantedAuthority.getAuthority().equals("ROLE_ADMINISTRADORES"))
				{
					isAdmin = true;
					break;
				}
				else if(grantedAuthority.getAuthority().equals("ROLE_MASTERS"))
				{
					isMaster = true;
					break;
				}
			}
		}
		
		if(isPerfilMultiplo)
		{
			return "/selecao_perfil.jsf";
		}
		else if (isVisitante)
		{
			return "/dashboard_visitante.jsf";
		} 
		else if(isAdmin)
		{
			return "/dashboard.jsf";
		}
		else if(isMaster)
		{
			return "/dashboard.jsf";
		}
		else
		{
			return "/dashboard_visitante.jsf";
			//throw new IllegalStateException();
		}
		
	}

	protected void clearAuthenticationAttributes(HttpServletRequest request)
	{
		HttpSession session = request.getSession(false);
		if (session == null)
		{
			return;
		}
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}

	public void setRedirectStrategy(RedirectStrategy redirectStrategy)
	{
		this.redirectStrategy = redirectStrategy;
	}

	protected RedirectStrategy getRedirectStrategy()
	{
		return redirectStrategy;
	}

}
