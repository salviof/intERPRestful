/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.teste;

import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.teste.simulacaoComunicacao.EnvelopeServeletSolicitarCodigoAcessoAoToken;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.teste.simulacaoComunicacao.EnvelopeRequisicacaoTokenAcesso;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreCriptoRSA;
import br.org.coletivoJava.fw.api.erp.erpintegracao.contextos.ERPIntegracaoSistemasApi;
import br.org.coletivoJava.fw.api.erp.erpintegracao.servico.ItfIntegracaoERP;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.ConfigCoreApiIntegracao;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.SolicitacaoControllerERP;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.SistemaERPAtual;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.servletOauthServer.ServletOauth2Server;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.SistemaERPConfiavel;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.teste.servicoTeste.UtilTesteServicoRestfull;
import br.org.coletivoJava.integracoes.restInterprestfull.api.FabIntApiRestIntegracaoERPRestfull;
import br.org.coletivoJava.integracoes.restInterprestfull.implementacao.GestaoTokenRestInterprestfull;
import com.super_bits.modulosSB.Persistencia.ConfigGeral.SBPersistencia;
import com.super_bits.modulosSB.Persistencia.dao.UtilSBPersistencia;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.RespostaWebServiceSimples;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token.ItfTokenGestao;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.transmissao_recepcao_rest_client.ItfAcaoApiRest;
import com.super_bits.modulosSB.webPaginas.controller.servlets.servletRecepcaoOauth.ServletRecepcaoOauth;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.coletivojava.fw.api.objetoNativo.controller.sistemaErp.ItfSistemaErp;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import testesFW.TesteJunitSBPersistencia;

/**
 *
 * @author sfurbino
 */
public class ApiIntegracaoRestfulimplTest extends TesteJunitSBPersistencia {

    public ApiIntegracaoRestfulimplTest() {
    }
    private SistemaERPConfiavel sistemaCliente;

    private SistemaERPConfiavel sistemaRemoto;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Override
    protected void configAmbienteDesevolvimento() {
        SBCore.configurar(new ConfigCoreApiIntegracao(), SBCore.ESTADO_APP.DESENVOLVIMENTO);
        SBPersistencia.configuraJPA(new ConfigPercistenciaItegracaoSistemas());

        ItfIntegracaoERP erp = ERPIntegracaoSistemasApi.RESTFUL.getImplementacaoDoContexto();
        sistemaRemoto = (SistemaERPAtual) erp.getSistemaAtual();
    }

    /**
     * Test of getCodigoApiExterna method, of class ApiIntegracaoRestfulimpl.
     */
    ItfIntegracaoERP integracao = ERPIntegracaoSistemasApi.RESTFUL.getImplementacaoDoContexto();
    private static String chavePrivadaDoAplicativoConfiavel;

    private static final String USUARIO_AUTENTICADO = "salviof@gmail.com";

    private static boolean criouAplicativo = false;

    public SistemaERPConfiavel buildAplicativoAcesso() {

        if (sistemaCliente != null) {

            return sistemaCliente;
        }
        criouAplicativo = true;

        sistemaCliente = new SistemaERPConfiavel();
        sistemaCliente.setNome("crm.casanovadigital.com.br");
        sistemaCliente.setDominio("crm.casanovadigital.com.br");
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
        sistemaRemoto.setUrlRecepcaoCodigo(sistemaRemotoRef.getUrlRecepcaoCodigo());
        sistemaRemoto.setDominio(sistemaRemotoRef.getDominio());
        UtilSBPersistencia.mergeRegistro(sistemaRemoto);

        return sistemaCliente;
    }

    private final ServletOauth2Server servletFornecimentoToken = new ServletOauth2Server();

    private final ServletRecepcaoOauth servletRecepcaoOauth = new ServletRecepcaoOauth();

