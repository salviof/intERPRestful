/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao;

import br.org.coletivoJava.fw.api.erp.erpintegracao.model.ItfSistemaErp;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.ConfigGeral.arquivosConfiguracao.ConfigModulo;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreDataHora;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringGerador;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringValidador;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.gestaoToken.MapaTokensGerenciados;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfUsuario;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sfurbino
 */
public class MapaTokensGerenciadosConcessaoOauth extends MapaTokensGerenciados {

    private static final Map<String, Map<String, TokenConcessaoOauthServer>> TOKENS_DE_CONSESSAO_BY_CHAVE_PUBLICA_SISTEMA_CONFIAVEL = new HashMap<>();
    private static final Map<String, Map<String, TokenAcessoOauthServer>> TOKENS_DE_ACCESSO_BY_CHAVE_PUBLICA_SISTEMA_CONFIAVEL = new HashMap<>();
    private static final Map<String, TokenConcessaoOauthServer> TOKEN_DE_CONCESSAO_BY_CODIGO = new HashMap<>();
    private static final Map<String, TokenAcessoOauthServer> TOKEN_DE_ACESSO_BY_CODIGO = new HashMap<>();
    private static final Map<String, TokenAcessoOauthServer> TOKEN_DE_ACESSO_BY_CODIGO_ATUALIZACAO = new HashMap<>();

    private static final ConfigModulo chavesDeAcessoErrp = SBCore.getConfigModulo(FabConfigModuloWebERPChaves.class);

    public static void loadTokensPersistidos(ItfSistemaErp pSistema) {

        String chavesPersistidasTExto = chavesDeAcessoErrp.getRepositorioDeArquivosExternos().getTexto(pSistema.getChavePublica());
        List<TokenAcessoOauthServer> tokensValidos = new ArrayList<>();
        if (!TOKENS_DE_ACCESSO_BY_CHAVE_PUBLICA_SISTEMA_CONFIAVEL.containsKey(pSistema.getChavePublica())) {
            TOKENS_DE_ACCESSO_BY_CHAVE_PUBLICA_SISTEMA_CONFIAVEL.put(pSistema.getChavePublica(), new HashMap<>());
        }

        if (!UtilSBCoreStringValidador.isNuloOuEmbranco(chavesPersistidasTExto)) {

            JsonReader jsonReader = Json.createReader(new StringReader(chavesPersistidasTExto));
            JsonArray array = jsonReader.readArray();
            jsonReader.close();

            for (JsonValue json : array) {
                TokenAcessoOauthServer token = new TokenAcessoOauthServer(json.asJsonObject());
                if (token.isTokenValido()) {
                    tokensValidos.add(token);
                }
            }
        }
        for (TokenAcessoOauthServer token : tokensValidos) {
            TOKEN_DE_ACESSO_BY_CODIGO.put(token.getToken(), token);
            TOKENS_DE_ACCESSO_BY_CHAVE_PUBLICA_SISTEMA_CONFIAVEL.get(pSistema.getChavePublica()).put(token.getToken(), TOKEN_DE_ACESSO_BY_CODIGO.get(token.getToken()));
        }
    }

    public static TokenAcessoOauthServer loadTokenExistente(ItfSistemaErp pSistema, ItfUsuario pUsuario) {
        if (TOKENS_DE_ACCESSO_BY_CHAVE_PUBLICA_SISTEMA_CONFIAVEL.containsKey(pSistema.getChavePublica())) {
            loadTokensPersistidos(pSistema);
        }

        if (!TOKENS_DE_ACCESSO_BY_CHAVE_PUBLICA_SISTEMA_CONFIAVEL.containsKey(pSistema.getChavePublica())) {
            return null;
        }
        TokenAcessoOauthServer token
                = TOKENS_DE_ACCESSO_BY_CHAVE_PUBLICA_SISTEMA_CONFIAVEL.get(pSistema.getChavePublica()).get(pUsuario.getEmail());
        return token;

    }

