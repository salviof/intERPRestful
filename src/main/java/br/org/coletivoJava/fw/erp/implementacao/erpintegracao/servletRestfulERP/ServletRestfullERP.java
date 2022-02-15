/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.servletRestfulERP;

import br.org.coletivoJava.fw.api.erp.erpintegracao.contextos.ERPIntegracaoSistemasApi;
import br.org.coletivoJava.fw.api.erp.erpintegracao.model.ItfSistemaERPAtual;
import br.org.coletivoJava.fw.api.erp.erpintegracao.servico.ItfIntegracaoERP;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.ErroTentandoObterTokenAcesso;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.MapaTokensGerenciadosConcessaoOauth;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.SolicitacaoControllerERP;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.token.TokenAcessoOauthServer;
import com.super_bits.modulosSB.Persistencia.dao.UtilSBPersistencia;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.MapaAcoesSistema;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreJson;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringValidador;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.erp.dto.DTO_SBGENERICO;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.ItfRespostaAcaoDoSistema;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.acoes.ItfAcaoDoSistema;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.permissoes.ItfAcaoGerenciarEntidade;
import com.super_bits.modulosSB.SBCore.modulos.Controller.UtilSBController;
import com.super_bits.modulosSB.SBCore.modulos.Controller.comunicacao.RespostaAcaoDoSistema;
import com.super_bits.modulosSB.SBCore.modulos.Mensagens.ItfMensagem;
import com.super_bits.modulosSB.SBCore.modulos.fabrica.ItfFabricaAcoes;
import com.super_bits.modulosSB.SBCore.modulos.objetos.MapaObjetosProjetoAtual;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfBeanSimples;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfUsuario;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonValue;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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

    private SolicitacaoControllerERP getSolicitacao(HttpServletRequest pRequisicao) {
        String pathChamada = pRequisicao.getRequestURI();

        String[] parametrosDeURL = pathChamada.split("/");
        ItfIntegracaoERP implementacao = ERPIntegracaoSistemasApi.RESTFUL.getImplementacaoDoContexto();
        ItfSistemaERPAtual sistema = implementacao.getSistemaAtual();
        SolicitacaoControllerERP solicitacao = new SolicitacaoControllerERP(sistema, null);

        return solicitacao;
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

    public static String buildRespostaOptions(HttpServletRequest req) {
        try {

            ItfUsuario usuario = autenticarUsuario(req);
            String uri = req.getRequestURI();
            String[] partes = uri.split("/");
            List<ItfAcaoGerenciarEntidade> acoesDoUsuario = new ArrayList();
            if (partes.length == 3) {
                List<ItfAcaoGerenciarEntidade> acoesGestao = MapaAcoesSistema.getListaTodasGestao();

                acoesGestao.stream().filter(gt -> SBCore.getServicoPermissao().isAcaoPermitidaUsuario(usuario, gt)).forEach(acoesDoUsuario::add);
            } else {

            }
            JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
            jsonBuilder.add("scope", usuario.getEmail());
            jsonBuilder.add("modulo", usuario.getGrupo().getModuloPrincipal().getNome());

            JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
            acoesDoUsuario.stream().map(ac -> ac.getNomeUnicoSlug()).forEach(jsonArrayBuilder::add);
            jsonBuilder.add("acoes", jsonArrayBuilder.build());
            JsonObject retorno = jsonBuilder.build();

            RespostaAcaoDoSistema resposta = new RespostaAcaoDoSistema();
            resposta.addAviso("As ações de gestão de entetidade disponíveis são");
            resposta.setRetorno(retorno);
            return buildResposta(resposta);

        } catch (ErroTentandoObterTokenAcesso ex) {
            //enviarAcessoNegado(ex, "asd");
            return null;
        } catch (ServletException ex) {
            Logger.getLogger(ServletRestfullERP.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public static String buildResposta(ItfRespostaAcaoDoSistema resposta) throws ServletException {
        JsonArrayBuilder mensagens = Json.createArrayBuilder();
        for (ItfMensagem msg : resposta.getMensagens()) {
            JsonObjectBuilder msgJsonBuilder = Json.createObjectBuilder();
            msgJsonBuilder.add("tipoMensagem", msg.getTipoDeMensagem().name());
            msgJsonBuilder.add("tipoAgente", msg.getTipoDeMensagem().name());
            msgJsonBuilder.add("mensagem", msg.getMenssagem());
            mensagens.add(msgJsonBuilder.build());
        }

        JsonObjectBuilder construtor = Json.createObjectBuilder();
        construtor.add("resultado", resposta.getResultado().name());
        construtor.add("sucesso", resposta.isSucesso());
        construtor.add("mensagem", mensagens);
        if (resposta.getRetorno() instanceof JsonObject) {
            construtor.add("retorno", (JsonObject) resposta.getRetorno());
        } else {
            construtor.add("retorno", "");
        }

        if (UtilSBCoreStringValidador.isNuloOuEmbranco(resposta.getUrlDestino())) {
            construtor.add("urlDestino", JsonValue.NULL);
        } else {
            construtor.add(SLUGPUBLICACAOSERVLET, BigDecimal.ONE);
        }

        String respostaTexto = UtilSBCoreJson.getTextoByJsonObjeect(construtor.build());
        return respostaTexto;
        //try {
        //     resp.getWriter().append(respostaTexto);
        // } catch (IOException ex) {
        //     throw new ServletException("Falha gerando resposta");
        // }
    }

    @Override
    public void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    private static ItfUsuario autenticarUsuario(HttpServletRequest pSolicitacao) throws ErroTentandoObterTokenAcesso {
        String autorizacaoWrap = pSolicitacao.getHeader("Authorization");
        String autorizacao = autorizacaoWrap.replace("Bearer ", "");

        TokenAcessoOauthServer dadosToken = MapaTokensGerenciadosConcessaoOauth.loadTokenExistente(autorizacao);

        String scopo = dadosToken.getScope();
        ItfUsuario usuario = SBCore.getServicoPermissao().getUsuarioByEmail(scopo);
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

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ItfUsuario usuario = autenticarUsuario(req);

            SolicitacaoControllerERP soclicitacao = getSolicitacao(req);
            ItfRespostaAcaoDoSistema resposata = getResposta(soclicitacao);
            //   buildResposta(resposata, resp);
        } catch (ErroTentandoObterTokenAcesso ex) {
            enviarAcessoNegado(ex, resp);
        }

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ItfUsuario usuario = autenticarUsuario(req);

            SolicitacaoControllerERP soclicitacao = getSolicitacao(req);
            ItfRespostaAcaoDoSistema resposata = getResposta(soclicitacao);
            //    buildResposta(resposata, resp);
        } catch (ErroTentandoObterTokenAcesso ex) {
            enviarAcessoNegado(ex, resp);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ItfUsuario usuario = autenticarUsuario(req);

            SolicitacaoControllerERP soclicitacao = getSolicitacao(req);
            ItfRespostaAcaoDoSistema resposata = getResposta(soclicitacao);
            //   buildResposta(resposata, resp);
        } catch (ErroTentandoObterTokenAcesso ex) {
            enviarAcessoNegado(ex, resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ItfUsuario usuario = autenticarUsuario(req);

            SolicitacaoControllerERP soclicitacao = getSolicitacao(req);
            ItfRespostaAcaoDoSistema resposata = getResposta(soclicitacao);
//            buildResposta(resposata, resp);
        } catch (ErroTentandoObterTokenAcesso ex) {
            enviarAcessoNegado(ex, resp);
        }
    }

}
