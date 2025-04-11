/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.parametros;

import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoCampo;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoObjetoSB;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.campo.FabTipoAtributoObjeto;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.ItemSimples;
import java.util.Date;
import javax.persistence.Id;

@InfoObjetoSB(tags = "Parametro restful", plural = "Parametros Restful")
public class ParametroRestful extends ItemSimples {

    @Id
    private Long id;

    @InfoCampo(tipo = FabTipoAtributoObjeto.NOME)
    private String nometipoParametro;

    public ParametroRestful(String nometipoParametro) {
        this.id = 0l;//new Date().toString().hashCode();
        this.nometipoParametro = nometipoParametro;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getNometipoParametro() {
        return nometipoParametro;
    }

    public void setNometipoParametro(String nometipoParametro) {
        this.nometipoParametro = nometipoParametro;
    }

}
