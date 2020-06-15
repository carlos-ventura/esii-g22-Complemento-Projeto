Requisito 1 do Complemento ao Projeto de ESII
By: André Henriques (82203)




1. Objetivos alcançados

- Os tipos de membros pedidos foram designados, sendo que o tipo "Visitor" se torna inútil visto apenas fazer alusão aos visitantes do website que não estão registados (logo não pertencem a qualquer tipo de membro).
- O menu pedido foi totalmente desenvolvido, bem como os seus links e associações.
- As permissões e restrições de páginas estão inteiramente asseguradas consoante o que foi pedido.
- A página inicial apresenta todas as caraterísticas pedidas.
- As páginas respetivas aos Requisitos 3, 4, 5 e 6 estão a funcionar por completo, tendo esses mesmos requisitos sido devidamente implementados e com sucesso.
- A "Wiki" foi desenvolvida com todos os tópicos de informação pedidos, tendo sido já adicionada informação ("artigos") a todos eles. É também possível aos utilizadores do website comentarem os "artigos", contribuindo para possíveis melhorias dos mesmos, bem como partilharem-nos nas redes sociais.
- A secção de perguntas e respostas está devidamente imposta no website, já com informação útil inserida.
- A página "Join Us" permite direcionar um utilizador para uma página de registo ou de login. Tanto o registo como o login estão a ocorrer sem problemas, tendo o registo todos os campos pedidos além de estar de acordo com os critérios exigidos a nível de privacidade, sendo obrigatório o utilizador confirmar que leu tanto a "Política de privacidade" como os "Termos e condições" do website (disponíveis na página de registo).
- A página de informações do grupo (22) e da instituição de ensino (ISCTE) está totalmente desenvolvida, tendo toda a informação pedida.
- O link para o repositório de ficheiros está a funcionar por completo, abrindo uma página que permite redirecionar (via botão) para dois repositórios distintos: um repositório geral e um repositório administrativo. O repositório global está disponível para todos os utilizadores abrirem e descarregarem todos os ficheiros disponíveis, enquanto que o repositório administrativo apenas se encontra aberto para o administrador (sendo bloqueado o acesso a qualquer outro tipo de utilizador) onde este pode eliminar ficheiros existentes e/ou carregar novos ficheiros.


2. Problemas encontrados

- A página que permite aos utilizadores entrarem em contacto com o administrador está a funcionar sem qualquer impedimento. O problema surge na parte do envio do email, pois este não é enviado para o administrador.
   - Após procurar vários plugins que permitissem enviar o email, não foi possível encontrar nenhum que funcionasse
   - Após alguma pesquisa percebeu-se que se devia ao facto de o email estar a ser enviado por PHP e não por SMTP, mas não se conseguiu alterar esta funcionalidade
- A página relativa às estatísticas do website é a que apresenta mais deficiências
   - Contém um botão já preparado para receber a informação do Requisito 2, mas este não foi entregue de forma a que se pudesse redirecionar o browser para uma página contendo os resultados da monitorização
   - Não se encontrou qualquer plugin que satisfizesse os pedidos do enunciado
      - Encontraram-se pouco plugins que permitissem inserção em frontend (no browser em si), e quando se encontrava não tinham nem metade das funcionalidades mínimas pedidas no enunciado
      - Encontraram-se alguns plugins que satisfaziam os requisitos funcionais do enunciado mas que não permitiam a inserção em frontend (no browser em si)
         - Decidiu-se colocar o melhor plugin encontrado, com praticamente todos os requisitos funcionais pedidos, não conseguindo ser incorporado no browser (em frontend)


3. Num panorama geral

- A grande maioria dos objetivos do Requisito 1 foram alcançados com sucesso, como acima assinalado.
- É considerado que o website apresenta, a nível de requisitos, um grande problema a nível da página "Web Site Analytics" e um problema de menor grau no caso da página "Contact Us" (acima referidos).