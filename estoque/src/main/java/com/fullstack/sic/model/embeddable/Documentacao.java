package com.fullstack.sic.model.embeddable;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fullstack.sic.model.Cidade;
import com.fullstack.sic.model.Estado;
import com.fullstack.sic.model.enums.ModeloCertidaoCivil;
import com.fullstack.sic.model.enums.OrgaoEmissorIdentidade;
import com.fullstack.sic.model.enums.TipoCertidaoCivil;

@Embeddable
public class Documentacao
{
	private String identidade;

	@Enumerated(EnumType.STRING)
	private OrgaoEmissorIdentidade orgaoEmissorIdentidade;

	private Estado ufIdentidade;

	@Column(name = "data_expedicao_rg")
	@Temporal(TemporalType.DATE)
	private Date dataExpedicaoIdentidade;

	@Enumerated(EnumType.STRING)
	private ModeloCertidaoCivil modeloCertidaoCivil;

	@Enumerated(EnumType.STRING)
	private TipoCertidaoCivil tipoCertidaoCivil;

	@Column(name = "numero_termo_cv")
	private String numeroTermoCertidaoCivil;

	@Column(name = "folha_cv")
	private String folhaCertidaoCivil;

	@Column(name = "livro_cv")
	private String livroCertidaoCivil;

	@Column(name = "data_emissao_cv")
	@Temporal(TemporalType.DATE)
	private Date dataEmissaoCertidaoCivil;

	@ManyToOne
	private Estado ufCertidaoCivil;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Cidade cidadeCartorio;
	
	private String cartorio;

	// PARA CERTIDOES NOVAS - 32 CARACTERES
	@Column(name = "matricula_registro_civil")
	private String matriculaRegistroCivil;

	private String passaporte;

	public String getIdentidade()
	{
		return identidade;
	}

	public void setIdentidade(String identidade)
	{
		this.identidade = identidade;
	}

	public OrgaoEmissorIdentidade getOrgaoEmissorIdentidade()
	{
		return orgaoEmissorIdentidade;
	}

	public void setOrgaoEmissorIdentidade(OrgaoEmissorIdentidade orgaoEmissorIdentidade)
	{
		this.orgaoEmissorIdentidade = orgaoEmissorIdentidade;
	}

	public Estado getUfIdentidade()
	{
		return ufIdentidade;
	}

	public void setUfIdentidade(Estado ufIdentidade)
	{
		this.ufIdentidade = ufIdentidade;
	}

	public Date getDataExpedicaoIdentidade()
	{
		return dataExpedicaoIdentidade;
	}

	public void setDataExpedicaoIdentidade(Date dataExpedicaoIdentidade)
	{
		this.dataExpedicaoIdentidade = dataExpedicaoIdentidade;
	}

	public ModeloCertidaoCivil getModeloCertidaoCivil()
	{
		return modeloCertidaoCivil;
	}

	public void setModeloCertidaoCivil(ModeloCertidaoCivil modeloCertidaoCivil)
	{
		this.modeloCertidaoCivil = modeloCertidaoCivil;
	}

	public TipoCertidaoCivil getTipoCertidaoCivil()
	{
		return tipoCertidaoCivil;
	}

	public void setTipoCertidaoCivil(TipoCertidaoCivil tipoCertidaoCivil)
	{
		this.tipoCertidaoCivil = tipoCertidaoCivil;
	}

	public String getNumeroTermoCertidaoCivil()
	{
		return numeroTermoCertidaoCivil;
	}

	public void setNumeroTermoCertidaoCivil(String numeroTermoCertidaoCivil)
	{
		this.numeroTermoCertidaoCivil = numeroTermoCertidaoCivil;
	}

	public String getFolhaCertidaoCivil()
	{
		return folhaCertidaoCivil;
	}

	public void setFolhaCertidaoCivil(String folhaCertidaoCivil)
	{
		this.folhaCertidaoCivil = folhaCertidaoCivil;
	}

	public String getLivroCertidaoCivil()
	{
		return livroCertidaoCivil;
	}

	public void setLivroCertidaoCivil(String livroCertidaoCivil)
	{
		this.livroCertidaoCivil = livroCertidaoCivil;
	}

	public Date getDataEmissaoCertidaoCivil()
	{
		return dataEmissaoCertidaoCivil;
	}

	public void setDataEmissaoCertidaoCivil(Date dataEmissaoCertidaoCivil)
	{
		this.dataEmissaoCertidaoCivil = dataEmissaoCertidaoCivil;
	}

	public String getCartorio()
	{
		return cartorio;
	}

	public void setCartorio(String cartorio)
	{
		this.cartorio = cartorio;
	}

	public String getMatriculaRegistroCivil()
	{
		return matriculaRegistroCivil;
	}

	public void setMatriculaRegistroCivil(String matriculaRegistroCivil)
	{
		this.matriculaRegistroCivil = matriculaRegistroCivil;
	}

	public String getPassaporte()
	{
		return passaporte;
	}

	public void setPassaporte(String passaporte)
	{
		this.passaporte = passaporte;
	}

	public Estado getUfCertidaoCivil()
	{
		return ufCertidaoCivil;
	}

	public void setUfCertidaoCivil(Estado ufCertidaoCivil)
	{
		this.ufCertidaoCivil = ufCertidaoCivil;
	}

	public Cidade getCidadeCartorio()
	{
		return cidadeCartorio;
	}

	public void setCidadeCartorio(Cidade cidadeCartorio)
	{
		this.cidadeCartorio = cidadeCartorio;
	}

}
