/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.paginas;

import br.org.coletivoJava.fw.api.erp.erpintegracao.contextos.ERPIntegracaoSistemasApi;
import br.org.coletivoJava.integracoes.restInterprestfull.api.FabIntApiRestIntegracaoERPRestfull;
import com.super_bits.modulosSB.Persistencia.dao.UtilSBPersistencia;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token.ItfTokenGestao;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token.ItfTokenGestaoOauth;
import com.super_bits.modulosSB.SBCore.modulos.erp.ItfSistemaERP;
import com.super_bits.modulosSB.SBCore.modulos.objetos.MapaObjetosProjetoAtual;
import com.super_bits.modulosSB.webPaginas.util.UtilSBWP_JSFTools;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;

@Named
@RequestScoped
public class ServicoSessaoOauth implements Serializable {

    private List<ItfSistemaERP> sistemas;

    @PostConstruct
    public void inicio() {
        EntityManager em = UtilSBPersistencia.getEntyManagerPadraoNovo();
        sistemas = UtilSBPersistencia.getListaTodos(MapaObjetosProjetoAtual.getClasseDoObjetoByNome("SistemaERPConfiavel"),
                em);
        UtilSBPersistencia.fecharEM(em);
    }

    public ItfSistemaERP getSistema(String pNomeSistema) {
        Optional<ItfSistemaERP> sistemaContabil = sistemas.stream().filter(sis -> sis.getNome().contains(pNomeSistema) || sis.getDominio().contains(pNomeSistema)).findFirst();
        if (sistemaContabil.isPresent()) {
            return sistemaContabil.get();
        } else {
            return null;
        }
    }

    public ItfTokenGestaoOauth getGestao(String pNomeSistema) {
        Optional<ItfSistemaERP> sistemaContabil = sistemas.stream().filter(sis -> sis.getNome().contains(pNomeSistema) || sis.getDominio().contains(pNomeSistema)).findFirst();
        if (sistemaContabil.isPresent()) {
            ItfTokenGestaoOauth tokenGestao = FabIntApiRestIntegracaoERPRestfull.getGestaoTokenOpcoes(sistemaContabil.get());
            return tokenGestao;
        }
        return null;
    }

    public void abrirJanelaConexao(String pNomeSistema) {
        Optional<ItfSistemaERP> sistemaContabil = sistemas.stream()
                .filter(sis -> sis.getNome().contains(pNomeSistema)
                || sis.getDominio().contains(pNomeSistema)).findFirst();
        if (sistemaContabil.isPresent()) {
            ItfTokenGestao tokenGestao = FabIntApiRestIntegracaoERPRestfull.getGestaoTokenOpcoes(sistemaContabil.get());
            if (!tokenGestao.isTemTokemAtivo()) {
                UtilSBWP_JSFTools.abrirJanelaConexaoAplicativo((ItfTokenGestaoOauth) tokenGestao);
            }
        }
    }

    public boolean isSistemaConectado(String pNomeSistema) {

        Optional<ItfSistemaERP> sistemaContabil = sistemas.stream().filter(sis -> sis.getNome().contains(pNomeSistema) || sis.getDominio().contains(pNomeSistema)).findFirst();
        if (sistemaContabil.isPresent()) {
            ItfTokenGestao tokenGestao = FabIntApiRestIntegracaoERPRestfull.getGestaoTokenOpcoes(sistemaContabil.get());
            return tokenGestao.isTemTokemAtivo();
        }
        return false;
    }

}
