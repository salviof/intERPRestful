/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.parametros;

import br.org.coletivoJava.integracoes.restInterprestfull.implementacao.IntegracaoRestInterprestfullAcoesGetListaEntidades;
import com.super_bits.modulosSB.SBCore.modulos.erp.SolicitacaoControllerERP;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoObjetoSB;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author salvio
 */
@InfoObjetoSB(tags = "Parametro  lista restful", plural = "Parametros lista Restful")
public class ParametroListaRestful extends ParametroRestful {

    private int pagina = 0;
    private int limite = 10;
    private String atributo;
    private Map<String, Object> filtros;

    public ParametroListaRestful() {
        super(ParametroListaRestful.class.getSimpleName());
    }

    public ParametroListaRestful(SolicitacaoControllerERP pSolicitacaoRequest) {
        super(ParametroListaRestful.class.getSimpleName());
        filtros = new HashMap<>();

        for (Map.Entry<String, String> chaves : pSolicitacaoRequest.getParametrosDeUrl().entrySet()) {
            final String valorStr = chaves.getValue();
            final String atr = chaves.getKey();
            switch (atr) {
                case IntegracaoRestInterprestfullAcoesGetListaEntidades.ATRIBUTO_JSON_FILTRO_ATRIBUTO_SUBLISTA:
                    atributo = valorStr;
                    break;
                case IntegracaoRestInterprestfullAcoesGetListaEntidades.ATRIBUTO_JSON_FILTRO_CODIGO:
                    setId(Integer.valueOf(valorStr));
                    break;
                case IntegracaoRestInterprestfullAcoesGetListaEntidades.ATRIBUTO_JSON_FILTRO_LIMITE:
                    setLimite(Integer.valueOf(valorStr));

                    break;
                case IntegracaoRestInterprestfullAcoesGetListaEntidades.ATRIBUTO_JSON_FILTRO_PAGINA:
                    setPagina(Integer.valueOf(valorStr));
                    break;
                default:
                    filtros.put(atr, valorStr);

            }
        }
    }

    public int getPagina() {
        return pagina;
    }

    public void setPagina(int pagina) {
        this.pagina = pagina;
    }

    public Map<String, Object> getFiltros() {
        return filtros;
    }

    public void setFiltros(Map<String, Object> filtros) {
        this.filtros = filtros;
    }

    public String getAtributo() {
        return atributo;
    }

    public void setAtributo(String atributo) {
        this.atributo = atributo;
    }

    public int getLimite() {
        return limite;
    }

    public void setLimite(int limite) {
        this.limite = limite;
    }

}
