/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.integracoes.restInterprestfull.implementacao;

import br.org.coletivoJava.fw.api.erp.erpintegracao.contextos.ERPIntegracaoSistemasApi;
import br.org.coletivoJava.fw.api.erp.erpintegracao.servico.ItfIntegracaoERP;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.UtilSBRestful;
import com.google.common.collect.Lists;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilCRCJson;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilCRCStringValidador;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.ComoFabricaIntegracaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.RespostaWebServiceSimples;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.AcaoApiIntegracaoComOauthAbstrato;
import com.super_bits.modulosSB.SBCore.modulos.Mensagens.FabMensagens;
import com.super_bits.modulosSB.SBCore.modulos.erp.ItfSistemaERP;
import com.super_bits.modulosSB.SBCore.modulos.erp.SolicitacaoControllerERP;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import java.util.ArrayList;
import java.util.List;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ComoUsuario;

/**
 *
 * @author sfurbino
 */
public abstract class IntegracaoResfullPadrao extends
        AcaoApiIntegracaoComOauthAbstrato {

    protected SolicitacaoControllerERP solicitacao;
    private ItfSistemaERP dadosServico;

    public IntegracaoResfullPadrao(String pTipoApicacao, ComoFabricaIntegracaoRest pIntegracaoEndpoint, FabTipoAgenteClienteApi pTipoAgente, ComoUsuario pUsuario, Object... pParametros) {
        super(pTipoApicacao, pIntegracaoEndpoint,
                pTipoAgente, pUsuario, pParametros);
    }

    @Override
    protected void executarAcao() {
        SolicitacaoControllerERP solicitacaoParametro = getSoliciatacao();

        String pNomeAcao = solicitacaoParametro.getAcaoStrNomeUnico();
        parametros = Lists.newArrayList(pNomeAcao);
        parametros.addAll(gerarParametrosResFullPelaSolicitacao());
        super.executarAcao();

    }

    protected List<Object> gerarParametrosResFullPelaSolicitacao() {
        if (SBCore.isEmModoDesenvolvimento()) {
            System.out.println("implemente este método para dicionar parametos a partir dos parametros iniciais");
        }
        return new ArrayList<>();
    }

    public ItfSistemaERP getDadoServico() {
        if (dadosServico == null) {
            ItfIntegracaoERP integracao = ERPIntegracaoSistemasApi.RESTFUL.getImplementacaoDoContexto();
            dadosServico = integracao.getSistemaByHashChavePublica(getIdTipoAplicacao());

        }
        return dadosServico;
    }

    public SolicitacaoControllerERP getSoliciatacao() {

        if (solicitacao == null) {

            if (!(parametros.get(0) instanceof SolicitacaoControllerERP)) {
                throw new UnsupportedOperationException("O único parâmetro aceitável é  " + SolicitacaoControllerERP.class.getSimpleName() + " utlize a classe " + UtilSBRestful.class.getSimpleName() + " para instanciar um parâmetro");
            }

            solicitacao = (SolicitacaoControllerERP) parametros.get(0);
        }
        return solicitacao;
    }

    @Override
    public String gerarCorpoRequisicao() {
        return getSoliciatacao().getCorpoParametros();
    }

    @Override
    protected RespostaWebServiceSimples gerarRespostaTratamentoFino(RespostaWebServiceSimples pRespostaWSSemTratamento) {
        if (!UtilCRCStringValidador.isNuloOuEmbranco(pRespostaWSSemTratamento.getRetorno())) {
            pRespostaWSSemTratamento.getMensagens();
            try {
                JsonObject respostaJson = UtilCRCJson.getJsonObjectByTexto(pRespostaWSSemTratamento.getRetorno().toString());
                if (respostaJson.containsKey("resultado")) {

                    String resultado = respostaJson.getString("resultado");
                    JsonArray mensagensJson = respostaJson.getJsonArray("mensagem");
                    mensagensJson.stream().forEach(msg -> {
                        JsonObject mensagemJson = msg.asJsonObject();
                        String tipo = mensagemJson.getString("tipoMensagem");
                        if (tipo.equals(FabMensagens.ERRO.toString())
                                || tipo.equals(FabMensagens.ERRO_FATAL.toString())) {
                            pRespostaWSSemTratamento.addErro(msg.asJsonObject().getString("mensagem"));
                        } else {
                            pRespostaWSSemTratamento.addAviso(msg.asJsonObject().getString("mensagem"));

                        }

                    });

                }
            } catch (Throwable t) {
                //SErvidor não respondeu com json resposta operação
                if (SBCore.isEmModoDesenvolvimento()) {
                    System.out.println("O webservice não respondeu com o json de resposta para ação executada em " + getUrlServidor());
                }
            }

            System.out.println(pRespostaWSSemTratamento.getResultado());

        }
        return pRespostaWSSemTratamento;

    }

    @Override
    public String getUrlServidor() {
        return getDadoServico().getUrlPublicaEndPoint();
    }

}
