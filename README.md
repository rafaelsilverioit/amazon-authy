# amazon-authy

API para gerenciamento dos usuários da livraria da Amazon.

## Desenho da API

Abaixo esta o desenho da API baseado na documentação gerada pelo Swagger2.

![Desenho da API](amazon-authy-design-new.png)

## 11 regras utilizadas para desenhar a API.

### APIs organizadas ao longo de recursos
As APIs foram organizadas com base nos recursos: books, basket e comments.

### APIs padronizadas
As APIs foram padronizadas, utilizando substantivos no plural para URIs que fazem referência à coleções e singular para as demais. Exemplo:
 - /v1/public/users
 
### APIs projetadas para mapear entidades de negócio e suas operações
 - /v1/public/users/auth
 
### APIs simples
Os recursos que envolvem coleções foram limitados ao padrão de coleção/item/coleção.
Não foi necessária a implementação.
 
### Atualização em lote para operações complexas
Não foi necessária a implementação.

### Padrão ISO 8601 para os atributos de data/hora
Os recursos que possuem atributos de data/hora utilizam o formato yyyy-MM-ddTHH:mm:ss.

### APIs documentadas
As APIs foram devidamente documentadas utilizando o Swagger2. Para consultar a documentação acesse http://[IP]:9001/v1/public/swagger-ui.html

### Utilizar o protocolo HTTPS/SSL
Em produção, o servidor de aplicações deverá ser configurado com um certificado válido para permitir acesso às APIs utilizando o protocolo HTTPS.

### APIs versionadas
A URI básica da API é baseada no versionamento da mesma: /v1/public/* 

### Utilizar paginação para coleções com grandes volumes
Não foi necessário a implementação.

### Utilização os códigos HTTP
 - 200 (OK) Quando os recursos são encontrados ou autorizados com sucesso.
   - POST /v1/public/users/auth
 - 201 (Created) A solicitação foi bem sucedida e um novo recurso foi criado.
   - POST /v1/public/users
 - 404 (Not Found) O usuário requisitado não pôde ser encontrado.
   - POST /v1/public/users/auth
 - 405 (Method Not Allowed) O método especificado na requisição não é permitido.
   - GET /v1/public/users
