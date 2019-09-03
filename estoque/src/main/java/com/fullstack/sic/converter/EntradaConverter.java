package com.fullstack.sic.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.fullstack.sic.model.EntradaAlmoxarifado;
import com.fullstack.sic.repository.Entradas;

@FacesConverter(forClass = EntradaAlmoxarifado.class)
public class EntradaConverter implements Converter {
	@Inject
	private Entradas entradas;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		EntradaAlmoxarifado retorno = null;

		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			retorno = entradas.porId(id);
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {

		if (value != null) {
			return ((EntradaAlmoxarifado) value).getId().toString();
		}

		return null;
	}

}