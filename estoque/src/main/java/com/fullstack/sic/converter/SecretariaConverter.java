package com.fullstack.sic.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.fullstack.sic.model.Secretaria;
import com.fullstack.sic.repository.Secretarias;

@FacesConverter(forClass = Secretaria.class)
public class SecretariaConverter implements Converter
{
	@Inject
	private Secretarias secretarias;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Secretaria retorno = null;
		
		if (StringUtils.isNotEmpty(value))
		{
			Long id = new Long(value);
			retorno = secretarias.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		if(value != null)
		{
			return ((Secretaria) value).getId().toString();
		}
		
		return null;
	}

}
