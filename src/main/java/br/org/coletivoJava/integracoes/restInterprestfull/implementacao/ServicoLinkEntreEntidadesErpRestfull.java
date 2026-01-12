/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.org.coletivoJava.integracoes.restInterprestfull.implementacao;

import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.LinkEntidadesSistemasERP;
import com.super_bits.modulosSB.Persistencia.dao.UtilSBPersistencia;
import com.super_bits.modulosSB.Persistencia.dao.consultaDinamica.ConsultaDinamicaDeEntidade;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilCRCObjetoSB;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilCRCReflexaoObjeto;
import com.super_bits.modulosSB.SBCore.modulos.erp.ItfServicoLinkEntreEntidadesErpRestfull;
import com.super_bits.modulosSB.SBCore.modulos.erp.ItfSistemaERP;
import com.super_bits.modulosSB.SBCore.modulos.objetos.entidade.basico.ComoEntidadeSimples;
import java.util.List;
import javax.persistence.EntityManager;
import com.super_bits.modulosSB.SBCore.modulos.objetos.entidade.basico.ComoEntidadeSimplesSomenteLeitura;

/**
 *
 * @author salvio
 */
public class ServicoLinkEntreEntidadesErpRestfull implements ItfServicoLinkEntreEntidadesErpRestfull {

    @Override
    public String getCodigoApiExterna(ItfSistemaERP pSistema, ComoEntidadeSimples pEntidade) {
        LinkEntidadesSistemasERP linkEntreEntidades
                = new LinkEntidadesSistemasERP(
                        pSistema,
                        pEntidade
                );
        EntityManager em = UtilSBPersistencia.getEntyManagerPadraoNovo();
        try {
            ConsultaDinamicaDeEntidade consulta = new ConsultaDinamicaDeEntidade(LinkEntidadesSistemasERP.class, em);
            Class entidade = UtilCRCReflexaoObjeto.getClassExtraindoProxy(pEntidade.getClass().getSimpleName());
            consulta.addcondicaoCampoIgualA("codigoIdentificador", linkEntreEntidades.getCodigoIdentificador());
            consulta.addcondicaoCampoIgualA("entidade", entidade.getSimpleName());
            List<LinkEntidadesSistemasERP> linl = consulta.resultadoRegistros();
            if (linl.isEmpty()) {
                return null;
            } else {
                return linl.get(0).getCodigoSistemaRemoto();
            }

        } finally {
            UtilSBPersistencia.fecharEM(em);
        }
    }

    @Override
    public String getCodigoSistemaInterno(ItfSistemaERP pSistema, Class pEntidade, String pCodigoapiExterno) {
        EntityManager em = UtilSBPersistencia.getEntyManagerPadraoNovo();
        try {
            ConsultaDinamicaDeEntidade consulta = new ConsultaDinamicaDeEntidade(LinkEntidadesSistemasERP.class, em);
            consulta.addcondicaoCampoIgualA("codigoSistemaRemoto", pCodigoapiExterno);
            Class entidade = UtilCRCReflexaoObjeto.getClassExtraindoProxy(pEntidade.getSimpleName());
            consulta.addcondicaoCampoIgualA("entidade", entidade.getSimpleName());

            List<LinkEntidadesSistemasERP> linl = consulta.resultadoRegistros();
            if (linl.isEmpty()) {
                return null;
            } else {
                return linl.get(0).getCodigoInterno();
            }

        } finally {
            UtilSBPersistencia.fecharEM(em);
        }
    }

    @Override
    public boolean registrarCodigoLigacaoApi(ItfSistemaERP pSistema, ComoEntidadeSimples pEntidade, String codigoAPIExterna) {
        LinkEntidadesSistemasERP linkEntreEntidades
                = new LinkEntidadesSistemasERP(
                        pSistema,
                        pEntidade,
                        codigoAPIExterna
                );

        EntityManager em = UtilSBPersistencia.getEntyManagerPadraoNovo();
        try {
            ConsultaDinamicaDeEntidade consulta = new ConsultaDinamicaDeEntidade(LinkEntidadesSistemasERP.class, em);
            consulta.addcondicaoCampoIgualA("codigoIdentificador", linkEntreEntidades.getCodigoIdentificador());
            Class entidade = UtilCRCReflexaoObjeto.getClassExtraindoProxy(pEntidade.getClass().getSimpleName());
            consulta.addcondicaoCampoIgualA("entidade", entidade.getSimpleName());

            List<LinkEntidadesSistemasERP> linl = consulta.resultadoRegistros();

            if (!linl.isEmpty()) {
                LinkEntidadesSistemasERP linkAtualizado = linl.get(0);
                linkAtualizado.setCodigoSistemaRemoto(codigoAPIExterna);
                UtilSBPersistencia.mergeRegistro(linkAtualizado);
            } else {
                UtilSBPersistencia.mergeRegistro(linkEntreEntidades);
            }

        } finally {
            UtilSBPersistencia.fecharEM(em);
        }
        return true;
    }

    @Override
    public String getCodigoApiExterna(Class pEntidade, Long pCodigoInterno
    ) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getCodigoSistemaInterno(Class pEntidade, int pCodigoapiExterno
    ) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean registrarCodigoLigacaoApi(Class pEntidade, Long codigoInterno, String codigoAPIExterna
    ) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public <T extends ComoEntidadeSimplesSomenteLeitura> T getObjetoDTOFromJson(Class< ? extends T> pClass,
            String Json
    ) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
