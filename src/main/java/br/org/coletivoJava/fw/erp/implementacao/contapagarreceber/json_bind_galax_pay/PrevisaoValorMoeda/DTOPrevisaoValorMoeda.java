package br.org.coletivoJava.fw.erp.implementacao.contapagarreceber.json_bind_galax_pay.PrevisaoValorMoeda;

import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.erp.dto.DTO_SBGENERICO;
import java.util.Date;

public class DTOPrevisaoValorMoeda
        extends
        DTO_SBGENERICO<ItfDTOPrevisaoValorMoeda>
        implements
        ItfDTOPrevisaoValorMoeda {

    public DTOPrevisaoValorMoeda(String pJson) {
        super(JsonBindDTOPrevisaoValorMoeda.class, pJson);
    }

    public DTOPrevisaoValorMoeda() {
        super(null, null);
    }

    @Override
    public void setValor(double valor) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setDataPrevista(Date pDataPrevista) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setParcelaNumero(double parcelaNumero) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
