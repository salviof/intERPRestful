/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao;

import com.super_bits.modulosSB.SBCore.ConfigGeral.arquivosConfiguracao.ItfFabConfigModulo;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sfurbino
 */
public enum FabConfigModuloWebERPChaves implements ItfFabConfigModulo {

    CHAVE_PUBLICA,
    CHAVE_PRIVADA;
    private static KeyPairGenerator gerador;

    @Override
    public synchronized String getValorPadrao() {
        if (gerador == null) {
            try {
                gerador = KeyPairGenerator.getInstance("RSA");
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(FabConfigModuloWebERPChaves.class.getName()).log(Level.SEVERE, null, ex);
            }
            gerador.initialize(1024);
            gerador.generateKeyPair();
        }
        switch (this) {
            case CHAVE_PUBLICA:
                Base64.Encoder encoder = Base64.getEncoder();
                String publicRSA = encoder.encodeToString(gerador.genKeyPair().getPublic().getEncoded());
                return publicRSA;

            case CHAVE_PRIVADA:
                Base64.Encoder encoder64 = Base64.getEncoder();
                String privateRCA = encoder64.encodeToString(gerador.genKeyPair().getPrivate().getEncoded());
                return privateRCA;

            default:
                throw new AssertionError(this.name());

        }
    }
}
