/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.integracoes.intGalaxPay.api;

import com.super_bits.modulosSB.SBCore.ConfigGeral.arquivosConfiguracao.ItfFabConfigModulo;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.tipoModulos.integracaoOauth.FabPropriedadeModuloIntegracaoOauth;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.tipoModulos.integracaoOauth.InfoPropriedadeConfigRestIntegracao;

/**
 *
 * @author desenvolvedorninja01
 */
public enum FabConfigApiGalaxyPay implements ItfFabConfigModulo {

    @InfoPropriedadeConfigRestIntegracao(tipoPropriedade = FabPropriedadeModuloIntegracaoOauth.CHAVE_PUBLICA)
    GALAXID,
    @InfoPropriedadeConfigRestIntegracao(tipoPropriedade = FabPropriedadeModuloIntegracaoOauth.CHAVE_PRIVADA)
    GALAXHASH,
    /**
     * https://api.galaxpay.com.br/v2, ou
     * https://api.sandbox.cloud.galaxpay.com.br/v2
     */
    @InfoPropriedadeConfigRestIntegracao(tipoPropriedade = FabPropriedadeModuloIntegracaoOauth.URL_SERVIDOR_API)
    GALAX_URL;

    public static final String NOME_INTEGRACAO = "intGalaxPay";

    @Override
    public String getValorPadrao() {
        //Valores da sandbox
        switch (this) {
            case GALAXID:
                return "5473";

            case GALAXHASH:
                return "83Mw5u8988Qj6fZqS4Z8K7LzOo1j28S706R0BeFe";

            case GALAX_URL:
                return "https://api.sandbox.cloud.galaxpay.com.br/v2";

            default:
                throw new AssertionError(this.name());
        }
    }
}
