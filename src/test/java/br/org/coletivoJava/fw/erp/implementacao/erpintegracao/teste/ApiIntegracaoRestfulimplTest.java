/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.teste;

import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.UtilSBCoreSegurancaRCA;
import br.org.coletivoJava.fw.api.erp.erpintegracao.contextos.ERPIntegracaoSistemasApi;
import br.org.coletivoJava.fw.api.erp.erpintegracao.servico.ItfIntegracaoERP;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.ConfigCoreApiIntegracao;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.ServletLoginGeracaoJWT;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.SistemaERPConfiavel;
import com.super_bits.modulosSB.Persistencia.ConfigGeral.SBPersistencia;
import com.super_bits.modulosSB.Persistencia.dao.UtilSBPersistencia;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import testesFW.TesteJUnitBasicoSemPersistencia;
import static org.mockito.Mockito.when;

/**
 *
 * @author sfurbino
 */
public class ApiIntegracaoRestfulimplTest extends TesteJUnitBasicoSemPersistencia {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    public ApiIntegracaoRestfulimplTest() {
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Override
    protected void configAmbienteDesevolvimento() {
        SBCore.configurar(new ConfigCoreApiIntegracao(), SBCore.ESTADO_APP.DESENVOLVIMENTO);
        SBPersistencia.configuraJPA(new ConfigPercistenciaItegracaoSistemas());
    }

    /**
     * Test of getCodigoApiExterna method, of class ApiIntegracaoRestfulimpl.
     */
    ItfIntegracaoERP integracao = ERPIntegracaoSistemasApi.RESTFUL.getImplementacaoDoContexto();
    private static String chavePrivadaDoAplicativoConfiavel;

    public SistemaERPConfiavel buildAplicativoAcesso() {

        SistemaERPConfiavel sistema = new SistemaERPConfiavel();
        sistema.setDominio("crm.casanovadigital.com.br");
        sistema.setUrlRecepcaoCodigo("crm.casanovadigital.com.br/recepcaoOauth/integracaoSistemas");
        Map<String, String> parDeChaves = UtilSBCoreSegurancaRCA.chavePublicaPrivada();
        sistema.setChavePublica(parDeChaves.keySet().stream().findFirst().get());
        chavePrivadaDoAplicativoConfiavel = parDeChaves.values().stream().findFirst().get();
        String emailcripto = UtilSBCoreSegurancaRCA.getTextoCriptografado("salviof@gmail.com", chavePrivadaDoAplicativoConfiavel);
        when(request.getHeader("emailCripto")).thenReturn(emailcripto);
        when(request.getHeader("CHAVE_PUBLICA")).thenReturn(sistema.getChavePublica());
        when(request.getHeader("origin")).thenReturn(sistema.getDominio());
        sistema = UtilSBPersistencia.mergeRegistro(sistema);
        return sistema;
    }

    @Test
    public void testGetCodigoApiExterna() {

        Map<String, String> parDeChaves = UtilSBCoreSegurancaRCA.chavePublicaPrivada();
        final String texto = "Olá mundo";
        System.out.println("Chave Privada:");
        System.out.println(parDeChaves.entrySet().stream().findFirst().get().getValue());
        System.out.println("Chave Publica:");
        System.out.println(parDeChaves.entrySet().stream().findFirst().get().getKey());

        String textoCriptografado = UtilSBCoreSegurancaRCA.getTextoCriptografado(texto, parDeChaves.entrySet().stream().findFirst().get().getValue());
        System.out.println("Criptografado=" + textoCriptografado);

        String textodescriptString = UtilSBCoreSegurancaRCA.getTextoDescriptografado(textoCriptografado, parDeChaves.entrySet().stream().findFirst().get().getKey());
        assertEquals("O texto não foi descriptografado corretamente", texto, textodescriptString);
        System.out.println("Descriptografado=" + textodescriptString);

        integracao
                .getSistemaAtual();
        try {
            SistemaERPConfiavel sistemaCRP = buildAplicativoAcesso();

            ServletLoginGeracaoJWT myServlet = new ServletLoginGeracaoJWT();
            myServlet.doGet(request, response);
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            when(response.getWriter()).thenReturn(pw);
            String result = sw.getBuffer().toString().trim();
            assertEquals(result, new String("TESTE"));
        } catch (IOException ex) {
            Logger.getLogger(ApiIntegracaoRestfulimplTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServletException ex) {
            Logger.getLogger(ApiIntegracaoRestfulimplTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
