/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.teste;

import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreCriptoRSA;
import br.org.coletivoJava.fw.api.erp.erpintegracao.contextos.ERPIntegracaoSistemasApi;
import br.org.coletivoJava.fw.api.erp.erpintegracao.servico.ItfIntegracaoERP;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.ConfigCoreApiIntegracao;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.FabConfigModuloWebERPChaves;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.SistemaERPAtual;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.servletOauthServer.ServletOauth2Server;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.SistemaERPConfiavel;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.servletOauthServer.FabTipoRequisicaoOauthServer;
import com.super_bits.modulos.SBAcessosModel.model.UsuarioSB;
import com.super_bits.modulosSB.Persistencia.ConfigGeral.SBPersistencia;
import com.super_bits.modulosSB.Persistencia.dao.UtilSBPersistencia;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.ConfigGeral.arquivosConfiguracao.ConfigModulo;
import java.io.BufferedReader;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;
import testesFW.TesteJunitSBPersistencia;

/**
 *
 * @author sfurbino
 */
public class ApiIntegracaoRestfulimplTest extends TesteJunitSBPersistencia {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    public ApiIntegracaoRestfulimplTest() {
    }
    private SistemaERPConfiavel sistemaConfiavelERP;

    private SistemaERPAtual sistemaAutual;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Override
    protected void configAmbienteDesevolvimento() {
        SBCore.configurar(new ConfigCoreApiIntegracao(), SBCore.ESTADO_APP.DESENVOLVIMENTO);
        SBPersistencia.configuraJPA(new ConfigPercistenciaItegracaoSistemas());
        ItfIntegracaoERP erp = ERPIntegracaoSistemasApi.RESTFUL.getImplementacaoDoContexto();
        sistemaAutual = (SistemaERPAtual) erp.getSistemaAtual();
    }

    private static StringWriter sw;
    /**
     * Test of getCodigoApiExterna method, of class ApiIntegracaoRestfulimpl.
     */
    ItfIntegracaoERP integracao = ERPIntegracaoSistemasApi.RESTFUL.getImplementacaoDoContexto();
    private static String chavePrivadaDoAplicativoConfiavel;

    private static final String USUARIO_AUTENTICADO = "salviof@gmail.com";

    private static boolean criouAplicativo = false;

