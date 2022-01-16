/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.teste;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;

/**
 *
 * @author sfurbino
 */
public abstract class EnvelopeRequisicaoServlet {

    private final HttpServletRequest requisicao;

    private final HttpServletResponse resposta;

    private final StringWriter sw;

    public EnvelopeRequisicaoServlet(HttpServletRequest pRequisicao, HttpServletResponse pResposta) {

        requisicao = pRequisicao;
        resposta = pResposta;
        sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        try {
            Mockito.when(resposta.getWriter()).thenReturn(pw);
        } catch (IOException ex) {
            Logger.getLogger(ApiIntegracaoRestfulimplTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected HttpServletResponse getResposta() {
        return resposta;
    }

    protected HttpServletRequest getRequisicao() {
        return requisicao;
    }

    public void setRequestURI(String pRequestURI) {
        Mockito.when(requisicao.getRequestURI()).thenReturn(pRequestURI);
    }

    public void adicionarHeader(String pNomeHeader, String pResposta) {
        Mockito.when(requisicao.getHeader(pNomeHeader)).thenReturn(pResposta);
    }

    public void adicionarParametro(String pNomeHeader, String pResposta) {
        Mockito.when(requisicao.getParameter(pNomeHeader)).thenReturn(pResposta);
    }

    public void setOrigimHeader(String pOrigem) {
        Mockito.when(requisicao.getHeader("origin")).thenReturn(pOrigem);
    }

    public void setBodyRequisicao(String dataCorpo) {
        try {
            Mockito.when(requisicao.getReader()).thenReturn(new BufferedReader(new StringReader(dataCorpo)));
        } catch (IOException ex) {
            Logger.getLogger(EnvelopeRequisicaoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void adicionarPostData() {

    }

    protected StringWriter getStringwriter() {
        return sw;
    }

    public abstract String getRespostaGet(HttpServlet pServeletProcessador);

    public abstract String getRespostaPost(HttpServlet pServeletProcessador);

}
