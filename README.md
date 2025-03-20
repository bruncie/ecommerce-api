# ecommerce-api

mvn clean install para compilar

- A aplicação sobe com dois usuarios iniciais para realizar as operações no sistema, um deles com perfil ADMIN e outro com perfil USER.

- Para gerar novos usuarios é necessário utilizar o usuario inicial do tipo ADMIN

- Os dados desses dois usuarios estão na classe DataInitializer, no pacote config

- A exceção do endpoint auth e h2-console, todos os demais endpoints precisam informar um token de autenticação

- Há uma colection para testes no arquivo ecommerce.postman_collection.json