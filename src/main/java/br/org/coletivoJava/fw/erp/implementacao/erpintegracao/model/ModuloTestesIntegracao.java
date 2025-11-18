/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model;

import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.modulo.ComoFabricaModulo;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoCampo;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.campo.FabTipoAtributoObjeto;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import com.super_bits.modulos.SBAcessosModel.model.ModuloAcaoSistema;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoObjetoSB;
import javax.persistence.Entity;
import com.super_bits.modulosSB.SBCore.modulos.fabrica.ComoFabrica;

/**
 *
 * @author sfurbino
 */
@Entity
@InfoObjetoSB(tags = "Modulo", plural = "Modulos")
public class ModuloTestesIntegracao extends ModuloAcaoSistema {

    @Enumerated(EnumType.ORDINAL)
    @InfoCampo(tipo = FabTipoAtributoObjeto.ENUM_FABRICA, fabricaDeOpcoes = FabModuloTestesIntegracao.class)
    private FabModuloTestesIntegracao moduloFab;

    @Override
    public FabModuloTestesIntegracao getFabricaObjeto() {
        return moduloFab;
    }

    @Override
    public ComoFabricaModulo getEnumVinculado() {
        return moduloFab;
    }

    @Override
    public void setEnumVinculado(ComoFabrica pFabrica) {
        super.setEnumVinculado(pFabrica);
    }

}