    @Test
    public void testeFluxoREgistroDeToken() {
        buildAplicativoAcesso();

        renovarConexao();

        List<SistemaERPConfiavel> sistemasRegistrados = UtilSBPersistencia.getListaTodos(SistemaERPConfiavel.class, getEMTeste());
        System.out.println(sistemasRegistrados.size() + " sistemas encontrados");
        for (SistemaERPConfiavel sis : sistemasRegistrados) {
            System.out.println("nome ->" + sis.getNome());
            System.out.println("Dominio ->" + sis.getDominio());
            System.out.println("Hashpub ->" + sis.getHashChavePublica());
            System.out.println("___________________________________________");
        }

        ItfIntegracaoERP erp = ERPIntegracaoSistemasApi.RESTFUL.getImplementacaoDoContexto();
        // sistemaRemoto = (SistemaERPAtual) erp.getSistemaAtual();
        // SBCore.getServicoSessao().logarEmailESenha("salviof@gmail.com", "123456");

        System.out.println("O usuário utiliza o sistema crm.casanovadigital.com.br");
        assertTrue(sistemaCliente.getDominio().contains("crm.casanovadigita"));

        System.out.println("O usuário deseja ver uma nota no sistema fatura");
        ItfSistemaErp sisRemoto = erp.getSistemaByDominio("localhost");
        assertNotNull("O sistema fatura não foi registrado no sistemas", sisRemoto);

        System.out.println("O usuário não possui um token de acesso");
        ItfTokenGestao gestao = FabIntApiRestIntegracaoERPRestfull.OAUTH_VALIDAR_CREDENCIAL.getGestaoToken(sistemaRemoto);
        assertFalse(gestao.isTemTokemAtivo());

        System.out.println("O usuário acessa a url para obter o código de solicitação,"
                + " e uma vez que já está logado, recebe um comando de redirecionamento para o crm contendo o codigo de acesso");
        String urlRecepcaoCodigoAcesso = obterCodigoSolicitacaoAposLogin();
        String codigoAcesso = extrairCodigoDeAcessoPelaUrl(urlRecepcaoCodigoAcesso);

        GestaoTokenRestInterprestfull gestaoResful = (GestaoTokenRestInterprestfull) FabIntApiRestIntegracaoERPRestfull.OAUTH_VALIDAR_CREDENCIAL
                .getGestaoToken(sisRemoto);
        assertNotNull("O código de acesso não foi enviado", codigoAcesso);
        //gestaoResful.getComoGestaoOauth().get
        System.out.println("o sistema crm recebe o código de solicitação");
        String reposta = receberCodigoSolicitacaoRegistrarToken(urlRecepcaoCodigoAcesso);
        assertTrue("O código de solicitação não foi registrado", gestaoResful.isCodigoSolicitacaoRegistrado());

        System.out.println("O token é registrado com sucesso");
        assertTrue("token", gestaoResful.isTemTokemAtivo());
        System.out.println("Usando o token, a ação listar ações do sistema é executada");
        //obterCodigoSolicitacaoDeslogado();

        String url = reposta;
        UtilTesteServicoRestfull.iniciarServico();
        SolicitacaoControllerERP solicitacao = new SolicitacaoControllerERP(sisRemoto, "");

        ItfAcaoApiRest acao = FabIntApiRestIntegracaoERPRestfull.ACOES_GET_OPCOES.getAcao(sisRemoto);
        RespostaWebServiceSimples resp = acao.getResposta();
        System.out.println(resp.getCodigoResposta());
        System.out.println(resp.getResposta());
    }

    public void obterCodigoSolicitacaoDeslogado() {
        EnvelopeServeletSolicitarCodigoAcessoAoToken obterCodigoSolicitacaoToken
                = new EnvelopeServeletSolicitarCodigoAcessoAoToken(
                        sistemaRemoto, sistemaCliente, USUARIO_AUTENTICADO);
        String respostaSolicitacaoTOken = obterCodigoSolicitacaoToken.getRespostaGet(servletFornecimentoToken);

        assertTrue("Formulário para login não foi exibido", respostaSolicitacaoTOken.contains("input"));
    }

