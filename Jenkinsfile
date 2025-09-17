// pipeline {
//     agent any
//
//     environment {
//         BE_IMAGE_NAME = "weather-be"
//         BE_IMAGE_TAG = "latest"
//         BE_HOST_PORT = "8081"  // Change if 8080 busy
//         ZIPKIN_IMAGE_NAME = "zipkin"
//         ZIPKIN_HOST_PORT = "9411"
//          GRAFANA_IMAGE_NAME = "grafana"
//          GRAFANA_HOST_PORT = "3002"
//     }
//
//     stages {
//
//         stage('Checkout Backend') {
//             steps {
//                 git branch: 'main', url: 'https://github.com/Samiriiit/weather-be.git'
//             }
//         }
//
//         stage('Build Backend') {
//             steps {
//                 bat 'mvn clean package'
//             }
//         }
//
//       stage('Run Tests & Generate Jacoco') {
//             steps {
//                 bat 'mvn test jacoco:report'
//             }
//         }
//           stage('SonarQube Analysis') {
//     steps {
//         script {
//             def mvn = tool 'Maven3'
//             withSonarQubeEnv('weather') {
//                 bat "\"${mvn}\\bin\\mvn\" clean verify sonar:sonar -Dsonar.projectKey=weather-app -Dsonar.projectName=weather-app"
//
//             }
//         }
//     }
// }
//
//
//         stage('Build Podman Image') {
//             steps {
//                 bat """
//                 podman build -t %BE_IMAGE_NAME%:%BE_IMAGE_TAG% .
//                 """
//             }
//         }
//         stage('Start Zipkin Server') {
//                     steps {
//                         bat """
//                         REM Stop existing Zipkin container
//                         podman ps -a --format "{{.Names}}" | findstr /I "%ZIPKIN_IMAGE_NAME%" >nul
//                         IF %ERRORLEVEL%==0 (
//                             podman stop %ZIPKIN_IMAGE_NAME%
//                             podman rm %ZIPKIN_IMAGE_NAME%
//                         )
//                         REM Run Zipkin
//                         podman run -d -p %ZIPKIN_HOST_PORT%:9411 --name %ZIPKIN_IMAGE_NAME% openzipkin/zipkin
//                         """
//                     }
//                 }
//          stage('Start Grafana') {
//                     steps {
//                         bat """
//                         REM Stop existing Grafana container
//                         podman ps -a --format "{{.Names}}" | findstr /I "%GRAFANA_IMAGE_NAME%" >nul
//                         IF %ERRORLEVEL%==0 (
//                             podman stop %GRAFANA_IMAGE_NAME%
//                             podman rm %GRAFANA_IMAGE_NAME%
//                         )
//                         REM Run Grafana
//                         podman run -d -p %GRAFANA_HOST_PORT%:3000 --name %GRAFANA_IMAGE_NAME% grafana/grafana
//                         """
//                     }
//                 }
//
//         stage('Run Backend Container') {
//             steps {
//                 bat """
//                 REM Stop & remove existing container if exists
//                 podman ps -a --format "{{.Names}}" | findstr /I "%BE_IMAGE_NAME%-container" >nul
//                 IF %ERRORLEVEL%==0 (
//                     podman stop %BE_IMAGE_NAME%-container
//                     podman rm %BE_IMAGE_NAME%-container
//                 )
//
//                 REM Run new container
//                 podman run -d -p %BE_HOST_PORT%:8081 --name %BE_IMAGE_NAME%-container %BE_IMAGE_NAME%:%BE_IMAGE_TAG%
//                 """
//             }
//         }
//
//
//
//        stage('Backend Health Check') {
//     steps {
//         powershell """
//         \$port = ${BE_HOST_PORT}
//         \$maxTries = 12
//         \$count = 0
//
//         do {
//             try {
//                 Invoke-WebRequest -UseBasicParsing http://localhost:\$port -TimeoutSec 5
//                 Write-Host "Backend is up!"
//                 exit 0
//             } catch {
//                 Write-Host "Waiting for backend to start... Try \$count"
//                 Start-Sleep -Seconds 5
//                 \$count++
//             }
//         } while (\$count -lt \$maxTries)
//
//         Write-Error "Backend not responding after \$maxTries tries"
//         exit 1
//         """
//     }
// }
//
//
//     }
//
//     post {
//         success {
//             echo "✅ Backend CI/CD pipeline completed successfully!"
//         }
//         failure {
//             echo "❌ Backend pipeline failed!"
//         }
//         always {
//             echo "🧹 Cleaning workspace"
//             cleanWs()
//         }
//     }
// }

