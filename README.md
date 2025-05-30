# 📋 Visão Geral do Projeto
Este projeto implementa uma API robusta para cadastro de leads, desenvolvida como solução para o desafio técnico do BTG. O sistema foi arquitetado para ser altamente escalável, seguro e resiliente, atendendo aos requisitos de:

- **Alta disponibilidade**: Arquitetura tolerante a falhas
- **Escalabilidade**: Projetada para lidar com picos de acesso
- **Segurança**: Proteção de dados sensíveis em conformidade com a LGPD
- **Performance**: Tempos de resposta otimizados

# 🚀 Como Executar o Projeto
#### **Pré-requisitos**
- Docker 20.10+
- Docker Compose 1.29+
- Java JDK 17
- Maven 3.8+

#### Passo a Passo para Execução

1. Iniciar o Container MySQL
```bash  
docker run --name btg-mysql \  
-e MYSQL_ROOT_PASSWORD=root \  
-e MYSQL_DATABASE=btg_leads_db \  
-e MYSQL_USER=btg_user \  
-e MYSQL_PASSWORD=btg_password \  
-p 3306:3306 \  
-d mysql:8.0 \  
--default-authentication-plugin=mysql_native_password 
```

2. Compilar a Aplicação
```bash  
mvn clean package  
```

3. Executar com Docker Compose
``` bash  
docker-compose up --build
```

4. Acessar a Aplicação
- A aplicação estará disponível em: http://localhost:8082
    - Página para cadastro de lead: http://localhost:8082/leads/v1/form
    - API Docs (Swagger): http://localhost:8080/leads/v1/swagger-ui.html

🔧 Arquitetura da Solução

| Componente      | Tecnologia                 | Justificativa                        |
| --------------- | -------------------------- | ------------------------------------ |
| Frontend        | Thymeleaf + Bootstrap + JS | Simplicidade e integração com Spring |
| Backend         | Java 22 + Spring Boot 3.1  | Ecossistema robusto e produtivo      |
| Banco de Dados  | MySQL 8                    | ACID e consistência de dados         |
| Cache           | Redis                      | Melhoria de performance              |
| Monitoramento   | Prometheus + Grafana       | Visibilidade do sistema              |
| Containerização | Docker                     | Isolamento e portabilidade           |

# 🛡️ Medidas de Segurança Implementadas

- Rate Limiting
- Autenticação
- Filtros contra SQL Injection
- Proteção básica contra DDoS

# 📝 Endpoints Principais

| Método | Endpoint      | Descrição                         |
| ------ | ------------- | --------------------------------- |
| POST   | /api/v1/leads | Cadastro de novo lead             |
| GET    | /api/v1/leads | Listagem de leads (Admin)         |
| GET    | /api/v1/form  | Formulário de cadastro (Frontend) |
