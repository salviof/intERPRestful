package br.org.coletivoJava.fw.erp.implementacao.contapagarreceber.json_bind_galax_pay.PessoaFisicoJuridico;

import br.org.coletivoJava.fw.erp.implementacao.contapagarreceber.json_bind_galax_pay.PessoaFisicoJuridico.ItfDTOPessoaFisicoJuridico;
import br.org.coletivoJava.fw.erp.implementacao.contapagarreceber.json_bind_galax_pay.PessoaFisicoJuridico.JsonBindDTOPessoaFisicoJuridico;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.erp.dto.DTO_SBGENERICO;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.cep.ItfLocal;

public class DTOPessoaFisicoJuridico
        extends
        DTO_SBGENERICO<ItfDTOPessoaFisicoJuridico>
        implements
        ItfDTOPessoaFisicoJuridico {

    public DTOPessoaFisicoJuridico(String pJson) {
        super(JsonBindDTOPessoaFisicoJuridico.class, pJson);
    }

    public DTOPessoaFisicoJuridico() {
        super(null, null);
    }

    @Override
    public void setLocalizacao(ItfLocal pLocal) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setNomeLongo(String pnomeLongo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setDescritivo(String pDescritivo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setAtivo(boolean pAtivo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getImgMedia() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isTemImagemMedioAdicionada() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getImgGrande() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isTemImagemGrandeAdicionada() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
