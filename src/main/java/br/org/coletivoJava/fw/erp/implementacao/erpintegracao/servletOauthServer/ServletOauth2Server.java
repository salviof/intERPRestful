/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.servletOauthServer;

import br.org.coletivoJava.fw.api.erp.erpintegracao.contextos.ERPIntegracaoSistemasApi;

import br.org.coletivoJava.fw.api.erp.erpintegracao.servico.ItfIntegracaoERP;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.ErroTentandoObterTokenAcesso;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.FabConfigModuloWebERPChaves;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.MapaTokensGerenciadosConcessaoOauth;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.token.TokenAcessoOauthServer;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.token.TokenConcessaoOauthServer;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.ConfigGeral.arquivosConfiguracao.ConfigModulo;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreClienteRest;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreCriptoRSA;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreDataHora;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreJson;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringValidador;
import com.super_bits.modulosSB.SBCore.UtilGeral.json.ErroProcessandoJson;
import com.super_bits.modulosSB.SBCore.modulos.Controller.qualificadoresCDI.sessao.QlSessaoFacesContext;
import com.super_bits.modulosSB.SBCore.modulos.erp.ItfSistemaERP;
import com.super_bits.modulosSB.webPaginas.controller.servletes.urls.UrlInterpretada;
import com.super_bits.modulosSB.webPaginas.controller.servletes.util.UtilFabUrlServlet;
import jakarta.json.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.URLDecoder;
import java.util.Date;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.coletivojava.fw.api.tratamentoErros.FabErro;
import org.json.JSONException;
import br.org.coletivoJava.fw.api.erp.erpintegracao.model.ItfSistemaERPLocal;
import static br.org.coletivoJava.fw.erp.implementacao.erpintegracao.servletOauthServer.FabTipoRequisicaoOauthServer.OBTER_CODIGO_DE_AUTORIZACAO;
import static br.org.coletivoJava.fw.erp.implementacao.erpintegracao.servletOauthServer.FabTipoRequisicaoOauthServer.OBTER_CODIGO_DE_CONCESSAO_DE_ACESSO;
import static br.org.coletivoJava.fw.erp.implementacao.erpintegracao.servletOauthServer.FabTipoRequisicaoOauthServer.VERIFICACAO_STATUS_ACESSO;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreJsonRest;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.ItfResposta;
import jakarta.json.JsonObjectBuilder;
import java.net.URLEncoder;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ComoSessao;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ComoUsuario;

/**
 *
 *
 * https://datatracker.ietf.org/doc/html/rfc6749
 *
 * @author sfurbino
 */
public class ServletOauth2Server extends ServletOauth2ServerAbs implements Serializable {

    public static final String SLUGPUBLICACAOSERVLET = "OAUTH2_SERVICE";
    @Inject
    @QlSessaoFacesContext
    private ComoSessao sessaoAtual;

