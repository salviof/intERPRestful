/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.servletOauthServer;

import br.org.coletivoJava.fw.api.erp.erpintegracao.contextos.ERPIntegracaoSistemasApi;
import br.org.coletivoJava.fw.api.erp.erpintegracao.model.ItfSistemaERPLocal;
import br.org.coletivoJava.fw.api.erp.erpintegracao.servico.ItfIntegracaoERP;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.MapaTokensGerenciadosConcessaoOauth;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.token.TokenConcessaoOauthServer;
import static br.org.coletivoJava.fw.erp.implementacao.erpintegracao.servletOauthServer.FabTipoRequisicaoOauthServer.OBTER_CODIGO_DE_CONCESSAO_DE_ACESSO;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.servletOauthServer.model.SolicitacaoTokenOpenId;
import br.org.coletivoJava.integracoes.restInterprestfull.api.FabIntApiRestIntegracaoERPRestfull;
import com.super_bits.modulosSB.Persistencia.dao.UtilSBPersistencia;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.modulos.erp.ItfSistemaERP;
import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.super_bits.modulosSB.SBCore.modulos.objetos.entidade.basico.ComoSessao;
import com.super_bits.modulosSB.SBCore.modulos.objetos.entidade.basico.ComoUsuario;

/**
 *
 * @author salvio
 */
public abstract class ServletOauth2ServerAbs extends HttpServlet implements Serializable {

    protected static final ItfIntegracaoERP integracaoEntreSistemas = ERPIntegracaoSistemasApi.RESTFUL.getImplementacaoDoContexto();
    protected static final ItfIntegracaoERP erp = ERPIntegracaoSistemasApi.RESTFUL.getImplementacaoDoContexto();

    protected abstract ComoSessao getSessao();

    public void despacharLoginUsuarioRemotoEscopoDeUsuario(HttpServletResponse pResposta, HttpServletRequest requisicao, TipoRequisicaoOauth pTipoRequisicao,
            String pDominioConexao, ItfSistemaERP pSistemaCliente, ComoUsuario pUsuario, String pUrlRedirecionamentoTokenCodigoSolicitacao)
            throws Throwable {

        String dominioDoSistemaClienteResgistrado = pSistemaCliente.getDominio();
        if (SBCore.isEmModoProducao()
                && (pDominioConexao == null
                || !pDominioConexao.contains(dominioDoSistemaClienteResgistrado))) {
            if (!dominioDoSistemaClienteResgistrado.contains("localhost")) {
                pResposta.getWriter().append("ACESSO NEGADO, A ORIGEM DA REQUISIÇÃO DIVERGE DA ORIEM AUTORIZADA [" + pDominioConexao + "|" + dominioDoSistemaClienteResgistrado + "]");
                System.out.println("Acesso negado, origem inválida:" + pDominioConexao + "Origem aceita:" + dominioDoSistemaClienteResgistrado);
                return;
            }
        }

        getSessao().getUsuario();
        System.out.println(getSessao().getUsuario().getEmail());

        if (!pUsuario.equals(getSessao().getUsuario())) {

            if (getSessao().isIdentificado()) {

                getSessao().encerrarSessao();
            }

        }
        despacharLoginUsuarioRemoto(pResposta, requisicao, pTipoRequisicao, pDominioConexao, pSistemaCliente, pUsuario.getEmail(), pUrlRedirecionamentoTokenCodigoSolicitacao, null, null);

    }

    public void despacharLoginUsuarioRemoto(HttpServletResponse pResposta, HttpServletRequest requisicao, TipoRequisicaoOauth pTipoRequisicao, String pDominioConexao, ItfSistemaERP pSistemaCliente,
            String escopo,
            String pUrlRedirecionamentoTokenCodigoSolicitacao, String pState, String pNonce) throws Throwable {
        ItfSistemaERPLocal sistemaRecursos = erp.getSistemaAtual();
        if (!getSessao().isIdentificado()) {
            if (SBCore.isEmModoDesenvolvimento()) {
                pResposta.getWriter().append("EFETUE LOGIN DE FORMA PROGRAMÁTICA,POR PADRÃO O SERVIÇO CDI QUE GERE OS BEANS DE SESSÃO NÃO É ATIVADO NO MODO TESTES");
            } else {
                RequestDispatcher despachadorDeRespostaParaRequisicao = requisicao
                        .getRequestDispatcher("/resources/oauth/login.xhtml?hashChavePublicaAplicacaoSolicitante=" + pSistemaCliente.getHashChavePublica() + "&scopo=" + escopo);

                despachadorDeRespostaParaRequisicao.forward(requisicao, pResposta);

                return;
            }
            return;
        }
        switch (pTipoRequisicao.getEnumVinculado()) {
            case OBTER_CODIGO_DE_CONCESSAO_DE_ACESSO:

                TokenConcessaoOauthServer tokenConcessaodeAcesso = MapaTokensGerenciadosConcessaoOauth.gerarNovoTokenCocessaoDeAcesso(pSistemaCliente, SBCore.getUsuarioLogado());

                if (tokenConcessaodeAcesso == null) {
                    throw new ServletException("Falha gerando código de concessao do token de acesso");
                }

                System.out.println("REspondendo ao usuário com o link de registro do token");
                String url = pUrlRedirecionamentoTokenCodigoSolicitacao + "?code=" + tokenConcessaodeAcesso.getToken();

                if (pState != null) {
                    url = url + "&state=" + pState;
                }
                if (pNonce != null) {

                    // url = url + "&nonce=" + pNonce;
                    UtilSBOpenIDOauth.armazenarSolicitaca(new SolicitacaoTokenOpenId(pNonce, pSistemaCliente.getHashChavePublica(), tokenConcessaodeAcesso.getToken(), getSessao().getUsuario().getId()));
                }
                System.out.println("RETORNO PARA " + url);

                //+ "&tipoAplicacao=" + sistemaRecursos.getHashChavePublica() + "&escopo=" + getSessao().getUsuario().getEmail();
                pResposta.getWriter().append("<script> windows.location='" + url + "'</script>");
                pResposta.sendRedirect(url);

                break;

            default:
                throw new ServletException("o método despachar login admin só processa requisições do tipo  " + FabTipoRequisicaoOauthServer.OBTER_CODIGO_DE_AUTORIZACAO.toString());

        }
    }
}
