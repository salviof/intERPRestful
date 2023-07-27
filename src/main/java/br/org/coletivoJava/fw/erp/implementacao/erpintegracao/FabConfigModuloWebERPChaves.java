/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.ConfigGeral.arquivosConfiguracao.ItfFabConfigModulo;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreCriptoRSA;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.tipoModulos.integracaoOauth.FabPropriedadeModuloIntegracaoOauth;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.tipoModulos.integracaoOauth.InfoPropriedadeConfigRestIntegracao;
import com.super_bits.modulosSB.SBCore.modulos.chavesPublicasePrivadas.RepositorioChavePublicaPrivada;
import com.super_bits.modulosSB.webPaginas.ConfigGeral.FabConfigModuloWebAppGenerico;
import java.security.KeyPairGenerator;
import java.util.Map;

/**
 *
 * @author sfurbino
 */
public enum FabConfigModuloWebERPChaves implements ItfFabConfigModulo {

    @InfoPropriedadeConfigRestIntegracao(tipoPropriedade = FabPropriedadeModuloIntegracaoOauth.CHAVE_PUBLICA)
    PAR_DE_CHAVES_IDENTIFICADOR,
    @InfoPropriedadeConfigRestIntegracao(tipoPropriedade = FabPropriedadeModuloIntegracaoOauth.URL_SERVIDOR_API)
    SITE_URL,
    @InfoPropriedadeConfigRestIntegracao(tipoPropriedade = FabPropriedadeModuloIntegracaoOauth.USUARIO)
    USUARIO_ADMIN;

    private static KeyPairGenerator gerador;

    @Override
    public synchronized String getValorPadrao() {

        switch (this) {
            case PAR_DE_CHAVES_IDENTIFICADOR: {
                Map<String, String> parDeChaves = UtilSBCoreCriptoRSA.chavePublicaPrivada();
                String identificador = RepositorioChavePublicaPrivada.getIndentificadorParDeChaves(parDeChaves);
                RepositorioChavePublicaPrivada.persistirChavePublica(parDeChaves);
                return identificador;
            }
            case SITE_URL:
                if (SBCore.isEmModoDesenvolvimento()) {
                    // ServicoRecepcaoOauthTestes utiliza a porta 766
                    return "http://localhost:7666";
                }

                return SBCore.getConfigModulo(FabConfigModuloWebAppGenerico.class).getPropriedade(FabConfigModuloWebAppGenerico.URL_DOMINIO_APLICACAO);

            default:
                throw new AssertionError(this.name());

        }
    }
}
