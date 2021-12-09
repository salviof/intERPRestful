package br.org.coletivoJava.fw.erp.implementacao.contapagarreceber.json_bind_galax_pay.PrevisaoValorMoeda;

import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.erp.dto.ItfDTOSBJSON;
import br.org.coletivoJava.fw.api.erp.contaPagarReceber.model.valormoedaFuturo.ItfPrevisaoValorMoeda;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import br.org.coletivoJava.fw.erp.implementacao.contapagarreceber.json_bind_galax_pay.PrevisaoValorMoeda.JsonBindDTOPrevisaoValorMoeda;
import java.lang.String;
import java.util.Date;

@JsonDeserialize(using = JsonBindDTOPrevisaoValorMoeda.class)
public interface ItfDTOPrevisaoValorMoeda
		extends
			ItfDTOSBJSON,
			ItfPrevisaoValorMoeda {

	@Override
	public default double getValor() {
		return (double) getValorPorReflexao();
	}

	@Override
	public default String getPix() {
		return (String) getValorPorReflexao();
	}

	@Override
	public default String getPDFCobranca() {
		return (String) getValorPorReflexao();
	}

	@Override
	public default double getValorRealizado() {
		return (double) getValorPorReflexao();
	}

	@Override
	public default boolean isPagamentoEfetuado() {
		return (boolean) getValorPorReflexao();
	}

	@Override
	public default boolean isUmValorPersonalizado() {
		return (boolean) getValorPorReflexao();
	}

	@Override
	public default double getParcelaNumero() {
		return (double) getValorPorReflexao();
	}

	@Override
	public default Date getDataPrevista() {
		return (Date) getValorPorReflexao();
	}

	@Override
	public default String getNomeUnicoSlug() {
		return (String) getValorPorReflexao();
	}

	@Override
	public default boolean isTemImagemPequenaAdicionada() {
		return (boolean) getValorPorReflexao();
	}

	@Override
	public default String getSlugIdentificador() {
		return (String) getValorPorReflexao();
	}

	@Override
	public default String getNome() {
		return (String) getValorPorReflexao();
	}

	@Override
	public default String getIconeDaClasse() {
		return (String) getValorPorReflexao();
	}

	@Override
	public default String getNomeCurto() {
		return (String) getValorPorReflexao();
	}

	@Override
	public default int getId() {
		return (int) getValorPorReflexao();
	}
}