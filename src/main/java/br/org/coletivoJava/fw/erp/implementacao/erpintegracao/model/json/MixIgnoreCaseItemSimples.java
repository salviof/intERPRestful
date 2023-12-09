/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.calculos.ItfCalculos;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.campoInstanciado.ItfAssistenteDeLocalizacao;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.campoInstanciado.ItfCampoInstanciado;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.CampoMapValores;
import java.lang.reflect.Field;
import java.util.Map;

/**
 *
 * @author salvio
 */
@JsonIgnoreProperties({"mapaCamposInstanciados",
    "novoBeanPreparado",
    "mapaCampoPorAnotacao",
    "controleCalculo",
    "instancia",
    "camposInstanciados",
    "camposEsperados",
    "mapaJustificativasExecucaoAcao",
    "mapaAssistenteLocalizacao",
    "mapeouTodosOsCampos"})
public class MixIgnoreCaseItemSimples {

    @JsonIgnore
    protected CampoMapValores camposEsperados;
    private Map<String, ItfCampoInstanciado> mapaCamposInstanciados;
    @JsonIgnore
    private boolean novoBeanPreparado;
    // TODO, Justificativas para alteração de Campos
    @JsonIgnore
    private Map<String, String> mapaJustificativasExecucaoAcao;
    @JsonIgnore
    private Map<String, ItfAssistenteDeLocalizacao> mapaAssistenteLocalizacao;
    @JsonIgnore
    private Map<String, Field> mapaCampoPorAnotacao;
    @JsonIgnore
    private boolean mapeouTodosOsCampos = false;
    @JsonIgnore
    private Map<ItfCalculos, Boolean> controleCalculo;

    @JsonIgnore(true)
    private final Object instancia = null;
}
