package org.coletivoJava.fw.projetos.integracao.implementacao.cucumber.fluxooauthinteracaosistema.etapas;

import br.org.coletivoJava.fw.api.erp.erpintegracao.contextos.ERPIntegracaoSistemasApi;
import br.org.coletivoJava.fw.api.erp.erpintegracao.servico.ItfIntegracaoERP;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.SistemaERPConfiavel;
import br.org.coletivoJava.integracoes.restInterprestfull.api.FabIntApiRestIntegracaoERPRestfull;
import br.org.coletivoJava.integracoes.restInterprestfull.implementacao.GestaoTokenRestInterprestfull;
import com.super_bits.modulosSB.Persistencia.dao.UtilSBPersistencia;
import com.super_bits.modulosSB.SBCore.modulos.erp.ItfSistemaERP;
import org.coletivoJava.fw.projetos.integracao.api.cucumber.fluxooauthinteracaosistema.EtapasFluxoOauthInteracaoSistema;
import cucumber.api.java.pt.Dado;
import java.lang.UnsupportedOperationException;
import java.util.List;
import org.coletivoJava.fw.projetos.integracao.api.cucumber.fluxooauthinteracaosistema.FluxoOauth2SistemaAcessoRestfull;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class Dado__um_usuario_logado_no_sitema_com_chave_de_acesso_configuradas_entre_cliente_e_servidor {

    @Dado(EtapasFluxoOauthInteracaoSistema.DADO_UM_USUARIO_LOGADO_NO_SITEMA_COM_CHAVE_DE_ACESSO_CONFIGURADAS_ENTRE_CLIENTE_E_SERVIDOR)
    public void implementacaoEtapa() {

        FluxoOauth2SistemaAcessoRestfull.renovarConexaoEntityManagerEscopoTeste();

        List<SistemaERPConfiavel> sistemasRegistrados = UtilSBPersistencia
                .getListaTodos(SistemaERPConfiavel.class, FluxoOauth2SistemaAcessoRestfull.getEM());
        System.out.println(sistemasRegistrados.size() + " sistemas encontrados");
        for (SistemaERPConfiavel sis : sistemasRegistrados) {
            System.out.println("nome ->" + sis.getNome());
            System.out.println("Dominio ->" + sis.getDominio());
            System.out.println("Hashpub ->" + sis.getHashChavePublica());
            System.out.println("___________________________________________");
        }

        ItfIntegracaoERP erp = ERPIntegracaoSistemasApi.RESTFUL.getImplementacaoDoContexto();
        // sistemaRemoto = (SistemaERPAtual) erp.getSistemaAtual();
        // SBCore.getServicoSessao().logarEmailESenha("salviof@gmail.com", "123456");

        System.out.println("O usuário utiliza o sistema crm.casanovadigital.com.br");
        assertTrue(FluxoOauth2SistemaAcessoRestfull.sistemaCliente.getDominio().contains("crm.casanovadigita"));

        System.out.println("O usuário deseja ver uma nota no sistema fatura");
        ItfSistemaERP sisRemoto = erp.getSistemaByDominio("localhost");
        assertNotNull("O sistema fatura não foi registrado no sistemas", sisRemoto);

        GestaoTokenRestInterprestfull gestaoResful = (GestaoTokenRestInterprestfull) FabIntApiRestIntegracaoERPRestfull.OAUTH_VALIDAR_CREDENCIAL
                .getGestaoToken(sisRemoto);

    }
}
