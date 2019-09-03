package com.fullstack.sic.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.fullstack.sic.model.Sexo;
import com.fullstack.sic.repository.Sexos;

@FacesConverter(forClass = Sexo.class)
public class SexoConverter implements Converter
{
	@Inject
	private Sexos sexos;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value)
	{
		Sexo retorno = null;
		
		if (StringUtils.isNotEmpty(value))
		{
			Long id = new Long(value);
			retorno = sexos.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value)
	{
		
		if(value != null)
		{
			return ((Sexo) value).getId().toString();
		}
		
		return null;
	}

}