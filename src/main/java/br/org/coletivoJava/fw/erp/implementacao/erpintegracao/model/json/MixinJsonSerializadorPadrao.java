/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.json;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 *
 * @author salvio
 */
@JsonSerialize(using = SerializadorPadrao.class)
public interface MixinJsonSerializadorPadrao {

}
