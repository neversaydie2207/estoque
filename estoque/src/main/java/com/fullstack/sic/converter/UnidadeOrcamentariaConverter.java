package com.fullstack.sic.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.fullstack.sic.model.UnidadeOrcamentaria;
import com.fullstack.sic.repository.UnidadeOrcamentarias;

@FacesConverter(forClass = UnidadeOrcamentaria.class)
public class UnidadeOrcamentariaConverter implements Converter
{
	@Inject
	private UnidadeOrcamentarias unidadeOrcamentarias;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		UnidadeOrcamentaria retorno = null;
		
		if (StringUtils.isNotEmpty(value))
		{
			Long id = new Long(value);
			retorno = unidadeOrcamentarias.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		if(value != null)
		{
			return ((UnidadeOrcamentaria) value).getId().toString();
		}
		
		return null;
	}

}