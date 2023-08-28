package br.org.coletivoJava.fw.erp.implementacao.erpintegracao;

import com.super_bits.modulosSB.SBCore.modulos.erp.SolicitacaoControllerERP;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfBeanSimplesSomenteLeitura;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.erp.repositorioLinkEntidades.RepositorioLinkEntidadesGenerico;
import br.org.coletivoJava.fw.api.erp.erpintegracao.ApiIntegracaoRestful;

import br.org.coletivoJava.fw.api.erp.erpintegracao.servico.ItfIntegracaoERP;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.SistemaERPAtual;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.SistemaERPConfiavel;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.parametros.ParametroListaRestful;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.parametros.ParametroRestful;
import com.super_bits.modulosSB.SBCore.modulos.erp.conversao.ItfConversorERRestfullToJson;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.servletRestfulERP.ServletRestfullERP;
import br.org.coletivoJava.integracoes.restInterprestfull.api.FabIntApiRestIntegracaoERPRestfull;
import com.super_bits.modulosSB.Persistencia.ConfigGeral.SBPersistencia;
import com.super_bits.modulosSB.Persistencia.dao.UtilSBPersistencia;
import com.super_bits.modulosSB.Persistencia.dao.consultaDinamica.ConsultaDinamicaDeEntidade;
import com.super_bits.modulosSB.Persistencia.registro.persistidos.EntidadeSimples;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.MapaAcoesSistema;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreJson;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreReflexaoObjeto;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringFiltros;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringValidador;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.transmissao_recepcao_rest_client.ItfAcaoApiRest;
import com.super_bits.modulosSB.SBCore.modulos.Controller.ErroChamadaController;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.ItfResposta;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.ItfRespostaAcaoDoSistema;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.acoes.ItfAcaoDoSistema;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.acoes.ItfAcaoSecundaria;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.permissoes.ItfAcaoGerenciarEntidade;
import com.super_bits.modulosSB.SBCore.modulos.Controller.comunicacao.RespostaAcaoDoSistema;
import com.super_bits.modulosSB.SBCore.modulos.Controller.fabricas.FabTipoAcaoSistemaGenerica;
import com.super_bits.modulosSB.SBCore.modulos.chavesPublicasePrivadas.RepositorioChavePublicaPrivada;
import com.super_bits.modulosSB.SBCore.modulos.erp.ItfSistemaERP;
import com.super_bits.modulosSB.SBCore.modulos.geradorCodigo.model.EstruturaCampo;
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
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import org.coletivojava.fw.api.tratamentoErros.FabErro;
import org.coletivojava.fw.utilCoreBase.UtilSBCoreReflexaoAPIERPRestFull;
import br.org.coletivoJava.fw.api.erp.erpintegracao.model.ItfSistemaERPLocal;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.SistemaErpChaveLocal;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.ItfRespostaWebServiceSimples;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token.ItfTokenGestaoOauth;
import com.super_bits.modulosSB.SBCore.modulos.Mensagens.FabTipoAgenteDoSistema;
import java.util.HashMap;

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
    private static ItfSistemaERPLocal chaveLocalPadrao;

    @Override
    public ItfSistemaERPLocal getSistemaAtual() {
        if (chaveLocalPadrao != null) {
            return chaveLocalPadrao;
        }
        MapaObjetosProjetoAtual.adcionarObjeto(ParametroListaRestful.class);
        MapaObjetosProjetoAtual.adcionarObjeto(ParametroRestful.class);
        SistemaERPAtual sistemAtual = new SistemaERPAtual();
        sistemAtual.setNome(SBCore.getGrupoProjeto());

        String idChave = SBCore.getConfigModulo(FabConfigModuloWebERPChaves.class).getPropriedade(FabConfigModuloWebERPChaves.PAR_DE_CHAVES_IDENTIFICADOR);
        System.out.println("buscando por" + idChave);
        Map<String, String> par = RepositorioChavePublicaPrivada.getParDeChavesPubPrivada(idChave);
        if (par == null) {
            throw new UnsupportedOperationException("O par de chaves local do sistema não foi encontrado buscando pelo par com hash " + idChave);
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

            sistemAtual.setUrlRecepcaoCodigo(urlStr + "/solicitacaoAuth2Recept/code/" + FabTipoAgenteDoSistema.USUARIO.name() + "/" + getaoTpkenIdentificador);

        } catch (MalformedURLException ex) {
            throw new UnsupportedOperationException("o sistema atual não possui uma url válida");
        }
        chaveLocalPadrao = sistemAtual;
        if (SBCore.isEmModoDesenvolvimento()) {
            System.out.println("---------------------- OBTENDO DADOS SISTEMA ATUAL CONEXÇÃO ERP ---------------------------------");
            printSistema(sistemAtual);

        }
        return sistemAtual;
    }

    private static void printSistema(SistemaERPConfiavel pSistema) {
        System.out.println("______________________________________________");
        System.out.println("|Nome:           " + UtilSBCoreStringFiltros.getLpad(pSistema.getNome(), 29, " ") + "|");
        System.out.println("|Chave pública   " + UtilSBCoreStringFiltros.getLpad(pSistema.getHashChavePublica(), 29, " ") + "|");
        System.out.println("|Domímio         " + UtilSBCoreStringFiltros.getLpad(pSistema.getDominio(), 29, " ") + "|");
        System.out.println("|UrlEndPoint     " + UtilSBCoreStringFiltros.getLpad(pSistema.getUrlPublicaEndPoint(), 29, " ") + "|");
        System.out.println("|Urlrecepção COD " + UtilSBCoreStringFiltros.getLpad(pSistema.getUrlRecepcaoCodigo(), 29, " ") + "|");
        System.out.println("______________________________________________");
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

        return MapaSistemasConfiaveis.getSistemaByHashChavePublica(pHashChavePuvlica);

    }

    private ItfResposta getResposta(ItfSistemaERP pSistemaServico, String nomeAcao, ItfBeanSimples pParametro, boolean pRenovarTokenCasoFalha) {
        boolean acessarComoAdmin = false;

        if (pSistemaServico.getEmailusuarioAdmin() != null && !pSistemaServico.getEmailusuarioAdmin().isEmpty()) {
            if (pSistemaServico.getEmailusuarioAdmin().equals(SBCore.getUsuarioLogado().getEmail())) {
                acessarComoAdmin = true;
            }
        }

        FabTipoAcaoSistemaGenerica tipoAcao = FabTipoAcaoSistemaGenerica.getAcaoGenericaByNome(nomeAcao);
        ItfRespostaWebServiceSimples respostaREquisicao = null;
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

                ItfAcaoApiRest acaoListagem = FabIntApiRestIntegracaoERPRestfull.ACOES_GET_LISTA_ENTIDADES
                        .getAcao(UtilSBRestful.getSolicitacaoAcaoListagemDeEntidade(getSistemaAtual(), pSistemaServico, nomeAcao, acessarComoAdmin, pParametro)
                        );
                respostaREquisicao = acaoListagem.getResposta();

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
                respostaREquisicao = acao.getResposta();

            case GERENCIAR_DOMINIO:
                break;
            default:
                throw new AssertionError();
        }
        if (respostaREquisicao != null && respostaREquisicao.isSucesso()) {
            return respostaREquisicao;
        } else {
            if (pRenovarTokenCasoFalha && respostaREquisicao.getCodigoResposta() >= 400 && respostaREquisicao.getCodigoResposta() < 500) {
                ItfTokenGestaoOauth gestao = FabIntApiRestIntegracaoERPRestfull.getGestaoTokenOpcoes(pSistemaServico);
                gestao.excluirToken();
                gestao.gerarNovoToken();
                return getResposta(pSistemaServico, nomeAcao, pParametro, false);

            }
            return respostaREquisicao;

        }

    }

    @Override
    public ItfResposta getResposta(ItfSistemaERP pSistemaServico, String nomeAcao, ItfBeanSimples pParametro) {
        return getResposta(pSistemaServico, nomeAcao, pParametro, true);

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
    public JsonObject gerarConversaoObjetoToJson(ItfSistemaERP pSistema, ItfBeanSimples pBeanSimples, boolean pUsarIdComoIdRemotoCorrespondente) {
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

        JsonObject json = UtilSBRestFulEntityToJson.getJsonFromObjetoGenerico(pBeanSimples, pUsarIdComoIdRemotoCorrespondente);
        return json;

    }

    @Override
    public JsonObject gerarConversaoObjetoToJson(ItfBeanSimples pItemSimples) {
        return UtilSBRestFulEntityToJson.getJsonFromObjetoGenerico(pItemSimples, false);

    }

    public static JsonObject buildRespostaOptionsServico(SolicitacaoControllerERP pSolicitacao) {
        try {
            JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
            jsonBuilder.add("scope", pSolicitacao.getUsuarioSolicitante().getEmail());
            jsonBuilder.add("modulo", pSolicitacao.getUsuarioSolicitante().getGrupo().getModuloPrincipal().getNome());
            JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
            if (pSolicitacao.isAutenticadoComSucesso() && pSolicitacao.getAcaoStrNomeUnico() != null) {

                ItfAcaoGerenciarEntidade acaoGEstao = MapaAcoesSistema.getAcaoDoSistemaByNomeUnico(pSolicitacao.getAcaoStrNomeUnico()).getAcaoDeGestaoEntidade();
                List<ItfAcaoSecundaria> acoes = acaoGEstao.getAcoesVinculadas();
                acoes.stream().map(ac -> ac.getNomeUnico()).forEach(jsonArrayBuilder::add);
                jsonBuilder.add("acoes", jsonArrayBuilder.build());

            } else {

                if (pSolicitacao.isAutenticadoComSucesso() && pSolicitacao.getAcaoStrNomeUnico() == null) {
                    //mostra apenas as ações do usuário por questão de segurança
                    List<ItfAcaoGerenciarEntidade> acoesDoUsuario = new ArrayList();

                    List<ItfAcaoGerenciarEntidade> acoes = MapaAcoesSistema.getListaTodasGestao();

                    acoes.stream().filter(ac -> SBCore.getServicoPermissao().isAcaoPermitidaUsuario(pSolicitacao.getUsuarioSolicitante(), ac))
                            .forEach(acoesDoUsuario::add);

                    acoesDoUsuario.stream().map(ac -> ac.getNomeUnico()).forEach(jsonArrayBuilder::add);
                    jsonBuilder.add("acoes", jsonArrayBuilder.build());

                }
            }
            JsonObject retorno = jsonBuilder.build();
            return retorno;

        } catch (Throwable ex) {
            SBCore.RelatarErroAoUsuario(FabErro.SOLICITAR_REPARO, "Falha criando resposta com opções de endPoint", ex);
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
            final String acaoNomeUnico = pSolicitacao.getAcaoStrNomeUnico();
            acao = MapaAcoesSistema.getAcaoDoSistemaByNomeUnico(acaoNomeUnico);
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
                    classeEntidade = acao.getComoAcaoDeEntidade().getClasseRelacionada();
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
                        resposta.addErro("Falha executando ação: " + resposta.getAcaoVinculada().getNomeUnico() + "| Erro:" + ex.getMessage());
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
                            List lista = new ArrayList<>();
                            Class entidadeListagem = acao.getComoAcaoDeEntidade().getClasseRelacionada();
                            if (pSolicitacao.getParametrosDeUrl() == null || pSolicitacao.getParametrosDeUrl().isEmpty()) {
                                lista = UtilSBPersistencia.getListaTodos(entidadeListagem, emLista, 10);
                                resposta.setRetorno(lista);
                            } else {
                                ConsultaDinamicaDeEntidade consultaDinamica = new ConsultaDinamicaDeEntidade(entidadeListagem, emLista);
                                EstruturaDeEntidade estutura = MapaObjetosProjetoAtual.getEstruturaObjeto(entidadeListagem);

                                try {
                                    for (Map.Entry<String, String> chaves : pSolicitacao.getParametrosDeUrl().entrySet()) {
                                        String valor = chaves.getValue();
                                        EstruturaCampo estruturaCampo = estutura.getCampoByNomeDeclarado(chaves.getKey());
                                        System.out.println("pesauisando via RESTfull");
                                        System.out.println(chaves.getKey());
                                        System.out.println(chaves.getValue());
                                        System.out.println(estruturaCampo.getTipoPrimitivoDoValor());
                                        String valorStr = chaves.getValue().toString();
                                        switch (estruturaCampo.getTipoPrimitivoDoValor()) {
                                            case INTEIRO:

                                                Integer valorInteiro = Integer.valueOf(valorStr);
                                                consultaDinamica.addcondicaoCampoIgualA(chaves.getKey(), valorInteiro);
                                                break;
                                            case NUMERO_LONGO:
                                                consultaDinamica.addcondicaoCampoIgualA(chaves.getKey(), chaves.getValue());
                                                break;
                                            case LETRAS:
                                                consultaDinamica.addcondicaoCampoIgualA(chaves.getKey(), chaves.getValue());
                                                break;
                                            case DATAS:
                                                Date data = new Date(Long.valueOf(valor));
                                                consultaDinamica.addcondicaoCampoIgualA(chaves.getKey(), data);
                                                break;
                                            case BOOLEAN:
                                                if (valor.equals("1") || valor.equals("true") || valor.equals("sim") || valor.equals("verdadeiro")) {
                                                    consultaDinamica.addCondicaoPositivo(chaves.getKey());
                                                } else {
                                                    consultaDinamica.addCondicaoNegativo(chaves.getKey());
                                                }
                                                break;
                                            case DECIMAL:
                                                consultaDinamica.addcondicaoCampoIgualA(chaves.getKey(), Double.valueOf(chaves.getValue()));
                                                break;
                                            case ENTIDADE:
                                                consultaDinamica.addcondicaoCampoIgualA(chaves.getKey() + "_id", chaves.getKey());
                                                break;
                                            case OUTROS_OBJETOS:
                                                break;
                                            default:
                                                throw new AssertionError(estruturaCampo.getTipoPrimitivoDoValor().name());

                                        }

                                    }
                                } catch (Throwable t) {
                                    resposta.addErro("Falha configurando parametros de pesquisa: " + t.getMessage());
                                }
                                lista = consultaDinamica.resultadoRegistros();
                                if (!UtilSBCoreStringValidador.isNuloOuEmbranco(pSolicitacao.getAtributoEntidade())) {
                                    if (lista.isEmpty()) {
                                        resposta.addErro("Impossível acessar os atributos pois nenhuma entidade foi encontrada");
                                    }
                                    if (lista.size() > 1) {
                                        resposta.addErro("Impossível acessar o atributo, pois a pesquisa retornou mais de um registro");
                                    }
                                    if (lista.size() == 1) {
                                        resposta.setRetorno(((ItfBeanSimples) lista.get(0)).getCPinst(pSolicitacao.getAtributoEntidade()).getValor());
                                    }
                                } else {
                                    resposta.setRetorno(lista);
                                }
                            }

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

    private static Map<String, ItfSistemaERP> sistemasRegistrados = new HashMap<>();

    @Override
    public ItfSistemaERP getSistemaByChavePublica(String pChavePublica) {

        if (sistemasRegistrados.containsKey(pChavePublica)) {
            return sistemasRegistrados.get(pChavePublica);
        }

        EntityManager em = UtilSBPersistencia.getEntyManagerPadraoNovo();
        try {
            ConsultaDinamicaDeEntidade novaConsulta = new ConsultaDinamicaDeEntidade(SistemaERPConfiavel.class, em);
            novaConsulta.addcondicaoCampoIgualA("chavePublica", pChavePublica);
            List<SistemaERPConfiavel> sistemaConfiavel = novaConsulta.resultadoRegistros();
            if (sistemaConfiavel.isEmpty()) {
                novaConsulta = new ConsultaDinamicaDeEntidade(SistemaErpChaveLocal.class, em);
                sistemaConfiavel = novaConsulta.resultadoRegistros();
                if (sistemaConfiavel.isEmpty()) {
                    return null;
                } else {
                    sistemasRegistrados.put(pChavePublica, sistemaConfiavel.get(0));
                    return sistemaConfiavel.get(0);
                }

            } else {
                sistemasRegistrados.put(pChavePublica, sistemaConfiavel.get(0));
                return sistemaConfiavel.get(0);
            }
        } finally {
            UtilSBPersistencia.fecharEM(em);
        }

    }

    @Override
    public ItfSistemaERPLocal getSistemaLocalByHashChavePublica(String pHashChavePuvlica) {
        if (getSistemaAtual() != null) {
            if (getSistemaAtual().getHashChavePublica().equals(pHashChavePuvlica)) {
                return getSistemaAtual();
            }
        }

        if (sistemasRegistrados.containsKey(pHashChavePuvlica)) {
            if (sistemasRegistrados.get(pHashChavePuvlica) instanceof ItfSistemaERPLocal) {
                return (ItfSistemaERPLocal) sistemasRegistrados.get(pHashChavePuvlica);
                //throw new UnsupportedOperationException("O sistema com hash público " + pHashChavePuvlica + " foi encontrado, mas não possui chave privada");
            }

        }

        EntityManager em = UtilSBPersistencia.getEntyManagerPadraoNovo();
        try {
            ConsultaDinamicaDeEntidade novaConsulta = new ConsultaDinamicaDeEntidade(SistemaErpChaveLocal.class, em);
            List<SistemaErpChaveLocal> sistemaConfiavel = novaConsulta.resultadoRegistros();
            if (!sistemaConfiavel.isEmpty()) {
                sistemasRegistrados.put(pHashChavePuvlica, sistemaConfiavel.get(0));
                return sistemaConfiavel.get(0);
            }
        } finally {
            UtilSBPersistencia.fecharEM(em);
        }
        return null;
    }
}
