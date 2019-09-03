package com.fullstack.sic.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.fullstack.sic.model.Usuario;
import com.fullstack.sic.repository.Usuarios;

@FacesConverter(forClass = Usuario.class)
public class UsuarioConverter implements Converter
{
	@Inject
	private Usuarios usuarios;
	
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value)
	{
		Usuario retorno = null;
		
		if (StringUtils.isNotEmpty(value))
		{
			Long id = new Long(value);
			retorno = usuarios.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value)
	{
		if(value != null)
		{
			return ((Usuario) value).getId().toString();
		}
		return null;
	}
	
}
