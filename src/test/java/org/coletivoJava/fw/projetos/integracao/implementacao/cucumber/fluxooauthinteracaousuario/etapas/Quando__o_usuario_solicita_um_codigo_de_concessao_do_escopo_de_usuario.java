package org.coletivoJava.fw.projetos.integracao.implementacao.cucumber.fluxooauthinteracaousuario.etapas;

import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.teste.simulacaoComunicacao.EnvelopeServeletSolicitarCodigoAcessoAoToken;
import org.coletivoJava.fw.projetos.integracao.api.cucumber.fluxooauthinteracaousuario.FluxoOauth2UsuarioAcessoRestfull;
import br.org.coletivoJava.integracoes.restInterprestfull.api.FabIntApiRestIntegracaoERPRestfull;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token.ItfTokenGestao;
import org.coletivoJava.fw.projetos.integracao.api.cucumber.fluxooauthinteracaousuario.EtapasFluxoOauthInteracaoUsuario;
import cucumber.api.java.pt.Quando;
import java.lang.UnsupportedOperationException;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Quando__o_usuario_solicita_um_codigo_de_concessao_do_escopo_de_usuario {

    @Quando(EtapasFluxoOauthInteracaoUsuario.QUANDO_O_USUARIO_SOLICITA_UM_CODIGO_DE_CONCESSAO_DO_ESCOPO_DE_USUARIO)
    public void implementacaoEtapa() {

        ItfTokenGestao gestao = FabIntApiRestIntegracaoERPRestfull.OAUTH_VALIDAR_CREDENCIAL.getGestaoToken(FluxoOauth2UsuarioAcessoRestfull.sistemaServidorRecursos);
        assertFalse(gestao.isTemTokemAtivo());

        System.out.println("O usuário acessa a url para obter o código de solicitação,"
                + " e uma vez que já está logado, recebe um comando de redirecionamento para o crm contendo o codigo de acesso");

        assertTrue("Usuário não logrou êxito ao efetuar login", SBCore.getServicoSessao().getSessaoAtual().isIdentificado());
        FluxoOauth2UsuarioAcessoRestfull.envelopeSolicitacaoCodigoDeAcesso = new EnvelopeServeletSolicitarCodigoAcessoAoToken(
                FluxoOauth2UsuarioAcessoRestfull.sistemaServidorRecursos, FluxoOauth2UsuarioAcessoRestfull.sistemaCliente, FluxoOauth2UsuarioAcessoRestfull.EMAIL_USUARIO_AUTENTICADO);
        System.out.println("- " + FluxoOauth2UsuarioAcessoRestfull.sistemaCliente.getNome() + "(A) Solictará cod. de concessão-> em: " + FluxoOauth2UsuarioAcessoRestfull.envelopeSolicitacaoCodigoDeAcesso.getUrlRequisicao());

    }
}
