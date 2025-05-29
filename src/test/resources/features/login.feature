# language: pt
Funcionalidade: Login no sistema
  Como um usuário do sistema
  Eu quero realizar o login
  Para acessar as funcionalidades protegidas

  Cenário: Login com sucesso
    Dado que estou na página de login
    Quando preencho o usuário "student"
    E preencho a senha "Password123"
    E clico no botão submit
    Então devo ser redirecionado para a página de sucesso
    E devo ver a mensagem de login realizado com sucesso
    E devo ver o botão de logout

  Cenário: Login com usuário inválido
    Dado que estou na página de login
    Quando preencho o usuário "incorrectUser"
    E preencho a senha "Password123"
    E clico no botão submit
    Então devo ver a mensagem de erro "Your username is invalid!"

  Cenário: Login com senha inválida
    Dado que estou na página de login
    Quando preencho o usuário "student"
    E preencho a senha "incorrectPassword"
    E clico no botão submit
    Então devo ver a mensagem de erro "Your password is invalid!"
