package br.org.coletivoJava.integracoes.restInterprestfull.implementacao;

import br.org.coletivoJava.fw.api.erp.erpintegracao.contextos.ERPIntegracaoSistemasApi;
import br.org.coletivoJava.fw.api.erp.erpintegracao.model.ItfSistemaERPAtual;
import org.coletivojava.fw.api.objetoNativo.controller.sistemaErp.ItfSistemaErp;
import br.org.coletivoJava.fw.api.erp.erpintegracao.servico.ItfIntegracaoERP;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.servletOauthServer.FabTipoRequisicaoOauthServer;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.servletOauthServer.ServletOauth2Server;
import br.org.coletivoJava.integracoes.restInterprestfull.api.InfoIntegracaoRestInterprestfullRestfull;
import br.org.coletivoJava.integracoes.restInterprestfull.api.FabIntApiRestIntegracaoERPRestfull;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreCriptoRSA;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreDataHora;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.FabTipoConexaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.oauth.InfoTokenOauth2;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token.ItfTokenDeAcessoExterno;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token.ItfTokenGestaoOauth;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.ChamadaHttpSimples;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.gestaoToken.GestaoTokenOath2Base;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfUsuario;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * https://datatracker.ietf.org/doc/html/rfc6749
 *
 * @author sfurbino
 */
@InfoIntegracaoRestInterprestfullRestfull(tipo = FabIntApiRestIntegracaoERPRestfull.OAUTH_VALIDAR_CREDENCIAL)
public class GestaoTokenRestInterprestfull extends GestaoTokenOath2Base implements ItfTokenGestaoOauth {

    private final String chavePublicaLocal;
    private final String chavePrivadaLocal;
    private final String chavePublicaRemoto;

    private final String urlServidorApiRest;
    private final ERPIntegracaoSistemasApi erpIntegracao = ERPIntegracaoSistemasApi.RESTFUL;
    private final ItfIntegracaoERP restFulERP = erpIntegracao.getImplementacaoDoContexto();
    private final String urlObterCodigoSolicitacao;
    private final String urlRetornoReceberCodigoSolicitacao;
    private final String urlRetornoSucessoObterToken;

    public GestaoTokenRestInterprestfull(
            final FabTipoAgenteClienteApi pTipoAgente, final ItfUsuario pUsuario, String pDominio) {
        super(FabIntApiRestIntegracaoERPRestfull.class, pTipoAgente, pUsuario, pDominio);
        ItfSistemaErp sistemaRemoto = restFulERP.getSistemaByDominio(pDominio);
        ItfSistemaERPAtual sistemaLocal = restFulERP.getSistemaAtual();
        chavePublicaLocal = sistemaLocal.getChavePublica();
        chavePrivadaLocal = sistemaLocal.getChavePrivada();
        chavePublicaRemoto = sistemaRemoto.getChavePublica();
        ItfSistemaERPAtual sistemaAtual = restFulERP.getSistemaAtual();
        urlServidorApiRest = sistemaRemoto.getUrlRecepcaoCodigo();
        urlRetornoReceberCodigoSolicitacao = sistemaAtual.getUrlRecepcaoCodigo();
        urlObterCodigoSolicitacao = sistemaRemoto.getDominio() + "/" + ServletOauth2Server.SLUGPUBLICACAOSERVLET
                + "/" + FabTipoRequisicaoOauthServer.OBTER_CODIGO_DE_AUTORIZACAO.toString()
                + "/" + sistemaRemoto.getChavePublica().hashCode()
                + "/" + chavePublicaLocal.hashCode()
                + "/" + URLEncoder.encode(urlRetornoReceberCodigoSolicitacao)
                + "/" + pUsuario.getEmail();
        urlRetornoSucessoObterToken = sistemaAtual.getDominio();

    }

    @Override
    protected ChamadaHttpSimples gerarChamadaTokenObterChaveAcesso() {

        if (codigoSolicitacao != null) {
            try {

                String codigoCriptogrado = UtilSBCoreCriptoRSA.getTextoCriptografado(codigoSolicitacao, chavePublicaRemoto);
                ChamadaHttpSimples chamada = new ChamadaHttpSimples();
                chamada.setTipoConexao(FabTipoConexaoRest.POST);
                JsonObjectBuilder jsonEnvioCodigoAcesso = Json.createObjectBuilder();
                jsonEnvioCodigoAcesso.add("grant_type", FabTipoRequisicaoOauthServer.OBTER_CODIGO_DE_AUTORIZACAO.toString());
                jsonEnvioCodigoAcesso.add("client_id", chavePublicaLocal.hashCode());
                jsonEnvioCodigoAcesso.add("code", codigoCriptogrado);
                jsonEnvioCodigoAcesso.add("redirect_uri", urlRetornoSucessoObterToken);
                JsonObject jsonPostSolicitacao = jsonEnvioCodigoAcesso.build();
                chamada.setCorpo(jsonPostSolicitacao.toString());
                return chamada;
            } catch (Throwable ex) {
                Logger.getLogger(GestaoTokenRestInterprestfull.class.getName()).log(Level.SEVERE, "Falha gerando chamada para obter token", ex);
                return null;
            }

        }
        return null;
    }

    @Override
    public boolean validarToken() {
        if (getTokenCompleto() == null) {
            return false;
        }
        return getTokenCompleto().isTokenValido();
    }

    @Override
    public ItfTokenDeAcessoExterno extrairToken(org.json.simple.JSONObject pJson) {
        String tk = (String) pJson.get("access_token");
        InfoTokenOauth2 tokenGerado = new InfoTokenOauth2(tk);
        tokenGerado.setTokenRefresh((String) pJson.get("refresh_token"));
        String expiraStr = String.valueOf(pJson.get("dataHoraExpirarToken"));
        tokenGerado.setDataHoraExpirarToken(new Date(Long.valueOf(expiraStr)));
        return tokenGerado;
    }

    @Override
    public boolean armazenarRespostaToken(String pToken) {

        JSONParser parser = new JSONParser();
        org.json.simple.JSONObject respostaJson;
        try {
            respostaJson = (org.json.simple.JSONObject) parser.parse(pToken);
            Date dataHora = UtilSBCoreDataHora.incrementaSegundos(new Date(), Integer.parseInt(respostaJson.get("expires_in").toString()));
            respostaJson.put("dataHoraExpirarToken", String.valueOf(dataHora.getTime()));

            return super.armazenarRespostaToken(respostaJson.toJSONString()); //chamada super do metodo (implementação classe pai)
        } catch (ParseException ex) {

            return false;
        }

    }

    @Override
    public String getUrlObterCodigoSolicitacao() {
        return urlObterCodigoSolicitacao;
    }

    @Override
    public String getUrlRetornoReceberCodigoSolicitacao() {
        return urlRetornoReceberCodigoSolicitacao;
    }

    @Override
    public String getUrlServidorApiRest() {
        return urlServidorApiRest;
    }

    @Override
    protected String gerarUrlAutenticaoObterCodigoSolicitacaoToken() {
        return urlObterCodigoSolicitacao;
    }

    @Override
    protected String gerarUrlRetornoSucessoGeracaoTokenDeAcesso() {
        return urlRetornoSucessoObterToken;
    }

}
