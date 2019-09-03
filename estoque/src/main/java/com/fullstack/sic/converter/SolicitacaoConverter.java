package com.fullstack.sic.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.fullstack.sic.model.Solicitacao;
import com.fullstack.sic.repository.Solicitacoes;

@FacesConverter(forClass = Solicitacao.class)
public class SolicitacaoConverter implements Converter {
	@Inject
	private Solicitacoes solicitacoes;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Solicitacao retorno = null;

		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			retorno = solicitacoes.porId(id);
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {

		if (value != null) {
			return ((Solicitacao) value).getId().toString();
		}

		return null;
	}

}