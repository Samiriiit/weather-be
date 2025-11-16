

// pipeline {
//     agent any

//     stages {
//         stage('Checkout Code') {
//             steps {
//                 git branch: 'main', url: 'https://github.com/Samiriiit/weather-be.git'
//             }
//         }
//          stage('Cleanup Old Pods') {
//             steps {
//                 echo "🧹 Cleaning up old BE pods inside cluster"
//                 bat 'kubectl delete pod -l app=weather-be --ignore-not-found'
//             }
//         }

//         stage('Build Image') {
//             steps {
//                 // Build the BE jar first
//                 bat 'mvn clean package -DskipTests'

//                 // Build Docker/Podman image inside Minikube
//                 bat 'minikube image build -t weather-be:latest .'
//             }
//         }

//         stage('Deploy') {
//             steps {
//                 // Deploy Redis if needed
//                 bat 'kubectl apply -f redis-deployment.yaml'

//                 // Deploy BE using local image
//                 bat 'kubectl apply -f weather-be-deployment.yaml'
//             }
//         }

//         stage('Verify') {
//             steps {
//                 // Wait for pod to come up
//                 sleep(120)

//                 // Verify BE pod is running
//                 bat 'kubectl get pods -l app=weather-be | findstr Running'
//                 echo "✅ Backend Pod is Running"
//             }
//         }
//     }

//     post {
//         always {
//             echo "=== FINAL STATUS ==="
//             bat 'kubectl get pods -l app=weather-be'
//             bat 'kubectl get svc -l app=weather-be'
//         }
//     }
// }

pipeline {
    agent any

    environment {
        AWS_REGION = 'us-east-1'
        ECR = '490196132533.dkr.ecr.us-east-1.amazonaws.com/weather-be'
        TAG = 'latest'
        CLUSTER_NAME = 'weather-app'
        CONTAINER_NAME = 'weather-be'
        ENVIRONMENT = "prod"
    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'eks', url: 'https://github.com/Samiriiit/weather-be.git'
            }
        }

        stage('Build & Package') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Docker Build & Push') {
            steps {
                sh """
                    aws ecr get-login-password --region $AWS_REGION \
                    | docker login --username AWS --password-stdin $ECR

                    docker build --no-cache --build-arg APP_ENV=${ENVIRONMENT} -t $ECR:$TAG .
                    docker push $ECR:${TAG}-${ENVIRONMENT}
                """
            }
        }
        stage('Vulnerability Scan') {
          steps {
            sh """
              echo "🔍 Running Trivy scan..."
        
              mkdir -p reports
        
              trivy image \
                --exit-code 0 \
                --severity CRITICAL \
                --ignore-unfixed \
                --format table \
                --output reports/trivy-report.txt \
                ${ECR}:${TAG}
        
              echo "⚠️ Scan completed"
            """
          }
          // post {
          //   always {
          //     archiveArtifacts artifacts: 'reports/trivy-report.txt', allowEmptyArchive: true
          //   }
          // }
    }


        stage('Deploy to EKS') {
            steps {
                sh 'kubectl apply -f weather-be-deployment.yaml'
            }
        }
         stage('Grafana deployment') {
            steps {
                sh 'kubectl apply -f grafana-deployment.yaml'
            }
        }

        stage('Verify Deployment') {
            steps {
                sh '''
                    echo "🟢 Waiting for pods to be ready..."
                    sleep 60
                    kubectl get pods -l app=weather-be
                '''
            }
        }
    }

    post {
        success {
            echo "✅ Backend Deployment successful"
        }
        failure {
            echo "❌ Deployment failed!"
        }
        always { cleanWs() }
    }
}
