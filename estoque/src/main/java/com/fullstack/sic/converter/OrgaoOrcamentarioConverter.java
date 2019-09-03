package com.fullstack.sic.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.fullstack.sic.model.OrgaoOrcamentario;
import com.fullstack.sic.repository.OrgaoOrcamentarios;

@FacesConverter(forClass = OrgaoOrcamentario.class)
public class OrgaoOrcamentarioConverter implements Converter {
	@Inject
	private OrgaoOrcamentarios orgaoOrcamentarios;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		OrgaoOrcamentario retorno = null;

		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			retorno = orgaoOrcamentarios.porId(id);
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {

		if (value != null) {
			return ((OrgaoOrcamentario) value).getId().toString();
		}

		return null;
	}

}