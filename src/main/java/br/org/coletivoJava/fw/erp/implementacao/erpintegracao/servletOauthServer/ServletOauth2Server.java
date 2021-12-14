/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.servletOauthServer;

import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreCriptoRCA;
import br.org.coletivoJava.fw.api.erp.erpintegracao.contextos.ERPIntegracaoSistemasApi;
import br.org.coletivoJava.fw.api.erp.erpintegracao.model.ItfSistemaErp;
import br.org.coletivoJava.fw.api.erp.erpintegracao.servico.ItfIntegracaoERP;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.MapaTokensGerenciadosConcessaoOauth;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.TokenConcessaoOauthServer;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfUsuario;
import com.super_bits.modulosSB.webPaginas.controller.servletes.urls.UrlInterpretada;
import com.super_bits.modulosSB.webPaginas.controller.servletes.util.UtilFabUrlServlet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.net.URLDecoder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author sfurbino
 */
public class ServletOauth2Server extends HttpServlet implements Serializable {

    private static final ItfIntegracaoERP integracaoEntreSistemas = ERPIntegracaoSistemasApi.RESTFUL.getImplementacaoDoContexto();

    @Override
    public void doGet(HttpServletRequest requisicao, HttpServletResponse resp) throws ServletException, IOException {

        UrlInterpretada parametrosDeUrl;
        try {

            parametrosDeUrl = UtilFabUrlServlet.getUrlInterpretada(FabUrlOauth2Server.class, requisicao);

        } catch (Throwable t) {
            resp.getWriter().append("PARAMETROS DE ACESSO INCORRETOS, VERIFIQUE A DOCUMENTAÇÃO DA CLASSE " + FabUrlOauth2Server.class.getSimpleName());
            return;
        }

        String chavePublica = URLDecoder.decode(parametrosDeUrl.getValor(FabUrlOauth2Server.CHAVE_PUBLICA_ID_CLIENTE).toString());
        String emailCriptografado = requisicao.getHeader("emailCripto");
        String emailDescriptografado = UtilSBCoreCriptoRCA.getTextoDescriptografado(emailCriptografado, chavePublica);
        ItfSistemaErp sistemaSolicitante = integracaoEntreSistemas.getSistemaByChavePublica(chavePublica);

        if (sistemaSolicitante == null) {
            resp.getWriter().append("ACESSO NEGADO- CHAVE PÚBLICA NÃO ENCONTRADA, VOCÊ PRECISA CADASTRAR A CHAVE PÚBLICA DO APLICATIVO COMO CONFIÁVEL");
            return;

        }

        //Verifica se a origem é vinda do dominio do sistema solicitante
        String dominioDoSistema = sistemaSolicitante.getDominio();
        String dominioDaRequisicao = requisicao.getHeader("origin");
        if (dominioDaRequisicao == null || !dominioDoSistema.equals(dominioDaRequisicao)) {
            resp.getWriter().append("ACESSO NEGADO, A ORIGEM DA REQUISIÇÃO DIVERGE DA ORIEM AUTORIZADA");
            return;

        }
        boolean foiCriptografadoComAChavePrivada = true;
        ItfUsuario pUsuario = SBCore.getServicoPermissao().getUsuarioByEmail(emailDescriptografado);

        if (emailDescriptografado == null) {
            resp.getWriter().append("ACESSO NEGADO IMPOSSÍVEL RECONHECER O USUÁRIO VERIFIQUE SUA CHAVE PRIVADA");
            return;
        }

        if (pUsuario == null) {
            resp.getWriter().append("ACESSO NEGADO O USUÁRIO " + emailDescriptografado + " NÃO FOI ENCONTRADO NO SISTEMA");
            return;
        }

        if (pUsuario.equals(SBCore.getUsuarioLogado())) {

            TokenConcessaoOauthServer tokenConcessaodeAcesso = MapaTokensGerenciadosConcessaoOauth.gerarNovoTokenCocessaoDeAcesso(sistemaSolicitante, pUsuario);
            String url = URLDecoder.decode(parametrosDeUrl.getValorComoString(FabUrlOauth2Server.REDIRECT_URI));
            url = url + "?code=" + tokenConcessaodeAcesso.getToken();
            resp.getWriter().append("<script> windows.location='" + url + "'</script>");
            resp.sendRedirect(url);
        } else {
            if (SBCore.getServicoSessao().getSessaoAtual().isIdentificado()) {

                SBCore.getServicoSessao().efetuarLogOut();
            }
            if (foiCriptografadoComAChavePrivada) {
                resp.getWriter().append("<form><input value='" + emailDescriptografado + "' > <buton  /> </form>");

            } else {
                resp.getWriter().append("ACESSO NEGADO");
            }
        }

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null) {
                jb.append(line);
            }
        } catch (Exception e) {
            /*report an error*/ }

        try {
            String jsonSTR = jb.toString();
            System.out.println(jsonSTR);
        } catch (Throwable e) {
            // crash and burn
            throw new IOException("Error parsing JSON request string");
        }

    }

}
