package br.org.coletivoJava.fw.erp.implementacao.contapagarreceber;

import br.org.coletivoJava.fw.api.erp.contaPagarReceber.apiCore.ERPContaPagarReceber;
import br.org.coletivoJava.fw.api.erp.contaPagarReceber.apiCore.ItfERPContaPagarReceber;
import java.util.List;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.financeiro.ItfPessoaFisicoJuridico;
import br.org.coletivoJava.fw.api.erp.contapagarreceber.CtPagarReceberGalaxPay;

import br.org.coletivoJava.integracoes.intGalaxPay.api.FabApiRestIntGalaxPayAssinatura;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.RespostaWebServiceSimples;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import br.org.coletivoJava.fw.api.erp.contaPagarReceber.model.assinatura.ItfFaturaAssinatura;
import br.org.coletivoJava.fw.api.erp.contaPagarReceber.model.valormoedaFuturo.ItfPrevisaoValorMoeda;
import br.org.coletivoJava.fw.api.erp.contaPagarReceber.model.valormoedaFuturo.ItfPrevisaoValorMoedaRecorrente;
import br.org.coletivoJava.fw.erp.implementacao.contapagarreceber.json_bind_galax_pay.FaturaAssinatura.DTOFaturaAssinatura;
import br.org.coletivoJava.fw.erp.implementacao.contapagarreceber.json_bind_galax_pay.PrevisaoValorMoeda.DTOPrevisaoValorMoeda;

import br.org.coletivoJava.integracoes.intGalaxPay.api.FabApiRestIntGalaxPayCliente;
import br.org.coletivoJava.integracoes.intGalaxPay.api.FabApiRestIntGalaxPayCobrancaSazonal;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreDataHora;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringFiltros;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringValidador;
import com.super_bits.modulosSB.SBCore.modulos.erp.ErroJsonInterpredador;
import com.super_bits.modulosSB.SBCore.modulos.erp.ItfServicoLinkDeEntidadesERP;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.cep.ItfLocalPostagem;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.coletivojava.fw.api.tratamentoErros.FabErro;

