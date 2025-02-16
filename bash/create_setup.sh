#!/bin/bash

# Define o endpoint base
ENDPOINT="http://localhost:4566"

# Define as filas a serem criadas
QUEUES=(
  "order-status-update-queue"
  "payment-order-create-queue"
)

# Loop para criar as filas
for QUEUE_NAME in "${QUEUES[@]}"; do
  echo "Criando a fila: $QUEUE_NAME"
  aws --endpoint="$ENDPOINT" sqs create-queue --queue-name "$QUEUE_NAME"
done

echo "Todas as filas foram criadas com sucesso!"

# Criar a tabela no DynamoDB
echo "Criando a tabela payment_entity no DynamoDB..."
aws dynamodb create-table \
  --endpoint-url "$ENDPOINT" \
  --table-name payment_entity \
  --attribute-definitions AttributeName=id,AttributeType=S \
  --key-schema AttributeName=id,KeyType=HASH \
  --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5

echo "Tabela payment_entity criada com sucesso!"