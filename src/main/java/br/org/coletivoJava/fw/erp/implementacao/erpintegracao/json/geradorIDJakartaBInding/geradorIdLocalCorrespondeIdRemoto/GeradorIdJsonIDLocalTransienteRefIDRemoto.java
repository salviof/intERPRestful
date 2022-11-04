/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.json.geradorIDJakartaBInding.geradorIdLocalCorrespondeIdRemoto;

import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.json.geradorIDJakartaBInding.GeradorIdJsonGenerico;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfBeanSimples;

/**
 *
 * @author salvio
 */
public class GeradorIdJsonIDLocalTransienteRefIDRemoto extends GeradorIdJsonGenerico {

    private static final long serialVersionUID = 1L;

    public GeradorIdJsonIDLocalTransienteRefIDRemoto() {
        this(ItfBeanSimples.class, -1);
    }

    public GeradorIdJsonIDLocalTransienteRefIDRemoto(Class<?> scope, int fv) {
        super(scope);

    }

    @Override
    public Integer generateId(Object forPojo) {
        return ((ItfBeanSimples) forPojo).getId();
    }

    @Override
    public String toString() {
        return super.toString(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

}
