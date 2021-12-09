/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.integracoes.restIntgalaxpay.implementacao;

import br.org.coletivoJava.integracoes.intGalaxPay.api.ConfiguradorCoreApiGalaxPay;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token.ItfTokenDeAcessoExterno;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sfurbino
 */
public class GestaoTokenRestIntgalaxpayTest {

    public GestaoTokenRestIntgalaxpayTest() {
    }

    /**
     * Test of validarToken method, of class GestaoTokenRestIntgalaxpay.
     */
    @Test
    public void testValidarToken() {
        SBCore.configurar(new ConfiguradorCoreApiGalaxPay(), SBCore.ESTADO_APP.DESENVOLVIMENTO);
        GestaoTokenRestIntgalaxpay gestaoDeToken = new GestaoTokenRestIntgalaxpay(FabTipoAgenteClienteApi.SISTEMA, null);
        ItfTokenDeAcessoExterno token = gestaoDeToken.gerarNovoToken();
        assertNotNull("O token não foi gerado", token);
        System.out.println("O token é " + token.getToken());

    }

}
