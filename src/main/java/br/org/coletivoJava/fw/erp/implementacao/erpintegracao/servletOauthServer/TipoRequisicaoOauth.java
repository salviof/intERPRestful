/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.servletOauthServer;

import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoCampo;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoObjetoSB;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.campo.FabTipoAtributoObjeto;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.ItemSimples;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ComoEntidadeVinculadoAEnum;
import com.super_bits.modulosSB.SBCore.modulos.fabrica.ComoFabrica;

/**
 *
 * @author sfurbino
 */
@InfoObjetoSB(tags = "Tipo requisição", plural = "Tipos de Requisição", fabricaVinculada = FabTipoRequisicaoOauthServer.class)
public class TipoRequisicaoOauth extends ItemSimples implements ComoEntidadeVinculadoAEnum {

    @InfoCampo(tipo = FabTipoAtributoObjeto.ID)
    private Long id;
    @InfoCampo(tipo = FabTipoAtributoObjeto.NOME)
    private String nome;
    private FabTipoRequisicaoOauthServer enumVinculado;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public void setEnumVinculado(ComoFabrica pFabrica) {
        enumVinculado = (FabTipoRequisicaoOauthServer) pFabrica;
    }

    @Override
    public FabTipoRequisicaoOauthServer getEnumVinculado() {
        return enumVinculado;
    }

}
