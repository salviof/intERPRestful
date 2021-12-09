package br.org.coletivoJava.integracoes.restIntgalaxpay.implementacao;

import br.org.coletivoJava.integracoes.restIntgalaxpay.api.InfoIntegracaoRestIntgalaxpayCliente;
import br.org.coletivoJava.integracoes.intGalaxPay.api.FabApiRestIntGalaxPayCliente;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.AcaoApiIntegracaoAbstrato;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfUsuario;

@InfoIntegracaoRestIntgalaxpayCliente(tipo = FabApiRestIntGalaxPayCliente.LISTAR_CLIENTE_BY_DOCUMENTO)
public class IntegracaoRestIntgalaxpayListarClienteByDocumento
        extends
        AcaoApiIntegracaoAbstrato {

    public IntegracaoRestIntgalaxpayListarClienteByDocumento(
            final FabTipoAgenteClienteApi pTipoAgente,
            final ItfUsuario pUsuario, final java.lang.Object... pParametro) {
        super(FabApiRestIntGalaxPayCliente.LISTAR_CLIENTE_BY_DOCUMENTO,
                pTipoAgente, pUsuario, pParametro);
    }
}
