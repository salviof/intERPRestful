/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.integracoes.restInterprestfull.api;

import br.org.coletivoJava.fw.api.erp.codigoPostal.br.ERPCodigoPostalBR;
import br.org.coletivoJava.fw.api.erp.erpintegracao.contextos.ERPIntegracaoSistemasApi;
import br.org.coletivoJava.fw.api.erp.erpintegracao.servico.ItfIntegracaoERP;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.FabConfigModuloWebERPChaves;
import com.super_bits.modulosSB.SBCore.modulos.erp.SolicitacaoControllerERP;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilCRCStringJson;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilCRCStringValidador;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.ComoFabricaIntegracaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.FabTipoConexaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.InfoConsumoRestService;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.servicoRegistrado.FabTipoAutenticacaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.servicoRegistrado.InfoConfigRestClientIntegracao;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token.ItfTokenGestao;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token.ItfTokenGestaoOauth;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.transmissao_recepcao_rest_client.ItfAcaoApiRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.UtilSBIntegracaoClientReflexao;
import com.super_bits.modulosSB.SBCore.modulos.ManipulaArquivo.importacao.FabTipoArquivoImportacao;
import com.super_bits.modulosSB.SBCore.modulos.erp.ItfSistemaERP;
import com.super_bits.modulosSB.SBCore.modulos.objetos.entidade.basico.ComoUsuario;

/**
 *
 * @author sfurbino
 */
@InfoConfigRestClientIntegracao(enderecosDocumentacao = "https://coletivojava.com.br",
        tipoAutenticacao = FabTipoAutenticacaoRest.OAUTHV2,
        nomeIntegracao = "intERPRestfull",
        configuracao = FabConfigModuloWebERPChaves.class
)
public enum FabIntApiRestIntegracaoERPRestfull implements ComoFabricaIntegracaoRest {

    @InfoConsumoRestService(getPachServico = "/oauth2Service/status/{0}",
            tipoConexao = FabTipoConexaoRest.GET,
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            parametrosGet = {"chavePublica"},
            urlDocumentacao = "https://coletivojava.com.br",
            adicionarAutenticacaoBearer = true)
    OAUTH_VALIDAR_CREDENCIAL,
    @InfoConsumoRestService(getPachServico = "/acao/executar/{0}/{1}",
            tipoConexao = FabTipoConexaoRest.PUT,
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            parametrosGet = {"nomeUnicoAcaoGestao", "codigoEntidade"},
            urlDocumentacao = "https://coletivojava.com.br",
            adicionarAutenticacaoBearer = true)
    ACOES_EXECUTAR_CONTROLLER_ENTIDADE_EXISTENTE,
    @InfoConsumoRestService(getPachServico = "/{0}/executar/",
            tipoConexao = FabTipoConexaoRest.POST,
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            parametrosGet = {"nomeUnicoAcaoGestao", "codigoEntidade"},
            urlDocumentacao = "https://coletivojava.com.br",
            adicionarAutenticacaoBearer = true)
    ACOES_EXECUTAR_CONTROLLER,
    @InfoConsumoRestService(getPachServico = "/acoesRestful/acaogestao/",
            tipoConexao = FabTipoConexaoRest.POST,
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            aceitarCertificadoDeHostNaoConfiavel = true,
            parametrosGet = {},
            urlDocumentacao = "https://coletivojava.com.br",
            adicionarAutenticacaoBearer = true)
    ACOES_EXECUTAR_CRIAR_NOVA_ENTIDADE,
    @InfoConsumoRestService(getPachServico = "/acoesRestful/acaogestao/{0}",
            tipoConexao = FabTipoConexaoRest.PUT,
            aceitarCertificadoDeHostNaoConfiavel = true,
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            parametrosGet = {"idEntidade"},
            urlDocumentacao = "https://coletivojava.com.br",
            adicionarAutenticacaoBearer = true)
    ACOES_EXECUTAR_ATUALIZAR_ENTIDADE,
    @InfoConsumoRestService(getPachServico = "/acoesRestful/acaogestao/{0}",
            tipoConexao = FabTipoConexaoRest.DELETE,
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            parametrosGet = {"identidade"},
            urlDocumentacao = "https://coletivojava.com.br",
            adicionarAutenticacaoBearer = true)
    ACOES_EXECUTAR_DELETE,
    @InfoConsumoRestService(getPachServico = "/{0}",
            tipoConexao = FabTipoConexaoRest.OPTIONS,
            parametrosGet = "Acção Gestão",
            aceitarCertificadoDeHostNaoConfiavel = true,
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            urlDocumentacao = "https://coletivojava.com.br",
            adicionarAutenticacaoBearer = true)
    ACOES_GET_OPCOES,
    ACOES_GET_ESTRUTURA_CAMPOS_FORMULARIO,
    @InfoConsumoRestService(getPachServico = "/{0}/{1}/{02}",
            tipoConexao = FabTipoConexaoRest.GET,
            aceitarCertificadoDeHostNaoConfiavel = true,
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            parametrosGet = {"nomeUnicoAcaoGestao", "codEntidade", "atributo"},
            urlDocumentacao = "https://coletivojava.com.br",
            adicionarAutenticacaoBearer = true)
    ACOES_GET_LISTA_ENTIDADES,
    ACOES_GET_REGISTRO_ENTIDADE;