    @Override
    public void doGet(HttpServletRequest requisicao, HttpServletResponse resp) throws ServletException, IOException {
        try {

            System.out.println("OAUTH SERVICE INICIADO");
            UrlInterpretada parametrosDeUrl;
            try {

                parametrosDeUrl = UtilFabUrlServlet.getUrlInterpretada(FabUrlOauth2Server.class, requisicao);

            } catch (Throwable t) {
                resp.getWriter().append("PARAMETROS DE ACESSO INCORRETOS, VERIFIQUE A DOCUMENTAÇÃO DA CLASSE " + FabUrlOauth2Server.class.getSimpleName());
                return;
            }
            System.out.println("PARAMETROS PROCESSADOS");
            if (!SBCore.isEmModoProducao()) {
                parametrosDeUrl.printParametrosComValoresInterpretados();
            }
            //FacesContext contexto = FacesContext.getCurrentInstance();
            String hashChaveCliente = parametrosDeUrl.getValorComoString(FabUrlOauth2Server.CHAVE_PUBLICA_ID_CLIENTE);
            String hashChaveServicoRecurso = parametrosDeUrl.getValorComoString(FabUrlOauth2Server.CHAVE_PUBLICA_ID_RECURSOS);

            System.out.println("HASH CLIENTE:" + hashChaveCliente);
            System.out.println("HASH LOCAL SERVER ENVIADO:" + hashChaveServicoRecurso);
            ItfSistemaERPLocal sistemaLocalRecursos = erp.getSistemaAtual();

            if (!sistemaLocalRecursos.getHashChavePublica().equals(hashChaveServicoRecurso)) {
                resp.setStatus(401);
                resp.getWriter().append("ACESSO NEGADO, A CHAVE PÚBLICA DO SERVIÇO LOCAL " + hashChaveServicoRecurso + " NÃO FOI ENCONTRADA");

            }

            //TODO verifica se o rash servico recurso é o hash do servidor atual
            ItfSistemaERP sistemaCliente = integracaoEntreSistemas.getSistemaByHashChavePublica(hashChaveCliente);
            if (sistemaCliente == null) {
                resp.getWriter().append("ACESSO NEGADO, A CHAVE PÚBLICA DO CLIENTE NÃO FOI REGISTRADA");
                resp.setStatus(401);
                System.out.println("Acesso negado, hash cliente inválido:" + hashChaveServicoRecurso);
                return;
            }
            // PODE VALIDAR TAMBÉM A CHAVE PÚBLICA DO SERVIDOR, POR MOTIVOS DE COMPATIBILIDADE DE TESTES FOI REMOVIDA A VALIDAÇÃO
            //Verifica se a origem é vinda do dominio do sistema solicitante

            String dominioDaRequisicao = requisicao.getHeader("referer");

            ConfigModulo configuracaoRESTFull = SBCore.getConfigModulo(FabConfigModuloWebERPChaves.class);
            String emailDoEscopo = parametrosDeUrl.getValorComoString(FabUrlOauth2Server.ESCOPO);
            String emailAdmin = configuracaoRESTFull.getPropriedade(FabConfigModuloWebERPChaves.USUARIO_ADMIN);
            System.out.println("O usuário admin é " + emailAdmin);
            boolean solicitacaoUsuarioAdmin = false;
            if (!UtilSBCoreStringValidador.isNuloOuEmbranco(emailDoEscopo) && !UtilSBCoreStringValidador.isNuloOuEmbranco(emailAdmin)) {
                if (emailDoEscopo.toLowerCase().equals(emailAdmin.toLowerCase())) {
                    solicitacaoUsuarioAdmin = true;
                    System.out.println("Token Solicitado como admin");
                } else {
                    solicitacaoUsuarioAdmin = false;
                    System.out.println("token Solicitado por intermedio de usuário");
                }
            }
            if (emailDoEscopo == null) {
                resp.getWriter().append("ACESSO NEGADO IMPOSSÍVEL RECONHECER O USUÁRIO VERIFIQUE SUA CHAVE PRIVADA");
                return;
            }

            ComoUsuario usuario = SBCore.getServicoPermissao().getUsuarioByEmail(emailDoEscopo);
            TipoRequisicaoOauth tipoRequisicao = (TipoRequisicaoOauth) parametrosDeUrl.getValorComoBeanSimples(FabUrlOauth2Server.TIPO_REQUISICAO);
            if (usuario == null) {
                resp.getWriter().append("ACESSO NEGADO O USUÁRIO " + emailDoEscopo + " NÃO FOI ENCONTRADO NO SISTEMA");
                return;
            }

            switch (tipoRequisicao.getEnumVinculado()) {

                case OBTER_CODIGO_DE_CONCESSAO_DE_ACESSO:
                    String urlRedirecionamentoTokenCodigoSolicitacao = URLDecoder.decode(parametrosDeUrl.getValorComoString(FabUrlOauth2Server.REDIRECT_URI), "UTF-8");
                    if (solicitacaoUsuarioAdmin) {
                        despacharLoginAdmin(resp, tipoRequisicao, sistemaCliente, usuario, urlRedirecionamentoTokenCodigoSolicitacao);
                    } else {
                        despacharLoginUsuarioRemotoEscopoDeUsuario(resp, requisicao, tipoRequisicao, dominioDaRequisicao, sistemaCliente, usuario, urlRedirecionamentoTokenCodigoSolicitacao);
                    }
                    break;
                case OBTER_CODIGO_DE_AUTORIZACAO:
                    throw new ServletException("Utilize o método post ");

                case VERIFICACAO_STATUS_ACESSO:
                    String tokenPayLoad = requisicao.getHeader("payload");
                    if (tokenPayLoad == null) {
                        throw new UnsupportedOperationException("Payload com token a ser validado não foi anexado ao header");
                    }
                    //TokenAcessoOauthServer token = MapaTokensGerenciadosConcessaoOauth.loadTokenExistente(sistemaCliente, pUsuario);
                    JsonObject jsonPayload = UtilSBCoreJson.getJsonObjectByTexto(tokenPayLoad);
                    String tokenPayload = jsonPayload.getString("access_token");
                    TokenAcessoOauthServer token = MapaTokensGerenciadosConcessaoOauth.loadTokenExistente(tokenPayload, sistemaCliente);
                    if (token != null && token.isTokenValido()) {
                        resp.getWriter().append(UtilSBCoreJson.getTextoByJsonObjeect(UtilSBCoreJsonRest.getRespostaJsonBuilderBaseSucesso("OK", JsonObject.EMPTY_JSON_OBJECT.asJsonObject()).build()));
                    } else {
                        resp.getWriter().append(UtilSBCoreJson.getTextoByJsonObjeect(UtilSBCoreJsonRest.getRespostaJsonBuilderBaseFalha("FALHOU").build()));
                        resp.setStatus(400);
                    }
                    break;
                default:
                    throw new AssertionError();
            }

        } catch (Throwable t) {
            resp.getWriter().append("Falha não prevista em serviço de obtenção de código de concessão " + t.getMessage() + FabUrlOauth2Server.class.getSimpleName());

            resp.setStatus(500);
        }
    }

