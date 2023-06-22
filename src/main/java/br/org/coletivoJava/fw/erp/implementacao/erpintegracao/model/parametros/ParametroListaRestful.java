/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.parametros;

import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoObjetoSB;
import java.util.Map;

/**
 *
 * @author salvio
 */
@InfoObjetoSB(tags = "Parametro  lista restful", plural = "Parametros lista Restful")
public class ParametroListaRestful extends ParametroRestful {

    private int pagina = 0;
    private Map<String, Object> parametros;
    private String atributo;

    public ParametroListaRestful() {
        super(ParametroListaRestful.class.getSimpleName());
    }

    public int getPagina() {
        return pagina;
    }

    public void setPagina(int pagina) {
        this.pagina = pagina;
    }

    public Map<String, Object> getParametros() {
        return parametros;
    }

    public void setParametros(Map<String, Object> parametros) {
        this.parametros = parametros;
    }

    public String getAtributo() {
        return atributo;
    }

    public void setAtributo(String atributo) {
        this.atributo = atributo;
    }

}
