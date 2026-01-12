/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao;

import com.super_bits.modulosSB.Persistencia.dao.UtilSBPersistencia;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilCRCJson;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilCRCNumeros;
import com.super_bits.modulosSB.SBCore.modulos.geradorCodigo.model.EstruturaCampo;
import com.super_bits.modulosSB.SBCore.modulos.geradorCodigo.model.EstruturaDeEntidade;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.campoInstanciado.ItfCampoInstanciado;
import com.super_bits.modulosSB.SBCore.modulos.objetos.MapaObjetosProjetoAtual;
import com.super_bits.modulosSB.SBCore.modulos.objetos.entidade.basico.ComoEntidadeSimples;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import org.coletivojava.fw.api.tratamentoErros.FabErro;

/**
 *
 * @author salvio
 */
public class UtilSBRestFulJsonToEntity {

    public static void aplicarValor(ComoEntidadeSimples pEntidade, String pChave, JsonValue pValorJson) {

        EntityManager em = UtilSBPersistencia.getEntyManagerPadraoNovo();
        try {
            ItfCampoInstanciado campo = pEntidade.getCPinst(pChave);
            switch (campo.getFabricaTipoAtributo().getTipoPrimitivo()) {

                case INTEIRO:
                    pEntidade.getCampoInstanciadoByNomeOuAnotacao(pChave)
                            .setValor(UtilCRCJson.getComoInteiro(pValorJson));
                    break;
                case NUMERO_LONGO:

                    break;
                case LETRAS:
                    String valorstr = null;
                    if (pValorJson instanceof jakarta.json.JsonString) {
                        valorstr = ((jakarta.json.JsonString) pValorJson).getString();
                    }

                    if (valorstr == null) {
                        if (pValorJson != null) {
                            if (!pValorJson.getValueType().equals(JsonValue.ValueType.NULL)) {
                                valorstr = pValorJson.toString();
                            }
                        }
                    }

                    pEntidade.getCampoInstanciadoByNomeOuAnotacao(pChave)
                            .setValor(valorstr);

                    break;

                case DATAS:

                    break;
                case BOOLEAN:

                    break;
                case DECIMAL:
                    double valorDouble = UtilCRCNumeros.getDoublePorString(pValorJson.toString());
                    campo.setValor(valorDouble);
                    break;
                case ENTIDADE:

                    switch (campo.getFabricaTipoAtributo()) {
                        case LC_LOCALIZACAO:
                            if (pValorJson.getValueType().equals(JsonValue.ValueType.OBJECT)) {
                                if (pValorJson.asJsonObject().containsKey("cep")) {
                                    ItfCampoInstanciado cpLocalizacao = pEntidade.getCampoInstanciadoByNomeOuAnotacao(pChave);
                                    String cep = pValorJson.asJsonObject().getString("cep");
                                    cpLocalizacao.getComoCampoLocalizacao().aplicarCEP(cep);
                                    if (pValorJson.asJsonObject().containsKey("complemento")) {
                                        cpLocalizacao.getComoCampoLocalizacao().setComplemento(pValorJson.asJsonObject().getString("complemento"));
                                    }

                                }
                            }

                            break;
                        case OBJETO_DE_UMA_LISTA:
                            ItfCampoInstanciado cp = pEntidade.getCampoInstanciadoByNomeOuAnotacao(pChave);
                            if (pValorJson.getValueType().equals(JsonValue.ValueType.OBJECT)) {
                                if (pValorJson.asJsonObject().containsKey("@id")) {
                                    ComoEntidadeSimples beanSimples = cp.getObjetoDoAtributo();
                                    EstruturaDeEntidade estrutura = MapaObjetosProjetoAtual.getEstruturaObjeto(beanSimples.getClass().getSimpleName());
                                    String entidade = estrutura.getCampoByNomeDeclarado(pChave).getClasseCampoDeclaradoOuTipoLista();
                                    Long id = 0l;
                                    if (pValorJson instanceof jakarta.json.JsonObject) {
                                        id = Long.valueOf(UtilCRCJson.getComoInteiro(pValorJson.asJsonObject().get("@id")));
                                    }

                                    ComoEntidadeSimples item = (ComoEntidadeSimples) UtilSBPersistencia.getRegistroByID(MapaObjetosProjetoAtual.getClasseDoObjetoByNome(entidade), id, em);
                                    cp.setValor(item);

                                } else {
                                    throw new UnsupportedOperationException("Falha criando Atributo [" + pChave + "] o @id não foi informado o sistema ainda não suporta Cascata de persistencia com @ManyToOne");
                                }
                                String tipoCampo = cp.getTipoCampoSTR();
                                //    ComoEntidadeSimples objeto = (ComoEntidadeSimples) UtilSBPersistencia.
                                //          getRegistroByID(entidadeFilho, pJsonAtributosAtualizadosObjeto.getJsonObject(campo.getNomeDeclarado())
                                //                .getInt("id"), em);
                                //cp.setValor(objeto);
                            }
                            break;
                        case LISTA_OBJETOS_PARTICULARES:
                        case LISTA_OBJETOS_PUBLICOS:
                            if (campo.getValor() == null) {
                                campo.setValor(new ArrayList());
                            }
                            JsonArray jsonArray = pValorJson.asJsonArray();
                            jsonArray.stream().forEach(jsonValue -> {
                                EstruturaDeEntidade estruturaObjeto = MapaObjetosProjetoAtual.getEstruturaObjeto(campo.getObjetoDoAtributo().getClass().getSimpleName());
                                EstruturaCampo estruturaCampo = estruturaObjeto.getCampoByNomeDeclarado(pChave);
                                String objetoDaLista = estruturaCampo.getClasseCampoDeclaradoOuTipoLista();
                                Class entidade = MapaObjetosProjetoAtual.getClasseDoObjetoByNome(objetoDaLista);
                                ComoEntidadeSimples item = null;
                                JsonObject itemJson = jsonValue.asJsonObject();
                                if (itemJson.containsKey("@id")) {
                                    item = (ComoEntidadeSimples) UtilSBPersistencia.getRegistroByID(entidade, Long.valueOf(itemJson.getInt("@id")), em);
                                } else {
                                    try {
                                        item = (ComoEntidadeSimples) entidade.newInstance();
                                    } catch (InstantiationException | IllegalAccessException ex) {
                                        Logger.getLogger(UtilSBRestFulJsonToEntity.class.getName()).log(Level.SEVERE, null, ex);
                                    }

                                }
                                for (String atributoDoItem : itemJson.asJsonObject().keySet()) {
                                    ItfCampoInstanciado campoInstanciado = item.getCampoInstanciadoByNomeOuAnotacao(atributoDoItem);
                                    if (!campoInstanciado.isCampoNaoInstanciado()) {
                                        aplicarValor(item, atributoDoItem, itemJson.get(atributoDoItem));
                                    }
                                }
                                ((List) campo.getValor()).add(item);

                            });
                            System.out.println("Listas ainda não foram definidas");
                            break;
                        default:
                            throw new AssertionError(campo.getFabricaTipoAtributo().name());

                    }

                    break;

                case OUTROS_OBJETOS:
                    break;
                default:
                    throw new AssertionError(campo.getFabricaTipoAtributo().getTipoPrimitivo().name());

            }
        } catch (Throwable t) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Falha processando Json para Entidade" + t.getMessage(), t);
        } finally {
            UtilSBPersistencia.fecharEM(em);
        }
    }

    public static void aplicarAtributosJsonEmEntidade(ComoEntidadeSimples pEntidade, JsonObject pJsonAtributosAtualizadosObjeto) {
        if (pJsonAtributosAtualizadosObjeto == null || pEntidade == null) {
            return;
        }

        pJsonAtributosAtualizadosObjeto.asJsonObject().keySet().stream().forEach(nomeAtributo -> {
            if (nomeAtributo != null && !nomeAtributo.contains("@")) {
                JsonValue valor = pJsonAtributosAtualizadosObjeto.get(nomeAtributo);
                ItfCampoInstanciado campoInstanciado = pEntidade.getCampoInstanciadoByNomeOuAnotacao(nomeAtributo);
                if (!campoInstanciado.isCampoNaoInstanciado()) {
                    aplicarValor(pEntidade, nomeAtributo, valor);
                }
            }

        });

    }
}
