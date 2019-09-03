package com.fullstack.sic.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.fullstack.sic.model.StatusSolicitacao;
import com.fullstack.sic.repository.StatusSolicitacoes;

@FacesConverter(forClass = StatusSolicitacao.class)
public class StatusSolicitacaoConverter implements Converter {
	@Inject
	private StatusSolicitacoes statusSolicitacoes;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		StatusSolicitacao retorno = null;

		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			retorno = statusSolicitacoes.porId(id);
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {

		if (value != null) {
			return ((StatusSolicitacao) value).getId().toString();
		}

		return null;
	}

}