package org.coletivoJava.fw.projetos.integracao.implementacao.cucumber.fluxooauthinteracaousuario.etapas;

import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.UtilSBRestful;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.teste.servicoTeste.UtilTesteServicoRestfull;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.teste.simulacaoComunicacao.acoes.FabAcaoRestfullTestes;
import org.coletivoJava.fw.projetos.integracao.api.cucumber.fluxooauthinteracaousuario.FluxoOauth2UsuarioAcessoRestfull;
import br.org.coletivoJava.integracoes.restInterprestfull.api.FabIntApiRestIntegracaoERPRestfull;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.ItfRespostaWebServiceSimples;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.transmissao_recepcao_rest_client.ItfAcaoApiRest;
import org.coletivoJava.fw.projetos.integracao.api.cucumber.fluxooauthinteracaousuario.EtapasFluxoOauthInteracaoUsuario;
import cucumber.api.java.pt.Quando;
import org.junit.Assert;

public class Quando__o_cliente_possuidor_de_token_tenta_acessar_um_recurso_do_servidor {

    @Quando(EtapasFluxoOauthInteracaoUsuario.QUANDO_O_CLIENTE_POSSUIDOR_DE_TOKEN_TENTA_ACESSAR_UM_RECURSO_DO_SERVIDOR)
    public void implementacaoEtapa() {

        UtilTesteServicoRestfull.iniciarServico();
        String nomeUnicoAcao = FabAcaoRestfullTestes.USUARIO_RESTFUL_MB_GESTAO.getNomeUnico();
        ItfAcaoApiRest acaoOpcoes = FabIntApiRestIntegracaoERPRestfull.ACOES_GET_OPCOES
                .getAcao(UtilSBRestful.getSolicitacaoOption(FluxoOauth2UsuarioAcessoRestfull.sistemaServidorRecursos, FluxoOauth2UsuarioAcessoRestfull.sistemaCliente,
                        nomeUnicoAcao));
        ItfRespostaWebServiceSimples respOpcoes = acaoOpcoes.getResposta();
        Assert.assertTrue("Falha obtendo opções" + respOpcoes.getRespostaTexto(), respOpcoes.isSucesso());

    }
}
