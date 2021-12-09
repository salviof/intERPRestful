package br.org.coletivoJava.integracoes.restIntgalaxpay.implementacao;

import br.org.coletivoJava.integracoes.restIntgalaxpay.api.InfoIntegracaoRestIntgalaxpayAssinatura;
import br.org.coletivoJava.integracoes.intGalaxPay.api.FabApiRestIntGalaxPayAssinatura;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.AcaoApiIntegracaoAbstrato;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfUsuario;

@InfoIntegracaoRestIntgalaxpayAssinatura(tipo = FabApiRestIntGalaxPayAssinatura.ASSINATURAS_DO_CLIENTE)
public class IntegracaoRestIntgalaxpayAssinaturasDoCliente
        extends
        AcaoApiIntegracaoAbstrato {

    public IntegracaoRestIntgalaxpayAssinaturasDoCliente(
            final FabTipoAgenteClienteApi pTipoAgente,
            final ItfUsuario pUsuario, final java.lang.Object... pParametro) {
        super(FabApiRestIntGalaxPayAssinatura.ASSINATURAS_DO_CLIENTE,
                pTipoAgente, pUsuario, pParametro);
    }

    @Override
    protected void executarAcao() {
        super.executarAcao(); //To change body of generated methods, choose Tools | Templates.
    }

}
