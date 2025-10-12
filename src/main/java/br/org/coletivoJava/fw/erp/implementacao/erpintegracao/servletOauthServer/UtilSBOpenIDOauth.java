/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.servletOauthServer;

import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.servletOauthServer.model.SolicitacaoTokenOpenId;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author salvio
 */
public abstract class UtilSBOpenIDOauth {

    private static Map<String, SolicitacaoTokenOpenId> MAPA_SOLICITACOES = new ConcurrentHashMap<>();

    public static void armazenarSolicitaca(SolicitacaoTokenOpenId pSolicitacao) {
        MAPA_SOLICITACOES.put(pSolicitacao.getCodigoConsecao(), pSolicitacao);
    }

    public static SolicitacaoTokenOpenId getSolicitacao(String pCodigoConcessao) {
        return MAPA_SOLICITACOES.get(pCodigoConcessao);
    }

}
