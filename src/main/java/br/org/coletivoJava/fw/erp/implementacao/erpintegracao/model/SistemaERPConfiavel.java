/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model;

import org.coletivojava.fw.api.objetoNativo.controller.sistemaErp.ItfSistemaErp;
import com.super_bits.modulosSB.Persistencia.registro.persistidos.EntidadeSimples;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoCampo;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoObjetoSB;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.campo.FabTipoAtributoObjeto;
import jakarta.json.JsonObject;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author sfurbino
 */
@Entity
@Table(indexes = {
    @Index(name = "CHAVEPUBLICA", columnList = "chavePublica")})
@InfoObjetoSB(tags = "Sistema ERP Confiável", plural = "Sistemas Confiáveis")
public class SistemaERPConfiavel extends EntidadeSimples implements ItfSistemaErp {

    @Id
    @InfoCampo(tipo = FabTipoAtributoObjeto.ID)
    private int id;

    @InfoCampo(tipo = FabTipoAtributoObjeto.AAA_NOME)
    private String nome;

    @InfoCampo(tipo = FabTipoAtributoObjeto.SITE)
    @Column(nullable = false)
    private String dominio;

    @InfoCampo(tipo = FabTipoAtributoObjeto.URL)
    @Column(nullable = false)
    private String urlRecepcaoCodigo;

    @Column(length = 8000)
    private String chavePublica;

    private String hashChavePublica;

    @Override
    public String getDominio() {
        return dominio;
    }

    @Override
    public String getChavePublica() {
        return chavePublica;
    }

    @Override
    public String getUrlRecepcaoCodigo() {
        return urlRecepcaoCodigo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDominio(String dominio) {
        this.dominio = dominio;
    }

    public void setUrlRecepcaoCodigo(String urlRecepcaoCodigo) {
        this.urlRecepcaoCodigo = urlRecepcaoCodigo;
    }

    public void setChavePublica(String chavePublica) {
        this.chavePublica = chavePublica;
        hashChavePublica = String.valueOf(chavePublica.hashCode());
    }

    @Override
    public String getHashChavePublica() {
        return hashChavePublica;
    }

    public void setHashChavePublica(String hashChavePublica) {
        this.hashChavePublica = hashChavePublica;
    }

    @Transient
    private String comoJson;

    @Override
    public JsonObject getComoJson() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
