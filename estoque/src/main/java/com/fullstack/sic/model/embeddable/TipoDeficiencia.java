package com.fullstack.sic.model.embeddable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TipoDeficiencia
{
	@Column(name = "deficiencia")
	private boolean isDeficiencia = false;

	// TGD - Transtorno Global do Desenvolvimento
	@Column(name = "transtorno")
	private boolean isTranstorno = false;

	@Column(name = "superdotacao")
	private boolean isSuperdotacao = false;

	/**
	 * Tipos Deficiencia
	 */

	@Column(name = "baixa_visao")
	private boolean isBaixaVisao = false;

	@Column(name = "cegueira")
	private boolean isCegueira = false;

	@Column(name = "deficiancia_auditiva")
	private boolean isDeficienciaAuditiva = false;

	@Column(name = "deficiencia_fisica")
	private boolean isDeficienciaFisica = false;

	@Column(name = "deficiencia_intelectual")
	private boolean isDeficienciaIntelectual = false;

	@Column(name = "surdez")
	private boolean isSurdez = false;

	@Column(name = "surdo_cegueira")
	private boolean isSurdoCegueira = false;

	@Column(name = "deficiencia_multipla")
	private boolean isDeficienciaMultipla = false;

	/**
	 * Tipos Transtorno Global do Desevolvimento
	 */

	@Column(name = "autismo_infantil")
	private boolean isAutismoInfantil = false;

	@Column(name = "sindrome_asperger")
	private boolean isSindromeAsperger = false;

	@Column(name = "sindrome_rett")
	private boolean isSindromeRett = false;

	@Column(name = "tdi") // Transtorno Desintegrativo da Infancia
	private boolean isTdi = false;

	@Column(name = "tdah") // Transtorno do Déficit de Atenção com
							// Hiperatividade
	private boolean isTdah = false;

	/**
	 * Tipos Altas Habilidades / Superdotação
	 */

	@Column(name = "altas_habilidades")
	private boolean isAltasHabilitades = false;

	public boolean isDeficiencia()
	{
		return isDeficiencia;
	}

	public void setDeficiencia(boolean isDeficiencia)
	{
		this.isDeficiencia = isDeficiencia;
	}

	public boolean isTranstorno()
	{
		return isTranstorno;
	}

	public void setTranstorno(boolean isTranstorno)
	{
		this.isTranstorno = isTranstorno;
	}

	public boolean isSuperdotacao()
	{
		return isSuperdotacao;
	}

	public void setSuperdotacao(boolean isSuperdotacao)
	{
		this.isSuperdotacao = isSuperdotacao;
	}

	public boolean isBaixaVisao()
	{
		return isBaixaVisao;
	}

	public void setBaixaVisao(boolean isBaixaVisao)
	{
		this.isBaixaVisao = isBaixaVisao;
	}

	public boolean isCegueira()
	{
		return isCegueira;
	}

	public void setCegueira(boolean isCegueira)
	{
		this.isCegueira = isCegueira;
	}

	public boolean isDeficienciaAuditiva()
	{
		return isDeficienciaAuditiva;
	}

	public void setDeficienciaAuditiva(boolean isDeficienciaAuditiva)
	{
		this.isDeficienciaAuditiva = isDeficienciaAuditiva;
	}

	public boolean isDeficienciaFisica()
	{
		return isDeficienciaFisica;
	}

	public void setDeficienciaFisica(boolean isDeficienciaFisica)
	{
		this.isDeficienciaFisica = isDeficienciaFisica;
	}

	public boolean isDeficienciaIntelectual()
	{
		return isDeficienciaIntelectual;
	}

	public void setDeficienciaIntelectual(boolean isDeficienciaIntelectual)
	{
		this.isDeficienciaIntelectual = isDeficienciaIntelectual;
	}

	public boolean isSurdez()
	{
		return isSurdez;
	}

	public void setSurdez(boolean isSurdez)
	{
		this.isSurdez = isSurdez;
	}

	public boolean isSurdoCegueira()
	{
		return isSurdoCegueira;
	}

	public void setSurdoCegueira(boolean isSurdoCegueira)
	{
		this.isSurdoCegueira = isSurdoCegueira;
	}

	public boolean isDeficienciaMultipla()
	{
		return isDeficienciaMultipla;
	}

	public void setDeficienciaMultipla(boolean isDeficienciaMultipla)
	{
		this.isDeficienciaMultipla = isDeficienciaMultipla;
	}

	public boolean isAutismoInfantil()
	{
		return isAutismoInfantil;
	}

	public void setAutismoInfantil(boolean isAutismoInfantil)
	{
		this.isAutismoInfantil = isAutismoInfantil;
	}

	public boolean isSindromeAsperger()
	{
		return isSindromeAsperger;
	}

	public void setSindromeAsperger(boolean isSindromeAsperger)
	{
		this.isSindromeAsperger = isSindromeAsperger;
	}

	public boolean isSindromeRett()
	{
		return isSindromeRett;
	}

	public void setSindromeRett(boolean isSindromeRett)
	{
		this.isSindromeRett = isSindromeRett;
	}

	public boolean isTdi()
	{
		return isTdi;
	}

	public void setTdi(boolean isTdi)
	{
		this.isTdi = isTdi;
	}

	public boolean isTdah()
	{
		return isTdah;
	}

	public void setTdah(boolean isTdah)
	{
		this.isTdah = isTdah;
	}

	public boolean isAltasHabilitades()
	{
		return isAltasHabilitades;
	}

	public void setAltasHabilitades(boolean isAltasHabilitades)
	{
		this.isAltasHabilitades = isAltasHabilitades;
	}
	
	

}
