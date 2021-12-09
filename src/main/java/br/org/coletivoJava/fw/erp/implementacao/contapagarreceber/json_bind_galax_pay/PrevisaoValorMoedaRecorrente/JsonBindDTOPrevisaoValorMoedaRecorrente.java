package br.org.coletivoJava.fw.erp.implementacao.contapagarreceber.json_bind_galax_pay.PrevisaoValorMoedaRecorrente;

import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.erp.dto.DTO_SB_JSON_PROCESSADOR_GENERICO;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;

public class JsonBindDTOPrevisaoValorMoedaRecorrente
        extends
        DTO_SB_JSON_PROCESSADOR_GENERICO<DTOPrevisaoValorMoedaRecorrente> {

    public JsonBindDTOPrevisaoValorMoedaRecorrente() {
        super(DTOPrevisaoValorMoedaRecorrente.class);
    }

    @Override
    public DTOPrevisaoValorMoedaRecorrente deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        ObjectCodec codec = jp.getCodec();
        JsonNode node = codec.readTree(jp);
        DTOPrevisaoValorMoedaRecorrente dtoCobrancasazonal = new DTOPrevisaoValorMoedaRecorrente();
        adicionarPropriedadeDouble("valor", node, "value");
        adicionarPropriedadeData("dataPrevista", node, "payday");

        if (node.has("Pix")) {
            JsonNode pix = node.get("Pix");
            adicionarPropriedadeString("pix", pix, "qrCode");
        }
        if (node.has("Boleto")) {
            JsonNode boleto = node.get("Boleto");
            adicionarPropriedadeString("PDFCobranca", boleto, "pdf");
        }
        adicionarPropriedadeBoolean("pagamentoEfetuado", "payedBoleto", node, "status");

        selarProcesamento(dtoCobrancasazonal);
        return dtoCobrancasazonal;
    }
}
