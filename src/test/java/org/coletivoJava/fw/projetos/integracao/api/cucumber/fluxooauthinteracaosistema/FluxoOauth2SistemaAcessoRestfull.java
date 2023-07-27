/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.coletivoJava.fw.projetos.integracao.api.cucumber.fluxooauthinteracaosistema;

import br.org.coletivoJava.fw.api.erp.erpintegracao.contextos.ERPIntegracaoSistemasApi;
import br.org.coletivoJava.fw.api.erp.erpintegracao.servico.ItfIntegracaoERP;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.ConfigCoreApiIntegracao;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.SistemaERPAtual;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.SistemaERPConfiavel;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.servletOauthServer.ServletOauth2Server;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.servletRestfulERP.ServletRestfullERP;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.teste.ConfigPercistenciaItegracaoSistemas;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.teste.simulacaoComunicacao.EnvelopeServeletSolicitarCodigoAcessoAoToken;
import br.org.coletivoJava.integracoes.restInterprestfull.implementacao.GestaoTokenRestInterprestfull;
import com.super_bits.modulosSB.Persistencia.ConfigGeral.SBPersistencia;
import com.super_bits.modulosSB.Persistencia.dao.UtilSBPersistencia;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreCriptoRSA;
import com.super_bits.modulosSB.webPaginas.controller.servlets.serveletRecepConcessaoOauthSisToSis.ServletRecepcaoOauthEntreSistemas;
import com.super_bits.modulosSB.webPaginas.controller.servlets.servletRecepcaoOauth.ServletRecepcaoOauth;
import cucumber.api.CucumberOptions;
import java.util.Map;
import javax.persistence.EntityManager;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import testesFW.cucumber.CucumberSBTestes;
import testesFW.cucumber.TesteIntegracaoFuncionalidadeCucumber;
import testesFW.devOps.DevOpsCucumberPersistenciaMysql;

/**
 *
 * @author salvio
 */
@RunWith(CucumberSBTestes.class)
@CucumberOptions(
        features = "classpath:cucumber", tags = "@FluxoOauthInteracaoSistema",
        glue = "org.coletivoJava.fw.projetos.integracao.implementacao.cucumber.fluxooauthinteracaosistema.etapas",
        monochrome = false, dryRun = false)
public class FluxoOauth2SistemaAcessoRestfull extends TesteIntegracaoFuncionalidadeCucumber {

    public static final String EMAIL_USUARIO_AUTENTICADO = "salviof@gmail.com";
    public static SistemaERPConfiavel sistemaCliente;
    public static String URL_ENTREGA_CODIGO_CONCESSAO_TOKEN;
    public static SistemaERPConfiavel sistemaRemoto;
    public static String respostaServidorOauthObtencaoCodigoDeAcesso;

    public static boolean criouAplicativo = false;

    public static final ServletOauth2Server servletCodConcessaoTokenService = new ServletOauth2Server();

    public static final ServletRecepcaoOauthEntreSistemas servletRecepcaoOauthSistema = new ServletRecepcaoOauthEntreSistemas();

    public static String chavePrivadaDoAplicativoConfiavel;

    public static EnvelopeServeletSolicitarCodigoAcessoAoToken envelopeSolicitacaoCodigoDeAcesso;
    public static EnvelopeServeletSolicitarCodigoAcessoAoToken solicitacaoTokenPosLogin;
    public static String urlRecepcaoCodigoAcesso;
    public static String codigoConcessaoToken;

    @Override
    protected void configAmbienteDesevolvimento() {
        SBCore.configurar(new ConfigCoreApiIntegracao(), SBCore.ESTADO_APP.DESENVOLVIMENTO);
        SBPersistencia.configuraJPA(new ConfigPercistenciaItegracaoSistemas());

        ItfIntegracaoERP erp = ERPIntegracaoSistemasApi.RESTFUL.getImplementacaoDoContexto();
        sistemaRemoto = (SistemaERPAtual) erp.getSistemaAtual();
        buildAplicativoAcesso();
    }

    public static SistemaERPConfiavel buildAplicativoAcesso() {

        if (sistemaCliente != null) {

            return sistemaCliente;
        }
        criouAplicativo = true;

        sistemaCliente = new SistemaERPConfiavel();
        sistemaCliente.setNome("Sistema Cliente Demonstração");
        sistemaCliente.setDominio("crm.casanovadigital.com.br");
        sistemaCliente.setUrlPublicaEndPoint("https://crm.casanovadigital.com.br/" + ServletRestfullERP.SLUGPUBLICACAOSERVLET);
        String urlRecpcaoCodigo = "https://crm.coletivojava.com.br/solicitacaoAuth2Recept/code/Usuario/" + GestaoTokenRestInterprestfull.class.getSimpleName() + "/" + "UTF8";
        sistemaCliente.setUrlRecepcaoCodigo(urlRecpcaoCodigo);
        Map<String, String> parDeChaves = UtilSBCoreCriptoRSA.chavePublicaPrivada();
        sistemaCliente.setChavePublica(parDeChaves.keySet().stream().findFirst().get());
        chavePrivadaDoAplicativoConfiavel = parDeChaves.values().stream().findFirst().get();

        sistemaCliente = UtilSBPersistencia.mergeRegistro(sistemaCliente);

        ItfIntegracaoERP erp = ERPIntegracaoSistemasApi.RESTFUL.getImplementacaoDoContexto();
        SistemaERPAtual sistemaRemotoRef = (SistemaERPAtual) erp.getSistemaAtual();

        sistemaRemoto = new SistemaERPConfiavel();
        sistemaRemoto.setChavePublica(sistemaRemotoRef.getChavePublica());
        sistemaRemoto.setHashChavePublica(sistemaRemotoRef.getHashChavePublica());
        sistemaRemoto.setNome("Sistema remoto Demostração");
        sistemaRemoto.setUrlRecepcaoCodigo("http://localhost:8066");
        sistemaRemoto.setUrlPublicaEndPoint("http://localhost:8066/" + ServletRestfullERP.SLUGPUBLICACAOSERVLET);
        sistemaRemoto.setDominio(sistemaRemotoRef.getDominio());
        UtilSBPersistencia.mergeRegistro(sistemaRemoto);

        return sistemaCliente;
    }

    @AfterClass
    public static void finalScenario() {
        DevOpsCucumberPersistenciaMysql.commpilarResultadoRequisito(FluxoOauth2SistemaAcessoRestfull.class);
    }

    @Override
    public EntityManager renovarConexao() {
        return super.renovarConexao();
    }

}
