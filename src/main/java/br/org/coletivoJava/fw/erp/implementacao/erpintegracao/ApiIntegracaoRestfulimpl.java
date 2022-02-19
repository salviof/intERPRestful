package br.org.coletivoJava.fw.erp.implementacao.erpintegracao;

import com.super_bits.modulosSB.SBCore.modulos.erp.SolicitacaoControllerERP;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfBeanSimplesSomenteLeitura;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.erp.repositorioLinkEntidades.RepositorioLinkEntidadesGenerico;
import br.org.coletivoJava.fw.api.erp.erpintegracao.ApiIntegracaoRestful;
import br.org.coletivoJava.fw.api.erp.erpintegracao.model.ItfSistemaERPAtual;
import org.coletivojava.fw.api.objetoNativo.controller.sistemaErp.ItfSistemaErp;
import br.org.coletivoJava.fw.api.erp.erpintegracao.servico.ItfIntegracaoERP;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.SistemaERPAtual;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.SistemaERPConfiavel;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.servletRestfulERP.ServletRestfullERP;
import br.org.coletivoJava.integracoes.restInterprestfull.api.FabIntApiRestIntegracaoERPRestfull;
import com.super_bits.modulosSB.Persistencia.dao.UtilSBPersistencia;
import com.super_bits.modulosSB.Persistencia.dao.consultaDinamica.ConsultaDinamicaDeEntidade;
import com.super_bits.modulosSB.Persistencia.registro.persistidos.EntidadeSimples;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.MapaAcoesSistema;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.ItfResposta;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.ItfRespostaAcaoDoSistema;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.acoes.ItfAcaoDoSistema;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.permissoes.ItfAcaoGerenciarEntidade;
import com.super_bits.modulosSB.SBCore.modulos.Controller.comunicacao.RespostaAcaoDoSistema;
import com.super_bits.modulosSB.SBCore.modulos.chavesPublicasePrivadas.RepositorioChavePublicaPrivada;
import com.super_bits.modulosSB.SBCore.modulos.geradorCodigo.model.EstruturaDeEntidade;
import com.super_bits.modulosSB.SBCore.modulos.objetos.MapaObjetosProjetoAtual;
import com.super_bits.modulosSB.SBCore.modulos.objetos.estrutura.ItfEstruturaCampoEntidade;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfBeanSimples;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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
            sistemAtual.setUrlPublicaEndPoint(urlStr + "/" + ServletRestfullERP.SLUGPUBLICACAOSERVLET);
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
    public ItfResposta getResposta(ItfSistemaErp pSistema, String nomeAcao, ItfBeanSimples pParametro) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ItfBeanSimples gerarConversaoJsonStringToObjeto(ItfSistemaErp pSistema, String pJson) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ItfBeanSimples gerarConversaoJsonStringToObjeto(String pJson) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ItfBeanSimples gerarConversaoJsonToObjeto(ItfSistemaErp pSistema, JsonObject pJson) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ItfBeanSimples gerarConversaoJsonToObjeto(JsonObject pJson) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JsonObject gerarConversaoObjetoToJson(ItfSistemaErp pSistema, ItfBeanSimples pJson) {

        JsonObject json = UtilSBJsonRestfulTemp.getJsonFromObjeto(pJson);
        return json;

    }

    @Override
    public JsonObject gerarConversaoObjetoToJson(ItfBeanSimples pJson) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static JsonObject buildRespostaOptions(SolicitacaoControllerERP pSolicitacao) {
        try {
            if (pSolicitacao.isAutenticadoComSucesso() && pSolicitacao.getAcaoStrNomeUnico() == null) {
                List<ItfAcaoGerenciarEntidade> acoesDoUsuario = new ArrayList();

                JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
                jsonBuilder.add("scope", pSolicitacao.getUsuarioSolicitante().getEmail());
                jsonBuilder.add("modulo", pSolicitacao.getUsuarioSolicitante().getGrupo().getModuloPrincipal().getNome());

                List<ItfAcaoGerenciarEntidade> acoes = MapaAcoesSistema.getListaTodasGestao();

                acoes.stream().filter(ac -> SBCore.getServicoPermissao().isAcaoPermitidaUsuario(pSolicitacao.getUsuarioSolicitante(), ac))
                        .forEach(acoesDoUsuario::add);
                JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
                acoesDoUsuario.stream().map(ac -> ac.getNomeUnicoSlug()).forEach(jsonArrayBuilder::add);
                jsonBuilder.add("acoes", jsonArrayBuilder.build());

                JsonObject retorno = jsonBuilder.build();

                return retorno;
            }
            return null;
        } catch (Throwable ex) {

            return null;
        }

    }

    @Override
    public ItfRespostaAcaoDoSistema getRespostaAcaoDoSistema(SolicitacaoControllerERP pSolicitacao) {

        ItfAcaoDoSistema acao = null;
        ItfRespostaAcaoDoSistema resposta = null;

        if (!pSolicitacao.isAutenticadoComSucesso()) {
            resposta = new RespostaAcaoDoSistema();
            resposta.addErro("Ação " + pSolicitacao.getAcaoStrNomeUnico() + " não encontrada");
            return resposta;
        }

        if (pSolicitacao.getAcaoStrNomeUnico() != null) {
            acao = MapaAcoesSistema.getAcaoDoSistemaByNomeUnico(pSolicitacao.getAcaoStrNomeUnico());
            if (acao == null) {
                resposta = new RespostaAcaoDoSistema();
                resposta.addErro("Ação " + pSolicitacao.getAcaoStrNomeUnico() + " não encontrada");
            } else {
                resposta = getRespostaAcaoDoSistema(pSolicitacao);
            }
        }

        if (resposta == null) {
            resposta = new RespostaAcaoDoSistema();
        }

        String metodo = pSolicitacao.getMetodo();
        JsonObject retornoProcessado;

        switch (metodo) {
            case "POST":
                ItfBeanSimples entidade = null;
                if (pSolicitacao.getCodigoEntidade() != null) {
                    if (!pSolicitacao.getCodigoEntidade().equals("0")) {
                        Class<? extends EntidadeSimples> classeEntidade = acao.getComoAcaoDeEntidade().getClasseRelacionada();
                        EntityManager em = UtilSBPersistencia.getEntyManagerPadraoNovo();
                        int codigo = Integer.valueOf(pSolicitacao.getCodigoEntidade());
                        entidade = UtilSBPersistencia.getRegistroByID(classeEntidade, codigo, em);
                        EstruturaDeEntidade estrutura = MapaObjetosProjetoAtual.getEstruturaObjeto(classeEntidade);
                        for (ItfEstruturaCampoEntidade campo : estrutura.getCampos()) {

                        }

                        UtilSBPersistencia.fecharEM(em);
                    }
                }

                break;
            case "GET":
                break;
            case "OPTIONS":
                retornoProcessado = buildRespostaOptions(pSolicitacao);
                resposta.setRetorno(retornoProcessado);
                break;
            case "PUT":

                break;

        }
        return resposta;

    }

    @Override
    public String executarAcaoPacoteServico() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
