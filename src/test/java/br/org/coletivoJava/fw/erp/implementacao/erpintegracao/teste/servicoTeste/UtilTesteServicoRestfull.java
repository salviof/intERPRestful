/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.teste.servicoTeste;

import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.servletRestfulERP.ServletRestfullERP;
import com.super_bits.modulosSB.SBCore.UtilGeral.UTilSBCoreInputs;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreInputOutputConversoes;
import spark.Spark;

/**
 *
 * @author sfurbino
 */
public class UtilTesteServicoRestfull {

    public static void iniciarServico() {
        Spark.port(8066);
        Spark.get("/hello", (req, res) -> "Hello World");

        Spark.options("/controllerERP/*", (req, res) -> {

            return ServletRestfullERP.buildRespostaOptions(req.raw());

        });

    }
;

}
