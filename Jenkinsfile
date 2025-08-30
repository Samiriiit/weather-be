pipeline {
    agent any

    environment {
        IMAGE_NAME = "weather-be"
        IMAGE_TAG = "latest"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Samiriiit/weather-be.git'
            }
        }

        stage('Build & Test') {
            steps {
                // Maven clean, compile, test
                bat 'mvn clean verify'
            }
        }

        stage('Package') {
            steps {
                // Build JAR without running tests again
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Build Podman Image') {
            steps {
                bat "podman build -t %IMAGE_NAME%:%IMAGE_TAG% ."
            }
        }

        stage('Run Podman Container') {
            steps {
                bat """
                REM Stop and remove existing container if exists
                podman ps -a --format "{{.Names}}" | findstr /I "%IMAGE_NAME%-container" >nul
                IF %ERRORLEVEL%==0 (
                    podman stop %IMAGE_NAME%-container
                    podman rm %IMAGE_NAME%-container
                ) ELSE (
                    echo Container not found, skipping
                )

                REM Run new container
                podman run -d -p 8080:8080 --name %IMAGE_NAME%-container %IMAGE_NAME%:%IMAGE_TAG%
                """
            }
        }

        stage('Test Container') {
            steps {
                bat """
                REM Wait for container to start
                timeout /t 10 /nobreak >nul

                REM Check if Spring Boot app is responding
                powershell -Command "try {Invoke-WebRequest http://localhost:8080/actuator/health -UseBasicParsing} catch {exit 1}"
                """
            }
        }
    }

    post {
        success {
            echo "✅ Spring Boot app deployed successfully!"
        }
        failure {
            echo "❌ Deployment failed!"
        }
    }
}
