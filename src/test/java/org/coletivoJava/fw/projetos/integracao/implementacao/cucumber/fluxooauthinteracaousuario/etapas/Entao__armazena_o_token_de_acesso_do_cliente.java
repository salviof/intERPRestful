package org.coletivoJava.fw.projetos.integracao.implementacao.cucumber.fluxooauthinteracaousuario.etapas;

import org.coletivoJava.fw.projetos.integracao.api.cucumber.fluxooauthinteracaousuario.FluxoOauth2UsuarioAcessoRestfull;
import br.org.coletivoJava.integracoes.restInterprestfull.api.FabIntApiRestIntegracaoERPRestfull;
import br.org.coletivoJava.integracoes.restInterprestfull.implementacao.GestaoTokenRestInterprestfull;
import com.super_bits.modulosSB.webPaginas.controller.servlets.servletRecepcaoOauth.ServletRecepcaoOauth;
import org.coletivoJava.fw.projetos.integracao.api.cucumber.fluxooauthinteracaousuario.EtapasFluxoOauthInteracaoUsuario;
import cucumber.api.java.pt.Entao;
import org.junit.Assert;

public class Entao__armazena_o_token_de_acesso_do_cliente {

    @Entao(EtapasFluxoOauthInteracaoUsuario.E_ARMAZENA_O_TOKEN_DE_ACESSO_DO_CLIENTE)
    public void implementacaoEtapa() {
        GestaoTokenRestInterprestfull gestaoResful = (GestaoTokenRestInterprestfull) FabIntApiRestIntegracaoERPRestfull.OAUTH_VALIDAR_CREDENCIAL
                .getGestaoToken(FluxoOauth2UsuarioAcessoRestfull.sistemaServidorRecursos);
        Assert.assertTrue("Token de acesso não encontrado adicione um breakpoint no Servlet " + ServletRecepcaoOauth.class.getSimpleName(), gestaoResful.isPossuiTokenValido());
    }
}
