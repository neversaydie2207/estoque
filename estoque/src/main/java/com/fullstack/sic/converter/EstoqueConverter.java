package com.fullstack.sic.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.fullstack.sic.model.Estoque;
import com.fullstack.sic.repository.Estoques;

@FacesConverter(forClass = Estoque.class)
public class EstoqueConverter implements Converter {
	@Inject
	private Estoques estoques;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Estoque retorno = null;

		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			retorno = estoques.porId(id);
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {

		if (value != null) {
			return ((Estoque) value).getId().toString();
		}

		return null;
	}

}