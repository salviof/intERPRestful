package br.org.coletivoJava.fw.erp.implementacao.contapagarreceber.json_bind_galax_pay.FaturaAssinatura;

import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.erp.dto.ItfDTOSBJSON;
import br.org.coletivoJava.fw.api.erp.contaPagarReceber.model.assinatura.ItfFaturaAssinatura;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import br.org.coletivoJava.fw.erp.implementacao.contapagarreceber.json_bind_galax_pay.FaturaAssinatura.JsonBindDTOFaturaAssinatura;
import java.util.List;
import br.org.coletivoJava.fw.api.erp.contaPagarReceber.model.formaPagamento.ItfFormaPagamento;
import java.util.Date;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.financeiro.ItfPessoaFisicoJuridico;
import java.lang.String;

@JsonDeserialize(using = JsonBindDTOFaturaAssinatura.class)
public interface ItfDTOFaturaAssinatura
		extends
			ItfDTOSBJSON,
			ItfFaturaAssinatura {

	@Override
	public default boolean isAtivo() {
		return (boolean) getValorPorReflexao();
	}

	@Override
	public default double getValorAtualMensal() {
		return (double) getValorPorReflexao();
	}

	@Override
	public default List getParcelas() {
		return (List) getValorPorReflexao();
	}

	@Override
	public default double getValorTotal() {
		return (double) getValorPorReflexao();
	}

	@Override
	public default ItfFormaPagamento getFormaPagamento() {
		return (ItfFormaPagamento) getValorPorReflexao();
	}

	@Override
	public default int getDiaDoMesVencimento() {
		return (int) getValorPorReflexao();
	}

	@Override
	public default Date getDataPrimeiroPagamento() {
		return (Date) getValorPorReflexao();
	}

	@Override
	public default int getQtdParcelas() {
		return (int) getValorPorReflexao();
	}

	@Override
	public default ItfPessoaFisicoJuridico getDevedor() {
		return (ItfPessoaFisicoJuridico) getValorPorReflexao();
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