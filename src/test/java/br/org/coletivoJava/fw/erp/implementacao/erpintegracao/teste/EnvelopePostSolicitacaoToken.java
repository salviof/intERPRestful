/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.teste;

import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.FabConfigModuloWebERPChaves;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.SistemaERPAtual;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.SistemaERPConfiavel;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.servletOauthServer.FabTipoRequisicaoOauthServer;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.servletOauthServer.ServletOauth2Server;
import com.super_bits.modulos.SBAcessosModel.model.UsuarioSB;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.ConfigGeral.arquivosConfiguracao.ConfigModulo;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreCriptoRSA;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.coletivojava.fw.api.tratamentoErros.FabErro;
import static org.mockito.Mockito.when;

/**
 *
 * @author sfurbino
 */
public class EnvelopePostSolicitacaoToken extends EnvelopeRequisicaoServlet {

    private final SistemaERPAtual sistemaServidor;
    private final SistemaERPConfiavel sistemaConfiavel;
    private final String usuario;
    private final String chavPrivadaSolicitante;

    public EnvelopePostSolicitacaoToken(HttpServletRequest pRequisicao, HttpServletResponse pResposta,
            SistemaERPAtual pSistemaServidor, SistemaERPConfiavel pSistemaRequisicao, String pEmailUsuarioACesso, String pChavePrivada
    ) {
        super(pRequisicao, pResposta);
        sistemaServidor = pSistemaServidor;
        sistemaConfiavel = pSistemaRequisicao;
        buildRequisicao();
        chavPrivadaSolicitante = pChavePrivada;
        usuario = pEmailUsuarioACesso;
    }

    private void buildRequisicao() {
        String emailcripto = UtilSBCoreCriptoRSA.getTextoCriptografado(usuario, chavPrivadaSolicitante);

        adicionarHeader("emailCripto", emailcripto);
        adicionarHeader("CHAVE_PUBLICA", sistemaConfiavel.getChavePublica());
        setOrigimHeader(sistemaConfiavel.getDominio());

        ConfigModulo moduloServidorOauth = SBCore.getConfigModulo(FabConfigModuloWebERPChaves.class);

        try {
            String urlRequisicao = "/" + ServletOauth2Server.SLUGPUBLICACAOSERVLET
                    + "/" + FabTipoRequisicaoOauthServer.OBTER_CODIGO_DE_AUTORIZACAO.toString()
                    + "/" + sistemaServidor.getHashChavePublica()
                    + "/" + URLEncoder.encode(sistemaConfiavel.getHashChavePublica())
                    + "/" + URLEncoder.encode("https://crm.coletivojava.com.br/solicitacaoAuth2Recept/code/USUARIO/", "UTF8")
                    + "/USUARIO";
            setRequestURI(urlRequisicao);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(EnvelopePostSolicitacaoToken.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public String getRespostaGet(HttpServlet pServeletProcessador) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getRespostaPost(HttpServlet pServeletProcessador) {
        try {
            ServletOauth2Server servlet = (ServletOauth2Server) pServeletProcessador;
            servlet.doPost(getRequisicao(), getResposta());
            String resposta = getStringwriter().getBuffer().toString().trim();
            return resposta;
        } catch (ServletException | IOException ex) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Falha chamando servlet" + ex.getMessage(), ex);
            return null;
        }
    }

}
