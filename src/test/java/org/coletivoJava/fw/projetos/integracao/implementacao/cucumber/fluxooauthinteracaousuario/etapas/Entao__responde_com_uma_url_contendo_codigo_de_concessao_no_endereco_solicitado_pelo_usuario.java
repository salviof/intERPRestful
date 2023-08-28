package org.coletivoJava.fw.projetos.integracao.implementacao.cucumber.fluxooauthinteracaousuario.etapas;

import org.coletivoJava.fw.projetos.integracao.api.cucumber.fluxooauthinteracaousuario.FluxoOauth2UsuarioAcessoRestfull;
import br.org.coletivoJava.integracoes.restInterprestfull.api.FabIntApiRestIntegracaoERPRestfull;
import br.org.coletivoJava.integracoes.restInterprestfull.implementacao.GestaoTokenRestInterprestfull;
import com.super_bits.modulosSB.webPaginas.controller.servlets.servletRecepcaoOauth.ServletRecepcaoOauth;
import org.coletivoJava.fw.projetos.integracao.api.cucumber.fluxooauthinteracaousuario.EtapasFluxoOauthInteracaoUsuario;
import cucumber.api.java.pt.Entao;
import java.lang.UnsupportedOperationException;
import java.net.MalformedURLException;
import java.net.URL;
import org.junit.Assert;
import static org.junit.Assert.assertNotNull;

public class Entao__responde_com_uma_url_contendo_codigo_de_concessao_no_endereco_solicitado_pelo_usuario {

    @Entao(EtapasFluxoOauthInteracaoUsuario.E_RESPONDE_COM_UMA_URL_CONTENDO_CODIGO_DE_CONCESSAO_NO_ENDERECO_SOLICITADO_PELO_USUARIO)
    public void implementacaoEtapa() {
        try {
            int indiceConexao = FluxoOauth2UsuarioAcessoRestfull.respostaServidorOauthObtencaoCodigoDeAcesso.indexOf("?code=");
            String respostaCodigo = FluxoOauth2UsuarioAcessoRestfull.respostaServidorOauthObtencaoCodigoDeAcesso.substring(indiceConexao);
            String codigoSolicitacao = respostaCodigo.substring("?code=".length(), respostaCodigo.indexOf("&"));
            assertNotNull("Codigo não encontrado", codigoSolicitacao);
            FluxoOauth2UsuarioAcessoRestfull.codigoConcessaoToken = codigoSolicitacao;
        } catch (Throwable t) {
            Assert.fail("código de concessão de token não foi encontrado na resposta");
        }

        String urlClienteRecepcaoCodigoParaSolicitarToken = FluxoOauth2UsuarioAcessoRestfull.respostaServidorOauthObtencaoCodigoDeAcesso.substring(
                FluxoOauth2UsuarioAcessoRestfull.respostaServidorOauthObtencaoCodigoDeAcesso.indexOf("windows.location='") + "windows.location='".length(),
                FluxoOauth2UsuarioAcessoRestfull.respostaServidorOauthObtencaoCodigoDeAcesso.indexOf("'</script>"));
        FluxoOauth2UsuarioAcessoRestfull.URL_ENTREGA_CODIGO_CONCESSAO_TOKEN = urlClienteRecepcaoCodigoParaSolicitarToken;
        try {
            new URL(urlClienteRecepcaoCodigoParaSolicitarToken);
        } catch (MalformedURLException ex) {
            Assert.fail("formato de url inválido");
        }
        //Verificar código de acesso
        GestaoTokenRestInterprestfull gestaoResful = (GestaoTokenRestInterprestfull) FabIntApiRestIntegracaoERPRestfull.OAUTH_VALIDAR_CREDENCIAL
                .getGestaoToken(FluxoOauth2UsuarioAcessoRestfull.sistemaServidorRecursos);
        gestaoResful.isCodigoSolicitacaoRegistrado();

        try {
            new URL(FluxoOauth2UsuarioAcessoRestfull.URL_ENTREGA_CODIGO_CONCESSAO_TOKEN);
        } catch (MalformedURLException ex) {
            Assert.fail("Url entrega código convessão token não encontrada");
        }
        System.out.println("Simulando recepção  via " + ServletRecepcaoOauth.class.getSimpleName() + " ");

    }
}
