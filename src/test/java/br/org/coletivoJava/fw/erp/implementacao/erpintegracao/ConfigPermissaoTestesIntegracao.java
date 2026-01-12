/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao;

import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.teste.servicoTeste.ModuloRestfulTeste;
import com.super_bits.modulos.SBAcessosModel.model.UsuarioSB;
import com.super_bits.modulosSB.Persistencia.dao.UtilSBPersistencia;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.permissoes.ErroDadosDeContatoUsuarioNaoEncontrado;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.permissoes.token.ItfTokenAcessoDinamico;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.permissoes.token.ItfTokenRecuperacaoEmail;
import com.super_bits.modulosSB.SBCore.modulos.Controller.permissaoPadrao.ConfigPermissaoPadraoEmMemoria;
import com.super_bits.modulosSB.SBCore.modulos.erp.FabTipoAgenteOrganizacao;
import com.super_bits.modulosSB.SBCore.modulos.fabrica.ComoFabricaAcoes;
import org.coletivojava.fw.api.objetoNativo.view.menu.MenusDaSessao;
import java.util.List;
import javax.persistence.EntityManager;
import org.coletivojava.fw.api.objetoNativo.view.menu.MenuSBFW;
import com.super_bits.modulosSB.SBCore.modulos.objetos.entidade.basico.ComoEntidadeSimplesSomenteLeitura;
import com.super_bits.modulosSB.SBCore.modulos.objetos.entidade.basico.ComoGrupoUsuario;
import com.super_bits.modulosSB.SBCore.modulos.objetos.entidade.basico.ComoUsuario;
import com.super_bits.modulosSB.SBCore.modulos.objetos.entidade.contato.ComoContatoHumano;
import com.super_bits.modulosSB.SBCore.modulos.view.menu.ComoMenusDeSessao;

/**
 *
 * @author sfurbino
 */
public class ConfigPermissaoTestesIntegracao extends ConfigPermissaoPadraoEmMemoria {

    public ConfigPermissaoTestesIntegracao() {
        super(new Class[]{ModuloRestfulTeste.class});
    }

    @Override
    public ComoMenusDeSessao definirMenu(ComoGrupoUsuario pGrupo) {
        return new MenusDaSessao(new MenuSBFW(), new MenuSBFW());
    }

    @Override
    public ItfTokenRecuperacaoEmail gerarTokenRecuperacaoDeSenha(ComoUsuario pUsuario, int pMinutosValidade) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ItfTokenAcessoDinamico gerarTokenDinamico(ComoFabricaAcoes pAcao, ComoEntidadeSimplesSomenteLeitura pItem, String pEmail) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isTokenDinamicoExiste(ComoFabricaAcoes pAcao, ComoEntidadeSimplesSomenteLeitura pItem, String pEmail) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void persitirMergePermissoes() {

    }

    @Override
    public List<ComoUsuario> configuraUsuarios() {
        List<ComoUsuario> usuarios = super.configuraUsuarios(); //To change body of generated methods, choose Tools | Templates.
        EntityManager em = UtilSBPersistencia.getEntyManagerPadraoNovo();
        List<UsuarioSB> usuariosDB = UtilSBPersistencia.getListaTodos(UsuarioSB.class, em);
        usuariosDB.stream().forEach(usuarios::add);
        UtilSBPersistencia.fecharEM(em);
        return usuarios;
    }

    @Override
    public FabTipoAgenteOrganizacao getTipoAgente(ComoUsuario pUsuario) {
        return FabTipoAgenteOrganizacao.ATENDIMENTO;
    }

    @Override
    public ComoContatoHumano getContatoDoUsuario(ComoUsuario pUsuairo) throws ErroDadosDeContatoUsuarioNaoEncontrado {
        return null;
    }

}
