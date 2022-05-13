package br.org.coletivoJava.integracoes.restInterprestfull.implementacao;

import br.org.coletivoJava.integracoes.restInterprestfull.api.InfoIntegracaoRestInterprestfullRestfull;
import br.org.coletivoJava.integracoes.restInterprestfull.api.FabIntApiRestIntegracaoERPRestfull;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreJson;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringValidador;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;
import com.super_bits.modulosSB.SBCore.modulos.erp.SolicitacaoControllerERP;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfUsuario;
import jakarta.json.JsonObject;
import org.json.JSONObject;

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

        SolicitacaoControllerERP solicitacao = getSoliciatacao();
        String pNomeAcao = solicitacao.getAcaoStrNomeUnico();
        JsonObject parametrosLista = UtilSBCoreJson.getJsonObjectByTexto(solicitacao.getCorpoParametros());
        int pagina = parametrosLista.getInt("pagina");

        parametros = new Object[]{pNomeAcao, pagina};
        super.executarAcao();
    }

}
