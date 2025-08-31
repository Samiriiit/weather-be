pipeline {
    agent any

    environment {
        BE_IMAGE_NAME = "weather-be"
        BE_IMAGE_TAG = "latest"
        BE_HOST_PORT = "8081"  // Change if 8080 busy
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
                bat """
                podman build -t %BE_IMAGE_NAME%:%BE_IMAGE_TAG% .
                """
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
          stage('SonarQube Analysis') {
    steps {
        script {
            def mvn = tool 'Maven3'  
            withSonarQubeEnv('weather') {  
                bat "\"${mvn}\\bin\\mvn\" clean verify sonar:sonar -Dsonar.projectKey=weather-app -Dsonar.projectName=weather-app"
                
            }
        }
    }
}


       stage('Backend Health Check') {
    steps {
        powershell """
        \$port = ${BE_HOST_PORT}
        \$maxTries = 12
        \$count = 0

        do {
            try {
                Invoke-WebRequest -UseBasicParsing http://localhost:\$port -TimeoutSec 5
                Write-Host "Backend is up!"
                exit 0
            } catch {
                Write-Host "Waiting for backend to start... Try \$count"
                Start-Sleep -Seconds 5
                \$count++
            }
        } while (\$count -lt \$maxTries)

        Write-Error "Backend not responding after \$maxTries tries"
        exit 1
        """
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
