/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.teste;

import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.FabGrupoTestesIntegracao;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.FabModuloTestesIntegracao;
import com.super_bits.modulosSB.Persistencia.ConfigGeral.ConfigPersistenciaPadrao;
import com.super_bits.modulosSB.SBCore.modulos.fabrica.ComoFabrica;

/**
 *
 * @author sfurbino
 */
public class ConfigPercistenciaItegracaoSistemas extends ConfigPersistenciaPadrao {

    @Override
    public Class<? extends ComoFabrica>[] fabricasRegistrosIniciais() {
        return new Class[]{FabModuloTestesIntegracao.class, FabGrupoTestesIntegracao.class, FabDadosIniciaisTesteIntegracao.class};
    }

    @Override
    public boolean isExibirLogBancoDeDados() {
        return false;
    }

}
