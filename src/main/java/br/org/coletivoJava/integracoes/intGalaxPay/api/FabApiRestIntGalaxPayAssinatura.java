/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.integracoes.intGalaxPay.api;

import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.ItfFabricaIntegracaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.FabTipoConexaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.InfoConsumoRestService;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.servicoRegistrado.FabTipoAutenticacaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.servicoRegistrado.InfoConfigRestClientIntegracao;
import com.super_bits.modulosSB.SBCore.modulos.ManipulaArquivo.importacao.FabTipoArquivoImportacao;

/**
 *
 * @author desenvolvedorninja01
 * @since 30/01/2020
 * @version 1.0
 */
@InfoConfigRestClientIntegracao(enderecosDocumentacao = "https://docs.galaxpay.com.br/",
        tipoAutenticacao = FabTipoAutenticacaoRest.CHAVE_ACESSO_METODOLOGIA_PROPRIA,
        nomeIntegracao = FabConfigApiGalaxyPay.NOME_INTEGRACAO,
        configuracao = FabConfigApiGalaxyPay.class
)
public enum FabApiRestIntGalaxPayAssinatura implements ItfFabricaIntegracaoRest {

    @InfoConsumoRestService(getPachServico = "/subscriptions?startAt=0&limit=100&customerGalaxPayIds={0}",
            tipoConexao = FabTipoConexaoRest.GET,
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            parametrosGet = {"documents"},
            urlDocumentacao = "https://docs.galaxpay.com.br/subscriptions/list",
            adicionarAutenticacaoBearer = true)
    ASSINATURAS_DO_CLIENTE,
    @InfoConsumoRestService(getPachServico = "/subscriptions?startAt={0}&limit={1}",
            tipoConexao = FabTipoConexaoRest.GET,
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            parametrosGet = {"documents"},
            urlDocumentacao = "https://docs.galaxpay.com.br/subscriptions/list",
            adicionarAutenticacaoBearer = true)
    ASSINATURAS_DE_CLIENTES;

}
