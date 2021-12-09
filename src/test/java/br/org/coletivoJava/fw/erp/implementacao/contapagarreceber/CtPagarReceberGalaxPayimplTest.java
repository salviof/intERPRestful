/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.contapagarreceber;

import br.org.coletivoJava.fw.api.erp.contaPagarReceber.apiCore.ERPContaPagarReceber;
import br.org.coletivoJava.integracoes.intGalaxPay.api.ConfiguradorCoreApiGalaxPay;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.financeiro.ItfPessoaFisicoJuridico;
import org.junit.Test;
import static org.junit.Assert.*;
import br.org.coletivoJava.fw.api.erp.contaPagarReceber.model.assinatura.ItfFaturaAssinatura;
import br.org.coletivoJava.fw.api.erp.contaPagarReceber.model.valormoedaFuturo.ItfPrevisaoValorMoeda;

import com.super_bits.modulosSB.SBCore.modulos.erp.ErroJsonInterpredador;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import spark.utils.IOUtils;

/**
 *
 * @author sfurbino
 */
public class CtPagarReceberGalaxPayimplTest {

    ERPContaPagarReceber contextoGalaxPay = ERPContaPagarReceber.GALAX_PAY;

    @BeforeClass
    public static void setUpClass() {
        SBCore.configurar(new ConfiguradorCoreApiGalaxPay(), SBCore.ESTADO_APP.DESENVOLVIMENTO);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of getDevedorByCNPJ method, of class CtPagarReceberGalaxPayimpl.
     */
    @Test
    public void testGetDevedorByCNPJ() {
        CtPagarReceberGalaxPayimpl instanciaGP = (CtPagarReceberGalaxPayimpl) ERPContaPagarReceber.GALAX_PAY.getImplementacaoDoContexto();

        ClassLoader classLoader = getClass().getClassLoader();

        ///home/superBits/projetos/coletivoJava/source/integracao/intGalaxPay/src/test/resources/bola.jpg
        InputStream is = classLoader.getResourceAsStream("exemplos/galaxPay/devedor.json");
        try {
            String jsonTextoDevedor = IOUtils.toString(is);

            ItfPessoaFisicoJuridico devedorInterno = contextoGalaxPay.getDTO(jsonTextoDevedor, ItfPessoaFisicoJuridico.class);
            ItfPessoaFisicoJuridico devedor = instanciaGP.getDevedorByCNPJ(devedorInterno.getCpfCnpj());
            assertNotNull("O devedor com CNPJ " + devedorInterno.getCpfCnpj() + " não foi encontrado", devedor);
        } catch (IOException t) {
            Logger.getLogger(CtPagarReceberGalaxPayimplTest.class.getName()).log(Level.SEVERE, null, t);
        } catch (ErroJsonInterpredador ex) {
            Logger.getLogger(CtPagarReceberGalaxPayimplTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of getCobrancaSazonal method, of class CtPagarReceberGalaxPayimpl.
     */
    @Test
    public void testGetCobrancaSazonal() {
        CtPagarReceberGalaxPayimpl instanciaGP = (CtPagarReceberGalaxPayimpl) ERPContaPagarReceber.GALAX_PAY.getImplementacaoDoContexto();
        ClassLoader classLoader = getClass().getClassLoader();

        InputStream is = classLoader.getResourceAsStream("exemplos/galaxPay/cobrancaSazonal.json");
        try {
            String jsonTextoSazonal = IOUtils.toString(is);
            ItfPrevisaoValorMoeda cobrancaSazonal = contextoGalaxPay.getDTO(jsonTextoSazonal, ItfPrevisaoValorMoeda.class);

            InputStream isDevedor = classLoader.getResourceAsStream("exemplos/galaxPay/devedor.json");
            String jsonTextoDevedor = IOUtils.toString(isDevedor);
            ItfPessoaFisicoJuridico devedorDTO = contextoGalaxPay.getDTO(jsonTextoDevedor, ItfPessoaFisicoJuridico.class);
            ItfPrevisaoValorMoeda cobranca = instanciaGP.getCobrancaSazonal(cobrancaSazonal.getDataPrevista(), cobrancaSazonal.getValor(), devedorDTO);
            assertNotNull("O devedor com CNPJ " + devedorDTO.getCpfCnpj() + " não foi encontrado", cobranca);
        } catch (IOException t) {

        } catch (ErroJsonInterpredador ex) {
            Logger.getLogger(CtPagarReceberGalaxPayimplTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("Falha criando DTO");
        }

    }

    /**
     * Test of getCobrancaAssinatura method, of class
     * CtPagarReceberGalaxPayimpl.
     */
    @Test
    public void testGetCobrancaAssinatura() {
    }

    /**
     * Test of getAssinatura method, of class CtPagarReceberGalaxPayimpl.
     */
    @Test
    public void testGetAssinatura() {
        String assinaturaTeste;
        try {
            ClassLoader classLoader = getClass().getClassLoader();

            ///home/superBits/projetos/coletivoJava/source/integracao/intGalaxPay/src/test/resources/bola.jpg
            InputStream is = classLoader.getResourceAsStream("exemplos/galaxPay/assinatura.json");
            assinaturaTeste = IOUtils.toString(is);
            ItfFaturaAssinatura assinaturaInterna = contextoGalaxPay.getDTO(assinaturaTeste, ItfFaturaAssinatura.class);
            CtPagarReceberGalaxPayimpl instanciaGP = (CtPagarReceberGalaxPayimpl) ERPContaPagarReceber.GALAX_PAY.getImplementacaoDoContexto();
            ItfFaturaAssinatura assinaturaEncontrada = instanciaGP.getAssinatura(assinaturaInterna.getValorAtualMensal(), assinaturaInterna.getDevedor());
            assertNotNull("Fatura não foi encontrada", assinaturaEncontrada);
        } catch (IOException ex) {
            fail("Falha lendo" + "exemplos/galaxPay/assinatura.json");
        } catch (ErroJsonInterpredador ex) {
            fail("Falha criando dto de assinatura");
        }
    }

}
