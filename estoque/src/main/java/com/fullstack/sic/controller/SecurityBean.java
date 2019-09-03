package com.fullstack.sic.controller;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.springframework.security.core.GrantedAuthority;

import com.fullstack.sic.util.jsf.FacesUtilSecurity;

@Named
@RequestScoped
public class SecurityBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public String encriptar(String value)
	{
		return FacesUtilSecurity.encodeBase64(value);
	}
	
	public String determineTargetUrl(Collection<? extends GrantedAuthority> authorities)
	{
		boolean isPerfilMultiplo = false;
		boolean isAdmin = false;
		boolean isMaster = false;
		boolean isVisitante = false;
		
		if(authorities != null)
		{
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
}
