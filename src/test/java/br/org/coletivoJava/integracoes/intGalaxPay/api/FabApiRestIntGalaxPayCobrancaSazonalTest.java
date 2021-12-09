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
public class FabApiRestIntGalaxPayCobrancaSazonalTest extends TestesApiRest {

    public FabApiRestIntGalaxPayCobrancaSazonalTest() {
    }

    @Test
    public void testValueOf() {
        SBCore.configurar(new ConfiguradorCoreApiGalaxPay(), SBCore.ESTADO_APP.DESENVOLVIMENTO);
        gerarCodigos(FabApiRestIntGalaxPayCobrancaSazonal.class);

    }

}
