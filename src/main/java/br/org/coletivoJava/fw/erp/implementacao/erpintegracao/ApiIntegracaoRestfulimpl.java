package br.org.coletivoJava.fw.erp.implementacao.erpintegracao;

import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfBeanSimplesSomenteLeitura;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.erp.repositorioLinkEntidades.RepositorioLinkEntidadesGenerico;
import br.org.coletivoJava.fw.api.erp.erpintegracao.ApiIntegracaoRestful;
import br.org.coletivoJava.fw.api.erp.erpintegracao.model.ItfSistemaERPAtual;
import org.coletivojava.fw.api.objetoNativo.controller.sistemaErp.ItfSistemaErp;
import br.org.coletivoJava.fw.api.erp.erpintegracao.servico.ItfIntegracaoERP;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.SistemaERPAtual;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.SistemaERPConfiavel;
import br.org.coletivoJava.integracoes.restInterprestfull.api.FabIntApiRestIntegracaoERPRestfull;
import com.super_bits.modulosSB.Persistencia.dao.UtilSBPersistencia;
import com.super_bits.modulosSB.Persistencia.dao.consultaDinamica.ConsultaDinamicaDeEntidade;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.RespostaWebServiceSimples;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.ItfResposta;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.ItfRespostaAcaoDoSistema;
import com.super_bits.modulosSB.SBCore.modulos.chavesPublicasePrivadas.RepositorioChavePublicaPrivada;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfBeanSimples;
import jakarta.json.JsonObject;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
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
    public ItfResposta getResposta(ItfSistemaErp pSistema, String nomeAcao, ItfBeanSimples pParametro) {
        JsonObject jsonParametro = gerarConversaoJson(pSistema, pParametro);
        SolicitacaoControllerERP solicitacao = new SolicitacaoControllerERP(pSistema, nomeAcao, jsonParametro);
        RespostaWebServiceSimples resposta = FabIntApiRestIntegracaoERPRestfull.ACOES_EXECUTAR_CONTROLLER
                .getAcao(solicitacao).getResposta();

        return resposta;
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

        SistemaERPAtual sistemAtual = new SistemaERPAtual();
        sistemAtual.setNome(SBCore.getGrupoProjeto());

        String idPardeChaves = SBCore.getConfigModulo(FabConfigModuloWebERPChaves.class).getPropriedade(FabConfigModuloWebERPChaves.PAR_DE_CHAVES_IDENTIFICADOR);
        Map<String, String> par = RepositorioChavePublicaPrivada.geParDeChavesPubPrivada(idPardeChaves);
        if (par == null) {
            throw new UnsupportedOperationException("O par de chaves local do sistema não foi encontrado");
        }
        String cvPublicaDecoded = par.keySet().iterator().next();
        String cvPrivadaDecoded = par.values().iterator().next();
        sistemAtual.setChavePrivada(cvPrivadaDecoded);
        sistemAtual.setChavePublica(cvPublicaDecoded);

        String urlStr = SBCore.getConfigModulo(FabConfigModuloWebERPChaves.class).getPropriedade(FabConfigModuloWebERPChaves.SITE_URL);
        try {
            URL urlSite = new URL(urlStr);
            sistemAtual.setDominio(urlSite.getHost());
            String getaoTpkenIdentificador = FabIntApiRestIntegracaoERPRestfull.ACOES_EXECUTAR_CONTROLLER.getClasseGestaoOauth().getSimpleName();
            sistemAtual.setUrlRecepcaoCodigo(urlStr + "/solicitacaoAuth2Recept/" + "/code/Usuario/" + getaoTpkenIdentificador);

        } catch (MalformedURLException ex) {
            throw new UnsupportedOperationException("o sistema atual não possui uma url válida");
        }

        return sistemAtual;
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

    @Override
    public ItfSistemaErp getSistemaByDominio(String pChavePublica) {
        EntityManager em = UtilSBPersistencia.getEntyManagerPadraoNovo();
        ConsultaDinamicaDeEntidade novaConsulta = new ConsultaDinamicaDeEntidade(SistemaERPConfiavel.class, em);
        novaConsulta.addcondicaoCampoIgualA("dominio", pChavePublica);
        List<SistemaERPConfiavel> sistemaConfiavel = novaConsulta.resultadoRegistros();
        UtilSBPersistencia.fecharEM(em);
        if (sistemaConfiavel.isEmpty()) {
            return null;
        } else {
            return sistemaConfiavel.get(0);
        }
    }

    @Override
    public ItfSistemaErp getSistemaByHashChavePublica(String pHashChavePuvlica) {
        EntityManager em = UtilSBPersistencia.getEntyManagerPadraoNovo();
        ConsultaDinamicaDeEntidade novaConsulta = new ConsultaDinamicaDeEntidade(SistemaERPConfiavel.class, em);
        novaConsulta.addcondicaoCampoIgualA("hashChavePublica", pHashChavePuvlica);
        List<SistemaERPConfiavel> sistemaConfiavel = novaConsulta.resultadoRegistros();
        UtilSBPersistencia.fecharEM(em);
        if (sistemaConfiavel.isEmpty()) {
            return null;
        } else {
            return sistemaConfiavel.get(0);
        }
    }

    @Override
    public JsonObject gerarConversaoJson(ItfSistemaErp pSistema, String pJson) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ItfBeanSimples gerarConversaoJson(ItfSistemaErp pSistema, JsonObject pJson) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JsonObject gerarConversaoJson(ItfSistemaErp pSistema, ItfBeanSimples pJson) {
        return null;
    }
}
