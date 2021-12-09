package br.org.coletivoJava.integracoes.restIntgalaxpay.implementacao;

import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.AcaoApiIntegracaoHeaderBuilder;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.transmissao_recepcao_rest_client.ItfAcaoApiRest;

public class IntegracaoRestIntgalaxpay_HeaderPadrao
        extends
        AcaoApiIntegracaoHeaderBuilder {

    public IntegracaoRestIntgalaxpay_HeaderPadrao(final ItfAcaoApiRest pAcao) {
        super(pAcao);
    }

    @Override
    public void buildHeaderPadrao() {
        super.buildHeaderPadrao();
        cabecalho.put("user-agent", "casanovaDigitalServer");

    }

}
