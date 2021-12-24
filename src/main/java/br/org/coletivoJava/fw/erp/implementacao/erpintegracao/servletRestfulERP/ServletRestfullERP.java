/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.servletRestfulERP;

import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.ErroTentandoObterTokenAcesso;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.MapaTokensGerenciadosConcessaoOauth;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.SolicitacaoControllerERP;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.token.TokenAcessoOauthServer;
import com.super_bits.modulos.SBAcessosModel.model.acoes.AcaoDoSistema;
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
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfUsuario;
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

    private SolicitacaoControllerERP getSolicitacao(HttpServletRequest pRequisicao) {
        String jsonSolicitacaoTexto = pRequisicao.getHeader("solicitacao");
        JsonObject jsonSolicitacao = UtilSBCoreJson.getJsonObjectByTexto(jsonSolicitacaoTexto);
        SolicitacaoControllerERP solicitacao = new SolicitacaoControllerERP(jsonSolicitacao);
        return solicitacao;
    }

    private ItfUsuario autenticarUsuario(HttpServletResponse pSolicitacao) throws ErroTentandoObterTokenAcesso {
        String autorizacaoWrap = pSolicitacao.getHeader("Authorization");
        String autorizacao = autorizacaoWrap.replace("Bearer ", "");

        TokenAcessoOauthServer dadosToken = MapaTokensGerenciadosConcessaoOauth.loadTokenExistente(autorizacao);
        ItfUsuario usuario = SBCore.getServicoPermissao().getUsuarioByEmail(dadosToken.getIdentificacaoAgenteDeAcesso());
        SBCore.getServicoSessao().getSessaoAtual().setUsuario(usuario);
        if (dadosToken == null) {
            throw new ErroTentandoObterTokenAcesso("Token não existe");
        }
        if (!dadosToken.isTokenValido()) {
            throw new ErroTentandoObterTokenAcesso("Este token não é mais válido");
        }
        return usuario;

    }

    private void enviarAcessoNegado(ErroTentandoObterTokenAcesso pErro, HttpServletResponse pREsposta) throws ServletException {
        try {
            pREsposta.sendError(401, pErro.getMessage());
        } catch (IOException ex) {
            throw new ServletException("Erro registrando resposta código 401");
        }
    }

    private ItfRespostaAcaoDoSistema getResposta(SolicitacaoControllerERP pSolicitacao) {
        ItfFabricaAcoes fabAcao = SBCore.getFabricaByNOME_UNICO(pSolicitacao.getNomeUnicoAcao());
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ItfUsuario usuario = autenticarUsuario(resp);
            SolicitacaoControllerERP soclicitacao = getSolicitacao(req);
            ItfRespostaAcaoDoSistema resposata = getResposta(soclicitacao);
            UtilSBCoreJson
                    .getJsonObjectBySequenciaChaveValor(
                            "resultado", "",
                            "resposta", "{}",
                            "mensagens", "{}",
                            "sucesso", "{}",
                            "urlDestino", "",
                            "urlDestinoFalha", "",
                            "urlDestinoSucesso", ""
                    );
        } catch (ErroTentandoObterTokenAcesso ex) {
            enviarAcessoNegado(ex, resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doOptions(req, resp); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doTrace(req, resp); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp); //To change body of generated methods, choose Tools | Templates.
    }

}
