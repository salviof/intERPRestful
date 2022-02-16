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
import com.super_bits.modulosSB.SBCore.modulos.erp.SolicitacaoControllerERP;
import com.super_bits.modulosSB.Persistencia.dao.UtilSBPersistencia;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreJson;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.erp.dto.DTO_SBGENERICO;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.ItfRespostaAcaoDoSistema;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.acoes.ItfAcaoDoSistema;
import com.super_bits.modulosSB.SBCore.modulos.Controller.UtilSBController;
import com.super_bits.modulosSB.SBCore.modulos.fabrica.ItfFabricaAcoes;
import com.super_bits.modulosSB.SBCore.modulos.objetos.MapaObjetosProjetoAtual;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfBeanSimples;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
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

    public static ItfRespostaAcaoDoSistema getResposta(SolicitacaoControllerERP pSolicitacao) {
        ItfFabricaAcoes fabAcao = SBCore.getFabricaByNOME_UNICO(pSolicitacao.getAcaoStrNomeUnico());
        ItfAcaoDoSistema acao = fabAcao.getRegistro();
        if (acao.isUmaAcaoController()) {
            Method metodo = UtilSBController.getMetodoByAcaoController(acao.getComoController());
            String tipoMetodoParametro = metodo.getParameterTypes()[0].getSimpleName();

            JsonObject jsonSolicitacao = UtilSBCoreJson.getJsonObjectByTexto(pSolicitacao.getCorpoParametros());
            JsonArray parametros = jsonSolicitacao.getJsonArray("parametros");
            if (!parametros.isEmpty()) {
                ItfBeanSimples objetoParametro;
                JsonObject parametro = (JsonObject) parametros.get(0);
                String nomeEntidade = parametro.getString("entidade");
                String codigoEntidadeStr = parametro.getString("id");
                if (codigoEntidadeStr != null) {
                    int codigoEntidade = Integer.valueOf(codigoEntidadeStr);
                    EntityManager em = UtilSBPersistencia.getEntyManagerPadraoNovo();
                    Class entidade = MapaObjetosProjetoAtual.getClasseDoObjetoByNome(nomeEntidade);
                    objetoParametro = (ItfBeanSimples) UtilSBPersistencia.getRegistroByID(entidade, codigoEntidade, em);
                    DTO_SBGENERICO teste;
                    try {
                        ItfRespostaAcaoDoSistema resp = (ItfRespostaAcaoDoSistema) metodo.invoke(null, objetoParametro);
                        return resp;
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                        Logger.getLogger(ServletRestfullERP.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }

        }
        return null;
    }

    @Override
    public void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SolicitacaoControllerERP solicitacao = UtilSBRestful.getSolicitacaoByRequest(req);
        ItfRespostaAcaoDoSistema resposta = erpIntegraca.getRespostaAcaoDoSistema(solicitacao);
        String respostaStr = UtilSBRestful.buildTextoJsonResposta(resposta);
        resp.getWriter().append(respostaStr);
    }

    private void enviarAcessoNegado(ErroTentandoObterTokenAcesso pErro, HttpServletResponse pREsposta) throws ServletException {
        try {
            pREsposta.sendError(401, pErro.getMessage());
        } catch (IOException ex) {
            throw new ServletException("Erro registrando resposta código 401");
        }
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SolicitacaoControllerERP solicitacao = UtilSBRestful.getSolicitacaoByRequest(req);
        ItfRespostaAcaoDoSistema resposta = erpIntegraca.getRespostaAcaoDoSistema(solicitacao);
        String respostaStr = UtilSBRestful.buildTextoJsonResposta(resposta);
        resp.getWriter().append(respostaStr);

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SolicitacaoControllerERP solicitacao = UtilSBRestful.getSolicitacaoByRequest(req);
        ItfRespostaAcaoDoSistema resposta = erpIntegraca.getRespostaAcaoDoSistema(solicitacao);
        String respostaStr = UtilSBRestful.buildTextoJsonResposta(resposta);
        resp.getWriter().append(respostaStr);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SolicitacaoControllerERP solicitacao = UtilSBRestful.getSolicitacaoByRequest(req);
        ItfRespostaAcaoDoSistema resposta = erpIntegraca.getRespostaAcaoDoSistema(solicitacao);
        String respostaStr = UtilSBRestful.buildTextoJsonResposta(resposta);
        resp.getWriter().append(respostaStr);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SolicitacaoControllerERP solicitacao = UtilSBRestful.getSolicitacaoByRequest(req);
        ItfRespostaAcaoDoSistema resposta = erpIntegraca.getRespostaAcaoDoSistema(solicitacao);
        String respostaStr = UtilSBRestful.buildTextoJsonResposta(resposta);
        resp.getWriter().append(respostaStr);
    }

}
