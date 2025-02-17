<div align="center">
  
# Tech Challenge - Payment API

![GitHub Release Date](https://img.shields.io/badge/Release%20Date-Fevereiro%202025-yellowgreen)
![](https://img.shields.io/badge/Status-Em%20Desenvolvimento-yellowgreen)
<br>
![](https://img.shields.io/badge/Version-%20v1.0.0-brightgreen)
</div>

## 💻 Descrição

O **Tech Challenge - Payment API** é um microserviço desenvolvido em **Java** com **Spring Boot**, seguindo os princípios da **Clean Architecture**. Ele é responsável por gerenciar os endpoints de **comunicação com o gateway de pagamento externo** do restaurante e **consulta do QR do pedido** para o pagamento.

## 🛠 Tecnologias Utilizadas

![Java](https://img.shields.io/badge/java_21-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring_3-%236DB33F.svg?style=for-the-badge&logo=springboot&logoColor=white)
![JUnit5](https://img.shields.io/badge/JUnit5-25A162.svg?style=for-the-badge&logo=JUnit5&logoColor=white)
![Mockito](https://img.shields.io/badge/Mockito-53AC56.svg?style=for-the-badge&logo=Minetest&logoColor=white)
![Maven](https://img.shields.io/badge/Apache%20Maven-C71A36.svg?style=for-the-badge&logo=Apache-Maven&logoColor=white)
![DynamoDB](https://img.shields.io/badge/Amazon%20DynamoDB-4053D6.svg?style=for-the-badge&logo=Amazon-DynamoDB&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=white)
![Sonnar](https://img.shields.io/badge/Sonar-FD3456.svg?style=for-the-badge&logo=Sonar&logoColor=white)
![Mercado Pago](https://img.shields.io/badge/Mercado%20Pago-00B1EA.svg?style=for-the-badge&logo=Mercado-Pago&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/GitHub%20Actions-2088FF.svg?style=for-the-badge&logo=GitHub-Actions&logoColor=white)
![Terraform](https://img.shields.io/badge/Terraform-7B42BC?style=for-the-badge&logo=terraform&logoColor=white)

## 💫 Arquitetura

O projeto adota a **Clean Architecture**, garantindo flexibilidade, testabilidade e manutenção escalável.

## ⚙️ Configuração

### Pré-requisitos

1. É necessário executar a pipeline para criar o VPC no repositório: https://github.com/fiap-soat-12/tech-challenge-vpc
2. É necessário executar a pipeline para criar o SQS no repositório: https://github.com/fiap-soat-12/tech-challenge-queue

### Desenvolvimento

- **[Java 21](https://docs.oracle.com/en/java/javase/21/)**: Documentação oficial do Java 21.
- **[Maven 3.6.3+](https://maven.apache.org/)**: Site oficial do Maven.
- **[Docker](https://www.docker.com/)**: Site oficial do Docker.
- **[Docker Compose](https://docs.docker.com/compose/)**: Documentação oficial do Docker Compose.
- **[DynamoDB](https://aws.amazon.com/pt/dynamodb/)**: Site oficial da DynamoDB.
- **[Sonarqube](https://www.sonarsource.com/products/sonarqube/)**: Site oficial do Sonarqube.
- **[Kubernetes](https://kubernetes.io/pt-br/docs/home/)**: Documentação oficial do Kubernetes.
- **[Terraform](https://www.terraform.io/)**: Site oficial do Terraform.
- **[AWS](https://aws.amazon.com/pt/)**: Site oficial da AWS.

### 🚀 Execução

### Subindo a aplicação com Docker Compose

1. Executar o comando:

```sh
docker compose up
```

3. O serviço estará disponível em `http://localhost:8100/payment`

### Subindo a Payment API
  Caso deseje subir a Payment API, basta seguir os seguintes passos:
  
  1. Certificar que o Terraform esteja instalado executando o comando `terraform --version`;
  ![terraform-version](./assets/terraform-version.png)

  2. Certificar que o `aws cli` está instalado e configurado com as credenciais da sua conta AWS;
  ![aws-cli-version](./assets/aws-cli-version.png)

  3. Acessar a pasta `terraform` que contém os arquivos que irão criar a Payment API;
  4. Inicializar o Terraform no projeto `terraform init`;
  5. Verificar que o script do Terraform é valido rodando o comando `terraform validate`;
  6. Executar o comando `terraform plan` para executar o planejamento da execução/implementação;
  7. Executar o comando `terraform apply` para criar a Payment API;
  8. Após a execução do Terraform finalizar, verificar se a Payment API subiu corretamente na AWS;
  ![aws-resource](./assets/aws-resource.png)

## 📄 Documentação da API

A documentação da API pode ser acessada através do Swagger:

```bash
http://localhost:8100/payment/swagger-ui/index.html
```

## 🔃 Fluxo de Execução das APIs

1. Busca do **QR** do pedido (GET) `/payment/v1/qrs/{orderId}`
2. **WebHook** de pagamento (POST) `/payment/v1/webhook-payment`

## ✅ Cobertura de Testes

### Testes Unitarios
![unit-test](./assets/unit_test_payment.png)

### Scan do Sonar
![Sonar_1](./assets/sonar_payment_1.png)
![Sonar_1](./assets/sonar_payment_2.png)
![Sonar_1](./assets/sonar_payment_3.png)

### JaCoCo
Cobertura do Projeto usando o JaCoCo: 
[Link](https://fiap-soat-12.github.io/tech-challenge-payment-api/)


## 📚 Event Storming

![Event Storming](./assets/event_storming.png)

Acesso ao MIRO com o Event Storming:
[Event Storming](https://miro.com/app/board/uXjVK1ekBDM=/)
