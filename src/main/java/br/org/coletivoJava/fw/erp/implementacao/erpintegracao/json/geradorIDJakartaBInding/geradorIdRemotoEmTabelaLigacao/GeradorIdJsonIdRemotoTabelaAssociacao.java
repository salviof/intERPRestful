/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.json.geradorIDJakartaBInding.geradorIdRemotoEmTabelaLigacao;

import br.org.coletivoJava.fw.api.erp.erpintegracao.contextos.ERPIntegracaoSistemasApi;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.json.geradorIDJakartaBInding.GeradorIdJsonGenerico;
import com.super_bits.modulosSB.SBCore.modulos.erp.ItfSistemaERP;
import com.super_bits.modulosSB.SBCore.modulos.objetos.entidade.basico.ComoEntidadeSimples;

/**
 *
 * @author salvio
 */
public abstract class GeradorIdJsonIdRemotoTabelaAssociacao extends GeradorIdJsonGenerico {

    private static final long serialVersionUID = 1L;
    private ItfSistemaERP sistemaRemoto;

    public GeradorIdJsonIdRemotoTabelaAssociacao(ItfSistemaERP pSistemaRemoto) {
        this(ComoEntidadeSimples.class, -1);
    }

    public GeradorIdJsonIdRemotoTabelaAssociacao(Class<?> scope, int fv) {
        super(scope);

    }

    @Override
    public Long generateId(Object forPojo) {
        ComoEntidadeSimples itemSimples = (ComoEntidadeSimples) forPojo;
        String itemRemoto = ERPIntegracaoSistemasApi.RESTFUL.getRepositorioLinkEntidadesByID().getCodigoApiExterna(sistemaRemoto, itemSimples);
        if (itemRemoto == null) {
            itemRemoto = "0";
        }
        return Long.valueOf(itemRemoto);
    }

    @Override
    public String toString() {
        return super.toString(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

}
