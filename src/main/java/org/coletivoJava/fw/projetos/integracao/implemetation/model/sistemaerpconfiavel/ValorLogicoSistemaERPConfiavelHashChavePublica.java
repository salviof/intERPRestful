package org.coletivoJava.fw.projetos.integracao.implemetation.model.sistemaerpconfiavel;

import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.SistemaERPConfiavel;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilCRCStringValidador;
import com.super_bits.modulosSB.SBCore.modulos.objetos.calculos.ValorLogicoCalculoGenerico;
import org.coletivoJava.fw.projetos.integracao.api.model.sistemaerpconfiavel.ValorLogicoSistemaERPConfiavel;
import org.coletivoJava.fw.projetos.integracao.api.model.sistemaerpconfiavel.ValoresLogicosSistemaERPConfiavel;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.campoInstanciado.ItfCampoInstanciado;

@ValorLogicoSistemaERPConfiavel(calculo = ValoresLogicosSistemaERPConfiavel.HASHCHAVEPUBLICA)
public class ValorLogicoSistemaERPConfiavelHashChavePublica
        extends
        ValorLogicoCalculoGenerico {

    public ValorLogicoSistemaERPConfiavelHashChavePublica(ItfCampoInstanciado pCampo) {
        super(pCampo);
    }

    @Override
    public Object getValor(Object... pEntidade) {

        if (!UtilCRCStringValidador.isNuloOuEmbranco(getSistema().getChavePublica())) {
            getSistema().setHashChavePublica(String.valueOf(getSistema().getChavePublica().hashCode()));
        }

        return getSistema().getHashChavePublica();
    }

    public SistemaERPConfiavel getSistema() {
        return (SistemaERPConfiavel) getCampoInst().getObjetoDoAtributo();
    }
}