// pipeline {
//     agent any

//     environment {
//         BE_IMAGE_NAME = "weather-be"
//         BE_IMAGE_TAG = "latest"
//         BE_HOST_PORT = "8081"
//         REDIS_IMAGE_NAME = "weather-redis"
//         REDIS_IMAGE_TAG = "latest"
//         ZIPKIN_IMAGE_NAME = "zipkin"
//         ZIPKIN_HOST_PORT = "9411"
//         GRAFANA_IMAGE_NAME = "grafana"
//         GRAFANA_HOST_PORT = "3002"
//         SPRING_REDIS_HOST = 'weather-redis'
//         SPRING_REDIS_PORT = '6379'
//     }

//     stages {

//         stage('Checkout Backend') {
//             steps { git branch: 'main', url: 'https://github.com/Samiriiit/weather-be.git' }
//         }

//         stage('Build Backend') {
//             steps { bat 'mvn clean package' }
//         }

//         stage('Build Podman Image') {
//             steps { bat "podman build -t %BE_IMAGE_NAME%:%BE_IMAGE_TAG% ." }
//         }

//         stage('Deploy Redis') {
//             steps {
//                 bat """
//                 podman ps -a --format "{{.Names}}" | findstr /I "%REDIS_IMAGE_NAME%" >nul
//                 IF %ERRORLEVEL%==0 (
//                     podman stop %REDIS_IMAGE_NAME%
//                     podman rm %REDIS_IMAGE_NAME%
//                 )
//                 podman run -d -p 6379:6379 --name %REDIS_IMAGE_NAME% redis:7-alpine
//                 """
//             }
//         }

//         stage('Deploy Zipkin') {
//             steps {
//                 bat """
//                 podman ps -a --format "{{.Names}}" | findstr /I "%ZIPKIN_IMAGE_NAME%" >nul
//                 IF %ERRORLEVEL%==0 (
//                     podman stop %ZIPKIN_IMAGE_NAME%
//                     podman rm %ZIPKIN_IMAGE_NAME%
//                 )
//                 podman run -d -p %ZIPKIN_HOST_PORT%:9411 --name %ZIPKIN_IMAGE_NAME% openzipkin/zipkin
//                 """
//             }
//         }

//         stage('Deploy Grafana') {
//             steps {
//                 bat """
//                 podman ps -a --format "{{.Names}}" | findstr /I "%GRAFANA_IMAGE_NAME%" >nul
//                 IF %ERRORLEVEL%==0 (
//                     podman stop %GRAFANA_IMAGE_NAME%
//                     podman rm %GRAFANA_IMAGE_NAME%
//                 )
//                 podman run -d -p %GRAFANA_HOST_PORT%:3000 --name %GRAFANA_IMAGE_NAME% grafana/grafana
//                 """
//             }
//         }

//         stage('Deploy Backend Pod') {
//             steps {
//                 bat 'podman kube play weather-be.yaml --replace'
//                 echo "⚠️ Skipping Service YAML on Windows Podman (NodePort not supported)"
//             }
//         }

//         stage('Run Backend Container') {
//             steps {
//                 bat """
//                 podman ps -a --format "{{.Names}}" | findstr /I "%BE_IMAGE_NAME%-container" >nul
//                 IF %ERRORLEVEL%==0 (
//                     podman stop %BE_IMAGE_NAME%-container
//                     podman rm %BE_IMAGE_NAME%-container
//                 )
//                 podman run -d -p %BE_HOST_PORT%:8081 --name %BE_IMAGE_NAME%-container ^
//                     -e SPRING_REDIS_HOST=weather-redis ^
//                     -e SPRING_REDIS_PORT=6379 ^
//                     %BE_IMAGE_NAME%:%BE_IMAGE_TAG%
//                 """
//             }
//         }

