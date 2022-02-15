/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.token;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token.TokenDeAcessoExternoDinamico;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.Json;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author sfurbino
 */
public class TokenAcessoOauthServer extends TokenDeAcessoExternoDinamico {

    private String chavePublicaAplicativoConfiavel;
    private String identificacaoAgenteDeAcesso;
    private JsonObject objetoJsonArmazenamento;
    private JsonObject objetoJsonResposta;
    private String refresh_token;
    private String scope;

    public TokenAcessoOauthServer(String pCodigoToken, String pRefresh_token, Date pDataHoraExipira, String client_id, String pIdentigicadorAgente) {
        super(pCodigoToken, pDataHoraExipira);
        refresh_token = pRefresh_token;
        scope = pIdentigicadorAgente;
    }

    public TokenAcessoOauthServer(JsonObject pObjetoJsonArmazenamento) {
        super(pObjetoJsonArmazenamento.getString("token"), new Date(pObjetoJsonArmazenamento.getJsonNumber("validade").longValue()));
        chavePublicaAplicativoConfiavel = pObjetoJsonArmazenamento.getString("chavePublicaAplicativoConfiavel");
        identificacaoAgenteDeAcesso = pObjetoJsonArmazenamento.getString("identificacaoAgenteDeAcesso");
        scope = pObjetoJsonArmazenamento.getString("scope");
    }

    public JsonObject getComoJsonResposta() {
        if (objetoJsonResposta == null) {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

        }
        return objetoJsonResposta;
    }

    public String getChavePublicaAplicativoConfiavel() {
        return chavePublicaAplicativoConfiavel;
    }

    public String getIdentificacaoAgenteDeAcesso() {
        return identificacaoAgenteDeAcesso;
    }

    public JsonObject getObjetoJsonArmazenamento() {
        if (objetoJsonArmazenamento == null) {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("refresh_token", refresh_token);
            objectBuilder.add("access_token", getToken());
            objectBuilder.add("dataHoraExpirarToken", Long.toString(getDataHoraExpira().getTime()));
            objectBuilder.add("scope", SBCore.getUsuarioLogado().getEmail());
            objetoJsonArmazenamento = objectBuilder.build();
        }

        return objetoJsonArmazenamento;

    }

    public JsonObject getObjetoJsonResposta() {
        return objetoJsonResposta;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

}
