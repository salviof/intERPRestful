package org.coletivoJava.fw.projetos.integracao.api.cucumber.fluxooauthinteracaousuario;
public enum EtapasFluxoOauthInteracaoUsuario {
	_DADO_UM_USUARIO_LOGADO_NO_SITEMA_COM_CHAVE_DE_ACESSO_CONFIGURADAS_ENTRE_CLIENTE_E_SERVIDOR, _QUANDO_O_USUARIO_SOLICITA_UM_CODIGO_DE_CONCESSAO_DO_ESCOPO_DE_USUARIO, _ENTAO_O_SERVIDOR_VALIDA_AS_CHAVES_DE_ACESSO_ASSINCRONAS_E_RECONHECE_O_NOME_DE_USUARIO, _E_RESPONDE_COM_UMA_URL_CONTENDO_CODIGO_DE_CONCESSAO_NO_ENDERECO_SOLICITADO_PELO_USUARIO, _QUANDO_O_CLIENTE_RECEBE_O_CODIGO_DE_CONCESSAO_NO_SEU_ENDERECO, _ENTAO_O_APLICATIVO_DO_CLIENTE_SOLICITA_O_TOKEN_USANDO_CODIGO_DE_CONCESSAO_VIA_POST, _E_ARMAZENA_O_TOKEN_DE_ACESSO_DO_CLIENTE, _QUANDO_O_CLIENTE_POSSUIDOR_DE_TOKEN_TENTA_ACESSAR_UM_RECURSO_DO_SERVIDOR, _ENTAO_O_SERVIDOR_RECONHECE_O_TOKEN, _E_EXECUTAR_UMA_ACAO_CONTROLLER_COM_O_TOKEN;

	public static final String DADO_UM_USUARIO_LOGADO_NO_SITEMA_COM_CHAVE_DE_ACESSO_CONFIGURADAS_ENTRE_CLIENTE_E_SERVIDOR = "um usuario logado no sitema com chave de acesso configuradas entre cliente e servidor";
	public static final String QUANDO_O_USUARIO_SOLICITA_UM_CODIGO_DE_CONCESSAO_DO_ESCOPO_DE_USUARIO = "o usuário solicita um código de concessão do escopo de usuario";
	public static final String ENTAO_O_SERVIDOR_VALIDA_AS_CHAVES_DE_ACESSO_ASSINCRONAS_E_RECONHECE_O_NOME_DE_USUARIO = "o servidor valida as chaves de acesso assincronas e reconhece o nome de usuário";
	public static final String E_RESPONDE_COM_UMA_URL_CONTENDO_CODIGO_DE_CONCESSAO_NO_ENDERECO_SOLICITADO_PELO_USUARIO = "responde com uma url contendo código de concessão no endereço solicitado pelo usuário";
	public static final String QUANDO_O_CLIENTE_RECEBE_O_CODIGO_DE_CONCESSAO_NO_SEU_ENDERECO = "o cliente recebe o codigo de concessao no seu endereço";
	public static final String ENTAO_O_APLICATIVO_DO_CLIENTE_SOLICITA_O_TOKEN_USANDO_CODIGO_DE_CONCESSAO_VIA_POST = "o aplicativo do cliente solicita o token usando código de concessão via post";
	public static final String E_ARMAZENA_O_TOKEN_DE_ACESSO_DO_CLIENTE = "armazena o token de acesso do cliente";
	public static final String QUANDO_O_CLIENTE_POSSUIDOR_DE_TOKEN_TENTA_ACESSAR_UM_RECURSO_DO_SERVIDOR = "o cliente possuidor de token tenta acessar um recurso do servidor";
	public static final String ENTAO_O_SERVIDOR_RECONHECE_O_TOKEN = "o servidor reconhece o token";
	public static final String E_EXECUTAR_UMA_ACAO_CONTROLLER_COM_O_TOKEN = "executar uma ação controller com o token";
}