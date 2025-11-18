/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.json.geradorIDJakartaBInding;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ComoEntidadeSimples;

/**
 *
 * @author salvio
 */
public abstract class GeradorIdJsonGenerico extends ObjectIdGenerator<Long> {

    protected final Class<?> _scope;

    protected GeradorIdJsonGenerico(Class<?> scope) {
        _scope = scope;
    }

    @Override
    public final Class<?> getScope() {
        return _scope;
    }

    @Override
    public boolean canUseFor(ObjectIdGenerator<?> gen) {
        return true;
    }

    @Override
    public ObjectIdGenerator<Long> forScope(Class<?> scope) {
        //return (_scope == scope) ? this : new ObjectIdGenerators.IntSequenceGenerator(scope, _nextValue);
        return this;
    }

    @Override
    public ObjectIdGenerator<Long> newForSerialization(Object context) {
        return this;
    }

    @Override
    public IdKey key(Object key) {
        // 02-Apr-2015, tatu: As per [annotations#56], should check for null
        if (key == null) {
            return null;
        }
        return new IdKey(getClass(), _scope, key);
    }

    @Override
    public abstract Long generateId(Object forPojo);
}
