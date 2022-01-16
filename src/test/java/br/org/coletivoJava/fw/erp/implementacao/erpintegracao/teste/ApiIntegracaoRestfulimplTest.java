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
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.SistemaERPAtual;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.servletOauthServer.ServletOauth2Server;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.SistemaERPConfiavel;
import com.super_bits.modulosSB.Persistencia.ConfigGeral.SBPersistencia;
import com.super_bits.modulosSB.Persistencia.dao.UtilSBPersistencia;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.webPaginas.controller.servlets.servletRecepcaoOauth.ServletRecepcaoOauth;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import testesFW.TesteJunitSBPersistencia;

/**
 *
 * @author sfurbino
 */
public class ApiIntegracaoRestfulimplTest extends TesteJunitSBPersistencia {

    @Mock
    HttpServletRequest requisicaoObterCodigoSoclitacaoToken;

    @Mock
    HttpServletResponse respostaObterCodigoSoclitacaoToken;

    @Mock
    HttpServletRequest requisicaoSolicitarTokenAcessso;

    @Mock
    HttpServletResponse respostaObterSolicitarTokenAcesso;

    @Mock
    HttpServletRequest requisicaoAcessoComToken;

    @Mock
    HttpServletResponse respostaAcessoComToken;

    @Mock
    HttpServletRequest requisicaoRegistroCodigoSolicitacao;

    @Mock
    HttpServletResponse respostaRegistroCodigoSolicitacao;

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

    /**
     * Test of getCodigoApiExterna method, of class ApiIntegracaoRestfulimpl.
     */
    ItfIntegracaoERP integracao = ERPIntegracaoSistemasApi.RESTFUL.getImplementacaoDoContexto();
    private static String chavePrivadaDoAplicativoConfiavel;

    private static final String USUARIO_AUTENTICADO = "salviof@gmail.com";

    private static boolean criouAplicativo = false;

    public SistemaERPConfiavel buildAplicativoAcesso() {

        if (sistemaConfiavelERP != null) {

            return sistemaConfiavelERP;
        }
        criouAplicativo = true;

        sistemaConfiavelERP = new SistemaERPConfiavel();
        sistemaConfiavelERP.setNome("crm.casanovadigital.com.br");
        sistemaConfiavelERP.setDominio("crm.casanovadigital.com.br");
        sistemaConfiavelERP.setUrlRecepcaoCodigo("crm.casanovadigital.com.br/recepcaoOauth/integracaoSistemas");
        Map<String, String> parDeChaves = UtilSBCoreCriptoRSA.chavePublicaPrivada();
        sistemaConfiavelERP.setChavePublica(parDeChaves.keySet().stream().findFirst().get());
        chavePrivadaDoAplicativoConfiavel = parDeChaves.values().stream().findFirst().get();

        sistemaConfiavelERP = UtilSBPersistencia.mergeRegistro(sistemaConfiavelERP);

        List<SistemaERPConfiavel> sistemasConfiaveis = UtilSBPersistencia.getListaTodos(SistemaERPConfiavel.class);
        for (SistemaERPConfiavel sistemaConfiavel : sistemasConfiaveis) {
            System.out.println("__________________________________");
            System.out.println(sistemaConfiavel.getNome());
            System.out.println(sistemaConfiavel.getHashChavePublica());
            System.out.println("########################################");
        }
        return sistemaConfiavelERP;
    }

    private final ServletOauth2Server servletFornecimentoToken = new ServletOauth2Server();

    private final ServletRecepcaoOauth servletRecepcaoOauth = new ServletRecepcaoOauth();

    @Test
    public void testeFormularioDeLogin() {
        buildAplicativoAcesso();

        obterCodigoSolicitacaoDeslogado();
        String codigoSolicitacaoToken = obterCodigoSolicitacaoAposLogin();
        String reposta = obterToken(codigoSolicitacaoToken);
        String url = reposta;
        receberToken(url);

    }

    public void obterCodigoSolicitacaoDeslogado() {
        EnvelopeServeletSolicitarCodigoObterToken obterCodigoSolicitacaoToken = new EnvelopeServeletSolicitarCodigoObterToken(requisicaoObterCodigoSoclitacaoToken,
                respostaObterCodigoSoclitacaoToken, sistemaAutual, sistemaConfiavelERP, USUARIO_AUTENTICADO);
        String respostaSolicitacaoTOken = obterCodigoSolicitacaoToken.getRespostaGet(servletFornecimentoToken);

        assertTrue("Formulário para login não foi exibido", respostaSolicitacaoTOken.contains("input"));
    }

    public String obterCodigoSolicitacaoAposLogin() {
        SBCore.getServicoSessao().logarEmailESenha("salviof@gmail.com", "123456");
        assertTrue("Usuário não logrou êxito ao efetuar login", SBCore.getServicoSessao().getSessaoAtual().isIdentificado());
        EnvelopeServeletSolicitarCodigoObterToken solicitacaoTokenPosLogin
                = new EnvelopeServeletSolicitarCodigoObterToken(requisicaoObterCodigoSoclitacaoToken,
                        respostaObterCodigoSoclitacaoToken, sistemaAutual, sistemaConfiavelERP, USUARIO_AUTENTICADO);
        String respostaPosLogin = solicitacaoTokenPosLogin.getRespostaGet(servletFornecimentoToken);
        System.out.println(respostaPosLogin);
        assertTrue("O código não foi enviado", respostaPosLogin.contains("&code="));
        int indiceConexao = respostaPosLogin.indexOf("&code=");

        String respostaCodigo = respostaPosLogin.substring(indiceConexao);
        String codigoSolicitacao = respostaCodigo.substring("&code=".length(), respostaCodigo.indexOf("'"));
        String url = respostaPosLogin.substring(respostaPosLogin.indexOf("windows.location='") + "windows.location='".length(), respostaPosLogin
                .indexOf("'</script>")
        );
        return codigoSolicitacao;
    }

    public String obterToken(String pCodigoSolicitacao) {
        EnvelopePostSolicitacaoToken postRequisicao = new EnvelopePostSolicitacaoToken(requisicaoSolicitarTokenAcessso, respostaObterSolicitarTokenAcesso, sistemaAutual, sistemaConfiavelERP, USUARIO_AUTENTICADO, chavePrivada);
        String dadosObterToken = "{\"grant_type\":\"authorization_code\",\n"
                + "		 \"client_id\": \"SEU_CLIENT_ID\",\n"
                + "		 \"client_secret\": \"EMAILCRIPTOGRAFADO\",\n"
                + "		 \"code\": \"SEU_AUTHORIZATION_CODE\",\n"
                + "		 \"redirect_uri\": \"https://SEU_APP/callback\"}'";
        postRequisicao.setBodyRequisicao(dadosObterToken);
        String resposta = postRequisicao.getRespostaPost(servletFornecimentoToken);
        return resposta;
    }

    public void receberToken(String urlRedirecionamento) {
        String codigoSolicitacao = null;
        String url = null;
        String hashChavePublicaServidor = sistemaAutual.getHashChavePublica();
        EnvelopeRequisicacaoRegistroDeCodigoSolicitacao clienteRegistroCodigo
                = new EnvelopeRequisicacaoRegistroDeCodigoSolicitacao(requisicaoRegistroCodigoSolicitacao,
                        respostaRegistroCodigoSolicitacao, codigoSolicitacao, url, hashChavePublicaServidor);
        clienteRegistroCodigo.getRespostaGet(servletRecepcaoOauth);
    }

}