    public ItfTokenGestaoOauth getGestaoToken(SolicitacaoControllerERP pSolicitacao) {
        return (ItfTokenGestaoOauth) getGestaoToken(pSolicitacao.getErpServico());
    }

    /**
     * Utilize getAcao(SolicitacaoControllerERP pSolicicatacao)
     *
     * @param parametros
     * @return
     * @deprecated
     */
    @Override
    @Deprecated
    public ItfAcaoApiRest getAcao(Object... parametros) {
        return ComoFabricaIntegracaoRest.super.getAcao(parametros); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    public ItfAcaoApiRest getAcao(SolicitacaoControllerERP pSolicicatacao) {
        ItfIntegracaoERP resp = ERPIntegracaoSistemasApi.RESTFUL.getImplementacaoDoContexto();
        String hashServidor = pSolicicatacao.getErpServico();
        ItfSistemaERP sistemaSErvidor = resp.getSistemaByHashChavePublica(hashServidor);
        if (sistemaSErvidor == null) {
            throw new UnsupportedOperationException("Hash chave pública do serviço não foi definida");
        }
        FabTipoAgenteClienteApi tipoAgente = FabTipoAgenteClienteApi.USUARIO;
        if (pSolicicatacao.isSolicitarComoAdmin()) {
            tipoAgente = FabTipoAgenteClienteApi.SISTEMA;
        }
        switch (tipoAgente) {

            case USUARIO:
                return ComoFabricaIntegracaoRest.super.getAcao(tipoAgente, SBCore.getUsuarioLogado(), pSolicicatacao);
            case SISTEMA:
                return ComoFabricaIntegracaoRest.super.getAcao(tipoAgente, pSolicicatacao.getUsuarioSolicitante(), pSolicicatacao);

            default:
                throw new AssertionError();
        }

    }

    public ItfTokenGestaoOauth getGestaoToken(ItfSistemaERP pSistemaServico) {

        return (ItfTokenGestaoOauth) ComoFabricaIntegracaoRest.super.getGestaoToken(SBCore.getUsuarioLogado(), pSistemaServico.getHashChavePublica());
    }

    private ItfTokenGestaoOauth getGestaoToken(ItfSistemaERP pSistemaServico, ComoUsuario pUsuario) {

        return (ItfTokenGestaoOauth) ComoFabricaIntegracaoRest.super.getGestaoToken(pUsuario,
                pSistemaServico.getHashChavePublica());
    }

    @Override
    public ItfTokenGestaoOauth getGestaoToken(ComoUsuario pUsuario) {
        throw new UnsupportedOperationException("Informe o sistema chamando get getGestaoToken(ItfSistemaERP)");
    }

    public static ItfTokenGestaoOauth getGestaoTokenOpcoes(ItfSistemaERP pSistemaServico) {

        if (!UtilCRCStringValidador.isNuloOuEmbranco(pSistemaServico.getEmailusuarioAdmin())) {
            if (SBCore.getUsuarioLogado().getEmail().equals(pSistemaServico.getEmailusuarioAdmin())) {
                return getGestaoTokenOpcoesAdmin(pSistemaServico);
            }
        }
        return FabIntApiRestIntegracaoERPRestfull.ACOES_GET_OPCOES.getGestaoToken(pSistemaServico);
    }

    public synchronized static ItfTokenGestaoOauth getGestaoTokenOpcoesAdmin(ItfSistemaERP pSistemaServico) {

        if (UtilCRCStringValidador.isNuloOuEmbranco(pSistemaServico.getEmailusuarioAdmin())) {
            throw new UnsupportedOperationException("Email do usuaário administrativo do sistema" + pSistemaServico.getNome() + " não foi definida");
        }

        return (ItfTokenGestaoOauth) UtilSBIntegracaoClientReflexao.getNovaInstanciaGestaoAutenticador(FabIntApiRestIntegracaoERPRestfull.ACOES_GET_OPCOES,
                FabTipoAgenteClienteApi.SISTEMA, null, pSistemaServico.getHashChavePublica());

    }

}
