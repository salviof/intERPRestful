/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao;

import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.SistemaERPConfiavel;
import com.super_bits.modulosSB.Persistencia.ConfigGeral.SBPersistencia;
import com.super_bits.modulosSB.Persistencia.dao.UtilSBPersistencia;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UTilSBCoreInputs;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreJson;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringValidador;
import com.super_bits.modulosSB.SBCore.modulos.ManipulaArquivo.UtilSBCoreArquivoTexto;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonValue;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;

/**
 *
 * @author salvio
 */
public class MapaSistemasConfiaveis {

    private static final String pnomeArquivo = "sistemasConfiaveisERP.json";
    private static Map<String, SistemaERPConfiavel> mapaSistemas = new HashMap<>();

    private static String getArquivoPersistencia() {
        String arquivo = SBCore.getConfigModulo(FabConfigModuloWebERPChaves.class).getRepositorioDeArquivosExternos().getCaminhoArquivosRepositorio() + "/" + pnomeArquivo;
        return arquivo;
    }

    public static void persistirNovoSistema(SistemaERPConfiavel pSistema) {
        loadSistemasPersistidos();

        mapaSistemas.put(pSistema.getHashChavePublica(), pSistema);
        persistirDados();
        loadSistemasPersistidos();
    }

    private static void persistirDados() {
        if (SBPersistencia.isConfigurado()) {
            mapaSistemas.values().stream().forEach(UtilSBPersistencia::mergeRegistro);
        } else {
            JsonArrayBuilder lista = Json.createArrayBuilder();
            for (SistemaERPConfiavel sistema : mapaSistemas.values()) {
                JsonObjectBuilder sistemaJson = Json.createObjectBuilder();
                sistemaJson.add("dominio", sistema.getDominio());
                sistemaJson.add("chavePublica", sistema.getChavePublica());
                sistemaJson.add("urlPublicaEndPoint", sistema.getUrlPublicaEndPoint());
                sistemaJson.add("hashChavePublica", sistema.getHashChavePublica());
                sistemaJson.add("emailusuarioAdmin", sistema.getEmailusuarioAdmin());
                lista.add(sistemaJson);
                String jsonTexto = UtilSBCoreJson.getTextoByJsonArray(lista.build());
                UtilSBCoreArquivoTexto.escreverEmArquivoSubstituindoArqAnterior(getArquivoPersistencia(), jsonTexto);
                //sistemaJson.add("recepcaoCodigo", sistema.getUrlRecepcaoCodigo());
            }
        }
    }

    public static SistemaERPConfiavel getSistemaByHashChavePublica(String pHash) {
        if (mapaSistemas.isEmpty()) {
            loadSistemasPersistidos();
        }
        if (!mapaSistemas.containsKey(pHash)) {
            loadSistemasPersistidos();
        }
        return mapaSistemas.get(pHash);
    }

    public static void loadSistemasPersistidos() {
        mapaSistemas.clear();
        if (SBPersistencia.isConfigurado()) {
            EntityManager em = UtilSBPersistencia.getEntyManagerPadraoNovo();

            List<SistemaERPConfiavel> sistemas = UtilSBPersistencia.getListaTodos(SistemaERPConfiavel.class, em);
            for (SistemaERPConfiavel sis : sistemas) {
                mapaSistemas.put(sis.getHashChavePublica(), sis);
            }
            UtilSBPersistencia.fecharEM(em);
        } else {
            String json = UTilSBCoreInputs.getStringByArquivoLocal(getArquivoPersistencia());
            if (!UtilSBCoreStringValidador.isNuloOuEmbranco(json)) {
                JsonArray sistemasJson = UtilSBCoreJson.getJsonArrayByTexto(json);
                for (JsonValue valor : sistemasJson) {
                    JsonObject sistemaJson = valor.asJsonObject();
                    SistemaERPConfiavel sistema = new SistemaERPConfiavel();
                    sistema.setDominio(sistemaJson.getString("dominio"));
                    sistema.setChavePublica(sistemaJson.getString("chavePublica"));
                    if (sistemaJson.containsKey("emailusuarioAdmin")) {
                        if (!sistemaJson.isNull("emailusuarioAdmin")) {
                            sistema.setEmailusuarioAdmin(sistemaJson.getString("emailusuarioAdmin"));
                        }
                    }
                    // sistema.setUrlRecepcaoCodigo(sistemaJson.getString("recepcaoCodigo"));
                    sistema.setUrlPublicaEndPoint(sistemaJson.getString("urlPublicaEndPoint"));
                    mapaSistemas.put(sistema.getHashChavePublica(), sistema);
                }
            }
        }

    }

}
