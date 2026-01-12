/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilCRCDataHora;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.campoInstanciado.ItfCampoInstanciado;
import com.super_bits.modulosSB.SBCore.modulos.objetos.entidade.basico.ComoEntidadeSimples;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.Entity;

/**
 *
 * @author salvio
 */
public class SerializadorPadrao extends JsonSerializer<ComoEntidadeSimples> {

    @Override
    public void serialize(ComoEntidadeSimples itemSimples, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();

        boolean representaEntidade = itemSimples.getClass().isAnnotationPresent(Entity.class);

        boolean processarListas = false;

        if (!representaEntidade) {
            processarListas = true;
        }

        for (ItfCampoInstanciado cpInst : itemSimples.getCamposInstanciados()) {

            try {
                if (cpInst.getValor() == null) {
                    continue;
                }
                if (cpInst.getValor() instanceof Map) {

                    gen.writeObjectField(cpInst.getNomeCamponaClasse(), cpInst.getValor());

                } else {

                    switch (cpInst.getTipoPrimitivoDoValor()) {
                        case INTEIRO:
                            gen.writeNumberField(cpInst.getNomeCamponaClasse(), cpInst.getValorComoInteger());
                            break;
                        case NUMERO_LONGO:
                            gen.writeNumberField(cpInst.getNomeCamponaClasse(), cpInst.getValorComoLong());
                            break;
                        case LETRAS:
                            gen.writeStringField(cpInst.getNomeCamponaClasse(), String.valueOf(cpInst.getValor()));
                            break;
                        case DATAS:
                            gen.writeStringField(cpInst.getNomeCamponaClasse(), UtilCRCDataHora.getDataHoraString((Date) cpInst.getValor(), UtilCRCDataHora.FORMATO_TEMPO.DATA_USUARIO));
                            break;
                        case BOOLEAN:

                            gen.writeBooleanField(cpInst.getNomeCamponaClasse(), (boolean) cpInst.getValor());

                            break;
                        case DECIMAL:
                            gen.writeNumberField(cpInst.getNomeCamponaClasse(), cpInst.getValorComoDouble());
                            break;
                        case ENTIDADE:
                            if (cpInst.isUmValorEmLista()) {

                                List<ComoEntidadeSimples> itens = (List) cpInst.getValor();
                                List<Long> itensCodigo = new ArrayList<>();
                                for (ComoEntidadeSimples item : itens) {
                                    itensCodigo.add(item.getId());

                                }
                                long[] codigosArray = new long[itens.size()];

                                int i = 0;
                                for (Long element : itensCodigo) {
                                    codigosArray[i++] = element;

                                }
                                gen.writeArray(codigosArray, 0, itensCodigo.size());
                                System.out.println("Lista");
                            } else {
                                if (cpInst.getValor() instanceof ComoEntidadeSimples) {
                                    gen.writeObjectField(cpInst.getNomeCamponaClasse(), ((ComoEntidadeSimples) cpInst.getValor()).getId());
                                }

                            }

                            break;
                        case OUTROS_OBJETOS:
                            if (cpInst.isUmValorEmLista()) {
                                System.out.println("Lista");
                            }
                            break;

                        default:
                            throw new AssertionError();
                    }
                }
            } catch (Throwable t) {

            }
        }

        gen.writeEndObject();
    }

}
