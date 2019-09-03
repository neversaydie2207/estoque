package com.fullstack.sic.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.fullstack.sic.model.Distrito;
import com.fullstack.sic.repository.Distritos;

@FacesConverter(forClass = Distrito.class)
public class DistritoConverter implements Converter
{
	@Inject
	private Distritos distritos;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value)
	{
		Distrito retorno = null;
		
		if (StringUtils.isNotEmpty(value))
		{
			Long id = new Long(value);
			retorno = distritos.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value)
	{
		
		if(value != null)
		{
			return ((Distrito) value).getId().toString();
		}
		
		return null;
	}

}