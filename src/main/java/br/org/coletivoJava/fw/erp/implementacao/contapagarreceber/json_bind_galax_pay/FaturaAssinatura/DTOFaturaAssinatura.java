package br.org.coletivoJava.fw.erp.implementacao.contapagarreceber.json_bind_galax_pay.FaturaAssinatura;

import br.org.coletivoJava.fw.api.erp.contaPagarReceber.model.formaPagamento.ItfFormaPagamento;
import br.org.coletivoJava.fw.api.erp.contaPagarReceber.model.valormoedaFuturo.ItfPrevisaoValorMoeda;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.erp.dto.DTO_SBGENERICO;
import java.util.Date;
import java.util.List;

public class DTOFaturaAssinatura extends DTO_SBGENERICO<ItfDTOFaturaAssinatura>
        implements
        ItfDTOFaturaAssinatura {

    public DTOFaturaAssinatura(String pJson) {
        super(JsonBindDTOFaturaAssinatura.class, pJson);
    }

    public DTOFaturaAssinatura() {
        super(null, null);
    }

    @Override
    public void setDataPrimeiroPagamento(Date dataPrimeiroPagamento) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setDiaDoMesVencimento(int diaDoMesPagamento) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setFormaPagamento(ItfFormaPagamento formaPagamento) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setParcelas(List<ItfPrevisaoValorMoeda> parcelas) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setQtdParcelas(int qtdParcelas) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setTipoFatura(String tipoFatura) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setValorTotal(double valorTotal) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
