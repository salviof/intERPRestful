# language: pt
@FluxoOauthInteracaoUsuario
Funcionalidade: Esabelecer comunicação entre sistemas, obtendo uma chave gerada via OAUTH2,
  COM intermédio do usuário para solicitação de token
  Para solicitação do código de concessão foi utilizado o padrão RCA 2.2 https://datatracker.ietf.org/doc/html/rfc8017
  O Fluxo  do protocolo foi criadas conforme documentação vigente em: https://datatracker.ietf.org/doc/html/rfc6749
  Seguindo as seguintes etapas:
    +--------+                                +---------------+
   1 |        |-(A)Solict. cod. de concessão->|   Resource    |
     |        |                               |     Owner     |
   2 |        |<-(B)-- Codigo de concessão ---|               |
     |        |                               +---------------+
     |        |
     |        |                               +---------------+
   3 |        |--(C)-- Código de concessao -->| Authorization |
     | Client |                               |     Server    |
   4 |        |<-(D)-----Token de acesso -----|               |
     |        |                               +---------------+
     |        |
     |        |                               +---------------+
  5  |        |--(E)----- Token de cacesso -->|    Resource   |
     |        |                               |     Server    |
  6  |        |<-(F)--- Recurso protegido  ---|               |
     +--------+                               +---------------+

  Contexto: CenárioServidorFicticio

  Cenario: Sistema cliente lista os usuários em servidor apos se autenticar via oauth
    Dado um usuario logado no sitema com chave de acesso configuradas entre cliente e servidor
    Quando o usuário solicita um código de concessão do escopo de usuario
    Então o servidor valida as chaves de acesso assincronas e reconhece o nome de usuário
    E responde com uma url contendo código de concessão no endereço solicitado pelo usuário
    Quando o cliente recebe o codigo de concessao no seu endereço
    Entao o aplicativo do cliente solicita o token usando código de concessão via post
    E armazena o token de acesso do cliente
    Quando o cliente possuidor de token tenta acessar um recurso do servidor
    Entao o servidor reconhece o token
    E executar uma ação controller com o token


