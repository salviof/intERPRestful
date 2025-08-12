/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.servletRestfulERP;

import br.org.coletivoJava.fw.api.erp.erpintegracao.contextos.ERPIntegracaoSistemasApi;
import br.org.coletivoJava.fw.api.erp.erpintegracao.servico.ItfIntegracaoERP;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.ErroTentandoObterTokenAcesso;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.UtilSBRestful;
import com.super_bits.modulos.SBAcessosModel.view.FabAcaoPaginasDoSistema;
import com.super_bits.modulosSB.SBCore.modulos.erp.SolicitacaoControllerERP;
import com.super_bits.modulosSB.SBCore.UtilGeral.MapaAcoesSistema;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.acoes.ItfAcaoDoSistema;
import com.super_bits.modulosSB.SBCore.modulos.Controller.comunicacao.RespostaAcaoDoSistema;
import com.super_bits.modulosSB.SBCore.modulos.Controller.qualificadoresCDI.sessao.QlSessaoFacesContext;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfSessao;
import com.super_bits.modulosSB.SBCore.modulos.servicosCore.ItfServicoControllerExecucao;
import com.super_bits.modulosSB.webPaginas.controller.servlets.WebPaginasServlet;
import com.super_bits.modulosSB.webPaginas.util.UtilSBWPServletTools;
import com.super_bits.modulosSB.webPaginas.util.UtilSBWP_JSFTools;
import java.io.IOException;
import java.io.Serializable;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author sfurbino
 */
public class ServletRestfullERP extends HttpServlet implements Serializable {

    public static final String SLUGPUBLICACAOSERVLET = "acoesRestful";
    private static ItfIntegracaoERP erpIntegraca = ERPIntegracaoSistemasApi.RESTFUL.getImplementacaoDoContexto();

    @Inject
    @QlSessaoFacesContext
    private ItfSessao sessaoAtual;

    @Inject
    private ItfServicoControllerExecucao entregaJson;

    private void processarSolicitacao(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SolicitacaoControllerERP solicitacao;
        try {

            solicitacao = UtilSBRestful.getSolicitacaoByRequest(req);

            if (solicitacao != null && solicitacao.getUsuarioSolicitante() != null) {
                sessaoAtual.setUsuario(solicitacao.getUsuarioSolicitante());
            }
            if (!solicitacao.getMetodo().equals("OPTIONS")) {
                if (solicitacao.getAcaoStrNomeUnico() == null) {
                    RespostaAcaoDoSistema resposta = new RespostaAcaoDoSistema();
                    resposta.addErro("A ação do sistema não foi enviada");
                    resp.setStatus(503);
                    String respostaStr = UtilSBRestful.buildTextoJsonResposta(resposta);
                    resp.getWriter().append(respostaStr);
                    return;
                }

                ItfAcaoDoSistema acao = MapaAcoesSistema.getAcaoDoSistemaByNomeUnico(solicitacao.getAcaoStrNomeUnico());
                if (acao == null) {
                    RespostaAcaoDoSistema resposta = new RespostaAcaoDoSistema();
                    resposta.addErro("A ação " + solicitacao.getAcaoStrNomeUnico() + " não foi encontrata");
                    resp.setStatus(503);
                    String respostaStr = UtilSBRestful.buildTextoJsonResposta(resposta);
                    resp.getWriter().append(respostaStr);
                    return;
                }

            }
        } catch (ErroTentandoObterTokenAcesso ex) {
            RespostaAcaoDoSistema resposta = new RespostaAcaoDoSistema();
            resposta.addErro("Autenticação negada ");
            resp.setStatus(401);
            String respostaStr = UtilSBRestful.buildTextoJsonResposta(resposta);

            resp.getWriter().append(respostaStr);
            return;
        }
        req.setAttribute("solicitacao", solicitacao);
        ;
        //UtilSBWPServletTools.getRequestAtribute(WebPaginasServlet.NOME_BEAN_PACOTE_CONTROLLER_REQ) //UtilSBWP_JSFTools.dispacharRespostaController(pPacote, req, resp);
        RequestDispatcher despachadorDeRespostaParaRequisicao = req.getRequestDispatcher(FabAcaoPaginasDoSistema.FORMULARIO_API_RESTFUL);
        req.setAttribute("solicitacao", solicitacao);
        despachadorDeRespostaParaRequisicao.forward(req, resp);

    }

    @Override
    public void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processarSolicitacao(req, resp);

    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processarSolicitacao(req, resp);

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processarSolicitacao(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processarSolicitacao(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processarSolicitacao(req, resp);
    }

}
