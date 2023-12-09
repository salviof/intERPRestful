package br.org.coletivoJava.integracoes.restInterprestfull.implementacao;

import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.parametros.ParametroListaRestful;
import br.org.coletivoJava.integracoes.restInterprestfull.api.InfoIntegracaoRestInterprestfullRestfull;
import br.org.coletivoJava.integracoes.restInterprestfull.api.FabIntApiRestIntegracaoERPRestfull;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreJson;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;
import com.super_bits.modulosSB.SBCore.modulos.erp.SolicitacaoControllerERP;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfUsuario;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.math3.analysis.function.Add;

@InfoIntegracaoRestInterprestfullRestfull(tipo = FabIntApiRestIntegracaoERPRestfull.ACOES_GET_LISTA_ENTIDADES)
public class IntegracaoRestInterprestfullAcoesGetListaEntidades
        extends
        IntegracaoResfullPadrao {

    public static final String ATRIBUTO_JSON_FILTRO_PAGINA = "pagina";
    public static final String ATRIBUTO_JSON_FILTRO_LIMITE = "pgItemLimite";
    public static final String ATRIBUTO_JSON_FILTRO_ATRIBUTO_SUBLISTA = "atributo";
    public static final String ATRIBUTO_JSON_FILTRO_CODIGO = "id";

    public IntegracaoRestInterprestfullAcoesGetListaEntidades(
            final String pTipoAplicacaoERP,
            final FabTipoAgenteClienteApi pTipoAgente,
            final ItfUsuario pUsuario, final java.lang.Object... pParametro) {
        super(pTipoAplicacaoERP, FabIntApiRestIntegracaoERPRestfull.ACOES_GET_LISTA_ENTIDADES,
                pTipoAgente, pUsuario, pParametro);

    }

    @Override
    public SolicitacaoControllerERP getSoliciatacao() {
        return super.getSoliciatacao(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    protected List<Object> gerarParametrosResFullPelaSolicitacao() {

        JsonObject parametrosJson = null;
        SolicitacaoControllerERP sl = getSoliciatacao();
        List<Object> novosPr = new ArrayList<>();

        parametrosJson = UtilSBCoreJson.getJsonObjectByTexto(sl.getCorpoParametros());

        novosPr.add(parametrosJson.getJsonNumber(ATRIBUTO_JSON_FILTRO_PAGINA).intValue());

        if (parametrosJson.containsKey(ATRIBUTO_JSON_FILTRO_ATRIBUTO_SUBLISTA)) {
            if (!parametrosJson.isNull(ATRIBUTO_JSON_FILTRO_ATRIBUTO_SUBLISTA)) {
                String valor = parametrosJson.getString(ATRIBUTO_JSON_FILTRO_ATRIBUTO_SUBLISTA);
                novosPr.add(valor);
            }

        }

        return novosPr;
    }

    @Override
    public String gerarUrlRequisicao() {
        SolicitacaoControllerERP sl = getSoliciatacao();
        List<String> filtros = new ArrayList<>();
        JsonObject parametrosJson = null;

        parametrosJson = UtilSBCoreJson.getJsonObjectByTexto(sl.getCorpoParametros());

        String url = super.gerarUrlRequisicao();

        JsonObject parametrosLista = UtilSBCoreJson.getJsonObjectByTexto(sl.getCorpoParametros());
        final StringBuilder strBuilderAtributoParametros = new StringBuilder();
        int idxParam = 0;
        if (parametrosLista.containsKey("filtros")) {
            if (!parametrosLista.isNull("filtros")) {
                JsonObject parametro = parametrosLista.getJsonObject("filtros");

                for (String atributo : parametro.keySet()) {
                    if (idxParam > 0) {
                        strBuilderAtributoParametros.append("&");
                    }
                    strBuilderAtributoParametros.append(atributo);
                    strBuilderAtributoParametros.append("=");
                    try {
                        if (!parametro.isNull(atributo)) {
                            switch (parametro.get(atributo).getValueType()) {
                                case ARRAY:

                                    break;
                                case OBJECT:
                                    break;
                                case STRING:
                                    strBuilderAtributoParametros.append(URLEncoder.encode(parametro.getString(atributo), StandardCharsets.UTF_8.toString()));
                                    break;
                                case NUMBER:
                                    if (parametro.getJsonNumber(atributo).isIntegral()) {
                                        strBuilderAtributoParametros.append(parametro.getJsonNumber(atributo).longValue());
                                    } else {
                                        strBuilderAtributoParametros.append(parametro.getJsonNumber(atributo).doubleValue());
                                    }
                                    break;
                                case TRUE:
                                    strBuilderAtributoParametros.append(1);
                                    break;
                                case FALSE:
                                    strBuilderAtributoParametros.append(0);
                                    break;
                                case NULL:
                                    break;

                                default:
                                    throw new AssertionError();
                            }

                        }

                    } catch (UnsupportedEncodingException ex) {
                        Logger.getLogger(IntegracaoRestInterprestfullAcoesGetListaEntidades.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    idxParam++;
                }

            }

        }
        String parametrosDeUrl = strBuilderAtributoParametros.toString();
        if (!parametrosDeUrl.isEmpty()) {
            url = url + "/?" + parametrosDeUrl;
        }
        return url;
    }

}
