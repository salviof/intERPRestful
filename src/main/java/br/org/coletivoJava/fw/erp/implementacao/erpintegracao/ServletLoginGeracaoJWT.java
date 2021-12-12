/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao;

import br.org.coletivoJava.fw.api.erp.erpintegracao.contextos.ERPIntegracaoSistemasApi;
import br.org.coletivoJava.fw.api.erp.erpintegracao.model.ItfSistemaErp;
import br.org.coletivoJava.fw.api.erp.erpintegracao.servico.ItfIntegracaoERP;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfUsuario;
import java.io.IOException;
import java.io.Serializable;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author sfurbino
 */
public class ServletLoginGeracaoJWT extends HttpServlet implements Serializable {

    private static final ItfIntegracaoERP integracaoEntreSistemas = ERPIntegracaoSistemasApi.RESTFUL.getImplementacaoDoContexto();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String chavePublica = req.getHeader("CHAVE_PUBLICA");
        String emailCriptografado = req.getHeader("emailCripto");
        String emailDescriptografado = UtilSBCoreSegurancaRCA.getTextoDescriptografado(emailCriptografado, chavePublica);
        ItfSistemaErp sistemaSolicitante = integracaoEntreSistemas.getSistemaByChavePublica(chavePublica);

        if (sistemaSolicitante == null) {
            resp.getWriter().append("ACESSO NEGADO- CHAVE PÚBLICA NÃO ENCONTRADA, VOCÊ PRECISA CADASTRAR A CHAVE PÚBLICA DO APLICATIVO COMO CONFIÁVEL");
            return;

        }

        //Verifica se a origem é vinda do dominio do sistema solicitante
        String dominioDoSistema = sistemaSolicitante.getDominio();
        String dominioDaRequisicao = req.getHeader("origin");
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
            resp.sendRedirect(chavePublica);
        } else {
            if (foiCriptografadoComAChavePrivada) {
                resp.getWriter().append("<form><input value='" + emailDescriptografado + "' > <buton  /> </form>");

            } else {
                resp.getWriter().append("ACESSO NEGADO");
            }
        }

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String chavePublica = req.getHeader("CHAVE_PUBLICA");
        String usuario = req.getParameter("nomeUsuario");

        ItfSistemaErp sistemaSolicitante = integracaoEntreSistemas.getSistemaByChavePublica(chavePublica);
        String urlRecepcaoCodigop = sistemaSolicitante.getUrlRecepcaoCodigo();

    }

}
