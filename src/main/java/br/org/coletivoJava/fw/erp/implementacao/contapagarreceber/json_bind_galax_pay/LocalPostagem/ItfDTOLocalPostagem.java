package br.org.coletivoJava.fw.erp.implementacao.contapagarreceber.json_bind_galax_pay.LocalPostagem;

import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.erp.dto.ItfDTOSBJSON;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.cep.ItfLocalPostagem;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import br.org.coletivoJava.fw.erp.implementacao.contapagarreceber.json_bind_galax_pay.LocalPostagem.JsonBindDTOLocalPostagem;
import java.lang.String;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.cep.ItfBairro;

@JsonDeserialize(using = JsonBindDTOLocalPostagem.class)
public interface ItfDTOLocalPostagem extends ItfDTOSBJSON, ItfLocalPostagem {

	@Override
	public default String getCep() {
		return (String) getValorPorReflexao();
	}

	@Override
	public default String getLogradouro() {
		return (String) getValorPorReflexao();
	}

	@Override
	public default String getComplemento() {
		return (String) getValorPorReflexao();
	}

	@Override
	public default long getLatitude() {
		return (long) getValorPorReflexao();
	}

	@Override
	public default ItfBairro getBairro() {
		return (ItfBairro) getValorPorReflexao();
	}

	@Override
	public default ItfLocalPostagem getComoLocalPostavel() {
		return (ItfLocalPostagem) getValorPorReflexao();
	}

	@Override
	public default boolean isLocaPostavel() {
		return (boolean) getValorPorReflexao();
	}

	@Override
	public default long getLongitude() {
		return (long) getValorPorReflexao();
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