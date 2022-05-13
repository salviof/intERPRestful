package br.org.coletivoJava.fw.erp.implementacao.erpintegracao;

import com.super_bits.modulosSB.SBCore.modulos.erp.SolicitacaoControllerERP;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfBeanSimplesSomenteLeitura;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.erp.repositorioLinkEntidades.RepositorioLinkEntidadesGenerico;
import br.org.coletivoJava.fw.api.erp.erpintegracao.ApiIntegracaoRestful;
import br.org.coletivoJava.fw.api.erp.erpintegracao.model.ItfSistemaERPAtual;

import br.org.coletivoJava.fw.api.erp.erpintegracao.servico.ItfIntegracaoERP;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.SistemaERPAtual;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.SistemaERPConfiavel;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.parametros.ParametroListaRestful;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.parametros.ParametroRestful;
import com.super_bits.modulosSB.SBCore.modulos.erp.conversao.ItfConversorERRestfullToJson;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.servletRestfulERP.ServletRestfullERP;
import br.org.coletivoJava.integracoes.restInterprestfull.api.FabIntApiRestIntegracaoERPRestfull;
import com.super_bits.modulosSB.Persistencia.dao.UtilSBPersistencia;
import com.super_bits.modulosSB.Persistencia.dao.consultaDinamica.ConsultaDinamicaDeEntidade;
import com.super_bits.modulosSB.Persistencia.registro.persistidos.EntidadeSimples;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.MapaAcoesSistema;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreJson;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreReflexaoObjeto;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringValidador;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.transmissao_recepcao_rest_client.ItfAcaoApiRest;
import com.super_bits.modulosSB.SBCore.modulos.Controller.ErroChamadaController;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.ItfResposta;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.ItfRespostaAcaoDoSistema;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.acoes.ItfAcaoDoSistema;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.permissoes.ItfAcaoGerenciarEntidade;
import com.super_bits.modulosSB.SBCore.modulos.Controller.comunicacao.RespostaAcaoDoSistema;
import com.super_bits.modulosSB.SBCore.modulos.Controller.fabricas.FabTipoAcaoSistemaGenerica;
import com.super_bits.modulosSB.SBCore.modulos.chavesPublicasePrivadas.RepositorioChavePublicaPrivada;
import com.super_bits.modulosSB.SBCore.modulos.erp.ItfSistemaERP;
import com.super_bits.modulosSB.SBCore.modulos.geradorCodigo.model.EstruturaDeEntidade;
import com.super_bits.modulosSB.SBCore.modulos.objetos.MapaObjetosProjetoAtual;
import com.super_bits.modulosSB.SBCore.modulos.objetos.estrutura.ItfEstruturaCampoEntidade;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfBeanSimples;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import org.coletivojava.fw.api.tratamentoErros.FabErro;
import org.coletivojava.fw.utilCoreBase.UtilSBCoreReflexaoAPIERPRestFull;

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
    public List<String> getAcoesDisponiveis(ItfSistemaERP pSistema, String nomeAcao, ItfBeanSimples pParametro) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getAcoesDisponiveis(ItfSistemaERP pSistema, String nomeAcao) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getAcoesDisponiveis(ItfSistemaERP pSistema) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ItfRespostaAcaoDoSistema enviarChavePublica(ItfSistemaERP pSistema) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean adicionarSistemaConfianca(String pChavePublica, String url) {
        return false;
    }

    @Override
    public String gerarTokenUsuarioLogado(ItfSistemaERP pSistema) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ItfSistemaERPAtual getSistemaAtual() {
        MapaObjetosProjetoAtual.adcionarObjeto(ParametroListaRestful.class);
        MapaObjetosProjetoAtual.adcionarObjeto(ParametroRestful.class);
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
            sistemAtual.setUrlRecepcaoCodigo(urlStr + "/solicitacaoAuth2Recept/code/Usuario/" + getaoTpkenIdentificador);

        } catch (MalformedURLException ex) {
            throw new UnsupportedOperationException("o sistema atual não possui uma url válida");
        }

        return sistemAtual;
    }

    @Override
    public ItfSistemaERP getSistemaByChavePublica(String pChavePublica) {
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
    public ItfSistemaERP getSistemaByDominio(String pChavePublica) {
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
    public ItfSistemaERP getSistemaByHashChavePublica(String pHashChavePuvlica) {
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
    public ItfResposta getResposta(ItfSistemaERP pSistemaServico, String nomeAcao, ItfBeanSimples pParametro) {

        FabTipoAcaoSistemaGenerica tipoAcao = FabTipoAcaoSistemaGenerica.getAcaoGenericaByNome(nomeAcao);
        switch (tipoAcao) {
            case FORMULARIO_NOVO_REGISTRO:
                break;
            case FORMULARIO_EDITAR:
                break;
            case FORMULARIO_PERSONALIZADO:
                break;
            case FORMULARIO_VISUALIZAR:
                break;
            case FORMULARIO_LISTAR:

                ItfAcaoApiRest acaoListagem = FabIntApiRestIntegracaoERPRestfull.ACOES_GET_LISTA_ENTIDADES.getAcao(UtilSBRestful.getSolicitacaoAcaoListagemDeEntidade(getSistemaAtual(), pSistemaServico, nomeAcao, pParametro));
                return acaoListagem.getResposta();

            case FORMULARIO_MODAL:
                break;
            case SELECAO_DE_ACAO:
                break;
            case CONTROLLER_SALVAR_EDICAO:
            case CONTROLLER_SALVAR_NOVO:
            case CONTROLLER_SALVAR_MODO_MERGE:
            case CONTROLLER_PERSONALIZADO:
            case CONTROLLER_ATIVAR_DESATIVAR:
            case CONTROLLER_ATIVAR:
            case CONTROLLER_REMOVER:
            case CONTROLLER_DESATIVAR:
                ItfAcaoApiRest acao = FabIntApiRestIntegracaoERPRestfull.ACOES_EXECUTAR_CONTROLLER.getAcao(UtilSBRestful.getSolicitacaoAcaoController(getSistemaAtual(), pSistemaServico, nomeAcao, pParametro));
                return acao.getResposta();

            case GERENCIAR_DOMINIO:
                break;
            default:
                throw new AssertionError();
        }

        throw new UnsupportedOperationException("Tipo de ação não detectado");

    }

    @Override
    public ItfBeanSimples gerarConversaoJsonStringToObjeto(ItfSistemaERP pSistema, String pJson) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ItfBeanSimples gerarConversaoJsonStringToObjeto(String pJson) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ItfBeanSimples gerarConversaoJsonToObjeto(ItfSistemaERP pSistema, JsonObject pJson) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ItfBeanSimples gerarConversaoJsonToObjeto(JsonObject pJson) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JsonObject gerarConversaoObjetoToJson(ItfSistemaERP pSistema, ItfBeanSimples pBeanSimples) {
        // ERPIntegracaoSistemasApi.RESTFUL.getRepositorioLinkEntidadesByID().getCodigoApiExterna(pEntidade, 0);
        // ERPIntegracaoSistemasApi.RESTFUL.getDTO(pBeanSimples, pItefaceObjeto);
        if (pBeanSimples == null) {
            return JsonObject.EMPTY_JSON_OBJECT;
        }
        String caminhoImplementacaoConversor = UtilSBCoreReflexaoAPIERPRestFull.gerarCaminhoCompletoClasseObjetoToJson(pSistema, UtilSBCoreReflexaoObjeto.getClassExtraindoProxy(pBeanSimples.getClass().getSimpleName()));
        boolean temImplementacao = false;
        Class classeConversor = null;
        try {
            classeConversor = pBeanSimples.getClass().getClassLoader().loadClass(caminhoImplementacaoConversor);
            if (classeConversor != null) {
                temImplementacao = true;
            }
            try {
                Constructor cstructor = classeConversor.getConstructor(ItfSistemaERP.class);
                ItfConversorERRestfullToJson conversor = (ItfConversorERRestfullToJson) cstructor.newInstance(pSistema);
                return conversor.getJson(pBeanSimples);
            } catch (InstantiationException | IllegalAccessException ex) {
                SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Falha instanciando conversor " + classeConversor, ex);
            } catch (NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException ex) {
                Logger.getLogger(ApiIntegracaoRestfulimpl.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ClassNotFoundException ex) {
            temImplementacao = false;
        }

        JsonObject json = UtilSBRestFulEntityToJson.getJsonFromObjetoGenerico(pBeanSimples);
        return json;

    }

    @Override
    public JsonObject gerarConversaoObjetoToJson(ItfBeanSimples pJson) {
        return UtilSBRestFulEntityToJson.getJsonFromObjetoGenerico(pJson);

    }

    public static JsonObject buildRespostaOptionsServico(SolicitacaoControllerERP pSolicitacao) {
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
    public ItfRespostaAcaoDoSistema gerarRespostaAcaoDoSistemaServico(SolicitacaoControllerERP pSolicitacao) {

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
                return resposta;
            } else {
                resposta = new RespostaAcaoDoSistema(acao);
            }
        }
        String metodo = pSolicitacao.getMetodo();
        if (resposta == null) {
            if (metodo.equals("OPTIONS")) {
                resposta = new RespostaAcaoDoSistema();
            } else {
                resposta = new RespostaAcaoDoSistema();
                resposta.addErro("Apenas o método OPTIONS suporta AÇÃO nula");
                return resposta;
            }
        }

        JsonObject retornoProcessado;
        JsonObject parametroSolicitacao = null;
        if (!UtilSBCoreStringValidador.isNuloOuEmbranco(pSolicitacao.getCorpoParametros())) {
            parametroSolicitacao = UtilSBCoreJson.getJsonObjectByTexto(pSolicitacao.getCorpoParametros());
        }
        switch (metodo) {
            case "POST":
                ItfBeanSimples entidade = null;
                Class<? extends EntidadeSimples> classeEntidade = null;
                if (acao != null) {
                    classeEntidade = acao.getAcaoDeGestaoEntidade().getClasseRelacionada();
                }
                EntityManager em = UtilSBPersistencia.getEntyManagerPadraoNovo();
                try {
                    if (!UtilSBCoreStringValidador.isNuloOuEmbranco(pSolicitacao.getCodigoEntidade()) || pSolicitacao.getCodigoEntidade().equals("0")) {
                        try {
                            entidade = (ItfBeanSimples) classeEntidade.newInstance();
                        } catch (InstantiationException | IllegalAccessException ex) {
                            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Falha instanciando nova entidade" + entidade, ex);
                        }

                    } else {

                        int codigo = Integer.valueOf(pSolicitacao.getCodigoEntidade());
                        entidade = UtilSBPersistencia.getRegistroByID(classeEntidade, codigo, em);
                        EstruturaDeEntidade estrutura = MapaObjetosProjetoAtual.getEstruturaObjeto(classeEntidade);
                        for (ItfEstruturaCampoEntidade campo : estrutura.getCampos()) {
                            try {
                                SBCore.getServicoController().getResposta(acao.getEnumAcaoDoSistema(), entidade);
                            } catch (ErroChamadaController ex) {
                                SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro localizando médodo de execução da ação " + acao, ex);
                            }
                        }

                    }
                } finally {
                    UtilSBPersistencia.fecharEM(em);
                }
                UtilSBRestFulJsonToEntity.aplicarAtributosJsonEmEntidade(entidade, parametroSolicitacao);
                 {
                    try {
                        resposta = SBCore.getServicoController().getResposta(acao.getEnumAcaoDoSistema(), entidade);
                    } catch (ErroChamadaController ex) {
                        Logger.getLogger(ApiIntegracaoRestfulimpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                return resposta;

            case "GET":
                pSolicitacao.getAcaoStrNomeUnico();

                switch (acao.getTipoAcaoGenerica()) {
                    case FORMULARIO_NOVO_REGISTRO:
                    case FORMULARIO_EDITAR:
                    case FORMULARIO_PERSONALIZADO:
                    case FORMULARIO_VISUALIZAR:
                        resposta = new RespostaAcaoDoSistema();
                        resposta.addErro("O retorno de estrutura de formulários ainda não foi implementado");
                        return resposta;

                    case FORMULARIO_LISTAR:
                        EntityManager emLista = UtilSBPersistencia.getEntyManagerPadraoNovo();
                        try {
                            Class entidadeListagem = acao.getComoAcaoDeEntidade().getClasseRelacionada();
                            List lista = UtilSBPersistencia.getListaTodos(entidadeListagem, emLista);

                            resposta.setRetorno(lista);
                            return resposta;
                        } finally {
                            UtilSBPersistencia.fecharEM(emLista);
                        }

                    case FORMULARIO_MODAL:
                        break;
                    case SELECAO_DE_ACAO:
                        resposta = new RespostaAcaoDoSistema();
                        resposta.addErro("Este tipo de ação não foi implementado");
                        return resposta;

                    case CONTROLLER_SALVAR_EDICAO:
                    case CONTROLLER_SALVAR_NOVO:
                    case CONTROLLER_SALVAR_MODO_MERGE:
                    case CONTROLLER_PERSONALIZADO:
                    case CONTROLLER_ATIVAR_DESATIVAR:
                    case CONTROLLER_ATIVAR:
                    case CONTROLLER_REMOVER:
                    case CONTROLLER_DESATIVAR:
                        resposta = new RespostaAcaoDoSistema();
                        resposta.addErro("Ação " + pSolicitacao.getAcaoStrNomeUnico() + "do tipo controller suporta apenas PUT e POST");
                        return resposta;
                    case GERENCIAR_DOMINIO:
                        break;

                    default:
                        throw new AssertionError();
                }

            case "OPTIONS":
                retornoProcessado = buildRespostaOptionsServico(pSolicitacao);
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

    @Override
    public String getRespostaJsonString(ItfRespostaAcaoDoSistema pResposta
    ) {

        return UtilSBRestful.buildTextoJsonResposta(pResposta);

    }

}
