package com.fullstack.sic.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.fullstack.sic.model.EmissorRcc;
import com.fullstack.sic.repository.EmissoresRcc;

@FacesConverter(forClass = EmissorRcc.class)
public class EmissorRccConverter implements Converter
{
	@Inject
	private EmissoresRcc Emissores;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value)
	{
		EmissorRcc retorno = null;
		
		if (StringUtils.isNotEmpty(value))
		{
			Long id = new Long(value);
			retorno = Emissores.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value)
	{
		
		if(value != null)
		{
			return ((EmissorRcc) value).getId().toString();
		}
		
		return null;
	}

}