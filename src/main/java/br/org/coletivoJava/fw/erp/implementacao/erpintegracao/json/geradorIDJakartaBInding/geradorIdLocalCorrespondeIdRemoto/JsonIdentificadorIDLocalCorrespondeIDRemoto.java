/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.json.geradorIDJakartaBInding.geradorIdLocalCorrespondeIdRemoto;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação equivalente a
 *
 * @JsonIdentityInfo(generator =
 * GeradorIdJsonIDLocalTransienteRefIDRemoto.class, property = "@id", scope =
 * RegistroContabilEnvio.class)
 *
 *
 * @author salvio
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE,
    ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonIdentificadorIDLocalCorrespondeIDRemoto {

}
