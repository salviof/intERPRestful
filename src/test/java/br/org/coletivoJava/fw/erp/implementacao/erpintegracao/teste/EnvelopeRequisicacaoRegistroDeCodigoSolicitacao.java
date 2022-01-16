/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.teste;

import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.servletOauthServer.ServletOauth2Server;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.webPaginas.controller.servlets.servletRecepcaoOauth.ServletRecepcaoOauth;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.coletivojava.fw.api.tratamentoErros.FabErro;

/**
 *
 * @author sfurbino
 */
public class EnvelopeRequisicacaoRegistroDeCodigoSolicitacao extends EnvelopeRequisicaoServlet {

    private final String codigo;
    private final String url;
    private final String hashServidor;

    public EnvelopeRequisicacaoRegistroDeCodigoSolicitacao(HttpServletRequest pRequisicao,
            HttpServletResponse pResposta, String pCodigo, String pUrl, String pHashServidor
    ) {
        super(pRequisicao, pResposta);
        codigo = pCodigo;
        url = pCodigo;
        hashServidor = pHashServidor;
        buildRequisicao();

    }

    private void buildRequisicao() {
        setRequestURI(url);
        adicionarParametro("code", codigo);
        adicionarParametro("tipoAplicacao", hashServidor);
    }

    @Override
    public String getRespostaGet(HttpServlet pServeletProcessador) {
        try {
            ServletRecepcaoOauth servlet = (ServletRecepcaoOauth) pServeletProcessador;
            servlet.doGet(getRequisicao(), getResposta());
            String resposta = getStringwriter().getBuffer().toString().trim();
            return resposta;
        } catch (ServletException | IOException ex) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Falha chamando servlet" + ex.getMessage(), ex);
            return null;
        }
    }

    @Override
    public String getRespostaPost(HttpServlet pServeletProcessador) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
