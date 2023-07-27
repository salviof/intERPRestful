package org.coletivoJava.fw.projetos.integracao.implementacao.cucumber.fluxooauthinteracaousuario.etapas;

import com.super_bits.modulosSB.Persistencia.ConfigGeral.DevOpsPersistencia;
import org.coletivoJava.fw.projetos.integracao.api.cucumber.fluxooauthinteracaousuario.FluxoOauth2UsuarioAcessoRestfull;
import org.coletivoJava.fw.projetos.integracao.api.cucumber.fluxooauthinteracaousuario.EtapasFluxoOauthInteracaoUsuario;
import cucumber.api.java.pt.Entao;
import static org.junit.Assert.assertTrue;

public class Entao__o_servidor_valida_as_chaves_de_acesso_assincronas_e_reconhece_o_nome_de_usuario {

    @Entao(EtapasFluxoOauthInteracaoUsuario.ENTAO_O_SERVIDOR_VALIDA_AS_CHAVES_DE_ACESSO_ASSINCRONAS_E_RECONHECE_O_NOME_DE_USUARIO)
    public void implementacaoEtapa() {
        String respostaServidorOauthPosLogin = FluxoOauth2UsuarioAcessoRestfull.envelopeSolicitacaoCodigoDeAcesso.getRespostaGet(FluxoOauth2UsuarioAcessoRestfull.servletCodConcessaoTokenService);
        System.out.println("  2 |        |<- " + FluxoOauth2UsuarioAcessoRestfull.sistemaServidorRecursos.getNome() + " (B)-- retorna o Codigo de concessão: ---|               |\n");
        System.out.println(respostaServidorOauthPosLogin);
        assertTrue("O código de concessao não foi criado, falha na autenticação das chaves ou usuário invalido", respostaServidorOauthPosLogin.contains("?code="));
        FluxoOauth2UsuarioAcessoRestfull.respostaServidorOauthObtencaoCodigoDeAcesso = respostaServidorOauthPosLogin;

    }
}
