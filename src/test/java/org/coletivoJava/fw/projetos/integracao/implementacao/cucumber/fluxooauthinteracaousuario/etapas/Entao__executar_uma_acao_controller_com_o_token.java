package org.coletivoJava.fw.projetos.integracao.implementacao.cucumber.fluxooauthinteracaousuario.etapas;

import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.UtilSBRestful;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.FabGrupoTestesIntegracao;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.teste.simulacaoComunicacao.acoes.FabAcaoRestfullTestes;
import org.coletivoJava.fw.projetos.integracao.api.cucumber.fluxooauthinteracaousuario.FluxoOauth2UsuarioAcessoRestfull;
import br.org.coletivoJava.integracoes.restInterprestfull.api.FabIntApiRestIntegracaoERPRestfull;
import br.org.coletivoJava.integracoes.restInterprestfull.implementacao.IntegracaoRestInterprestfullAcoesExecutarController;
import com.super_bits.modulos.SBAcessosModel.model.UsuarioSB;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.ItfRespostaWebServiceSimples;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.transmissao_recepcao_rest_client.ItfAcaoApiRest;
import com.super_bits.modulosSB.SBCore.modulos.erp.SolicitacaoControllerERP;
import org.coletivoJava.fw.projetos.integracao.api.cucumber.fluxooauthinteracaousuario.EtapasFluxoOauthInteracaoUsuario;
import cucumber.api.java.pt.Entao;
import org.junit.Assert;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ComoGrupoUsuario;

public class Entao__executar_uma_acao_controller_com_o_token {

    @Entao(EtapasFluxoOauthInteracaoUsuario.E_EXECUTAR_UMA_ACAO_CONTROLLER_COM_O_TOKEN)
    public void implementacaoEtapa() {
        UsuarioSB novoUsuario = new UsuarioSB();
        novoUsuario.setNome("Usuario teste");
        novoUsuario.setEmail("emailTesterestfull@casanovadigital.com.br");
        novoUsuario.setApelido("Usuario teste");
        novoUsuario.setSenha("123");

        novoUsuario.setGrupo((ComoGrupoUsuario) FabGrupoTestesIntegracao.GRUPO_TESTE.getRegistro());

        SolicitacaoControllerERP solicitaca = UtilSBRestful.getSolicitacaoAcaoController(FluxoOauth2UsuarioAcessoRestfull.sistemaCliente, FluxoOauth2UsuarioAcessoRestfull.sistemaServidorRecursos,
                FabAcaoRestfullTestes.USUARIO_RESTFUL_CTR_SALVAR_MERGE.getRegistro().getNomeUnico(),
                novoUsuario, true);

        IntegracaoRestInterprestfullAcoesExecutarController acaoPost = (IntegracaoRestInterprestfullAcoesExecutarController) FabIntApiRestIntegracaoERPRestfull.ACOES_EXECUTAR_CONTROLLER
                .getAcao(solicitaca);

        ItfRespostaWebServiceSimples resposta = acaoPost.getResposta();

        resposta.dispararMensagens();
        System.out.println(resposta.getRespostaTexto());
        Assert.assertTrue("Arquivamento de usuário falhou", resposta.isSucesso());
    }
}