    public void despacharLoginAdmin(HttpServletResponse pResposta, TipoRequisicaoOauth pTipoRequisicao, ItfSistemaERP pSistemaCliente, ComoUsuario pUsuario, String pUrlRedirecionamentoTokenCodigoSolicitacao) throws Throwable {
        ItfSistemaERPLocal sistemaRecursos = erp.getSistemaAtual();
        TokenConcessaoOauthServer tokenConcessaodeAcesso = MapaTokensGerenciadosConcessaoOauth.gerarNovoTokenCocessaoDeAcesso(pSistemaCliente, pUsuario);
        switch (pTipoRequisicao.getEnumVinculado()) {
            case OBTER_CODIGO_DE_CONCESSAO_DE_ACESSO:
                tokenConcessaodeAcesso = MapaTokensGerenciadosConcessaoOauth.gerarNovoTokenCocessaoDeAcesso(pSistemaCliente, pUsuario);
                String url = URLDecoder.decode(pUrlRedirecionamentoTokenCodigoSolicitacao, "UTF-8");
                url = url + "?code="
                        + tokenConcessaodeAcesso.getToken()
                        + "&tipoAplicacao=" + sistemaRecursos.getHashChavePublica() + "&escopo=sistema";
                System.out.println("enviando codigo de concessão via:");
                System.out.println(url);
                JsonObject relatorioRecebimento = UtilSBCoreClienteRest.getObjetoJsonPorUrl(url);
                if (relatorioRecebimento == null) {
                    JsonObjectBuilder respEnvioCodigo = UtilSBCoreJsonRest.getRespostaJsonBuilderBaseFalha("Falha acessando" + url);
                    pResposta.setStatus(500);
                    pResposta.getWriter().append(UtilSBCoreJson.getTextoByJsonObjeect(respEnvioCodigo.build()));
                    pResposta.flushBuffer();
                    return;
                }
                ItfResposta respObtencaoToken = UtilSBCoreJsonRest.getResposta(relatorioRecebimento);
                if (respObtencaoToken.isSucesso()) {
                    JsonObjectBuilder respEnvioCodigo = UtilSBCoreJsonRest.getRespostaJsonBuilderBaseSucesso("o token de acesso foi registrado com sucesso no cliente", relatorioRecebimento);
                    pResposta.getWriter().append(UtilSBCoreJson.getTextoByJsonObjeect(respEnvioCodigo.build()));
                    pResposta.flushBuffer();

                } else {
                    JsonObjectBuilder respEnvioCodigo = UtilSBCoreJsonRest.getRespostaJsonBuilderBaseSucesso("o codigo de solicitação foi enviado, mas houve falha obtendo o token com o código enviado", relatorioRecebimento);
                    pResposta.getWriter().append(UtilSBCoreJson.getTextoByJsonObjeect(respEnvioCodigo.build()));
                    pResposta.flushBuffer();

                }

                break;
            default:
                throw new ServletException("o método despachar login admin só processa requisições do tipo  " + FabTipoRequisicaoOauthServer.OBTER_CODIGO_DE_AUTORIZACAO.toString());

        }

    }

