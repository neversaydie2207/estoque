package com.fullstack.sic.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.fullstack.sic.model.Almoxarifado;
import com.fullstack.sic.repository.Almoxarifados;

@FacesConverter(forClass = Almoxarifado.class)
public class AlmoxarifadoConverter implements Converter {
	@Inject
	private Almoxarifados almoxarifados;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Almoxarifado retorno = null;

		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			retorno = almoxarifados.porId(id);
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {

		if (value != null) {
			return ((Almoxarifado) value).getId().toString();
		}

		return null;
	}

}