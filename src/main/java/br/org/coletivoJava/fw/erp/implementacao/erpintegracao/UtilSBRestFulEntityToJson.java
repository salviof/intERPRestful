/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao;

import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.json.geradorIDJakartaBInding.geradorIdLocalCorrespondeIdRemoto.GeradorIdJsonIDLocalTransienteRefIDRemoto;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.json.geradorIDJakartaBInding.geradorIdLocalCorrespondeIdRemoto.JsonIdentificadorIDLocalCorrespondeIDRemoto;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.json.geradorIDJakartaBInding.geradorIdLocalCorrespondeIdRemoto.MixLocalCorrespondeRemoto;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.json.MixIgnoreCaseItemSimples;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.json.MixinJsonSerializadorPadrao;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.json.SerializadorPadrao;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreJson;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfBeanSimples;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sfurbino
 */
public class UtilSBRestFulEntityToJson {

    private static Map<Class, String> mapaNomesClassesConversaoToJson = new HashMap<>();
    private static Map<Class, String> mapaNomesClassesJsonToObjeto = new HashMap<>();
    private static Map<Class, Class> mapaConversorEntidadeToJson = new HashMap<>();
    private static Map<Class, Class> mapaConversorJsonToEntidade = new HashMap<>();

    public static JsonObject getJsonFromObjetoGenerico(ItfBeanSimples beanSimples, boolean pUsarIdBeanSimplesComoIdRemoto) {
        if (beanSimples == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();

        SimpleModule module = new SimpleModule();
        module.addSerializer(beanSimples.getClass(), new SerializadorPadrao());
        mapper.registerModule(module);

        String serialized = null;
        try {
            serialized = mapper.writeValueAsString(beanSimples);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(UtilSBRestFulEntityToJson.class.getName()).log(Level.SEVERE, null, ex);
        }
        return UtilSBCoreJson.getJsonObjectByTexto(serialized);
    }

    public static JsonObjectBuilder getJsonBuilderFromObjetoGenerico(ItfBeanSimples beanSimples) {

        return Json.createObjectBuilder(getJsonFromObjetoGenerico(beanSimples, false));
    }

    static class AutoAnotacaoJacksonDefinindoCampoId extends JacksonAnnotationIntrospector {

        public AutoAnotacaoJacksonDefinindoCampoId() {
            System.out.println("Test");

        }

        @Override
        public JsonIgnoreProperties.Value findPropertyIgnorals(Annotated a) {
            Class classe = a.getRawType();

            JsonIgnoreProperties.Value ignorados = JsonIgnoreProperties.Value
                    .forIgnoredProperties("mapaCamposInstanciados",
                            "novoBeanPreparado",
                            "mapaCampoPorAnotacao",
                            "controleCalculo",
                            "instancia",
                            "camposEsperados",
                            "camposInstanciados",
                            "mapaJustificativasExecucaoAcao",
                            "mapaAssistenteLocalizacao",
                            "mapeouTodosOsCampos"
                    );

            return ignorados;

        }

        @Override
        public String findImplicitPropertyName(AnnotatedMember m) {
            String nomePropriedade = super.findImplicitPropertyName(m);
            return nomePropriedade;
        }

        @Override
        public ObjectIdInfo findObjectIdInfo(com.fasterxml.jackson.databind.introspect.Annotated anotacao) {
            JsonIdentityInfo an = anotacao.getAnnotation(JsonIdentityInfo.class);
            JsonIdentificadorIDLocalCorrespondeIDRemoto anotacaoIdLocalCorresponde = anotacao.getAnnotation(JsonIdentificadorIDLocalCorrespondeIDRemoto.class);
            if (anotacaoIdLocalCorresponde != null) {
                return new ObjectIdInfo(
                        PropertyName.construct("@id", null),
                        null,
                        GeradorIdJsonIDLocalTransienteRefIDRemoto.class,
                        null);
            }
            if (an == null) {
                return new ObjectIdInfo(
                        PropertyName.construct("@id", null),
                        null,
                        ObjectIdGenerators.IntSequenceGenerator.class,
                        null);
            } else {
                return super.findObjectIdInfo(anotacao);
                //return new ObjectIdInfo(
                //       PropertyName.construct("@id", null),
                //      an.scope(),
                //      an.generator(),
                //      an.resolver());
            }
        }

    }

    static class AutoAnotacaoCampoIdPadrao extends JacksonAnnotationIntrospector {

        public AutoAnotacaoCampoIdPadrao() {
            System.out.println("Test");

        }

        @Override
        public JsonIgnoreProperties.Value findPropertyIgnorals(Annotated a) {
            Class classe = a.getRawType();

            JsonIgnoreProperties.Value ignorados = JsonIgnoreProperties.Value
                    .forIgnoredProperties("mapaCamposInstanciados",
                            "novoBeanPreparado",
                            "mapaCampoPorAnotacao",
                            "controleCalculo",
                            "instancia",
                            "camposEsperados",
                            "mapaJustificativasExecucaoAcao",
                            "mapaAssistenteLocalizacao",
                            "mapeouTodosOsCampos"
                    );

            return ignorados;

        }

        @Override
        public String findImplicitPropertyName(AnnotatedMember m) {
            String nomePropriedade = super.findImplicitPropertyName(m);
            return nomePropriedade;
        }

        @Override
        public ObjectIdInfo findObjectIdInfo(com.fasterxml.jackson.databind.introspect.Annotated anotacao) {

            return super.findObjectIdInfo(anotacao);

        }
    }
}