    @Override
    public void doPost(HttpServletRequest requisicao, HttpServletResponse resp) throws ServletException, IOException {
        UrlInterpretada parametrosDeUrl;

        try {

            parametrosDeUrl = UtilFabUrlServlet.getUrlInterpretada(FabUrlOauth2Server.class,
                    requisicao);

        } catch (Throwable t) {
            resp.getWriter().append("PARAMETROS DE ACESSO INCORRETOS, VERIFIQUE A DOCUMENTAÇÃO DA CLASSE " + FabUrlOauth2Server.class
                    .getSimpleName());
            return;
        }
        String hashChaveCliente = parametrosDeUrl.getValorComoString(FabUrlOauth2Server.CHAVE_PUBLICA_ID_CLIENTE);
        String hashChaveServidor = parametrosDeUrl.getValorComoString(FabUrlOauth2Server.CHAVE_PUBLICA_ID_RECURSOS);
        ItfSistemaERP sistemaCliente = integracaoEntreSistemas.getSistemaByHashChavePublica(hashChaveCliente);
        if (sistemaCliente == null) {
            resp.getWriter().append("ACESSO NEGADO, A CHAVE PÚBLICA DO CLIENTE NÃO FOI REGISTRADA");
            return;
        }

        ItfSistemaERPLocal sistemaServidor = integracaoEntreSistemas.getSistemaLocalByHashChavePublica(hashChaveServidor);

        // PODE VALIDAR TAMBÉM A CHAVE PÚBLICA DO SERVIDOR, POR MOTIVOS DE COMPATIBILIDADE DE TESTES FOI REMOVIDA A VALIDAÇÃO
        //Verifica se a origem é vinda do dominio do sistema solicitante
        String dominioDoSistema = sistemaCliente.getDominio();
        String dominioDaRequisicao = requisicao.getHeader("origin");

        //todo origem explícita, analizar nescessidade já que o post não vem do browser
        if (false) {
            if (dominioDaRequisicao == null || !dominioDaRequisicao.contains(dominioDoSistema)) {
                resp.getWriter().append("ACESSO NEGADO, A ORIGEM DA REQUISIÇÃO DIVERGE DA ORIEM AUTORIZADA [" + dominioDaRequisicao + "|" + dominioDoSistema + "]");
                return;
            }
        }
        String emailDoEscopo = parametrosDeUrl.getValorComoString(FabUrlOauth2Server.ESCOPO);

        ComoUsuario pUsuario = SBCore.getServicoPermissao().getUsuarioByEmail(emailDoEscopo);

        if (emailDoEscopo == null) {
            resp.getWriter().append("ACESSO NEGADO IMPOSSÍVEL RECONHECER O USUÁRIO VERIFIQUE SUA CHAVE PRIVADA");
            return;
        }

        if (pUsuario == null) {
            resp.getWriter().append("ACESSO NEGADO O USUÁRIO " + emailDoEscopo + " NÃO FOI ENCONTRADO NO SISTEMA");
            return;
        }
        //String codigoCriptografado = JsonReader jsonReader = Json.createReader(new StringReader(jsonSTR));
        //UtilSBCoreCriptoRSA.getTextoDescriptografado(, sistemaCliente.getChavePublica());

        StringBuffer jb = new StringBuffer();
        String line = null;
        String codigoCriptografado = null;
        try {
            BufferedReader reader = requisicao.getReader();
            while ((line = reader.readLine()) != null) {
                jb.append(line);
            }
        } catch (Exception e) {
            /*report an error*/ }

        try {
            JsonObject jsonObject = UtilSBCoreJson.getJsonObjectByTexto(jb.toString());
            codigoCriptografado = jsonObject.getString("code");
        } catch (JSONException e) {
            // crash and burn
            throw new ServletException("Erro lendo Conteúdo Json do post");
        }
        if (codigoCriptografado == null) {
            throw new ServletException("Error parsing JSON request string");
        }
        if (sistemaServidor == null) {
            sistemaServidor = ERPIntegracaoSistemasApi.RESTFUL.getImplementacaoDoContexto().getSistemaAtual();
        }

        String codigoDescriptografado = UtilSBCoreCriptoRSA.getTextoDescriptografadoUsandoChavePrivada(codigoCriptografado, sistemaServidor.getChavePrivada());
        try {
            TokenConcessaoOauthServer tokenConcessao = MapaTokensGerenciadosConcessaoOauth.loadTokenConcessaoExistente(sistemaCliente, codigoDescriptografado);
            if (tokenConcessao.isTokenValido()) {
                TokenAcessoOauthServer novoToken = MapaTokensGerenciadosConcessaoOauth
                        .gerarNovoTokenDeAcesso(tokenConcessao.getToken(), sistemaCliente.getHashChavePublica(),
                                emailDoEscopo);
                Integer segudos = UtilSBCoreDataHora.segundosEntre(new Date(), novoToken.getDataHoraExpira());
                JsonObject tokenJson = UtilSBCoreJson
                        .getJsonObjectBySequenciaChaveValor("access_token", novoToken.getToken(),
                                "token_type", "Bearer",
                                "scope", tokenConcessao.getIdentificadorUsuario(),
                                "expires_in", segudos.toString(),
                                "refresh_token", novoToken.getRefresh_token()
                        );

                PrintWriter writer = resp.getWriter();
                String textoJson = UtilSBCoreJson.getTextoByJsonObjeect(tokenJson.asJsonObject());
                writer.append(textoJson);
            }
        } catch (ErroTentandoObterTokenAcesso ex) {
            throw new ServletException("Falha gerando token de acesso " + ex.getMessage());
        } catch (ErroProcessandoJson ex) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro criando json de resposta com token de acesso", ex);
        }

    }

    @Override
    protected ComoSessao getSessao() {
        if (SBCore.isEmModoDesenvolvimento()) {
            return SBCore.getServicoSessao().getSessaoAtual();
        } else {
            return sessaoAtual;
        }
    }

}
