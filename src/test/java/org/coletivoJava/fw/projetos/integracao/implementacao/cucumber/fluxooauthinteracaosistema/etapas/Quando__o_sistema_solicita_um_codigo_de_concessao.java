package org.coletivoJava.fw.projetos.integracao.implementacao.cucumber.fluxooauthinteracaosistema.etapas;

import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.teste.simulacaoComunicacao.EnvelopeServeletSolicitarCodigoAcessoAoToken;
import br.org.coletivoJava.integracoes.restInterprestfull.api.FabIntApiRestIntegracaoERPRestfull;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token.ItfTokenGestao;
import org.coletivoJava.fw.projetos.integracao.api.cucumber.fluxooauthinteracaosistema.EtapasFluxoOauthInteracaoSistema;
import cucumber.api.java.pt.Quando;
import java.lang.UnsupportedOperationException;
import org.coletivoJava.fw.projetos.integracao.api.cucumber.fluxooauthinteracaosistema.FluxoOauth2SistemaAcessoRestfull;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Quando__o_sistema_solicita_um_codigo_de_concessao {

    @Quando(EtapasFluxoOauthInteracaoSistema.QUANDO_O_SISTEMA_SOLICITA_UM_CODIGO_DE_CONCESSAO)
    public void implementacaoEtapa() {
        System.out.println("O usuário não possui um token de acesso");
        ItfTokenGestao gestao = FabIntApiRestIntegracaoERPRestfull.OAUTH_VALIDAR_CREDENCIAL.getGestaoToken(FluxoOauth2SistemaAcessoRestfull.sistemaRemoto);
        assertFalse(gestao.isTemTokemAtivo());

        System.out.println("O usuário acessa a url para obter o código de solicitação,"
                + " e uma vez que já está logado, recebe um comando de redirecionamento para o crm contendo o codigo de acesso");

        SBCore.getServicoSessao().logarEmailESenha(FluxoOauth2SistemaAcessoRestfull.EMAIL_USUARIO_AUTENTICADO, "123456");
        assertTrue("Usuário não logrou êxito ao efetuar login", SBCore.getServicoSessao().getSessaoAtual().isIdentificado());
        FluxoOauth2SistemaAcessoRestfull.envelopeSolicitacaoCodigoDeAcesso = new EnvelopeServeletSolicitarCodigoAcessoAoToken(
                FluxoOauth2SistemaAcessoRestfull.sistemaRemoto, FluxoOauth2SistemaAcessoRestfull.sistemaCliente, FluxoOauth2SistemaAcessoRestfull.EMAIL_USUARIO_AUTENTICADO);
        System.out.println("O código de acesso será soclicitado pela url:");
        System.out.println(FluxoOauth2SistemaAcessoRestfull.envelopeSolicitacaoCodigoDeAcesso.getUrlRequisicao());
    }
}
