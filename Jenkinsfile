pipeline {
    agent any

    environment {
        BE_IMAGE_NAME = "weather-be"
        BE_IMAGE_TAG = "latest"
        BE_HOST_PORT = "8081"  // change if 8080 busy
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

        stage('Build Podman Image') {
            steps {
                bat "podman build -t %BE_IMAGE_NAME%:%BE_IMAGE_TAG% ."
            }
        }

        stage('Run Backend Container') {
            steps {
                bat """
                REM Stop & remove existing container if exists
                podman ps -a --format "{{.Names}}" | findstr /I "%BE_IMAGE_NAME%-container" >nul
                IF %ERRORLEVEL%==0 (
                    podman stop %BE_IMAGE_NAME%-container
                    podman rm %BE_IMAGE_NAME%-container
                )

                REM Run new container
                podman run -d -p %BE_HOST_PORT%:8081 --name %BE_IMAGE_NAME%-container %BE_IMAGE_NAME%:%BE_IMAGE_TAG%
                """
            }
        }

        stage('Backend Health Check') {
            steps {
                bat """
                timeout /t 5 /nobreak >nul
                curl http://localhost:%BE_HOST_PORT% || echo "Backend not responding" && exit /b 1
                """
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    def mvn = tool 'Default Maven'
                    withSonarQubeEnv('MySonarQube') {
                        bat "\"${mvn}\\bin\\mvn\" clean verify sonar:sonar -Dsonar.projectKeysqp_33d8c8ecc201dba5f917eb7661e40f4e3c43b343 -Dsonar.projectName='weather'"
                    }
                }
            }
        }

    }

    post {
        success {
            echo "✅ Backend CI/CD pipeline completed successfully!"
        }
        failure {
            echo "❌ Backend pipeline failed!"
        }
        always {
            echo "🧹 Cleaning workspace"
            cleanWs()
        }
    }
}
