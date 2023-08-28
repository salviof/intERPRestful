package org.coletivoJava.fw.projetos.integracao.api.cucumber.fluxooauthinteracaosistema;
public enum EtapasFluxoOauthInteracaoSistema {
	_DADO_UM_USUARIO_LOGADO_NO_SITEMA_COM_CHAVE_DE_ACESSO_CONFIGURADAS_ENTRE_CLIENTE_E_SERVIDOR, _QUANDO_O_SISTEMA_SOLICITA_UM_CODIGO_DE_CONCESSAO_NO_ESCOPO_DO_SISTEMA, _ENTAO_O_SERVIDOR_VALIDA_AS_CHAVES_DE_ACESSO_ASSINCRONAS_E_RECONHECE_O_NOME_DE_USUARIO, _E_O_PROPRIO_SERVIDOR_ACESSA_A_URL_ENVIANDO_O_CODIGO_DE_CONCESSAO_QUE_POR_SUA_VEZ_SOLICITA_O_TOKEN, _QUANDO_O_SISTEMA_SOLICITA_A_LISTAGEM_DE_USUARIO_COM_UM_TOKEN_VALIDO, _ENTAO_O_SERVIDOR_ENTREGA_UMA_LISTA_EM_JSON_COM_OS_DADOS_DE_USUARIO;

	public static final String DADO_UM_USUARIO_LOGADO_NO_SITEMA_COM_CHAVE_DE_ACESSO_CONFIGURADAS_ENTRE_CLIENTE_E_SERVIDOR = "um usuario logado no sitema com chave de acesso configuradas entre cliente e servidor";
	public static final String QUANDO_O_SISTEMA_SOLICITA_UM_CODIGO_DE_CONCESSAO_NO_ESCOPO_DO_SISTEMA = "o sistema solicita um código de concessão no escopo do sistema";
	public static final String ENTAO_O_SERVIDOR_VALIDA_AS_CHAVES_DE_ACESSO_ASSINCRONAS_E_RECONHECE_O_NOME_DE_USUARIO = "o servidor valida as chaves de acesso assincronas e reconhece o nome de usuário";
	public static final String E_O_PROPRIO_SERVIDOR_ACESSA_A_URL_ENVIANDO_O_CODIGO_DE_CONCESSAO_QUE_POR_SUA_VEZ_SOLICITA_O_TOKEN = "o proprio servidor acessa a url enviando o código de concessao que por sua vez solicita o token";
	public static final String QUANDO_O_SISTEMA_SOLICITA_A_LISTAGEM_DE_USUARIO_COM_UM_TOKEN_VALIDO = "o sistema solicita a listagem de usuario com um token valido";
	public static final String ENTAO_O_SERVIDOR_ENTREGA_UMA_LISTA_EM_JSON_COM_OS_DADOS_DE_USUARIO = "o servidor entrega uma lista em Json com os dados de usuário";
}