/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model;

import br.org.coletivoJava.fw.api.erp.erpintegracao.model.ItfSistemaERPLocal;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoCampo;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoObjetoSB;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.campo.FabTipoAtributoObjeto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 *
 * @author salvio
 */
@Entity
@Table(indexes = {
    @Index(name = "CHAVEPRIVADA", columnList = "chavePrivada")}
)
@InfoObjetoSB(tags = "Sistema ERP Recurso Local", plural = "Sistema Local")
public class SistemaErpChaveLocal extends SistemaERPConfiavel implements ItfSistemaERPLocal {

    public SistemaErpChaveLocal() {
    }

    public SistemaErpChaveLocal(SistemaERPAtual pSistemaLocal) {
        this.chavePrivada = pSistemaLocal.getChavePrivada();
        setChavePublica(pSistemaLocal.getChavePublica());
        setNome(pSistemaLocal.getChavePublica());
        setDominio(pSistemaLocal.getDominio());
        setUrlPublicaEndPoint(pSistemaLocal.getUrlPublicaEndPoint());
        setUrlRecepcaoCodigo(pSistemaLocal.getUrlRecepcaoCodigo());

    }

    @Column(length = 8000)
    @InfoCampo(tipo = FabTipoAtributoObjeto.AAA_DESCRITIVO, obrigatorio = true)
    private String chavePrivada;

    @Override
    public String getChavePrivada() {
        return chavePrivada;
    }

    public void setChavePrivada(String chavePrivada) {
        this.chavePrivada = chavePrivada;
    }

}
