/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao;

import br.org.coletivoJava.fw.api.erp.erpintegracao.contextos.ERPIntegracaoSistemasApi;
import org.coletivojava.fw.api.objetoNativo.controller.sistemaErp.ItfSistemaErp;
import br.org.coletivoJava.fw.api.erp.erpintegracao.servico.ItfIntegracaoERP;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

/**
 *
 * @author sfurbino
 */
public class SolicitacaoControllerERP {

    private final String nomeUnicoAcao;
    private final String corpoParametros;
    private final ItfSistemaErp erpServico;
    private final ItfSistemaErp erpCliente;
    private final JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();

    public SolicitacaoControllerERP(ItfSistemaErp pErpServico, String pNomeUnicoAcao) {
        this(pErpServico, pNomeUnicoAcao, null);
    }

    public SolicitacaoControllerERP(ItfSistemaErp pErpServico, String pNomeUnicoAcao, JsonObject pParametros) {
        erpServico = pErpServico;
        if (pParametros == null) {
            corpoParametros = "";
        } else {
            corpoParametros = pParametros.toString();
        }

        ItfIntegracaoERP erpRestfull = (ItfIntegracaoERP) ERPIntegracaoSistemasApi.RESTFUL.getImplementacaoDoContexto();
        erpCliente = erpRestfull.getSistemaAtual();
        nomeUnicoAcao = pNomeUnicoAcao;
    }

    public SolicitacaoControllerERP(JsonObject pjson) {
        erpServico = null;
        corpoParametros = null;
        ItfIntegracaoERP erpRestfull = null;
        erpCliente = null;
        nomeUnicoAcao = null;
    }

    public String getNomeUnicoAcao() {
        return nomeUnicoAcao;
    }

    public ItfSistemaErp getErpCliente() {
        return erpCliente;
    }

    public String getCorpoParametros() {
        return corpoParametros;
    }

    public ItfSistemaErp getErpServico() {
        return erpServico;
    }

    public JsonObject getSolicitacaoJson() {
        jsonBuilder.add("nomeUnicoAcao", nomeUnicoAcao);
        jsonBuilder.add("corpoParametros", corpoParametros);
        jsonBuilder.add("erpServico", erpServico.getComoJson());
        jsonBuilder.add("erpCliente", erpCliente.getComoJson());
        return jsonBuilder.build();
    }

}
