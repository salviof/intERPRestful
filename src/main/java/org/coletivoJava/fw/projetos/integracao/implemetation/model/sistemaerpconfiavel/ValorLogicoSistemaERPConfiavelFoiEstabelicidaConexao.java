package org.coletivoJava.fw.projetos.integracao.implemetation.model.sistemaerpconfiavel;

import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.SistemaERPConfiavel;
import br.org.coletivoJava.integracoes.restInterprestfull.api.FabIntApiRestIntegracaoERPRestfull;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token.ItfTokenGestao;
import com.super_bits.modulosSB.SBCore.modulos.objetos.calculos.ValorLogicoCalculoGenerico;
import org.coletivoJava.fw.projetos.integracao.api.model.sistemaerpconfiavel.ValorLogicoSistemaERPConfiavel;
import org.coletivoJava.fw.projetos.integracao.api.model.sistemaerpconfiavel.ValoresLogicosSistemaERPConfiavel;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.campoInstanciado.ItfCampoInstanciado;
import org.coletivojava.fw.api.tratamentoErros.FabErro;

@ValorLogicoSistemaERPConfiavel(calculo = ValoresLogicosSistemaERPConfiavel.FOIESTABELICIDACONEXAO)
public class ValorLogicoSistemaERPConfiavelFoiEstabelicidaConexao
        extends
        ValorLogicoCalculoGenerico {

    public ValorLogicoSistemaERPConfiavelFoiEstabelicidaConexao(
            ItfCampoInstanciado pCampo) {
        super(pCampo);
    }

    @Override
    public Object getValor(Object... pEntidade) {
        if (getSistema().getId() != null && getSistema().getId() > 0) {
            try {
                if (!isCacheAtivado()) {
                    ItfTokenGestao gestao = FabIntApiRestIntegracaoERPRestfull.OAUTH_VALIDAR_CREDENCIAL.getGestaoToken(getSistema());
                    if (gestao == null) {
                        getSistema().setFoiEstabelicidaConexao(false);
                    } else {
                        getSistema().setFoiEstabelicidaConexao(gestao.isTemTokemAtivo());
                    }

                    setCacheSegundosPadrao(10);
                }
            } catch (Throwable t) {
                SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Falha detectando conexão estabeleciada com app" + getSistema().getNome(), t);
            }
        }
        return getSistema().isFoiEstabelicidaConexao();
    }

    public SistemaERPConfiavel getSistema() {
        return (SistemaERPConfiavel) getCampoInst().getObjetoDoAtributo();
    }
}
