package com.fullstack.sic.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.fullstack.sic.model.Movimentacao;
import com.fullstack.sic.repository.Movimentacoes;

@FacesConverter(forClass = Movimentacao.class)
public class MovimentacaoConverter implements Converter {
	@Inject
	private Movimentacoes movimentacoes;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Movimentacao retorno = null;

		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			retorno = movimentacoes.porId(id);
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {

		if (value != null) {
			return ((Movimentacao) value).getId().toString();
		}

		return null;
	}

}