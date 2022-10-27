package br.org.coletivoJava.integracoes.restInterprestfull.implementacao;

import br.org.coletivoJava.integracoes.restInterprestfull.api.InfoIntegracaoRestInterprestfullRestfull;
import br.org.coletivoJava.integracoes.restInterprestfull.api.FabIntApiRestIntegracaoERPRestfull;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreJson;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;
import com.super_bits.modulosSB.SBCore.modulos.erp.SolicitacaoControllerERP;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfUsuario;
import jakarta.json.JsonObject;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

@InfoIntegracaoRestInterprestfullRestfull(tipo = FabIntApiRestIntegracaoERPRestfull.ACOES_GET_LISTA_ENTIDADES)
public class IntegracaoRestInterprestfullAcoesGetListaEntidades
        extends
        IntegracaoResfullPadrao {

    public IntegracaoRestInterprestfullAcoesGetListaEntidades(
            final String pTipoAplicacaoERP,
            final FabTipoAgenteClienteApi pTipoAgente,
            final ItfUsuario pUsuario, final java.lang.Object... pParametro) {
        super(pTipoAplicacaoERP, FabIntApiRestIntegracaoERPRestfull.ACOES_GET_LISTA_ENTIDADES,
                pTipoAgente, pUsuario, pParametro);
    }

    @Override
    protected void executarAcao() {

        SolicitacaoControllerERP sl = getSoliciatacao();
        String pNomeAcao = sl.getAcaoStrNomeUnico();
        JsonObject parametrosLista = UtilSBCoreJson.getJsonObjectByTexto(sl.getCorpoParametros());
        int pagina = parametrosLista.getInt("pagina");

        parametros = new Object[]{pNomeAcao, pagina};
        super.executarAcao();
    }

    @Override
    public String gerarUrlRequisicao() {

        String url = super.gerarUrlRequisicao();
        SolicitacaoControllerERP sl = getSoliciatacao();
        JsonObject parametrosLista = UtilSBCoreJson.getJsonObjectByTexto(sl.getCorpoParametros());
        final StringBuilder strBuilderAtributoParametros = new StringBuilder();
        if (parametrosLista.containsKey("parametros")) {
            if (!parametrosLista.isNull("parametros")) {
                JsonObject parametro = parametrosLista.getJsonObject("parametros");
                parametro.keySet().stream().forEach(atributo -> {
                    strBuilderAtributoParametros.append(atributo);
                    strBuilderAtributoParametros.append("=");
                    try {
                        strBuilderAtributoParametros.append(URLEncoder.encode(parametrosLista.getJsonObject("parametros").get(atributo).toString(), StandardCharsets.UTF_8.toString()));
                    } catch (UnsupportedEncodingException ex) {
                        Logger.getLogger(IntegracaoRestInterprestfullAcoesGetListaEntidades.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });

            }
        }
        String parametrosDeUrl = strBuilderAtributoParametros.toString();
        if (!parametrosDeUrl.isEmpty()) {
            url = url + "/?" + parametrosDeUrl;
        }
        return url;
    }

}
