package br.org.coletivoJava.fw.erp.implementacao.contapagarreceber.json_bind_galax_pay.FaturaAssinatura;

import br.org.coletivoJava.fw.erp.implementacao.contapagarreceber.json_bind_galax_pay.PessoaFisicoJuridico.DTOPessoaFisicoJuridico;
import br.org.coletivoJava.fw.erp.implementacao.contapagarreceber.json_bind_galax_pay.PrevisaoValorMoedaRecorrente.DTOPrevisaoValorMoedaRecorrente;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.erp.dto.DTO_SB_JSON_PROCESSADOR_GENERICO;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;

public class JsonBindDTOFaturaAssinatura
        extends
        DTO_SB_JSON_PROCESSADOR_GENERICO<DTOFaturaAssinatura> {

    public JsonBindDTOFaturaAssinatura() {
        super(DTOFaturaAssinatura.class);
    }

    @Override
    public DTOFaturaAssinatura deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        ObjectCodec codec = jp.getCodec();
        JsonNode node = codec.readTree(jp);
        DTOFaturaAssinatura dto = new DTOFaturaAssinatura();

        adicionarPropriedadeInteiro("qtdParcelas", node, "quantity");
        adicionarPropriedadeBoolean("ativo", "active", node, "status");
        adicionarPropriedadeInteiro("id", node, "galaxPayId");
        adicionarPropriedadeDouble("valorAtualMensal", node, "value");
        adicionarPropriedadeData("dataPrimeiroPagamento", node, "firstPayDayDate");
        adicionarPropriedadeListaObjetos(DTOPrevisaoValorMoedaRecorrente.class, "parcelas", node, "Transactions");
        adicionarPropriedadObjeto(DTOPessoaFisicoJuridico.class, "devedor", node, "Customer");

        selarProcesamento(dto);
        if (dto.isAtivo()) {
            System.out.println("");
        }
        return dto;
    }
}
