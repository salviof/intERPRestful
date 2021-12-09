/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.integracoes.intGalaxPay.api;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import org.junit.Test;
import testes.testesSupers.TestesApiRest;

/**
 *
 * @author sfurbino
 */
public class FabApiRestCobrancaGalaxPayClienteTest extends TestesApiRest {

    /**
     * Test of values method, of class FabApiRestCobrancaGalaxPayCliente.
     */
    @Test
    public void testValues() {
        SBCore.configurar(new ConfiguradorCoreApiGalaxPay(), SBCore.ESTADO_APP.DESENVOLVIMENTO);
        gerarCodigos(FabApiRestIntGalaxPayCliente.class);

    }

}