//         stage('Backend Health Check') {
//             steps {
//                 powershell """
//                 \$port = ${BE_HOST_PORT}
//                 \$maxTries = 12
//                 \$count = 0
//                 do {
//                     try {
//                         Invoke-WebRequest -UseBasicParsing http://localhost:\$port -TimeoutSec 5
//                         Write-Host "Backend is up!"
//                         exit 0
//                     } catch {
//                         Write-Host "Waiting for backend... Try \$count"
//                         Start-Sleep -Seconds 5
//                         \$count++
//                     }
//                 } while (\$count -lt \$maxTries)
//                 Write-Error "Backend not responding after \$maxTries tries"
//                 exit 1
//                 """
//             }
//         }

//     }

//     post {
//         success { echo "✅ Backend + Redis + Zipkin + Grafana deployed successfully!" }
//         failure { echo "❌ Pipeline failed!" }
//         always { cleanWs() }
//     }
// }

// pipeline {
//     agent any

//     environment {
//         BE_IMAGE_NAME = "weather-be"
//         BE_IMAGE_TAG = "latest"
//         CLUSTER_NAME = "weather-app"
//         REDIS_IMAGE_NAME = "redis"
//         REDIS_IMAGE_TAG = "7-alpine"
//         ZIPKIN_IMAGE_NAME = "openzipkin/zipkin"
//         GRAFANA_IMAGE_NAME = "grafana/grafana"
//         NAMESPACE = "weather-app"
//     }

//     stages {
//         stage('Checkout Backend') {
//             steps {
//                 git branch: 'main', url: 'https://github.com/Samiriiit/weather-be.git'
//             }
//         }

//         stage('Build Backend') {
//             steps {
//                 bat 'mvn clean package -DskipTests'
//             }
//         }

//         stage('Build Backend Image') {
//             steps {
//                 bat "podman build -t %BE_IMAGE_NAME%:%BE_IMAGE_TAG% ."
//             }
//         }

//         stage('Load Images into Kind') {
//             steps {
//                 script {
//                     // Load backend image
//                     bat "podman save %BE_IMAGE_NAME%:%BE_IMAGE_TAG% -o weather-be.tar"
//                     bat "podman cp weather-be.tar weather-app-control-plane:/weather-be.tar"
//                     bat "podman exec weather-app-control-plane ctr image import /weather-be.tar"
//                     bat "podman exec weather-app-control-plane rm /weather-be.tar"
//                     bat "del weather-be.tar"
                    
//                     echo "✅ Backend image loaded into Kind cluster"
//                 }
//             }
//         }

//         stage('Create Namespace') {
//             steps {
//                 bat "kubectl create namespace %NAMESPACE% --dry-run=client -o yaml | kubectl apply -f -"
//             }
//         }

//         stage('Deploy Redis') {
//             steps {
//                 bat "kubectl apply -f redis-deployment.yaml -n %NAMESPACE%"
//             }
//         }

//         stage('Deploy Zipkin') {
//             steps {
//                 bat "kubectl apply -f zipkin-deployment.yaml -n %NAMESPACE%"
//             }
//         }

//         stage('Deploy Grafana') {
//             steps {
//                 bat "kubectl apply -f grafana-deployment.yaml -n %NAMESPACE%"
//             }
//         }

//         stage('Deploy Backend') {
//             steps {
//                 bat "kubectl apply -f weather-be-deployment.yaml -n %NAMESPACE%"
//             }
//         }

//         stage('Wait for Deployment') {
//     steps {
//         script {
//             // Wait for Redis
//             bat "kubectl wait --for=condition=available deployment/redis -n %NAMESPACE% --timeout=240s || echo 'Redis wait continued'"
            
//             // Simple wait for Spring Boot application to start
//             echo "⏳ Waiting 2 minutes for Spring Boot application to start..."
//             sleep(120) // 2 minutes wait
//         }
//     }
// }

