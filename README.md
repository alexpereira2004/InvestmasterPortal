# InvestMaster

Este é o backend da aplicação InvestMaster, construído com **Spring Boot** para fornecer uma API robusta e eficiente. Ele é responsável pela lógica de negócios, persistência de dados e integração com serviços externos, servindo como o coração do sistema InvestMaster.

---

## Principais Tecnologias e Funcionalidades

* **Spring Boot (v2.4.0)**: O framework principal para o desenvolvimento rápido de APIs REST, garantindo um ambiente de desenvolvimento ágil e escalável.
* **Persistência de Dados (Spring Data JPA)**: Gerenciamento da camada de dados com JPA (Java Persistence API), facilitando a interação com o banco de dados.
* **Banco de Dados MySQL**: Utiliza MySQL para armazenamento de dados, com o `mysql-connector-java` para conectividade.
* **Validação de Dados (`jakarta.validation` & Spring Boot Starter Validation)**: Implementação de regras de validação para garantir a integridade e consistência dos dados nas requisições da API.
* **Documentação de API (Springfox/Swagger)**: Geração automática de documentação interativa para todos os endpoints da API, facilitando o consumo e a compreensão por parte de outros desenvolvedores.
* **Migrações de Banco de Dados (Flyway Core)**: Gerenciamento de versões e migrações do esquema do banco de dados de forma controlada e eficiente.
* **Integração com Google APIs (Sheets)**: Capacidade de interagir com serviços Google, incluindo Google Sheets, para leitura ou manipulação de dados externos.
* **Processamento de Arquivos CSV (OpenCSV)**: Funcionalidade para leitura e escrita de dados em formato CSV.
* **Utilitários e Otimizações**:
    * **Lombok**: Reduz a verbosidade do código Java, gerando automaticamente getters, setters, construtores e outros.
    * **Google Guava & Apache Commons Lang 3**: Bibliotecas que fornecem um conjunto rico de utilitários para operações comuns, melhorando a produtividade.
* **Ferramentas de Desenvolvimento**: Inclui `spring-boot-devtools` para agilizar o ciclo de desenvolvimento com recargas automáticas.
* **Contêineres (Docker)**: Configurado para fácil empacotamento em imagens Docker através do plugin Maven do Spring Boot.

---

## Como Iniciar o Projeto

Para configurar e rodar o projeto localmente, siga estes passos:

1.  **Clone o repositório:**
    ```bash
    git clone [URL_DO_SEU_REPOSITORIO_BACKEND]
    cd portal # Ou o nome da pasta raiz do seu projeto backend
    ```
2.  **Configurar o Banco de Dados:**
    * Certifique-se de ter uma instância do MySQL disponível.
    * Configure as credenciais do banco de dados no arquivo `application.properties` ou `application.yml` (localizado em `src/main/resources`).
3.  **Compilar e Rodar (usando Maven):**
    ```bash
    # Compila o projeto
    mvn clean install

    # Executa a aplicação
    mvn spring-boot:run
    ```
    A API estará acessível em `http://localhost:12001` (porta padrão configurada no seu Docker Run, pode variar conforme sua configuração interna).

---

## Docker

A aplicação é configurada para ser facilmente empacotada em uma imagem Docker:

* **Criar a imagem Docker:**
    ```bash
    mvn spring-boot:build-image
    ```
  Isso criará uma imagem com o nome `investmaster/portal` (conforme configurado no `pom.xml`).

* **Iniciar o contêiner Docker:**
    ```bash
    docker run -p 13001:12001 investmaster/portal
    ```
  Isso mapeia a porta `13001` do seu host para a porta `12001` dentro do contêiner.

---

## Swagger (Documentação da API)

Após iniciar a aplicação (seja via `mvn spring-boot:run` ou Docker), você pode acessar a documentação interativa da API via Swagger UI:

* Acesse: `http://localhost:12001/swagger-ui/index.html#/`

---