package org.coletivoJava.fw.projetos.integracao.implementacao.cucumber.fluxooauthinteracaosistema.etapas;

import br.org.coletivoJava.fw.api.erp.erpintegracao.contextos.ERPIntegracaoSistemasApi;
import br.org.coletivoJava.fw.api.erp.erpintegracao.servico.ItfIntegracaoERP;
import br.org.coletivoJava.integracoes.restInterprestfull.api.FabIntApiRestIntegracaoERPRestfull;
import br.org.coletivoJava.integracoes.restInterprestfull.implementacao.GestaoTokenRestInterprestfull;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.modulos.erp.ItfSistemaERP;
import org.coletivoJava.fw.projetos.integracao.api.cucumber.fluxooauthinteracaosistema.EtapasFluxoOauthInteracaoSistema;
import cucumber.api.java.pt.Dado;
import org.coletivoJava.fw.projetos.integracao.api.cucumber.fluxooauthinteracaosistema.FluxoOauth2SistemaAcessoRestfull;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class Dado__um_usuario_logado_no_sitema_com_chave_de_acesso_configuradas_entre_cliente_e_servidor {

    @Dado(EtapasFluxoOauthInteracaoSistema.DADO_UM_USUARIO_LOGADO_NO_SITEMA_COM_CHAVE_DE_ACESSO_CONFIGURADAS_ENTRE_CLIENTE_E_SERVIDOR)
    public void implementacaoEtapa() {

        FluxoOauth2SistemaAcessoRestfull.renovarConexaoEntityManagerEscopoTeste();

        ItfIntegracaoERP erp = ERPIntegracaoSistemasApi.RESTFUL.getImplementacaoDoContexto();
        // sistemaCliente = (SistemaERPAtual) erp.getSistemaAtual();
        // SBCore.getServicoSessao().logarEmailESenha("salviof@gmail.com", "123456");

        assertTrue("Sistema remoto crm.casanovadigital não foi encontrado", FluxoOauth2SistemaAcessoRestfull.sistemaServidorRecursos.getDominio().contains("crm.casanovadigital.com.b"));

        ItfSistemaERP sisRemoto = FluxoOauth2SistemaAcessoRestfull.sistemaServidorRecursos;
        assertNotNull("O sistema local não foi registrado no sistema", sisRemoto);

        GestaoTokenRestInterprestfull gestaoResful = (GestaoTokenRestInterprestfull) FabIntApiRestIntegracaoERPRestfull.OAUTH_VALIDAR_CREDENCIAL
                .getGestaoToken(sisRemoto);

        SBCore.getServicoSessao().logarEmailESenha(FluxoOauth2SistemaAcessoRestfull.EMAIL_USUARIO_AUTENTICADO, "123456");
        assertTrue("Usuário não logrou êxito ao efetuar login", SBCore.getServicoSessao().getSessaoAtual().isIdentificado());
    }

    public static String extrairCodigoDeAcessoPelaUrl(String pUrl) {

        int indiceConexao = pUrl.indexOf("?code=");

        String respostaCodigo = pUrl.substring(indiceConexao);
        String codigoSolicitacao = respostaCodigo.substring("?code=".length(), respostaCodigo.indexOf("&"));
        return codigoSolicitacao;
    }
}
