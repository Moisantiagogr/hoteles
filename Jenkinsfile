pipeline {
    agent any

    // Aseguramos que el PATH incluya los binarios necesarios (como en tu ejemplo)
    environment {
        PATH = "/usr/bin:${env.PATH}"
    }

    stages {
        // 1. Parar servicios (Cambiado 'gmu' por 'hoteles')
        stage('Stopping services') {
            steps {
                sh '''
                    docker compose -p hoteles down || true
                '''
            }
        }

        // 2. Limpiar imágenes (Cambiado filtro a 'hoteles')
        stage('Deleting old images') {
            steps{
                sh '''
                    IMAGES=$(docker images --filter "label=com.docker.compose.project=hoteles" -q)
                    if [ -n "$IMAGES" ]; then
                        docker rmi -f $IMAGES
                    fi
                '''
            }
        }

        // 3. Descargar código
        stage('Pulling update') {
            steps {
                checkout scm
            }
        }

        // 4. Construir imágenes (Igual que el ejemplo, pero aplica a tu docker-compose)
        stage('Building new images') {
            steps {
                sh '''
                    docker compose build --no-cache
                '''
            }
        }

        // 5. Desplegar (AQUÍ AGREGUÉ LA SOLUCIÓN A TUS ERRORES ANTERIORES)
        stage('Deploying containers') {
            steps {
                sh '''
                    echo "Checking network and volumes..."
                    # Creamos la red 'hotel-net' si no existe (solución a tu error de red)
                    docker network create hotel-net || true
                    
                    # Creamos el volumen 'hotel-data' si no existe (solución a tu error de volumen)
                    docker volume create hotel-data || true

                    # Levantamos los servicios
                    docker compose up -d
                '''
            }
        }
    }

    post {
        success {
            echo 'Pipeline executed successfully.'
        }

        failure {
            echo 'An error occurred during pipeline execution, check the logs.'
        }
    }
}
