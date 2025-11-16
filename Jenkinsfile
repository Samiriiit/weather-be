


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

                    docker build --no-cache --build-arg APP_ENV=${ENVIRONMENT} -t $ECR:${TAG}-${ENVIRONMENT} .
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