@CtPagarReceberGalaxPay
public class CtPagarReceberGalaxPayimpl
        implements
        ItfERPContaPagarReceber {

    private static final ERPContaPagarReceber galaxPayERPContaAPagar = ERPContaPagarReceber.GALAX_PAY;

    @Override
    public ItfPrevisaoValorMoeda getCobrancaSazonal(Date pData, double pValor, ItfPessoaFisicoJuridico pDevedor) {
        if (UtilSBCoreStringValidador.isNuloOuEmbranco(pDevedor.getCpfCnpj())) {
            return null;
        }
        ItfPessoaFisicoJuridico devedor = getDevedorByCNPJ(pDevedor.getCpfCnpj());
        RespostaWebServiceSimples resposta = FabApiRestIntGalaxPayCobrancaSazonal.COBRANCAS_SAZONAIS_DO_CLIENTE.getAcao(devedor.getId()).getResposta();
        JSONObject json = resposta.getRespostaComoObjetoJson();

        JSONArray arrayCobrancasEncontradas = (JSONArray) json.get("Charges");
        if (arrayCobrancasEncontradas.size() == 0) {
            return null;
        }
        List<ItfPrevisaoValorMoeda> cobrancasSazonais = new ArrayList<>();
        for (Object cobranca : arrayCobrancasEncontradas) {
            JSONObject cobrancaJson = (JSONObject) cobranca;
            String valorStr = cobrancaJson.get("value").toString();
            int tamanhoTotal = valorStr.length();
            String valorStrFormatado = valorStr.substring(0, tamanhoTotal - 2) + "." + valorStr.substring(tamanhoTotal - 2, tamanhoTotal);
            double valorDouble = Double.parseDouble(valorStrFormatado);
            if (valorDouble == pValor) {
                JSONArray arrayTranzacoes = (JSONArray) cobrancaJson.get("Transactions");
                for (Object tranzacao : arrayTranzacoes) {
                    DTOPrevisaoValorMoeda cobrancaSazonal = new DTOPrevisaoValorMoeda(tranzacao.toString());
                    System.out.println(cobrancaSazonal.getValor());
                    System.out.println(cobrancaSazonal.getDataPrevista());
                    cobrancasSazonais.add(cobrancaSazonal);
                }
                Optional<ItfPrevisaoValorMoeda> cobrancaCompativel = cobrancasSazonais.stream().filter(asnt
                        -> asnt.getValor() == pValor && UtilSBCoreDataHora.isDiaIgual(asnt.getDataPrevista(), pData))
                        .findFirst();
                if (cobrancaCompativel.isPresent()) {
                    return cobrancaCompativel.get();
                }
            }
        }

        return null;

    }

    @Override
    public ItfPrevisaoValorMoedaRecorrente getCobrancaAssinatura(Date pData, double pValor, ItfPessoaFisicoJuridico pDevedor) {
        if (pDevedor == null) {
            return null;
        }
        if (pValor == 0) {
            return null;
        }
        ItfPessoaFisicoJuridico devedor = getDevedorByCNPJ(pDevedor.getCpfCnpj());
        if (devedor == null) {
            return null;
        }
        List<ItfFaturaAssinatura> assinaturas = getAssinaturasAtivas(devedor);
        if (assinaturas.isEmpty()) {
            return null;
        }
        if (assinaturas.size() > 1) {
            //throw new UnsupportedOperationException("Foram encontradas mais de uma assinatura para o cliente");
        }
        Optional<ItfFaturaAssinatura> assinaturaEquivalente
                = assinaturas.stream().filter(ass
                        -> ass.getValorAtualMensal() == pValor
                ).findFirst();
        if (!assinaturaEquivalente.isPresent()) {
            assinaturaEquivalente = assinaturas.stream().filter(ass -> ass.getParcelas().stream().filter(p -> p.getValor() == pValor).findFirst().isPresent()).findFirst();
        }
        if (!assinaturaEquivalente.isPresent()) {
            return null;
        }
        Optional<ItfPrevisaoValorMoeda> previssao = assinaturaEquivalente.get().getParcelas().stream()
                .filter(parcela -> parcela.getValor() == pValor
                && UtilSBCoreDataHora.isMesIgual(parcela.getDataPrevista(), pData))
                .findFirst();
        if (!previssao.isPresent()) {
            return null;
        } else {
            return (ItfPrevisaoValorMoedaRecorrente) previssao.get();
        }

    }

    @Override
    public ItfFaturaAssinatura getAssinatura(double pValor, ItfPessoaFisicoJuridico pDevedor) {
        // Considerar Valor CPF do cliente, e estado ativo.

        String cnpj = pDevedor.getCpfCnpj();
        ItfPessoaFisicoJuridico devedor = getDevedorByCNPJ(cnpj);
        if (devedor == null) {
            return null;
        }
        String idCliente = String.valueOf(devedor.getId());
        RespostaWebServiceSimples resposta = FabApiRestIntGalaxPayAssinatura.ASSINATURAS_DO_CLIENTE.getAcao(idCliente).getResposta();
        JSONObject json = resposta.getRespostaComoObjetoJson();
        System.out.println(json.toJSONString());
        JSONArray array = (JSONArray) json.get("Subscriptions");
        List<ItfFaturaAssinatura> assinaturas = new ArrayList<>();

        for (Object jsonAssinatura : array) {
            DTOFaturaAssinatura assinatura = new DTOFaturaAssinatura(jsonAssinatura.toString());
            assinaturas.add(assinatura);
        }
        Optional<ItfFaturaAssinatura> faturaCompativel = assinaturas.stream().filter(asnt
                -> asnt.getValorAtualMensal() == pValor && asnt.isAtivo())
                .findFirst();
        if (!faturaCompativel.isPresent()) {
            faturaCompativel = assinaturas.stream().filter(asnt
                    -> asnt.isAtivo() && asnt.getParcelas().stream().filter(p -> p.getValor() == pValor).findFirst().isPresent())
                    .findFirst();
        }

        if (faturaCompativel.isPresent()) {
            return faturaCompativel.get();
        } else {
            return null;
        }

    }

    @Override
    public List<ItfPrevisaoValorMoeda> getCobrancasSazonaisEmAberto(ItfPessoaFisicoJuridico pPessoas) {
        galaxPayERPContaAPagar.getImplementacaoDoContexto();
        return null;

    }

    @Override
    public List<ItfFaturaAssinatura> getAssinaturasAtivas(ItfPessoaFisicoJuridico pPessoas) {
        ItfServicoLinkDeEntidadesERP repositorio = galaxPayERPContaAPagar.getRepositorioLinkEntidadesByID();
        String idAssinaturaVinculado = null;
        if (repositorio != null) {
            idAssinaturaVinculado = repositorio.getCodigoApiExterna(pPessoas.getClass(), pPessoas.getId());
        }
        RespostaWebServiceSimples resposta;
        if (idAssinaturaVinculado != null) {
            resposta = FabApiRestIntGalaxPayAssinatura.ASSINATURAS_DO_CLIENTE.getAcao(String.valueOf(idAssinaturaVinculado)).getResposta();
        } else {
            String cnpj = pPessoas.getCpfCnpj();
            ItfPessoaFisicoJuridico devedor = getDevedorByCNPJ(cnpj);
            if (devedor == null) {
                return new ArrayList<>();
            }
            galaxPayERPContaAPagar.getRepositorioLinkEntidadesByID().registrarCodigoLigacaoApi(pPessoas.getClass(), pPessoas.getId(), String.valueOf(devedor.getId()));
            resposta = FabApiRestIntGalaxPayAssinatura.ASSINATURAS_DO_CLIENTE.getAcao(String.valueOf(devedor.getId())).getResposta();
        }

        JSONObject jsonResposta = resposta.getRespostaComoObjetoJson();
        System.out.println(jsonResposta.toJSONString());
        JSONArray array = (JSONArray) jsonResposta.get("Subscriptions");
        List<ItfFaturaAssinatura> assinaturas = new ArrayList<>();

        for (Object jsonAssinatura : array) {

            try {

                ItfFaturaAssinatura assinatura = galaxPayERPContaAPagar.getDTO(jsonAssinatura.toString(), ItfFaturaAssinatura.class);
                if (assinatura.isAtivo()) {
                    assinaturas.add(assinatura);
                }
            } catch (ErroJsonInterpredador ex) {
                SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Falha interpretando json de assinatura", ex);
                throw new UnsupportedOperationException("Falha interpretando json de assinatura");
            }
        }
        return assinaturas;
    }

    @Override
    public List<ItfPessoaFisicoJuridico> getDevedoresRegistrados() {

        int quantidade = 100;

        int indice = 0;

        List<ItfPessoaFisicoJuridico> pessoas = new ArrayList<>();
        while (quantidade != 0) {

            RespostaWebServiceSimples resposta = FabApiRestIntGalaxPayCliente.LISTAR_CLIENTES.getAcao(indice, 100).getResposta();
            Long objetoQuantidade = (long) resposta.getRespostaComoObjetoJson().get("totalQtdFoundInPage");
            quantidade = objetoQuantidade.intValue();
            indice = quantidade + indice;
            JSONArray objetoClientes = (JSONArray) resposta.getRespostaComoObjetoJson().get("Customers");
            for (Object clientObjto : objetoClientes) {
                JSONObject cliente = (JSONObject) clientObjto;
                try {
                    ItfPessoaFisicoJuridico pessoa = galaxPayERPContaAPagar.getDTO(cliente.toString(), ItfPessoaFisicoJuridico.class);
                    pessoas.add(pessoa);
                } catch (ErroJsonInterpredador ex) {
                    Logger.getLogger(CtPagarReceberGalaxPayimpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return pessoas;
    }

    @Override
    public ItfPessoaFisicoJuridico getDevedorByCNPJ(String pCNPJ) {
        if (UtilSBCoreStringValidador.isNuloOuEmbranco(pCNPJ)) {
            return null;
        }
        String cnpj = UtilSBCoreStringFiltros.filtrarApenasNumeros(pCNPJ);
        RespostaWebServiceSimples resp = FabApiRestIntGalaxPayCliente.LISTAR_CLIENTE_BY_DOCUMENTO.getAcao(cnpj).getResposta();
        if (resp.isSucesso()) {
            try {
                long objetoQuantidade = (long) resp.getRespostaComoObjetoJson().get("totalQtdFoundInPage");
                if (objetoQuantidade == 0) {
                    return null;
                }
                JSONArray objetoClientes = (JSONArray) resp.getRespostaComoObjetoJson().get("Customers");
                return galaxPayERPContaAPagar.getDTO(objetoClientes.get(0).toString(), ItfPessoaFisicoJuridico.class);
            } catch (ErroJsonInterpredador ex) {
                SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro tratando Json devedor", ex);
            }
        } else {
            System.out.println("Erro comunicando com a api");
            System.out.println(resp.getRespostaErro());
            System.out.println(resp.getRespostaTexto());
        }
        return null;
    }

    @Override
    public List<ItfPessoaFisicoJuridico> getDevedorByIdExterno(String pCNPJ) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ItfPessoaFisicoJuridico> getDevedorByIdAplicacao(int pId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ItfLocalPostagem getLocalizacaoByDocumento(String pLocalizacao) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
