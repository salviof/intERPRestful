/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.servletOauthServer;

import br.org.coletivoJava.fw.api.erp.erpintegracao.contextos.ERPIntegracaoSistemasApi;
import br.org.coletivoJava.fw.api.erp.erpintegracao.model.ItfSistemaERPAtual;
import org.coletivojava.fw.api.objetoNativo.controller.sistemaErp.ItfSistemaErp;
import br.org.coletivoJava.fw.api.erp.erpintegracao.servico.ItfIntegracaoERP;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.MapaTokensGerenciadosConcessaoOauth;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.SistemaERPAtual;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.token.TokenAcessoOauthServer;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.token.TokenConcessaoOauthServer;
import br.org.coletivoJava.integracoes.restInterprestfull.implementacao.GestaoTokenRestInterprestfull;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreCriptoRSA;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreJson;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.UtilSBApiRestClient;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfUsuario;
import com.super_bits.modulosSB.webPaginas.controller.servletes.urls.UrlInterpretada;
import com.super_bits.modulosSB.webPaginas.controller.servletes.util.UtilFabUrlServlet;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.net.URLDecoder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 *
 * https://datatracker.ietf.org/doc/html/rfc6749
 *
 * @author sfurbino
 */
public class ServletOauth2Server extends HttpServlet implements Serializable {

    public static final String SLUGPUBLICACAOSERVLET = "oauth2_service";
    private static final ItfIntegracaoERP integracaoEntreSistemas = ERPIntegracaoSistemasApi.RESTFUL.getImplementacaoDoContexto();

