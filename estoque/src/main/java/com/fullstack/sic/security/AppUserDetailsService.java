package com.fullstack.sic.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.fullstack.sic.model.Usuario;
import com.fullstack.sic.repository.Usuarios;
import com.fullstack.sic.util.cdi.CDIServiceLocator;

public class AppUserDetailsService implements UserDetailsService
{
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{

		Usuarios usuarioRepository = CDIServiceLocator.getBean(Usuarios.class);
		Usuario usuario = usuarioRepository.buscarUsuarioOnUsername(username);

		UsuarioSistema user = null;

		if (usuario != null)
		{
			user = new UsuarioSistema(usuario, getGrupos(usuario));
		}
		else
		{
			throw new UsernameNotFoundException("Usuário não encontrado.");
		}

		return user;
	}
	
	
	private Collection<? extends GrantedAuthority> getGrupos(Usuario usuario)
	{
		// authoritys = grupos de permissões (roles)
		List<SimpleGrantedAuthority> authoritys = new ArrayList<SimpleGrantedAuthority>();
		
		// somente um grupo 
		if(usuario.getGrupo() != null)
		{
			authoritys.add(new SimpleGrantedAuthority("ROLE_" + usuario.getGrupo().getNome().toUpperCase()));
		}
		
		/* varios grupos 
		for (Grupo grupo : usuario.getGrupos())
		{
			// System.out.println("Grupos Nome: "+grupo.getNome());
			authoritys.add(new SimpleGrantedAuthority("ROLE_" + grupo.getNome().toUpperCase()));
		}
		*/
		
		return authoritys;
	}
}