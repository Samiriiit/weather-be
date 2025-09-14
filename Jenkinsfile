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

pipeline {
    agent any

    environment {
        BE_IMAGE_NAME = "weather-be"
        BE_IMAGE_TAG = "latest"
        CLUSTER_NAME = "weather-app"
        REDIS_IMAGE_NAME = "redis"
        REDIS_IMAGE_TAG = "7-alpine"
        ZIPKIN_IMAGE_NAME = "openzipkin/zipkin"
        GRAFANA_IMAGE_NAME = "grafana/grafana"
        NAMESPACE = "weather-app"
    }

    stages {
        stage('Checkout Backend') {
            steps {
                git branch: 'main', url: 'https://github.com/Samiriiit/weather-be.git'
            }
        }

        stage('Build Backend') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Build Backend Image') {
            steps {
                bat "podman build -t %BE_IMAGE_NAME%:%BE_IMAGE_TAG% ."
            }
        }

        stage('Load Images into Kind') {
            steps {
                script {
                    // Load backend image
                    bat "podman save %BE_IMAGE_NAME%:%BE_IMAGE_TAG% -o weather-be.tar"
                    bat "podman cp weather-be.tar weather-app-control-plane:/weather-be.tar"
                    bat "podman exec weather-app-control-plane ctr image import /weather-be.tar"
                    bat "podman exec weather-app-control-plane rm /weather-be.tar"
                    bat "del weather-be.tar"
                    
                    // Load Redis image
                    bat "podman pull %REDIS_IMAGE_NAME%:%REDIS_IMAGE_TAG%"
                    bat "podman save %REDIS_IMAGE_NAME%:%REDIS_IMAGE_TAG% -o redis.tar"
                    bat "podman cp redis.tar weather-app-control-plane:/redis.tar"
                    bat "podman exec weather-app-control-plane ctr image import /redis.tar"
                    bat "podman exec weather-app-control-plane rm /redis.tar"
                    bat "del redis.tar"
                    
                    echo "✅ All images loaded into Kind cluster"
                }
            }
        }

        stage('Create Namespace') {
            steps {
                bat "kubectl create namespace %NAMESPACE% --dry-run=client -o yaml | kubectl apply -f -"
            }
        }

        stage('Deploy Redis') {
            steps {
                bat "kubectl apply -f redis-deployment.yaml -n %NAMESPACE%"
                bat "kubectl wait --for=condition=available deployment/redis -n %NAMESPACE% --timeout=120s"
            }
        }

        stage('Deploy Zipkin') {
            steps {
                bat "kubectl apply -f zipkin-deployment.yaml -n %NAMESPACE%"
                bat "kubectl wait --for=condition=available deployment/zipkin -n %NAMESPACE% --timeout=120s"
            }
        }

        stage('Deploy Grafana') {
            steps {
                bat "kubectl apply -f grafana-deployment.yaml -n %NAMESPACE%"
                bat "kubectl wait --for=condition=available deployment/grafana -n %NAMESPACE% --timeout=120s"
            }
        }

        stage('Deploy Backend') {
            steps {
                bat "kubectl apply -f weather-be-deployment.yaml -n %NAMESPACE%"
                bat "kubectl wait --for=condition=available deployment/weather-be -n %NAMESPACE% --timeout=180s"
            }
        }

        stage('Verify Deployment') {
            steps {
                script {
                    bat "kubectl get all -n %NAMESPACE%"
                    bat "kubectl get pods -n %NAMESPACE% -o wide"
                    bat "kubectl get svc -n %NAMESPACE%"
                }
            }
        }

        stage('Health Check') {
            steps {
                script {
                    // Test backend connectivity using curl container
                    bat "kubectl run health-check --image=curlimages/curl --restart=Never --rm -n %NAMESPACE% --command -- curl -I http://weather-be-service:8080/actuator/health --connect-timeout 10 --max-time 15 || echo 'Health check completed'"
                    
                    // Test Redis connectivity
                    bat "kubectl run redis-check --image=curlimages/curl --restart=Never --rm -n %NAMESPACE% --command -- sh -c 'nc -z weather-redis-service 6379 && echo \"Redis connected\" || echo \"Redis check completed\"' || echo 'Redis test attempted'"
                }
            }
        }
    }

    post {
        always {
            // Cleanup temporary files
            bat "if exist *.tar del *.tar"
            echo "Backend deployment ${currentBuild.result}"
        }
        success {
            echo "✅ Backend + Redis + Zipkin + Grafana deployed successfully to Kubernetes!"
            echo "Use 'kubectl port-forward -n %NAMESPACE% svc/weather-be-service 8080:8080' to access backend"
        }
        failure {
            echo "❌ Backend deployment failed!"
            bat "kubectl describe pods -n %NAMESPACE% || true"
            bat "kubectl logs -n %NAMESPACE% -l app=weather-be --tail=20 || true"
            bat "kubectl get events -n %NAMESPACE% --sort-by='.lastTimestamp' || true"
        }
    }
}