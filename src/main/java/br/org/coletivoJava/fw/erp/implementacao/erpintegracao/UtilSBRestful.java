/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao;

import br.org.coletivoJava.fw.api.erp.erpintegracao.contextos.ERPIntegracaoSistemasApi;
import br.org.coletivoJava.fw.api.erp.erpintegracao.model.ItfSistemaERPAtual;
import br.org.coletivoJava.fw.api.erp.erpintegracao.servico.ItfIntegracaoERP;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.token.TokenAcessoOauthServer;
import static br.org.coletivoJava.fw.erp.implementacao.erpintegracao.servletRestfulERP.ServletRestfullERP.SLUGPUBLICACAOSERVLET;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreJson;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringValidador;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.ItfRespostaAcaoDoSistema;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.acoes.ItfAcaoDoSistema;
import com.super_bits.modulosSB.SBCore.modulos.Mensagens.ItfMensagem;
import com.super_bits.modulosSB.SBCore.modulos.erp.SolicitacaoControllerERP;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfBeanSimples;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfUsuario;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonValue;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.coletivojava.fw.api.objetoNativo.controller.sistemaErp.ItfSistemaErp;

/**
 *
 * @author sfurbino
 */
public class UtilSBRestful {

    public static String getCorpoRequisicao(HttpServletRequest pRequest) {
        String corpo;
        try {
            corpo = pRequest.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            return corpo;
        } catch (IOException ex) {
            return null;
        }

    }

    public static String getCodigoEntidade(HttpServletRequest pRequest) {

        return null;
    }

    public static String getAtributoEntidade(HttpServletRequest pRequest) {

        return null;
    }

    public static String getNomeSlugAcao(HttpServletRequest pRequest) {
        String caminhoChamada = pRequest.getRequestURI();

        String[] partes = caminhoChamada.split("/");
        if (partes.length >= 3) {
            String slugAcao = partes[2];
            return slugAcao;
        }
        return null;
    }

    //(String pMetodoRestful, String pHashIdentificadorCliente,
    //String pNomeUnicoAcao, ItfUsuario pUsuario, String pCodigo, String pAtributo, JsonObject pParametros)
    public static SolicitacaoControllerERP getSolicitacao(ItfSistemaErp pCliente, ItfSistemaErp pServico, FabTipoSolicitacaoRestfull pAcaoRestful,
            ItfAcaoDoSistema pAcaoSistema, ItfBeanSimples pBeanSimples) {
        ItfIntegracaoERP erpIntegracao = ERPIntegracaoSistemasApi.RESTFUL.getImplementacaoDoContexto();
        String acao = null;
        if (pAcaoSistema != null) {
            acao = pAcaoSistema.getNomeUnico();
        }
        String codigoBeanSimples = null;
        if (pBeanSimples != null) {
            codigoBeanSimples = String.valueOf(pBeanSimples.getId());
        }
        SolicitacaoControllerERP novaSolicitacao = new SolicitacaoControllerERP(
                pAcaoRestful.getMetodo(),
                pServico.getHashChavePublica(),
                pCliente.getHashChavePublica(),
                acao, SBCore.getUsuarioLogado(), codigoBeanSimples,
                null,
                erpIntegracao.gerarConversaoObjetoToJson(pServico, pBeanSimples));

        return novaSolicitacao;
    }

    public static SolicitacaoControllerERP getSolicitacaoByRequest(HttpServletRequest pRequest) throws ErroTentandoObterTokenAcesso {
        TokenAcessoOauthServer token = null;
        ItfUsuario usuario = null;
        String acaoDoSistemaEnum = null;
        String codigoEntidade = null;
        String atributoEntidade = null;
        JsonObject json = null;

        token = getTokenAcesso(pRequest);
        usuario = autenticarUsuario(token);
        acaoDoSistemaEnum = getNomeSlugAcao(pRequest);
        token.getObjetoJsonResposta();
        String corpoRequisicaoTesxto = getCorpoRequisicao(pRequest);
        json = UtilSBCoreJson.getJsonObjectByTexto(corpoRequisicaoTesxto);
        ItfIntegracaoERP servicoRestful = ERPIntegracaoSistemasApi.RESTFUL.getImplementacaoDoContexto();
        ItfSistemaERPAtual servidor = servicoRestful.getSistemaAtual();

        SolicitacaoControllerERP solicitacao = new SolicitacaoControllerERP(
                pRequest.getMethod(),
                servidor.getHashChavePublica(),
                token.getClient_id(), codigoEntidade, usuario, codigoEntidade, atributoEntidade, json);

        return solicitacao;

    }

    private static TokenAcessoOauthServer getTokenAcesso(HttpServletRequest pSolicitacao) throws ErroTentandoObterTokenAcesso {

        String autorizacaoWrap = pSolicitacao.getHeader("Authorization");
        String autorizacao = autorizacaoWrap.replace("Bearer ", "");
        TokenAcessoOauthServer dadosToken = MapaTokensGerenciadosConcessaoOauth.loadTokenExistente(autorizacao);
        return dadosToken;
    }

    private static ItfUsuario getUsuario(TokenAcessoOauthServer pToken) throws ErroTentandoObterTokenAcesso {

        String scopo = pToken.getScope();
        ItfUsuario usuario = SBCore.getServicoPermissao().getUsuarioByEmail(scopo);
        return usuario;

    }

    private static ItfUsuario autenticarUsuario(TokenAcessoOauthServer pToken) throws ErroTentandoObterTokenAcesso {
        ItfUsuario usuario = getUsuario(pToken);
        SBCore.getServicoSessao().getSessaoAtual().setUsuario(usuario);
        if (pToken == null) {
            throw new ErroTentandoObterTokenAcesso("Token não existe");
        }
        if (!pToken.isTokenValido()) {
            throw new ErroTentandoObterTokenAcesso("Este token não é mais válido");
        }
        return usuario;

    }

    public static String buildTextoJsonResposta(ItfRespostaAcaoDoSistema resposta) throws ServletException {
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

    }
}
