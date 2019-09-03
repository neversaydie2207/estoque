package com.fullstack.sic.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.fullstack.sic.model.Estado;
import com.fullstack.sic.repository.Estados;

@FacesConverter(forClass = Estado.class)
public class EstadoConverter implements Converter
{
	@Inject
	private Estados estados;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value)
	{
		Estado retorno = null;
		
		if (StringUtils.isNotEmpty(value))
		{
			Long id = new Long(value);
			retorno = estados.porId(id);
		}

		return retorno;
		
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value)
	{
		
		if(value != null)
		{
			return ((Estado) value).getId().toString();
		}
		
		return null;
	}

}