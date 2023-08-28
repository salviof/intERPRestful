/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao;

import br.org.coletivoJava.fw.api.erp.erpintegracao.contextos.ERPIntegracaoSistemasApi;
import br.org.coletivoJava.fw.api.erp.erpintegracao.servico.ItfIntegracaoERP;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.parametros.ParametroListaRestful;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.token.TokenAcessoOauthServer;
import static br.org.coletivoJava.fw.erp.implementacao.erpintegracao.servletRestfulERP.ServletRestfullERP.SLUGPUBLICACAOSERVLET;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreJson;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreJsonRest;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringValidador;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.ItfRespostaAcaoDoSistema;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.acoes.ItfAcaoDoSistema;
import com.super_bits.modulosSB.SBCore.modulos.Controller.fabricas.FabTipoAcaoSistemaGenerica;
import com.super_bits.modulosSB.SBCore.modulos.erp.ItfSistemaERP;
import com.super_bits.modulosSB.SBCore.modulos.erp.SolicitacaoControllerERP;
import com.super_bits.modulosSB.SBCore.modulos.fabrica.ItfFabrica;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfBeanSimples;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfUsuario;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonValue;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import br.org.coletivoJava.fw.api.erp.erpintegracao.model.ItfSistemaERPLocal;

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
        String caminhoChamada = pRequest.getRequestURI();

        String[] partes = caminhoChamada.split("/");
        if (partes.length >= 4) {
            String slugEntidade = partes[3];
            return slugEntidade;
        }
        return null;
    }

    public static String getAtributoEntidade(HttpServletRequest pRequest) {

        String caminhoChamada = pRequest.getRequestURI();

        String[] partes = caminhoChamada.split("/");
        if (partes.length >= 5) {
            String slugAtributo = partes[4];
            return slugAtributo;
        }
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
    public static SolicitacaoControllerERP getSolicitacaoAcaoController(ItfSistemaERP pCliente, ItfSistemaERP pServico,
            ItfAcaoDoSistema pAcaoSistema, ItfBeanSimples pBeanSimples) {
        ItfIntegracaoERP erpIntegracao = ERPIntegracaoSistemasApi.RESTFUL.getImplementacaoDoContexto();
        String acao = null;
        if (pAcaoSistema != null) {
            acao = pAcaoSistema.getNomeUnico();
        }

        return getSolicitacaoAcaoController(pCliente, pServico, acao, pBeanSimples);
    }

    private static boolean isUsuarioAdmin(ItfSistemaERP pSistemaServico) {
        if (pSistemaServico == null) {
            return false;
        }
        if (pSistemaServico.getEmailusuarioAdmin() != null && pSistemaServico.getEmailusuarioAdmin().isEmpty()) {
            if (SBCore.getUsuarioLogado().getEmail().equals(pSistemaServico.getEmailusuarioAdmin())) {
                return true;
            }
        }
        return false;
    }

    public static SolicitacaoControllerERP getSolicitacaoAcaoListagemDeEntidade(ItfSistemaERP pCliente, ItfSistemaERP pServico,
            String pNomeUnicoAcao, ItfBeanSimples pBeanSimples) {

        return getSolicitacaoAcaoListagemDeEntidade(pCliente, pServico, pNomeUnicoAcao, isUsuarioAdmin(pServico), pBeanSimples);

    }

    public static SolicitacaoControllerERP getSolicitacaoAcaoListagemDeEntidade(ItfSistemaERP pCliente, ItfSistemaERP pServico,
            String pNomeUnicoAcao, boolean pAcessarComoAdmin, ItfBeanSimples pBeanSimples) {
        ItfIntegracaoERP erpIntegracao = ERPIntegracaoSistemasApi.RESTFUL.getImplementacaoDoContexto();

        if (pNomeUnicoAcao == null) {
            throw new UnsupportedOperationException("A ação só pode ser nula com o tipo de solicitação options");
        }
        FabTipoAcaoSistemaGenerica tipoGenerico = FabTipoAcaoSistemaGenerica.getAcaoGenericaByNome(pNomeUnicoAcao);
        if (!tipoGenerico.equals(FabTipoAcaoSistemaGenerica.FORMULARIO_LISTAR)) {
            throw new UnsupportedOperationException("A ação " + pNomeUnicoAcao + " não parece ser do tipo lista");
        }

        String codigoBeanSimples = null;
        if (pBeanSimples != null) {
            codigoBeanSimples = String.valueOf(pBeanSimples.getId());
        }
        String atributo = null;
        if (pBeanSimples instanceof ParametroListaRestful) {
            atributo = ((ParametroListaRestful) pBeanSimples).getAtributo();
        }
        SolicitacaoControllerERP novaSolicitacao = new SolicitacaoControllerERP(
                FabTipoSolicitacaoRestfull.LISTA_DE_ENTIDADE.getMetodo(),
                pServico.getHashChavePublica(),
                pCliente.getHashChavePublica(),
                pNomeUnicoAcao, SBCore.getUsuarioLogado(), pAcessarComoAdmin, codigoBeanSimples,
                atributo,
                erpIntegracao.gerarConversaoObjetoToJson(pServico, pBeanSimples, true));

        return novaSolicitacao;
    }

    public static SolicitacaoControllerERP getSolicitacaoAcaoController(ItfSistemaERP pCliente, ItfSistemaERP pServico,
            String pNomeUnicoAcao, ItfBeanSimples pBeanSimples) {
        return getSolicitacaoAcaoController(pCliente, pServico, pNomeUnicoAcao, pBeanSimples, false);
    }

    public static SolicitacaoControllerERP getSolicitacaoAcaoController(ItfSistemaERP pCliente, ItfSistemaERP pServico,
            String pNomeUnicoAcao, ItfBeanSimples pBeanSimples, boolean pUsarCodigoIdComoId) {
        ItfIntegracaoERP erpIntegracao = ERPIntegracaoSistemasApi.RESTFUL.getImplementacaoDoContexto();

        if (pNomeUnicoAcao == null) {
            throw new UnsupportedOperationException("A ação só pode ser nula com o tipo de solicitação options");
        }
        if (!pNomeUnicoAcao.contains("_CTR_")) {
            throw new UnsupportedOperationException("A ação " + pNomeUnicoAcao + " não parece ser do tipo controller");
        }
        String codigoBeanSimples = null;
        if (pBeanSimples != null) {
            codigoBeanSimples = String.valueOf(pBeanSimples.getId());
        }
        JsonObject itemJson = erpIntegracao.gerarConversaoObjetoToJson(pServico, pBeanSimples, pUsarCodigoIdComoId);
        SolicitacaoControllerERP novaSolicitacao = new SolicitacaoControllerERP(
                FabTipoSolicitacaoRestfull.CONTROLLER.getMetodo(),
                pServico.getHashChavePublica(),
                pCliente.getHashChavePublica(),
                pNomeUnicoAcao, SBCore.getUsuarioLogado(), isUsuarioAdmin(pServico), codigoBeanSimples,
                null,
                itemJson);

        return novaSolicitacao;
    }

    /**
     *
     * @param pCliente
     * @param pServico
     * @param pNomeUnicoAcao Nome único da ação
     * @return
     */
    public static SolicitacaoControllerERP getSolicitacaoOption(ItfSistemaERP pCliente, ItfSistemaERP pServico,
            String pNomeUnicoAcao) {
        if (!UtilSBCoreStringValidador.isNuloOuEmbranco(pNomeUnicoAcao)) {
            if (!pNomeUnicoAcao.contains("_MB_")) {
                throw new UnsupportedOperationException("A ação " + pNomeUnicoAcao + " não parece ser do tipo Gestão ManagedBean");
            }
        }
        ItfIntegracaoERP erpIntegracao = ERPIntegracaoSistemasApi.RESTFUL.getImplementacaoDoContexto();
        SolicitacaoControllerERP novaSolicitacao = new SolicitacaoControllerERP(
                FabTipoSolicitacaoRestfull.OPCOES.getMetodo(),
                pServico.getHashChavePublica(),
                pCliente.getHashChavePublica(),
                pNomeUnicoAcao, SBCore.getUsuarioLogado(), isUsuarioAdmin(pServico), null,
                null,
                null);
        return novaSolicitacao;
    }

    public static SolicitacaoControllerERP getSolicitacaoInserirPost(ItfSistemaERP pCliente, ItfSistemaERP pServico,
            String pNomeUnicoAcao) {
        throw new UnsupportedOperationException("A solicitação post não foi implementada");
    }

    public static SolicitacaoControllerERP getSolicitacaoAtualizarPut(ItfSistemaERP pCliente, ItfSistemaERP pServico,
            String pNomeUnicoAcao) {
        throw new UnsupportedOperationException("A solicitação put não foi implementada");
    }

    public static SolicitacaoControllerERP getSolicitacaoExcluirDelete(ItfSistemaERP pCliente, ItfSistemaERP pServico,
            String pNomeUnicoAcao) {
        throw new UnsupportedOperationException("A solicitação put não foi implementada");
    }

    public static SolicitacaoControllerERP getSolicitacaoByRequest(HttpServletRequest pRequest) throws ErroTentandoObterTokenAcesso {
        System.out.println("Processando requisição RESTfull:");
        System.out.println(pRequest.getRequestURI());
        TokenAcessoOauthServer token = null;
        ItfUsuario usuario = null;
        String acaoDoSistemaEnum = null;
        String codigoEntidade = getCodigoEntidade(pRequest);
        System.out.println("Código Entidade=" + codigoEntidade);

        String atributoEntidade = getAtributoEntidade(pRequest);
        System.out.println("Atributo=" + atributoEntidade);
        JsonObject json = null;
        try {
            token = getTokenAcesso(pRequest);
            usuario = getUsuario(token);
        } catch (Throwable t) {
            throw new ErroTentandoObterTokenAcesso("Token de acesso não identificado, certificque a presença do  header Authorization com o valor: Bearer xxxxxmeutokenAlfanumericoregistradoxxxx");
        }
        if (usuario == null) {
            throw new ErroTentandoObterTokenAcesso("Token de acesso não identificado, certificque a presença do  header Authorization com o valor: Bearer xxxxxmeutokenAlfanumericoregistradoxxxx");
        }
        if (!token.isTokenValido()) {
            throw new ErroTentandoObterTokenAcesso("Token de acesso expirou");
        }
        acaoDoSistemaEnum = getNomeSlugAcao(pRequest);
        token.getObjetoJsonResposta();
        String corpoRequisicaoTesxto = getCorpoRequisicao(pRequest);
        if (!UtilSBCoreStringValidador.isNuloOuEmbranco(corpoRequisicaoTesxto)) {
            json = UtilSBCoreJson.getJsonObjectByTexto(corpoRequisicaoTesxto);
        }
        String metodo = pRequest.getMethod();
        HashMap parametorsRequisicao = new HashMap();
        if (metodo.toUpperCase().equals("GET")) {
            pRequest.getParameterMap().keySet().stream().forEach(atr -> {
                try {
                    parametorsRequisicao.put(atr, URLDecoder.decode(pRequest.getParameterMap().get(atr)[0], StandardCharsets.UTF_8.toString()));
                    if (parametorsRequisicao.get(atr) instanceof String) {
                        parametorsRequisicao.put(atr, parametorsRequisicao.get(atr).toString().replace("\"", ""));
                    }
                } catch (Throwable ex) {
                    Logger.getLogger(UtilSBRestful.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        ItfIntegracaoERP servicoRestful = ERPIntegracaoSistemasApi.RESTFUL.getImplementacaoDoContexto();
        ItfSistemaERPLocal servidor = servicoRestful.getSistemaAtual();

        SolicitacaoControllerERP solicitacao = new SolicitacaoControllerERP(
                pRequest.getMethod(),
                servidor.getHashChavePublica(),
                token.getClient_id(), acaoDoSistemaEnum, usuario, isUsuarioAdmin(servidor), codigoEntidade, atributoEntidade, json, parametorsRequisicao);

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

    public static String buildTextoJsonResposta(ItfRespostaAcaoDoSistema pResposta) {
        ItfIntegracaoERP erpIntegracao = ERPIntegracaoSistemasApi.RESTFUL.getImplementacaoDoContexto();

        JsonObjectBuilder jsonRespconstrutor = UtilSBCoreJsonRest.getRespostaJsonBuilderBase(pResposta.isSucesso(), pResposta.getResultado(), pResposta.getMensagens());

        if (pResposta.getRetorno() instanceof JsonObject) {
            jsonRespconstrutor.add("retorno", (JsonObject) pResposta.getRetorno());
        } else if (pResposta.getRetorno() instanceof List) {
            List lista = (List) pResposta.getRetorno();
            JsonArrayBuilder array = Json.createArrayBuilder();
            for (Object item : lista) {
                if (item instanceof ItfBeanSimples) {
                    JsonObject itemJson = erpIntegracao.gerarConversaoObjetoToJson((ItfBeanSimples) item);
                    if (itemJson.containsKey("@id")) {
                        JsonObjectBuilder itemEdicao = Json.createObjectBuilder(itemJson);
                        itemEdicao.remove("@id");
                        itemJson = itemEdicao.build();
                    }

                    array.add(itemJson);
                }
            }
            jsonRespconstrutor.add("retorno", array);
            String repostaTexto = UtilSBCoreJson.getTextoByJsonObjeect(jsonRespconstrutor.build());
            return repostaTexto;
        } else if (pResposta.getRetorno() instanceof ItfBeanSimples) {
            JsonObject retorno = erpIntegracao.gerarConversaoObjetoToJson((ItfBeanSimples) pResposta.getRetorno());
            jsonRespconstrutor.add("retorno", retorno);
        } else {
            if (pResposta.getRetorno() instanceof Integer) {
                jsonRespconstrutor.add("retorno", (int) pResposta.getRetorno());
            }
            if (pResposta.getRetorno() instanceof Long) {
                jsonRespconstrutor.add("retorno", (long) pResposta.getRetorno());
            }
            if (pResposta.getRetorno() instanceof String) {
                jsonRespconstrutor.add("retorno", (String) pResposta.getRetorno());
            }
            if (pResposta.getRetorno() instanceof Double) {
                jsonRespconstrutor.add("retorno", (Double) pResposta.getRetorno());
            }
            if (pResposta.getRetorno() instanceof ItfFabrica) {
                ItfFabrica item = (ItfFabrica) pResposta.getRetorno();
                JsonObject retorno = erpIntegracao.gerarConversaoObjetoToJson((ItfBeanSimples) item.getRegistro());
                jsonRespconstrutor.add("retorno", retorno);
            }

        }

        if (UtilSBCoreStringValidador.isNuloOuEmbranco(pResposta.getUrlDestino())) {
            jsonRespconstrutor.add("urlDestino", JsonValue.NULL);
        } else {
            jsonRespconstrutor.add(SLUGPUBLICACAOSERVLET, BigDecimal.ONE);
        }

        String respostaTexto = UtilSBCoreJson.getTextoByJsonObjeect(jsonRespconstrutor.build());
        return respostaTexto;

    }

}
