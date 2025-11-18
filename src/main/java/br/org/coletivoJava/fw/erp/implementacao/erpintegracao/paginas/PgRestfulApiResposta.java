/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.paginas;

import br.org.coletivoJava.fw.api.erp.erpintegracao.ApiIntegracaoRestful;
import br.org.coletivoJava.fw.api.erp.erpintegracao.servico.ItfIntegracaoERP;
import com.super_bits.modulos.SBAcessosModel.view.FabAcaoPaginasDoSistema;
import com.super_bits.modulos.SBAcessosModel.view.InfoAcaoPaginaDoSistema;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.ItfRespostaAcaoDoSistema;
import com.super_bits.modulosSB.SBCore.modulos.Controller.qualificadoresCDI.sessao.QlSessaoFacesContext;
import com.super_bits.modulosSB.SBCore.modulos.erp.SolicitacaoControllerERP;

import com.super_bits.modulosSB.webPaginas.controller.sessao.SessaoAtualSBWP;
import com.super_bits.modulosSB.webPaginas.util.UtilSBWPServletTools;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import com.super_bits.modulosSB.SBCore.modulos.servicosCore.ComoServicoControllerExecucao;

@RequestScoped
@Named
@InfoAcaoPaginaDoSistema(acao = FabAcaoPaginasDoSistema.PAGINA_NATIVA_RESTFUL_RESP_MB_GESTAO)
public class PgRestfulApiResposta implements ComoServicoControllerExecucao, Serializable {

    @Inject
    @ApiIntegracaoRestful()
    private ItfIntegracaoERP erpIntegraca;

    @Inject
    @QlSessaoFacesContext
    private SessaoAtualSBWP sessaoAtualUsuario;

    private String respostaJson;
    private SolicitacaoControllerERP solicitacao;

    @PostConstruct
    public void inicio() {
        solicitacao = (SolicitacaoControllerERP) UtilSBWPServletTools.getRequestAtribute("solicitacao");
    }

    @Override
    public ItfRespostaAcaoDoSistema executarAcao(SolicitacaoControllerERP pSolicitacao) {

        System.out.println(sessaoAtualUsuario.getUsuario().getEmail());
        ItfRespostaAcaoDoSistema resposta = erpIntegraca.gerarRespostaAcaoDoSistemaServico(pSolicitacao);
        respostaJson = erpIntegraca.getRespostaJsonString(resposta);
        return resposta;
    }

    public String getRespostaJson() {
        if (respostaJson == null) {
            executarAcao(solicitacao);
        }
        return respostaJson;
    }

}