    @Override
    public void doGet(HttpServletRequest requisicao, HttpServletResponse resp) throws ServletException, IOException {

        UrlInterpretada parametrosDeUrl;
        try {

            parametrosDeUrl = UtilFabUrlServlet.getUrlInterpretada(FabUrlOauth2Server.class, requisicao);

        } catch (Throwable t) {
            resp.getWriter().append("PARAMETROS DE ACESSO INCORRETOS, VERIFIQUE A DOCUMENTAÇÃO DA CLASSE " + FabUrlOauth2Server.class.getSimpleName());
            return;
        }
        String hashChave = parametrosDeUrl.getValorComoString(FabUrlOauth2Server.CHAVE_PUBLICA_ID_CLIENTE);
        ItfSistemaErp sistemaSolicitante = integracaoEntreSistemas.getSistemaByHashChavePublica(hashChave);
        if (sistemaSolicitante == null) {
            resp.getWriter().append("ACESSO NEGADO, A CHAVE PÚBLICA DO SISTEMA SOLICITANTE NÃO FOI REGISTRADA");
            return;
        }

        if (sistemaSolicitante == null) {
            resp.getWriter().append("ACESSO NEGADO- CHAVE PÚBLICA NÃO ENCONTRADA, VOCÊ PRECISA CADASTRAR A CHAVE PÚBLICA DO APLICATIVO COMO CONFIÁVEL");
            return;
        }

        //Verifica se a origem é vinda do dominio do sistema solicitante
        String dominioDoSistema = sistemaSolicitante.getDominio();
        String dominioDaRequisicao = requisicao.getHeader("origin");

        if (dominioDaRequisicao == null || !dominioDoSistema.equals(dominioDaRequisicao)) {
            resp.getWriter().append("ACESSO NEGADO, A ORIGEM DA REQUISIÇÃO DIVERGE DA ORIEM AUTORIZADA");
            return;
        }
        String emailDoEscopo = parametrosDeUrl.getValorComoString(FabUrlOauth2Server.ESCOPO);

        ItfUsuario pUsuario = SBCore.getServicoPermissao().getUsuarioByEmail(emailDoEscopo);

        if (emailDoEscopo == null) {
            resp.getWriter().append("ACESSO NEGADO IMPOSSÍVEL RECONHECER O USUÁRIO VERIFIQUE SUA CHAVE PRIVADA");
            return;
        }

        if (pUsuario == null) {
            resp.getWriter().append("ACESSO NEGADO O USUÁRIO " + emailDoEscopo + " NÃO FOI ENCONTRADO NO SISTEMA");
            return;
        }

        if (pUsuario.equals(SBCore.getUsuarioLogado())) {

            TokenConcessaoOauthServer tokenConcessaodeAcesso = MapaTokensGerenciadosConcessaoOauth.gerarNovoTokenCocessaoDeAcesso(sistemaSolicitante, pUsuario);
            String url = URLDecoder.decode(parametrosDeUrl.getValorComoString(FabUrlOauth2Server.REDIRECT_URI));
            ItfIntegracaoERP erp = ERPIntegracaoSistemasApi.RESTFUL.getImplementacaoDoContexto();
            ItfSistemaERPAtual sistemaAutual = (SistemaERPAtual) erp.getSistemaAtual();
            url = url + "?tipoAplicacao=" + sistemaAutual.getHashChavePublica() + "&code=" + tokenConcessaodeAcesso.getToken();
            resp.getWriter().append("<script> windows.location='" + url + "'</script>");
            resp.sendRedirect(url);
        } else {
            if (SBCore.getServicoSessao().getSessaoAtual().isIdentificado()) {

                SBCore.getServicoSessao().efetuarLogOut();
            }

            resp.getWriter().append("<form><input value='" + emailDoEscopo + "' > <buton  /> </form>");

        }

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        StringBuffer stringBuffer = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null) {
                stringBuffer.append(line);
            }
        } catch (Exception e) {
            /*report an error*/ }

        try {
            String jsonSTR = stringBuffer.toString();
            JsonReader jsonReader = Json.createReader(new StringReader(jsonSTR));
            JsonObject json = jsonReader.readObject();
            String codigoCripto = json.getString("code");
            String hashChavePublicaSolicitante = json.getString("client_id");
            ItfSistemaErp sistemaSolicitante = integracaoEntreSistemas.getSistemaByHashChavePublica(hashChavePublicaSolicitante);
            ItfIntegracaoERP erp = ERPIntegracaoSistemasApi.RESTFUL.getImplementacaoDoContexto();
            ItfSistemaERPAtual sistemaAutual = (SistemaERPAtual) erp.getSistemaAtual();

            String codigoDescriptografado = UtilSBCoreCriptoRSA.getTextoDescriptografado(codigoCripto, sistemaSolicitante.getChavePublica());

            TokenConcessaoOauthServer tokenConcessao = MapaTokensGerenciadosConcessaoOauth.loadTokenConcessaoExistente(sistemaSolicitante, codigoDescriptografado);
            TokenAcessoOauthServer tokenAcesso = MapaTokensGerenciadosConcessaoOauth.gerarNovoTokenDeAcesso(codigoCripto, hashChavePublicaSolicitante, tokenConcessao.getIdentificadorUsuario());
            JsonObject tokenJson = UtilSBCoreJson
                    .getJsonObjectBySequenciaChaveValor("access_token", tokenAcesso.getToken(),
                            "token_type", "Bearer",
                            "scope", tokenConcessao.getIdentificadorUsuario(),
                            "dataHoraExpira", String.valueOf(tokenAcesso.getDataHoraExpira().getTime()),
                            "refresh_token", tokenAcesso.getRefresh_token()
                    );
            // compatível com redirecturl e com conteúdo do post.
            String urlEnvioCodigo = UtilSBApiRestClient.gerarUrlServicoReceberCodigoSolicitacaoPadrao(GestaoTokenRestInterprestfull.class, FabTipoAgenteClienteApi.SISTEMA, "code", sistemaAutual.getDominio());
            String urlEnvioCodigoTeste2 = sistemaAutual.getUrlRecepcaoCodigo();
            String urlRetorno = urlEnvioCodigo + "?tipoAplicacao=" + sistemaAutual.getHashChavePublica() + "&code=" + tokenAcesso.getToken();
            resp.getWriter().append(tokenJson.toString());

        } catch (Throwable e) {
            // crash and burn
            resp.getWriter().append("{erro: 'ACESSO NEGADO, ERRO SECRETO OBTENDO TOKEN'}");

        }

    }

}
