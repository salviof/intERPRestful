package br.org.coletivoJava.fw.erp.implementacao.erpintegracao;

import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfBeanSimplesSomenteLeitura;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.erp.repositorioLinkEntidades.RepositorioLinkEntidadesGenerico;
import br.org.coletivoJava.fw.api.erp.erpintegracao.ApiIntegracaoRestful;
import br.org.coletivoJava.fw.api.erp.erpintegracao.model.ItfSistemaERPAtual;
import br.org.coletivoJava.fw.api.erp.erpintegracao.model.ItfSistemaErp;
import br.org.coletivoJava.fw.api.erp.erpintegracao.servico.ItfIntegracaoERP;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.SistemaERPConfiavel;
import com.super_bits.modulosSB.Persistencia.dao.UtilSBPersistencia;
import com.super_bits.modulosSB.Persistencia.dao.consultaDinamica.ConsultaDinamicaDeEntidade;
import com.super_bits.modulosSB.SBCore.UtilGeral.MapaAcoesSistema;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.ItfRespostaAcaoDoSistema;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.acoes.ItfAcaoDoSistema;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfBeanSimples;
import java.util.List;
import javax.persistence.EntityManager;

@ApiIntegracaoRestful
public class ApiIntegracaoRestfulimpl extends RepositorioLinkEntidadesGenerico
        implements
        ItfIntegracaoERP {

    public ApiIntegracaoRestfulimpl() {
        System.out.println("");
    }

    @Override
    public String getCodigoSistemaInterno(java.lang.Class c, int i) {
        return null;
    }

    @Override
    public boolean registrarCodigoLigacaoApi(java.lang.Class c, int i,
            java.lang.String s) {
        return false;
    }

    @Override
    public ItfBeanSimplesSomenteLeitura getObjetoDTOFromJson(java.lang.Class c,
            java.lang.String s) {
        return null;
    }

    @Override
    public String getCodigoApiExterna(java.lang.Class c, int i) {
        return null;
    }

    @Override
    public ItfRespostaAcaoDoSistema getResposta(ItfSistemaErp pSistema, String nomeAcao, ItfBeanSimples pParametro) {

        ItfAcaoDoSistema acao = MapaAcoesSistema.getAcaoDoSistemaByNomeUnico(nomeAcao);

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getAcoesDisponiveis(ItfSistemaErp pSistema, String nomeAcao, ItfBeanSimples pParametro) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getAcoesDisponiveis(ItfSistemaErp pSistema, String nomeAcao) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getAcoesDisponiveis(ItfSistemaErp pSistema) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ItfRespostaAcaoDoSistema enviarChavePublica(ItfSistemaErp pSistema) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean adicionarSistemaConfianca(String pChavePublica, String url) {
        return false;
    }

    @Override
    public String gerarTokenUsuarioLogado(ItfSistemaErp pSistema) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ItfSistemaERPAtual getSistemaAtual() {
        return null;
    }

    @Override
    public ItfSistemaErp getSistemaByChavePublica(String pChavePublica) {
        EntityManager em = UtilSBPersistencia.getEntyManagerPadraoNovo();
        ConsultaDinamicaDeEntidade novaConsulta = new ConsultaDinamicaDeEntidade(SistemaERPConfiavel.class, em);
        novaConsulta.addcondicaoCampoIgualA("chavePublica", pChavePublica);
        List<SistemaERPConfiavel> sistemaConfiavel = novaConsulta.resultadoRegistros();
        UtilSBPersistencia.fecharEM(em);
        if (sistemaConfiavel.isEmpty()) {
            return null;
        } else {
            return sistemaConfiavel.get(0);
        }
    }
}
