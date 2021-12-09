package br.org.coletivoJava.integracoes.restIntgalaxpay.implementacao;

import br.org.coletivoJava.integracoes.restIntgalaxpay.api.InfoIntegracaoRestIntgalaxpaySazonal;
import br.org.coletivoJava.integracoes.intGalaxPay.api.FabApiRestIntGalaxPayCobrancaSazonal;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.AcaoApiIntegracaoAbstrato;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfUsuario;

@InfoIntegracaoRestIntgalaxpaySazonal(tipo = FabApiRestIntGalaxPayCobrancaSazonal.COBRANCAS_SAZONAIS_DO_CLIENTE)
public class IntegracaoRestIntgalaxpayCobrancasSazonaisDoCliente
		extends
			AcaoApiIntegracaoAbstrato {

	public IntegracaoRestIntgalaxpayCobrancasSazonaisDoCliente(
			final FabTipoAgenteClienteApi pTipoAgente,
			final ItfUsuario pUsuario, final java.lang.Object... pParametro) {
		super(
				FabApiRestIntGalaxPayCobrancaSazonal.COBRANCAS_SAZONAIS_DO_CLIENTE,
				pTipoAgente, pUsuario, pParametro);
	}
}