/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.integracoes.restIntgalaxpay.implementacao;

import br.org.coletivoJava.integracoes.intGalaxPay.api.ConfiguradorCoreApiGalaxPay;
import br.org.coletivoJava.integracoes.intGalaxPay.api.FabApiRestIntGalaxPayAssinatura;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.RespostaWebServiceSimples;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.transmissao_recepcao_rest_client.ItfAcaoApiRest;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sfurbino
 */
public class IntegracaoRestIntgalaxpayAssinaturasDoClienteTest {

    public IntegracaoRestIntgalaxpayAssinaturasDoClienteTest() {
    }

    @Test
    public void testSomeMethod() {
        // TODO review the generated test code and remove the default call to fail.
        SBCore.configurar(new ConfiguradorCoreApiGalaxPay(), SBCore.ESTADO_APP.DESENVOLVIMENTO);

        FabApiRestIntGalaxPayAssinatura.ASSINATURAS_DO_CLIENTE.getGestaoToken();
        ItfAcaoApiRest acao = FabApiRestIntGalaxPayAssinatura.ASSINATURAS_DO_CLIENTE.getAcao("5");
        RespostaWebServiceSimples resposta = acao.getResposta();
        assertTrue("Falha de comuniucação com a api", resposta.isSucesso());
        if (resposta.isSucesso()) {
            System.out.println(resposta.getRespostaComoObjetoJson().toJSONString());
        }

    }

}
