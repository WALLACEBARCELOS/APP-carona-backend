# Aplicativo de Caronas Universitárias - Backend

Este repositório contém o desenvolvimento do backend para um aplicativo de caronas universitárias, projetado para conectar estudantes que desejam compartilhar ou encontrar caronas para seus trajetos diários. O backend gerencia todas as operações de persistência de dados, autenticação de usuários e comunicação com o frontend através de APIs REST.

## Funcionalidades Principais

- **Gerenciamento de Usuários**: Criação, atualização e autenticação de usuários, permitindo que eles se registrem como motoristas ou passageiros.
- **Gerenciamento de Viagens**: Motoristas podem criar e gerenciar viagens, especificando detalhes como pontos de partida, destino e disponibilidade de assentos.
- **Sistema de Reservas**: Passageiros podem reservar assentos em viagens disponíveis, e os motoristas podem confirmar ou cancelar reservas.
- **Avaliações e Feedback**: Motoristas e passageiros podem avaliar uns aos outros após a conclusão da viagem, ajudando a construir uma comunidade de confiança.
- **Gerenciamento de Veículos**: Motoristas podem cadastrar e gerenciar os veículos utilizados para as viagens.

## Tecnologias Utilizadas

- **Java**: Linguagem de programação principal utilizada para desenvolver a lógica de negócios.
- **Spring Boot**: Framework utilizado para criar o backend, gerenciando dependências e simplificando a configuração.
- **JPA (Java Persistence API)**: Utilizado para mapeamento objeto-relacional (ORM) e gerenciamento da persistência dos dados.
- **Banco de Dados**: PostgreSQL, utilizado para armazenar todas as informações do sistema.
- **APIs REST**: Desenvolvidas para permitir a comunicação entre o backend e o frontend.
- **Autenticação**: Implementação de autenticação segura para proteger os dados dos usuários.

## Como Rodar o Projeto

1. **Clone o Repositório**:
    ```bash
    git clone https://github.com/WALLACEBARCELOS/APP-carona-backend.git
    ```
2. **Configurar o Banco de Dados**:
    - Certifique-se de que o PostgreSQL esteja instalado e configurado.
    - Crie o banco de dados e configure as credenciais no arquivo `application.properties` ou `application.yml`.
    - Execute as migrações do banco de dados (se aplicável) para criar as tabelas e inserir os dados iniciais.

3. **Configurar o Arquivo de Propriedades**:
    - Configure o arquivo `application.properties` ou `application.yml` com as credenciais e configurações do banco de dados.

4. **Compilar e Executar**:
    ```bash
    ./mvnw spring-boot:run
    ```

5. **Acessar o Aplicativo**:
    - O backend estará disponível em `http://localhost:8080`.

## Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues para relatar bugs ou sugerir novas funcionalidades. Pull requests também são encorajados, desde que estejam alinhados com os objetivos do projeto.
