package org.coletivoJava.fw.projetos.integracao.implementacao.cucumber.fluxooauthinteracaosistema.etapas;

import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.teste.servicoTeste.UtilTesteServicoRestfull;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilCRCJson;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilCRCJsonRest;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.ItfResposta;
import org.coletivoJava.fw.projetos.integracao.api.cucumber.fluxooauthinteracaosistema.EtapasFluxoOauthInteracaoSistema;
import cucumber.api.java.pt.Entao;
import java.lang.UnsupportedOperationException;
import org.coletivoJava.fw.projetos.integracao.api.cucumber.fluxooauthinteracaosistema.FluxoOauth2SistemaAcessoRestfull;

import static org.junit.Assert.assertTrue;

public class Entao__o_servidor_valida_as_chaves_de_acesso_assincronas_e_reconhece_o_nome_de_usuario {

    @Entao(EtapasFluxoOauthInteracaoSistema.ENTAO_O_SERVIDOR_VALIDA_AS_CHAVES_DE_ACESSO_ASSINCRONAS_E_RECONHECE_O_NOME_DE_USUARIO)
    public void implementacaoEtapa() {
        // iniciando serviço que vai receber o código de concessao
        UtilTesteServicoRestfull.iniciarServicoRecepcaoCodigo();

        String respostaServidorOauthPosLogin = FluxoOauth2SistemaAcessoRestfull.envelopeSolicitacaoCodigoDeAcesso.getRespostaGet(FluxoOauth2SistemaAcessoRestfull.servletCodConcessaoTokenService);
        System.out.println(respostaServidorOauthPosLogin);
        ItfResposta resp = UtilCRCJsonRest.getResposta(UtilCRCJson.getJsonObjectByTexto(respostaServidorOauthPosLogin));
        FluxoOauth2SistemaAcessoRestfull.repostaRegistroDeToken = resp;
        assertTrue("O código de concessao não foi criado, falha na autenticação das chaves ou usuário invalido", resp.isSucesso());
        FluxoOauth2SistemaAcessoRestfull.respostaServidorOauthObtencaoCodigoDeAcesso = respostaServidorOauthPosLogin;

    }
}
