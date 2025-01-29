resource "kubernetes_namespace" "payment_namespace" {
  metadata {
    name = "payment"
  }
}

resource "kubernetes_secret" "payment_secret" {
  metadata {
    name      = "tech-challenge-payment-secret"
    namespace = kubernetes_namespace.payment_namespace.metadata[0].name
  }

  binary_data = {
    external-api-token = "QmVhcmVyIEFQUF9VU1ItMTQ5NDA0NTE4MjMwMTg4Ni0wOTEyMDgtZDIyZWFhNzAyMGYwYmYyZWU0ZTQxMDQ1ZGYxZDlmNjAtMTk4NjM1NzIzOQ=="
  }

  data = {
    db-table = var.dynamo_db_name
  }

  type = "Opaque"

  depends_on = [kubernetes_namespace.payment_namespace]
}

resource "kubernetes_deployment" "payment_deployment" {
  metadata {
    name      = "tech-challenge-payment-app"
    namespace = kubernetes_namespace.payment_namespace.metadata[0].name
    labels = {
      app = "tech-challenge-payment-app"
    }
  }

  spec {
    replicas = 1

    selector {
      match_labels = {
        app = "tech-challenge-payment-app"
      }
    }

    template {
      metadata {
        labels = {
          app = "tech-challenge-payment-app"
        }
      }

      spec {
        container {
          image             = data.aws_ecr_image.latest_image.image_uri
          name              = "tech-challenge-payment-app"
          image_pull_policy = "Always"

          resources {
            limits = {
              cpu    = "500m"
              memory = "1Gi"
            }
            requests = {
              cpu    = "250m"
              memory = "512Mi"
            }
          }

          liveness_probe {
            http_get {
              path = "/api/actuator/health"
              port = var.server_port
            }
            initial_delay_seconds = 60
            period_seconds        = 30
            timeout_seconds       = 5
            failure_threshold     = 3
          }

          readiness_probe {
            http_get {
              path = "/api/actuator/health"
              port = var.server_port
            }
            initial_delay_seconds = 60
            period_seconds        = 10
            timeout_seconds       = 3
            failure_threshold     = 1
          }

          env {
            name  = "SPRING_PROFILES_ACTIVE"
            value = "default"
          }

          env {
            name  = "EXTERNAL_API_HOST"
            value = "https://api.mercadopago.com"
          }

          env {
            name  = "EXTERNAL_API_CREATE_QR"
            value = "/instore/orders/qr/seller/collectors/1986357239/pos/FIAPSOAT12C/qrs"
          }

          env {
            name  = "EXTERNAL_API_GET_PAYMENT"
            value = "/v1/payments/"
          }

          env {
            name = "SPRING_DATASOURCE_URL"
            value_from {
              secret_key_ref {
                name = "tech-challenge-payment-secret"
                key  = "db-table"
              }
            }
          }

          env {
            name = "EXTERNAL_API_TOKEN"
            value_from {
              secret_key_ref {
                name = "tech-challenge-payment-secret"
                key  = "external-api-token"
              }
            }
          }
        }
      }
    }
  }

  depends_on = [kubernetes_secret.payment_secret]
}

resource "kubernetes_service" "payment_service" {
  metadata {
    name      = "tech-challenge-payment-app-service"
    namespace = kubernetes_namespace.payment_namespace.metadata[0].name
  }

  spec {
    selector = {
      app = "tech-challenge-payment-app"
    }

    port {
      port        = var.server_port
      target_port = var.server_port
    }

    cluster_ip = "None"
  }
}

resource "kubernetes_ingress_v1" "payment_ingress" {
  metadata {
    name      = "tech-challenge-payment-ingress"
    namespace = kubernetes_namespace.payment_namespace.metadata[0].name

    annotations = {
      "nginx.ingress.kubernetes.io/x-forwarded-port" = "true"
      "nginx.ingress.kubernetes.io/x-forwarded-host" = "true"
    }
  }

  spec {
    ingress_class_name = "nginx"

    rule {
      http {
        path {
          path      = "/payment"
          path_type = "Prefix"

          backend {
            service {
              name = "tech-challenge-payment-app-service"
              port {
                number = var.server_port
              }
            }
          }
        }
      }
    }
  }

  depends_on = [kubernetes_service.payment_service]

}

resource "kubernetes_horizontal_pod_autoscaler_v2" "payment_hpa" {
  metadata {
    name      = "tech-challenge-payment-hpa"
    namespace = kubernetes_namespace.payment_namespace.metadata[0].name
  }

  spec {
    scale_target_ref {
      api_version = "apps/v1"
      kind        = "Deployment"
      name        = "tech-challenge-payment-app"
    }

    min_replicas = 1
    max_replicas = 5

    metric {
      type = "Resource"

      resource {
        name = "cpu"
        target {
          type                = "Utilization"
          average_utilization = 75
        }
      }
    }
  }

  depends_on = [kubernetes_service.payment_service]

}
