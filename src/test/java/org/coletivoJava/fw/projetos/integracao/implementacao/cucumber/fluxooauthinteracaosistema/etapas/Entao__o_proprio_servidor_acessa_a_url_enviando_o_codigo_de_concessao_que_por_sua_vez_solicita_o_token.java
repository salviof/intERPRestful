package org.coletivoJava.fw.projetos.integracao.implementacao.cucumber.fluxooauthinteracaosistema.etapas;

import br.org.coletivoJava.integracoes.restInterprestfull.api.FabIntApiRestIntegracaoERPRestfull;
import org.coletivoJava.fw.projetos.integracao.api.cucumber.fluxooauthinteracaosistema.EtapasFluxoOauthInteracaoSistema;
import cucumber.api.java.pt.Entao;
import java.lang.UnsupportedOperationException;
import org.coletivoJava.fw.projetos.integracao.api.cucumber.fluxooauthinteracaosistema.FluxoOauth2SistemaAcessoRestfull;
import org.junit.Assert;

public class Entao__o_proprio_servidor_acessa_a_url_enviando_o_codigo_de_concessao_que_por_sua_vez_solicita_o_token {

    @Entao(EtapasFluxoOauthInteracaoSistema.E_O_PROPRIO_SERVIDOR_ACESSA_A_URL_ENVIANDO_O_CODIGO_DE_CONCESSAO_QUE_POR_SUA_VEZ_SOLICITA_O_TOKEN)
    public void implementacaoEtapa() {
        Assert.assertTrue("Token não registrado", FluxoOauth2SistemaAcessoRestfull.repostaRegistroDeToken.isSucesso());
        Assert.assertTrue("Token não registrado", FabIntApiRestIntegracaoERPRestfull.ACOES_EXECUTAR_ATUALIZAR_ENTIDADE.getGestaoToken(FluxoOauth2SistemaAcessoRestfull.sistemaServidorRecursos).isCodigoSolicitacaoRegistrado());
    }
}
