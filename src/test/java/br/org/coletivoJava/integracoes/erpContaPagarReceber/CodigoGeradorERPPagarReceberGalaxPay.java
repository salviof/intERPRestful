/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.integracoes.erpContaPagarReceber;

import br.org.coletivoJava.fw.api.erp.contaPagarReceber.apiCore.ERPContaPagarReceber;
import br.org.coletivoJava.integracoes.intGalaxPay.api.ConfiguradorCoreApiGalaxPay;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import org.apache.logging.log4j.LogManager;
import org.coletivojava.fw.api.objetoNativo.log.LogPadraoSB;
import org.junit.Test;
import testes.geradorCodigo.erp.dto.GeradorDTOInterface;
import testes.geradorCodigo.erp.dto.GeradorDTOPojo;
import testes.geradorCodigo.erp.dto.GeradorDTOProcessador;
import testes.geradorCodigo.erp.GeradorERPImplementacaoContexto;

/**
 *
 * @author sfurbino
 */
public class CodigoGeradorERPPagarReceberGalaxPay {

    @Test
    public void inicio() {
        try {
            //GeradorInterfaceDTO
            //GeradorDTOPojo
            //GeradorDTOProcessador
            //ERPContaPagarReceber.GALAX_PAY.getImplementacaoDoContexto().
            SBCore.configurar(new ConfiguradorCoreApiGalaxPay(), SBCore.ESTADO_APP.DESENVOLVIMENTO);
            new GeradorERPImplementacaoContexto(ERPContaPagarReceber.GALAX_PAY).salvarEmDiretorioPadraCASO_NAO_EXISTA();
            new GeradorERPImplementacaoContexto(ERPContaPagarReceber.GALAX_PAY).salvarEmDiretorioPadraCASO_NAO_EXISTA();
            for (Class entidade : ERPContaPagarReceber.GALAX_PAY.getInterfacesDeEntidade()) {
                GeradorDTOInterface geradorInterface = new GeradorDTOInterface(ERPContaPagarReceber.GALAX_PAY, entidade);
                geradorInterface.salvarEmDiretorioPadraoSubstituindoAnterior();
                GeradorDTOPojo geradorPojo = new GeradorDTOPojo(ERPContaPagarReceber.GALAX_PAY, entidade);
                geradorPojo.salvarEmDiretorioPadraCASO_NAO_EXISTA();
                GeradorDTOProcessador geradorProcessador = new GeradorDTOProcessador(ERPContaPagarReceber.GALAX_PAY, entidade);
                geradorProcessador.salvarEmDiretorioPadraCASO_NAO_EXISTA();

            }
        } catch (Throwable t) {
            LogManager.getLogger(LogPadraoSB.class).error("Erro Criando anotações", t);
        }
    }
}
