/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.teste;

import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.FabGrupoTestesIntegracao;
import com.super_bits.modulos.SBAcessosModel.model.UsuarioSB;
import com.super_bits.modulosSB.Persistencia.fabrica.ItfFabricaComPersistencia;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfGrupoUsuario;

/**
 *
 * @author sfurbino
 */
public enum FabDadosIniciaisTesteIntegracao implements ItfFabricaComPersistencia {
    //   GRUPO_TESTE,
    USUARIO_SALVIO,
    USUARIO_WEB;
    //   MODULO_TESTE_INTEGRACAO;

    @Override
    public Object getRegistro() {
        switch (this) {
            //  case MODULO_TESTE_INTEGRACAO:
            //     ModuloAcaoSistema nonvomodulo = new ModuloAcaoSistema();
            //        nonvomodulo.setId(1);
            //         nonvomodulo.setDescricao("teste");
            //          nonvomodulo.setNome("teste");
            //             return nonvomodulo;
            //           case GRUPO_TESTE:
            //              GrupoUsuarioSB grupo = new GrupoUsuarioSB();
            //               grupo.setId(1);
            //             return grupo;
            case USUARIO_SALVIO:
                UsuarioSB usuario = new UsuarioSB();
                usuario.setNome("Salvio");
                usuario.setId(1);
                usuario.setGrupo((ItfGrupoUsuario) FabGrupoTestesIntegracao.GRUPO_TESTE.getRegistro());
                usuario.setEmail("salviof@gmail.com");
                usuario.setSenha("123456");
                return usuario;
            case USUARIO_WEB:
                UsuarioSB usuarioCliente = new UsuarioSB();
                usuarioCliente.setNome("Salvio");
                usuarioCliente.setId(1);
                usuarioCliente.setGrupo((ItfGrupoUsuario) FabGrupoTestesIntegracao.GRUPO_TESTE.getRegistro());
                usuarioCliente.setEmail("cliente@sistemaerp2.com.br");
                usuarioCliente.setSenha("123456");
                return usuarioCliente;

            default:
                throw new AssertionError(this.name());

        }

    }

}
