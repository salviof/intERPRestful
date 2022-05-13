package org.coletivoJava.fw.projetos.integracao.api.model.linkentidadessistemaserp;

import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.LinkEntidadesSistemasERP;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoReferenciaEntidade;

@InfoReferenciaEntidade(tipoObjeto = LinkEntidadesSistemasERP.class)
public enum CPLinkEntidadesSistemasERP {
	_ID, _CODIGOIDENTIFICADOR, _SISTEMAREMOTO, _ENTIDADE, _CODIGOSISTEMAREMOTO, _CODIGOINTERNO;

	public static final String id = "id";
	public static final String codigoidentificador = "codigoIdentificador";
	public static final String sistemaremoto = "sistemaRemoto";
	public static final String entidade = "entidade";
	public static final String codigosistemaremoto = "codigoSistemaRemoto";
	public static final String codigointerno = "codigoInterno";
}