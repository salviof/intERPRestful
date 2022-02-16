/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.teste.servicoTeste;

import br.org.coletivoJava.fw.api.erp.erpintegracao.contextos.ERPIntegracaoSistemasApi;
import br.org.coletivoJava.fw.api.erp.erpintegracao.servico.ItfIntegracaoERP;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.UtilSBRestful;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.ItfRespostaAcaoDoSistema;
import com.super_bits.modulosSB.SBCore.modulos.erp.SolicitacaoControllerERP;
import spark.Spark;

/**
 *
 * @author sfurbino
 */
public class UtilTesteServicoRestfull {

    private static ItfIntegracaoERP erpIntegraca = ERPIntegracaoSistemasApi.RESTFUL.getImplementacaoDoContexto();

    public static void iniciarServico() {
        Spark.port(8066);
        Spark.get("/hello", (req, res) -> "Hello World");

        Spark.options("/controllerERP/*", (req, res) -> {

            SolicitacaoControllerERP solicitacao = UtilSBRestful.getSolicitacaoByRequest(req.raw());
            ItfRespostaAcaoDoSistema resposta = erpIntegraca.getRespostaAcaoDoSistema(solicitacao);
            String respostaStr = UtilSBRestful.buildTextoJsonResposta(resposta);
            return respostaStr;

        });

    }
;

}