    private void preparaRespostasServletPostToken(String pJson) {
        String emailcripto = UtilSBCoreCriptoRSA.getTextoCriptografado(USUARIO_AUTENTICADO, chavePrivadaDoAplicativoConfiavel);
        sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        try {
            when(response.getWriter()).thenReturn(pw);
        } catch (IOException ex) {
            Logger.getLogger(ApiIntegracaoRestfulimplTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            when(request.getReader()).thenReturn(new BufferedReader(new StringReader(pJson)));
        } catch (IOException ex) {
            Logger.getLogger(ApiIntegracaoRestfulimplTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        when(request.getHeader("emailCripto")).thenReturn(emailcripto);
        when(request.getHeader("CHAVE_PUBLICA")).thenReturn(sistemaConfiavelERP.getChavePublica());
        when(request.getHeader("origin")).thenReturn(sistemaConfiavelERP.getDominio());
        ConfigModulo moduloServidorOauth = SBCore.getConfigModulo(FabConfigModuloWebERPChaves.class);

        try {
            when(request.getRequestURI()).thenReturn(
                    "/" + ServletOauth2Server.SLUGPUBLICACAOSERVLET
                    + "/" + FabTipoRequisicaoOauthServer.OBTER_CODIGO_DE_AUTORIZACAO.toString()
                    + "/" + sistemaAutual.getHashChavePublica()
                    + "/" + URLEncoder.encode(sistemaConfiavelERP.getHashChavePublica())
                    + "/" + URLEncoder.encode("https://crm.coletivojava.com.br/solicitacaoAuth2Recept/code/USUARIO/", "UTF8")
                    + "/USUARIO");

        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ApiIntegracaoRestfulimplTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void preparaRespostasServlet() {

        String emailcripto = UtilSBCoreCriptoRSA.getTextoCriptografado(USUARIO_AUTENTICADO, chavePrivadaDoAplicativoConfiavel);
        sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        try {
            when(response.getWriter()).thenReturn(pw);
        } catch (IOException ex) {
            Logger.getLogger(ApiIntegracaoRestfulimplTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        when(request.getHeader("emailCripto")).thenReturn(emailcripto);
        when(request.getHeader("CHAVE_PUBLICA")).thenReturn(sistemaConfiavelERP.getChavePublica());
        when(request.getHeader("origin")).thenReturn(sistemaConfiavelERP.getDominio());
        ConfigModulo moduloServidorOauth = SBCore.getConfigModulo(FabConfigModuloWebERPChaves.class);

        try {
            when(request.getRequestURI()).thenReturn(
                    "/OAUTH2_SERVICE"
                    + "/OBTER_CODIGO_DE_CONCESSAO_DE_ACESSO"
                    + "/" + sistemaAutual.getHashChavePublica()
                    + "/" + sistemaConfiavelERP.getHashChavePublica()
                    + "/" + URLEncoder.encode("https://crm.coletivojava.com.br/solicitacaoAuth2Recept/code/USUARIO/", "UTF8")
                    + "/USUARIO");

        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ApiIntegracaoRestfulimplTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public SistemaERPConfiavel buildAplicativoAcesso() {

        if (sistemaConfiavelERP != null) {

            return sistemaConfiavelERP;
        }
        criouAplicativo = true;

        sistemaConfiavelERP = new SistemaERPConfiavel();
        sistemaConfiavelERP.setDominio("crm.casanovadigital.com.br");
        sistemaConfiavelERP.setUrlRecepcaoCodigo("crm.casanovadigital.com.br/recepcaoOauth/integracaoSistemas");
        Map<String, String> parDeChaves = UtilSBCoreCriptoRSA.chavePublicaPrivada();
        sistemaConfiavelERP.setChavePublica(parDeChaves.keySet().stream().findFirst().get());
        chavePrivadaDoAplicativoConfiavel = parDeChaves.values().stream().findFirst().get();

        sistemaConfiavelERP = UtilSBPersistencia.mergeRegistro(sistemaConfiavelERP);
        return sistemaConfiavelERP;
    }

    private final ServletOauth2Server servletFornecimentoToken = new ServletOauth2Server();

    @Test
    public void testeFormularioDeLogin() {
        buildAplicativoAcesso();
        preparaRespostasServlet();
        integracao.getSistemaAtual();
        try {

            servletFornecimentoToken.doGet(request, response);

            String result = sw.getBuffer().toString().trim();
            assertTrue("O servlet não exibu o formulário de login", result.contains(USUARIO_AUTENTICADO));

        } catch (IOException ex) {
            Logger.getLogger(ApiIntegracaoRestfulimplTest.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (ServletException ex) {
            Logger.getLogger(ApiIntegracaoRestfulimplTest.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Test
    public void testeRedirecionamentoFornecimentoToken() {
        buildAplicativoAcesso();

        UsuarioSB usuario = UtilSBPersistencia.getListaTodos(UsuarioSB.class,
                getEM()).stream().findFirst().get();
        SBCore.getServicoSessao().getSessaoAtual().setUsuario(usuario);

        try {
            preparaRespostasServlet();
            servletFornecimentoToken.doGet(request, response);
            String resultado = sw.getBuffer().toString().trim();
            System.out.println(resultado);
            assertTrue("O código não foi retornado", resultado.contains("?code="));
            int indicecodigostr = resultado.indexOf("?code");
            String codigo = resultado.substring(indicecodigostr, resultado.length());
            codigo = codigo.replace("?code=", "");
            codigo = codigo.substring(0, codigo.indexOf("</"));
            preparaRespostasServlet();
            String dadosObterToken = "{\"grant_type\":\"authorization_code\",\n"
                    + "		 \"client_id\": \"SEU_CLIENT_ID\",\n"
                    + "		 \"client_secret\": \"EMAILCRIPTOGRAFADO\",\n"
                    + "		 \"code\": \"SEU_AUTHORIZATION_CODE\",\n"
                    + "		 \"redirect_uri\": \"https://SEU_APP/callback\"}'";
            preparaRespostasServletPostToken(dadosObterToken);
            servletFornecimentoToken.doPost(request, response);
            System.out.println("UP");
        } catch (ServletException | IOException ex) {
            Logger.getLogger(ApiIntegracaoRestfulimplTest.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

}
