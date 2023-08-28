package org.coletivoJava.fw.projetos.integracao.implementacao.cucumber.fluxooauthinteracaosistema.etapas;

import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.UtilSBRestful;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.parametros.ParametroListaRestful;
import br.org.coletivoJava.integracoes.restInterprestfull.api.FabIntApiRestIntegracaoERPRestfull;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.ItfRespostaWebServiceSimples;
import com.super_bits.modulosSB.SBCore.modulos.erp.SolicitacaoControllerERP;
import org.coletivoJava.fw.projetos.integracao.api.cucumber.fluxooauthinteracaosistema.EtapasFluxoOauthInteracaoSistema;
import cucumber.api.java.pt.Quando;
import org.coletivoJava.fw.projetos.integracao.api.cucumber.fluxooauthinteracaosistema.FluxoOauth2SistemaAcessoRestfull;

public class Quando__o_sistema_solicita_a_listagem_de_usuario_com_um_token_valido {

    @Quando(EtapasFluxoOauthInteracaoSistema.QUANDO_O_SISTEMA_SOLICITA_A_LISTAGEM_DE_USUARIO_COM_UM_TOKEN_VALIDO)
    public void implementacaoEtapa() {
        ParametroListaRestful parametroListagem = new ParametroListaRestful();
        parametroListagem.setPagina(0);
        SolicitacaoControllerERP solicitacao = UtilSBRestful.getSolicitacaoAcaoListagemDeEntidade(FluxoOauth2SistemaAcessoRestfull.sistemaCliente, FluxoOauth2SistemaAcessoRestfull.sistemaServidorRecursos,
                "FabAcaoRestfullTestes.USUARIO_RESTFUL_FRM_LISTAR",
                parametroListagem);
        ItfRespostaWebServiceSimples resp = FabIntApiRestIntegracaoERPRestfull.ACOES_GET_LISTA_ENTIDADES
                .getAcao(solicitacao).getResposta();
        System.out.println(resp.getRespostaTexto());

    }
}
