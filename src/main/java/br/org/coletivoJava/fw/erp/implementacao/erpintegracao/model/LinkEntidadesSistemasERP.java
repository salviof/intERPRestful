/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model;

import com.super_bits.modulosSB.Persistencia.registro.persistidos.EntidadeSimplesORM;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilCRCReflexaoObjeto;
import com.super_bits.modulosSB.SBCore.modulos.erp.ItfSistemaERP;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoCampo;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoObjetoSB;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.campo.FabTipoAtributoObjeto;
import com.super_bits.modulosSB.SBCore.modulos.objetos.entidade.basico.ComoEntidadeSimples;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author salvio
 */
@Entity
@InfoObjetoSB(plural = "Links de entidades", tags = "Link de entidade")
public class LinkEntidadesSistemasERP extends EntidadeSimplesORM {

    public LinkEntidadesSistemasERP() {
    }

    public LinkEntidadesSistemasERP(ItfSistemaERP sistemaRemoto, ComoEntidadeSimples pBeanSimples, String pCodigoSistemaRemoto) {
        this.sistemaRemoto = (SistemaERPConfiavel) sistemaRemoto;
        this.entidade = UtilCRCReflexaoObjeto.getClassExtraindoProxy(pBeanSimples.getClass().getSimpleName()).getSimpleName();
        this.codigoSistemaRemoto = pCodigoSistemaRemoto;
        this.codigoInterno = String.valueOf(pBeanSimples.getId());
        this.codigoIdentificador = entidade + this.codigoInterno;
    }

    public LinkEntidadesSistemasERP(ItfSistemaERP sistemaRemoto, ComoEntidadeSimples pBeanSimples) {
        this.sistemaRemoto = (SistemaERPConfiavel) sistemaRemoto;
        this.entidade = UtilCRCReflexaoObjeto.getClassExtraindoProxy(pBeanSimples.getClass().getSimpleName()).getSimpleName();
        this.codigoInterno = String.valueOf(pBeanSimples.getId());
        this.codigoIdentificador = entidade + codigoInterno;
    }

    @Id
    @InfoCampo(tipo = FabTipoAtributoObjeto.ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String codigoIdentificador;

    @ManyToOne(targetEntity = SistemaERPConfiavel.class)
    @InfoCampo(tipo = FabTipoAtributoObjeto.OBJETO_DE_UMA_LISTA)
    private SistemaERPConfiavel sistemaRemoto;

    @Column(nullable = false)
    @InfoCampo(tipo = FabTipoAtributoObjeto.TEXTO_SIMPLES)
    private String entidade;

    @Column(nullable = false)
    @InfoCampo(tipo = FabTipoAtributoObjeto.TEXTO_SIMPLES)
    private String codigoSistemaRemoto;

    @Column(nullable = false)
    @InfoCampo(tipo = FabTipoAtributoObjeto.TEXTO_SIMPLES)
    private String codigoInterno;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public SistemaERPConfiavel getSistemaRemoto() {
        return sistemaRemoto;
    }

    public void setSistemaRemoto(SistemaERPConfiavel sistemaRemoto) {
        this.sistemaRemoto = sistemaRemoto;
    }

    public String getEntidade() {
        return entidade;
    }

    public void setEntidade(String entidade) {
        this.entidade = entidade;
    }

    public String getCodigoSistemaRemoto() {
        return codigoSistemaRemoto;
    }

    public void setCodigoSistemaRemoto(String codigoSistemaRemoto) {
        this.codigoSistemaRemoto = codigoSistemaRemoto;
    }

    public String getCodigoInterno() {
        return codigoInterno;
    }

    public void setCodigoInterno(String codigoInterno) {
        this.codigoInterno = codigoInterno;
    }

    public String getCodigoIdentificador() {
        return codigoIdentificador;
    }

    public void setCodigoIdentificador(String codigoIdentificador) {
        this.codigoIdentificador = codigoIdentificador;
    }

}
