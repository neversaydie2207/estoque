package com.fullstack.sic.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.fullstack.sic.model.Grupo;
import com.fullstack.sic.repository.Grupos;

//@FacesConverter("grupoConverter")
@FacesConverter(forClass = Grupo.class)
public class GrupoConverter implements Converter
{
	@Inject
	private Grupos grupos;
	

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value)
	{
		Grupo retorno = null;
		
		if (StringUtils.isNotEmpty(value))
		{
			Integer id = new Integer(value);
			retorno = grupos.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value)
	{
		if(value != null)
		{
			return ((Grupo) value).getId().toString();
		}
		return null;
	}
	
}
