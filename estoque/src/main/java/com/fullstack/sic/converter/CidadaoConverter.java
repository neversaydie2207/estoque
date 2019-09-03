package com.fullstack.sic.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.fullstack.sic.model.Cidadao;
import com.fullstack.sic.repository.Cidadaos;

@FacesConverter(forClass = Cidadao.class)
public class CidadaoConverter implements Converter
{
	@Inject
	private Cidadaos cidadaos;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value)
	{
		Cidadao retorno = null;
		
		if (StringUtils.isNotEmpty(value))
		{
			Long id = new Long(value);
			retorno = cidadaos.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value)
	{
		
		if(value != null)
		{
			return ((Cidadao) value).getId().toString();
		}
		
		return null;
	}

}