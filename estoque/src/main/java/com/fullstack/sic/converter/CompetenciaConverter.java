package com.fullstack.sic.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.fullstack.sic.model.Competencia;
import com.fullstack.sic.repository.Competencias;

@FacesConverter(forClass = Competencia.class)
public class CompetenciaConverter implements Converter
{
	@Inject
	private Competencias competencias;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value)
	{
		Competencia retorno = null;
		
		if (StringUtils.isNotEmpty(value))
		{
			Long id = new Long(value);
			retorno = competencias.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value)
	{
		
		if(value != null)
		{
			return ((Competencia) value).getId().toString();
		}
		
		return null;
	}

}