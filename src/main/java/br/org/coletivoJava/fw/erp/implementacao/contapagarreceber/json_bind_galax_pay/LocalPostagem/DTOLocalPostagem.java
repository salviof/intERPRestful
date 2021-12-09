package br.org.coletivoJava.fw.erp.implementacao.contapagarreceber.json_bind_galax_pay.LocalPostagem;

import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.erp.dto.DTO_SBGENERICO;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.cep.ItfBairro;

public class DTOLocalPostagem extends DTO_SBGENERICO<ItfDTOLocalPostagem>
        implements
        ItfDTOLocalPostagem {

    public DTOLocalPostagem(String pJson) {
        super(JsonBindDTOLocalPostagem.class, pJson);
    }

    public DTOLocalPostagem() {
        super(null, null);
    }

    @Override
    public void setCep(String pCep) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setLogradouro(String pLogradouro) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setLatitude(long pLatitude) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setLongitude(long pLongitude) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setBairro(ItfBairro bairro) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setComplemento(String pComplemento) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
