package org.coletivoJava.fw.projetos.integracao.implementacao.cucumber.fluxooauthinteracaousuario.etapas;

import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.teste.ApiIntegracaoRestfulimplTest;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.teste.simulacaoComunicacao.EnvelopeRequisicacaoTokenAcesso;
import org.coletivoJava.fw.projetos.integracao.api.cucumber.fluxooauthinteracaousuario.FluxoOauth2UsuarioAcessoRestfull;
import org.coletivoJava.fw.projetos.integracao.api.cucumber.fluxooauthinteracaousuario.EtapasFluxoOauthInteracaoUsuario;
import cucumber.api.java.pt.Entao;
import java.lang.UnsupportedOperationException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Entao__o_aplicativo_do_cliente_solicita_o_token_usando_codigo_de_concessao_via_post {

    @Entao(EtapasFluxoOauthInteracaoUsuario.ENTAO_O_APLICATIVO_DO_CLIENTE_SOLICITA_O_TOKEN_USANDO_CODIGO_DE_CONCESSAO_VIA_POST)
    public void implementacaoEtapa() {
        System.out.println("_________________________________________________________");
        System.out.println("--" + FluxoOauth2UsuarioAcessoRestfull.sistemaCliente.getNome() + " solicita o token usando o código de concessao com um post no endereço " + FluxoOauth2UsuarioAcessoRestfull.URL_ENTREGA_CODIGO_CONCESSAO_TOKEN + " (C)-- Código de concessao -->|");
        System.out.println("_________________________________________________________");
        String hashChavePublicaSistemaServidor = FluxoOauth2UsuarioAcessoRestfull.sistemaServidorRecursos.getHashChavePublica();
        EnvelopeRequisicacaoTokenAcesso clienteRegistroCodigo;

        System.out.println("_________________________________________________________");
        System.out.println("+ \"   3 |        |--(C)-- Código de concessao é recebido por " + FluxoOauth2UsuarioAcessoRestfull.sistemaCliente.getNome() + " e enviado com post para -->| Authorization |\\n\"");
        System.out.println("_________________________________________________________");

        try {
            clienteRegistroCodigo = new EnvelopeRequisicacaoTokenAcesso(FluxoOauth2UsuarioAcessoRestfull.codigoConcessaoToken,
                    FluxoOauth2UsuarioAcessoRestfull.URL_ENTREGA_CODIGO_CONCESSAO_TOKEN, hashChavePublicaSistemaServidor);
            System.out.println("_________________________________________________________");
            System.out.println("|<-(D)----- " + FluxoOauth2UsuarioAcessoRestfull.sistemaServidorRecursos.getNome() + "entrega o  Token de acesso en resposta ao post contendo o código de solicitação correto  -----|               |");
            System.out.println("_________________________________________________________");
            String resposta = clienteRegistroCodigo.getRespostaGet(FluxoOauth2UsuarioAcessoRestfull.servletRecepcaoOauth);

        } catch (MalformedURLException ex) {
            Logger.getLogger(ApiIntegracaoRestfulimplTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new UnsupportedOperationException("Falha simulando comunicação para obtenção de token");
        }
    }
}
