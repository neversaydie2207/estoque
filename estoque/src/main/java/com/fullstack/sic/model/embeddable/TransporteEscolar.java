package com.fullstack.sic.model.embeddable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import com.fullstack.sic.model.enums.PoderPublico;
import com.fullstack.sic.model.enums.TipoVeiculo;

@Embeddable
public class TransporteEscolar
{

	@Column(name = "utiliza_transporte")
	@NotNull(message = "Campo UTILIZA TRANSPORTE PÚBLICO é obrigatório")
	private boolean isUtilizaTransporteEscolar = false;

	@Enumerated(EnumType.STRING)
	@NotNull(message = "Campo PODER PÚBLICO é obrigatório")
	@Column(name = "responsabilidade_te")
	private PoderPublico responsabilidadeTransporteEscolar;

	@Enumerated(EnumType.STRING)
	@NotNull(message = "Campo TIPO VEÍCULO é obrigatório")
	@Column(name = "tipo_veiculo_te")
	private TipoVeiculo tipoVeiculoTrasporteEscolar;

	public boolean isUtilizaTransporteEscolar()
	{
		return isUtilizaTransporteEscolar;
	}

	public void setUtilizaTransporteEscolar(boolean isUtilizaTransporteEscolar)
	{
		this.isUtilizaTransporteEscolar = isUtilizaTransporteEscolar;
	}

	public PoderPublico getResponsabilidadeTransporteEscolar()
	{
		return responsabilidadeTransporteEscolar;
	}

	public void setResponsabilidadeTransporteEscolar(PoderPublico responsabilidadeTransporteEscolar)
	{
		this.responsabilidadeTransporteEscolar = responsabilidadeTransporteEscolar;
	}

	public TipoVeiculo getTipoVeiculoTrasporteEscolar()
	{
		return tipoVeiculoTrasporteEscolar;
	}

	public void setTipoVeiculoTrasporteEscolar(TipoVeiculo tipoVeiculoTrasporteEscolar)
	{
		this.tipoVeiculoTrasporteEscolar = tipoVeiculoTrasporteEscolar;
	}

}