//         stage('Verify Deployment') {
//             steps {
//                 script {
//                     bat "kubectl get all -n %NAMESPACE%"
//                     bat "kubectl get pods -n %NAMESPACE% -o wide"
                    
//                     // Simple health check - if pod is running, consider success
//                     def podStatus = bat(script: "kubectl get pods -n %NAMESPACE% -l app=weather-be -o jsonpath='{.items[0].status.phase}'", returnStdout: true).trim()
                    
//                     if (podStatus == "Running") {
//                         echo "✅ Backend pod is running successfully!"
//                         bat "kubectl logs -n %NAMESPACE% -l app=weather-be --tail=10 || echo 'Logs not available yet'"
//                     } else {
//                         echo "⚠️ Pod status: $podStatus"
//                         bat "kubectl describe pods -n %NAMESPACE% -l app=weather-be || true"
//                         error("Backend deployment failed - pod status: $podStatus")
//                     }
//                 }
//             }
//         }
//     }

//     post {
//         always {
//             // Cleanup temporary files
//             bat "if exist *.tar del *.tar"
//             echo "Pipeline completed: ${currentBuild.result}"
//         }
//         success {
//             echo "✅ Backend + Redis + Zipkin + Grafana deployed successfully!"
//         }
//         failure {
//             echo "❌ Deployment failed!"
//             bat "kubectl get events -n %NAMESPACE% --sort-by='.lastTimestamp' | findstr /i \"error\\|fail\" || echo 'No error events'"
//             bat "kubectl logs -n %NAMESPACE% -l app=weather-be --tail=20 || true"
//         }
//     }
// }


// pipeline {
//     agent any

//     environment {
//         BE_IMAGE_NAME = "weather-be"
//         BE_IMAGE_TAG = "latest"
//         CLUSTER_NAME = "weather-app"
//     }

//     stages {
//         stage('Checkout Backend') {
//             steps {
//                 git branch: 'main', url: 'https://github.com/Samiriiit/weather-be.git'
//             }
//         }

//         stage('Build Backend') {
//             steps {
//                 bat 'mvn clean package -DskipTests'
//             }
//         }

//         stage('Build Image') {
//             steps {
//                 bat "podman build -t %BE_IMAGE_NAME%:%BE_IMAGE_TAG% ."
//             }
//         }

//         stage('Load Image into Kind') {
//             steps {
//                 script {
//                     bat "podman save %BE_IMAGE_NAME%:%BE_IMAGE_TAG% -o weather-be.tar"
//                     bat "podman cp weather-be.tar weather-app-control-plane:/weather-be.tar"
//                     bat "podman exec weather-app-control-plane ctr image import /weather-be.tar"
//                     bat "podman exec weather-app-control-plane rm /weather-be.tar"
//                     bat "del weather-be.tar"
//                 }
//             }
//         }

//         stage('Deploy Backend') {
//             steps {
//                 bat "kubectl apply -f weather-be-deployment.yaml"
//             }
//         }

//         stage('Wait for Startup') {
//             steps {
//                 script {
//                     // Wait 2 minutes for Spring Boot to start
//                     echo "⏳ Waiting for Spring Boot application to start..."
//                     sleep(120)
//                 }
//             }
//         }

//         stage('Verify Deployment') {
//             steps {
//                 script {
//                     bat "kubectl get pods -l app=weather-be"
//                     bat "kubectl get svc -l app=weather-be"
                    
//                     // Check if pod is running (ignore probes for now)
//                     def podStatus = bat(script: "kubectl get pods -l app=weather-be -o jsonpath='{.items[0].status.phase}'", returnStdout: true).trim()
                    
//                     if (podStatus == "Running") {
//                         echo "✅ BE deployed successfully!"
//                         bat "kubectl logs -l app=weather-be --tail=10 || echo 'Logs check'"
//                     } else {
//                         echo "⚠️ Pod status: $podStatus - checking details..."
//                         bat "kubectl describe pods -l app=weather-be || true"
//                     }
//                 }
//             }
//         }
//     }

