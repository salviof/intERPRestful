/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model;

import br.org.coletivoJava.fw.api.erp.erpintegracao.model.ItfSistemaERPLocal;

/**
 *
 * @author sfurbino
 */
public class SistemaERPAtual extends SistemaERPConfiavel implements ItfSistemaERPLocal {

    private String chavePrivada;

    @Override
    public String getChavePrivada() {
        return chavePrivada;
    }

    public void setChavePrivada(String chavePrivada) {
        this.chavePrivada = chavePrivada;
    }

    @Override
    public String getUrlRecepcaoCodigo() {
        return super.getUrlRecepcaoCodigo(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public void setUrlRecepcaoCodigo(String urlRecepcaoCodigo) {
        super.setUrlRecepcaoCodigo(urlRecepcaoCodigo); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

}
