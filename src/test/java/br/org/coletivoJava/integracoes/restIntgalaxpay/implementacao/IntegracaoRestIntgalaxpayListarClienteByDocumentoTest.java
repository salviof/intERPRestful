/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.integracoes.restIntgalaxpay.implementacao;

import br.org.coletivoJava.integracoes.intGalaxPay.api.ConfiguradorCoreApiGalaxPay;
import br.org.coletivoJava.integracoes.intGalaxPay.api.FabApiRestIntGalaxPayCliente;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.RespostaWebServiceSimples;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.transmissao_recepcao_rest_client.ItfAcaoApiRest;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sfurbino
 */
public class IntegracaoRestIntgalaxpayListarClienteByDocumentoTest {

    public IntegracaoRestIntgalaxpayListarClienteByDocumentoTest() {
    }

    @Test
    public void testSomeMethod() {
        SBCore.configurar(new ConfiguradorCoreApiGalaxPay(), SBCore.ESTADO_APP.DESENVOLVIMENTO);

        FabApiRestIntGalaxPayCliente.LISTAR_CLIENTE_BY_DOCUMENTO.getGestaoToken();
        ItfAcaoApiRest acao = FabApiRestIntGalaxPayCliente.LISTAR_CLIENTE_BY_DOCUMENTO.getAcao("58547422170");

        RespostaWebServiceSimples resposta = acao.getResposta();
        System.out.println(resposta.getRespostaErro());
        assertTrue("Falha de comuniucação com a api", resposta.isSucesso());
        if (resposta.isSucesso()) {
            System.out.println(resposta.getRespostaComoObjetoJson().toJSONString());
        }
    }

}
