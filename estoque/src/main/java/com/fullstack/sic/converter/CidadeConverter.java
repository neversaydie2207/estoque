package com.fullstack.sic.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.fullstack.sic.model.Cidade;
import com.fullstack.sic.repository.Cidades;

@FacesConverter(forClass = Cidade.class)
public class CidadeConverter implements Converter
{
	@Inject
	private Cidades cidades;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value)
	{
		Cidade retorno = null;
		
		if (StringUtils.isNotEmpty(value))
		{
			Long id = new Long(value);
			retorno = cidades.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value)
	{
		
		if(value != null)
		{
			return ((Cidade) value).getId().toString();
		}
		
		return null;
	}

}