pipeline {
    agent any

    stages {
        // 1. Parar los servicios existentes (si los hay)
        stage('Parando los servicios...') {
            steps {
                // El "|| true" asegura que el pipeline no falle si no hay nada corriendo
                sh 'docker compose -p hoteles down || true'
            }
        }

        // 2. Eliminar las imágenes antiguas
        stage('Eliminando imágenes anteriores...') {
            steps {
                // Lógica en Bash (Linux) para limpiar imágenes
                sh '''
                    IMG_IDS=$(docker images --filter "label=com.docker.compose.project=hoteles" -q)
                    
                    if [ -z "$IMG_IDS" ]; then
                        echo "No hay imagenes por eliminar"
                    else
                        docker rmi -f $IMG_IDS
                        echo "Imagenes eliminadas correctamente"
                    fi
                '''
            }
        }

        // 3. Descargar el código del repositorio
        stage('Obteniendo actualización...') {
            steps {
                checkout scm
            }
        }

        // 4. Construir y levantar los servicios
        stage('Construyendo y desplegando servicios...') {
            steps {
                sh '''
                    echo "Creando red 'hotel-net' si no existe..."
                    docker network create hotel-net || true

                    echo "Construyendo y levantando contenedores..."
                    docker compose up --build -d
                '''
            }
        }
    }

    post {
        success {
            echo 'Pipeline ejecutado con éxito'
        }

        failure {
            echo 'Hubo un error al ejecutar el pipeline'
        }

        always {
            echo 'Pipeline finalizado'
        }
    }
}