//     post {
//         always {
//             bat "if exist *.tar del *.tar"
//             echo "Pipeline completed: ${currentBuild.result}"
//         }
//         success {
//             echo "🎉 Backend deployed successfully!"
//             echo "Use: kubectl port-forward svc/weather-be-service 8081:8081"
//         }
//         failure {
//             echo "❌ Deployment failed!"
//             bat "kubectl logs -l app=weather-be --tail=20 || true"
//         }
//     }
// }

// pipeline {
//     agent any
//     stages {
//         stage('Checkout Code') {
//             steps {
//                 git branch: 'main', url: 'https://github.com/Samiriiit/weather-be.git'
//             }
//         }
        
//         stage('Build Image') {
//             steps {
//                 bat 'mvn clean package -DskipTests'
//                 bat 'minikube image build -t weather-be:latest .'
//             }
//         }
        
//         stage('Deploy') {
//             steps {
//                 bat 'kubectl apply -f redis-deployment.yaml'
//                 // bat 'kubectl apply -f zipkin-deployment.yaml'
//                 // bat 'kubectl apply -f grafana-deployment.yaml'
//                 bat 'kubectl apply -f weather-be-deployment.yaml'
//             }
//         }
        
//         stage('Verify') {
//             steps {
//                 sleep(30)
//                 bat "kubectl get pods -l app=weather-be | findstr Running"
//                 echo "✅ Backend Pod is Running"
//             }
//         }
//     }
    
//     post {
//         always {
//             echo "=== FINAL STATUS ==="
//             bat 'kubectl get pods'
//             bat 'kubectl get svc'
//         }
//     }
// }

// pipeline {
//     agent any
//     stages {
//         stage('Checkout Code') {
//             steps {
//                 git branch: 'main', url: 'https://github.com/Samiriiit/weather-be.git'
//             }
//         }

//         stage('Build Image') {
//             steps {
//                 bat 'mvn clean package -DskipTests'
//                 bat 'podman build -t docker.io/samiriiit1/weather-app:latest .'
//             }
//         }

//         stage('Push Image') {
//             steps {
//                 withCredentials([usernamePassword(credentialsId: 'docker-hub', 
//                                                 usernameVariable: 'DOCKER_USER', 
//                                                 passwordVariable: 'DOCKER_PASS')]) {
//                     bat 'podman login docker.io -u %DOCKER_USER% -p %DOCKER_PASS%'
//                     bat 'podman push docker.io/%DOCKER_USER%/weather-app:latest'
//                 }
//             }
//         }

//         stage('Deploy') {
//             steps {
//                 bat 'kubectl apply -f redis-deployment.yaml'
//                 bat 'kubectl apply -f weather-be-deployment.yaml'
//             }
//         }

//         stage('Verify') {
//             steps {
//                 sleep(30)
//                 bat 'kubectl get pods -l app=weather-be | findstr Running'
//                 echo "✅ Backend Pod is Running"
//             }
//         }
//     }

//     post {
//         always {
//             echo "=== FINAL STATUS ==="
//             bat 'kubectl get pods'
//             bat 'kubectl get svc'
//         }
//     }
// }

pipeline {
    agent any

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/Samiriiit/weather-be.git'
            }
        }

        stage('Build Image') {
            steps {
                // Build the BE jar first
                bat 'mvn clean package -DskipTests'

                // Build Docker/Podman image inside Minikube
                bat 'minikube image build -t weather-be:latest .'
            }
        }

        stage('Deploy') {
            steps {
                // Deploy Redis if needed
                bat 'kubectl apply -f redis-deployment.yaml'

                // Deploy BE using local image
                bat 'kubectl apply -f weather-be-deployment.yaml'
            }
        }

        stage('Verify') {
            steps {
                // Wait for pod to come up
                sleep(120)

                // Verify BE pod is running
                bat 'kubectl get pods -l app=weather-be | findstr Running'
                echo "✅ Backend Pod is Running"
            }
        }
    }

    post {
        always {
            echo "=== FINAL STATUS ==="
            bat 'kubectl get pods -l app=weather-be'
            bat 'kubectl get svc -l app=weather-be'
        }
    }
}