    public static TokenConcessaoOauthServer gerarNovoTokenCocessaoDeAcesso(ItfSistemaErp pSistema, ItfUsuario pUsuario) {
        String tokenConcessao = UtilSBCoreStringGerador.getStringRandomicaTokenAleatorio();
        Date dataExpiracao = UtilSBCoreDataHora.incrementaSegundos(new Date(), 300);
        TokenConcessaoOauthServer tokenDinamico = new TokenConcessaoOauthServer(tokenConcessao, dataExpiracao, pSistema.getChavePublica());
        if (!TOKENS_DE_CONSESSAO_BY_CHAVE_PUBLICA_SISTEMA_CONFIAVEL.containsKey(pSistema.getChavePublica())) {
            TOKENS_DE_CONSESSAO_BY_CHAVE_PUBLICA_SISTEMA_CONFIAVEL.put(pSistema.getChavePublica(), new HashMap<>());
        }
        TOKENS_DE_CONSESSAO_BY_CHAVE_PUBLICA_SISTEMA_CONFIAVEL.get(pSistema.getChavePublica()).put(pUsuario.getEmail(), tokenDinamico);
        TOKEN_DE_CONCESSAO_BY_CODIGO.put(tokenConcessao, tokenDinamico);
        return tokenDinamico;
    }

    public static TokenAcessoOauthServer gerarNovoTokenDeAcesso(String pcodigoTokenConsessao, String pSistemaConfiavelChavePublica, String pIdentificador) {
        TokenConcessaoOauthServer tokenConcessao = TOKEN_DE_CONCESSAO_BY_CODIGO.get(pcodigoTokenConsessao);
        if (tokenConcessao == null) {
            return null;
        }
        String codigoTokenDeAcesso = UtilSBCoreStringGerador.getStringRandomicaTokenAleatorio();
        Date dataExpiracao = UtilSBCoreDataHora.incrementaSegundos(new Date(), 3000);
        TokenAcessoOauthServer novoToken = new TokenAcessoOauthServer(codigoTokenDeAcesso, dataExpiracao, pSistemaConfiavelChavePublica, pIdentificador);
        persistirToken(pSistemaConfiavelChavePublica, novoToken);
        return novoToken;
    }

    public static TokenAcessoOauthServer renovarNovoTokenDeAcesso(String pcodigoAtualizacaoToken) {
        TokenAcessoOauthServer tokenEncontrado = TOKEN_DE_ACESSO_BY_CODIGO_ATUALIZACAO.get(pcodigoAtualizacaoToken);
        if (tokenEncontrado == null) {
            return null;
        }
        Date dataHoraLimiteRenovacaoToken = UtilSBCoreDataHora.incrementaDias(tokenEncontrado.getDataHoraExpira(), 5);
        if (dataHoraLimiteRenovacaoToken.getTime() <= new Date().getTime()) {
            return null;
        }
        String sistemaConfiavelChavePublica = tokenEncontrado.getChavePublicaAplicativoConfiavel();
        Date dataExpiracao = UtilSBCoreDataHora.incrementaSegundos(new Date(), 3000);
        String codigoTokenDeAcesso = UtilSBCoreStringGerador.getStringRandomicaTokenAleatorio();
        TokenAcessoOauthServer novoToken = new TokenAcessoOauthServer(codigoTokenDeAcesso, dataExpiracao, sistemaConfiavelChavePublica, tokenEncontrado.getIdentificacaoAgenteDeAcesso());
        persistirToken(sistemaConfiavelChavePublica, novoToken);
        return novoToken;
    }

    public synchronized static boolean persistirToken(String pSistemaConfiavelChavePublica, TokenAcessoOauthServer pToken) {
        try {
            String chavesPersistidasTExto = chavesDeAcessoErrp.getRepositorioDeArquivosExternos().getTexto(pSistemaConfiavelChavePublica);
            List<TokenAcessoOauthServer> tokensValidos = new ArrayList<>();
            tokensValidos.add(pToken);
            if (!UtilSBCoreStringValidador.isNuloOuEmbranco(chavesPersistidasTExto)) {
                JsonReader jsonReader = Json.createReader(new StringReader(chavesPersistidasTExto));
                JsonArray array = jsonReader.readArray();
                jsonReader.close();

                for (JsonValue json : array) {
                    TokenAcessoOauthServer token = new TokenAcessoOauthServer(json.asJsonObject());
                    if (token.isTokenValido()) {
                        tokensValidos.add(token);
                    }
                }
            }
            JsonArrayBuilder jsonArraybuilder = Json.createArrayBuilder();
            for (TokenAcessoOauthServer tokenValido : tokensValidos) {
                jsonArraybuilder.add(tokenValido.getObjetoJsonArmazenamento());
            }
            chavesDeAcessoErrp.getRepositorioDeArquivosExternos().putConteudoRecursoExterno(pToken.getChavePublicaAplicativoConfiavel(), jsonArraybuilder.build().toString());
            return true;
        } catch (Throwable t) {
            return false;
        }
    }

}
