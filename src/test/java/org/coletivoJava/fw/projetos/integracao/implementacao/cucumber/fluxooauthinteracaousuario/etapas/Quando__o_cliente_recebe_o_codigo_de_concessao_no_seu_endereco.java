package org.coletivoJava.fw.projetos.integracao.implementacao.cucumber.fluxooauthinteracaousuario.etapas;

import org.coletivoJava.fw.projetos.integracao.api.cucumber.fluxooauthinteracaousuario.FluxoOauth2UsuarioAcessoRestfull;
import org.coletivoJava.fw.projetos.integracao.api.cucumber.fluxooauthinteracaousuario.EtapasFluxoOauthInteracaoUsuario;
import cucumber.api.java.pt.Quando;
import static org.junit.Assert.assertTrue;

public class Quando__o_cliente_recebe_o_codigo_de_concessao_no_seu_endereco {

    @Quando(EtapasFluxoOauthInteracaoUsuario.QUANDO_O_CLIENTE_RECEBE_O_CODIGO_DE_CONCESSAO_NO_SEU_ENDERECO)
    public void implementacaoEtapa() {

        String respostaServidorOauthPosLogin = FluxoOauth2UsuarioAcessoRestfull.envelopeSolicitacaoCodigoDeAcesso.getRespostaGet(FluxoOauth2UsuarioAcessoRestfull.servletCodConcessaoTokenService);
        System.out.println(respostaServidorOauthPosLogin);
        assertTrue("O token de acesso não foi criado falha recebendo código de concessão no cliente", respostaServidorOauthPosLogin.contains("?code="));

        System.out.println("_________________________________________________________");
        System.out.println("+ \"   4 |        |<-(D)-----Token de acesso é recebido e registrado para usufruto do cliente -----|               |\\n\"");
        System.out.println("_________________________________________________________");

        FluxoOauth2UsuarioAcessoRestfull.respostaServidorOauthObtencaoCodigoDeAcesso = respostaServidorOauthPosLogin;

    }
}
