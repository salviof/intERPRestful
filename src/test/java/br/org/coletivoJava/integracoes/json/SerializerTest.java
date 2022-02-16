/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.integracoes.json;

import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.teste.ConfigPercistenciaItegracaoSistemas;
import br.org.coletivoJava.integracoes.intRestful.api.ConfiguradorCoreApiERPIntegracoes;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

import com.super_bits.modulos.SBAcessosModel.model.UsuarioSB;
import com.super_bits.modulosSB.Persistencia.ConfigGeral.SBPersistencia;
import com.super_bits.modulosSB.Persistencia.dao.UtilSBPersistencia;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.modulos.objetos.MapaObjetosProjetoAtual;
import com.super_bits.modulosSB.SBCore.modulos.objetos.estrutura.ItfEstruturaCampoEntidade;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfBeanSimplesSomenteLeitura;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import org.coletivojava.fw.api.tratamentoErros.FabErro;
import org.junit.Test;

/**
 *
 * @author sfurbino
 */
public class SerializerTest {

    @Test
    public void testeJsonSerializacaoObjeto() {
        SBCore.configurar(new ConfiguradorCoreApiERPIntegracoes(), SBCore.ESTADO_APP.DESENVOLVIMENTO);
        SBPersistencia.configuraJPA(new ConfigPercistenciaItegracaoSistemas());
        EntityManager em = UtilSBPersistencia.getEntyManagerPadraoNovo();
        List<UsuarioSB> usuarios = UtilSBPersistencia.getListaTodos(UsuarioSB.class, em);

        for (ItfEstruturaCampoEntidade campo : MapaObjetosProjetoAtual.getEstruturaObjeto(UsuarioSB.class).getCampos()) {
            System.out.println(campo.getNome());
        }
        for (UsuarioSB usr : usuarios) {
            System.out.println(getJsonFromObjeto(usr));
        }

    }

    public String getJsonFromObjeto(ItfBeanSimplesSomenteLeitura pBean) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

        mapper.setAnnotationIntrospector(new AutoAtotacaoJacksonDefinindoCampoId());
        mapper.enable(MapperFeature.USE_ANNOTATIONS);
        mapper.registerModule(new Hibernate5Module());

        try {
            String json = mapper.writer().withDefaultPrettyPrinter().writeValueAsString(pBean);

            return json;
        } catch (JsonProcessingException ex) {
            Logger.getLogger(SerializerTest.class.getName()).log(Level.SEVERE, null, ex);
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Falha gerando objeto", ex);
            return null;
        }
    }

    static class AutoAtotacaoJacksonDefinindoCampoId extends JacksonAnnotationIntrospector {

        public AutoAtotacaoJacksonDefinindoCampoId() {
            System.out.println("Test");
        }

        @Override
        public JsonIgnoreProperties.Value findPropertyIgnorals(Annotated a) {
            Class classe = a.getRawType();

            for (ItfEstruturaCampoEntidade campo : MapaObjetosProjetoAtual.getEstruturaObjeto(classe).getCampos()) {
                System.out.println(campo.getNome());
            }
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
        public ObjectIdInfo findObjectIdInfo(com.fasterxml.jackson.databind.introspect.Annotated ann) {
            return new ObjectIdInfo(
                    PropertyName.construct("@id", null),
                    null,
                    ObjectIdGenerators.IntSequenceGenerator.class,
                    null);
        }

    }

}
