/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.integracoes.restInterprestfull.api;

import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.FabConfigModuloWebERPChaves;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.SolicitacaoControllerERP;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.ItfFabricaIntegracaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.FabTipoConexaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.InfoConsumoRestService;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.servicoRegistrado.FabTipoAutenticacaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.servicoRegistrado.InfoConfigRestClientIntegracao;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token.ItfTokenGestao;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.transmissao_recepcao_rest_client.ItfAcaoApiRest;
import com.super_bits.modulosSB.SBCore.modulos.ManipulaArquivo.importacao.FabTipoArquivoImportacao;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfUsuario;
import org.coletivojava.fw.api.objetoNativo.controller.sistemaErp.ItfSistemaErp;

/**
 *
 * @author sfurbino
 */
@InfoConfigRestClientIntegracao(enderecosDocumentacao = "https://coletivojava.com.br",
        tipoAutenticacao = FabTipoAutenticacaoRest.OAUTHV2,
        nomeIntegracao = "intERPRestfull",
        configuracao = FabConfigModuloWebERPChaves.class
)
public enum FabIntApiRestIntegracaoERPRestfull implements ItfFabricaIntegracaoRest {

    @InfoConsumoRestService(getPachServico = "/oauth2Service/status/{0}",
            tipoConexao = FabTipoConexaoRest.GET,
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            parametrosGet = {"chavePublica"},
            urlDocumentacao = "https://coletivojava.com.br",
            adicionarAutenticacaoBearer = true)
    OAUTH_VALIDAR_CREDENCIAL,
    @InfoConsumoRestService(getPachServico = "/controllerERP/acao/executar/{0}/{1}",
            tipoConexao = FabTipoConexaoRest.PUT,
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            parametrosGet = {"nomeUnicoAcaoGestao", "codigoEntidade"},
            urlDocumentacao = "https://coletivojava.com.br",
            adicionarAutenticacaoBearer = true)
    ACOES_EXECUTAR_CONTROLLER_ENTIDADE_EXISTENTE,
    @InfoConsumoRestService(getPachServico = "/controllerERP/acao/executar/{0}/",
            tipoConexao = FabTipoConexaoRest.POST,
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            parametrosGet = {"nomeUnicoAcaoGestao"},
            urlDocumentacao = "https://coletivojava.com.br",
            adicionarAutenticacaoBearer = true)
    ACOES_EXECUTAR_CONTROLLER,
    @InfoConsumoRestService(getPachServico = "/controllerERP/acaogestao/",
            tipoConexao = FabTipoConexaoRest.POST,
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            parametrosGet = {},
            urlDocumentacao = "https://coletivojava.com.br",
            adicionarAutenticacaoBearer = true)
    ACOES_EXECUTAR_CRIAR_NOVA_ENTIDADE,
    @InfoConsumoRestService(getPachServico = "/controllerERP/acaogestao/{0}",
            tipoConexao = FabTipoConexaoRest.PUT,
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            parametrosGet = {"idEntidade"},
            urlDocumentacao = "https://coletivojava.com.br",
            adicionarAutenticacaoBearer = true)
    ACOES_EXECUTAR_ATUALIZAR_ENTIDADE,
    @InfoConsumoRestService(getPachServico = "/controllerERP/acaogestao/{0}",
            tipoConexao = FabTipoConexaoRest.DELET,
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            parametrosGet = {"identidade"},
            urlDocumentacao = "https://coletivojava.com.br",
            adicionarAutenticacaoBearer = true)
    ACOES_EXECUTAR_DELETE,
    @InfoConsumoRestService(getPachServico = "/controllerERP/acaogestao/",
            tipoConexao = FabTipoConexaoRest.OPTIONS,
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            urlDocumentacao = "https://coletivojava.com.br",
            adicionarAutenticacaoBearer = true)
    ACOES_GET_OPCOES,
    ACOES_GET_ESTRUTURA_CAMPOS_FORMULARIO,
    ACOES_GET_LISTA_ENTIDADES,
    ACOES_GET_REGISTRO_ENTIDADE;

    public ItfTokenGestao getGestaoToken(SolicitacaoControllerERP pSistema) {
        return ItfFabricaIntegracaoRest.super.getGestaoToken(SBCore.getUsuarioLogado(),
                pSistema.getErpServico().getHashChavePublica());
    }

    public ItfAcaoApiRest getAcao(SolicitacaoControllerERP pSolicicatacao) {
        return ItfFabricaIntegracaoRest.super.getAcao(SBCore.getUsuarioLogado(), pSolicicatacao, pSolicicatacao.getErpServico().getHashChavePublica());
    }

    public ItfTokenGestao getGestaoToken(ItfSistemaErp pIdentificadorApi) {
        return ItfFabricaIntegracaoRest.super.getGestaoToken(SBCore.getUsuarioLogado(), pIdentificadorApi.getHashChavePublica());
    }

    @Override
    public ItfTokenGestao getGestaoToken(ItfUsuario pUsuario) {
        throw new UnsupportedOperationException("Informe o sistema chamando get GestaoDeToken(Sistema)");
    }

}
