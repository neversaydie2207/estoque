package com.fullstack.sic.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Base64;
import java.util.Date;

import javax.faces.context.FacesContext;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.poi.util.IOUtils;

import com.fullstack.sic.model.embeddable.Endereco;
import com.fullstack.sic.model.embeddable.TransporteEscolar;
import com.fullstack.sic.model.enums.EstadoCivil;
import com.fullstack.sic.model.enums.Etnia;
import com.fullstack.sic.model.enums.OrgaoEmissorIdentidade;

@Entity
@Table(name = "tb_cidadao")
public class Cidadao implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String cpf;
	
	private String nis;
	
	private String cns;

	@NotNull(message = "Campo NOME é obrigatório")
	private String nome;

	// @NotNull(message = "Campo DATA NASCIMENTO é obrigatório")
	@Column(name = "data_nascimento")
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;

	@Enumerated(EnumType.STRING)
	// @NotNull(message = "Campo ETNIA é obrigatório")
	private Etnia etnia;

	@ManyToOne
	// @NotNull(message = "Campo SEXO é obrigatório")
	private Sexo sexo;

	@ManyToOne
	@JoinColumn(name = "uf_nascimento_id")
	// @NotNull(message = "campo UF DE NASCIMENTO é obrigatório")
	private Estado ufNascimento;

	@ManyToOne
	@JoinColumn(name = "cidade_nascimento_id")
	// @NotNull(message = "campo CIDADE DE NASCIMENTO é obrigatório")
	private Cidade cidadeNascimento;

	// @NotNull(message = "Campo NOME DA MÃE é obrigatório")
	@Column(name = "nome_mae")
	private String nomeMae;

	@Column(name = "data_nascimento_mae")
	@Temporal(TemporalType.DATE)
	private Date dataNascimentoMae;

	@Column(name = "nome_pai")
	private String nomePai;

	@Column(name = "data_nascimento_pai")
	@Temporal(TemporalType.DATE)
	private Date dataNascimentoPai;

	// @NotNull(message = "Campo IDENTIDADE é obrigatório")
	private String identidade;

	@Enumerated(EnumType.STRING)
	// @NotNull(message = "campo ORGÃO EMISSOR é obrigatório")
	private OrgaoEmissorIdentidade orgaoEmissorIdentidade;

	@ManyToOne
	@JoinColumn(name = "uf_rg_id")
	// @NotNull(message = "campo UF DA IDENTIDADE é obrigatório") (retirado em
	// 18/12/2017 pq não tras com criteria)
	private Estado ufIdentidade;

	@Column(name = "data_expedicao_rg")
	@Temporal(TemporalType.DATE)
	// @NotNull(message = "campo DATA EXPEDIÇÃO é obrigatório")
	private Date dataExpedicaoIdentidade;

	@Column(name = "titulo_eleitor")
	private String tituloEleitor;

	@Column(name = "zona_eleitoral")
	private String zonaEleitoral;

	@Column(name = "secao_eleitoral")
	private String secaoEleitoral;

	@Column(name = "reservista")
	private String reservista;

	@Column(name = "pis_pasep")
	private String pisPasep;

	// ESTADO CIVIL
	@Enumerated(EnumType.STRING)
	// @NotNull(message = "campo ESTADO CIVIL é obrigatório")
	@Column(name = "estado_civil")
	private EstadoCivil estadoCivil;

	@Lob
	@Column(name = "foto")
	private byte[] foto;

	@Transient
	private String imagemFoto;

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

	@Embedded
	private Endereco endereco;

	private String celular;
	private String telefone;
	private String email;

	@Embedded
	private TransporteEscolar transporteEscolar;

	//add nivel escolaridade

	@Column(name = "dados_atualizados")
	private boolean isAtualizado = false;

	@ManyToOne
	@JoinColumn(name = "responsavel_id")
	private Usuario responsavelPorAtualizar;

	@Column(name = "data_atualizacao_cadastral")
	@Temporal(TemporalType.DATE)
	private Date dataAtualizacaoCadastral;

	@Column(name = "registro_conselho_classe")
	private String registroConselhoClasse;

	@ManyToOne
	@JoinColumn(name = "emissor_rcc_id")
	private EmissorRcc orgaoEmissorRcc;
	
	@ManyToOne
	@JoinColumn(name = "ufrcc_id")
	private Estado ufRcc;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getCpf()
	{
		return cpf;
	}

	public void setCpf(String cpf)
	{
		this.cpf = cpf;
	}

	public String getNis()
	{
		return nis;
	}

	public void setNis(String nis)
	{
		this.nis = nis;
	}

	public String getCns()
	{
		return cns;
	}

	public void setCns(String cns)
	{
		this.cns = cns;
	}

	public String getNome()
	{

		return nome;
	}

	public void setNome(String nome)
	{
		this.nome = nome.toUpperCase();
	}

	public Date getDataNascimento()
	{
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento)
	{
		this.dataNascimento = dataNascimento;
	}

	public Etnia getEtnia()
	{
		return etnia;
	}

	public void setEtnia(Etnia etnia)
	{
		this.etnia = etnia;
	}

	public Sexo getSexo()
	{
		return sexo;
	}

	public void setSexo(Sexo sexo)
	{
		this.sexo = sexo;
	}

	public Estado getUfNascimento()
	{
		return ufNascimento;
	}

	public void setUfNascimento(Estado ufNascimento)
	{
		this.ufNascimento = ufNascimento;
	}

	public Cidade getCidadeNascimento()
	{
		return cidadeNascimento;
	}

	public void setCidadeNascimento(Cidade cidadeNascimento)
	{
		this.cidadeNascimento = cidadeNascimento;
	}

	public String getNomeMae()
	{
		return nomeMae;
	}

	public void setNomeMae(String nomeMae)
	{
		this.nomeMae = nomeMae;
	}

	public Date getDataNascimentoMae()
	{
		return dataNascimentoMae;
	}

	public void setDataNascimentoMae(Date dataNascimentoMae)
	{
		this.dataNascimentoMae = dataNascimentoMae;
	}

	public String getNomePai()
	{
		return nomePai;
	}

	public void setNomePai(String nomePai)
	{
		this.nomePai = nomePai;
	}

	public Date getDataNascimentoPai()
	{
		return dataNascimentoPai;
	}

	public void setDataNascimentoPai(Date dataNascimentoPai)
	{
		this.dataNascimentoPai = dataNascimentoPai;
	}

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

	public String getTituloEleitor()
	{
		return tituloEleitor;
	}

	public void setTituloEleitor(String tituloEleitor)
	{
		this.tituloEleitor = tituloEleitor;
	}

	public String getZonaEleitoral()
	{
		return zonaEleitoral;
	}

	public void setZonaEleitoral(String zonaEleitoral)
	{
		this.zonaEleitoral = zonaEleitoral;
	}

	public String getSecaoEleitoral()
	{
		return secaoEleitoral;
	}

	public void setSecaoEleitoral(String secaoEleitoral)
	{
		this.secaoEleitoral = secaoEleitoral;
	}

	public String getReservista()
	{
		return reservista;
	}

	public void setReservista(String reservista)
	{
		this.reservista = reservista;
	}

	public String getPisPasep()
	{
		return pisPasep;
	}

	public void setPisPasep(String pisPasep)
	{
		this.pisPasep = pisPasep;
	}

	public EstadoCivil getEstadoCivil()
	{
		return estadoCivil;
	}

	public void setEstadoCivil(EstadoCivil estadoCivil)
	{
		this.estadoCivil = estadoCivil;
	}

	public byte[] getFoto()
	{
		return foto;
	}

	public void setFoto(byte[] foto)
	{
		this.foto = foto;
	}

	public String getImagemFoto() throws IOException
	{
		if (this.getFoto() != null)
		{
			return Base64.getEncoder().encodeToString(this.getFoto());
		} else
		{
			FacesContext context = FacesContext.getCurrentInstance();
			InputStream io = context.getExternalContext()
					.getResourceAsStream("//resources//images//imagem-servidor.png");

			byte[] img = IOUtils.toByteArray(io);

			return Base64.getEncoder().encodeToString(img);
		}
	}

	public void setImagemFoto(String imagemFoto)
	{
		this.imagemFoto = imagemFoto;
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

	public Endereco getEndereco()
	{
		if (endereco == null)
		{
			endereco = new Endereco();
		}

		return endereco;
	}

	public void setEndereco(Endereco endereco)
	{
		this.endereco = endereco;
	}

	public String getCelular()
	{
		return celular;
	}

	public void setCelular(String celular)
	{
		this.celular = celular;
	}

	public String getTelefone()
	{
		return telefone;
	}

	public void setTelefone(String telefone)
	{
		this.telefone = telefone;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public TransporteEscolar getTransporteEscolar()
	{
		if (transporteEscolar == null)
		{
			transporteEscolar = new TransporteEscolar();
		}

		return transporteEscolar;
	}

	public void setTransporteEscolar(TransporteEscolar transporteEscolar)
	{
		this.transporteEscolar = transporteEscolar;
	}

	public boolean isAtualizado()
	{
		return isAtualizado;
	}

	public void setAtualizado(boolean isAtualizado)
	{
		this.isAtualizado = isAtualizado;
	}

	public Usuario getResponsavelPorAtualizar()
	{
		return responsavelPorAtualizar;
	}

	public void setResponsavelPorAtualizar(Usuario responsavelPorAtualizar)
	{
		this.responsavelPorAtualizar = responsavelPorAtualizar;
	}

	public Date getDataAtualizacaoCadastral()
	{
		return dataAtualizacaoCadastral;
	}

	public void setDataAtualizacaoCadastral(Date dataAtualizacaoCadastral)
	{
		this.dataAtualizacaoCadastral = dataAtualizacaoCadastral;
	}

	public String getRegistroConselhoClasse()
	{
		return registroConselhoClasse;
	}

	public void setRegistroConselhoClasse(String registroConselhoClasse)
	{
		this.registroConselhoClasse = registroConselhoClasse;
	}

	public EmissorRcc getOrgaoEmissorRcc()
	{
		return orgaoEmissorRcc;
	}

	public void setOrgaoEmissorRcc(EmissorRcc orgaoEmissorRcc)
	{
		this.orgaoEmissorRcc = orgaoEmissorRcc;
	}

	public Estado getUfRcc()
	{
		return ufRcc;
	}

	public void setUfRcc(Estado ufRcc)
	{
		this.ufRcc = ufRcc;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cidadao other = (Cidadao) obj;
		if (id == null)
		{
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}