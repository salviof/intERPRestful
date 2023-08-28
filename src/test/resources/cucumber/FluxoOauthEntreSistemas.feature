# language: pt
@FluxoOauthInteracaoSistema
Funcionalidade: Esabelecer comunicação entre sistemas, obtendo uma chave gerada via OAUTH2
  SEM intermédio do usuário para solicitação de token
  Para solicitação do código de concessão foi utilizado o padrão RCA 2.2 https://datatracker.ietf.org/doc/html/rfc8017
  O Fluxo  do protocolo foi criadas conforme documentação vigente em: https://datatracker.ietf.org/doc/html/rfc6749
  Seguindo as seguintes etapas:
    +--------+                               +---------------+
   1 |        |--(A)Solicit. de autorização ->|   Resource    |
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
  6  |        |<-(F)--- Recurso progido    ---|               |
     +--------+                               +---------------+

Contexto: CenárioServidorFicticio

  Cenario: Sistema cliente lista os usuários em servidor apos se autenticar via oauth
    Dado um usuario logado no sitema com chave de acesso configuradas entre cliente e servidor
    Quando o sistema solicita um código de concessão no escopo do sistema
    Então o servidor valida as chaves de acesso assincronas e reconhece o nome de usuário
    E o proprio servidor acessa a url enviando o código de concessao que por sua vez solicita o token
    Quando o sistema solicita a listagem de usuario com um token valido
    Entao o servidor entrega uma lista em Json com os dados de usuário