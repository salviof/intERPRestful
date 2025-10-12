/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.servletOauthServer.model;

import java.util.Date;

/**
 *
 * @author salvio
 */
public class SolicitacaoTokenOpenId {

    private final Date dataHoraCriacao = new Date();
    private final String nonce;
    private final String clientID;
    private final String codigoConsecao;
    private final Long idUsuarioCRM;

    public SolicitacaoTokenOpenId(String nonce, String clientID, String codigoConsecao, Long pIdUsuarioCRM) {
        this.nonce = nonce;
        this.clientID = clientID;
        this.codigoConsecao = codigoConsecao;
        this.idUsuarioCRM = pIdUsuarioCRM;

    }

    public Date getDataHoraCriacao() {
        return dataHoraCriacao;
    }

    public String getNonce() {
        return nonce;
    }

    public String getClientID() {
        return clientID;
    }

    public String getCodigoConsecao() {
        return codigoConsecao;
    }

    public Long getIdUsuarioCRM() {
        return idUsuarioCRM;
    }

}
