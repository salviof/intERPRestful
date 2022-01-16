/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.teste;

import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.SistemaERPAtual;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.SistemaERPConfiavel;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.servletOauthServer.ServletOauth2Server;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.coletivojava.fw.api.tratamentoErros.FabErro;

/**
 *
 * @author sfurbino
 */
public class EnvelopeServeletSolicitarCodigoObterToken extends EnvelopeRequisicaoServlet {

    private final SistemaERPAtual sistemaServidor;
    private final SistemaERPConfiavel sistemaConfiavel;
    private final String emailSolicitante;

    public EnvelopeServeletSolicitarCodigoObterToken(HttpServletRequest pRequisicao, HttpServletResponse pResposta,
            SistemaERPAtual pSistemaServidor, SistemaERPConfiavel pSistemaRequisicao, String pEmailSolicitante
    ) {
        super(pRequisicao, pResposta);
        sistemaServidor = pSistemaServidor;
        sistemaConfiavel = pSistemaRequisicao;
        emailSolicitante = pEmailSolicitante;
        buildRequisicao();
    }

    private void buildRequisicao() {
        adicionarHeader("CHAVE_PUBLICA", sistemaConfiavel.getChavePublica());
        setOrigimHeader(sistemaConfiavel.getDominio());

        try {
            String urlRequisicao = "/OAUTH2_SERVICE"
                    + "/OBTER_CODIGO_DE_CONCESSAO_DE_ACESSO"
                    + "/" + sistemaServidor.getHashChavePublica()
                    + "/" + sistemaConfiavel.getHashChavePublica()
                    + "/" + URLEncoder.encode("https://crm.coletivojava.com.br/solicitacaoAuth2Recept/code/USUARIO/", "UTF8")
                    + "/" + emailSolicitante;
            setRequestURI(urlRequisicao);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(EnvelopeServeletSolicitarCodigoObterToken.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public String getRespostaGet(HttpServlet pServeletProcessador) {
        try {
            ServletOauth2Server servlet = (ServletOauth2Server) pServeletProcessador;
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