    public String extrairCodigoDeAcessoPelaUrl(String pUrl) {

        int indiceConexao = pUrl.indexOf("?code=");

        String respostaCodigo = pUrl.substring(indiceConexao);
        String codigoSolicitacao = respostaCodigo.substring("?code=".length(), respostaCodigo.indexOf("&"));
        return codigoSolicitacao;
    }

    public String obterCodigoSolicitacaoAposLogin() {
        SBCore.getServicoSessao().logarEmailESenha("salviof@gmail.com", "123456");
        assertTrue("Usuário não logrou êxito ao efetuar login", SBCore.getServicoSessao().getSessaoAtual().isIdentificado());
        EnvelopeServeletSolicitarCodigoAcessoAoToken solicitacaoTokenPosLogin
                = new EnvelopeServeletSolicitarCodigoAcessoAoToken(
                        sistemaRemoto, sistemaCliente, USUARIO_AUTENTICADO);
        String respostaPosLogin = solicitacaoTokenPosLogin.getRespostaGet(servletFornecimentoToken);
        System.out.println(respostaPosLogin);
        assertTrue("O código não foi enviado", respostaPosLogin.contains("?code="));
        int indiceConexao = respostaPosLogin.indexOf("?code=");

        String respostaCodigo = respostaPosLogin.substring(indiceConexao);
        String codigoSolicitacao = respostaCodigo.substring("?code=".length(), respostaCodigo.indexOf("'"));
        String url = respostaPosLogin.substring(respostaPosLogin.indexOf("windows.location='") + "windows.location='".length(), respostaPosLogin
                .indexOf("'</script>")
        );
        return url;
    }

    public String receberCodigoSolicitacaoRegistrarToken(String urlRedirecionamento) {
        String codigoSolicitacao = extrairCodigoDeAcessoPelaUrl(urlRedirecionamento);
        String url = urlRedirecionamento;
        System.out.println("Simulando recepção  via " + ServletRecepcaoOauth.class.getSimpleName() + " ");
        String hashChavePublicaSistemaCliente = sistemaCliente.getHashChavePublica();
        EnvelopeRequisicacaoTokenAcesso clienteRegistroCodigo;
        try {
            clienteRegistroCodigo = new EnvelopeRequisicacaoTokenAcesso(codigoSolicitacao,
                    url, hashChavePublicaSistemaCliente);
            String resposta = clienteRegistroCodigo.getRespostaGet(servletRecepcaoOauth);
            return resposta;
        } catch (MalformedURLException ex) {
            Logger.getLogger(ApiIntegracaoRestfulimplTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new UnsupportedOperationException("Falha simulando comunicação para obtenção de token");
        }

    }
    //   public String obterToken(String pCodigoSolicitacao) {
    //      EnvelopeSolicitacaoCodigoObterToken postRequisicao = new EnvelopeSolicitacaoCodigoObterToken(
    //            requisicaoSolicitarTokenAcessso, respostaObterSolicitarTokenAcesso, sistemaAutual,
    //              sistemaConfiavelERP, USUARIO_AUTENTICADO);
    //      String dadosObterToken = "{\"grant_type\":\"authorization_code\",\n"
    //               + "		 \"client_id\": \"SEU_CLIENT_ID\",\n"
    //               + "		 \"client_secret\": \"EMAILCRIPTOGRAFADO\",\n"
    //               + "		 \"code\": \"SEU_AUTHORIZATION_CODE\",\n"
    //               + "		 \"redirect_uri\": \"https://SEU_APP/callback\"}'";
    //       postRequisicao.setBodyRequisicao(dadosObterToken);
    //       String resposta = postRequisicao.getRespostaPost(servletFornecimentoToken);
    //      return resposta;
    //  }

}
