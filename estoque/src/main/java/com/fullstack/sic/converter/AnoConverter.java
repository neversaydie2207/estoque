package com.fullstack.sic.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.fullstack.sic.model.Ano;
import com.fullstack.sic.repository.Anos;

@FacesConverter(forClass = Ano.class)
public class AnoConverter implements Converter
{
	@Inject
	private Anos anos;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value)
	{
		Ano retorno = null;
		
		if (StringUtils.isNotEmpty(value))
		{
			Long id = new Long(value);
			retorno = anos.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value)
	{
		
		if(value != null)
		{
			return ((Ano) value).getId().toString();
		}
		
		return null;
	}

}