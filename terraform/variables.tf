variable "aws_region" {
  type        = string
  default     = "us-east-1"
  description = "AWS Account region"
}

variable "eks_cluster_name" {
  type        = string
  default     = "fiap-tech-challenge-eks-cluster"
  description = "EKS Cluster name"
}

variable "ecr_repository_name" {
    type = string
    default = "tech-challenge-payment-app"
    description = "AWS ECR repository name"
}

variable "server_port" {
    type = number
    default = 8000
    description = "Payment App server port"
}

variable "dynamo_db_name" {
  type        = string
  default     = "tc_payment_db"
  description = "Dynamo DB Table name"
}
