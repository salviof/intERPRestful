/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.coletivoJava.fw.projetos.integracao.api.cucumber.fluxooauthinteracaousuario;

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
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringFiltros;
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
        features = "classpath:cucumber", tags = "@FluxoOauthInteracaoUsuario",
        glue = "org.coletivoJava.fw.projetos.integracao.implementacao.cucumber.fluxooauthinteracaousuario.etapas",
        monochrome = false, dryRun = false)
public class FluxoOauth2UsuarioAcessoRestfull extends TesteIntegracaoFuncionalidadeCucumber {

    public static final String EMAIL_USUARIO_AUTENTICADO = "cliente@sistemaerp2.com.br";
    public static SistemaERPConfiavel sistemaServidorRecursos;
    public static String URL_ENTREGA_CODIGO_CONCESSAO_TOKEN;
    public static SistemaERPConfiavel sistemaCliente;
    public static String respostaServidorOauthObtencaoCodigoDeAcesso;

    public static boolean criouAplicativo = false;

    public static final ServletOauth2Server servletCodConcessaoTokenService = new ServletOauth2Server();

    public static final ServletRecepcaoOauth servletRecepcaoOauth = new ServletRecepcaoOauth();

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
        sistemaCliente = (SistemaERPAtual) erp.getSistemaAtual();
        buildAplicativoAcesso();
    }

    public static SistemaERPConfiavel buildAplicativoAcesso() {

        if (sistemaServidorRecursos != null) {

            return sistemaServidorRecursos;
        }
        criouAplicativo = true;

        sistemaServidorRecursos = new SistemaERPConfiavel();
        sistemaServidorRecursos.setNome("Sistema Remoto Demonstração");
        sistemaServidorRecursos.setDominio("crm.casanovadigital.com.br");
        sistemaServidorRecursos.setUrlPublicaEndPoint("https://crm.casanovadigital.com.br/" + ServletRestfullERP.SLUGPUBLICACAOSERVLET);

        sistemaServidorRecursos.setUrlRecepcaoCodigo("não Se Aplica apenas servidor");
        Map<String, String> parDeChaves = UtilSBCoreCriptoRSA.chavePublicaPrivada();
        sistemaServidorRecursos.setChavePublica(parDeChaves.keySet().stream().findFirst().get());
        chavePrivadaDoAplicativoConfiavel = parDeChaves.values().stream().findFirst().get();

        sistemaServidorRecursos = UtilSBPersistencia.mergeRegistro(sistemaServidorRecursos);

        ItfIntegracaoERP erp = ERPIntegracaoSistemasApi.RESTFUL.getImplementacaoDoContexto();
        SistemaERPAtual sistemaClienteRef = (SistemaERPAtual) erp.getSistemaAtual();

        sistemaCliente = new SistemaERPConfiavel();
        sistemaCliente.setChavePublica(sistemaClienteRef.getChavePublica());
        sistemaCliente.setHashChavePublica(sistemaClienteRef.getHashChavePublica());
        sistemaCliente.setNome("Sistema Cliente Demostração");
        String urlRecpcaoCodigo = "http://localhost:8066/solicitacaoAuth2Recept/code/Usuario/" + GestaoTokenRestInterprestfull.class.getSimpleName() + "/" + "UTF8";
        sistemaCliente.setUrlRecepcaoCodigo(urlRecpcaoCodigo);
        sistemaCliente.setUrlPublicaEndPoint("http://localhost:8066/" + ServletRestfullERP.SLUGPUBLICACAOSERVLET);
        sistemaCliente.setDominio(sistemaClienteRef.getDominio());
        UtilSBPersistencia.mergeRegistro(sistemaCliente);
        System.out.println(intro);
        printSistema(sistemaServidorRecursos);
        printSistema(sistemaCliente);
        return sistemaServidorRecursos;
    }

    public static void printSistema(SistemaERPConfiavel pSistema) {
        System.out.println("______________________________________________");
        System.out.println("|Nome:           " + UtilSBCoreStringFiltros.getLpad(pSistema.getNome(), 29, " ") + "|");
        System.out.println("|Chave pública   " + UtilSBCoreStringFiltros.getLpad(pSistema.getHashChavePublica(), 29, " ") + "|");
        System.out.println("|Domímio         " + UtilSBCoreStringFiltros.getLpad(pSistema.getDominio(), 29, " ") + "|");
        System.out.println("|UrlEndPoint     " + UtilSBCoreStringFiltros.getLpad(pSistema.getUrlPublicaEndPoint(), 29, " ") + "|");
        System.out.println("|Urlrecepção COD " + UtilSBCoreStringFiltros.getLpad(pSistema.getUrlRecepcaoCodigo(), 29, " ") + "|");
        System.out.println("______________________________________________");
    }

    @AfterClass
    public static void finalScenario() {
        DevOpsCucumberPersistenciaMysql.commpilarResultadoRequisito(FluxoOauth2UsuarioAcessoRestfull.class);
    }

    @Override
    public EntityManager renovarConexao() {
        return super.renovarConexao();
    }

    private static final String intro = "    +--------+                                +---------------+\n"
            + "   1 |        |-(A)Solict. cod. de concessão->|   Resource    |\n"
            + "     |        |                               |     Owner     |\n"
            + "   2 |        |<-(B)-- Codigo de concessão ---|               |\n"
            + "     |        |                               +---------------+\n"
            + "     |        |\n"
            + "     |        |                               +---------------+\n"
            + "   3 |        |--(C)-- Código de concessao -->| Authorization |\n"
            + "     | Client |                               |     Server    |\n"
            + "   4 |        |<-(D)-----Token de acesso -----|               |\n"
            + "     |        |                               +---------------+\n"
            + "     |        |\n"
            + "     |        |                               +---------------+\n"
            + "  5  |        |--(E)----- Token de cacesso -->|    Resource   |\n"
            + "     |        |                               |     Server    |\n"
            + "  6  |        |<-(F)--- Recurso protegido  ---|               |\n"
            + "     +--------+                               +---------------+";

}
